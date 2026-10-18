# HidraAPI — Ultimate Canonical Roadmap

## 1. Control Status

| Field | Value |
|---|---|
| Status | ACTIVE |
| Accountable authority | Repository owner; initial regeneration and manifest addition explicitly authorized |
| Source parent / audit baseline | e0d6cfc15e9e5f57bbfe4ddf2992fb0026cd22f8 |
| Parent | aea3e497502c3e83a49fe4b7b2070b984d846bb0 |
| Tree | 92827aa8aed6afb490b12cf9669f9e9aad677b31 |
| Verification point | 2026-10-16; exact-source audit with Full #713, Fast #41 and Docs #358 success |
| Applicability | Development platform; synthetic software evidence, no current production/SITE acceptance established |

## 2. Canonical identifiers and authority

HPS-XXX identifies one major functional phase. HAPI-XXX identifies one backend obligation and is numbered sequentially across the repository, independent of phase. Legacy IDs remain permanent references. No source class, migration, commit or job is renamed. [ULTIMATE_PHASES.md](ULTIMATE_PHASES.md) owns the fixed phase baseline; [ULTIMATE_TASKS.md](ULTIMATE_TASKS.md) owns technical definitions, reconciliation, evidence, unknowns and proposed scope. This initial owner-authorized structural transition supersedes old identifier examples for these documents while retaining repository architecture and historical evidence.

## 3. Independent status vocabulary

Implementation Status: VERIFIED = bounded implementation and supporting verification established; IMPLEMENTED = source exists but verification incomplete; PARTIAL = incomplete capability; INTERNAL_ONLY = implemented without required public integration; NOT_IMPLEMENTED = required behavior absent; DEFECTIVE = demonstrated requirement/invariant violation; NOT_VERIFIABLE = insufficient evidence. These labels apply to the task's bounded objective, not automatically to an entire phase.

Planning Status: COMPLETED, IN_PROGRESS, REGISTERED_PENDING, BLOCKED, DEFERRED, SUPERSEDED. A completed documentation/preflight task proves its own deliverable only. A historical completion with insufficient independent implementation evidence is explicitly NOT_VERIFIABLE, not silently reopened. Further evidence and status rationale belong exclusively in ULTIMATE_TASKS.md. TARGET/DEFERRED work is distinct from current behavior; unresolved limits stay explicit.

## 4. Priorities

P0: authority/security or accepted verification prerequisite. P1: foundational engineering and operations. P2: dependent workflow, governance, integration or assurance. P3: explicitly deferred extension/numerical/industrial target. Priority does not override technical dependencies or select a release/deployment.

## 5. Global roadmap task index

| Phase ID | Phase Name | Task ID | Legacy ID(s) | Task Name | Deliverable | Priority | Dependencies | Implementation Status | Planning Status |
|---|---|---|---|---|---|---|---|---|---|
| HPS-001 | Identity and Authentication | [HAPI-011](ULTIMATE_TASKS.md#hapi-011) | AUTH-001 | add ldap and local authentication roadmap | Establish initial execution memory | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-012](ULTIMATE_TASKS.md#hapi-012) | AUTH-001A | refocus roadmap on authentication gaps | Preserve correct Identity implementation and target runtime gaps | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-013](ULTIMATE_TASKS.md#hapi-013) | AUTH-001B | align roadmap with dynamic provider routing | Preserve OIDC, make provider-selection runtime routing explicit, add central provider routing and unified JWT plan | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-014](ULTIMATE_TASKS.md#hapi-014) | AUTH-002 | inventory existing authentication runtime contracts | Classify live provider DTOs, provider-selection contract, OIDC flow, Basic/in-memory path, JWT components, Identity models, persistence, and missing pieces | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-015](ULTIMATE_TASKS.md#hapi-015) | AUTH-003 | add provider-specific authentication request tokens | Add/reuse distinct Spring Authentication request types for deterministic LOCAL, LDAP/AD, and OIDC routing | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-016](ULTIMATE_TASKS.md#hapi-016) | AUTH-004 | add authentication request router | Convert an explicit provider selection into the correct Authentication request without credential verification | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-017](ULTIMATE_TASKS.md#hapi-017) | AUTH-005 | compose authentication provider manager | Register provider-specific strategies behind one AuthenticationManager/ProviderManager with no fallback | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-018](ULTIMATE_TASKS.md#hapi-018) | AUTH-006 | normalize authenticated hidra principal | Reuse/add one Hidra principal contract shared by LOCAL, LDAP/AD, and OIDC | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-020](ULTIMATE_TASKS.md#hapi-020) | AUTH-008 | add local credential model and port | Add the proven-missing LOCAL credential domain/application contract | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-021](ULTIMATE_TASKS.md#hapi-021) | AUTH-009 | add local credential persistence | Add forward Flyway migration plus JPA repository/adapter for LOCAL password hashes | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-022](ULTIMATE_TASKS.md#hapi-022) | AUTH-010 | add database local authentication provider | Replace ordinary in-memory LOCAL verification with persistent password verification behind LocalAuthenticationProvider | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-023](ULTIMATE_TASKS.md#hapi-023) | AUTH-011 | record local authentication outcomes | Apply existing User lock/login state and AuthenticationEvent to LOCAL success/failure | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-024](ULTIMATE_TASKS.md#hapi-024) | AUTH-012 | add ldap security infrastructure | Add required LDAP dependency/configuration/TLS/timeouts without domain coupling | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-025](ULTIMATE_TASKS.md#hapi-025) | AUTH-013 | add ldap credential verification adapter | Implement AD/LDAP bind/search credential verification | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-026](ULTIMATE_TASKS.md#hapi-026) | AUTH-014 | add ldap authentication provider | Route LDAP token through directory verification, ExternalIdentity mapping, Hidra account state, and HidraPrincipal | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-027](ULTIMATE_TASKS.md#hapi-027) | AUTH-015 | add unified hidra access token issuer | Add JwtEncoder and issue one standardized Hidra JWT for all providers | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-028](ULTIMATE_TASKS.md#hapi-028) | AUTH-016 | complete authentication session lifecycle | Reuse LoginSession and AuthenticationEvent for all provider paths | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-029](ULTIMATE_TASKS.md#hapi-029) | AUTH-017 | wire dynamic login endpoint | Introduce and wire the direct LOCAL/LDAP provider-selection login boundary to router, AuthenticationManager, session, and token issuer | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-030](ULTIMATE_TASKS.md#hapi-030) | AUTH-018 | converge oidc completion on hidra token | Ensure OIDC completion produces same Hidra principal/session/JWT result as LOCAL/LDAP while preserving current PKCE behavior | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-032](ULTIMATE_TASKS.md#hapi-032) | AUTH-020 | retire ordinary in-memory local authentication | Remove InMemoryUserDetailsManager as ordinary LOCAL login only after DB path is proven | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-033](ULTIMATE_TASKS.md#hapi-033) | AUTH-021 | add safe local administrator bootstrap | Provide controlled persistent LOCAL administrator provisioning without permanent in-memory fallback | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-034](ULTIMATE_TASKS.md#hapi-034) | AUTH-022 | cover dynamic provider routing | Verify provider-selection dispatch, supports contracts, unsupported type, and no fallback | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-035](ULTIMATE_TASKS.md#hapi-035) | AUTH-023 | cover local authentication | Unit/integration/API coverage for persisted LOCAL authentication | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-036](ULTIMATE_TASKS.md#hapi-036) | AUTH-024 | cover ldap authentication | LDAP/AD adapter, mapping, outage, TLS, and end-to-end coverage | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-040](ULTIMATE_TASKS.md#hapi-040) | AUTH-028 | add ldap and provider deployment runbook | Document non-secret provider/TLS/configuration inputs and diagnostics | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-041](ULTIMATE_TASKS.md#hapi-041) | AUTH-029 | add authentication architecture and secret guardrails | Enforce module boundaries, credential secrecy, and provider isolation | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-042](ULTIMATE_TASKS.md#hapi-042) | AUTH-030 | finalize authentication gap closure checklist | Record executable evidence and remaining optional cleanup | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-155](ULTIMATE_TASKS.md#hapi-155) | HMC-010 | scan identity domain models | Scan all 15 identity model files. | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-187](ULTIMATE_TASKS.md#hapi-187) | HMR-010 | remediate semantic review IdentityProvider | remediate semantic review IdentityProvider | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-257](ULTIMATE_TASKS.md#hapi-257) | HMR-063 | remediate semantic review User | remediate semantic review User | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-281](ULTIMATE_TASKS.md#hapi-281) | HMR-087 | remediate semantic review LoginSession | remediate semantic review LoginSession | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-320](ULTIMATE_TASKS.md#hapi-320) | HMSR-010 | review identity IdentityProvider | review identity IdentityProvider | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-355](ULTIMATE_TASKS.md#hapi-355) | HMSR-045 | review identity ExternalRoleMapping | review identity ExternalRoleMapping | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-385](ULTIMATE_TASKS.md#hapi-385) | HMSR-075 | review identity User | review identity User | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-409](ULTIMATE_TASKS.md#hapi-409) | HMSR-099 | review identity AuthenticationEvent | review identity AuthenticationEvent | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-412](ULTIMATE_TASKS.md#hapi-412) | HMSR-102 | review identity HidraPrincipal | review identity HidraPrincipal | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-413](ULTIMATE_TASKS.md#hapi-413) | HMSR-103 | review identity LocalCredential | review identity LocalCredential | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-414](ULTIMATE_TASKS.md#hapi-414) | HMSR-104 | review identity LoginSession | review identity LoginSession | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-556](ULTIMATE_TASKS.md#hapi-556) | HPR-P25-044 | HPR-P25-044 historical bounded obligation | HPR-P25-044 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-557](ULTIMATE_TASKS.md#hapi-557) | HPR-P25-045 | HPR-P25-045 historical bounded obligation | HPR-P25-045 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-558](ULTIMATE_TASKS.md#hapi-558) | HPR-P25-046 | HPR-P25-046 historical bounded obligation | HPR-P25-046 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-559](ULTIMATE_TASKS.md#hapi-559) | HPR-P25-047 | Existing frozen seven-file task | Existing frozen seven-file task | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-560](ULTIMATE_TASKS.md#hapi-560) | HPR-P25-048 | HPR-P25-048 historical bounded obligation | HPR-P25-048 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-561](ULTIMATE_TASKS.md#hapi-561) | HPR-P25-049 | HPR-P25-049 historical bounded obligation | HPR-P25-049 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-562](ULTIMATE_TASKS.md#hapi-562) | HPR-P25-050 | Identity binding, issuance and operation reader | Identity binding, issuance and operation reader | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-563](ULTIMATE_TASKS.md#hapi-563) | HPR-P25-050A | add trusted authentication binding foundations | Trusted binding foundations and private verification receipts | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-564](ULTIMATE_TASKS.md#hapi-564) | HPR-P25-050B | persist immutable authentication bindings | Fresh-schema binding storage and session guards | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-565](ULTIMATE_TASKS.md#hapi-565) | HPR-P25-050C | complete trusted bound authentication | Real provider verification and atomic bound issuance | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-566](ULTIMATE_TASKS.md#hapi-566) | HPR-P25-050D | bind protected operations to trusted authentication | Bound-operation reader and integrated B1 acceptance | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-660](ULTIMATE_TASKS.md#hapi-660) | HRA-101 | isolate authentication web contract | Adapt Spring principal/session details into application-level authentication inputs/results. | P2 | — | VERIFIED | COMPLETED |
| HPS-001 | Identity and Authentication | [HAPI-667](ULTIMATE_TASKS.md#hapi-667) | ID-001 | add identity roadmap | Create execution memory | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-001 | Identity and Authentication | [HAPI-668](ULTIMATE_TASKS.md#hapi-668) | ID-002 | add identity package skeleton | Production package boundaries | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-001 | Identity and Authentication | [HAPI-669](ULTIMATE_TASKS.md#hapi-669) | ID-003 | add identity domain value objects | IDs, username, email, codes, names, statuses, references | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-001 | Identity and Authentication | [HAPI-673](ULTIMATE_TASKS.md#hapi-673) | ID-007 | add identity domain exceptions | Domain-specific failures | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-001 | Identity and Authentication | [HAPI-675](ULTIMATE_TASKS.md#hapi-675) | ID-009 | add identity domain policies and services | Assignment/evaluation/SoD policies | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-001 | Identity and Authentication | [HAPI-676](ULTIMATE_TASKS.md#hapi-676) | ID-010 | add application commands and queries | Framework-neutral use-case inputs | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-001 | Identity and Authentication | [HAPI-677](ULTIMATE_TASKS.md#hapi-677) | ID-011 | add application ports and DTOs | Inbound/outbound contracts | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-001 | Identity and Authentication | [HAPI-678](ULTIMATE_TASKS.md#hapi-678) | ID-012 | add application services | Use-case orchestration | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-001 | Identity and Authentication | [HAPI-679](ULTIMATE_TASKS.md#hapi-679) | ID-013 | add persistence entities and repositories | JPA adapters and migration | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-001 | Identity and Authentication | [HAPI-680](ULTIMATE_TASKS.md#hapi-680) | ID-014 | add infrastructure adapters and configuration | Password/event/configuration adapters | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-001 | Identity and Authentication | [HAPI-681](ULTIMATE_TASKS.md#hapi-681) | ID-015 | add REST API contracts and controllers | REST boundary | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-001 | Identity and Authentication | [HAPI-682](ULTIMATE_TASKS.md#hapi-682) | ID-016 | add identity domain tests | Domain/value/policy tests | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-001 | Identity and Authentication | [HAPI-683](ULTIMATE_TASKS.md#hapi-683) | ID-017 | add identity application tests | Application service tests | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-001 | Identity and Authentication | [HAPI-684](ULTIMATE_TASKS.md#hapi-684) | ID-018 | add identity persistence tests | Persistence adapter tests | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-001 | Identity and Authentication | [HAPI-685](ULTIMATE_TASKS.md#hapi-685) | ID-019 | add identity API tests | Controller/API tests | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-001 | Identity and Authentication | [HAPI-686](ULTIMATE_TASKS.md#hapi-686) | ID-020 | add identity architecture guardrail | ArchUnit boundary checks | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-001 | Identity and Authentication | [HAPI-687](ULTIMATE_TASKS.md#hapi-687) | ID-021 | finalize identity checklist | Final execution status | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-001 | Identity and Authentication | [HAPI-688](ULTIMATE_TASKS.md#hapi-688) | ID-GAP-001 | Qualify ordinary-user first-use activation | Determine the actual ordinary-user activation boundary and deliver the owner-selected single-use/expiry/access-restriction policy, without imposing local… | P0 | — | NOT_VERIFIABLE | BLOCKED |
| HPS-001 | Identity and Authentication | [HAPI-822](ULTIMATE_TASKS.md#hapi-822) | STB2-003 | restore identity roadmap execution memory | Recover Identity roadmap history and merge current-state record | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-001 | Identity and Authentication | [HAPI-832](ULTIMATE_TASKS.md#hapi-832) | STB-005 | add identity flyway migration | add identity flyway migration | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-019](ULTIMATE_TASKS.md#hapi-019) | AUTH-007 | normalize existing oidc authentication | Preserve working OIDC flow while mapping successful identities to Hidra User/HidraPrincipal and Hidra authorization | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-031](ULTIMATE_TASKS.md#hapi-031) | AUTH-019 | standardize protected api bearer authentication | Make protected APIs consume the unified Hidra JWT while preserving authorization behavior | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-037](ULTIMATE_TASKS.md#hapi-037) | AUTH-025 | cover oidc normalization | Protect existing OIDC behavior and prove Hidra principal/authorization normalization | P2 | — | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-038](ULTIMATE_TASKS.md#hapi-038) | AUTH-026 | verify unified jwt compatibility | Verify all providers produce tokens accepted by current security filters and permission enforcement | P2 | — | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-039](ULTIMATE_TASKS.md#hapi-039) | AUTH-027 | protect hidra authorization ownership | Prove external groups/claims do not bypass Hidra role/permission decisions | P2 | — | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-181](ULTIMATE_TASKS.md#hapi-181) | HMR-007 | remediate semantic review Role | remediate semantic review Role | P2 | — | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-182](ULTIMATE_TASKS.md#hapi-182) | HMR-007A | amend Role uniqueness remediation scope | amend Role uniqueness remediation scope | P2 | — | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-188](ULTIMATE_TASKS.md#hapi-188) | HMR-011 | remediate semantic review Permission | remediate semantic review Permission | P2 | — | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-279](ULTIMATE_TASKS.md#hapi-279) | HMR-085 | remediate semantic review AuthorizationDecision | remediate semantic review AuthorizationDecision | P2 | — | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-280](ULTIMATE_TASKS.md#hapi-280) | HMR-086 | remediate semantic review AuthorizationDelegationGrant | remediate semantic review AuthorizationDelegationGrant | P2 | — | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-282](ULTIMATE_TASKS.md#hapi-282) | HMR-088 | remediate semantic review UserPermissionGrant | remediate semantic review UserPermissionGrant | P2 | — | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-283](ULTIMATE_TASKS.md#hapi-283) | HMR-089 | remediate semantic review UserRoleGrant | remediate semantic review UserRoleGrant | P2 | — | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-317](ULTIMATE_TASKS.md#hapi-317) | HMSR-007 | review identity Role | review identity Role | P2 | — | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-321](ULTIMATE_TASKS.md#hapi-321) | HMSR-011 | review identity Permission | review identity Permission | P2 | — | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-356](ULTIMATE_TASKS.md#hapi-356) | HMSR-046 | review identity GroupRoleGrant | review identity GroupRoleGrant | P2 | — | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-357](ULTIMATE_TASKS.md#hapi-357) | HMSR-047 | review identity RolePermissionGrant | review identity RolePermissionGrant | P2 | — | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-410](ULTIMATE_TASKS.md#hapi-410) | HMSR-100 | review identity AuthorizationDecision | review identity AuthorizationDecision | P2 | — | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-411](ULTIMATE_TASKS.md#hapi-411) | HMSR-101 | review identity AuthorizationDelegationGrant | review identity AuthorizationDelegationGrant | P2 | — | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-415](ULTIMATE_TASKS.md#hapi-415) | HMSR-105 | review identity UserPermissionGrant | review identity UserPermissionGrant | P2 | — | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-416](ULTIMATE_TASKS.md#hapi-416) | HMSR-106 | review identity UserRoleGrant | review identity UserRoleGrant | P2 | — | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-552](ULTIMATE_TASKS.md#hapi-552) | HPR-P25-040 | HPR-P25-040 historical bounded obligation | HPR-P25-040 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-553](ULTIMATE_TASKS.md#hapi-553) | HPR-P25-041 | HPR-P25-041 historical bounded obligation | HPR-P25-041 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-555](ULTIMATE_TASKS.md#hapi-555) | HPR-P25-043 | HPR-P25-043 historical bounded obligation | HPR-P25-043 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-567](ULTIMATE_TASKS.md#hapi-567) | HPR-P25-051 | Integrated closure: Revocation, operation time and physical transaction fence | Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-051; child tasks own implementation. Revocation, operation time and physical… | P0 | HAPI-585 | PARTIAL | IN_PROGRESS |
| HPS-002 | Authorization and Access Control | [HAPI-568](ULTIMATE_TASKS.md#hapi-568) | HPR-P25-051A | enforce protected transaction lifecycle | enforce protected transaction lifecycle | P2 | — | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-569](ULTIMATE_TASKS.md#hapi-569) | HPR-P25-051B | compile protected static expression syntax | compile protected static expression syntax | P2 | HAPI-568 | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-570](ULTIMATE_TASKS.md#hapi-570) | HPR-P25-051C | reduce protected temporal boundaries | reduce protected temporal boundaries | P2 | HAPI-569 | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-571](ULTIMATE_TASKS.md#hapi-571) | HPR-P25-051D | collect fenced temporal event candidates | collect fenced temporal event candidates | P2 | HAPI-570 | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-572](ULTIMATE_TASKS.md#hapi-572) | HPR-P25-051E | fence complete authority dependency relations | fence complete authority dependency relations | P2 | HAPI-571 | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-573](ULTIMATE_TASKS.md#hapi-573) | HPR-P25-051F | contribute protected bound authentication | contribute protected bound authentication | P2 | HAPI-572 | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-574](ULTIMATE_TASKS.md#hapi-574) | HPR-P25-051G | load fenced authorization evidence | Fresh fenced authorization evidence; Identity | P2 | HAPI-573 | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-575](ULTIMATE_TASKS.md#hapi-575) | HPR-P25-051H | select operation time fencing proof | Trusted authorization-claim provenance and coverage; Identity | P0 | HAPI-574, HAPI-580 | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-576](ULTIMATE_TASKS.md#hapi-576) | HPR-P25-051H-A | define bounded claim coverage semantics | define bounded claim coverage semantics | P2 | HAPI-574 | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-577](ULTIMATE_TASKS.md#hapi-577) | HPR-P25-051H-B1 | preserve claim catalog fail-closed exception | preserve claim catalog fail-closed exception | P2 | HAPI-576 | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-578](ULTIMATE_TASKS.md#hapi-578) | HPR-P25-051H-B2 | project bounded oidc claim coverage | project bounded oidc claim coverage | P2 | HAPI-577 | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-579](ULTIMATE_TASKS.md#hapi-579) | HPR-P25-051H-C1 | project bounded oidc claim coverage | project bounded oidc claim coverage | P2 | HAPI-578 | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-580](ULTIMATE_TASKS.md#hapi-580) | HPR-P25-051H-C2A | capture verified oidc claim receipt | capture verified oidc claim receipt | P2 | HAPI-579 | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-581](ULTIMATE_TASKS.md#hapi-581) | HPR-P25-051I | Paired protected Identity authorization | Paired protected Identity authorization; Identity | P0 | HAPI-575 | VERIFIED | COMPLETED |
| HPS-002 | Authorization and Access Control | [HAPI-582](ULTIMATE_TASKS.md#hapi-582) | HPR-P25-051J | Party eligibility and Facility ownership participation | Party eligibility and Facility ownership participation; Party/Topology | P0 | HAPI-581 | PARTIAL | BLOCKED |
| HPS-002 | Authorization and Access Control | [HAPI-583](ULTIMATE_TASKS.md#hapi-583) | HPR-P25-051K | Global admission, fences and common database time | Global admission/fences and common time; trusted coordinator with owner contracts, Platform technical mechanics | P0 | HAPI-582 | PARTIAL | BLOCKED |
| HPS-002 | Authorization and Access Control | [HAPI-584](ULTIMATE_TASKS.md#hapi-584) | HPR-P25-051L | Protected internal Topology head and operation boundary | Protected internal Topology operation boundary; Topology | P0 | HAPI-583 | PARTIAL | BLOCKED |
| HPS-002 | Authorization and Access Control | [HAPI-585](ULTIMATE_TASKS.md#hapi-585) | HPR-P25-051M | Integrated adversarial, concurrency and latency qualification | Integrated adversarial/concurrency/latency qualification and parent closure; independent verification | P0 | HAPI-584 | PARTIAL | BLOCKED |
| HPS-002 | Authorization and Access Control | [HAPI-670](ULTIMATE_TASKS.md#hapi-670) | ID-004 | add permission domain model and policy | Permission model and code policy | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-002 | Authorization and Access Control | [HAPI-671](ULTIMATE_TASKS.md#hapi-671) | ID-005 | add role domain model | Role aggregate and permission assignment | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-002 | Authorization and Access Control | [HAPI-672](ULTIMATE_TASKS.md#hapi-672) | ID-006 | add user domain model | User aggregate and role assignment | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-002 | Authorization and Access Control | [HAPI-674](ULTIMATE_TASKS.md#hapi-674) | ID-008 | add identity domain events | User/role/permission events | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-002 | Authorization and Access Control | [HAPI-689](ULTIMATE_TASKS.md#hapi-689) | ID-GAP-002 | Qualify organization-scoped station monitoring | Prove station monitoring, history, counts, exports and subscriptions enforce current company and organizational responsibility; absorb compatible implementation… | P0 | — | PARTIAL | BLOCKED |
| HPS-003 | Organization and Responsibility | [HAPI-050](ULTIMATE_TASKS.md#hapi-050) | COR2-004 | remove organization unit type compatibility wrapper | Delete deprecated OrganizationUnitType wrapper and legacy organization bridges | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-052](ULTIMATE_TASKS.md#hapi-052) | COR2-006 | complete organization unit type catalog model | Ensure organization unit types are catalog-backed and trilingual | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-079](ULTIMATE_TASKS.md#hapi-079) | COR-016 | convert organization unit type to catalog entity | Convert organization unit type to catalog/reference modeling. | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-162](ULTIMATE_TASKS.md#hapi-162) | HMC-017 | scan organization domain models | Scan all 17 organization model files. | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-163](ULTIMATE_TASKS.md#hapi-163) | HMC-018 | scan party domain models | Scan all 2 party model files. | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-177](ULTIMATE_TASKS.md#hapi-177) | HMR-004 | remediate semantic review Party | remediate semantic review Party | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-199](ULTIMATE_TASKS.md#hapi-199) | HMR-020 | remediate semantic review Position | remediate semantic review Position | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-200](ULTIMATE_TASKS.md#hapi-200) | HMR-021 | remediate semantic review Shift | remediate semantic review Shift | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-209](ULTIMATE_TASKS.md#hapi-209) | HMR-028 | remediate semantic review ReportingLine | remediate semantic review ReportingLine | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-215](ULTIMATE_TASKS.md#hapi-215) | HMR-032 | remediate semantic review OrganizationUnit | remediate semantic review OrganizationUnit | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-221](ULTIMATE_TASKS.md#hapi-221) | HMR-036A | register Facility owner lookup prerequisite | register Facility owner lookup prerequisite | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-228](ULTIMATE_TASKS.md#hapi-228) | HMR-041 | remediate semantic review PartyRoleAssignment | remediate semantic review PartyRoleAssignment | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-267](ULTIMATE_TASKS.md#hapi-267) | HMR-073 | remediate semantic review EmployeeAssignment | remediate semantic review EmployeeAssignment | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-268](ULTIMATE_TASKS.md#hapi-268) | HMR-074 | remediate semantic review OrganizationDelegation | remediate semantic review OrganizationDelegation | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-269](ULTIMATE_TASKS.md#hapi-269) | HMR-075 | remediate semantic review OrganizationHierarchySnapshot | remediate semantic review OrganizationHierarchySnapshot | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-270](ULTIMATE_TASKS.md#hapi-270) | HMR-076 | remediate semantic review ShiftAssignment | remediate semantic review ShiftAssignment | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-311](ULTIMATE_TASKS.md#hapi-311) | HMSR-001 | review organization OrganizationUnitType | review organization OrganizationUnitType | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-313](ULTIMATE_TASKS.md#hapi-313) | HMSR-003 | review organization AdministrativeState | review organization AdministrativeState | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-314](ULTIMATE_TASKS.md#hapi-314) | HMSR-004 | review party Party | review party Party | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-326](ULTIMATE_TASKS.md#hapi-326) | HMSR-016 | review organization OperationalScope | review organization OperationalScope | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-332](ULTIMATE_TASKS.md#hapi-332) | HMSR-022 | review organization Position | review organization Position | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-333](ULTIMATE_TASKS.md#hapi-333) | HMSR-023 | review organization Shift | review organization Shift | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-340](ULTIMATE_TASKS.md#hapi-340) | HMSR-030 | review organization OrganizationContactPoint | review organization OrganizationContactPoint | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-341](ULTIMATE_TASKS.md#hapi-341) | HMSR-031 | review organization ReportingLine | review organization ReportingLine | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-345](ULTIMATE_TASKS.md#hapi-345) | HMSR-035 | review organization OrganizationUnit | review organization OrganizationUnit | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-346](ULTIMATE_TASKS.md#hapi-346) | HMSR-036 | review organization AdministrativeDistrict | review organization AdministrativeDistrict | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-350](ULTIMATE_TASKS.md#hapi-350) | HMSR-040 | review organization ResponsibilityAssignment | review organization ResponsibilityAssignment | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-359](ULTIMATE_TASKS.md#hapi-359) | HMSR-049 | review party PartyRoleAssignment | review party PartyRoleAssignment | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-363](ULTIMATE_TASKS.md#hapi-363) | HMSR-053 | review organization AdministrativeLocality | review organization AdministrativeLocality | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-375](ULTIMATE_TASKS.md#hapi-375) | HMSR-065 | review organization Employee | review organization Employee | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-396](ULTIMATE_TASKS.md#hapi-396) | HMSR-086 | review organization EmployeeAddress | review organization EmployeeAddress | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-397](ULTIMATE_TASKS.md#hapi-397) | HMSR-087 | review organization EmployeeAssignment | review organization EmployeeAssignment | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-398](ULTIMATE_TASKS.md#hapi-398) | HMSR-088 | review organization OrganizationDelegation | review organization OrganizationDelegation | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-399](ULTIMATE_TASKS.md#hapi-399) | HMSR-089 | review organization OrganizationHierarchySnapshot | review organization OrganizationHierarchySnapshot | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-400](ULTIMATE_TASKS.md#hapi-400) | HMSR-090 | review organization ShiftAssignment | review organization ShiftAssignment | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-551](ULTIMATE_TASKS.md#hapi-551) | HPR-P25-039 | HPR-P25-039 historical bounded obligation | HPR-P25-039 historical bounded obligation | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-632](ULTIMATE_TASKS.md#hapi-632) | HRA-010 | persist employee birth fields | Add dateOfBirth, birthLocalityId, birthPlaceAr, birthPlaceFr, birthPlaceEn to EmployeeJpaEntity. | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-633](ULTIMATE_TASKS.md#hapi-633) | HRA-011 | round trip employee birth data | Update both directions of OrganizationPersistenceMapper; stop using a compatibility constructor where it drops canonical state; add round-trip tests. | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-634](ULTIMATE_TASKS.md#hapi-634) | HRA-012 | align employee personal data contracts | Give every Employee birth field an explicit application/REST read/write policy; update registration or add a dedicated personal-data update use case as supported… | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-635](ULTIMATE_TASKS.md#hapi-635) | HRA-013 | migrate employee birth data schema | Inspect live Flyway/schema and add only missing immutable schema changes; preserve optional birthplace locality semantics. | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-636](ULTIMATE_TASKS.md#hapi-636) | HRA-020 | route employee contacts through contact points | Make OrganizationContactPoint the new-write path for employee EMAIL/MOBILE/PHONE; add missing contact-point application use cases. | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-638](ULTIMATE_TASKS.md#hapi-638) | HRA-022 | derive employee display names at boundaries | Stop accepting caller-supplied display names as authoritative; return derived Arabic/Latin display names. | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-639](ULTIMATE_TASKS.md#hapi-639) | HRA-023 | retire employee compatibility fields | Remove legacy Employee contact/display Java/schema state only after parity, consumer migration, and rollback evidence. | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-723](ULTIMATE_TASKS.md#hapi-723) | ORG-001 | add organization roadmap | Add this roadmap file | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-724](ULTIMATE_TASKS.md#hapi-724) | ORG-002 | add organization package skeleton | Add production package-info.java files only | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-725](ULTIMATE_TASKS.md#hapi-725) | ORG-003 | add organization domain value objects | Add IDs, codes, names, statuses, unit types, reporting types, operational scope types | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-726](ULTIMATE_TASKS.md#hapi-726) | ORG-004 | add position domain model | Add position model and catalog | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-727](ULTIMATE_TASKS.md#hapi-727) | ORG-005 | add organization unit domain model | Add organization unit aggregate with unit type and operational scope reference | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-728](ULTIMATE_TASKS.md#hapi-728) | ORG-006 | add employee domain model | Add employee aggregate, employee assignment, and reporting line model | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-729](ULTIMATE_TASKS.md#hapi-729) | ORG-007 | add organization domain exceptions | Add organization-specific exceptions including reporting line exception | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-730](ULTIMATE_TASKS.md#hapi-730) | ORG-008 | add organization domain events | Add employee/unit/position/reporting events | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-731](ULTIMATE_TASKS.md#hapi-731) | ORG-009 | add organization domain policies and services | Add hierarchy, lifecycle, assignment, and reporting line policies/services | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-732](ULTIMATE_TASKS.md#hapi-732) | ORG-010 | add application commands and queries | Add command/query records | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-733](ULTIMATE_TASKS.md#hapi-733) | ORG-011 | add application ports and DTOs | Add inbound/outbound ports and DTOs | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-734](ULTIMATE_TASKS.md#hapi-734) | ORG-012 | add application services | Add use-case services | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-735](ULTIMATE_TASKS.md#hapi-735) | ORG-013 | add persistence entities and repositories | Add JPA persistence layer and migration | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-736](ULTIMATE_TASKS.md#hapi-736) | ORG-014 | add infrastructure adapters and configuration | Add no-op event adapter and configuration | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-737](ULTIMATE_TASKS.md#hapi-737) | ORG-015 | add REST API contracts and controllers | Add REST requests/responses/controllers/mappers with Swagger | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-738](ULTIMATE_TASKS.md#hapi-738) | ORG-016 | add organization domain tests | Add domain/value/policy tests | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-739](ULTIMATE_TASKS.md#hapi-739) | ORG-017 | add organization application tests | Add application service tests | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-740](ULTIMATE_TASKS.md#hapi-740) | ORG-018 | add organization persistence tests | Add persistence adapter tests | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-741](ULTIMATE_TASKS.md#hapi-741) | ORG-019 | add organization API tests | Add controller/API tests | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-742](ULTIMATE_TASKS.md#hapi-742) | ORG-020 | add organization architecture guardrail | Add ArchUnit architecture tests | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-743](ULTIMATE_TASKS.md#hapi-743) | ORG-021 | finalize organization checklist | Update this roadmap with final status | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-744](ULTIMATE_TASKS.md#hapi-744) | ORG-022 | approve scope integrity ADR and correction roadmap | approve scope integrity ADR and correction roadmap | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-745](ULTIMATE_TASKS.md#hapi-745) | ORG-023 | inventory operational scope contracts and legacy tuples | inventory operational scope contracts and legacy tuples | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-746](ULTIMATE_TASKS.md#hapi-746) | ORG-024 | introduce canonical operational scope reference invariants | introduce canonical operational scope reference invariants | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-747](ULTIMATE_TASKS.md#hapi-747) | ORG-025 | add scope owner-resolution application ports | add scope owner-resolution application ports | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-748](ULTIMATE_TASKS.md#hapi-748) | ORG-026 | enforce multi-scope responsibility domain policy | enforce multi-scope responsibility domain policy | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-749](ULTIMATE_TASKS.md#hapi-749) | ORG-027 | add validated responsibility application use cases | add validated responsibility application use cases | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-750](ULTIMATE_TASKS.md#hapi-750) | ORG-028 | add backward-compatible scope schema | add backward-compatible scope schema | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-751](ULTIMATE_TASKS.md#hapi-751) | ORG-029 | align responsibility persistence and reconciliation | align responsibility persistence and reconciliation | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-752](ULTIMATE_TASKS.md#hapi-752) | ORG-030 | migrate operational scope API contracts | migrate operational scope API contracts | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-753](ULTIMATE_TASKS.md#hapi-753) | ORG-031 | remove duplicated active scope ownership | remove duplicated active scope ownership | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-754](ULTIMATE_TASKS.md#hapi-754) | ORG-032 | retire legacy redundant scope columns | retire legacy redundant scope columns | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-755](ULTIMATE_TASKS.md#hapi-755) | ORG-033 | verify scope integrity end to end | verify scope integrity end to end | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-756](ULTIMATE_TASKS.md#hapi-756) | ORG-034 | define embedded multilingual field policy | define embedded multilingual field policy | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-757](ULTIMATE_TASKS.md#hapi-757) | ORG-035 | inventory multilingual fields and unit-type translations | inventory multilingual fields and unit-type translations | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-758](ULTIMATE_TASKS.md#hapi-758) | ORG-036 | add embedded multilingual unit-type schema | add embedded multilingual unit-type schema | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-759](ULTIMATE_TASKS.md#hapi-759) | ORG-037 | embed multilingual unit-type fields | embed multilingual unit-type fields | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-760](ULTIMATE_TASKS.md#hapi-760) | ORG-038 | retire unit-type translation code | retire unit-type translation code | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-761](ULTIMATE_TASKS.md#hapi-761) | ORG-039 | retire unit-type translation table | retire unit-type translation table | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-762](ULTIMATE_TASKS.md#hapi-762) | ORG-040 | verify embedded multilingual integrity | verify embedded multilingual integrity | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-763](ULTIMATE_TASKS.md#hapi-763) | ORG-041 | type responsibility assignee discriminator | type responsibility assignee discriminator | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-764](ULTIMATE_TASKS.md#hapi-764) | ORG-042 | govern reporting line subjects | govern reporting line subjects | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-765](ULTIMATE_TASKS.md#hapi-765) | ORG-043 | govern contact point targets | govern contact point targets | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-766](ULTIMATE_TASKS.md#hapi-766) | ORG-044 | use operational scope reference value | use operational scope reference value | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-767](ULTIMATE_TASKS.md#hapi-767) | ORG-045 | adopt organization code value | adopt organization code value | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-768](ULTIMATE_TASKS.md#hapi-768) | ORG-046 | enforce internal reference integrity | enforce internal reference integrity | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-769](ULTIMATE_TASKS.md#hapi-769) | ORG-047 | retire multilingual compatibility columns | retire multilingual compatibility columns | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-770](ULTIMATE_TASKS.md#hapi-770) | ORG-048 | enforce typed dependency integrity | enforce typed dependency integrity | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-771](ULTIMATE_TASKS.md#hapi-771) | ORG-049 | define final employee domain ownership | define final employee domain ownership | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-772](ULTIMATE_TASKS.md#hapi-772) | ORG-050 | complete employee personal domain model | complete employee personal domain model | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-773](ULTIMATE_TASKS.md#hapi-773) | ORG-051 | separate employee contact ownership | separate employee contact ownership | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-774](ULTIMATE_TASKS.md#hapi-774) | ORG-052 | normalize employee display name semantics | normalize employee display name semantics | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-775](ULTIMATE_TASKS.md#hapi-775) | ORG-053 | harden organization domain invariants | harden organization domain invariants | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-776](ULTIMATE_TASKS.md#hapi-776) | ORG-054 | clarify organization identifier value semantics | clarify organization identifier value semantics | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-777](ULTIMATE_TASKS.md#hapi-777) | ORG-055 | verify model and value integrity | verify model and value integrity | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-778](ULTIMATE_TASKS.md#hapi-778) | ORG-GAP-003 | Complete employee appointment lifecycle and position history | Provide atomic end-date/transfer/correction and position-occupant history under an explicit concurrent/acting-assignment policy, preserving existing appointment… | P1 | — | PARTIAL | REGISTERED_PENDING |
| HPS-003 | Organization and Responsibility | [HAPI-829](ULTIMATE_TASKS.md#hapi-829) | STB-002 | expose jpa entity constructors to persistence mapper | expose jpa entity constructors to persistence mapper | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-833](ULTIMATE_TASKS.md#hapi-833) | STB-006 | confirm organization flyway migration | confirm organization flyway migration | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-834](ULTIMATE_TASKS.md#hapi-834) | STB-007 | complete organization controller openapi responses | complete organization controller openapi responses | P1 | — | VERIFIED | COMPLETED |
| HPS-003 | Organization and Responsibility | [HAPI-835](ULTIMATE_TASKS.md#hapi-835) | STB-008 | enforce reporting line self-reporting rule | enforce reporting line self-reporting rule | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-048](ULTIMATE_TASKS.md#hapi-048) | COR2-002 | inventory deprecated and transitional files | Create the verified deletion inventory before code changes | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-049](ULTIMATE_TASKS.md#hapi-049) | COR2-003 | remove product type compatibility wrapper | Delete deprecated ProductType wrapper and legacy topology bridges | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-051](ULTIMATE_TASKS.md#hapi-051) | COR2-005 | complete product type catalog model | Ensure product types are catalog-backed and trilingual | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-054](ULTIMATE_TASKS.md#hapi-054) | COR2-008 | standardize pipeline multilingual labels | Replace single-language pipeline API labels with trilingual fields | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-066](ULTIMATE_TASKS.md#hapi-066) | COR-003 | realign topology roadmap with repository reality | Realign topology docs with actual TOP sequence. | P1 | — | VERIFIED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-068](ULTIMATE_TASKS.md#hapi-068) | COR-005 | add topology type catalog tables | Add topology catalog and translation tables in V003. | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-069](ULTIMATE_TASKS.md#hapi-069) | COR-006 | add catalog domain model and references | Add topology catalog domain model and type references. | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-070](ULTIMATE_TASKS.md#hapi-070) | COR-007 | add topology catalog application services | Add catalog DTOs, queries, ports, and application service. | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-071](ULTIMATE_TASKS.md#hapi-071) | COR-008 | add topology catalog persistence adapters | Add catalog persistence adapter and configuration wiring. | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-072](ULTIMATE_TASKS.md#hapi-072) | COR-009 | replace enum usage in domain assets with type references | Refactor topology domain/application asset contracts to use references. | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-073](ULTIMATE_TASKS.md#hapi-073) | COR-010 | migrate topology assets to catalog foreign keys | Add and backfill topology catalog FK columns in V004. | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-074](ULTIMATE_TASKS.md#hapi-074) | COR-011 | update topology persistence mapping to catalog foreign keys | Use catalog FK ids in topology persistence. | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-075](ULTIMATE_TASKS.md#hapi-075) | COR-012 | update topology REST contracts for catalog types and localization | REST uses typeCode and localized type responses. | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-076](ULTIMATE_TASKS.md#hapi-076) | COR-013 | remove topology enum-style type constraints | Remove old topology type varchar columns/checks in V005. | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-077](ULTIMATE_TASKS.md#hapi-077) | COR-014 | repair unstable topology tests | Stabilize topology fixtures and tests after catalog conversion. | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-080](ULTIMATE_TASKS.md#hapi-080) | COR-017 | update topology roadmap after catalog refactor | Update topology roadmap/checklist after catalog refactor. | P1 | — | VERIFIED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-082](ULTIMATE_TASKS.md#hapi-082) | COR-019 | remove remaining business taxonomy enum declarations | Replace remaining forbidden enum declarations with compatibility classes. | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-083](ULTIMATE_TASKS.md#hapi-083) | COR-020 | record COR-019 and corrected baseline validation path | Record COR-019/COR-020 and add transition constructor fix. | P1 | — | VERIFIED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-169](ULTIMATE_TASKS.md#hapi-169) | HMC-024 | scan topology domain models | Scan all 5 topology model files. | P1 | — | VERIFIED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-201](ULTIMATE_TASKS.md#hapi-201) | HMR-022 | remediate semantic review PipelineSystem | remediate semantic review PipelineSystem | P1 | — | VERIFIED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-213](ULTIMATE_TASKS.md#hapi-213) | HMR-031 | remediate semantic review TopologyConnection | remediate semantic review TopologyConnection | P1 | — | VERIFIED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-220](ULTIMATE_TASKS.md#hapi-220) | HMR-036 | remediate semantic review Facility | remediate semantic review Facility | P1 | — | VERIFIED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-229](ULTIMATE_TASKS.md#hapi-229) | HMR-042 | remediate semantic review Pipeline | remediate semantic review Pipeline | P1 | — | VERIFIED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-230](ULTIMATE_TASKS.md#hapi-230) | HMR-042A | register Pipeline catalog prerequisite | register Pipeline catalog prerequisite | P1 | — | VERIFIED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-334](ULTIMATE_TASKS.md#hapi-334) | HMSR-024 | review topology PipelineSystem | review topology PipelineSystem | P1 | — | VERIFIED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-344](ULTIMATE_TASKS.md#hapi-344) | HMSR-034 | review topology TopologyConnection | review topology TopologyConnection | P1 | — | VERIFIED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-351](ULTIMATE_TASKS.md#hapi-351) | HMSR-041 | review topology Facility | review topology Facility | P1 | — | VERIFIED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-360](ULTIMATE_TASKS.md#hapi-360) | HMSR-050 | review topology Pipeline | review topology Pipeline | P1 | — | VERIFIED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-509](ULTIMATE_TASKS.md#hapi-509) | HPR-P25-003 | expose operational twin visualization | Provide authorized network map/state APIs with product and asset layers, connectivity, measured/estimated overlays, quality/freshness and issue location; verify… | P3 | HAPI-504 | NOT_VERIFIABLE | DEFERRED |
| HPS-004 | Physical Network Topology | [HAPI-586](ULTIMATE_TASKS.md#hapi-586) | HPR-P25-052 | Integrated closure: six topology/operator delivery packages | Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-052; child tasks own implementation. six topology/operator delivery packages | P1 | HAPI-592 | PARTIAL | BLOCKED |
| HPS-004 | Physical Network Topology | [HAPI-587](ULTIMATE_TASKS.md#hapi-587) | HPR-P25-052A | Company/network-owned engineering drafts | Company/network-owned drafts and bounded create/read/update/validate lifecycle. | P1 | HAPI-567 | NOT_IMPLEMENTED | BLOCKED |
| HPS-004 | Physical Network Topology | [HAPI-588](ULTIMATE_TASKS.md#hapi-588) | HPR-P25-052B | Physical network and station incidence authoring | Physical network/branch/station/port graph authoring and incidence validation. | P1 | HAPI-587 | NOT_IMPLEMENTED | BLOCKED |
| HPS-004 | Physical Network Topology | [HAPI-590](ULTIMATE_TASKS.md#hapi-590) | HPR-P25-052D | Public governed revision publication and history | Public protected publish/exact read/current/history/effective/list lifecycle over051. | P1 | HAPI-567, HAPI-588, HAPI-589 | NOT_IMPLEMENTED | BLOCKED |
| HPS-004 | Physical Network Topology | [HAPI-592](ULTIMATE_TASKS.md#hapi-592) | HPR-P25-052F | Topology operator/API/history qualification | Cross-slice operator/API/security/history qualification and consumer-ready contract. | P1 | HAPI-591 | NOT_IMPLEMENTED | BLOCKED |
| HPS-004 | Physical Network Topology | [HAPI-658](ULTIMATE_TASKS.md#hapi-658) | HRA-091 | expose operational scope resolution contract | Export the Topology scope-resolution contract consumed by Organization. | P1 | — | VERIFIED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-666](ULTIMATE_TASKS.md#hapi-666) | HRA-122 | remove residual projection vocabulary | Delete only the HRA-120-classified zero-incoming topology.domain.value.ProjectionType enum. Re-run static incoming-reference search immediately before deletion; if… | P1 | — | IMPLEMENTED | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-871](ULTIMATE_TASKS.md#hapi-871) | TOP-001 | add topology implementation roadmap | add topology implementation roadmap | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-872](ULTIMATE_TASKS.md#hapi-872) | TOP-002 | add topology package skeleton | add topology package skeleton | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-873](ULTIMATE_TASKS.md#hapi-873) | TOP-003 | add topology domain value objects | add topology domain value objects | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-874](ULTIMATE_TASKS.md#hapi-874) | TOP-004 | add topology domain models | add topology domain models | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-875](ULTIMATE_TASKS.md#hapi-875) | TOP-005 | add topology domain policies | add topology domain policies | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-876](ULTIMATE_TASKS.md#hapi-876) | TOP-006 | add topology domain services | add topology domain services | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-877](ULTIMATE_TASKS.md#hapi-877) | TOP-007 | add topology application commands and queries | add topology application commands and queries | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-878](ULTIMATE_TASKS.md#hapi-878) | TOP-008 | add topology application DTOs | add topology application DTOs | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-879](ULTIMATE_TASKS.md#hapi-879) | TOP-009 | add topology application ports | add topology application ports | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-880](ULTIMATE_TASKS.md#hapi-880) | TOP-010 | add topology application services | add topology application services | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-881](ULTIMATE_TASKS.md#hapi-881) | TOP-011 | add topology persistence entities and repositories | add topology persistence entities and repositories | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-882](ULTIMATE_TASKS.md#hapi-882) | TOP-012 | add topology persistence mapper and adapters | add topology persistence mapper and adapters | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-883](ULTIMATE_TASKS.md#hapi-883) | TOP-013 | add topology flyway migration | add topology flyway migration | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-884](ULTIMATE_TASKS.md#hapi-884) | TOP-014 | add topology REST request DTOs | add topology REST request DTOs | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-885](ULTIMATE_TASKS.md#hapi-885) | TOP-015 | add topology REST response DTOs | add topology REST response DTOs | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-886](ULTIMATE_TASKS.md#hapi-886) | TOP-016 | add topology REST mapper | add topology REST mapper | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-887](ULTIMATE_TASKS.md#hapi-887) | TOP-017 | add topology REST controllers | add topology REST controllers | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-888](ULTIMATE_TASKS.md#hapi-888) | TOP-018 | add topology domain tests | add topology domain tests | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-889](ULTIMATE_TASKS.md#hapi-889) | TOP-019 | add topology application service tests | add topology application service tests | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-890](ULTIMATE_TASKS.md#hapi-890) | TOP-020 | add topology persistence tests | add topology persistence tests | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-891](ULTIMATE_TASKS.md#hapi-891) | TOP-021 | add topology REST mapper and controller tests | add topology REST mapper and controller tests | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-892](ULTIMATE_TASKS.md#hapi-892) | TOP-022 | add topology application boot smoke test | add topology application boot smoke test | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-893](ULTIMATE_TASKS.md#hapi-893) | TOP-023 | finalize topology validation checklist | finalize topology validation checklist | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-004 | Physical Network Topology | [HAPI-894](ULTIMATE_TASKS.md#hapi-894) | TOP-COR-001 | TOP-COR-001 historical bounded obligation | TOP-COR-001 historical bounded obligation | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-004 | Physical Network Topology | [HAPI-895](ULTIMATE_TASKS.md#hapi-895) | TOP-COR-002 | TOP-COR-002 historical bounded obligation | TOP-COR-002 historical bounded obligation | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-005 | Geospatial Routing and Elevation | [HAPI-112](ULTIMATE_TASKS.md#hapi-112) | EXT-016 | prove postgis linear reference compatibility | Add a focused proof-of-concept for PostGIS geometry, Hibernate Spatial/JTS and measured-coordinate handling before schema commitment. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-005 | Geospatial Routing and Elevation | [HAPI-113](ULTIMATE_TASKS.md#hapi-113) | EXT-017 | add authoritative pipeline route geometry | Add approved PostGIS route geometry to Topology using actual next migration. | P3 | — | PARTIAL | DEFERRED |
| HPS-005 | Geospatial Routing and Elevation | [HAPI-114](ULTIMATE_TASKS.md#hapi-114) | EXT-018 | add pipeline route persistence | Add Topology domain/application/persistence support required by the approved route design. | P3 | — | PARTIAL | DEFERRED |
| HPS-005 | Geospatial Routing and Elevation | [HAPI-115](ULTIMATE_TASKS.md#hapi-115) | EXT-019 | define ili spatial extension | Map ILI run/anomaly requirements onto existing inspection run/finding, corrosion, wall-thickness and defect models. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-005 | Geospatial Routing and Elevation | [HAPI-116](ULTIMATE_TASKS.md#hapi-116) | EXT-020 | add ili spatial evidence | Add only missing PostGIS/ILI columns/tables using actual next Flyway migration. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-005 | Geospatial Routing and Elevation | [HAPI-117](ULTIMATE_TASKS.md#hapi-117) | EXT-021 | add ili spatial mapping | Add approved domain/application/persistence mapping for inspection anomalies and geometry/KP reconciliation. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-005 | Geospatial Routing and Elevation | [HAPI-531](ULTIMATE_TASKS.md#hapi-531) | HPR-P25-019 | define revisioned route and elevation profiles | define revisioned route and elevation profiles | P1 | — | VERIFIED | COMPLETED |
| HPS-005 | Geospatial Routing and Elevation | [HAPI-589](ULTIMATE_TASKS.md#hapi-589) | HPR-P25-052C | Survey, CRS, profile and pipe-section authoring | Survey/CRS/vertical datum/chainage/elevation/profile and pipe-section authoring. | P1 | HAPI-587 | NOT_IMPLEMENTED | BLOCKED |
| HPS-006 | Stations and Equipment | [HAPI-248](ULTIMATE_TASKS.md#hapi-248) | HMR-054 | remediate semantic review Equipment | remediate semantic review Equipment | P1 | — | VERIFIED | COMPLETED |
| HPS-006 | Stations and Equipment | [HAPI-373](ULTIMATE_TASKS.md#hapi-373) | HMSR-063 | review topology Equipment | review topology Equipment | P1 | — | VERIFIED | COMPLETED |
| HPS-006 | Stations and Equipment | [HAPI-504](ULTIMATE_TASKS.md#hapi-504) | HPR-P25-002 | establish product aware twin network inputs | Extend only demonstrated gaps in product-aware connected topology, fluid-property/composition references, equipment characteristics and versioned operating… | P3 | HAPI-503 | PARTIAL | DEFERRED |
| HPS-006 | Stations and Equipment | [HAPI-547](ULTIMATE_TASKS.md#hapi-547) | HPR-P25-035 | inventory station graph persistence prerequisites | f1659fe7677050c1a0d80542506db401ccbbdcec, Documentation #226 SUCCESS | P1 | — | VERIFIED | COMPLETED |
| HPS-006 | Stations and Equipment | [HAPI-549](ULTIMATE_TASKS.md#hapi-549) | HPR-P25-037 | persist immutable station graph revisions | persist immutable station graph revisions | P1 | — | VERIFIED | COMPLETED |
| HPS-006 | Stations and Equipment | [HAPI-598](ULTIMATE_TASKS.md#hapi-598) | HPR-P25-054 | Integrated closure: five installed-unit/manufacturer-data packages | Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-054; child tasks own implementation. five installed-unit/manufacturer-data… | P1 | HAPI-603 | PARTIAL | BLOCKED |
| HPS-006 | Stations and Equipment | [HAPI-599](ULTIMATE_TASKS.md#hapi-599) | HPR-P25-054A | Independent installed-unit identity and references | Independent installed-unit identity, model/serial/lifecycle and correct source-owner references. | P1 | HAPI-567 | NOT_IMPLEMENTED | BLOCKED |
| HPS-006 | Stations and Equipment | [HAPI-600](ULTIMATE_TASKS.md#hapi-600) | HPR-P25-054B | Effective station configuration and connectivity | Effective facility/station configuration membership and series/parallel installed incidence. | P1 | HAPI-588, HAPI-599 | NOT_IMPLEMENTED | BLOCKED |
| HPS-006 | Stations and Equipment | [HAPI-602](ULTIMATE_TASKS.md#hapi-602) | HPR-P25-054D | Versioned installed-unit/characteristic links | Versioned installed-unit to characteristic links, approval and current/historical read. | P1 | HAPI-600, HAPI-601 | NOT_IMPLEMENTED | BLOCKED |
| HPS-006 | Stations and Equipment | [HAPI-603](ULTIMATE_TASKS.md#hapi-603) | HPR-P25-054E | Installed-equipment consumer qualification | Owner/API/security/compatibility and consumer-ready qualification. | P1 | HAPI-602 | NOT_IMPLEMENTED | BLOCKED |
| HPS-007 | Manufacturer Engineering Data | [HAPI-533](ULTIMATE_TASKS.md#hapi-533) | HPR-P25-021 | define manufacturer characteristic provisioning | define manufacturer characteristic provisioning | P1 | — | VERIFIED | COMPLETED |
| HPS-007 | Manufacturer Engineering Data | [HAPI-601](ULTIMATE_TASKS.md#hapi-601) | HPR-P25-054C | Supplied manufacturer maps, tables and limits | Supplied manufacturer maps/tables/limits/axes/units/reference conditions and source evidence. | P1 | HAPI-599 | NOT_IMPLEMENTED | BLOCKED |
| HPS-008 | Products and Fluid Properties | [HAPI-532](ULTIMATE_TASKS.md#hapi-532) | HPR-P25-020 | define station and installed unit provisioning | define station and installed unit provisioning | P1 | — | VERIFIED | COMPLETED |
| HPS-008 | Products and Fluid Properties | [HAPI-593](ULTIMATE_TASKS.md#hapi-593) | HPR-P25-053 | Integrated closure: four product/composition/method packages | Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-053; child tasks own implementation. four product/composition/method packages | P1 | HAPI-597 | PARTIAL | BLOCKED |
| HPS-008 | Products and Fluid Properties | [HAPI-594](ULTIMATE_TASKS.md#hapi-594) | HPR-P25-053A | Product identity and metadata drafts | Company-owned product identity/classification and versioned metadata drafts. | P1 | HAPI-567 | NOT_IMPLEMENTED | BLOCKED |
| HPS-008 | Products and Fluid Properties | [HAPI-595](ULTIMATE_TASKS.md#hapi-595) | HPR-P25-053B | Supplied composition, properties and methods | Supplied composition/property/reference-basis/units/method applicability and evidence. | P1 | HAPI-594 | NOT_IMPLEMENTED | BLOCKED |
| HPS-008 | Products and Fluid Properties | [HAPI-596](ULTIMATE_TASKS.md#hapi-596) | HPR-P25-053C | Product publication, history and owner exports | Governed publish/withdrawal/current/history/effective selection and scalar owner exports. | P1 | HAPI-595 | NOT_IMPLEMENTED | BLOCKED |
| HPS-008 | Products and Fluid Properties | [HAPI-597](ULTIMATE_TASKS.md#hapi-597) | HPR-P25-053D | Multi-product ownership/API compatibility qualification | Multi-product owner/API/compatibility qualification and consumer-ready contract. | P1 | HAPI-596 | NOT_IMPLEMENTED | BLOCKED |
| HPS-009 | Assets and Maintenance | [HAPI-149](ULTIMATE_TASKS.md#hapi-149) | HMC-004 | scan assets domain models | Scan all 3 assets model files. | P2 | — | VERIFIED | COMPLETED |
| HPS-009 | Assets and Maintenance | [HAPI-233](ULTIMATE_TASKS.md#hapi-233) | HMR-045 | remediate semantic review MaintainableAsset | remediate semantic review MaintainableAsset | P2 | — | VERIFIED | COMPLETED |
| HPS-009 | Assets and Maintenance | [HAPI-234](ULTIMATE_TASKS.md#hapi-234) | HMR-045A | register MaintainableAsset owner prerequisites | register MaintainableAsset owner prerequisites | P2 | — | VERIFIED | COMPLETED |
| HPS-009 | Assets and Maintenance | [HAPI-263](ULTIMATE_TASKS.md#hapi-263) | HMR-069 | remediate semantic review MaintenanceWorkOrder | remediate semantic review MaintenanceWorkOrder | P2 | — | VERIFIED | COMPLETED |
| HPS-009 | Assets and Maintenance | [HAPI-364](ULTIMATE_TASKS.md#hapi-364) | HMSR-054 | review assets MaintainableAsset | review assets MaintainableAsset | P2 | — | VERIFIED | COMPLETED |
| HPS-009 | Assets and Maintenance | [HAPI-380](ULTIMATE_TASKS.md#hapi-380) | HMSR-070 | review assets AssetConditionRecord | review assets AssetConditionRecord | P2 | — | VERIFIED | COMPLETED |
| HPS-009 | Assets and Maintenance | [HAPI-391](ULTIMATE_TASKS.md#hapi-391) | HMSR-081 | review assets MaintenanceWorkOrder | review assets MaintenanceWorkOrder | P2 | — | VERIFIED | COMPLETED |
| HPS-010 | Telemetry and Monitoring | [HAPI-100](ULTIMATE_TASKS.md#hapi-100) | EXT-004 | add high velocity ingestion contracts | Add the minimum Telemetry input/output contracts for validated batch ingestion and append persistence; reuse existing reading/source/point concepts. | P3 | — | PARTIAL | DEFERRED |
| HPS-010 | Telemetry and Monitoring | [HAPI-101](ULTIMATE_TASKS.md#hapi-101) | EXT-005 | add timeseries storage migration | Add the actual next Flyway migration implementing the approved Timescale/PostgreSQL strategy. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-010 | Telemetry and Monitoring | [HAPI-102](ULTIMATE_TASKS.md#hapi-102) | EXT-006 | add jdbc high velocity persistence adapter | Implement approved JDBC batch persistence behind the Telemetry output port. | P3 | — | PARTIAL | DEFERRED |
| HPS-010 | Telemetry and Monitoring | [HAPI-103](ULTIMATE_TASKS.md#hapi-103) | EXT-007 | verify high velocity ingestion | Add integration/performance-oriented tests for batch writes, duplicates, quality/timestamps and schema compatibility. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-010 | Telemetry and Monitoring | [HAPI-160](ULTIMATE_TASKS.md#hapi-160) | HMC-015 | scan monitoring domain models | Scan all 2 monitoring model files. | P1 | — | VERIFIED | COMPLETED |
| HPS-010 | Telemetry and Monitoring | [HAPI-168](ULTIMATE_TASKS.md#hapi-168) | HMC-023 | scan telemetry domain models | Scan all 4 telemetry model files. | P1 | — | VERIFIED | COMPLETED |
| HPS-010 | Telemetry and Monitoring | [HAPI-178](ULTIMATE_TASKS.md#hapi-178) | HMR-005 | remediate semantic review TelemetryPoint | remediate semantic review TelemetryPoint | P1 | — | VERIFIED | COMPLETED |
| HPS-010 | Telemetry and Monitoring | [HAPI-179](ULTIMATE_TASKS.md#hapi-179) | HMR-005A | define point signal-unit compatibility metadata | define point signal-unit compatibility metadata | P1 | — | VERIFIED | COMPLETED |
| HPS-010 | Telemetry and Monitoring | [HAPI-212](ULTIMATE_TASKS.md#hapi-212) | HMR-030 | remediate semantic review TelemetrySource | remediate semantic review TelemetrySource | P1 | — | VERIFIED | COMPLETED |
| HPS-010 | Telemetry and Monitoring | [HAPI-216](ULTIMATE_TASKS.md#hapi-216) | HMR-033 | remediate semantic review TelemetryReading | remediate semantic review TelemetryReading | P1 | — | VERIFIED | COMPLETED |
| HPS-010 | Telemetry and Monitoring | [HAPI-226](ULTIMATE_TASKS.md#hapi-226) | HMR-040 | remediate semantic review MonitoringRule | remediate semantic review MonitoringRule | P1 | — | VERIFIED | COMPLETED |
| HPS-010 | Telemetry and Monitoring | [HAPI-227](ULTIMATE_TASKS.md#hapi-227) | HMR-040A | register MonitoringRule telemetry owner prerequisite | register MonitoringRule telemetry owner prerequisite | P1 | — | VERIFIED | COMPLETED |
| HPS-010 | Telemetry and Monitoring | [HAPI-247](ULTIMATE_TASKS.md#hapi-247) | HMR-053 | remediate semantic review TrustedTelemetryReading | remediate semantic review TrustedTelemetryReading | P1 | — | VERIFIED | COMPLETED |
| HPS-010 | Telemetry and Monitoring | [HAPI-297](ULTIMATE_TASKS.md#hapi-297) | HMR-103 | remediate semantic review PlanActualDeviation | remediate semantic review PlanActualDeviation | P1 | — | VERIFIED | COMPLETED |
| HPS-010 | Telemetry and Monitoring | [HAPI-315](ULTIMATE_TASKS.md#hapi-315) | HMSR-005 | review telemetry TelemetryPoint | review telemetry TelemetryPoint | P1 | — | VERIFIED | COMPLETED |
| HPS-010 | Telemetry and Monitoring | [HAPI-343](ULTIMATE_TASKS.md#hapi-343) | HMSR-033 | review telemetry TelemetrySource | review telemetry TelemetrySource | P1 | — | VERIFIED | COMPLETED |
| HPS-010 | Telemetry and Monitoring | [HAPI-347](ULTIMATE_TASKS.md#hapi-347) | HMSR-037 | review telemetry TelemetryReading | review telemetry TelemetryReading | P1 | — | VERIFIED | COMPLETED |
| HPS-010 | Telemetry and Monitoring | [HAPI-358](ULTIMATE_TASKS.md#hapi-358) | HMSR-048 | review monitoring MonitoringRule | review monitoring MonitoringRule | P1 | — | VERIFIED | COMPLETED |
| HPS-010 | Telemetry and Monitoring | [HAPI-372](ULTIMATE_TASKS.md#hapi-372) | HMSR-062 | review telemetry TrustedTelemetryReading | review telemetry TrustedTelemetryReading | P1 | — | VERIFIED | COMPLETED |
| HPS-010 | Telemetry and Monitoring | [HAPI-430](ULTIMATE_TASKS.md#hapi-430) | HMSR-120 | review monitoring PlanActualDeviation | review monitoring PlanActualDeviation | P1 | — | VERIFIED | COMPLETED |
| HPS-010 | Telemetry and Monitoring | [HAPI-494](ULTIMATE_TASKS.md#hapi-494) | HPR-P3-001 | establish capacity baseline | Define approved telemetry throughput, burst, retention, latency, backlog and replay requirements and benchmark current PostgreSQL/JPA. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-010 | Telemetry and Monitoring | [HAPI-495](ULTIMATE_TASKS.md#hapi-495) | HPR-P3-002 | introduce timescaledb persistence | Adopt TimescaleDB only if HPR-P3-001 evidence justifies it; provision extension and convert only approved high-volume tables to hypertables. | P3 | HAPI-494 | NOT_VERIFIABLE | DEFERRED |
| HPS-010 | Telemetry and Monitoring | [HAPI-496](ULTIMATE_TASKS.md#hapi-496) | HPR-P3-003 | add timescale lifecycle policies | Add approved compression, retention and continuous aggregates only from verified workload/retention requirements. | P3 | HAPI-495 | NOT_VERIFIABLE | DEFERRED |
| HPS-010 | Telemetry and Monitoring | [HAPI-497](ULTIMATE_TASKS.md#hapi-497) | HPR-P3-004 | add high throughput ingestion adapter | Replace per-entity JPA ingestion only where benchmarks prove it inadequate; retain new adapter behind Telemetry ports and verify burst/replay/failure behavior. | P3 | HAPI-494 | NOT_VERIFIABLE | DEFERRED |
| HPS-010 | Telemetry and Monitoring | [HAPI-510](ULTIMATE_TASKS.md#hapi-510) | HPR-P25-004 | synchronize trusted network measurements | Implement the approved read-only acquisition adapter and coherent timestamp/unit/asset binding; verify replay, duplicates, late/missing readings, disconnect and… | P3 | HAPI-503, HAPI-504 | NOT_VERIFIABLE | DEFERRED |
| HPS-010 | Telemetry and Monitoring | [HAPI-512](ULTIMATE_TASKS.md#hapi-512) | HPR-P25-006 | detect network issues from trusted state | Implement the approved issue catalogue and correlated detection across the connected network; distinguish sensor/data faults, suspected process faults and… | P3 | HAPI-511 | NOT_VERIFIABLE | DEFERRED |
| HPS-010 | Telemetry and Monitoring | [HAPI-610](ULTIMATE_TASKS.md#hapi-610) | HPR-P25-056 | Integrated closure: five telemetry-trust/quality/replay packages | Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-056; child tasks own implementation. five telemetry-trust/quality/replay… | P1 | HAPI-615 | PARTIAL | BLOCKED |
| HPS-010 | Telemetry and Monitoring | [HAPI-611](ULTIMATE_TASKS.md#hapi-611) | HPR-P25-056A | Configured telemetry source and effective bindings | Configured source identity and versioned effective point/tag/asset/unit bindings. | P1 | HAPI-567, HAPI-588 | PARTIAL | BLOCKED |
| HPS-010 | Telemetry and Monitoring | [HAPI-612](ULTIMATE_TASKS.md#hapi-612) | HPR-P25-056B | Authorized ingestion and immutable raw provenance | Authorized ingest with immutable raw provenance and idempotency/conflict semantics. | P1 | HAPI-611 | PARTIAL | BLOCKED |
| HPS-010 | Telemetry and Monitoring | [HAPI-613](ULTIMATE_TASKS.md#hapi-613) | HPR-P25-056C | Configured quality, freshness and ordering assessment | Configured quality, freshness, clock-skew and late/out-of-order assessment. | P1 | HAPI-612 | PARTIAL | BLOCKED |
| HPS-010 | Telemetry and Monitoring | [HAPI-614](ULTIMATE_TASKS.md#hapi-614) | HPR-P25-056D | Deterministic telemetry history, replay and recovery | Deterministic history/replay and source outage/recovery/dead-letter behavior. | P1 | HAPI-613 | PARTIAL | BLOCKED |
| HPS-010 | Telemetry and Monitoring | [HAPI-615](ULTIMATE_TASKS.md#hapi-615) | HPR-P25-056E | Telemetry API/security/monitoring qualification | Actual API/DB/monitoring export security and consumer qualification. | P1 | HAPI-614 | PARTIAL | BLOCKED |
| HPS-010 | Telemetry and Monitoring | [HAPI-839](ULTIMATE_TASKS.md#hapi-839) | TEL-001 | add telemetry implementation roadmap | Create and maintain this roadmap. | P1 | — | VERIFIED | COMPLETED |
| HPS-010 | Telemetry and Monitoring | [HAPI-840](ULTIMATE_TASKS.md#hapi-840) | TEL-002 | add telemetry package skeleton | Add package skeleton only. | P1 | — | IMPLEMENTED | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-841](ULTIMATE_TASKS.md#hapi-841) | TEL-003 | add telemetry domain value objects | Add ids, codes, timestamps, value objects, topology references, catalog references, technical statuses. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-842](ULTIMATE_TASKS.md#hapi-842) | TEL-004 | add telemetry catalog domain models | Add TelemetryTypeCatalog and TelemetryTypeTranslation. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-843](ULTIMATE_TASKS.md#hapi-843) | TEL-005 | add telemetry domain models | Add source, device, point, binding, reading, batch models. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-844](ULTIMATE_TASKS.md#hapi-844) | TEL-006 | add telemetry domain policies | Add lifecycle, binding, quality, ingestion, catalog policies. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-845](ULTIMATE_TASKS.md#hapi-845) | TEL-007 | add telemetry domain services | Add registration, binding, ingestion, catalog resolution services. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-846](ULTIMATE_TASKS.md#hapi-846) | TEL-008 | add telemetry application commands and queries | Add create/list/get/ingest command-query contracts. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-847](ULTIMATE_TASKS.md#hapi-847) | TEL-009 | add telemetry application DTOs | Add localized DTOs and reading DTOs. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-848](ULTIMATE_TASKS.md#hapi-848) | TEL-010 | add telemetry application ports | Add inbound use cases and outbound repository ports. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-849](ULTIMATE_TASKS.md#hapi-849) | TEL-011 | add telemetry application services | Add source/device/point/binding/reading/catalog services. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-850](ULTIMATE_TASKS.md#hapi-850) | TEL-012 | add telemetry catalog tables | Add V022 catalog/translation tables and seeds. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-851](ULTIMATE_TASKS.md#hapi-851) | TEL-013 | add telemetry metadata tables | Add V023 source/device/point/binding/batch tables. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-852](ULTIMATE_TASKS.md#hapi-852) | TEL-014 | add telemetry reading tables | Add V024 append-only reading table and indexes. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-853](ULTIMATE_TASKS.md#hapi-853) | TEL-015 | add telemetry persistence entities and repositories | Add JPA entities/repositories. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-854](ULTIMATE_TASKS.md#hapi-854) | TEL-016 | add telemetry persistence mapper and adapters | Add mappers/adapters implementing outbound ports. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-855](ULTIMATE_TASKS.md#hapi-855) | TEL-017 | add telemetry infrastructure configuration | Add Spring bean configuration. | P1 | — | IMPLEMENTED | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-856](ULTIMATE_TASKS.md#hapi-856) | TEL-018 | add telemetry REST request DTOs | Add request DTOs for sources/devices/points/bindings/readings/catalogs. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-857](ULTIMATE_TASKS.md#hapi-857) | TEL-019 | add telemetry REST response DTOs | Add localized response DTOs. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-858](ULTIMATE_TASKS.md#hapi-858) | TEL-020 | add telemetry REST mapper | Map REST to app contracts and localized responses. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-859](ULTIMATE_TASKS.md#hapi-859) | TEL-021 | add telemetry REST controllers | Add endpoint groups under /api/v1/telemetry/. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-860](ULTIMATE_TASKS.md#hapi-860) | TEL-022 | add telemetry OpenAPI coverage checklist | Ensure Swagger/OpenAPI coverage. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-861](ULTIMATE_TASKS.md#hapi-861) | TEL-023 | add telemetry domain tests | Test value objects, models, policies, services. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-862](ULTIMATE_TASKS.md#hapi-862) | TEL-024 | add telemetry application service tests | Test application services with fakes. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-863](ULTIMATE_TASKS.md#hapi-863) | TEL-025 | add telemetry persistence tests | Test mappers/adapters/repository contracts. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-864](ULTIMATE_TASKS.md#hapi-864) | TEL-026 | add telemetry REST mapper and controller tests | Test REST mapper/controllers with fake use cases. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-865](ULTIMATE_TASKS.md#hapi-865) | TEL-027 | add telemetry application boot smoke test | Validate full Spring context with telemetry. | P1 | — | IMPLEMENTED | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-866](ULTIMATE_TASKS.md#hapi-866) | TEL-028 | finalize telemetry validation checklist | Final checklist and handoff. | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-867](ULTIMATE_TASKS.md#hapi-867) | TEL-COR-001 | TEL-COR-001 historical bounded obligation | TEL-COR-001 historical bounded obligation | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-010 | Telemetry and Monitoring | [HAPI-868](ULTIMATE_TASKS.md#hapi-868) | TEL-COR-002 | TEL-COR-002 historical bounded obligation | TEL-COR-002 historical bounded obligation | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-010 | Telemetry and Monitoring | [HAPI-869](ULTIMATE_TASKS.md#hapi-869) | TEL-COR-003 | TEL-COR-003 historical bounded obligation | TEL-COR-003 historical bounded obligation | P1 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-010 | Telemetry and Monitoring | [HAPI-870](ULTIMATE_TASKS.md#hapi-870) | TEL-COR-004 | TEL-COR-004 historical bounded obligation | TEL-COR-004 historical bounded obligation | P1 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-011 | Alarms and Events | [HAPI-001](ULTIMATE_TASKS.md#hapi-001) | ALM-SUP-001 | approve suppression lifecycle semantics | This document only. Record the approved suppression policy and implementation sequence. | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-002](ULTIMATE_TASKS.md#hapi-002) | ALM-SUP-002 | add suppression application contracts | Add minimal Alarm application commands/queries/use-case ports and DTOs for create, release, expiry evaluation, and query behavior. Reuse existing… | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-003](ULTIMATE_TASKS.md#hapi-003) | ALM-SUP-003 | implement suppression lifecycle policy | Implement domain/application policy for ALARM overlay/restoration, broad-scope matching, acknowledgement/clear/close/escalation interaction, conflicts, and… | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-004](ULTIMATE_TASKS.md#hapi-004) | ALM-SUP-004 | expose suppression persistence queries | Extend existing suppression repository adapter only as required by approved use cases: exact-scope active lookup, expiry candidates, history/query support. | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-005](ULTIMATE_TASKS.md#hapi-005) | ALM-SUP-005 | integrate suppression workflow approval | Verify open-ended suppression through Workflow public contracts/ports and fail closed when approval is absent. | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-006](ULTIMATE_TASKS.md#hapi-006) | ALM-SUP-006 | add suppression expiry orchestration | Add idempotent backend-owned expiry orchestration using the existing Spring scheduling mechanism. | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-007](ULTIMATE_TASKS.md#hapi-007) | ALM-SUP-006A | add suppression expiry audit contract | Export a narrow Audit contract and provision canonical ALARM_SUPPRESSION_EXPIRED taxonomy for all suppression scopes. | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-008](ULTIMATE_TASKS.md#hapi-008) | ALM-SUP-007 | publish suppression REST contracts | Publish canonical create/release/query endpoints, generated OpenAPI schemas, route-permission metadata, deterministic 400/403/404/409 behavior. | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-009](ULTIMATE_TASKS.md#hapi-009) | ALM-SUP-008 | verify suppression lifecycle end to end | Domain/application/persistence/REST/Testcontainers coverage including ALARM and broad scopes, acknowledgement, clear/close, escalation preservation, workflow… | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-010](ULTIMATE_TASKS.md#hapi-010) | ALM-SUP-009 | finalize suppression capability | Record exact endpoints, permissions, events, validation evidence, remaining limits, and frontend-consumption boundary. | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-147](ULTIMATE_TASKS.md#hapi-147) | HMC-002 | scan alarm domain models | Scan all 4 alarm/domain/model files; record models, fields and Java types. | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-161](ULTIMATE_TASKS.md#hapi-161) | HMC-016 | scan notification domain models | Scan all 4 notification model files. | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-189](ULTIMATE_TASKS.md#hapi-189) | HMR-012 | remediate semantic review NotificationTemplate | remediate semantic review NotificationTemplate | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-219](ULTIMATE_TASKS.md#hapi-219) | HMR-035 | remediate semantic review NotificationRequest | remediate semantic review NotificationRequest | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-246](ULTIMATE_TASKS.md#hapi-246) | HMR-052 | remediate semantic review NotificationMessage | remediate semantic review NotificationMessage | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-254](ULTIMATE_TASKS.md#hapi-254) | HMR-060 | remediate semantic review NotificationDeliveryAttempt | remediate semantic review NotificationDeliveryAttempt | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-294](ULTIMATE_TASKS.md#hapi-294) | HMR-100 | remediate semantic review Alarm | remediate semantic review Alarm | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-298](ULTIMATE_TASKS.md#hapi-298) | HMR-104 | remediate semantic review AlarmAcknowledgement | remediate semantic review AlarmAcknowledgement | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-299](ULTIMATE_TASKS.md#hapi-299) | HMR-105 | remediate semantic review AlarmClosure | remediate semantic review AlarmClosure | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-300](ULTIMATE_TASKS.md#hapi-300) | HMR-106 | remediate semantic review AlarmShelving | remediate semantic review AlarmShelving | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-304](ULTIMATE_TASKS.md#hapi-304) | HMS-002 | inventory target model dependency evidence | Inventory candidate relationship evidence for all 123 models from domain source, JPA, Flyway, contracts and enums. Every candidate edge retains… | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-322](ULTIMATE_TASKS.md#hapi-322) | HMSR-012 | review notification NotificationTemplate | review notification NotificationTemplate | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-349](ULTIMATE_TASKS.md#hapi-349) | HMSR-039 | review notification NotificationRequest | review notification NotificationRequest | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-371](ULTIMATE_TASKS.md#hapi-371) | HMSR-061 | review notification NotificationMessage | review notification NotificationMessage | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-382](ULTIMATE_TASKS.md#hapi-382) | HMSR-072 | review notification NotificationDeliveryAttempt | review notification NotificationDeliveryAttempt | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-427](ULTIMATE_TASKS.md#hapi-427) | HMSR-117 | review alarm Alarm | review alarm Alarm | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-431](ULTIMATE_TASKS.md#hapi-431) | HMSR-121 | review alarm AlarmAcknowledgement | review alarm AlarmAcknowledgement | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-432](ULTIMATE_TASKS.md#hapi-432) | HMSR-122 | review alarm AlarmClosure | review alarm AlarmClosure | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-433](ULTIMATE_TASKS.md#hapi-433) | HMSR-123 | review alarm AlarmShelving | review alarm AlarmShelving | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-652](ULTIMATE_TASKS.md#hapi-652) | HRA-071 | simplify boundary dto mapping | Pilot compile-time generated mapping for the HRA-070-approved exact alarm pairs only; retain security-enriched alarm mappings as hand-written code. | P2 | — | VERIFIED | COMPLETED |
| HPS-011 | Alarms and Events | [HAPI-707](ULTIMATE_TASKS.md#hapi-707) | NOTIF-001 | NOTIF-001 historical bounded obligation | NOTIF-001 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-011 | Alarms and Events | [HAPI-708](ULTIMATE_TASKS.md#hapi-708) | NOTIF-002 | NOTIF-002 historical bounded obligation | NOTIF-002 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-011 | Alarms and Events | [HAPI-709](ULTIMATE_TASKS.md#hapi-709) | NOTIF-003 | NOTIF-003 historical bounded obligation | NOTIF-003 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-011 | Alarms and Events | [HAPI-710](ULTIMATE_TASKS.md#hapi-710) | NOTIF-004 | NOTIF-004 historical bounded obligation | NOTIF-004 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-011 | Alarms and Events | [HAPI-711](ULTIMATE_TASKS.md#hapi-711) | NOTIF-005 | NOTIF-005 historical bounded obligation | NOTIF-005 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-011 | Alarms and Events | [HAPI-712](ULTIMATE_TASKS.md#hapi-712) | NOTIF-006 | NOTIF-006 historical bounded obligation | NOTIF-006 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-011 | Alarms and Events | [HAPI-713](ULTIMATE_TASKS.md#hapi-713) | NOTIF-007 | NOTIF-007 historical bounded obligation | NOTIF-007 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-011 | Alarms and Events | [HAPI-714](ULTIMATE_TASKS.md#hapi-714) | NOTIF-008 | NOTIF-008 historical bounded obligation | NOTIF-008 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-011 | Alarms and Events | [HAPI-715](ULTIMATE_TASKS.md#hapi-715) | NOTIF-009 | NOTIF-009 historical bounded obligation | NOTIF-009 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-011 | Alarms and Events | [HAPI-716](ULTIMATE_TASKS.md#hapi-716) | NOTIF-010 | NOTIF-010 historical bounded obligation | NOTIF-010 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-011 | Alarms and Events | [HAPI-717](ULTIMATE_TASKS.md#hapi-717) | NOTIF-011 | NOTIF-011 historical bounded obligation | NOTIF-011 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-011 | Alarms and Events | [HAPI-718](ULTIMATE_TASKS.md#hapi-718) | NOTIF-012 | NOTIF-012 historical bounded obligation | NOTIF-012 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-011 | Alarms and Events | [HAPI-719](ULTIMATE_TASKS.md#hapi-719) | NOTIF-013 | NOTIF-013 historical bounded obligation | NOTIF-013 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-011 | Alarms and Events | [HAPI-720](ULTIMATE_TASKS.md#hapi-720) | NOTIF-014 | NOTIF-014 historical bounded obligation | NOTIF-014 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-011 | Alarms and Events | [HAPI-721](ULTIMATE_TASKS.md#hapi-721) | NOTIF-015 | NOTIF-015 historical bounded obligation | NOTIF-015 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-011 | Alarms and Events | [HAPI-722](ULTIMATE_TASKS.md#hapi-722) | NOTIF-016 | NOTIF-016 historical bounded obligation | NOTIF-016 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-085](ULTIMATE_TASKS.md#hapi-085) | ENV-001 | ENV-001 historical bounded obligation | ENV-001 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-086](ULTIMATE_TASKS.md#hapi-086) | ENV-002 | ENV-002 historical bounded obligation | ENV-002 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-087](ULTIMATE_TASKS.md#hapi-087) | ENV-003 | ENV-003 historical bounded obligation | ENV-003 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-088](ULTIMATE_TASKS.md#hapi-088) | ENV-004 | ENV-004 historical bounded obligation | ENV-004 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-089](ULTIMATE_TASKS.md#hapi-089) | ENV-005 | ENV-005 historical bounded obligation | ENV-005 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-090](ULTIMATE_TASKS.md#hapi-090) | ENV-006 | ENV-006 historical bounded obligation | ENV-006 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-091](ULTIMATE_TASKS.md#hapi-091) | ENV-007 | ENV-007 historical bounded obligation | ENV-007 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-092](ULTIMATE_TASKS.md#hapi-092) | ENV-008 | ENV-008 historical bounded obligation | ENV-008 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-093](ULTIMATE_TASKS.md#hapi-093) | ENV-009 | ENV-009 historical bounded obligation | ENV-009 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-094](ULTIMATE_TASKS.md#hapi-094) | ENV-010 | ENV-010 historical bounded obligation | ENV-010 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-095](ULTIMATE_TASKS.md#hapi-095) | ENV-011 | ENV-011 historical bounded obligation | ENV-011 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-096](ULTIMATE_TASKS.md#hapi-096) | ENV-012 | ENV-012 historical bounded obligation | ENV-012 historical bounded obligation | P3 | — | NOT_IMPLEMENTED | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-107](ULTIMATE_TASKS.md#hapi-107) | EXT-011 | define cpm service contract | Define LeakDetectionAPI responsibility, model/config input, telemetry input, anomaly output, availability and idempotency semantics. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-108](ULTIMATE_TASKS.md#hapi-108) | EXT-012 | add cpm protobuf contract | Add the approved versioned Protobuf contract in the authorized contract location. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-109](ULTIMATE_TASKS.md#hapi-109) | EXT-013 | add cpm grpc client port | Add Hidra application output port and mapping contracts for anomaly stream consumption. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-110](ULTIMATE_TASKS.md#hapi-110) | EXT-014 | add cpm grpc client adapter | Add gRPC client configuration/adapter, stream resilience and mapping to existing CreateLeakCandidateUseCase. | P3 | — | PARTIAL | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-111](ULTIMATE_TASKS.md#hapi-111) | EXT-015 | verify cpm anomaly integration | Contract/integration tests for anomaly mapping and idempotent candidate creation. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-118](ULTIMATE_TASKS.md#hapi-118) | EXT-022 | specify modified b31g calculation | Record approved engineering equation set, standard/edition, inputs, units, limits, ERF/MAOP semantics, rounding and reference cases. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-119](ULTIMATE_TASKS.md#hapi-119) | EXT-023 | add modified b31g assessment | Implement the calculator from EXT-022 and map outputs into the existing assessment model or an explicitly approved extension. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-120](ULTIMATE_TASKS.md#hapi-120) | EXT-024 | verify ili b31g workflow | End-to-end tests from approved ILI evidence through spatial/KP resolution and assessment persistence. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-121](ULTIMATE_TASKS.md#hapi-121) | EXT-025 | enforce industrial extension boundaries | Add architecture guards for Integration→Telemetry handoff, gRPC isolation and Topology/Integrity ownership. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-122](ULTIMATE_TASKS.md#hapi-122) | EXT-026 | add industrial extension runbooks | Document MQTT, Timescale/PostGIS prerequisites, gRPC operations, secrets, health and rollback. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-123](ULTIMATE_TASKS.md#hapi-123) | EXT-027 | close extended industrial capability milestone | Reconcile all EXT tasks, CI evidence, compatibility and unresolved risks. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-154](ULTIMATE_TASKS.md#hapi-154) | HMC-009 | scan hse domain models | Scan all 4 hse model files. | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-156](ULTIMATE_TASKS.md#hapi-156) | HMC-011 | scan incident domain models | Scan all 4 incident model files. | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-158](ULTIMATE_TASKS.md#hapi-158) | HMC-013 | scan integrity domain models | Scan all 4 integrity model files. | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-159](ULTIMATE_TASKS.md#hapi-159) | HMC-014 | scan leak detection domain models | Scan all 3 leakdetection model files. | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-192](ULTIMATE_TASKS.md#hapi-192) | HMR-015 | remediate semantic review LeakCandidate | remediate semantic review LeakCandidate | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-198](ULTIMATE_TASKS.md#hapi-198) | HMR-019 | remediate semantic review PipelineDefect | remediate semantic review PipelineDefect | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-244](ULTIMATE_TASKS.md#hapi-244) | HMR-050 | remediate semantic review IntegrityProgram | remediate semantic review IntegrityProgram | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-245](ULTIMATE_TASKS.md#hapi-245) | HMR-051 | remediate semantic review LeakDetectionCase | remediate semantic review LeakDetectionCase | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-253](ULTIMATE_TASKS.md#hapi-253) | HMR-059 | remediate semantic review LeakEscalationReference | remediate semantic review LeakEscalationReference | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-256](ULTIMATE_TASKS.md#hapi-256) | HMR-062 | remediate semantic review Incident | remediate semantic review Incident | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-266](ULTIMATE_TASKS.md#hapi-266) | HMR-072 | remediate semantic review IntegrityAssessment | remediate semantic review IntegrityAssessment | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-276](ULTIMATE_TASKS.md#hapi-276) | HMR-082 | remediate semantic review HseCase | remediate semantic review HseCase | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-284](ULTIMATE_TASKS.md#hapi-284) | HMR-090 | remediate semantic review IncidentClosure | remediate semantic review IncidentClosure | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-285](ULTIMATE_TASKS.md#hapi-285) | HMR-091 | remediate semantic review IncidentRelatedIncident | remediate semantic review IncidentRelatedIncident | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-286](ULTIMATE_TASKS.md#hapi-286) | HMR-092 | remediate semantic review IncidentResponseAction | remediate semantic review IncidentResponseAction | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-290](ULTIMATE_TASKS.md#hapi-290) | HMR-096 | remediate semantic review HseClosure | remediate semantic review HseClosure | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-291](ULTIMATE_TASKS.md#hapi-291) | HMR-097 | remediate semantic review HseCorrectivePreventiveAction | remediate semantic review HseCorrectivePreventiveAction | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-292](ULTIMATE_TASKS.md#hapi-292) | HMR-098 | remediate semantic review IntegrityCase | remediate semantic review IntegrityCase | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-325](ULTIMATE_TASKS.md#hapi-325) | HMSR-015 | review leakdetection LeakCandidate | review leakdetection LeakCandidate | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-331](ULTIMATE_TASKS.md#hapi-331) | HMSR-021 | review integrity PipelineDefect | review integrity PipelineDefect | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-369](ULTIMATE_TASKS.md#hapi-369) | HMSR-059 | review integrity IntegrityProgram | review integrity IntegrityProgram | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-370](ULTIMATE_TASKS.md#hapi-370) | HMSR-060 | review leakdetection LeakDetectionCase | review leakdetection LeakDetectionCase | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-381](ULTIMATE_TASKS.md#hapi-381) | HMSR-071 | review leakdetection LeakEscalationReference | review leakdetection LeakEscalationReference | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-384](ULTIMATE_TASKS.md#hapi-384) | HMSR-074 | review incident Incident | review incident Incident | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-393](ULTIMATE_TASKS.md#hapi-393) | HMSR-083 | review hse PermitToWork | review hse PermitToWork | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-395](ULTIMATE_TASKS.md#hapi-395) | HMSR-085 | review integrity IntegrityAssessment | review integrity IntegrityAssessment | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-406](ULTIMATE_TASKS.md#hapi-406) | HMSR-096 | review hse HseCase | review hse HseCase | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-417](ULTIMATE_TASKS.md#hapi-417) | HMSR-107 | review incident IncidentClosure | review incident IncidentClosure | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-418](ULTIMATE_TASKS.md#hapi-418) | HMSR-108 | review incident IncidentRelatedIncident | review incident IncidentRelatedIncident | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-419](ULTIMATE_TASKS.md#hapi-419) | HMSR-109 | review incident IncidentResponseAction | review incident IncidentResponseAction | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-423](ULTIMATE_TASKS.md#hapi-423) | HMSR-113 | review hse HseClosure | review hse HseClosure | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-424](ULTIMATE_TASKS.md#hapi-424) | HMSR-114 | review hse HseCorrectivePreventiveAction | review hse HseCorrectivePreventiveAction | P2 | — | VERIFIED | COMPLETED |
| HPS-012 | Incidents, HSE and Integrity | [HAPI-425](ULTIMATE_TASKS.md#hapi-425) | HMSR-115 | review integrity IntegrityCase | review integrity IntegrityCase | P2 | — | VERIFIED | COMPLETED |
| HPS-013 | Planning and Nominations | [HAPI-164](ULTIMATE_TASKS.md#hapi-164) | HMC-019 | scan planning domain models | Scan all 5 planning model files. | P2 | — | VERIFIED | COMPLETED |
| HPS-013 | Planning and Nominations | [HAPI-180](ULTIMATE_TASKS.md#hapi-180) | HMR-006 | remediate semantic review PlanningPeriod | remediate semantic review PlanningPeriod | P2 | — | VERIFIED | COMPLETED |
| HPS-013 | Planning and Nominations | [HAPI-258](ULTIMATE_TASKS.md#hapi-258) | HMR-064 | remediate semantic review PlanRevision | remediate semantic review PlanRevision | P2 | — | VERIFIED | COMPLETED |
| HPS-013 | Planning and Nominations | [HAPI-259](ULTIMATE_TASKS.md#hapi-259) | HMR-065 | remediate semantic review OperationalPlan | remediate semantic review OperationalPlan | P2 | — | VERIFIED | COMPLETED |
| HPS-013 | Planning and Nominations | [HAPI-274](ULTIMATE_TASKS.md#hapi-274) | HMR-080 | remediate semantic review Nomination | remediate semantic review Nomination | P2 | — | VERIFIED | COMPLETED |
| HPS-013 | Planning and Nominations | [HAPI-288](ULTIMATE_TASKS.md#hapi-288) | HMR-094 | remediate semantic review PlanTarget | remediate semantic review PlanTarget | P2 | — | VERIFIED | COMPLETED |
| HPS-013 | Planning and Nominations | [HAPI-316](ULTIMATE_TASKS.md#hapi-316) | HMSR-006 | review planning PlanningPeriod | review planning PlanningPeriod | P2 | — | VERIFIED | COMPLETED |
| HPS-013 | Planning and Nominations | [HAPI-386](ULTIMATE_TASKS.md#hapi-386) | HMSR-076 | review planning PlanRevision | review planning PlanRevision | P2 | — | VERIFIED | COMPLETED |
| HPS-013 | Planning and Nominations | [HAPI-387](ULTIMATE_TASKS.md#hapi-387) | HMSR-077 | review planning OperationalPlan | review planning OperationalPlan | P2 | — | VERIFIED | COMPLETED |
| HPS-013 | Planning and Nominations | [HAPI-404](ULTIMATE_TASKS.md#hapi-404) | HMSR-094 | review planning Nomination | review planning Nomination | P2 | — | VERIFIED | COMPLETED |
| HPS-013 | Planning and Nominations | [HAPI-421](ULTIMATE_TASKS.md#hapi-421) | HMSR-111 | review planning PlanTarget | review planning PlanTarget | P2 | — | VERIFIED | COMPLETED |
| HPS-013 | Planning and Nominations | [HAPI-659](ULTIMATE_TASKS.md#hapi-659) | HRA-100 | return application revision result | Return an application result DTO from plan-revision updates rather than a domain PlanRevision to REST. | P2 | — | VERIFIED | COMPLETED |
| HPS-013 | Planning and Nominations | [HAPI-796](ULTIMATE_TASKS.md#hapi-796) | PLN-001 | expose HWEB-010 query contracts | Read-only list/detail contracts for periods, operational plans, revisions, nominations and plan targets; stable pagination; deterministic 400/404; route-permission… | P2 | — | VERIFIED | COMPLETED |
| HPS-013 | Planning and Nominations | [HAPI-797](ULTIMATE_TASKS.md#hapi-797) | PLN-002 | publish authoritative workflow approval integration | Revision-scoped approval status/actions and backend-owned transition execution using workflow public contracts; planning applies the resulting lifecycle effect;… | P2 | — | VERIFIED | COMPLETED |
| HPS-013 | Planning and Nominations | [HAPI-798](ULTIMATE_TASKS.md#hapi-798) | PLN-003 | expose plan-target-scoped deviations | Extend the existing monitoring deviation collection with an optional exact planTargetId filter so HWEB-010-05 can retrieve authoritative comparison rows for one… | P2 | — | VERIFIED | COMPLETED |
| HPS-013 | Planning and Nominations | [HAPI-799](ULTIMATE_TASKS.md#hapi-799) | PLN-004 | publish revision concurrency contract | Publish an explicit concurrency-protected update for the current editable plan revision's change-reason metadata. expectedUpdatedAt is the authoritative client… | P2 | — | VERIFIED | COMPLETED |
| HPS-014 | Custody and Measurement | [HAPI-152](ULTIMATE_TASKS.md#hapi-152) | HMC-007 | scan custody domain models | Scan all 3 custody model files. | P2 | — | VERIFIED | COMPLETED |
| HPS-014 | Custody and Measurement | [HAPI-197](ULTIMATE_TASKS.md#hapi-197) | HMR-018 | remediate semantic review CustodyMeasurementPeriod | remediate semantic review CustodyMeasurementPeriod | P2 | — | VERIFIED | COMPLETED |
| HPS-014 | Custody and Measurement | [HAPI-208](ULTIMATE_TASKS.md#hapi-208) | HMR-027 | remediate semantic review CustodyDiscrepancy | remediate semantic review CustodyDiscrepancy | P2 | — | VERIFIED | COMPLETED |
| HPS-014 | Custody and Measurement | [HAPI-264](ULTIMATE_TASKS.md#hapi-264) | HMR-070 | remediate semantic review CustodyTransferTicket | remediate semantic review CustodyTransferTicket | P2 | — | VERIFIED | COMPLETED |
| HPS-014 | Custody and Measurement | [HAPI-330](ULTIMATE_TASKS.md#hapi-330) | HMSR-020 | review custody CustodyMeasurementPeriod | review custody CustodyMeasurementPeriod | P2 | — | VERIFIED | COMPLETED |
| HPS-014 | Custody and Measurement | [HAPI-339](ULTIMATE_TASKS.md#hapi-339) | HMSR-029 | review custody CustodyDiscrepancy | review custody CustodyDiscrepancy | P2 | — | VERIFIED | COMPLETED |
| HPS-014 | Custody and Measurement | [HAPI-392](ULTIMATE_TASKS.md#hapi-392) | HMSR-082 | review custody CustodyTransferTicket | review custody CustodyTransferTicket | P2 | — | VERIFIED | COMPLETED |
| HPS-015 | Workflow and Approvals | [HAPI-170](ULTIMATE_TASKS.md#hapi-170) | HMC-025 | scan workflow domain models | Scan all 8 workflow model files. | P2 | — | VERIFIED | COMPLETED |
| HPS-015 | Workflow and Approvals | [HAPI-175](ULTIMATE_TASKS.md#hapi-175) | HMR-002 | prepare executable remediation register | Re-read all 104 REVISE sections against live main; populate exact per-task file allowlists, validations, prerequisites, already-fixed evidence, SCC ordering, and… | P2 | — | VERIFIED | COMPLETED |
| HPS-015 | Workflow and Approvals | [HAPI-176](ULTIMATE_TASKS.md#hapi-176) | HMR-003 | remediate semantic review WorkflowDefinition | remediate semantic review WorkflowDefinition | P2 | — | VERIFIED | COMPLETED |
| HPS-015 | Workflow and Approvals | [HAPI-231](ULTIMATE_TASKS.md#hapi-231) | HMR-043 | remediate semantic review WorkflowStep | remediate semantic review WorkflowStep | P2 | — | VERIFIED | COMPLETED |
| HPS-015 | Workflow and Approvals | [HAPI-232](ULTIMATE_TASKS.md#hapi-232) | HMR-044 | remediate semantic review WorkflowStepAssignmentRule | remediate semantic review WorkflowStepAssignmentRule | P2 | — | VERIFIED | COMPLETED |
| HPS-015 | Workflow and Approvals | [HAPI-249](ULTIMATE_TASKS.md#hapi-249) | HMR-055 | remediate semantic review WorkflowInstance | remediate semantic review WorkflowInstance | P2 | — | VERIFIED | COMPLETED |
| HPS-015 | Workflow and Approvals | [HAPI-255](ULTIMATE_TASKS.md#hapi-255) | HMR-061 | remediate semantic review WorkflowTransition | remediate semantic review WorkflowTransition | P2 | — | VERIFIED | COMPLETED |
| HPS-015 | Workflow and Approvals | [HAPI-260](ULTIMATE_TASKS.md#hapi-260) | HMR-066 | remediate semantic review WorkflowTask | remediate semantic review WorkflowTask | P2 | — | VERIFIED | COMPLETED |
| HPS-015 | Workflow and Approvals | [HAPI-275](ULTIMATE_TASKS.md#hapi-275) | HMR-081 | remediate semantic review WorkflowAction | remediate semantic review WorkflowAction | P2 | — | VERIFIED | COMPLETED |
| HPS-015 | Workflow and Approvals | [HAPI-293](ULTIMATE_TASKS.md#hapi-293) | HMR-099 | remediate semantic review WorkflowStateHistory | remediate semantic review WorkflowStateHistory | P2 | — | VERIFIED | COMPLETED |
| HPS-015 | Workflow and Approvals | [HAPI-312](ULTIMATE_TASKS.md#hapi-312) | HMSR-002 | review workflow WorkflowDefinition | review workflow WorkflowDefinition | P2 | — | VERIFIED | COMPLETED |
| HPS-015 | Workflow and Approvals | [HAPI-361](ULTIMATE_TASKS.md#hapi-361) | HMSR-051 | review workflow WorkflowStep | review workflow WorkflowStep | P2 | — | VERIFIED | COMPLETED |
| HPS-015 | Workflow and Approvals | [HAPI-362](ULTIMATE_TASKS.md#hapi-362) | HMSR-052 | review workflow WorkflowStepAssignmentRule | review workflow WorkflowStepAssignmentRule | P2 | — | VERIFIED | COMPLETED |
| HPS-015 | Workflow and Approvals | [HAPI-374](ULTIMATE_TASKS.md#hapi-374) | HMSR-064 | review workflow WorkflowInstance | review workflow WorkflowInstance | P2 | — | VERIFIED | COMPLETED |
| HPS-015 | Workflow and Approvals | [HAPI-383](ULTIMATE_TASKS.md#hapi-383) | HMSR-073 | review workflow WorkflowTransition | review workflow WorkflowTransition | P2 | — | VERIFIED | COMPLETED |
| HPS-015 | Workflow and Approvals | [HAPI-388](ULTIMATE_TASKS.md#hapi-388) | HMSR-078 | review workflow WorkflowTask | review workflow WorkflowTask | P2 | — | VERIFIED | COMPLETED |
| HPS-015 | Workflow and Approvals | [HAPI-405](ULTIMATE_TASKS.md#hapi-405) | HMSR-095 | review workflow WorkflowAction | review workflow WorkflowAction | P2 | — | VERIFIED | COMPLETED |
| HPS-015 | Workflow and Approvals | [HAPI-426](ULTIMATE_TASKS.md#hapi-426) | HMSR-116 | review workflow WorkflowStateHistory | review workflow WorkflowStateHistory | P2 | — | VERIFIED | COMPLETED |
| HPS-015 | Workflow and Approvals | [HAPI-524](ULTIMATE_TASKS.md#hapi-524) | HPR-P25-012 | record operator decisions and applied actions | Record authenticated human approval, rejection/modification and the action actually applied with time/scope/context; preserve advisory boundaries and immutable… | P3 | HAPI-523 | PARTIAL | DEFERRED |
| HPS-015 | Workflow and Approvals | [HAPI-657](ULTIMATE_TASKS.md#hapi-657) | HRA-090 | expose planning workflow contract | Export Workflow contracts consumed by Planning; stop Planning importing Workflow's internal application package. | P2 | — | VERIFIED | COMPLETED |
| HPS-015 | Workflow and Approvals | [HAPI-896](ULTIMATE_TASKS.md#hapi-896) | WF-001 | docs(workflow): prepare workflow module roadmap | docs(workflow): prepare workflow module roadmap | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-897](ULTIMATE_TASKS.md#hapi-897) | WF-002 | chore(workflow): add workflow package skeleton | chore(workflow): add workflow package skeleton | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-898](ULTIMATE_TASKS.md#hapi-898) | WF-003 | feat(workflow): add workflow domain value objects | feat(workflow): add workflow domain value objects | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-899](ULTIMATE_TASKS.md#hapi-899) | WF-004 | feat(workflow): add workflow catalog domain models | feat(workflow): add workflow catalog domain models | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-900](ULTIMATE_TASKS.md#hapi-900) | WF-005 | feat(workflow): add workflow core domain models | feat(workflow): add workflow core domain models | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-901](ULTIMATE_TASKS.md#hapi-901) | WF-006 | feat(workflow): add workflow domain policies | feat(workflow): add workflow domain policies | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-902](ULTIMATE_TASKS.md#hapi-902) | WF-007 | feat(workflow): add workflow domain services | feat(workflow): add workflow domain services | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-903](ULTIMATE_TASKS.md#hapi-903) | WF-008 | feat(workflow): add workflow application commands and queries | feat(workflow): add workflow application commands and queries | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-904](ULTIMATE_TASKS.md#hapi-904) | WF-009 | feat(workflow): add workflow application DTOs | feat(workflow): add workflow application DTOs | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-905](ULTIMATE_TASKS.md#hapi-905) | WF-010 | feat(workflow): add workflow application ports | feat(workflow): add workflow application ports | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-906](ULTIMATE_TASKS.md#hapi-906) | WF-011 | feat(workflow): add workflow application services | feat(workflow): add workflow application services | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-907](ULTIMATE_TASKS.md#hapi-907) | WF-012 | feat(workflow): add workflow infrastructure configuration | feat(workflow): add workflow infrastructure configuration | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-908](ULTIMATE_TASKS.md#hapi-908) | WF-013 | db(workflow): add workflow catalog and core tables migration | db(workflow): add workflow catalog and core tables migration | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-909](ULTIMATE_TASKS.md#hapi-909) | WF-014 | feat(workflow): add workflow persistence entities and repositories | feat(workflow): add workflow persistence entities and repositories | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-910](ULTIMATE_TASKS.md#hapi-910) | WF-015 | feat(workflow): add workflow persistence mappers and adapters | feat(workflow): add workflow persistence mappers and adapters | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-911](ULTIMATE_TASKS.md#hapi-911) | WF-016 | feat(workflow): add workflow telemetry target lookup adapter | feat(workflow): add workflow telemetry target lookup adapter | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-912](ULTIMATE_TASKS.md#hapi-912) | WF-017 | feat(workflow): add workflow REST request DTOs | feat(workflow): add workflow REST request DTOs | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-913](ULTIMATE_TASKS.md#hapi-913) | WF-018 | feat(workflow): add workflow REST response DTOs | feat(workflow): add workflow REST response DTOs | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-914](ULTIMATE_TASKS.md#hapi-914) | WF-019 | feat(workflow): add workflow REST mapper | feat(workflow): add workflow REST mapper | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-915](ULTIMATE_TASKS.md#hapi-915) | WF-020 | feat(workflow): add workflow REST controllers | feat(workflow): add workflow REST controllers | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-916](ULTIMATE_TASKS.md#hapi-916) | WF-021 | test(workflow): add workflow domain tests | test(workflow): add workflow domain tests | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-917](ULTIMATE_TASKS.md#hapi-917) | WF-022 | test(workflow): add workflow application service tests | test(workflow): add workflow application service tests | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-918](ULTIMATE_TASKS.md#hapi-918) | WF-023 | test(workflow): add workflow persistence tests | test(workflow): add workflow persistence tests | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-919](ULTIMATE_TASKS.md#hapi-919) | WF-024 | test(workflow): add workflow REST mapper and controller tests | test(workflow): add workflow REST mapper and controller tests | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-920](ULTIMATE_TASKS.md#hapi-920) | WF-025 | test(workflow): add workflow application boot smoke test | test(workflow): add workflow application boot smoke test | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-015 | Workflow and Approvals | [HAPI-921](ULTIMATE_TASKS.md#hapi-921) | WF-026 | docs(workflow): finalize workflow validation checklist | docs(workflow): finalize workflow validation checklist | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-166](ULTIMATE_TASKS.md#hapi-166) | HMC-021 | scan risk domain models | Scan all 4 risk model files. | P2 | — | VERIFIED | COMPLETED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-211](ULTIMATE_TASKS.md#hapi-211) | HMR-029 | remediate semantic review RiskMatrixCell | remediate semantic review RiskMatrixCell | P2 | — | VERIFIED | COMPLETED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-241](ULTIMATE_TASKS.md#hapi-241) | HMR-049 | remediate semantic review RiskRegister | remediate semantic review RiskRegister | P2 | — | VERIFIED | COMPLETED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-242](ULTIMATE_TASKS.md#hapi-242) | HMR-049A | register RiskRegister owner prerequisites | register RiskRegister owner prerequisites | P2 | — | VERIFIED | COMPLETED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-252](ULTIMATE_TASKS.md#hapi-252) | HMR-058 | remediate semantic review RiskAssessment | remediate semantic review RiskAssessment | P2 | — | VERIFIED | COMPLETED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-271](ULTIMATE_TASKS.md#hapi-271) | HMR-077 | remediate semantic review RiskEvidenceLink | remediate semantic review RiskEvidenceLink | P2 | — | VERIFIED | COMPLETED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-342](ULTIMATE_TASKS.md#hapi-342) | HMSR-032 | review risk RiskMatrixCell | review risk RiskMatrixCell | P2 | — | VERIFIED | COMPLETED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-368](ULTIMATE_TASKS.md#hapi-368) | HMSR-058 | review risk RiskRegister | review risk RiskRegister | P2 | — | VERIFIED | COMPLETED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-379](ULTIMATE_TASKS.md#hapi-379) | HMSR-069 | review risk RiskAssessment | review risk RiskAssessment | P2 | — | VERIFIED | COMPLETED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-401](ULTIMATE_TASKS.md#hapi-401) | HMSR-091 | review risk RiskEvidenceLink | review risk RiskEvidenceLink | P2 | — | VERIFIED | COMPLETED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-616](ULTIMATE_TASKS.md#hapi-616) | HPR-P25-057 | Integrated closure: four observation/action/outcome packages | Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-057; child tasks own implementation. four observation/action/outcome packages | P2 | HAPI-620 | PARTIAL | BLOCKED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-617](ULTIMATE_TASKS.md#hapi-617) | HPR-P25-057A | Monitoring conditions and observation evidence | Monitoring conditions and measured/declared/estimated/UNKNOWN observation evidence. | P2 | HAPI-610 | PARTIAL | BLOCKED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-618](ULTIMATE_TASKS.md#hapi-618) | HPR-P25-057B | Authenticated human action declarations and Workflow | Authenticated operator action declaration and actual supported Workflow target transitions. | P2 | HAPI-617 | PARTIAL | BLOCKED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-619](ULTIMATE_TASKS.md#hapi-619) | HPR-P25-057C | Observation/outcome correlation and recovery | Subsequent observation/outcome correlation, factual audit/query and recovery. | P2 | HAPI-618 | PARTIAL | BLOCKED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-620](ULTIMATE_TASKS.md#hapi-620) | HPR-P25-057D | Cross-owner operational action/observation qualification | Cross-owner action/observation security, rollback and consumer qualification. | P2 | HAPI-619 | PARTIAL | BLOCKED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-705](ULTIMATE_TASKS.md#hapi-705) | None — initial registration | Implement accountable AI-assisted evidence synthesis | Add a governed, provider-neutral synthesis adapter behind an outbound port; use authorized versioned evidence, deterministic safety checks, provenance, explicit… | P2 | HAPI-604, HAPI-616 | NOT_IMPLEMENTED | BLOCKED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-800](ULTIMATE_TASKS.md#hapi-800) | RISK-001 | RISK-001 historical bounded obligation | RISK-001 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-801](ULTIMATE_TASKS.md#hapi-801) | RISK-002 | RISK-002 historical bounded obligation | RISK-002 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-802](ULTIMATE_TASKS.md#hapi-802) | RISK-003 | RISK-003 historical bounded obligation | RISK-003 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-803](ULTIMATE_TASKS.md#hapi-803) | RISK-004 | RISK-004 historical bounded obligation | RISK-004 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-804](ULTIMATE_TASKS.md#hapi-804) | RISK-005 | RISK-005 historical bounded obligation | RISK-005 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-805](ULTIMATE_TASKS.md#hapi-805) | RISK-006 | RISK-006 historical bounded obligation | RISK-006 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-806](ULTIMATE_TASKS.md#hapi-806) | RISK-007 | RISK-007 historical bounded obligation | RISK-007 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-807](ULTIMATE_TASKS.md#hapi-807) | RISK-008 | RISK-008 historical bounded obligation | RISK-008 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-808](ULTIMATE_TASKS.md#hapi-808) | RISK-009 | RISK-009 historical bounded obligation | RISK-009 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-809](ULTIMATE_TASKS.md#hapi-809) | RISK-010 | RISK-010 historical bounded obligation | RISK-010 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-810](ULTIMATE_TASKS.md#hapi-810) | RISK-011 | RISK-011 historical bounded obligation | RISK-011 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-811](ULTIMATE_TASKS.md#hapi-811) | RISK-012 | RISK-012 historical bounded obligation | RISK-012 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-812](ULTIMATE_TASKS.md#hapi-812) | RISK-013 | RISK-013 historical bounded obligation | RISK-013 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-813](ULTIMATE_TASKS.md#hapi-813) | RISK-014 | RISK-014 historical bounded obligation | RISK-014 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-814](ULTIMATE_TASKS.md#hapi-814) | RISK-015 | RISK-015 historical bounded obligation | RISK-015 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-815](ULTIMATE_TASKS.md#hapi-815) | RISK-016 | RISK-016 historical bounded obligation | RISK-016 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-816](ULTIMATE_TASKS.md#hapi-816) | RISK-017 | RISK-017 historical bounded obligation | RISK-017 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-817](ULTIMATE_TASKS.md#hapi-817) | RISK-018 | RISK-018 historical bounded obligation | RISK-018 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-818](ULTIMATE_TASKS.md#hapi-818) | RISK-019 | RISK-019 historical bounded obligation | RISK-019 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-016 | Risk and Operational Intelligence | [HAPI-819](ULTIMATE_TASKS.md#hapi-819) | RISK-020 | RISK-020 historical bounded obligation | RISK-020 historical bounded obligation | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-017 | Analytics and Reporting | [HAPI-148](ULTIMATE_TASKS.md#hapi-148) | HMC-003 | scan analytics domain models | Scan all 7 analytics model files. | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-165](ULTIMATE_TASKS.md#hapi-165) | HMC-020 | scan reporting domain models | Scan all 4 reporting model files. | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-190](ULTIMATE_TASKS.md#hapi-190) | HMR-013 | remediate semantic review ReportDefinition | remediate semantic review ReportDefinition | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-194](ULTIMATE_TASKS.md#hapi-194) | HMR-016 | remediate semantic review MetricEvaluationRun | remediate semantic review MetricEvaluationRun | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-203](ULTIMATE_TASKS.md#hapi-203) | HMR-023 | remediate semantic review AnalyticsInsight | remediate semantic review AnalyticsInsight | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-204](ULTIMATE_TASKS.md#hapi-204) | HMR-024 | remediate semantic review AnalyticsProjectionRun | remediate semantic review AnalyticsProjectionRun | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-206](ULTIMATE_TASKS.md#hapi-206) | HMR-025 | remediate semantic review DigitalTwinReadinessAssessment | remediate semantic review DigitalTwinReadinessAssessment | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-223](ULTIMATE_TASKS.md#hapi-223) | HMR-037 | remediate semantic review AnalyticsDatasetVersion | remediate semantic review AnalyticsDatasetVersion | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-224](ULTIMATE_TASKS.md#hapi-224) | HMR-038 | remediate semantic review MetricValue | remediate semantic review MetricValue | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-239](ULTIMATE_TASKS.md#hapi-239) | HMR-048 | remediate semantic review ReportRequest | remediate semantic review ReportRequest | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-240](ULTIMATE_TASKS.md#hapi-240) | HMR-048A | register ReportRequest owner prerequisites | register ReportRequest owner prerequisites | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-251](ULTIMATE_TASKS.md#hapi-251) | HMR-057 | remediate semantic review ReportRun | remediate semantic review ReportRun | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-287](ULTIMATE_TASKS.md#hapi-287) | HMR-093 | remediate semantic review ReportOutputArtifact | remediate semantic review ReportOutputArtifact | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-323](ULTIMATE_TASKS.md#hapi-323) | HMSR-013 | review reporting ReportDefinition | review reporting ReportDefinition | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-327](ULTIMATE_TASKS.md#hapi-327) | HMSR-017 | review analytics AnalyticsDataset | review analytics AnalyticsDataset | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-328](ULTIMATE_TASKS.md#hapi-328) | HMSR-018 | review analytics MetricEvaluationRun | review analytics MetricEvaluationRun | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-335](ULTIMATE_TASKS.md#hapi-335) | HMSR-025 | review analytics AnalyticsInsight | review analytics AnalyticsInsight | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-336](ULTIMATE_TASKS.md#hapi-336) | HMSR-026 | review analytics AnalyticsProjectionRun | review analytics AnalyticsProjectionRun | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-337](ULTIMATE_TASKS.md#hapi-337) | HMSR-027 | review analytics DigitalTwinReadinessAssessment | review analytics DigitalTwinReadinessAssessment | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-352](ULTIMATE_TASKS.md#hapi-352) | HMSR-042 | review analytics AnalyticsDatasetVersion | review analytics AnalyticsDatasetVersion | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-353](ULTIMATE_TASKS.md#hapi-353) | HMSR-043 | review analytics MetricValue | review analytics MetricValue | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-367](ULTIMATE_TASKS.md#hapi-367) | HMSR-057 | review reporting ReportRequest | review reporting ReportRequest | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-378](ULTIMATE_TASKS.md#hapi-378) | HMSR-068 | review reporting ReportRun | review reporting ReportRun | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-420](ULTIMATE_TASKS.md#hapi-420) | HMSR-110 | review reporting ReportOutputArtifact | review reporting ReportOutputArtifact | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-511](ULTIMATE_TASKS.md#hapi-511) | HPR-P25-005 | reconstruct operational network state | Reconstruct a versioned time-coherent network state using approved physical methods; distinguish measured, estimated and unknown values and observability; retain… | P3 | HAPI-510 | NOT_VERIFIABLE | DEFERRED |
| HPS-017 | Analytics and Reporting | [HAPI-525](ULTIMATE_TASKS.md#hapi-525) | HPR-P25-013 | evaluate observed decision outcomes | Compare post-action telemetry with predicted outcomes in an approved observation window; retain actual action, confounders, confidence and attribution limits;… | P3 | HAPI-510, HAPI-524 | NOT_VERIFIABLE | DEFERRED |
| HPS-017 | Analytics and Reporting | [HAPI-526](ULTIMATE_TASKS.md#hapi-526) | HPR-P25-014 | govern learning from operational outcomes | Build provenance-controlled learning datasets from actual actions/outcomes; evaluate proposed detector, calibration or recommendation updates offline, version them… | P3 | HAPI-525 | NOT_VERIFIABLE | DEFERRED |
| HPS-017 | Analytics and Reporting | [HAPI-653](ULTIMATE_TASKS.md#hapi-653) | HRA-072 | simplify boundary dto mapping | Generate the eight HRA-070-approved exact analytics request/command and summary/response mappings. | P2 | — | VERIFIED | COMPLETED |
| HPS-017 | Analytics and Reporting | [HAPI-706](ULTIMATE_TASKS.md#hapi-706) | None — initial registration | Execute reproducible report rendering and artifact completion | Execute an admitted report template against an exact authorized input revision, persist immutable output content through Documents, and complete or fail the… | P2 | HAPI-604 | PARTIAL | BLOCKED |
| HPS-018 | Engineering Baselines and Configuration | [HAPI-534](ULTIMATE_TASKS.md#hapi-534) | HPR-P25-022 | define multi product property provisioning | define multi product property provisioning | P1 | — | VERIFIED | COMPLETED |
| HPS-018 | Engineering Baselines and Configuration | [HAPI-540](ULTIMATE_TASKS.md#hapi-540) | HPR-P25-028 | define authoritative engineering baseline management | define authoritative engineering baseline management | P1 | — | VERIFIED | COMPLETED |
| HPS-018 | Engineering Baselines and Configuration | [HAPI-541](ULTIMATE_TASKS.md#hapi-541) | HPR-P25-029 | separate independent monitoring and action evidence | separate independent monitoring and action evidence | P1 | — | VERIFIED | COMPLETED |
| HPS-018 | Engineering Baselines and Configuration | [HAPI-542](ULTIMATE_TASKS.md#hapi-542) | HPR-P25-030 | define engineering foundation acceptance cases | define engineering foundation acceptance cases | P1 | — | VERIFIED | COMPLETED |
| HPS-018 | Engineering Baselines and Configuration | [HAPI-604](ULTIMATE_TASKS.md#hapi-604) | HPR-P25-055 | Integrated closure: five authoritative-baseline/exchange packages | Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-055; child tasks own implementation. five authoritative-baseline/exchange… | P1 | HAPI-609 | PARTIAL | BLOCKED |
| HPS-018 | Engineering Baselines and Configuration | [HAPI-605](ULTIMATE_TASKS.md#hapi-605) | HPR-P25-055A | Baseline manifest drafts and completeness | Versioned manifest identity/draft and exact owner-reference/completeness validation. | P1 | HAPI-586, HAPI-593, HAPI-598 | NOT_IMPLEMENTED | BLOCKED |
| HPS-018 | Engineering Baselines and Configuration | [HAPI-606](ULTIMATE_TASKS.md#hapi-606) | HPR-P25-055B | Atomic authoritative baseline freeze | Atomic authoritative freeze/publication and idempotent conflict handling. | P1 | HAPI-605 | NOT_IMPLEMENTED | BLOCKED |
| HPS-018 | Engineering Baselines and Configuration | [HAPI-607](ULTIMATE_TASKS.md#hapi-607) | HPR-P25-055C | Exact baseline read, compare and reconstruction | Authorized exact read/history/effective selection, compare and reconstruction. | P1 | HAPI-606 | NOT_IMPLEMENTED | BLOCKED |
| HPS-018 | Engineering Baselines and Configuration | [HAPI-609](ULTIMATE_TASKS.md#hapi-609) | HPR-P25-055E | Multi-owner baseline and exchange qualification | Multi-owner freeze/replay/rollback/concurrency and API/consumer qualification. | P1 | HAPI-608 | NOT_IMPLEMENTED | BLOCKED |
| HPS-019 | Simulation and Digital Twin | [HAPI-167](ULTIMATE_TASKS.md#hapi-167) | HMC-022 | scan simulation domain models | Scan all 6 simulation model files. | P2 | — | VERIFIED | COMPLETED |
| HPS-019 | Simulation and Digital Twin | [HAPI-185](ULTIMATE_TASKS.md#hapi-185) | HMR-009 | remediate semantic review SimulationModel | remediate semantic review SimulationModel | P2 | — | VERIFIED | COMPLETED |
| HPS-019 | Simulation and Digital Twin | [HAPI-217](ULTIMATE_TASKS.md#hapi-217) | HMR-034 | remediate semantic review SimulationScenario | remediate semantic review SimulationScenario | P2 | — | VERIFIED | COMPLETED |
| HPS-019 | Simulation and Digital Twin | [HAPI-218](ULTIMATE_TASKS.md#hapi-218) | HMR-034A | register SimulationScenario reference prerequisite | register SimulationScenario reference prerequisite | P2 | — | VERIFIED | COMPLETED |
| HPS-019 | Simulation and Digital Twin | [HAPI-235](ULTIMATE_TASKS.md#hapi-235) | HMR-046 | remediate semantic review SimulationRun | remediate semantic review SimulationRun | P2 | — | VERIFIED | COMPLETED |
| HPS-019 | Simulation and Digital Twin | [HAPI-236](ULTIMATE_TASKS.md#hapi-236) | HMR-046A | register SimulationRun application prerequisite | register SimulationRun application prerequisite | P2 | — | VERIFIED | COMPLETED |
| HPS-019 | Simulation and Digital Twin | [HAPI-272](ULTIMATE_TASKS.md#hapi-272) | HMR-078 | remediate semantic review SimulationCandidateChange | remediate semantic review SimulationCandidateChange | P2 | — | VERIFIED | COMPLETED |
| HPS-019 | Simulation and Digital Twin | [HAPI-273](ULTIMATE_TASKS.md#hapi-273) | HMR-079 | remediate semantic review SimulationRecommendation | remediate semantic review SimulationRecommendation | P2 | — | VERIFIED | COMPLETED |
| HPS-019 | Simulation and Digital Twin | [HAPI-319](ULTIMATE_TASKS.md#hapi-319) | HMSR-009 | review simulation SimulationModel | review simulation SimulationModel | P2 | — | VERIFIED | COMPLETED |
| HPS-019 | Simulation and Digital Twin | [HAPI-348](ULTIMATE_TASKS.md#hapi-348) | HMSR-038 | review simulation SimulationScenario | review simulation SimulationScenario | P2 | — | VERIFIED | COMPLETED |
| HPS-019 | Simulation and Digital Twin | [HAPI-365](ULTIMATE_TASKS.md#hapi-365) | HMSR-055 | review simulation SimulationRun | review simulation SimulationRun | P2 | — | VERIFIED | COMPLETED |
| HPS-019 | Simulation and Digital Twin | [HAPI-376](ULTIMATE_TASKS.md#hapi-376) | HMSR-066 | review simulation SimulationOptimizationCandidate | review simulation SimulationOptimizationCandidate | P2 | — | VERIFIED | COMPLETED |
| HPS-019 | Simulation and Digital Twin | [HAPI-402](ULTIMATE_TASKS.md#hapi-402) | HMSR-092 | review simulation SimulationCandidateChange | review simulation SimulationCandidateChange | P2 | — | VERIFIED | COMPLETED |
| HPS-019 | Simulation and Digital Twin | [HAPI-403](ULTIMATE_TASKS.md#hapi-403) | HMSR-093 | review simulation SimulationRecommendation | review simulation SimulationRecommendation | P2 | — | VERIFIED | COMPLETED |
| HPS-019 | Simulation and Digital Twin | [HAPI-505](ULTIMATE_TASKS.md#hapi-505) | HPR-P25-002A | establish immutable input manifest contracts | Implement immutable manifest, source-version identities/validity/origin and steady-state/transient metadata invariants under the exact six-file envelope below. | P2 | HAPI-504 | VERIFIED | COMPLETED |
| HPS-019 | Simulation and Digital Twin | [HAPI-506](ULTIMATE_TASKS.md#hapi-506) | HPR-P25-002B | define versioned physical input payloads | Define validated typed network/fluid/equipment/initial-state and transient timeline payloads with units, reference basis and missing-data semantics; register exact… | P2 | HAPI-505 | VERIFIED | COMPLETED |
| HPS-019 | Simulation and Digital Twin | [HAPI-507](ULTIMATE_TASKS.md#hapi-507) | HPR-P25-002C | resolve versioned owner input references | Implement bounded owner-exported topology/product/measurement queries and Simulation adapters; preserve eligibility/trust/ownership and reject unavailable physical… | P3 | HAPI-506 | PARTIAL | DEFERRED |
| HPS-019 | Simulation and Digital Twin | [HAPI-508](ULTIMATE_TASKS.md#hapi-508) | HPR-P25-002D | capture reproducible network calculation inputs | Assemble coherent immutable payload/manifest captures, verify canonical hashes and evidence, persist reproducible revisions and reject incompatible/missing inputs;… | P3 | HAPI-507 | NOT_VERIFIABLE | DEFERRED |
| HPS-019 | Simulation and Digital Twin | [HAPI-513](ULTIMATE_TASKS.md#hapi-513) | HPR-P25-007 | resolve connected scope and boundary conditions | Select connected local/regional/full-network simulation scope; derive validated boundary conditions, propagate potential effects and expand scope or reject… | P3 | HAPI-504, HAPI-511 | NOT_VERIFIABLE | DEFERRED |
| HPS-019 | Simulation and Digital Twin | [HAPI-514](ULTIMATE_TASKS.md#hapi-514) | HPR-P25-008 | execute approved hydraulic solver | After HPR-P25-008A..F, establish governed execution of Hidra-owned gas engine for both modes with immutable inputs, product/mode checks, diagnostics, failure… | P3 | HAPI-513, HAPI-515 | NOT_VERIFIABLE | DEFERRED |
| HPS-019 | Simulation and Digital Twin | [HAPI-515](ULTIMATE_TASKS.md#hapi-515) | HPR-P25-008A | define internal gas engine numerical design | Specify gas equations/assumptions, gas-property methods, units/reference basis, boundary/initial conditions, supported network/equipment behavior, numerical… | P2 | HAPI-503, HAPI-504 | VERIFIED | COMPLETED |
| HPS-019 | Simulation and Digital Twin | [HAPI-516](ULTIMATE_TASKS.md#hapi-516) | HPR-P25-008B | implement internal steady state gas solver | Implement verified steady-state pipe/network calculation against analytical or independent synthetic reference cases; expose residuals, conservation, validity and… | P2 | HAPI-515 | VERIFIED | COMPLETED |
| HPS-019 | Simulation and Digital Twin | [HAPI-517](ULTIMATE_TASKS.md#hapi-517) | HPR-P25-008C | model gas network equipment behavior | Integrate approved compressor/valve/regulator behavior and current curve/configuration input contracts; test constraints, flow changes and effects across connected… | P3 | HAPI-504, HAPI-516 | PARTIAL | DEFERRED |
| HPS-019 | Simulation and Digital Twin | [HAPI-518](ULTIMATE_TASKS.md#hapi-518) | HPR-P25-008D | implement internal transient gas solver | Implement time-dependent gas-network behavior with initial state and boundary/action timelines; verify conservation, spatial/time refinement, stability and… | P3 | HAPI-517 | NOT_VERIFIABLE | DEFERRED |
| HPS-019 | Simulation and Digital Twin | [HAPI-519](ULTIMATE_TASKS.md#hapi-519) | HPR-P25-008E | verify internal gas engine numerical behavior | Independently verify both engine modes against reproducible analytical/manufactured/reference cases, numerical refinement and adverse/failure cases; publish… | P3 | HAPI-516 | NOT_VERIFIABLE | DEFERRED |
| HPS-019 | Simulation and Digital Twin | [HAPI-520](ULTIMATE_TASKS.md#hapi-520) | HPR-P25-008F | integrate internal gas engine execution | Bind versioned engine to run lifecycle and immutable input/result contracts; verify actual execution, cancellation/timeouts/resource budgets and reproducibility;… | P3 | HAPI-513, HAPI-519 | NOT_VERIFIABLE | DEFERRED |
| HPS-019 | Simulation and Digital Twin | [HAPI-521](ULTIMATE_TASKS.md#hapi-521) | HPR-P25-009 | validate calibrated network physics | Calibrate and validate solver/estimator behavior against approved measured/reference cases and current equipment curves; publish per-product/mode tolerances,… | P3 | HAPI-511, HAPI-514 | NOT_VERIFIABLE | DEFERRED |
| HPS-019 | Simulation and Digital Twin | [HAPI-522](ULTIMATE_TASKS.md#hapi-522) | HPR-P25-010 | orchestrate issue triggered network runs | Automatically trigger governed real simulations from eligible issues using traceable state snapshots, debounce/deduplication, priorities and bounded resource… | P3 | HAPI-512 | NOT_VERIFIABLE | DEFERRED |
| HPS-019 | Simulation and Digital Twin | [HAPI-523](ULTIMATE_TASKS.md#hapi-523) | HPR-P25-011 | propose network validated operator decisions | Evaluate candidate operator actions against baseline and affected-network constraints; present expected benefits, adverse effects elsewhere, uncertainty and… | P3 | HAPI-521 | NOT_VERIFIABLE | DEFERRED |
| HPS-019 | Simulation and Digital Twin | [HAPI-535](ULTIMATE_TASKS.md#hapi-535) | HPR-P25-023 | define reproducible engineering baselines | define reproducible engineering baselines | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-019 | Simulation and Digital Twin | [HAPI-536](ULTIMATE_TASKS.md#hapi-536) | HPR-P25-024 | define model and provider extension contracts | define model and provider extension contracts | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-019 | Simulation and Digital Twin | [HAPI-537](ULTIMATE_TASKS.md#hapi-537) | HPR-P25-025 | define governed execution modes | define governed execution modes | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-019 | Simulation and Digital Twin | [HAPI-538](ULTIMATE_TASKS.md#hapi-538) | HPR-P25-026 | define calibration readiness contracts | define calibration readiness contracts | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-020 | Documents and Data Exchange | [HAPI-124](ULTIMATE_TASKS.md#hapi-124) | HDP-001 | add legacy data migration roadmap | add legacy data migration roadmap | P2 | — | VERIFIED | COMPLETED |
| HPS-020 | Documents and Data Exchange | [HAPI-125](ULTIMATE_TASKS.md#hapi-125) | HDP-002 | inventory hyflo source files and provenance | inventory hyflo source files and provenance | P2 | — | VERIFIED | COMPLETED |
| HPS-020 | Documents and Data Exchange | [HAPI-126](ULTIMATE_TASKS.md#hapi-126) | HDP-003 | inventory hidra target models and schema | inventory hidra target models and schema | P2 | — | VERIFIED | COMPLETED |
| HPS-020 | Documents and Data Exchange | [HAPI-127](ULTIMATE_TASKS.md#hapi-127) | HDP-004 | classify source datasets and import eligibility | classify source datasets and import eligibility | P2 | — | VERIFIED | COMPLETED |
| HPS-020 | Documents and Data Exchange | [HAPI-128](ULTIMATE_TASKS.md#hapi-128) | HDP-005 | map legacy fields to hidra contracts | map legacy fields to hidra contracts | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-020 | Documents and Data Exchange | [HAPI-129](ULTIMATE_TASKS.md#hapi-129) | HDP-006 | approve canonical sources and migration scope | approve canonical sources and migration scope | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-020 | Documents and Data Exchange | [HAPI-130](ULTIMATE_TASKS.md#hapi-130) | HDP-007 | define algerian geographic normalization rules | define algerian geographic normalization rules | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-020 | Documents and Data Exchange | [HAPI-131](ULTIMATE_TASKS.md#hapi-131) | HDP-008 | define pipeline and engineering normalization rules | define pipeline and engineering normalization rules | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-020 | Documents and Data Exchange | [HAPI-132](ULTIMATE_TASKS.md#hapi-132) | HDP-009 | define identifier and dependency mapping | define identifier and dependency mapping | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-020 | Documents and Data Exchange | [HAPI-133](ULTIMATE_TASKS.md#hapi-133) | HDP-010 | approve reproducible provisioning design | approve reproducible provisioning design | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-020 | Documents and Data Exchange | [HAPI-134](ULTIMATE_TASKS.md#hapi-134) | HDP-011 | add sanitized source fixtures and mapping contracts | add sanitized source fixtures and mapping contracts | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-020 | Documents and Data Exchange | [HAPI-135](ULTIMATE_TASKS.md#hapi-135) | HDP-012 | add deterministic extraction and quarantine | add deterministic extraction and quarantine | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-020 | Documents and Data Exchange | [HAPI-136](ULTIMATE_TASKS.md#hapi-136) | HDP-013 | add geographic and reference transformations | add geographic and reference transformations | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-020 | Documents and Data Exchange | [HAPI-137](ULTIMATE_TASKS.md#hapi-137) | HDP-014 | add pipeline and facility transformations | add pipeline and facility transformations | P3 | — | PARTIAL | DEFERRED |
| HPS-020 | Documents and Data Exchange | [HAPI-138](ULTIMATE_TASKS.md#hapi-138) | HDP-015 | add dry-run and idempotent import orchestration | add dry-run and idempotent import orchestration | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-020 | Documents and Data Exchange | [HAPI-139](ULTIMATE_TASKS.md#hapi-139) | HDP-016 | provision approved geographic and reference datasets | provision approved geographic and reference datasets | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-020 | Documents and Data Exchange | [HAPI-140](ULTIMATE_TASKS.md#hapi-140) | HDP-017 | provision approved physical network master data | provision approved physical network master data | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-020 | Documents and Data Exchange | [HAPI-141](ULTIMATE_TASKS.md#hapi-141) | HDP-018 | verify master-data imports and replay safety | verify master-data imports and replay safety | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-020 | Documents and Data Exchange | [HAPI-142](ULTIMATE_TASKS.md#hapi-142) | HDP-019 | disposition operational and historical sources | disposition operational and historical sources | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-020 | Documents and Data Exchange | [HAPI-143](ULTIMATE_TASKS.md#hapi-143) | HDP-020 | add end-to-end reconciliation and security gates | add end-to-end reconciliation and security gates | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-020 | Documents and Data Exchange | [HAPI-144](ULTIMATE_TASKS.md#hapi-144) | HDP-021 | document import operations and rollback | document import operations and rollback | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-020 | Documents and Data Exchange | [HAPI-145](ULTIMATE_TASKS.md#hapi-145) | HDP-022 | finalize data migration acceptance | finalize data migration acceptance | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-020 | Documents and Data Exchange | [HAPI-153](ULTIMATE_TASKS.md#hapi-153) | HMC-008 | scan documents domain models | Scan all 4 documents model files. | P2 | — | VERIFIED | COMPLETED |
| HPS-020 | Documents and Data Exchange | [HAPI-183](ULTIMATE_TASKS.md#hapi-183) | HMR-008 | remediate semantic review DocumentStorageObject | remediate semantic review DocumentStorageObject | P2 | — | VERIFIED | COMPLETED |
| HPS-020 | Documents and Data Exchange | [HAPI-184](ULTIMATE_TASKS.md#hapi-184) | HMR-008A | amend DocumentStorageObject remediation scope | amend DocumentStorageObject remediation scope | P2 | — | VERIFIED | COMPLETED |
| HPS-020 | Documents and Data Exchange | [HAPI-261](ULTIMATE_TASKS.md#hapi-261) | HMR-067 | remediate semantic review Document | remediate semantic review Document | P2 | — | VERIFIED | COMPLETED |
| HPS-020 | Documents and Data Exchange | [HAPI-262](ULTIMATE_TASKS.md#hapi-262) | HMR-068 | remediate semantic review DocumentVersion | remediate semantic review DocumentVersion | P2 | — | VERIFIED | COMPLETED |
| HPS-020 | Documents and Data Exchange | [HAPI-278](ULTIMATE_TASKS.md#hapi-278) | HMR-084 | remediate semantic review DocumentTargetLink | remediate semantic review DocumentTargetLink | P2 | — | VERIFIED | COMPLETED |
| HPS-020 | Documents and Data Exchange | [HAPI-318](ULTIMATE_TASKS.md#hapi-318) | HMSR-008 | review documents DocumentStorageObject | review documents DocumentStorageObject | P2 | — | VERIFIED | COMPLETED |
| HPS-020 | Documents and Data Exchange | [HAPI-389](ULTIMATE_TASKS.md#hapi-389) | HMSR-079 | review documents Document | review documents Document | P2 | — | VERIFIED | COMPLETED |
| HPS-020 | Documents and Data Exchange | [HAPI-390](ULTIMATE_TASKS.md#hapi-390) | HMSR-080 | review documents DocumentVersion | review documents DocumentVersion | P2 | — | VERIFIED | COMPLETED |
| HPS-020 | Documents and Data Exchange | [HAPI-408](ULTIMATE_TASKS.md#hapi-408) | HMSR-098 | review documents DocumentTargetLink | review documents DocumentTargetLink | P2 | — | VERIFIED | COMPLETED |
| HPS-020 | Documents and Data Exchange | [HAPI-591](ULTIMATE_TASKS.md#hapi-591) | HPR-P25-052E | Typed local engineering exchange and capabilities | Supported local typed import/export and per-editor capability/completeness closure. | P2 | HAPI-590 | NOT_IMPLEMENTED | BLOCKED |
| HPS-020 | Documents and Data Exchange | [HAPI-608](ULTIMATE_TASKS.md#hapi-608) | HPR-P25-055D | Aggregate typed engineering exchange | Supported aggregate engineering import/export, dry-run and conflict-safe replay. | P2 | HAPI-607 | NOT_IMPLEMENTED | BLOCKED |
| HPS-021 | Audit and Traceability | [HAPI-150](ULTIMATE_TASKS.md#hapi-150) | HMC-005 | scan audit domain models | Scan all 4 audit model files. | P2 | — | VERIFIED | COMPLETED |
| HPS-021 | Audit and Traceability | [HAPI-277](ULTIMATE_TASKS.md#hapi-277) | HMR-083 | remediate semantic review AuditExportRequest | remediate semantic review AuditExportRequest | P2 | — | VERIFIED | COMPLETED |
| HPS-021 | Audit and Traceability | [HAPI-289](ULTIMATE_TASKS.md#hapi-289) | HMR-095 | remediate semantic review AuditEvent | remediate semantic review AuditEvent | P2 | — | VERIFIED | COMPLETED |
| HPS-021 | Audit and Traceability | [HAPI-295](ULTIMATE_TASKS.md#hapi-295) | HMR-101 | remediate semantic review AuditAccessRecord | remediate semantic review AuditAccessRecord | P2 | — | VERIFIED | COMPLETED |
| HPS-021 | Audit and Traceability | [HAPI-296](ULTIMATE_TASKS.md#hapi-296) | HMR-102 | remediate semantic review AuditBeforeAfterValue | remediate semantic review AuditBeforeAfterValue | P2 | — | VERIFIED | COMPLETED |
| HPS-021 | Audit and Traceability | [HAPI-407](ULTIMATE_TASKS.md#hapi-407) | HMSR-097 | review audit AuditExportRequest | review audit AuditExportRequest | P2 | — | VERIFIED | COMPLETED |
| HPS-021 | Audit and Traceability | [HAPI-422](ULTIMATE_TASKS.md#hapi-422) | HMSR-112 | review audit AuditEvent | review audit AuditEvent | P2 | — | VERIFIED | COMPLETED |
| HPS-021 | Audit and Traceability | [HAPI-428](ULTIMATE_TASKS.md#hapi-428) | HMSR-118 | review audit AuditAccessRecord | review audit AuditAccessRecord | P2 | — | VERIFIED | COMPLETED |
| HPS-021 | Audit and Traceability | [HAPI-429](ULTIMATE_TASKS.md#hapi-429) | HMSR-119 | review audit AuditBeforeAfterValue | review audit AuditBeforeAfterValue | P2 | — | VERIFIED | COMPLETED |
| HPS-022 | Integration and Interoperability | [HAPI-104](ULTIMATE_TASKS.md#hapi-104) | EXT-008 | add sparkplug boundary contracts | Define Integration-side external identities and mapping of Group/Edge Node/Device/Metric to Telemetry references. | P3 | — | PARTIAL | DEFERRED |
| HPS-022 | Integration and Interoperability | [HAPI-105](ULTIMATE_TASKS.md#hapi-105) | EXT-009 | add mqtt sparkplug adapter | Add the approved MQTT client/configuration and Sparkplug decoding adapter. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-022 | Integration and Interoperability | [HAPI-106](ULTIMATE_TASKS.md#hapi-106) | EXT-010 | verify mqtt telemetry handoff | Verify MQTT/Sparkplug adapter hands accepted measurements to Telemetry through the approved application boundary. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-022 | Integration and Interoperability | [HAPI-157](ULTIMATE_TASKS.md#hapi-157) | HMC-012 | scan integration domain models | Scan all 4 integration model files. | P2 | — | VERIFIED | COMPLETED |
| HPS-022 | Integration and Interoperability | [HAPI-191](ULTIMATE_TASKS.md#hapi-191) | HMR-014 | remediate semantic review IntegrationJobRun | remediate semantic review IntegrationJobRun | P2 | — | VERIFIED | COMPLETED |
| HPS-022 | Integration and Interoperability | [HAPI-237](ULTIMATE_TASKS.md#hapi-237) | HMR-047 | remediate semantic review ExternalSystem | remediate semantic review ExternalSystem | P2 | — | VERIFIED | COMPLETED |
| HPS-022 | Integration and Interoperability | [HAPI-238](ULTIMATE_TASKS.md#hapi-238) | HMR-047A | register ExternalSystem owner prerequisite | register ExternalSystem owner prerequisite | P2 | — | VERIFIED | COMPLETED |
| HPS-022 | Integration and Interoperability | [HAPI-250](ULTIMATE_TASKS.md#hapi-250) | HMR-056 | remediate semantic review IntegrationExchangeMessage | remediate semantic review IntegrationExchangeMessage | P2 | — | VERIFIED | COMPLETED |
| HPS-022 | Integration and Interoperability | [HAPI-265](ULTIMATE_TASKS.md#hapi-265) | HMR-071 | remediate semantic review IntegrationDeadLetterRecord | remediate semantic review IntegrationDeadLetterRecord | P2 | — | VERIFIED | COMPLETED |
| HPS-022 | Integration and Interoperability | [HAPI-324](ULTIMATE_TASKS.md#hapi-324) | HMSR-014 | review integration IntegrationJobRun | review integration IntegrationJobRun | P2 | — | VERIFIED | COMPLETED |
| HPS-022 | Integration and Interoperability | [HAPI-366](ULTIMATE_TASKS.md#hapi-366) | HMSR-056 | review integration ExternalSystem | review integration ExternalSystem | P2 | — | VERIFIED | COMPLETED |
| HPS-022 | Integration and Interoperability | [HAPI-377](ULTIMATE_TASKS.md#hapi-377) | HMSR-067 | review integration IntegrationExchangeMessage | review integration IntegrationExchangeMessage | P2 | — | VERIFIED | COMPLETED |
| HPS-022 | Integration and Interoperability | [HAPI-394](ULTIMATE_TASKS.md#hapi-394) | HMSR-084 | review integration IntegrationDeadLetterRecord | review integration IntegrationDeadLetterRecord | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-047](ULTIMATE_TASKS.md#hapi-047) | COR2-001 | add correction 02 roadmap | Add this executable correction roadmap | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-053](ULTIMATE_TASKS.md#hapi-053) | COR2-007 | audit remaining user-facing type enums | Classify remaining domain/value type enums and references | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-055](ULTIMATE_TASKS.md#hapi-055) | COR2-009 | standardize domain name value objects | Align name/title/designation value objects with trilingual convention | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-056](ULTIMATE_TASKS.md#hapi-056) | COR2-010 | standardize multilingual columns | Align JPA entities and migrations with trilingual label fields | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-057](ULTIMATE_TASKS.md#hapi-057) | COR2-011 | complete REST parameter documentation | Add missing @Parameter, examples, and API response documentation | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-058](ULTIMATE_TASKS.md#hapi-058) | COR2-012 | decide domain schema annotation policy | Make the domain @Schema policy explicit and consistent | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-059](ULTIMATE_TASKS.md#hapi-059) | COR2-013 | add domain and persistence schema diagrams | Add Mermaid domain and persistence diagrams | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-060](ULTIMATE_TASKS.md#hapi-060) | COR2-014 | rewrite project README | Replace minimal README with full developer onboarding documentation | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-061](ULTIMATE_TASKS.md#hapi-061) | COR2-015 | add contribution and execution rules | Add CONTRIBUTING.md with module and review rules | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-062](ULTIMATE_TASKS.md#hapi-062) | COR2-016 | add correction 02 guardrails | Add/extend architecture tests for deprecated imports and module boundaries | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-063](ULTIMATE_TASKS.md#hapi-063) | COR2-017 | finalize correction 02 checklist | Record final validation status and checklist | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-064](ULTIMATE_TASKS.md#hapi-064) | COR-001 | add repository correction roadmap | Add correction roadmap. | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-065](ULTIMATE_TASKS.md#hapi-065) | COR-002 | remove generated bin roadmap artifacts | Remove committed generated bin/docs/ and ignore bin/. | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-067](ULTIMATE_TASKS.md#hapi-067) | COR-004 | define controlled vocabulary policy | Define enum-vs-catalog policy. | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-078](ULTIMATE_TASKS.md#hapi-078) | COR-015 | add controlled vocabulary guardrails | Add test blocking forbidden business taxonomy enum declarations. | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-081](ULTIMATE_TASKS.md#hapi-081) | COR-018 | validate corrected baseline | Validation-only task requiring local Maven/Flyway/Testcontainers execution. | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-084](ULTIMATE_TASKS.md#hapi-084) | COR-021 | replace measurement next-module references with telemetry | Correct next-module naming from measurement/MES to telemetry/TEL. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-097](ULTIMATE_TASKS.md#hapi-097) | EXT-001 | audit extended industrial capabilities | Create the live-repository capability audit and this roadmap only. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-098](ULTIMATE_TASKS.md#hapi-098) | EXT-002 | decide industrial extension boundaries | Add ADRs for MQTT/Sparkplug ownership, high-volume telemetry persistence, PostGIS/route ownership, gRPC service boundary, database-extension provisioning, and B31G… | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-099](ULTIMATE_TASKS.md#hapi-099) | EXT-003 | define industrial extension contracts | Specify versioned cross-boundary contracts and exact file allowlists for first implementation tasks, including idempotency and failure semantics. | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-146](ULTIMATE_TASKS.md#hapi-146) | HMC-001 | add module model scan roadmap | Create this roadmap only. No model extraction. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-151](ULTIMATE_TASKS.md#hapi-151) | HMC-006 | scan configuration domain models | Scan all 3 configuration model files. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-171](ULTIMATE_TASKS.md#hapi-171) | HMC-026 | reconcile module model catalogue | Reconcile all 24 module outputs against the pinned 123-file baseline; resolve extraction exceptions and duplicate/missing rows. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-172](ULTIMATE_TASKS.md#hapi-172) | HMC-027 | generate final model field workbook | Generate the consolidated Excel document from the reconciled catalogue. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-173](ULTIMATE_TASKS.md#hapi-173) | HMC-028 | validate and finalize model field catalogue | Validate workbook readability, row/model/module counts, provenance, and final delivery evidence. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-174](ULTIMATE_TASKS.md#hapi-174) | HMR-001 | establish semantic remediation roadmap | Create this roadmap from the completed HMS-006 reconciliation; reserve one production task per REVISE model; gate HMS-007 behind HMR; no production changes. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-186](ULTIMATE_TASKS.md#hapi-186) | HMR-009A | register SimulationModel topology owner-contract prerequisite | register SimulationModel topology owner-contract prerequisite | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-193](ULTIMATE_TASKS.md#hapi-193) | HMR-015A | register LeakCandidate topology owner-contract prerequisite | register LeakCandidate topology owner-contract prerequisite | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-195](ULTIMATE_TASKS.md#hapi-195) | HMR-016A | register MetricEvaluationRun scope and lifecycle prerequisite | register MetricEvaluationRun scope and lifecycle prerequisite | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-196](ULTIMATE_TASKS.md#hapi-196) | HMR-017 | remediate semantic review ConfigurationDefinition | remediate semantic review ConfigurationDefinition | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-202](ULTIMATE_TASKS.md#hapi-202) | HMR-022A | register PipelineSystem classification catalog prerequisite | register PipelineSystem classification catalog prerequisite | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-205](ULTIMATE_TASKS.md#hapi-205) | HMR-024A | register AnalyticsProjectionRun reproducibility prerequisite | register AnalyticsProjectionRun reproducibility prerequisite | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-207](ULTIMATE_TASKS.md#hapi-207) | HMR-026 | remediate semantic review FeatureFlag | remediate semantic review FeatureFlag | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-210](ULTIMATE_TASKS.md#hapi-210) | HMR-028A | register ReportingLine catalog and matrix-policy prerequisite | register ReportingLine catalog and matrix-policy prerequisite | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-214](ULTIMATE_TASKS.md#hapi-214) | HMR-031A | register TopologyConnection catalog prerequisite | register TopologyConnection catalog prerequisite | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-222](ULTIMATE_TASKS.md#hapi-222) | HMR-036B | register Facility Party contract guardrail prerequisite | register Facility Party contract guardrail prerequisite | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-225](ULTIMATE_TASKS.md#hapi-225) | HMR-039 | remediate semantic review ConfigurationValue | remediate semantic review ConfigurationValue | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-243](ULTIMATE_TASKS.md#hapi-243) | HMR-049B | register RiskRegister audit taxonomy prerequisite | register RiskRegister audit taxonomy prerequisite | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-301](ULTIMATE_TASKS.md#hapi-301) | HMR-107 | verify semantic remediation closure | After all model tasks resolve, run the repository-required full exact-SHA verification, validate Flyway replay/architecture/OpenAPI gates, and prove the 104-source… | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-302](ULTIMATE_TASKS.md#hapi-302) | HMR-108 | close semantic remediation programme | Reconcile HMR outcomes, record completed/skipped/deferred obligations, and explicitly determine whether the HMS-007 completion gate is satisfied. No HMS-007… | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-303](ULTIMATE_TASKS.md#hapi-303) | HMS-001 | establish target model semantic review roadmap | Create this roadmap and amend HDP so HDP-004 is explicitly paused behind the semantic-review prerequisite. No dependency analysis or production code changes. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-305](ULTIMATE_TASKS.md#hapi-305) | HMS-003 | classify target model dependencies | Resolve candidate edges into the dependency classifications in section 4; separate true dependencies from snapshots/codes and record unresolved edges. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-306](ULTIMATE_TASKS.md#hapi-306) | HMS-004 | validate dependency graph and cycles | Reconcile graph against persistence/contracts, identify strongly connected components, missing targets, contradictory edges and cross-module boundary concerns. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-307](ULTIMATE_TASKS.md#hapi-307) | HMS-005 | generate dependency ordered model review register | Compute deterministic review levels/order and generate the 123-model review register with HMSR-001…HMSR-123 codes and exact per-model commit messages. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-308](ULTIMATE_TASKS.md#hapi-308) | HMS-006 | reconcile interactive model decisions | After all HMSR tasks are resolved, reconcile APPROVED/REVISE/DEFER/REMOVE decisions, outstanding corrections, unresolved cycles and dependency impacts. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-309](ULTIMATE_TASKS.md#hapi-309) | HMS-007 | finalize approved target model semantic baseline | Publish the final target-model semantic baseline only when every model has a resolved disposition and any required model corrections are implemented or explicitly… | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-310](ULTIMATE_TASKS.md#hapi-310) | HMS-008 | align provisioning roadmap to semantic model baseline | Amend HDP target assumptions, record the approved HMS baseline, and determine whether HDP-004 may resume. Does not itself classify source data or start HDP-005. | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-329](ULTIMATE_TASKS.md#hapi-329) | HMSR-019 | review configuration ConfigurationDefinition | review configuration ConfigurationDefinition | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-338](ULTIMATE_TASKS.md#hapi-338) | HMSR-028 | review configuration FeatureFlag | review configuration FeatureFlag | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-354](ULTIMATE_TASKS.md#hapi-354) | HMSR-044 | review configuration ConfigurationValue | review configuration ConfigurationValue | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-481](ULTIMATE_TASKS.md#hapi-481) | HPR-P2-001 | complete canonical documentation controls | Complete canonical doc/ governance/index structure and status metadata; preserve docs/ as evidence. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-482](ULTIMATE_TASKS.md#hapi-482) | HPR-P2-002 | establish canonical architecture set | Create current/target-separated system context, architecture overview, bounded-context map, Hexagonal boundaries, module boundaries, cross-module contracts and… | P2 | HAPI-481 | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-483](ULTIMATE_TASKS.md#hapi-483) | HPR-P2-003 | establish ubiquitous language baseline | Create UBIQUITOUS_LANGUAGE.md, domain ownership and focused topology/telemetry/alarm-incident-leak/assets-integrity/simulation-analytics-AI semantic documents. | P2 | HAPI-481 | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-484](ULTIMATE_TASKS.md#hapi-484) | HPR-P2-004 | add canonical module documentation | Create one current-state doc/modules/<module>.md for each of the 24 implemented modules; do not create current-state module docs for agents/environment/otsecurity. | P2 | HAPI-482 | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-485](ULTIMATE_TASKS.md#hapi-485) | HPR-P2-005 | establish versioned api contract | Complete source-backed API conventions and runtime error documentation under the bounded audit amendment below; preserve the generated-envelope limitation and… | P2 | HAPI-459 | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-486](ULTIMATE_TASKS.md#hapi-486) | HPR-P2-006 | establish canonical database documentation | Complete the current physical table/column dictionary and explicit ownership/FK/migration governance under the bounded audit amendment below; preserve applied SQL… | P2 | HAPI-481 | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-487](ULTIMATE_TASKS.md#hapi-487) | HPR-P2-007 | reconcile remaining semantic obligations | Inventory unresolved HMR/HMSR obligations against exact current source; mark each as completed, still required, blocked, or superseded with evidence. | P2 | HAPI-483 | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-488](ULTIMATE_TASKS.md#hapi-488) | HPR-P2-008 | continue reconciled semantic remediation | Execute still-required semantic remediation in dependency order using revalidated HMSR obligations; do not restart completed HMRs without regression evidence. | P2 | HAPI-487 | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-489](ULTIMATE_TASKS.md#hapi-489) | HPR-P2-009 | canonicalize semantic decisions | Transfer permanent semantic decisions from legacy review/roadmaps into doc/domain/ and doc/modules/, then preserve legacy files as execution history. | P2 | HAPI-488 | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-490](ULTIMATE_TASKS.md#hapi-490) | HPR-P2-010 | establish data governance baseline | Create data governance, retention/archival, provenance and legacy-data migration documents without inventing retention values. | P2 | HAPI-481 | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-493](ULTIMATE_TASKS.md#hapi-493) | HPR-P2-013 | close P2 canonical governance | Reverify all twelve P2 checks after HPR-P2-005/006 corrections, canonical status/index consistency and exact-head CI; close P2 only with 12/12 VERIFIED. | P2 | HAPI-481 | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-498](ULTIMATE_TASKS.md#hapi-498) | HPR-P3-005 | define spatial persistence architecture | Approve spatial architecture: route ownership, geometry model, CRS/SRID, KP reconciliation and query requirements; current scalar/KP storage remains authoritative… | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-499](ULTIMATE_TASKS.md#hapi-499) | HPR-P3-006 | introduce postgis route persistence | Provision PostGIS and authoritative route geometry only after HPR-P3-005 approval; add appropriate spatial indexes. | P3 | HAPI-498 | NOT_VERIFIABLE | DEFERRED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-500](ULTIMATE_TASKS.md#hapi-500) | HPR-P3-007 | verify spatial route semantics | Reconcile route geometry with existing KP/linear referencing; verify SRID, geometry validity, route/KP consistency and query performance. | P3 | HAPI-499 | NOT_VERIFIABLE | DEFERRED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-501](ULTIMATE_TASKS.md#hapi-501) | HPR-P3-008 | reconcile deferred industrial capabilities | Keep MQTT/Sparkplug and other audit-classified industrial extensions deferred until a separately approved requirement exists; update CURRENT status only after… | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-502](ULTIMATE_TASKS.md#hapi-502) | HPR-P25-000 | register phase 2.5 operational digital twin | Register the owner-defined operational twin cycle, ordered tasks, prerequisites, acceptance gates and next requirements preflight. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-503](ULTIMATE_TASKS.md#hapi-503) | HPR-P25-001 | define requirements and execution preflight | Inventory exact-source gaps and owners; specify product/use-case coverage, data needs, solver integration options, steady-state/transient scope,… | P2 | HAPI-502 | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-527](ULTIMATE_TASKS.md#hapi-527) | HPR-P25-015 | verify operator visualization and decision workflow | Demonstrate operator visualization, issue-to-run explanation, candidate comparisons, decision capture and outcome views with the selected frontend; record separate… | P3 | HAPI-509, HAPI-522 | NOT_VERIFIABLE | DEFERRED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-528](ULTIMATE_TASKS.md#hapi-528) | HPR-P25-016 | verify operational decision feedback cycle | Execute end-to-end historical replay and governed pilot cases including adverse downstream effects, scope expansion, unavailable solver, poor/stale data,… | P3 | HAPI-527 | NOT_VERIFIABLE | DEFERRED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-529](ULTIMATE_TASKS.md#hapi-529) | HPR-P25-017 | close phase 2.5 operational twin verification | Independently audit all P2.5 gates against actual execution and per-product/mode evidence; close only after exact-head applicable CI and operator acceptance, with… | P3 | HAPI-503, HAPI-505, HAPI-515 | NOT_VERIFIABLE | DEFERRED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-530](ULTIMATE_TASKS.md#hapi-530) | HPR-P25-018 | define engineering provisioning contracts | define engineering provisioning contracts | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-539](ULTIMATE_TASKS.md#hapi-539) | HPR-P25-027 | define engineering acceptance and product qualification | define engineering acceptance and product qualification | P3 | — | NOT_VERIFIABLE | DEFERRED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-544](ULTIMATE_TASKS.md#hapi-544) | HPR-P25-032 | HPR-P25-032 historical bounded obligation | HPR-P25-032 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-545](ULTIMATE_TASKS.md#hapi-545) | HPR-P25-033 | HPR-P25-033 historical bounded obligation | HPR-P25-033 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-546](ULTIMATE_TASKS.md#hapi-546) | HPR-P25-034 | HPR-P25-034 historical bounded obligation | HPR-P25-034 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-548](ULTIMATE_TASKS.md#hapi-548) | HPR-P25-036 | Separate owner instruction after this registration's documentation acceptance | Separate owner instruction after this registration's documentation acceptance | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-550](ULTIMATE_TASKS.md#hapi-550) | HPR-P25-038 | HPR-P25-038 historical bounded obligation | HPR-P25-038 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-554](ULTIMATE_TASKS.md#hapi-554) | HPR-P25-042 | HPR-P25-042 historical bounded obligation | HPR-P25-042 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-628](ULTIMATE_TASKS.md#hapi-628) | HRA-001 | record forensic remediation baseline | Version this repository remediation roadmap and the evidence-backed forensic baseline. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-629](ULTIMATE_TASKS.md#hapi-629) | HRA-002 | enforce repository structural guardrails | Extend architecture/static-analysis guardrails for cross-module private-package imports, existing layer rules, duplicate JPA tables/columns, and deliberate… | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-630](ULTIMATE_TASKS.md#hapi-630) | HRA-003 | detect domain mapper field drift | Add domain-to-JPA mapper completeness/round-trip protection, beginning with Organization Employee and explicit exclusions for derived/compatibility state. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-631](ULTIMATE_TASKS.md#hapi-631) | HRA-004 | optimize repository verification workflow | Remove redundant Maven executions from GitHub Actions, retain one wrapper-based clean verify, keep PostgreSQL/OpenAPI verification for code changes, and skip the… | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-637](ULTIMATE_TASKS.md#hapi-637) | HRA-021 | Preflight and backfill deterministic legacy email/mobile values into contact points; quarantine ambiguity. | Preflight and backfill deterministic legacy email/mobile values into contact points; quarantine ambiguity. | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-640](ULTIMATE_TASKS.md#hapi-640) | HRA-030 | decide domain event and outbox strategy | Choose one canonical path: implement events/outbox or remove fictional scaffolding. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-641](ULTIMATE_TASKS.md#hapi-641) | HRA-031A | unify module domain event contract | If events are retained, reconcile module event interfaces with kernel DomainEvent, event IDs, timestamps, and version metadata. | P2 | — | IMPLEMENTED | SUPERSEDED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-642](ULTIMATE_TASKS.md#hapi-642) | HRA-031B | remove unused event scaffolding | If events are not currently required, remove unreferenced module event records/publishers and unused outbox contracts after consumer verification. | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-643](ULTIMATE_TASKS.md#hapi-643) | HRA-032A | implement transactional outbox pipeline | Implement serializer, outbox repository adapter, publisher, Spring wiring, and transactional tests. | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-644](ULTIMATE_TASKS.md#hapi-644) | HRA-033A | publish domain events from state transitions | Emit retained events from real state-changing use cases and remove replaced in-memory publisher stubs. | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-645](ULTIMATE_TASKS.md#hapi-645) | HRA-040 | classify static orphan candidates | Classify all 210 zero-incoming candidates as DELETE or evidence-backed KEEP. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-646](ULTIMATE_TASKS.md#hapi-646) | HRA-041 | remove confirmed orphan types | Delete only HRA-040 DELETE candidates and update imports/tests/docs. | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-647](ULTIMATE_TASKS.md#hapi-647) | HRA-050 | classify required invariant gaps | Review domain/JPA nullability mismatches and classify true business requirements vs migration/persistence-only constraints. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-648](ULTIMATE_TASKS.md#hapi-648) | HRA-051 | enforce classified domain invariants | Single consolidated repository-wide task / single commit. Apply every HRA-050-approved invariant gap across all affected modules in one batch: required… | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-649](ULTIMATE_TASKS.md#hapi-649) | HRA-060 | classify anemic persistence mirrors | Classify the 393 behaviorless exact domain/JPA mirrors as REAL DOMAIN, READ/PERSISTENCE MODEL, or BOUNDARY MODEL. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-650](ULTIMATE_TASKS.md#hapi-650) | HRA-061 | simplify classified domain persistence mirrors | Single consolidated repository-wide task / single commit. Apply every HRA-060-approved mirror simplification across all affected modules in one batch. Preserve… | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-651](ULTIMATE_TASKS.md#hapi-651) | HRA-070 | classify duplicate boundary contracts | Review the 65 exact Request/Command and 59 exact Response/SummaryDto pairs. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-654](ULTIMATE_TASKS.md#hapi-654) | HRA-073 | simplify remaining boundary dto mappings | Consolidated execution of the remaining HRA-07x module slices, explicitly authorized as one commit: generate only the 114 still-approved exact pairs across 22… | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-655](ULTIMATE_TASKS.md#hapi-655) | HRA-080 | review duplicate enum vocabularies | Make semantic KEEP/MERGE/DELETE decisions; identical constants alone do not justify merging. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-656](ULTIMATE_TASKS.md#hapi-656) | HRA-081 | remove redundant enum vocabularies | Apply only approved merges/deletions with persistence/API compatibility handling. | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-661](ULTIMATE_TASKS.md#hapi-661) | HRA-102 | define domain enum exposure policy | Decide whether REST exposure of domain enums is an intentional wire contract; define mapping policy. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-662](ULTIMATE_TASKS.md#hapi-662) | HRA-110 | inventory scalar reference integrity | Classify non-primary entity Id fields as same-module references, cross-module stable references, historical snapshots, external IDs, or non-relational identifiers. | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-663](ULTIMATE_TASKS.md#hapi-663) | HRA-111 | enforce classified internal reference integrity | Single consolidated repository-wide task / single commit. Apply every HRA-110-approved same-module integrity repair across all affected modules using fail-closed… | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-664](ULTIMATE_TASKS.md#hapi-664) | HRA-120 | close forensic remediation baseline | Re-run the same forensic methodology and compare against the baseline. After HRA-121 and HRA-122, rerun this same closure task against the new live head; do not… | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-665](ULTIMATE_TASKS.md#hapi-665) | HRA-121 | remove permission scope domain-record exposure | Replace identity.api.rest.request.EvaluatePermissionRequest.scope : AuthorizationScope with an API-owned boundary representation for authorization scope. Preserve… | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-690](ULTIMATE_TASKS.md#hapi-690) | KER-001 | add kernel roadmap | Add this roadmap file | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-691](ULTIMATE_TASKS.md#hapi-691) | KER-002 | add kernel package skeleton | Add production package-info.java files only | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-692](ULTIMATE_TASKS.md#hapi-692) | KER-003 | add domain exception contracts | Add generic domain exceptions | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-693](ULTIMATE_TASKS.md#hapi-693) | KER-004 | add domain model contracts | Add AggregateRoot, Entity, ValueObject | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-694](ULTIMATE_TASKS.md#hapi-694) | KER-005 | add identity and tracing value objects | Add CorrelationId, RequestId, ActorId, OrganizationScopeId | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-695](ULTIMATE_TASKS.md#hapi-695) | KER-006 | add temporal range value objects | Add DateRange, TimeRange | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-696](ULTIMATE_TASKS.md#hapi-696) | KER-007 | add domain event contracts | Add DomainEvent, DomainEventId | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-697](ULTIMATE_TASKS.md#hapi-697) | KER-008 | add command and query markers | Add Command, Query | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-698](ULTIMATE_TASKS.md#hapi-698) | KER-009 | add pagination primitives | Add PageRequest, PageResult, SortDirection | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-699](ULTIMATE_TASKS.md#hapi-699) | KER-010 | add operation result primitives | Add OperationResult, ResultStatus | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-700](ULTIMATE_TASKS.md#hapi-700) | KER-011 | add API error primitives | Add ApiErrorCode, ApiErrorResponse, ValidationErrorDetail | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-701](ULTIMATE_TASKS.md#hapi-701) | KER-012 | add API response primitives | Add ApiResponse, PagedApiResponse | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-702](ULTIMATE_TASKS.md#hapi-702) | KER-013 | add kernel unit tests | Add unit tests for kernel files | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-703](ULTIMATE_TASKS.md#hapi-703) | KER-014 | add kernel architecture guardrail | Add ArchUnit boundary test if ArchUnit exists | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-704](ULTIMATE_TASKS.md#hapi-704) | KER-015 | finalize kernel checklist | Update this roadmap with final status | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-779](ULTIMATE_TASKS.md#hapi-779) | PLAT-001 | add platform roadmap | Add this roadmap file | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-780](ULTIMATE_TASKS.md#hapi-780) | PLAT-002 | add platform package skeleton | Add production package-info.java files only | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-781](ULTIMATE_TASKS.md#hapi-781) | PLAT-003 | add typed platform configuration properties | Add platform configuration properties and base config | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-782](ULTIMATE_TASKS.md#hapi-782) | PLAT-004 | add observability correlation support | Add correlation/request ID infrastructure | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-783](ULTIMATE_TASKS.md#hapi-783) | PLAT-005 | add logging context and masking support | Add logging context helpers and sensitive value masking | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-784](ULTIMATE_TASKS.md#hapi-784) | PLAT-006 | add global exception handling foundation | Add API error factory and exception handler | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-785](ULTIMATE_TASKS.md#hapi-785) | PLAT-007 | add security authentication foundation | Add Spring Security configuration and authentication error handler | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-786](ULTIMATE_TASKS.md#hapi-786) | PLAT-008 | add security context support | Add current principal and actor resolver | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-787](ULTIMATE_TASKS.md#hapi-787) | PLAT-009 | add persistence and auditing foundation | Add JPA/auditing/transaction infrastructure | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-788](ULTIMATE_TASKS.md#hapi-788) | PLAT-010 | add event serialization foundation | Add domain event serialization support | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-789](ULTIMATE_TASKS.md#hapi-789) | PLAT-011 | add outbox persistence foundation | Add outbox entity, repository, store, and migration | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-790](ULTIMATE_TASKS.md#hapi-790) | PLAT-012 | add domain event publisher foundation | Add outbox-backed domain event publisher | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-791](ULTIMATE_TASKS.md#hapi-791) | PLAT-013 | add health indicators and metrics tags | Add health indicators and platform metric tag support | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-792](ULTIMATE_TASKS.md#hapi-792) | PLAT-014 | add tenancy context foundation | Add tenant and organization scope context placeholders | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-793](ULTIMATE_TASKS.md#hapi-793) | PLAT-015 | add platform unit and slice tests | Add platform tests | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-794](ULTIMATE_TASKS.md#hapi-794) | PLAT-016 | add platform architecture guardrail | Add ArchUnit platform boundary tests | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-795](ULTIMATE_TASKS.md#hapi-795) | PLAT-017 | finalize platform checklist | Update this roadmap with final status | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-820](ULTIMATE_TASKS.md#hapi-820) | STB2-001 | add repository recovery roadmap | Establish verified recovery plan | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-821](ULTIMATE_TASKS.md#hapi-821) | STB2-002 | remove tracked build artifacts | Remove tracked bin/ and harden ignore rules | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-823](ULTIMATE_TASKS.md#hapi-823) | STB2-004 | restore architecture indexes and inventories | Repair missing architecture docs and document module/persistence inventories | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-824](ULTIMATE_TASKS.md#hapi-824) | STB2-005 | align Spring Boot 4 and repository links | Align README and POM comments with live build | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-825](ULTIMATE_TASKS.md#hapi-825) | STB2-006 | restore baseline verification tests | Restore Boot smoke test and architecture guardrails without Boot test slices | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-826](ULTIMATE_TASKS.md#hapi-826) | STB2-007 | add Maven verification workflow | Add repeatable Java 21 compile/test/verify CI | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-827](ULTIMATE_TASKS.md#hapi-827) | STB2-008 | finalize repository recovery checklist | Record prepared state and required final validation | P2 | — | NOT_VERIFIABLE | SUPERSEDED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-828](ULTIMATE_TASKS.md#hapi-828) | STB-001 | add foundation stabilization roadmap | add foundation stabilization roadmap | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-830](ULTIMATE_TASKS.md#hapi-830) | STB-003 | register rest mapper beans | register rest mapper beans | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-831](ULTIMATE_TASKS.md#hapi-831) | STB-004 | register jackson object mapper bean | register jackson object mapper bean | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-836](ULTIMATE_TASKS.md#hapi-836) | STB-009 | add explicit development security baseline | add explicit development security baseline | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-837](ULTIMATE_TASKS.md#hapi-837) | STB-010 | verify application boot baseline | verify application boot baseline | P2 | — | VERIFIED | COMPLETED |
| HPS-023 | Platform Architecture and Infrastructure | [HAPI-838](ULTIMATE_TASKS.md#hapi-838) | STB-011 | finalize foundation validation checklist | finalize foundation validation checklist | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-043](ULTIMATE_TASKS.md#hapi-043) | CI-003A | Measured critical path and execution analysis | Refresh current job/step/raw-class timings, runner CPU/RAM/forks, cache and scheduling context. | P2 | — | VERIFIED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-044](ULTIMATE_TASKS.md#hapi-044) | CI-003B | Evidence-justified CI scheduling correction | Apply only a justified bounded scheduling/resource/evidence correction if measurements require one; otherwise record no code needed. | P2 | — | VERIFIED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-045](ULTIMATE_TASKS.md#hapi-045) | CI-003C | Equivalence and negative acceptance gates | Complete representative code/security, migration and API revision equivalence, cold/warm and negative acceptance proof. | P0 | HAPI-043, HAPI-044 | PARTIAL | IN_PROGRESS |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-046](ULTIMATE_TASKS.md#hapi-046) | CI-003D | Timing, runner cost and adoption decision | Evaluate repeatable timing/cost and make the independent adoption/closure decision. | P0 | HAPI-045 | NOT_VERIFIABLE | BLOCKED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-434](ULTIMATE_TASKS.md#hapi-434) | HPR-CI-001, CI-001 | parallelize isolated test JVM execution | Run the unchanged full test suite in two reused isolated JVM forks; retain Surefire reports and count summaries for performance/coverage comparison; preserve all… | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-435](ULTIMATE_TASKS.md#hapi-435) | HPR-CI-002, CI-002 | HPR-CI-002 historical bounded obligation | HPR-CI-002 historical bounded obligation | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-436](ULTIMATE_TASKS.md#hapi-436) | HPR-CI-003, CI-003 | Integrated closure: automate fast feedback and preserve full verification gates | Independently reconcile and accept the complete child-deliverable matrix for HPR-CI-003; child tasks own implementation. automate fast feedback and preserve full… | P0 | HAPI-046 | PARTIAL | IN_PROGRESS |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-437](ULTIMATE_TASKS.md#hapi-437) | HPR-P0-001 | secure operational workbench data exposure | Re-inspect the exact-head Workbench path, replace automatic metamodel exposure with a fail-closed approved-resource policy, replace unrestricted reflective… | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-438](ULTIMATE_TASKS.md#hapi-438) | HPR-P0-002 | guard workbench sensitive data exposure | Add focused regression tests covering list, detail, filter/search and generic attribute maps; assert that credential resources and passwordHash cannot be returned. | P2 | HAPI-437 | IMPLEMENTED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-439](ULTIMATE_TASKS.md#hapi-439) | HPR-P0-003 | guard generic persistence exposure | Extend architecture/security guardrails so a future generic platform reader cannot silently introduce unrestricted module-JPA exposure outside an explicitly… | P2 | HAPI-437 | IMPLEMENTED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-440](ULTIMATE_TASKS.md#hapi-440) | HPR-P0-004 | declare openapi security requirements | Add machine-readable OpenAPI security scheme and applicable security requirements for secured endpoints without weakening runtime security. | P2 | HAPI-437 | VERIFIED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-441](ULTIMATE_TASKS.md#hapi-441) | HPR-P0-005 | clarify audit actor provenance | Revalidate caller-supplied actor-header handling; ensure it is not represented as authenticated audit identity unless a verified binding exists; correct… | P2 | HAPI-437 | VERIFIED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-442](ULTIMATE_TASKS.md#hapi-442) | HPR-P0-006 | establish canonical security baseline | Complete canonical security documents: SECURITY_ARCHITECTURE.md, TRUST_BOUNDARIES.md, THREAT_MODEL.md, SECRETS_AND_CERTIFICATES.md, and INCIDENT_RESPONSE.md, using… | P2 | HAPI-437 | VERIFIED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-443](ULTIMATE_TASKS.md#hapi-443) | HPR-P0-007 | close P0 security remediation | Run full Maven verification, architecture/security tests, database/Flyway startup verification and deterministic OpenAPI generation; record exact-head evidence and… | P2 | HAPI-437 | VERIFIED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-444](ULTIMATE_TASKS.md#hapi-444) | HPR-P0-008 | verify workbench http exposure boundary | Close audit Checks 6–7 with HTTP-level Workbench regression tests using the real controller/serialization path. Cover resource discovery, list, detail, and search;… | P2 | HAPI-437 | IMPLEMENTED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-445](ULTIMATE_TASKS.md#hapi-445) | HPR-P0-009 | require exact-head openapi evidence | Close audit Check 9 as an evidence gap, not a code reimplementation. Adjust CI triggering so canonical doc/ security/roadmap changes do not silently bypass… | P2 | HAPI-444 | IMPLEMENTED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-446](ULTIMATE_TASKS.md#hapi-446) | HPR-P0-010 | map application trust transitions | Close audit Check 11 by extending SECURITY_ARCHITECTURE.md and TRUST_BOUNDARIES.md with repository-verified API → controller → application/use-case → module… | P2 | HAPI-444 | VERIFIED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-447](ULTIMATE_TASKS.md#hapi-447) | HPR-P0-011 | extend operator threat model | Close audit Check 12 by extending THREAT_MODEL.md with existing telemetry source/point registration and operator-action abuse paths. For each threat record asset,… | P2 | HAPI-446 | VERIFIED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-448](ULTIMATE_TASKS.md#hapi-448) | HPR-P0-012 | approve secret and certificate lifecycle | Obtain owner-approved lifecycle decisions required by audit Check 13: secret-store ownership, JWT signing-secret routine/emergency rotation and invalidation… | P2 | — | VERIFIED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-449](ULTIMATE_TASKS.md#hapi-449) | HPR-P0-013 | approve incident governance model | Obtain owner-approved incident-governance decisions required by audit Check 13: incident commander/decision authority, SOC/on-call/security escalation contacts,… | P2 | — | VERIFIED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-450](ULTIMATE_TASKS.md#hapi-450) | HPR-P0-014 | operationalize security lifecycle procedures | After HPR-P0-012 and HPR-P0-013 approvals, replace the applicable TBD sections in SECRETS_AND_CERTIFICATES.md and INCIDENT_RESPONSE.md with the approved concrete… | P2 | HAPI-448 | VERIFIED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-451](ULTIMATE_TASKS.md#hapi-451) | HPR-P0-015 | close P0 audit verification gaps | Re-run the complete P0 verification gate on the exact final remediation SHA: full Maven verification, HTTP Workbench leakage tests, architecture/security tests,… | P2 | HAPI-444 | VERIFIED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-458](ULTIMATE_TASKS.md#hapi-458) | HPR-P1-007 | add production deployment runbook | Create DEPLOYMENT_RUNBOOK.md and ENVIRONMENT_CONFIGURATION.md; require explicit production profile, secrets, PostgreSQL preparation, Flyway, startup, acceptance… | P2 | HAPI-452 | VERIFIED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-463](ULTIMATE_TASKS.md#hapi-463) | HPR-P1-012 | close P1 survivability verification | Close P1 only after all repository defects identified by the 2026-10-06 re-audit are remediated and the integrated production-equivalent exercise evidence is… | P2 | HAPI-472 | VERIFIED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-464](ULTIMATE_TASKS.md#hapi-464) | HPR-P1-013 | approve production infrastructure stack | Obtain and record the concrete production infrastructure selections required to implement P1: deployment/runtime target, traffic-distribution mechanism, PostgreSQL… | P2 | — | VERIFIED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-469](ULTIMATE_TASKS.md#hapi-469) | HPR-P1-018 | add controlled deployment pipeline | Complete former HPR-P1-009 after deployment-target approval: implement controlled release promotion, approval, exact-artifact deployment, explicit… | P2 | HAPI-458, HAPI-464 | IMPLEMENTED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-470](ULTIMATE_TASKS.md#hapi-470) | HPR-P1-019 | implement production alerting | Complete former HPR-P1-010 after observability decisions: approve concrete SLIs/SLOs/evaluation windows and numeric thresholds, configure collection/dashboards/log… | P2 | HAPI-461, HAPI-464 | IMPLEMENTED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-477](ULTIMATE_TASKS.md#hapi-477) | HPR-P1-026 | wire production observability signals | Complete production observability wiring: provide an approved authenticated scrape path for protected HidraAPI metrics without exposing them publicly, enable the… | P2 | HAPI-470 | IMPLEMENTED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-491](ULTIMATE_TASKS.md#hapi-491) | HPR-P2-011 | establish verification documentation | Create test strategy, architecture testing, database testing, API testing and requirements traceability documents tied to executable evidence. | P2 | HAPI-482 | VERIFIED | COMPLETED |
| HPS-024 | Security, DevSecOps and Quality | [HAPI-492](ULTIMATE_TASKS.md#hapi-492) | HPR-P2-012 | validate canonical documentation | Add documentation validation for canonical links/status/index drift and deterministic OpenAPI contract checks. | P2 | HAPI-481 | IMPLEMENTED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-452](ULTIMATE_TASKS.md#hapi-452) | HPR-P1-001 | define production runtime architecture | Create doc/architecture/RUNTIME_ARCHITECTURE.md from approved production decisions only; do not invent deployment technology. | P2 | HAPI-451 | VERIFIED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-453](ULTIMATE_TASKS.md#hapi-453) | HPR-P1-002 | define application high availability model | Approve stateless multi-node runtime and load-distribution mechanism; document node state, readiness/liveness integration and failure behavior. | P2 | HAPI-452 | VERIFIED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-454](ULTIMATE_TASKS.md#hapi-454) | HPR-P1-003 | define postgres high availability model | Approve PostgreSQL replication/failover topology, replication mode, failover authority, connection behavior, maintenance behavior and ownership. | P2 | HAPI-452 | VERIFIED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-455](ULTIMATE_TASKS.md#hapi-455) | HPR-P1-004 | define disaster recovery objectives | Obtain owner-approved RTO/RPO, backup frequency/retention and WAL/PITR strategy; keep values TBD until approved. | P2 | — | VERIFIED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-456](ULTIMATE_TASKS.md#hapi-456) | HPR-P1-005 | add disaster recovery runbook | Create doc/operations/DISASTER_RECOVERY_RUNBOOK.md with declaration authority, recovery roles, dependency order, restore steps and acceptance checks based on… | P2 | HAPI-455 | VERIFIED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-457](ULTIMATE_TASKS.md#hapi-457) | HPR-P1-006 | add high availability architecture | Create doc/operations/HIGH_AVAILABILITY_ARCHITECTURE.md covering approved application/database redundancy, failover, connection behavior and maintenance failover. | P2 | HAPI-453 | VERIFIED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-459](ULTIMATE_TASKS.md#hapi-459) | HPR-P1-008 | enforce openapi compatibility | Add real OpenAPI compatibility/breaking-change validation; generation/upload alone is not compatibility validation. | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-460](ULTIMATE_TASKS.md#hapi-460) | HPR-P1-009 | add controlled deployment pipeline | After deployment target approval, implement controlled deployment automation, promotion, approval, post-deployment health verification and rollback; document in… | P2 | HAPI-458 | IMPLEMENTED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-461](ULTIMATE_TASKS.md#hapi-461) | HPR-P1-010 | establish observability operating model | Create OBSERVABILITY_AND_SRE.md and implement approved alerting from existing Actuator/Prometheus signals; do not invent SLOs. | P2 | HAPI-452 | VERIFIED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-462](ULTIMATE_TASKS.md#hapi-462) | HPR-P1-011 | add database operations runbook | Create DATABASE_OPERATIONS_RUNBOOK.md covering Flyway, backup/restore, connection exhaustion, failover, maintenance and migration failures. | P2 | HAPI-454 | VERIFIED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-465](ULTIMATE_TASKS.md#hapi-465) | HPR-P1-014 | enforce production startup profile | Enforce production-context startup safety so an omitted or wrong Spring profile cannot silently fall back to dev/default behavior in an approved production… | P2 | HAPI-464 | VERIFIED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-466](ULTIMATE_TASKS.md#hapi-466) | HPR-P1-015 | implement application high availability | Implement the approved two-node HidraAPI runtime on the selected deployment/traffic platform, readiness-based admission/removal, graceful drain/replacement, and… | P2 | HAPI-453, HAPI-457, HAPI-464 | VERIFIED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-467](ULTIMATE_TASKS.md#hapi-467) | HPR-P1-016 | implement postgres failover recovery | Implement the selected PostgreSQL primary/standby replication, promotion/fencing and stable-endpoint mechanism; define explicit Hikari/JDBC recovery settings… | P2 | HAPI-454, HAPI-462, HAPI-464 | IMPLEMENTED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-468](ULTIMATE_TASKS.md#hapi-468) | HPR-P1-017 | implement backup and pitr operations | Bind the approved DR model to the selected backup and storage implementation with executable backup, WAL archive, restore, recovery-target/PITR, service-control… | P2 | HAPI-455, HAPI-462, HAPI-464 | IMPLEMENTED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-471](ULTIMATE_TASKS.md#hapi-471) | HPR-P1-020 | operationalize database procedures | Make the database runbook executable for the selected PostgreSQL/backup/HA stack: add reviewed maintenance and inspection commands for sessions, locks,… | P2 | HAPI-462, HAPI-464, HAPI-467 | VERIFIED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-472](ULTIMATE_TASKS.md#hapi-472) | HPR-P1-021 | backup/PITR binding evidence | Infra/Test/Doc | P2 | — | NOT_VERIFIABLE | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-473](ULTIMATE_TASKS.md#hapi-473) | HPR-P1-022 | align patroni health checks with tls | Make HAProxy primary health checks compatible with Patroni REST mutual TLS and required client authentication; validate the rendered HAProxy/Patroni transport… | P2 | HAPI-467 | IMPLEMENTED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-474](ULTIMATE_TASKS.md#hapi-474) | HPR-P1-023 | strengthen application ha exercise | Strengthen the two-node HA exercise so it proves both nodes are initially admitted, continuously probes a representative authenticated REST operation during each… | P2 | HAPI-466 | IMPLEMENTED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-475](ULTIMATE_TASKS.md#hapi-475) | HPR-P1-024 | verify application failover recovery | Extend the database failover exercise to prove HidraAPI-managed Hikari connection replacement through the stable endpoint using authenticated database-backed… | P2 | HAPI-473 | IMPLEMENTED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-476](ULTIMATE_TASKS.md#hapi-476) | HPR-P1-025 | enforce availability preserving rollout | Harden controlled rollout so the surviving application node is verified before draining the target, drain completion/active-request safety is explicitly checked… | P2 | HAPI-469, HAPI-474 | IMPLEMENTED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-478](ULTIMATE_TASKS.md#hapi-478) | HPR-P1-027 | complete operational health inspections | Complete database health inspection coverage with PostgreSQL index validity/readiness and a reviewed bloat/space-risk method that does not assume unapproved… | P2 | HAPI-471 | IMPLEMENTED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-479](ULTIMATE_TASKS.md#hapi-479) | HPR-P1-028 | enforce p1 retention controls | Enforce the approved P1 retention controls in executable configuration: distinguish bootstrap from mature monthly coverage and verify distinct monthly repo2… | P2 | HAPI-472, HAPI-477 | IMPLEMENTED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-480](ULTIMATE_TASKS.md#hapi-480) | HPR-P1-029 | execute p1 survivability exercises | Execute and retain one governed production-equivalent P1 evidence campaign after HPR-P1-021..028: scheduled backup/WAL operation; intentional current-schema PITR… | P2 | HAPI-472 | IMPLEMENTED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-543](ULTIMATE_TASKS.md#hapi-543) | HPR-P25-031 | audit engineering foundation acceptance | audit engineering foundation acceptance | P2 | HAPI-621 | NOT_VERIFIABLE | BLOCKED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-621](ULTIMATE_TASKS.md#hapi-621) | HPR-P25-058 | Integrated closure: four integrated PLATFORM qualification packages | Independently reconcile and accept the complete child-deliverable matrix for HPR-P25-058; child tasks own implementation. four integrated PLATFORM qualification… | P2 | HAPI-625 | NOT_VERIFIABLE | BLOCKED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-622](ULTIMATE_TASKS.md#hapi-622) | HPR-P25-058A | Versioned PLATFORM fixture and criterion matrix | Freeze versioned supported fixture/case/contract/namespace matrix and exact test scope. | P2 | HAPI-604, HAPI-610, HAPI-616 | NOT_VERIFIABLE | BLOCKED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-623](ULTIMATE_TASKS.md#hapi-623) | HPR-P25-058B | Operator/API/security/history end-to-end qualification | Actual operator/API/security/history end-to-end software qualification. | P2 | HAPI-622 | NOT_VERIFIABLE | BLOCKED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-624](ULTIMATE_TASKS.md#hapi-624) | HPR-P25-058C | Integrated concurrency, replay and operational evidence | Integrated concurrency/replay/reference/rollback and observation/action qualification. | P2 | HAPI-623 | NOT_VERIFIABLE | BLOCKED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-625](ULTIMATE_TASKS.md#hapi-625) | HPR-P25-058D | Exact-source PLATFORM evidence and disposition | Exact-SHA full database/contract/dictionary/24-module evidence and PLATFORM decision. | P2 | HAPI-624 | NOT_VERIFIABLE | BLOCKED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-626](ULTIMATE_TASKS.md#hapi-626) | HPR-REL-001 | prepare 0.6.0 platform milestone | Align the project version to 0.6.0 and record the completed platform milestone; publish only the five-path release-alignment scope below; require successful… | P2 | — | IMPLEMENTED | COMPLETED |
| HPS-025 | Deployment and Operational Readiness | [HAPI-627](ULTIMATE_TASKS.md#hapi-627) | HPR-REL-002 | start 0.7.0 development | Record real v0.6.0 publication and immutable target; advance only the Maven project version to 0.7.0-SNAPSHOT; refresh release/development traceability within the… | P2 | HAPI-626 | IMPLEMENTED | COMPLETED |

## 6. Execution and governance rules

After this initial adoption, ROADMAP and PHASES are STATUS-ONLY: only Implementation Status and Planning Status cell values may change. Do not change task identity/name/deliverable, phase membership, priority, dependency, ordering, phase description or scope without explicit owner authorization for baseline revision. Record all changing technical reconciliation in ULTIMATE_TASKS.md. If new work fits an existing task, refine that definition there; unavoidable additional standalone work is proposed there as PENDING_BASELINE_APPROVAL outside the approved registry. Do not silently expand either task table.

Next refreshes exact live source/CI/governance and selects one eligible registered HAPI package; Fail repairs failed CI first; Status reports the independent dimensions; Reconcile updates technical evidence and synchronized statuses. Preserve original bounded completion and accepted source behavior. A parent closure task validates its children; it does not duplicate their implementation. Blocked specifications require only their local unresolved contract/policy/path admission, never invented industrial data. Gas-first/GZ2, versioned inputs and operator authority remain; numerical expansion/P3 stays deferred.

Publish directly to existing main with an expected-head guard and verified exact scope/parent/tree/blobs/message. No branch or PR, production data import, release, tag, deployment or OT action follows from this registry. Run appropriate checks, confirm applicable exact-SHA CI queued/started, then STOP without waiting for completion. All source-specific evidence and current next-package selection belong in ULTIMATE_TASKS.md.

## 7. Historical link compatibility

Existing inbound roadmap fragments remain resolvable below. Their source and technical content are retained in ULTIMATE_TASKS.md and the unchanged historical roadmap; they are not competing execution instructions.

<a id="051a-lifecycle-method-and-failure-policy"></a>
<a id="11-development-execution-progress--2026-10-11"></a>
<a id="2-consolidated-task-table--statuses-and-dependencies"></a>
<a id="2-governing-rules"></a>
<a id="2026-10-11--ci-643-repair-authentic-slice-b-regulator-dictionary-reconciliation"></a>
<a id="2026-10-11--execute-hpr-p25-036-station-graph-codec-and-repository-contract"></a>
<a id="2026-10-11--execute-hpr-p25-037-immutable-station-graph-persistence"></a>
<a id="2026-10-11--execute-hpr-p25-038-station-revision-publicationhistory-policy"></a>
<a id="2026-10-11--execute-hpr-p25-039-party-eligibility-export"></a>
<a id="2026-10-11--first-f1-implementation-registration-after-hpr-p25-030"></a>
<a id="2026-10-11--flexible-operator-configured-simulation-roadmap-realignment"></a>
<a id="2026-10-11--hpr-ci-002-fast-feedback-test-execution-registration-documentation-only"></a>
<a id="2026-10-11--hpr-p25-018-engineering-provisioning-and-canonical-ownership-preflight"></a>
<a id="2026-10-11--hpr-p25-019-revisioned-route-elevation-profile-and-pipe-engineering-preflight"></a>
<a id="2026-10-11--hpr-p25-020-station-and-installed-unit-engineering-provisioning-preflight"></a>
<a id="2026-10-11--hpr-p25-021-independent-manufacturer-characteristic-provisioning-preflight"></a>
<a id="2026-10-11--hpr-p25-022-multi-product-and-property-method-provisioning-preflight"></a>
<a id="2026-10-11--hpr-p25-028-authoritative-engineering-baseline-management-preflight"></a>
<a id="2026-10-11--hpr-p25-029-independent-operational-monitoring-and-human-action-evidence-preflight"></a>
<a id="2026-10-11--hpr-p25-030-independent-engineering-foundation-acceptance-design"></a>
<a id="2026-10-11--hpr-p25-033-station-graph-revision-persistence-design-registration"></a>
<a id="2026-10-11--hpr-p25-033-topology-station-graph-revision-persistence-contract-design"></a>
<a id="2026-10-11--hpr-p25-034-immutable-station-graph-revision-envelope-registration"></a>
<a id="2026-10-11--hpr-p25-035-station-graph-persistence-prerequisite-inventory-documentation-only"></a>
<a id="2026-10-11--hpr-p25-035-topology-station-graph-persistence-implementation-preflight-registration"></a>
<a id="2026-10-11--hpr-p25-037-ci-653-repair-bounded-regulator-upgrade-target"></a>
<a id="2026-10-11--hpr-p25-040-trusted-current-topology-actor-implementation"></a>
<a id="2026-10-11--hpr-p25-041-paired-scoped-topology-permission-implementation"></a>
<a id="2026-10-11--hpr-p25-042-live-facilityparty-owner-reference-implementation"></a>
<a id="2026-10-11--owner-decision-defer-simulator-and-prioritize-engineering-foundations"></a>
<a id="2026-10-11--register-hpr-p25-036-station-graph-codec-and-repository-contract"></a>
<a id="2026-10-11--register-hpr-p25-037-immutable-station-graph-postgresql-persistence"></a>
<a id="2026-10-11--register-hpr-p25-038-station-revision-publicationhistory-policy-preflight"></a>
<a id="2026-10-11--register-hpr-p25-040-trusted-topology-actor-export"></a>
<a id="2026-10-11--register-hpr-p25-041-paired-topology-scoped-permission-export"></a>
<a id="2026-10-11--register-hpr-p25-042-live-facilityparty-owner-reference-query"></a>
<a id="2026-10-11--register-hpr-p25-043-cross-owner-access-linearization-preflight"></a>
<a id="2026-10-12--execute-hpr-p25-043-cross-owner-access-linearization-design"></a>
<a id="2026-10-12--execute-hpr-p25-044-operation-authentication-provenance-policy"></a>
<a id="2026-10-12--execute-hpr-p25-045-operation-binding-storage-policy"></a>
<a id="2026-10-12--execute-hpr-p25-046-local-credential-security-generations"></a>
<a id="2026-10-12--hpr-p25-046-ci-660-repair-migration-aware-station-upgrade-gate"></a>
<a id="2026-10-12--hpr-p25-046-ci-661-repair-exact-ci-dictionary-provenance"></a>
<a id="2026-10-12--hpr-p25-047-compatibility-blocker-and-hpr-p25-048-corrective-registration"></a>
<a id="2026-10-12--hpr-p25-048-provider-and-link-jsonb-orm-correction"></a>
<a id="2026-10-12--register-hpr-p25-044-operation-authentication-provenance-preflight"></a>
<a id="2026-10-12--register-hpr-p25-045-b1-binding-and-credential-security-storage-preflight"></a>
<a id="2026-10-12--register-hpr-p25-046-local-credential-database-generation-implementation"></a>
<a id="2026-10-12--register-hpr-p25-047-provider-and-external-link-database-generations"></a>
<a id="2026-10-13--hpr-ci-003-automatic-fast-and-fail-closed-full-shadow-implementation"></a>
<a id="2026-10-13--hpr-p25-047-ci-667-dictionary-repair-and-final-main-publication"></a>
<a id="2026-10-13--hpr-p25-047-complete-implementation-candidate-and-catalog-prerequisite"></a>
<a id="2026-10-13--hpr-p25-047-resumption-blocker-and-hpr-p25-049-test-correction-registration"></a>
<a id="2026-10-13--hpr-p25-049-forward-compatible-jsonb-migration-proof"></a>
<a id="2026-10-13--hpr-p25-050-d1-exact-directory-subject-transport-correction"></a>
<a id="2026-10-13--hpr-p25-050-d1-persistent-producer-origin-amendment"></a>
<a id="2026-10-13--hpr-p25-050-d1-scope-amendment-ci-003-stays-in-progress"></a>
<a id="2026-10-13--hpr-p25-050-session-truncate-compatibility-amendment"></a>
<a id="2026-10-14--hpr-p25-050a-trusted-binding-foundations-implemented-ci-pending"></a>
<a id="2026-10-14--hpr-p25-050b-fail-repair-provider-fixtures-and-genuine-dictionary"></a>
<a id="2026-10-14--hpr-p25-050b-storage-candidate-physical-catalog-and-ci-pending"></a>
<a id="2026-10-14--hpr-p25-050c-verified-producers-and-atomic-bound-completion-candidate"></a>
<a id="2026-10-14--hpr-p25-050d-point-in-time-b1-reader-candidate"></a>
<a id="2026-10-14--hpr-p25-051-ci-repair-independent-bounded-writer-probes"></a>
<a id="2026-10-14--hpr-p25-051-d2-method-selection-and-bounded-proof-envelope"></a>
<a id="2026-10-14--hpr-p25-051-isolated-temporal-fence-proof-candidate"></a>
<a id="2026-10-14--hpr-p25-051-proof-acceptance-and-051a-production-lifecycle-scope"></a>
<a id="2026-10-14--hpr-p25-051a-production-transaction-lifecycle-candidate"></a>
<a id="2026-10-14--owner-selected-hpr-p25-050-staged-development-implementation"></a>
<a id="3-in-flight-semantic-remediation-disposition"></a>
<a id="3-remaining-task-execution-rules"></a>
<a id="4-detailed-descriptions-of-remaining-tasks"></a>
<a id="4-state-of-the-platform-gate"></a>
<a id="5-execution-registry"></a>
<a id="5-full-authoritative-hpr-p25-037-registration--preserved-verbatim"></a>
<a id="6-governance-and-history"></a>
<a id="6-immediate-next-execution"></a>
<a id="7-original-p0-closure-evidence"></a>
<a id="8-independent-p0-verification-audit--gap-reconciliation"></a>
<a id="81-disposition-by-failed-check"></a>
<a id="82-gate-semantics"></a>
<a id="9-independent-p0-re-audit-closure"></a>
<a id="acceptance-and-exact-validation"></a>
<a id="acceptance-and-next-state-control"></a>
<a id="acceptance-and-unchanged-execution-envelope"></a>
<a id="acceptance-boundary-and-disposition"></a>
<a id="acceptance-fixture-register--specifications-not-executed-tests"></a>
<a id="acceptance-identities-and-real-postgresql-boundary-parity"></a>
<a id="acceptance-tests-and-complete-verification"></a>
<a id="accepted-baseline-and-one-stage-registration"></a>
<a id="accepted-mapping-correction-and-refreshed-source"></a>
<a id="accepted-on-next-proposed-design--twelve-parts"></a>
<a id="accepted-prerequisite-and-selected-boundary"></a>
<a id="accepted-selection-verified-gates-and-responsible-scope-stop"></a>
<a id="active-foundation-acceptance-cases--independent-of-simulation"></a>
<a id="active-selection-baseline-and-registration-scope"></a>
<a id="actual-amendment-validation-and-publication-disposition"></a>
<a id="actual-checks-and-supporting-publication"></a>
<a id="actual-current-process-and-measured-evidence"></a>
<a id="actual-exact-commit-ci-and-independent-mathematical-evidence"></a>
<a id="actual-hosted-acceptance-not-audit-summary-inference"></a>
<a id="actual-implementation-and-limits"></a>
<a id="actual-local-evidence-and-required-hosted-acceptance"></a>
<a id="actual-local-observations-and-stage-disposition"></a>
<a id="actual-local-validation"></a>
<a id="actual-local-validation-1"></a>
<a id="actual-local-validation-and-its-limits"></a>
<a id="actual-local-validation-and-limits"></a>
<a id="actual-preflight-validation"></a>
<a id="actual-read-only-review-and-concrete-closure-defect"></a>
<a id="actual-supporting-validation-result"></a>
<a id="actual-validation-and-limitations"></a>
<a id="actual-validation-and-limits"></a>
<a id="actual-validation-and-publication-protocol"></a>
<a id="additional-current-source-evidence"></a>
<a id="additional-real-acceptance-in-already-selected-tests"></a>
<a id="admissible-synthetic-only-verification-coverage"></a>
<a id="admitted-implementation-validation"></a>
<a id="admitted-implementation-validation-and-publication"></a>
<a id="admitted-validation-and-completion-evidence"></a>
<a id="admitted-validation-and-publication"></a>
<a id="admitted-validation-and-publication-1"></a>
<a id="admitted-validation-proposal"></a>
<a id="all-148-flyway-lexical-dmltrigger-census"></a>
<a id="amendment-validation"></a>
<a id="amendment-validation-and-publication-boundary"></a>
<a id="amendment-validation-and-publication-evidence-boundary"></a>
<a id="amendment-validation-and-publication-evidence-boundary-1"></a>
<a id="api-persistence-and-failure-behavior-design"></a>
<a id="append-transaction-and-security-boundary"></a>
<a id="applicationapi-and-security-contract-design-gates"></a>
<a id="approved-direction-and-dynamic-input-decision-record--2026-10-09"></a>
<a id="argument-free-trusted-reader"></a>
<a id="assembly-and-graph-limits"></a>
<a id="assessment-obligations-and-enforcement"></a>
<a id="association-alternatives-and-selected-cardinalityintegrity"></a>
<a id="attempted-stage-and-exact-failure-evidence"></a>
<a id="audit-method-and-evidence-limitations"></a>
<a id="authenticated-exact-head-postgresql--maintained-generator-evidence"></a>
<a id="authenticated-human-applied-action-and-observed-outcome-contracts"></a>
<a id="authentication-contexts-and-concrete-blocker-b1"></a>
<a id="authoring-revision-and-owner-api-design"></a>
<a id="authority-scope-and-gate"></a>
<a id="authority-verified-base-and-owner-approved-boundary"></a>
<a id="authorization-and-exact-reference-head"></a>
<a id="authorization-exact-baseline-and-current-execution-gate"></a>
<a id="authorization-prerequisite-acceptance-and-non-goals"></a>
<a id="authorized-forward-migration-and-append-semantics"></a>
<a id="authorized-workflows-validation-and-negative-cases"></a>
<a id="batch-10-hmr-083--implemented-ci-pending"></a>
<a id="batch-10-hmr-095--implemented-ci-pending"></a>
<a id="batch-10-hmr-101--implemented-ci-pending"></a>
<a id="batch-10-hmr-102--implemented-ci-pending"></a>
<a id="batch-11-hmr-056--implemented-ci-pending"></a>
<a id="batch-11-hmr-071--implemented-ci-pending"></a>
<a id="batch-12-ci-586-regression-repair--2026-10-08"></a>
<a id="batch-12-ci-587-parameter-fixture-repair--2026-10-08"></a>
<a id="batch-12-hmr-057--implemented-ci-pending"></a>
<a id="batch-12-hmr-093--implemented-ci-pending"></a>
<a id="batch-13-hmr-078-implementation-result--2026-10-08"></a>
<a id="batch-13-hmr-079-implementation-result--2026-10-08"></a>
<a id="batch-15-hmr-062-implementation-result--2026-10-08"></a>
<a id="batch-15-hmr-090-implementation-result--2026-10-08"></a>
<a id="batch-15-hmr-091-implementation-result--2026-10-08"></a>
<a id="batch-15-hmr-092-implementation-result--2026-10-08"></a>
<a id="batch-16-hmr-069-implementation-result--2026-10-08"></a>
<a id="batch-16-hmr-070-implementation-result--2026-10-08"></a>
<a id="batch-16-hmr-072-implementation-result--2026-10-08"></a>
<a id="batch-17-hmr-082-implementation-result--2026-10-08"></a>
<a id="batch-17-hmr-096-implementation-result--2026-10-08"></a>
<a id="batch-17-hmr-097-implementation-result--2026-10-08"></a>
<a id="batch-18-hmr-098-implementation-result--2026-10-08"></a>
<a id="batch-2-regression-repair--hmr-052--ci-571"></a>
<a id="batch-7-ci-580-repair-implementation--2026-10-07"></a>
<a id="batch-7-hmr-055--implemented-ci-pending"></a>
<a id="batch-7-hmr-061--implemented-ci-pending"></a>
<a id="batch-7-hmr-066--implemented-ci-pending"></a>
<a id="batch-7-hmr-081--implemented-ci-pending"></a>
<a id="batch-7-hmr-081-fixture-scope-extension"></a>
<a id="batch-7-hmr-099--implemented-ci-pending"></a>
<a id="batch-8-hmr-064--implemented-ci-pending"></a>
<a id="batch-8-hmr-065--implemented-ci-pending"></a>
<a id="batch-9-hmr-067--implemented-ci-pending"></a>
<a id="batch-9-hmr-068--implemented-ci-pending"></a>
<a id="batch-9-hmr-084--implemented-ci-pending"></a>
<a id="behavioral-acceptance-and-inventory"></a>
<a id="binding-c3c-file-allowlist"></a>
<a id="binding-c3d-file-allowlist"></a>
<a id="binding-owner-clarification-use-available-data-optional-requests"></a>
<a id="binding-preflight-outputs-and-decisions-to-resolve"></a>
<a id="binding-replacement-fields-and-qualification-rules"></a>
<a id="binding-scope-and-dependency-changes-for-engineering-design-tasks"></a>
<a id="binding-target-architecture-decisions"></a>
<a id="bound-session-stability-stale-merges-and-jpa-compatibility"></a>
<a id="boundaries-acceptance-gates-and-unresolved-decisions"></a>
<a id="bounded-c-stages"></a>
<a id="bounded-c3-execution-registry"></a>
<a id="bounded-current-source-reconciliation"></a>
<a id="bounded-future-implementation-envelopes--not-executable-authorization"></a>
<a id="bounded-hpr-p25-002-implementation-stages"></a>
<a id="bounded-implementation-envelopes--not-authorized-by-this-preflight"></a>
<a id="bounded-implementation-envelopes-and-next-selection"></a>
<a id="bounded-prerequisite-decomposition-and-next-boundary"></a>
<a id="bounded-replay-and-time-rules"></a>
<a id="bounded-storage-implementation"></a>
<a id="bounded-target-behavior--not-implementation-authorization"></a>
<a id="bounded-task-selection-and-write-authorization"></a>
<a id="c1-exact-next-implementation-envelope"></a>
<a id="c2-exact-next-implementation-envelope"></a>
<a id="c3a-exact-next-implementation-envelope"></a>
<a id="c3a-meaningful-validation-and-stop-boundary"></a>
<a id="c3a-retained-exact-scope-and-requirements"></a>
<a id="c3b-exact-write-allowlist"></a>
<a id="c3c-exact-content-and-validation"></a>
<a id="c3d-candidate-629-dictionary-repair--2026-10-10"></a>
<a id="c3d-exact-engineering-parameter-content-and-validation"></a>
<a id="c3e-gates-preserved-no-implementation-allowlist"></a>
<a id="candidate-implementation-areas-and-next-selected-boundary"></a>
<a id="candidate-implementation-envelope-and-independent-acceptance"></a>
<a id="candidate-readreview-scope-for-the-next-authorization-not-a-production-write-allowlist"></a>
<a id="candidate-same-physical-transaction-owner-contract-not-yet-executable"></a>
<a id="candidate-versions-ownership-strengths-and-compatibility-traps"></a>
<a id="canonical-ag-case-to-ownertestcontract-matrix"></a>
<a id="canonical-baseline-manifest-and-revision-requirements"></a>
<a id="canonical-bytes-and-digest"></a>
<a id="canonical-drift-corrected"></a>
<a id="canonical-engineering-objects-and-invariants"></a>
<a id="canonical-ownership-and-p3-overlap-resolution"></a>
<a id="canonical-ownership-and-reference-contract"></a>
<a id="canonical-product-and-property-source-semantics-proposed-not-implemented"></a>
<a id="canonical-source-revision-and-provenance-model"></a>
<a id="canonical-station-revision-wire-contract--v1"></a>
<a id="chosen-bounded-sequence-and-paired-owner-contract"></a>
<a id="ci-003c-hq1--owner-authorized-hosted-qualification-registration-2026-10-15"></a>
<a id="ci-003c-hq1-actual-hosted-negative-probe-acceptance-checkpoint--2026-10-15"></a>
<a id="ci-003c-hq1-hosted-actual-aggregate-policy-fixture-result--2026-10-16"></a>
<a id="ci-003c-hq1-hosted-cache-service-qualification-result--2026-10-15"></a>
<a id="ci-003c-hq1-hosted-negative-probe-implementation-checkpoint--2026-10-15"></a>
<a id="ci-003c-hq1-hosted-real-aggregate-policy-fixture-qualification--2026-10-16"></a>
<a id="ci-003c-hq1-hosted-run-evidence-and-controlled-cache-amendment--2026-10-15"></a>
<a id="ci-003c-hq1-isolated-qualification-harness-implementation-checkpoint--2026-10-15"></a>
<a id="ci-589-repair-result"></a>
<a id="ci-591-repair-result"></a>
<a id="ci-592-repair-result"></a>
<a id="ci-documentation-validation-protocol-adjustment"></a>
<a id="classification-rules-and-complete-inventory-coverage"></a>
<a id="clean-delivery-sequence--not-blanket-implementation-authorization"></a>
<a id="closed-reduction-contract"></a>
<a id="closed-synthetic-acceptance-and-remaining-gates"></a>
<a id="code-backed-coverage"></a>
<a id="collector-and-transaction-contract"></a>
<a id="common-stage-and-parent-completion-rules"></a>
<a id="compact-live-task-dashboard"></a>
<a id="compatibility-and-bounded-future-implementation-envelopes"></a>
<a id="complete-affected-task-disposition-matrix"></a>
<a id="complete-implementation-design"></a>
<a id="complete-proposed-design"></a>
<a id="complete-proposed-documentation-design"></a>
<a id="complete-remaining-roadmap-inventory--preserved-and-independent-obligations"></a>
<a id="completed-transfer-and-validation"></a>
<a id="completed-work-and-historical-navigation"></a>
<a id="concrete-batch-16-execution-proposal-for-acceptance"></a>
<a id="concrete-bounded-slice-b-design-for-subsequent-owner-selection"></a>
<a id="concrete-coordinated-execution-proposal-for-acceptance"></a>
<a id="concrete-decision-proposed-for-owner-acceptance"></a>
<a id="concrete-execution-proposal-for-acceptance"></a>
<a id="concrete-newly-discovered-compile-prerequisite"></a>
<a id="concrete-proposal-for-owner-acceptance"></a>
<a id="concrete-proposal-for-owner-acceptance-1"></a>
<a id="concrete-proposed-four-commit-design"></a>
<a id="concrete-proposed-two-commit-execution-envelope"></a>
<a id="concrete-recovered-gap-and-owner-boundaries"></a>
<a id="concrete-remaining-acceptance-gates"></a>
<a id="concrete-solo-execution-proposal-for-acceptance"></a>
<a id="concrete-source-to-document-mapping"></a>
<a id="consolidated-execution-protocol"></a>
<a id="contract-and-mathematical-implementation-evidence"></a>
<a id="coordinates-elevation-chainage-and-dimensions"></a>
<a id="corrected-current-order"></a>
<a id="corrected-order-and-boundary"></a>
<a id="correction-validation-and-publication-gate"></a>
<a id="current-authority-live-baseline-and-selected-scope"></a>
<a id="current-authorized-document-alignment-and-stop"></a>
<a id="current-authorized-document-update-and-stop"></a>
<a id="current-commit-scope-and-stop"></a>
<a id="current-development-facts-and-supersession"></a>
<a id="current-disposition"></a>
<a id="current-disposition-and-preflight-validation"></a>
<a id="current-disposition-and-preflight-validation-1"></a>
<a id="current-disposition-and-preflight-validation-2"></a>
<a id="current-disposition-and-stop-boundary"></a>
<a id="current-disposition-and-validation"></a>
<a id="current-documentation-registration-and-stop"></a>
<a id="current-documentation-validation-and-stop-condition"></a>
<a id="current-evidence-recovered"></a>
<a id="current-execution-checkpoint--051f-ci-accepted-051g-evidence-reader-registered-2026-10-15"></a>
<a id="current-execution-checkpoint--44-package-specification-review-results-2026-10-15"></a>
<a id="current-execution-checkpoint--hpr-ci-003--ci-003a-bounded-measurement-accepted-2026-10-15"></a>
<a id="current-execution-checkpoint--hpr-ci-003--ci-003a-measured-evidence-registration-2026-10-15"></a>
<a id="current-execution-checkpoint--hpr-ci-003--ci-003b-no-code-needed-disposition-2026-10-15"></a>
<a id="current-execution-checkpoint--hpr-ci-003--ci-003c-authenticated-historical-evidence-recovery-2026-10-15"></a>
<a id="current-execution-checkpoint--hpr-ci-003--ci-003c-bounded-archive-sweep-completed-2026-10-15"></a>
<a id="current-execution-checkpoint--hpr-ci-003--ci-003c-evidence-preflight-registration-2026-10-15"></a>
<a id="current-execution-checkpoint--hpr-ci-003-2026-10-13"></a>
<a id="current-execution-checkpoint--hpr-ci-003-oracle-resource-candidate-2026-10-13"></a>
<a id="current-execution-checkpoint--hpr-p25-050-d1-persistent-origin-correction-2026-10-13"></a>
<a id="current-execution-checkpoint--hpr-p25-050-d1-transport-correction-2026-10-13"></a>
<a id="current-execution-checkpoint--hpr-p25-050-session-truncate-compatibility-2026-10-13"></a>
<a id="current-execution-checkpoint--hpr-p25-050-staged-development-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-050a-foundations-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-050b-fail-repair-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-050c-candidate-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-050d-b1-reader-candidate-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051-complete-identity-dependency-fence-registration-2026-10-15"></a>
<a id="current-execution-checkpoint--hpr-p25-051-complete-migration-lexical-census-and-temporal-expression-gate-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051-d2-expanded-contributorwriter-census-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051-d2-fixture-repair-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051-d2-native-owner-compatibility-test-candidate-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051-d2-native-owner-lock-proof-test-registration-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051-d2-native-owner-test-acceptance-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051-d2-owner-source-census-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051-d2-proof-selection-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051-d2-relationwriter-matrix-and-protected-owner-contract-gate-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051-isolated-proof-candidate-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051-native-writer-and-trigger-audit-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051-proof-ci-repair-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051a-implementation-candidate-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051a-lifecycle-scope-registration-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051b-ci-acceptance-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051b-static-expression-compiler-registration-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051b-static-syntax-compiler-candidate-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051c-ci-acceptance-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051c-supplied-temporal-boundary-candidate-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051c-temporal-boundary-reduction-registration-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051d-assertion-import-repair-2026-10-15"></a>
<a id="current-execution-checkpoint--hpr-p25-051d-ci-acceptance-2026-10-15"></a>
<a id="current-execution-checkpoint--hpr-p25-051d-direct-grant-fixture-repair-2026-10-15"></a>
<a id="current-execution-checkpoint--hpr-p25-051d-fenced-identity-event-collector-candidate-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051d-identity-temporal-event-collector-registration-2026-10-14"></a>
<a id="current-execution-checkpoint--hpr-p25-051d-overflow-exception-chain-proof-repair-2026-10-15"></a>
<a id="current-execution-checkpoint--hpr-p25-051e-ci-acceptance-2026-10-15"></a>
<a id="current-execution-checkpoint--hpr-p25-051e-complete-identity-fence-candidate-2026-10-15"></a>
<a id="current-execution-checkpoint--hpr-p25-051f-failed-ci-repair-2026-10-15"></a>
<a id="current-execution-checkpoint--hpr-p25-051f-implemented-candidate-2026-10-15"></a>
<a id="current-execution-checkpoint--hpr-p25-051f-protected-b1-participant-registration-2026-10-15"></a>
<a id="current-execution-checkpoint--hpr-p25-051g-ci-699-failed-test-repair-2026-10-15"></a>
<a id="current-execution-checkpoint--hpr-p25-051g-fenced-evidence-implementation-candidate-2026-10-15"></a>
<a id="current-execution-checkpoint--hpr-p25-051g-formally-accepted-2026-10-15"></a>
<a id="current-execution-checkpoint--owner-approved-051g051m-finalization-sequence-2026-10-15"></a>
<a id="current-execution-checkpoint--remaining-parent-task-delivery-plans-2026-10-15"></a>
<a id="current-execution-roadmap--universal-hidra-platform-2026-10-11"></a>
<a id="current-registration-and-boundary"></a>
<a id="current-registration-and-stop"></a>
<a id="current-registration-and-stop-1"></a>
<a id="current-registration-scope-and-stop"></a>
<a id="current-source-and-chosen-bounded-strategy"></a>
<a id="current-source-and-engineering-boundary"></a>
<a id="current-source-evidence-and-honest-qualification-meaning"></a>
<a id="current-source-evidence-and-non-invented-capability-boundary"></a>
<a id="current-source-findings"></a>
<a id="current-source-findings-and-evidence-anchors"></a>
<a id="current-stage-and-bounded-acceptance"></a>
<a id="current-status-and-next-task"></a>
<a id="current-task-disposition-and-next-selection-boundary"></a>
<a id="current-validation-and-concrete-drift-recovered"></a>
<a id="current-validation-and-next-control"></a>
<a id="current-validation-publication-and-stop"></a>
<a id="d1-amendment-validation-and-limitations"></a>
<a id="d1-selected-implementation-contracts"></a>
<a id="database-security-generations-and-ordinary-writer-enforcement"></a>
<a id="decision-and-provenance"></a>
<a id="decision-gates-and-readiness-limits"></a>
<a id="decision-register--no-fabricated-operational-policy"></a>
<a id="deferred-mandatory-gates-and-present-disposition"></a>
<a id="deferred-numerical-consumption-and-future-compatibility"></a>
<a id="deferred-obligations-and-present-disposition"></a>
<a id="deliberate-slice-b-boundary-and-acceptance-gate"></a>
<a id="dependency-and-compatibility-design"></a>
<a id="dependency-graph-and-hidden-edge-disposition"></a>
<a id="dependency-handoff-and-scope-preservation"></a>
<a id="dependency-realignment-and-foundation-milestones"></a>
<a id="development-consolidation--authoritative-prospective-register-2026-10-13"></a>
<a id="development-roadmap-baseline-and-exceptional-change-control"></a>
<a id="disposition-and-next-authorized-gate"></a>
<a id="disposition-and-next-boundary"></a>
<a id="disposition-and-next-concrete-action"></a>
<a id="disposition-and-stop-boundary"></a>
<a id="disposition-and-what-follows-concretely"></a>
<a id="documentation-publication"></a>
<a id="dynamic-data-lifecycle"></a>
<a id="engine-work-and-phase-25-position"></a>
<a id="engineering-data-persistence-and-provisioning-audit"></a>
<a id="evidence-and-scope-of-owner-next"></a>
<a id="evidence-classes-and-acceptance-rules"></a>
<a id="exact-authorized-later-implementation-file-set"></a>
<a id="exact-b2-implementation-envelope-for-a-later-owner-next"></a>
<a id="exact-b4-implementation-envelope-for-a-later-owner-next"></a>
<a id="exact-boundary-and-stop-rule"></a>
<a id="exact-bounded-amendment-and-resulting-implementation-scope"></a>
<a id="exact-closed-allowlist-for-a-separately-selected-slice-a-implementation"></a>
<a id="exact-contract-adapter-and-missing-input-semantics"></a>
<a id="exact-current-evidence-and-mutation-inventory"></a>
<a id="exact-database-contract-from-accepted-area-b"></a>
<a id="exact-database-contract-to-implement"></a>
<a id="exact-design-output-ownership-and-gates"></a>
<a id="exact-evaluated-relation-to-writer-matrix-current-production-evidence"></a>
<a id="exact-evidence-recovery-and-reproduction"></a>
<a id="exact-executable-class--ci-ownership-inventory"></a>
<a id="exact-future-051a-scope-and-acceptance"></a>
<a id="exact-future-051c-scope"></a>
<a id="exact-future-051d-implementation"></a>
<a id="exact-future-hpr-p25-051e-implementation"></a>
<a id="exact-future-hpr-p25-051f-scope"></a>
<a id="exact-future-hpr-p25-051g-scope"></a>
<a id="exact-future-implementation-allowlist-and-owner-contracts"></a>
<a id="exact-future-implementation-envelope"></a>
<a id="exact-historical-access-ownership-epochs-and-disclosure"></a>
<a id="exact-implementation-write-allowlist"></a>
<a id="exact-independent-hmr-080-write-scope"></a>
<a id="exact-later-b3-implementation-envelope"></a>
<a id="exact-next-proof-scope-acceptance-and-stop"></a>
<a id="exact-next-step-rule"></a>
<a id="exact-outstanding-numerical-acceptance-gaps"></a>
<a id="exact-owner-ports-and-public-query-boundary"></a>
<a id="exact-per-child-scopes"></a>
<a id="exact-publication-boundary"></a>
<a id="exact-scope-and-publication-stop"></a>
<a id="exact-selection-and-verification"></a>
<a id="exact-source-evidence-index"></a>
<a id="exact-source-gap-and-ownership-matrix"></a>
<a id="exact-source-observations-and-unresolved-qualification-boundary"></a>
<a id="exact-source-prerequisites-and-bounded-selection"></a>
<a id="exact-time-expression-source-evidence-and-conservative-d2-proposal"></a>
<a id="exclusive-future-hpr-p25-050-implementation-allowlist"></a>
<a id="exclusive-future-implementation-write-allowlist"></a>
<a id="executed-disposition-and-stop"></a>
<a id="executed-hpr-p25-018-validation"></a>
<a id="executed-hpr-p25-019-validation-and-publication-boundary"></a>
<a id="executed-proof-and-acceptance-boundary"></a>
<a id="execution-disposition"></a>
<a id="execution-order-exact-messages-and-acceptance-state"></a>
<a id="execution-registry"></a>
<a id="exercised-mechanics-and-explicit-lifecycle-boundary"></a>
<a id="exhaustive-future-implementation-write-scope--21-paths"></a>
<a id="exhaustive-future-implementation-write-scope--37-paths"></a>
<a id="exhaustive-future-implementation-write-scope--nine-paths"></a>
<a id="exhaustive-future-implementation-write-scope--ten-paths"></a>
<a id="exhaustive-future-implementation-write-scope--thirteen-existing-paths"></a>
<a id="exhaustive-hmr-058-scope"></a>
<a id="exhaustive-hmr-062-scope"></a>
<a id="exhaustive-hmr-077-scope"></a>
<a id="exhaustive-hmr-078-write-scope"></a>
<a id="exhaustive-hmr-079-write-scope"></a>
<a id="exhaustive-hmr-090-scope"></a>
<a id="exhaustive-hmr-091-scope"></a>
<a id="exhaustive-hmr-092-scope"></a>
<a id="exhaustive-proposed-hmr-094-scope"></a>
<a id="exhaustive-proposed-hmr-098-write-scope"></a>
<a id="exhaustive-proposed-hmr-103-scope"></a>
<a id="exhaustive-revised-future-implementation-scope--sixteen-existing-paths"></a>
<a id="existing-hpr-identity-disposition-inventory"></a>
<a id="existing-requirement-selection-and-accepted-baseline"></a>
<a id="existing-requirement-served-selected-stage-and-baseline"></a>
<a id="explicit-acceptance-boundary-and-residual-obligations"></a>
<a id="explicit-future-file-candidates--not-an-implementation-allowlist"></a>
<a id="externalreference-provider-research-disposition--checked-2026-10-11"></a>
<a id="f1-02--govern-revision-history-and-publication"></a>
<a id="f1-03--expose-configurable-topology-and-authorized-apis"></a>
<a id="f1-04--qualify-universal-topology"></a>
<a id="f2-01--configure-product-and-property-methods"></a>
<a id="f2-02--manage-equipment-characteristics"></a>
<a id="f2-03--persist-immutable-engineering-history"></a>
<a id="f2-04--freeze-engineering-baselines"></a>
<a id="f3-01--configure-telemetry-sourcetag-bindings"></a>
<a id="f3-02--enforce-qualityfreshness-and-replay"></a>
<a id="f3-03--capture-independent-monitoring-evidence"></a>
<a id="f3-04--separate-decisions-from-outcomes"></a>
<a id="f4-01--implement-ai-acceptance-matrix"></a>
<a id="f4-02--verify-end-to-end-platform-quality"></a>
<a id="f4-03--independently-close-platform-gate"></a>
<a id="final-exact-file-implementation-allowlist-and-outstanding-regulator-obligation"></a>
<a id="final-p0-closure-evidence"></a>
<a id="final-required-verdict"></a>
<a id="final-tree-validation-and-truthful-limits"></a>
<a id="findings-that-materially-prevent-executable-readiness"></a>
<a id="fixed-mathematical-acceptance-and-ci-gates"></a>
<a id="focused-tests-and-validation-gates"></a>
<a id="focused-validation-and-mandatory-deferred-gates"></a>
<a id="forward-migration-authorization-proposed"></a>
<a id="forward-migration-design-and-historical-transition"></a>
<a id="forward-migration-freshupgrade-and-rollout-disposition"></a>
<a id="forward-migrations-and-api"></a>
<a id="four-delivery-outcomes-and-acceptance-evidence"></a>
<a id="frozen-evidence-and-non-negotiable-acceptance"></a>
<a id="frozen-g-contract-and-limits"></a>
<a id="frozen-independent-reference-cases-and-tolerances"></a>
<a id="future-acceptance-design--cases-fg-and-negatives-not-executed"></a>
<a id="future-closure-metadata-allowlist--fifteen-existing-paths"></a>
<a id="future-evidence-matrix-not-performed"></a>
<a id="future-implementation-acceptance-gates-present-disposition"></a>
<a id="future-positivenegative-acceptance-design-not-executed"></a>
<a id="future-validation-and-publication-gates"></a>
<a id="future-validation-and-stop-boundary"></a>
<a id="future-work-partition-no-java-authorization-granted"></a>
<a id="g-analysis-retained-as-implementation-constraints"></a>
<a id="gates-exclusions-and-next-permissible-selection"></a>
<a id="genuine-postgresql-acceptance-identities"></a>
<a id="governance-and-stop"></a>
<a id="governed-release-preparation--separate-from-p3"></a>
<a id="graph-and-configuration-semantics"></a>
<a id="hidraapi--ultimate-universal-platform-roadmap"></a>
<a id="hidraapi-ultimate-platform-finalization-roadmap"></a>
<a id="hist-req--unresolved-historical-requirements"></a>
<a id="historical-and-frontend-preservation"></a>
<a id="hmr-054-ci-574-guardrail-inventory-correction"></a>
<a id="hmr-055-additional-exact-allowlist"></a>
<a id="hmr-056-admitted-files"></a>
<a id="hmr-061-additional-exact-allowlist"></a>
<a id="hmr-064-admitted-files"></a>
<a id="hmr-065-admitted-files"></a>
<a id="hmr-066-additional-exact-allowlist"></a>
<a id="hmr-067-exact-files"></a>
<a id="hmr-068-exact-files"></a>
<a id="hmr-069-proposed-scope"></a>
<a id="hmr-070-proposed-scope"></a>
<a id="hmr-071-admitted-files"></a>
<a id="hmr-072-proposed-scope"></a>
<a id="hmr-077-implementation--owner-validated-risk-evidence"></a>
<a id="hmr-081-additional-exact-allowlist"></a>
<a id="hmr-082-proposed-scope"></a>
<a id="hmr-083-admitted-files"></a>
<a id="hmr-084-exact-files"></a>
<a id="hmr-095-admitted-files"></a>
<a id="hmr-096-proposed-scope"></a>
<a id="hmr-097-proposed-scope"></a>
<a id="hmr-099-additional-exact-allowlist"></a>
<a id="hmr-100--fixalarm-remediate-semantic-review-alarm"></a>
<a id="hmr-101-admitted-files"></a>
<a id="hmr-102-admitted-files"></a>
<a id="hmr-104--fixalarm-remediate-semantic-review-alarmacknowledgement"></a>
<a id="hmr-105--fixalarm-remediate-semantic-review-alarmclosure"></a>
<a id="hmr-106--fixalarm-remediate-semantic-review-alarmshelving"></a>
<a id="hmr-57-admitted-paths"></a>
<a id="hmr-93-admitted-paths"></a>
<a id="hpr-ci-001-verified-optimization-result--2026-10-09"></a>
<a id="hpr-ci-003--closed-implementation-scope-and-definition-of-done"></a>
<a id="hpr-ci-003--four-remaining-qualification-packages"></a>
<a id="hpr-ci-003-empirical-postgresql-partition-refresh--2026-10-13-candidate-only"></a>
<a id="hpr-ci-003-greenfield-only-routine-migration-test-policy--2026-10-13"></a>
<a id="hpr-ci-003-hosted-qualification-checkpoint--2026-10-13-five-runs-not-closed"></a>
<a id="hpr-p1-001-runtime-architecture-evidence"></a>
<a id="hpr-p1-002-application-ha-decision-evidence"></a>
<a id="hpr-p1-003-postgresql-ha-decision-evidence"></a>
<a id="hpr-p1-004-disaster-recovery-objective-evidence"></a>
<a id="hpr-p1-005-disaster-recovery-runbook-evidence"></a>
<a id="hpr-p1-006-high-availability-architecture-evidence"></a>
<a id="hpr-p1-007-production-deployment-documentation-evidence"></a>
<a id="hpr-p1-008-openapi-compatibility-evidence"></a>
<a id="hpr-p1-010-observabilitysre-evidence"></a>
<a id="hpr-p1-011-database-operations-runbook-evidence"></a>
<a id="hpr-p1-013-production-infrastructure-decision-evidence"></a>
<a id="hpr-p1-014-production-startup-safety-evidence"></a>
<a id="hpr-p1-015-application-ha-implementation-evidence"></a>
<a id="hpr-p1-016-postgresql-failover-implementation-evidence"></a>
<a id="hpr-p1-017-backupwalpitr-implementation-evidence"></a>
<a id="hpr-p1-017-exact-head-verification-evidence"></a>
<a id="hpr-p1-017-monthly-retention-binding-evidence"></a>
<a id="hpr-p1-017-retention-binding-repair-evidence"></a>
<a id="hpr-p1-018-controlled-release-implementation-evidence"></a>
<a id="hpr-p1-019-production-alerting-implementation-evidence"></a>
<a id="hpr-p1-020-database-operations-implementation-evidence"></a>
<a id="hpr-p1-021-backuppitr-binding-evidence"></a>
<a id="hpr-p1-022-patronihaproxy-tls-alignment-evidence"></a>
<a id="hpr-p1-023-application-ha-exercise-hardening-evidence"></a>
<a id="hpr-p1-024-application-failover-recovery-evidence"></a>
<a id="hpr-p1-025-availability-preserving-rollout-evidence"></a>
<a id="hpr-p1-026-observability-wiring-evidence"></a>
<a id="hpr-p1-027-database-operations-completion-evidence"></a>
<a id="hpr-p1-028-retention-enforcement-evidence"></a>
<a id="hpr-p1-029-completion-evidence--2026-10-06"></a>
<a id="hpr-p1-029-initial-blocked-execution-evidence--2026-10-06-historical"></a>
<a id="hpr-p2-005-api-correction-implementation--2026-10-09"></a>
<a id="hpr-p2-005-bounded-correction--audit-check-9"></a>
<a id="hpr-p2-005-concrete-api-correction-preflight--2026-10-09"></a>
<a id="hpr-p2-006-bounded-correction--audit-checks-10-and-11"></a>
<a id="hpr-p2-006-concrete-database-correction-preflight--2026-10-09"></a>
<a id="hpr-p2-006-stage-a-implementation--2026-10-09"></a>
<a id="hpr-p2-006-stage-b-implementation--2026-10-09"></a>
<a id="hpr-p2-006-verification-and-renewed-hpr-p2-013-preflight--2026-10-09"></a>
<a id="hpr-p2-008-batch-10-accepted-policy-and-exact-scope--2026-10-07"></a>
<a id="hpr-p2-008-batch-10-final-disposition--2026-10-07"></a>
<a id="hpr-p2-008-batch-10-preflight-split--2026-10-07"></a>
<a id="hpr-p2-008-batch-11-accepted-resolver-and-exact-scope--2026-10-07"></a>
<a id="hpr-p2-008-batch-11-final-disposition--2026-10-07"></a>
<a id="hpr-p2-008-batch-11-preflight-split--2026-10-07"></a>
<a id="hpr-p2-008-batch-12-accepted-execution-and-exact-scope--2026-10-07"></a>
<a id="hpr-p2-008-batch-12-final-disposition--2026-10-07"></a>
<a id="hpr-p2-008-batch-12-reporting-prerequisite-preflight--2026-10-07"></a>
<a id="hpr-p2-008-batch-13-accepted-execution-envelope--2026-10-08"></a>
<a id="hpr-p2-008-batch-13-ci-589-migration-fixture-repair--2026-10-08"></a>
<a id="hpr-p2-008-batch-13-simulation-prerequisite-preflight--2026-10-08"></a>
<a id="hpr-p2-008-batch-14--ci-591-repair-admission--2026-10-08"></a>
<a id="hpr-p2-008-batch-14--ci-592-compatibility-base-repair--2026-10-08"></a>
<a id="hpr-p2-008-batch-14-accepted-execution-envelope--2026-10-08"></a>
<a id="hpr-p2-008-batch-14-implementation-result--2026-10-08"></a>
<a id="hpr-p2-008-batch-14-risk-prerequisite-preflight--2026-10-08"></a>
<a id="hpr-p2-008-batch-15-accepted-execution-envelope--2026-10-08"></a>
<a id="hpr-p2-008-batch-15-ci-594-repair-admission--2026-10-08"></a>
<a id="hpr-p2-008-batch-15-final-implementation-disposition--2026-10-08"></a>
<a id="hpr-p2-008-batch-15-incident-execution-preflight--2026-10-08"></a>
<a id="hpr-p2-008-batch-16-accepted-execution-envelope--2026-10-08"></a>
<a id="hpr-p2-008-batch-16-execution-preflight--2026-10-08"></a>
<a id="hpr-p2-008-batch-16-final-implementation-disposition--2026-10-08"></a>
<a id="hpr-p2-008-batch-17-accepted-execution-envelope--2026-10-08"></a>
<a id="hpr-p2-008-batch-17-hse-execution-preflight--2026-10-08"></a>
<a id="hpr-p2-008-batch-18-accepted-execution-envelope--2026-10-08"></a>
<a id="hpr-p2-008-batch-18-integritycase-execution-preflight--2026-10-08"></a>
<a id="hpr-p2-008-batch-19-accepted-execution--hmr-094--2026-10-08"></a>
<a id="hpr-p2-008-batch-19-ci-599-repair--hmr-094--2026-10-08"></a>
<a id="hpr-p2-008-batch-19-ci-closure-and-batch-20-execution-preflight--2026-10-08"></a>
<a id="hpr-p2-008-batch-19-independent-execution--hmr-103--2026-10-08"></a>
<a id="hpr-p2-008-batch-19-planning-targets-and-monitoring-execution-preflight--2026-10-08"></a>
<a id="hpr-p2-008-batch-20-acceptance--2026-10-08"></a>
<a id="hpr-p2-008-batch-20-ci-601-repair-admission--2026-10-08"></a>
<a id="hpr-p2-008-batch-20-confirmation-and-nomination-ownership-preflight--2026-10-08"></a>
<a id="hpr-p2-008-batch-20-independent-execution--hmr-100--2026-10-08"></a>
<a id="hpr-p2-008-batch-20-independent-execution--hmr-104--2026-10-08"></a>
<a id="hpr-p2-008-batch-20-independent-execution--hmr-105--2026-10-08"></a>
<a id="hpr-p2-008-batch-20-independent-execution--hmr-106--2026-10-08"></a>
<a id="hpr-p2-008-batch-5-execution-admission--2026-10-06"></a>
<a id="hpr-p2-008-batch-5-execution-result--2026-10-06"></a>
<a id="hpr-p2-008-batch-6-ci-577-repair--hmr-085--2026-10-06"></a>
<a id="hpr-p2-008-batch-6-ci-578-repair--2026-10-06"></a>
<a id="hpr-p2-008-batch-6-implementation--2026-10-06"></a>
<a id="hpr-p2-008-batch-6-preflight-disposition--hmr-085--2026-10-06"></a>
<a id="hpr-p2-008-batch-6-scope-admission--hmr-085--2026-10-06"></a>
<a id="hpr-p2-008-batch-7-ci-580-guardrail-repair-admission--2026-10-07"></a>
<a id="hpr-p2-008-batch-7-contract-and-scope-admission--2026-10-06"></a>
<a id="hpr-p2-008-batch-7-implementation-and-validation-disposition--2026-10-06"></a>
<a id="hpr-p2-008-batch-7-preflight-disposition--2026-10-06"></a>
<a id="hpr-p2-008-batch-8-contract-and-scope-admission--2026-10-07"></a>
<a id="hpr-p2-008-batch-8-final-disposition--2026-10-07"></a>
<a id="hpr-p2-008-batch-8-preflight-split--2026-10-07"></a>
<a id="hpr-p2-008-batch-9-accepted-contract-and-exact-scope--2026-10-07"></a>
<a id="hpr-p2-008-batch-9-final-disposition--2026-10-07"></a>
<a id="hpr-p2-008-batch-9-preflight-split--2026-10-07"></a>
<a id="hpr-p2-008-final-reconciliation-preflight--2026-10-09"></a>
<a id="hpr-p2-008-owner-authorized-closure--2026-10-09"></a>
<a id="hpr-p2-008-solo-execution-admission--hmr-053--2026-10-06"></a>
<a id="hpr-p2-008-solo-execution-admission--hmr-054--2026-10-06"></a>
<a id="hpr-p2-008-solo-hmr-080-ci-603-repair--2026-10-08"></a>
<a id="hpr-p2-008-solo-hmr-080-ci-confirmation--2026-10-09"></a>
<a id="hpr-p2-008-solo-hmr-080-execution-acceptance--2026-10-08"></a>
<a id="hpr-p2-008-solo-hmr-080-implementation--2026-10-08"></a>
<a id="hpr-p2-008-solo-hmr-080-ownership-acceptance-and-execution-preflight--2026-10-08"></a>
<a id="hpr-p2-009-accepted-execution--2026-10-09"></a>
<a id="hpr-p2-009-semantic-canonicalization-preflight--2026-10-09"></a>
<a id="hpr-p2-010-accepted-execution--2026-10-09"></a>
<a id="hpr-p2-010-completed-governance-baseline--2026-10-09"></a>
<a id="hpr-p2-010-data-governance-preflight--2026-10-09"></a>
<a id="hpr-p2-011-accepted-execution--2026-10-09"></a>
<a id="hpr-p2-011-completed-verification-documentation--2026-10-09"></a>
<a id="hpr-p2-011-verification-documentation-preflight--2026-10-09"></a>
<a id="hpr-p2-012-accepted-execution--2026-10-09"></a>
<a id="hpr-p2-012-actual-implementation-checks--2026-10-09"></a>
<a id="hpr-p2-012-canonical-validation-preflight--2026-10-09"></a>
<a id="hpr-p2-013-accepted-implementation--2026-10-09"></a>
<a id="hpr-p2-013-actual-implementation-validation"></a>
<a id="hpr-p2-013-canonical-governance-closure-preflight--2026-10-09"></a>
<a id="hpr-p2-013-closure-scope-re-preflight--2026-10-09"></a>
<a id="hpr-p2-013-final-exact-head-verification--2026-10-09"></a>
<a id="hpr-p2-013-renewed-audit-and-closure-metadata-implementation--2026-10-09"></a>
<a id="hpr-p2-013-renewed-closure-gate"></a>
<a id="hpr-p2-013-renewed-final-exact-head-verification--2026-10-09"></a>
<a id="hpr-p25-001-requirements-and-execution-preflight--2026-10-09"></a>
<a id="hpr-p25-002-consolidated-delivery-plan--2026-10-09"></a>
<a id="hpr-p25-002-input-contract-execution-preflight--2026-10-09"></a>
<a id="hpr-p25-002-network-candidate-dictionary-regeneration--2026-10-09"></a>
<a id="hpr-p25-002-network-candidate-java-21-validation--2026-10-09"></a>
<a id="hpr-p25-002-network-candidate-postgresql-evidence--2026-10-09"></a>
<a id="hpr-p25-002-network-revision-delivery-publication--2026-10-10"></a>
<a id="hpr-p25-002-shared-fluid-equipment-delivery-preflight--2026-10-10"></a>
<a id="hpr-p25-002-shared-fluid-equipment-delivery-preflight--characteristic-provenance-correction--2026-10-10"></a>
<a id="hpr-p25-002-shared-network-revision-delivery-preflight--2026-10-09"></a>
<a id="hpr-p25-002-shared-operating-state-delivery-preflight--2026-10-10"></a>
<a id="hpr-p25-002a-exact-implementation-envelope"></a>
<a id="hpr-p25-002a-immutable-input-manifest-implementation--2026-10-09"></a>
<a id="hpr-p25-002b-physical-input-payload-preflight--2026-10-09"></a>
<a id="hpr-p25-002b-physical-payload-preflight--2026-10-09"></a>
<a id="hpr-p25-002b1-exact-next-implementation-envelope"></a>
<a id="hpr-p25-002b1-physical-network-input-implementation--2026-10-09"></a>
<a id="hpr-p25-002b2-fluid-and-equipment-input-implementation--2026-10-09"></a>
<a id="hpr-p25-002b2-fluid-and-equipment-input-preflight--2026-10-09"></a>
<a id="hpr-p25-002b3-state-and-boundary-timeline-implementation--2026-10-09"></a>
<a id="hpr-p25-002b3-state-and-boundary-timeline-preflight--2026-10-09"></a>
<a id="hpr-p25-002b4-consistent-physical-payload-implementation--2026-10-09"></a>
<a id="hpr-p25-002b4-consistent-physical-payload-preflight--2026-10-09"></a>
<a id="hpr-p25-002c-owner-query-and-adapter-preflight--2026-10-09"></a>
<a id="hpr-p25-002c1-r1-failed-ci-export-registry-repair--2026-10-09"></a>
<a id="hpr-p25-002c1-trusted-reading-evidence-implementation--2026-10-09"></a>
<a id="hpr-p25-002c2-owner-eligibility-evidence-implementation--2026-10-09"></a>
<a id="hpr-p25-002c2-topology-and-product-evidence-preflight--2026-10-09"></a>
<a id="hpr-p25-002c3-qualified-physical-source-preflight--2026-10-09"></a>
<a id="hpr-p25-002c3a-local-candidate--2026-10-09"></a>
<a id="hpr-p25-002c3b-network-delivery-candidate--2026-10-09"></a>
<a id="hpr-p25-002c3c-accepted-gas-fluid-revision-delivery--2026-10-10"></a>
<a id="hpr-p25-002c3c-actual-postgresql-candidate-evidence--2026-10-10"></a>
<a id="hpr-p25-002c3c-fluid-source-candidate--2026-10-10"></a>
<a id="hpr-p25-002c3d-accepted-equipment-parameter-delivery--2026-10-10"></a>
<a id="hpr-p25-002c3d-equipment-parameter-revision-candidate--2026-10-10"></a>
<a id="hpr-p25-002c3e--synthetic-fixture-to-contract-specification-2026-10-10"></a>
<a id="hpr-p25-002c3e-delivery-contract--blocked-implementation-authorization"></a>
<a id="hpr-p25-002c3e-synthetic-gz4-fixture-evidence-admission--2026-10-10"></a>
<a id="hpr-p25-008a--exact-design-document-allowlist-resolution-2026-10-10"></a>
<a id="hpr-p25-008a--internal-gas-numerical-design-execution-preflight-2026-10-10"></a>
<a id="hpr-p25-008a--numerical-design-delivery-2026-10-10"></a>
<a id="hpr-p25-008a-next-design-envelope-requires-separate-owner-next-after-passing-documentation-ci"></a>
<a id="hpr-p25-008b--acceptance-remediation-execution-preflight-2026-10-10"></a>
<a id="hpr-p25-008b--connected-synthetic-gas-network-remediation-delivery-2026-10-10"></a>
<a id="hpr-p25-008b--exact-javatest-write-allowlist-authorization-2026-10-10"></a>
<a id="hpr-p25-008b--post-repair-acceptance-adequacy-review-2026-10-10"></a>
<a id="hpr-p25-008b--remediation-exact-file-and-independent-benchmark-authorization-2026-10-10"></a>
<a id="hpr-p25-008b--steady-state-gas-solver-execution-preflight-2026-10-10"></a>
<a id="hpr-p25-008b--verified-synthetic-numerical-acceptance-2026-10-10"></a>
<a id="hpr-p25-008b-ci-632-forensic-repair--2026-10-10"></a>
<a id="hpr-p25-008b-implementation-candidate--2026-10-10"></a>
<a id="hpr-p25-008c--bounded-independent-numerical-test-remediation-candidate-2026-10-10"></a>
<a id="hpr-p25-008c--compressorvalve-synthetic-numerical-acceptance-disposition-2026-10-10"></a>
<a id="hpr-p25-008c--equipment-execution-preflight-2026-10-10"></a>
<a id="hpr-p25-008c--final-narrow-compressorvalve-acceptance-test-preflight-2026-10-10"></a>
<a id="hpr-p25-008c--final-narrow-numerical-acceptance-test-candidate-2026-10-10"></a>
<a id="hpr-p25-008c--independent-numerical-acceptance-disposition-after-exact-head-ci-639-2026-10-10"></a>
<a id="hpr-p25-008c--independent-numerical-test-coverage-remediation-authorization-2026-10-10"></a>
<a id="hpr-p25-008c--independent-post-repair-numerical-acceptance-review-2026-10-10"></a>
<a id="hpr-p25-008c--numerical-acceptance-completion-test-scope-authorization-2026-10-10"></a>
<a id="hpr-p25-008c--regulator-cross-module-sourceschemaownership-preflight-2026-10-10"></a>
<a id="hpr-p25-008c--regulator-slice-a-authenticated-physical-dictionary-synchronization-2026-10-11"></a>
<a id="hpr-p25-008c--regulator-slice-a-database-dictionary-ci-remediation-preflight-2026-10-11"></a>
<a id="hpr-p25-008c--regulator-slice-a-topology-contract-implementation-preflight-2026-10-10"></a>
<a id="hpr-p25-008c--regulator-slice-a-topology-implementation-candidate-2026-10-10"></a>
<a id="hpr-p25-008c--regulator-slice-a-verified-acceptance-disposition-2026-10-11"></a>
<a id="hpr-p25-008c--regulator-slice-b-simulation-source-and-storage-preflight-2026-10-11"></a>
<a id="hpr-p25-008c--regulator-synthetic-methodcontract-authorization-2026-10-10"></a>
<a id="hpr-p25-008c--remaining-independent-numerical-matrix-test-candidate-2026-10-10"></a>
<a id="hpr-p25-008c--synthetic-compressorvalve-code-candidate-2026-10-10"></a>
<a id="hpr-p25-008c--synthetic-equipment-candidate-numerical-acceptance-review-2026-10-10"></a>
<a id="hpr-p25-008c--synthetic-equipment-methods-and-implementation-authorization-2026-10-10"></a>
<a id="hpr-p25-008c-regulator-slice-b-candidate--compile-blocker-and-scope-correction-2026-10-11"></a>
<a id="hpr-p25-008c-regulator-slice-b-implementation-candidate-and-ci-handoff-2026-10-11"></a>
<a id="hpr-p25-018--complete-supported-engineering-authoring-before-operational-use"></a>
<a id="hpr-p25-019--authoritative-route-geodesy-and-sectionprofile-preservation"></a>
<a id="hpr-p25-020--independent-installed-units-and-station-hydraulic-configurations"></a>
<a id="hpr-p25-021--manufacturer-evidence-as-reusable-versioned-engineering-data"></a>
<a id="hpr-p25-022--product-definitions-and-property-methods-with-explicit-applicability"></a>
<a id="hpr-p25-023--baseline-manifests-scenario-overlays-and-historical-retrieval"></a>
<a id="hpr-p25-024--configurable-models-and-eligible-internalexternal-providers"></a>
<a id="hpr-p25-025--one-validated-request-lifecycle-for-manual-and-proactive-modes"></a>
<a id="hpr-p25-026--calibration-ready-correspondence-without-changing-asset-facts"></a>
<a id="hpr-p25-027--independent-end-to-end-engineering-and-productmode-acceptance"></a>
<a id="hpr-p25-028--authoritative-engineering-baseline-management-preflight"></a>
<a id="hpr-p25-029--independent-operational-state-and-action-evidence-preflight"></a>
<a id="hpr-p25-030--engineering-foundation-acceptance-design-preflight"></a>
<a id="hpr-p25-031--existing-independent-audit-prospectively-clarified"></a>
<a id="hpr-p25-031--independent-engineering-foundation-closure-audit"></a>
<a id="hpr-p25-031--one-independent-closure-package"></a>
<a id="hpr-p25-035--preflight-not-implementation"></a>
<a id="hpr-p25-037--immutable-station-graph-postgresql-append-and-exact-read"></a>
<a id="hpr-p25-047--existing-frozen-seven-file-task"></a>
<a id="hpr-p25-050--identity-binding-issuance-and-operation-reader"></a>
<a id="hpr-p25-050a--trusted-binding-foundations-and-private-verification-receipts"></a>
<a id="hpr-p25-050b--fresh-schema-binding-storage-and-session-guards"></a>
<a id="hpr-p25-050c--real-provider-verification-and-atomic-bound-issuance"></a>
<a id="hpr-p25-050d--bound-operation-reader-and-integrated-b1-acceptance"></a>
<a id="hpr-p25-051--revocation-operation-time-and-physical-transaction-fence"></a>
<a id="hpr-p25-051h-a--source-backed-bounded-claim-coverage-model-registration-2026-10-16"></a>
<a id="hpr-p25-051h-a-bounded-claim-coverage-source-implementation--2026-10-16"></a>
<a id="hpr-p25-051h-a-ci-acceptance-and-hpr-p25-051h-b1-scoped-selection--2026-10-16"></a>
<a id="hpr-p25-051h-b1--ci-709-acceptance-checkpoint-2026-10-16"></a>
<a id="hpr-p25-051h-b1--direct-production-ci-706-repair-same-stage-2026-10-16"></a>
<a id="hpr-p25-051h-b1--production-ci-708-exception-translation-repair-same-stage-2026-10-16"></a>
<a id="hpr-p25-051h-b1-ci-707-fixture-repair--2026-10-16"></a>
<a id="hpr-p25-051h-b1-source-implementation-checkpoint--2026-10-16"></a>
<a id="hpr-p25-051h-b2--exact-one-commit-runtime-registration-2026-10-16"></a>
<a id="hpr-p25-051h-b2--implementation-submission-awaiting-exact-sha-ci-2026-10-16"></a>
<a id="hpr-p25-051h-b2--same-stage-ci-710-repair-physical-catalog-and-owner-trigger-test"></a>
<a id="hpr-p25-051h-b2--writer-dag-and-authority-preflight-2026-10-16"></a>
<a id="hpr-p25-051h-b2-ci-711-acceptance-and-h-c1-source-only-admission--2026-10-16"></a>
<a id="hpr-p25-051h-c1--bounded-oidc-claim-tree-projection-source-submission-2026-10-16"></a>
<a id="hpr-p25-051h-c1-ci-712-accepted-h-c2-sourceissuance-contract-and-c2a-scope-registered--2026-10-16"></a>
<a id="hpr-p25-051h-c2a--original-source-oidc-bounded-claim-receipt-2026-10-16"></a>
<a id="hpr-p25-051h-owner-approved-minimum-claim-and-reauthentication-policy--2026-10-16"></a>
<a id="hpr-p25-052--six-topologyoperator-delivery-packages"></a>
<a id="hpr-p25-052--topology-operator-path-geometry-and-publication"></a>
<a id="hpr-p25-053--four-productcompositionmethod-packages"></a>
<a id="hpr-p25-053--supplied-product-and-method-metadata"></a>
<a id="hpr-p25-054--five-installed-unitmanufacturer-data-packages"></a>
<a id="hpr-p25-054--installed-units-and-manufacturer-facts"></a>
<a id="hpr-p25-055--exact-authoritative-baseline-and-exchange"></a>
<a id="hpr-p25-055--five-authoritative-baselineexchange-packages"></a>
<a id="hpr-p25-056--five-telemetry-trustqualityreplay-packages"></a>
<a id="hpr-p25-056--telemetry-binding-trust-and-deterministic-qualityreplay"></a>
<a id="hpr-p25-057--four-observationactionoutcome-packages"></a>
<a id="hpr-p25-057--monitoring-actions-and-observed-outcomes"></a>
<a id="hpr-p25-058--four-integrated-platform-qualification-packages"></a>
<a id="hpr-p25-058--integrated-synthetic-verification-and-acceptance-evidence"></a>
<a id="hpr-p25-pilot-selection-amendment--gz4-first-gas-demonstrator-2026-10-10"></a>
<a id="hpr-rel-001-bounded-release-alignment-preparation--2026-10-09"></a>
<a id="hpr-rel-001-exact-candidate-verification--2026-10-09"></a>
<a id="hpr-rel-002-final-exact-transition-verification--2026-10-09"></a>
<a id="hpr-rel-002-published-release-record-and-development-transition--2026-10-09"></a>
<a id="hse-prereq-01--independently-recovered-live-scope-and-policy-gaps"></a>
<a id="ic-prereq-01--independently-recovered-scope-and-taxonomy-gaps"></a>
<a id="identity-owner-evidence-and-remaining-fencetime-gates"></a>
<a id="implementation-acceptance-and-validation"></a>
<a id="implementation-and-closed-scope"></a>
<a id="implementation-and-evidence-limits"></a>
<a id="implementation-and-exact-scope"></a>
<a id="implemented-bounded-foundations"></a>
<a id="implemented-topology-only-candidate-compatibility-invariants"></a>
<a id="inc-prereq-01--independently-recovered-live-gaps"></a>
<a id="independent-equation-and-dimensional-review"></a>
<a id="independent-exact-scopes-and-semantic-messages"></a>
<a id="independent-frozen-synthetic-design-oracles-and-future-gates"></a>
<a id="independent-future-acceptance-cases-ai"></a>
<a id="independent-measured-state-association-contract"></a>
<a id="independent-numerical-reference-and-coverage-assessment"></a>
<a id="independent-p2-audit-analysis-and-bounded-correction-plan--2026-10-09"></a>
<a id="independent-sourcescope-review-and-accepted-boundary"></a>
<a id="independently-calculated-benchmark-inputs-and-machine-acceptance"></a>
<a id="independently-derived-references-and-fixed-numerical-acceptance"></a>
<a id="input-decomposition-and-version-semantics"></a>
<a id="inspected-live-ownership-and-compatibility"></a>
<a id="inspected-source-and-compatibility-inventory"></a>
<a id="issuance-termination-credential-policy-and-compatibility"></a>
<a id="later-validation-and-progress-control"></a>
<a id="legacy-heading-anchors--links-retained-for-historical-references"></a>
<a id="live-baseline-prior-acceptance-and-selected-scope"></a>
<a id="live-evidence-and-authorized-registration"></a>
<a id="live-evidence-and-prerequisite-rep-prereq-01"></a>
<a id="live-gate-decision-and-disposition"></a>
<a id="live-hmsr-recovery-and-prerequisite-disposition"></a>
<a id="live-prerequisites-and-execution-authorization"></a>
<a id="live-prerequisites-selection-and-scope-control"></a>
<a id="live-recovery-and-selected-registration"></a>
<a id="live-source-anchors-and-authority"></a>
<a id="mandatory-validation-obligation-matrix"></a>
<a id="material-methodschema-gaps-and-bounded-disposition"></a>
<a id="meaningful-synthetic-tests-and-required-verification"></a>
<a id="meaningful-synthetic-tests-and-validation"></a>
<a id="meaningful-tests-and-validation"></a>
<a id="mechanism-comparison-and-selected-persisted-state-fence"></a>
<a id="migration-reservation-storage-and-integrity"></a>
<a id="minimum-mixed-boundary-connected-network-formulation"></a>
<a id="missing-data-completeness-and-follow-up-gates"></a>
<a id="mutation-privilege-and-lock-order-disposition"></a>
<a id="named-executable-synthetic-semantics"></a>
<a id="new-independently-executable-split-tasks"></a>
<a id="new-postgresql-tests-and-actual-local-validation"></a>
<a id="new-proof-and-actual-local-validation"></a>
<a id="newly-admitted-bounded-task-registry"></a>
<a id="next-bounded-preflight"></a>
<a id="next-recommended-work"></a>
<a id="next-registered-selection"></a>
<a id="next-selection-validation-and-publication"></a>
<a id="next-separately-selected-narrower-three-file-test-only-correction"></a>
<a id="numerical-and-execution-architecture-assessment"></a>
<a id="one-action-selection-and-immutable-baseline"></a>
<a id="one-bounded-owner-next-verified-evidence-and-commit"></a>
<a id="one-physical-completion-commit"></a>
<a id="operation-boundary-temporal-blocker-b2-and-participation-blocker-b3"></a>
<a id="operator-authoring-lifecycle-and-completeness-semantics"></a>
<a id="operator-controlled-workflow-and-cross-owner-guards"></a>
<a id="ordered-researchverification-workstream--no-implementation-authorization"></a>
<a id="ordered-two-stage-hpr-p2-006-execution-envelope"></a>
<a id="ordering-and-common-sample-decision-gate"></a>
<a id="outcome-and-next-design-task"></a>
<a id="own-engine-decomposition-under-hpr-p25-008"></a>
<a id="owner-contracts-and-operator-workflows"></a>
<a id="owner-decisions"></a>
<a id="owner-evidence-and-missing-data-boundary"></a>
<a id="owner-execution-protocol--2026-10-13-fail-correction"></a>
<a id="owner-next-evidence-and-exact-one-task-boundary"></a>
<a id="owner-private-repository-port-contract--no-implementation-yet"></a>
<a id="owner-selected-parallel-continuation--hpr-p25-050-d1-2026-10-13"></a>
<a id="owner-selected-production-ci-optimization--2026-10-09"></a>
<a id="owner-selection-and-scope"></a>
<a id="owner-selection-evidence-classification-exact-scope"></a>
<a id="owner-selection-exact-scope-previous-blockers"></a>
<a id="owner-selection-exact-source-and-stage-boundary"></a>
<a id="owner-separated-implementation-decomposition-and-acceptance-mapping"></a>
<a id="owner-separated-implementation-envelopes--design-only-not-executable-tasks"></a>
<a id="ownership-and-authorized-workflow-preflight"></a>
<a id="p1-evidence-block-parallel-progression-authorization--2026-10-06"></a>
<a id="p1-final-survivability-closure--2026-10-06"></a>
<a id="p1-independent-audit-ingestion-evidence"></a>
<a id="p1-independent-audit-remediation-baseline--2026-10-05"></a>
<a id="p1-independent-survivability-re-audit--2026-10-06"></a>
<a id="p1-owner-value-approval-evidence--2026-10-05"></a>
<a id="p25--three-engine-independent-numerical-reference-evaluation-amendment-2026-10-10"></a>
<a id="p3-def--deferred-industrial-extensions"></a>
<a id="package-coverage"></a>
<a id="parent051-exit-criteria-all-required-no-slice-substitution"></a>
<a id="part-a--remaining-development-sequence"></a>
<a id="part-b--common-binding-task-contract"></a>
<a id="part-c--accepted-and-deferred-history"></a>
<a id="part-d--ci-baseline-and-target-verification-contract"></a>
<a id="part-e--approved-requirement-completeness-mapping"></a>
<a id="participant-protocol-concrete-implementation-contract"></a>
<a id="pending-exact-head-final-verification-and-scope-boundary"></a>
<a id="persistence-compatibility-and-bounded-implementation-envelopes--not-authorized"></a>
<a id="persistence-integrity-compatibility-and-proposed-envelopes"></a>
<a id="persistenceapi-authorization-and-integrity-boundaries"></a>
<a id="phase-p0--security--code-defect-resolution"></a>
<a id="phase-p1--production-infrastructure--survivability"></a>
<a id="phase-p2--canonical-governance-api-contracts--semantic-integration"></a>
<a id="phase-p25--operational-digital-twin-and-decision-feedback"></a>
<a id="phase-p3--deferred-industrial-scale--future-capabilities"></a>
<a id="physical-and-numerical-admissibility-limitations"></a>
<a id="physical-dictionary-limitation-and-expected-gate-outcome"></a>
<a id="physical-payload-stages"></a>
<a id="pinned-review-findings"></a>
<a id="preflight-disposition"></a>
<a id="preflight-only-filecommit-boundary"></a>
<a id="preflight-publication-and-next-action"></a>
<a id="prepared-candidate-and-actual-validation"></a>
<a id="prerequisite-acceptance-registered-scope-and-evidence-boundary"></a>
<a id="prerequisite-and-execution-envelope"></a>
<a id="prerequisite-disposition-and-next-registered-execution"></a>
<a id="present-status-and-stop"></a>
<a id="preservation-and-next-action"></a>
<a id="preservation-and-single-next-eligible-task"></a>
<a id="preservation-of-roadmap-gates-next-execution"></a>
<a id="preserved-pre-consolidation-dashboard-and-exact-task-records"></a>
<a id="product-invariant-and-two-independent-acceptance-gates"></a>
<a id="proposed-accepted-on-next-bounded-design"></a>
<a id="proposed-bounded-implementation-envelopes--not-authorized"></a>
<a id="proposed-exact-binding-relation-and-column-contract--not-a-migration-reservation"></a>
<a id="proposed-exhaustive-per-hmr-write-scopes"></a>
<a id="proposed-exhaustive-per-hmr-write-scopes-1"></a>
<a id="proposed-implementation-design--ten-parts"></a>
<a id="proposed-persistence-protocol-for-separately-authorized-implementation"></a>
<a id="proposed-semantic-model--independent-of-storage-technology"></a>
<a id="protected-ownerexport-contract-boundary"></a>
<a id="ptmd-prereq-01--independently-recovered-write-policy-and-owner-contract-gaps"></a>
<a id="publication-and-next-owner-action"></a>
<a id="publication-and-next-selection"></a>
<a id="publication-and-remaining-selection"></a>
<a id="publication-disposition-and-mandatory-remaining-reconciliation"></a>
<a id="publication-head-compare-and-set-and-immutable-event-protocol"></a>
<a id="publication-protocol-and-retained-position"></a>
<a id="qualification-and-evidence-gates"></a>
<a id="real-producers-and-exact-source-transport"></a>
<a id="recovered-actor-binding-source-and-design-boundary"></a>
<a id="recovered-current-source-constraints"></a>
<a id="recovered-evidence-and-reason-for-the-envelope"></a>
<a id="recovered-exact-source-and-implementation-boundary"></a>
<a id="recovered-implementation-and-governance-anchors"></a>
<a id="recovered-local-evidence-and-execution-limits"></a>
<a id="recovered-next-dependency-fresh-bounded-authorization-evidence"></a>
<a id="recovered-source-and-compatibility-boundary"></a>
<a id="recovered-source-and-reason-for-the-b1-only-split"></a>
<a id="recovered-source-and-the-actual-missing-boundary"></a>
<a id="recovered-source-and-unresolved-contract-boundaries"></a>
<a id="recovered-source-findings"></a>
<a id="recovered-storage-and-writer-evidence"></a>
<a id="references-validation-and-current-publication"></a>
<a id="registered-future-execution--hpr-ci-002"></a>
<a id="registered-future-implementation-scope-and-verification"></a>
<a id="registered-future-task--hpr-p25-036"></a>
<a id="registered-future-task-and-exclusive-write-contract"></a>
<a id="registered-future-test-only-execution"></a>
<a id="registered-next-executable-correction--hpr-p25-048"></a>
<a id="registered-next-executable-correction--hpr-p25-049"></a>
<a id="registered-next-executable-implementation--hpr-p25-040"></a>
<a id="registered-next-executable-implementation--hpr-p25-041"></a>
<a id="registered-next-executable-implementation--hpr-p25-046"></a>
<a id="registered-next-executable-implementation--hpr-p25-047"></a>
<a id="registered-next-executable-implementation-not-executed"></a>
<a id="registered-next-executable-task--hpr-p25-038"></a>
<a id="registered-next-executable-task--hpr-p25-042"></a>
<a id="registered-next-executable-task--hpr-p25-043"></a>
<a id="registered-next-executable-task--hpr-p25-044"></a>
<a id="registered-next-executable-task--hpr-p25-045"></a>
<a id="registered-next-implementation--hpr-p25-039"></a>
<a id="registered-next-work--hpr-p25-033-design-preflight-only-not-executed"></a>
<a id="registration-validation-actually-completed"></a>
<a id="registration-validation-actually-executed"></a>
<a id="regulator-method-decision-that-must-be-made-before-code"></a>
<a id="remaining-bounded-reasons-not-to-assert-complete-compressorvalve-numerical-acceptance"></a>
<a id="remaining-closure-and-deployment-boundaries"></a>
<a id="remaining-equipment-family-obligation-and-next-gate"></a>
<a id="remaining-parent-requirements-and-publication-boundary"></a>
<a id="remaining-stage-register"></a>
<a id="remaining-work-and-next-selected-action"></a>
<a id="renewed-bounded-execution"></a>
<a id="repair-and-retained-acceptance-boundary"></a>
<a id="repair-validation-and-publication-disposition"></a>
<a id="required-acceptance-mapping-and-negative-cases"></a>
<a id="required-b1-policy-output-and-acceptance"></a>
<a id="required-bounded-design-output-and-acceptance-gates"></a>
<a id="required-c1-validation-and-stop-boundary"></a>
<a id="required-compiler-contract"></a>
<a id="required-correction-proof-and-dictionary-scope"></a>
<a id="required-exact-file-implementation-authorization-before-any-008b-code"></a>
<a id="required-future-acceptance-and-negative-evidence"></a>
<a id="required-future-acceptance-and-negative-evidence-design-cases-only"></a>
<a id="required-future-acceptance-matrix-and-current-validation"></a>
<a id="required-future-data-only-acceptance-and-negative-cases"></a>
<a id="required-future-validation-and-acceptance"></a>
<a id="required-genuine-future-tests-and-regression-gates"></a>
<a id="required-genuine-g-tests-and-acceptance"></a>
<a id="required-meaningful-synthetic-tests-and-checks"></a>
<a id="required-product-and-physical-input-contract"></a>
<a id="required-real-synthetic-tests-and-validation"></a>
<a id="required-remaining-source-policy-approvals-and-exact-file-preflight"></a>
<a id="required-separate-implementation-decomposition"></a>
<a id="required-source-and-mathematical-review-for-the-next-separate-authorization"></a>
<a id="required-source-backed-storage-design-and-acceptance"></a>
<a id="required-synthetic-proof-and-bounded-existing-test-update"></a>
<a id="required-synthetic-storage-proof--specification-only"></a>
<a id="required-tests-and-exact-validation"></a>
<a id="reserved-schema-and-collision-result"></a>
<a id="retained-p2-closure-disposition"></a>
<a id="revalidated-exact-source-and-compatibility-constraints"></a>
<a id="revalidated-source-and-eligibility-boundary"></a>
<a id="revalidated-source-ownership-and-primary-technical-basis"></a>
<a id="review-artifact-identities-and-publication-boundary"></a>
<a id="revised-accepted-on-next-proposal"></a>
<a id="risk-prereq-01--live-unregistered-ownerlifecycle-prerequisites"></a>
<a id="scope-allocation-and-additional-real-acceptance"></a>
<a id="selected-action-and-exact-evidence-baseline"></a>
<a id="selected-action-exact-baseline-and-documentation-only-write-scope"></a>
<a id="selected-bounded-code-execution-exact-upstream-evidence"></a>
<a id="selected-candidate-fenced-stability-interval-plus-final-database-guard"></a>
<a id="selected-development-only-mathematical-candidate-not-a-field-law"></a>
<a id="selected-documentation-stage-and-accepted-evidence"></a>
<a id="selected-explicit-restriction-within-the-same-implementation-union"></a>
<a id="selected-implementation-and-concrete-missing-discriminator"></a>
<a id="selected-persistent-origin-witness-within-existing-050-paths"></a>
<a id="selected-registration-and-accepted-design-boundary"></a>
<a id="selected-registration-and-accepted-prerequisite"></a>
<a id="selected-registration-and-exact-head-acceptance"></a>
<a id="selected-scope-and-implementation"></a>
<a id="selected-scope-and-implementation-1"></a>
<a id="selected-stage-and-authoritative-baseline"></a>
<a id="selected-stage-exact-recovery-and-uncovered-compatibility-boundary"></a>
<a id="selected-stage-recovered-prerequisite-and-source-evidence"></a>
<a id="selected-task-and-verified-evidence"></a>
<a id="selection-and-accepted-evidence"></a>
<a id="selection-and-accepted-evidence-1"></a>
<a id="selection-and-accepted-prerequisite"></a>
<a id="selection-and-baseline"></a>
<a id="selection-and-current-disposition"></a>
<a id="selection-and-exact-baseline"></a>
<a id="selection-and-immutable-baseline"></a>
<a id="selection-and-source-backed-disposition"></a>
<a id="selection-and-verified-baseline"></a>
<a id="selection-and-verified-baseline-1"></a>
<a id="selection-and-verified-baseline-2"></a>
<a id="selection-and-verified-gates"></a>
<a id="selection-baseline-and-current-write-boundary"></a>
<a id="selection-current-failure-and-bounded-action"></a>
<a id="selection-evidence-and-current-scope"></a>
<a id="selection-evidence-and-disposition"></a>
<a id="selection-evidence-and-retained-tasks"></a>
<a id="selection-exact-scope-and-stop-rule"></a>
<a id="selection-gates-and-publication-boundary"></a>
<a id="selection-scope-and-disposition"></a>
<a id="selection-source-and-exact-scope"></a>
<a id="selection-source-authority-and-scope"></a>
<a id="selection-source-review-and-status"></a>
<a id="separate-append-only-schema-and-canonical-persistence"></a>
<a id="separate-future-test-only-continuation-exactly-five-allowed-update-paths"></a>
<a id="separate-implementation-acceptance-envelopes"></a>
<a id="sequential-gate"></a>
<a id="shared-workflow-approval-evidence-implemented-once-in-c3c"></a>
<a id="sim-def--deferred-numerical-simulation"></a>
<a id="sim-prereq-01--concrete-live-owner-boundary-and-write-scope-gap"></a>
<a id="solo-hmr-080-live-prerequisite-recovery"></a>
<a id="source-anchored-risk-and-test-boundary"></a>
<a id="source-and-data-contract-findings"></a>
<a id="source-and-ownership-findings-supporting-the-split"></a>
<a id="source-backed-decomposition-and-ownership"></a>
<a id="source-backed-design-and-graph-prerequisite-resolution"></a>
<a id="source-backed-existing-capability-and-gap-inventory"></a>
<a id="source-backed-gaps-and-qualification-disposition"></a>
<a id="source-backed-module-contracts-and-non-claims"></a>
<a id="source-backed-ownership-and-authorization-decisions"></a>
<a id="source-backed-root-cause-and-scope-disposition"></a>
<a id="source-backed-topology-findings-and-consequential-versioning-decision"></a>
<a id="source-complete-graph-for-the-reviewed-current-identity-evaluator"></a>
<a id="source-confirmed-time-inventory"></a>
<a id="source-derived-ownership-and-compatibility-boundary"></a>
<a id="source-evidence-and-purpose"></a>
<a id="source-facts-requiring-the-new-technique"></a>
<a id="source-findings-and-boundary"></a>
<a id="source-findings-and-reusable-evidence"></a>
<a id="source-owners-and-concrete-compatibility-findings"></a>
<a id="source-reality-and-ownership"></a>
<a id="source-recovery-and-bounded-decomposition"></a>
<a id="source-recovery-and-resolved-boundary"></a>
<a id="source-revalidation-and-selected-technical-boundaries"></a>
<a id="source-review-and-representation-choice"></a>
<a id="source-reviewed-technical-boundary"></a>
<a id="source-reviewed-trigger-and-writer-audit"></a>
<a id="source-storage-and-approval-design-decisions"></a>
<a id="sourceprecedent-review-and-selected-future-repair-procedure-not-executed-here"></a>
<a id="stage-a-exact-write-allowlist--six-paths"></a>
<a id="stage-a-extraction-protocol-and-acceptance"></a>
<a id="stage-b-dictionarypolicy-acceptance-and-drift-verification"></a>
<a id="stage-b-exact-write-allowlist--eight-paths"></a>
<a id="status-and-next-boundary"></a>
<a id="status-and-next-task"></a>
<a id="status-and-stop"></a>
<a id="status-sequence-and-stopping-rule"></a>
<a id="stop-and-disposition"></a>
<a id="subsequent-separately-selected-008c-test-only-write-allowlist"></a>
<a id="supporting-preflight-publication-and-actual-validation"></a>
<a id="supporting-publication-scope-and-checks"></a>
<a id="synthetic-engineering-envelope-and-failure-policy"></a>
<a id="synthetic-postgresql-and-unit-proofs"></a>
<a id="synthetic-race-specification-and-prerequisite-decomposition"></a>
<a id="synthetic-verification-specification--not-executed-in-this-stage"></a>
<a id="target-ci-architecture--specification-not-installed"></a>
<a id="task-selection-and-acceptance-boundary"></a>
<a id="task-status-and-evidence-boundary"></a>
<a id="test-environment-negative-and-migration-matrix"></a>
<a id="test-evidence-and-claim-limits"></a>
<a id="tests-and-acceptance-boundary"></a>
<a id="this-preflights-narrow-supporting-publication"></a>
<a id="this-preflights-supporting-execution"></a>
<a id="this-preflights-supporting-execution-and-actual-checks"></a>
<a id="this-preflights-supporting-scope"></a>
<a id="this-registration-validation-and-stop"></a>
<a id="this-supporting-publication"></a>
<a id="track-a--active-engineering-and-operational-foundation-milestones"></a>
<a id="track-b--deferred-simulator-registry-and-reactivation"></a>
<a id="trusted-provenance-matrix-for-future-protected-operations"></a>
<a id="truthful-regulator-and-pending-source-disposition"></a>
<a id="union-network-input-equations-and-result-design"></a>
<a id="unmet-original-hpr-p25-008b-acceptance-gates"></a>
<a id="updated-execution-disposition"></a>
<a id="user-authorized-p2-batch-1--2026-10-06"></a>
<a id="user-authorized-p2-batch-2--2026-10-06"></a>
<a id="validation-actually-executed"></a>
<a id="validation-actually-performed-and-limitations"></a>
<a id="validation-and-publication-protocol"></a>
<a id="verification-evidence-and-limits"></a>
<a id="verified-active-source-contracts-and-owner-boundaries"></a>
<a id="verified-contracts-and-fixture-admissibility"></a>
<a id="verified-evidence-and-execution-limitation"></a>
<a id="verified-foundation-and-missing-execution-boundary"></a>
<a id="verified-gate-execution-boundary"></a>
<a id="verified-head--ci--exact-prior-scope"></a>
<a id="verified-implementation-anchors"></a>
<a id="verified-module-review-and-preservation-inventory"></a>
<a id="verified-position-and-attached-selection"></a>
<a id="verified-position-and-material-gap"></a>
<a id="what-f1f4-mean-for-platform"></a>
<a id="work-prereq-01--live-owner-contract-and-migration-order-gaps"></a>
