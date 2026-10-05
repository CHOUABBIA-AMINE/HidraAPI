# HidraAPI Ultimate Platform Finalization Roadmap

> **AUTHORITATIVE EXECUTION CONTROL — SINGLE SOURCE OF TRUTH**
>
> This document is the single source of truth for HidraAPI platform-finalization sequencing and target-architecture disposition.
>
> Existing material under `docs/` remains preserved as audit, provenance, semantic-review, ADR, historical, and execution evidence. It does not override this roadmap for platform-finalization execution.
>
> Current implementation claims must be verified against production source, runtime configuration, Flyway migrations, architecture tests, and CI evidence before mutation.

## 1. Control Status

| Field | Value |
|---|---|
| Status | ACTIVE |
| Roadmap code | HPR — Hidra Platform Readiness |
| Canonical documentation root | `doc/` |
| Legacy/reference documentation root | `docs/` |
| Forensic audit evidence baseline | `3e6de10b6e64bd989626a522d2de21b8ea501b8c` |
| Bootstrap repository baseline | `e1f33e32519dcd1d07367e96cf399dfad2933053` |
| Production readiness | NOT ESTABLISHED — original P0 source defects are remediated, but independent P0 verification/completeness gaps are OPEN; P1 survivability has not started |
| TimescaleDB | NOT IMPLEMENTED — DEFERRED / TARGET |
| PostGIS | NOT IMPLEMENTED — DEFERRED / TARGET |
| High availability | NOT DOCUMENTED / NOT VERIFIED |
| RTO | TBD — BUSINESS / OPERATIONS DECISION REQUIRED |
| RPO | TBD — BUSINESS / OPERATIONS DECISION REQUIRED |

## 2. Governing Rules

1. Execute phases in order: P0 → P1 → P2 → P3.
2. Execute exactly one HPR code per user instruction unless this roadmap explicitly registers a batch.
3. Use the exact commit message registered for the HPR task.
4. P0 security closure precedes any production-readiness claim.
5. P1 closure requires implemented and exercised survivability controls; documentation alone cannot close HA or DR.
6. P2 absorbs unresolved model-semantic-remediation obligations. Completed HMR work is not restarted without concrete regression evidence.
7. `docs/roadmap/model-semantic-remediation.md` is subordinate execution-history/reference evidence. Its summary/status text is not execution authority.
8. Before executing a legacy semantic obligation, revalidate its HMSR evidence, current source, dependencies, write scope, migration need, and regression tests.
9. `doc/` is the canonical maintained documentation hierarchy. `docs/` remains preserved evidence/reference material.
10. P3 capabilities remain deferred until measured requirements and approved architecture justify adoption.
11. Never invent RTO, RPO, SLO, deployment topology, database topology, throughput targets, retention periods, SRIDs, or industrial protocols.
12. A capability moves from TARGET/DEFERRED to CURRENT only after implementation, tests, configuration/migrations, and operating procedures provide evidence.
13. If a task requires an owner/business/operations decision not present in repository evidence, mark the task BLOCKED and stop; do not fabricate the decision.
14. Every task closes with exact-head verification and an update to this roadmap recording evidence and the next executable code.
15. The 2026-10-05 independent P0 verification audit at `dcf69e1a4a4b788cd125ba6289384642efc4caa0` reopens P0 only for the six failed verification/completeness checks recorded below. Checks 1–5, 8, and 10 remain VERIFIED unless later regression evidence disproves them.
16. A verification-evidence gap must not be mislabeled as a missing implementation. In particular, OpenAPI security-scheme code exists; the remaining Check 9 gap is exact-head generated evidence.
17. Phase P1 may not begin until HPR-P0-015 closes the independent-audit gap set.

## 3. In-Flight Semantic Remediation Disposition

Decision: **MERGE**.

- Completed HMR code changes remain valid unless a concrete regression is established.
- Pending HMR obligations are absorbed into Phase P2 and must be selected through this roadmap.
- The legacy HMR roadmap remains preserved for HMSR obligations, historical execution evidence, migration authorization, and detailed task provenance.
- Permanent terminology, invariants, ownership, and current-state descriptions move into `doc/domain/` and `doc/modules/`.
- Stale HMR summary text must never be used to select the next task.

## 4. State-of-the-Platform Gate

The following audited statements govern prioritization:

- DDD modularity and Hexagonal boundaries are materially implemented and enforced by `ArchitectureGuardrailTest`.
- The forensic baseline identified `HidraOperationalWorkbenchService` as a material P0 exposure boundary; HPR-P0-001..003 replaced automatic exposure with fail-closed resource/field approval, prohibited credential/password exposure, added regressions, and added architecture guardrails.
- The audited `LocalCredentialJpaEntity.passwordHash` exposure path is remediated and covered by regression/architecture controls; P0 closure evidence is recorded under HPR-P0-007.
- HA architecture is not documented or verified.
- DR objectives/procedures are not established; RTO/RPO remain owner decisions.
- CI exists; CD/deployment automation is not established by repository evidence.
- TimescaleDB is not implemented and remains deferred/target.
- PostGIS is not implemented and remains deferred/target.
- GeoJSON/application geometry is not PostGIS persistence.
- Existing `docs/` contains valuable evidence but also stale and conflicting current-state material.
- Independent P0 verification audit at `dcf69e1a4a4b788cd125ba6289384642efc4caa0`: 7 checks VERIFIED; 6 checks FAILED. Failures are HTTP/serialization regression depth, exact-SHA OpenAPI evidence, application trust-boundary documentation, telemetry/operator threat coverage, and concrete owner-approved secret/certificate/incident procedures.

## 5. Execution Registry

### Phase P0 — Security & Code Defect Resolution

| Code | Status | Domain/Module | Type | Exact execution requirement | Exact commit message | Depends on |
|---|---|---|---|---|---|---|
| HPR-P0-001 | COMPLETED — fail-closed opt-in resource/field policy implemented in the task commit containing this status transition; default exposure is empty; credential/secret resources and password/secret fields are prohibited | Platform / Workbench / Identity | Code | Re-inspect the exact-head Workbench path, replace automatic metamodel exposure with a fail-closed approved-resource policy, replace unrestricted reflective response fields with a fail-closed approved-field/API-safe projection policy, and explicitly prevent exposure of credential/secret persistence including `LocalCredentialJpaEntity.passwordHash`. | `fix(platform): secure operational workbench data exposure` | Bootstrap |
| HPR-P0-002 | COMPLETED — focused policy/service regressions added for default-deny discovery, explicit fields, identifier requirement, unknown fields, credential/secret rejection, list/detail/search attribute exposure, hidden filter/sort rejection, and `passwordHash` prohibition | Platform / Workbench / Identity | Code | Add focused regression tests covering list, detail, filter/search and generic attribute maps; assert that credential resources and `passwordHash` cannot be returned. | `test(platform): guard workbench sensitive data exposure` | HPR-P0-001 |
| HPR-P0-003 | COMPLETED — `ArchitectureGuardrailTest` now restricts platform JPA access to the reviewed Workbench reader, forbids platform dependencies on module persistence packages, and requires that reader to retain the fail-closed exposure-policy dependency | Architecture Testing | Code | Extend architecture/security guardrails so a future generic platform reader cannot silently introduce unrestricted module-JPA exposure outside an explicitly reviewed boundary. | `test(architecture): guard generic persistence exposure` | HPR-P0-001 |
| HPR-P0-004 | COMPLETED — generated OpenAPI now declares separate Hidra-issued JWT and external-OIDC bearer schemes; ordinary protected operations use Hidra bearer, OIDC completion uses external OIDC bearer, and verified public endpoints are explicitly unauthenticated | API / Security | Code | Add machine-readable OpenAPI security scheme and applicable security requirements for secured endpoints without weakening runtime security. | `fix(api): declare openapi security requirements` | HPR-P0-001 |
| HPR-P0-005 | COMPLETED — `X-Actor-Id` is no longer a supported platform header; the early request-context filter no longer reads caller actor identity or populates actor MDC/logging context; authenticated audit/JPA attribution remains sourced from `CurrentSecurityContext`/`CurrentActorResolver`; spoofing regression added | Platform / Logging | Code | Revalidate caller-supplied actor-header handling; ensure it is not represented as authenticated audit identity unless a verified binding exists; correct code/tests/documentation only where evidence requires it. | `fix(platform): clarify audit actor provenance` | HPR-P0-001 |
| HPR-P0-006 | COMPLETED — canonical security architecture, trust-boundary, threat-model, secrets/certificates, and incident-response documents created from exact-head repository evidence; TLS/certificate lifecycle, secret rotation, SIEM/SOC, security contacts, severity model, HA/DR and RTO/RPO remain explicitly NOT ESTABLISHED/TBD | Security | Doc | Complete canonical security documents: `SECURITY_ARCHITECTURE.md`, `TRUST_BOUNDARIES.md`, `THREAT_MODEL.md`, `SECRETS_AND_CERTIFICATES.md`, and `INCIDENT_RESPONSE.md`, using only verified current controls and explicit TARGET/TBD markers. | `docs(security): establish canonical security baseline` | HPR-P0-001..005 |
| HPR-P0-007 | COMPLETED — P0 CLOSED. CI run #520 on executable verification anchor `359ae6d77bb9bb8f499941634760f4c375abb9ac` passed Java 21 `./mvnw -B -q clean verify`, architecture/security tests, PostgreSQL/Testcontainers + Flyway validation/migration, application startup/health, deterministic `/v3/api-docs` generation, and artifact upload. The only intervening commit `ad32650f71900a60c0ed7ee3123191ad26c688ed` changed six Markdown files, so the executable tree remained identical; the closure commit changes only this roadmap. | Repository | Code/Doc | Run full Maven verification, architecture/security tests, database/Flyway startup verification and deterministic OpenAPI generation; record exact-head evidence and close P0 only if all required checks pass. | `docs(roadmap): close P0 security remediation` | HPR-P0-001..006 |
| HPR-P0-008 | COMPLETED — `HidraOperationalWorkbenchHttpExposureTest` now exercises real MockMvc controller/serialization for discovery, list, detail, and search; direct `identity/local-credentials` list/detail/search requests return HTTP 400 before credential persistence access; serialized responses exclude `passwordHash` and a distinctive hash marker; hidden search/filter fields cannot reach JPA criteria | Platform / Workbench / API | Code/Test | Close audit Checks 6–7 with HTTP-level Workbench regression tests using the real controller/serialization path. Cover resource discovery, list, detail, and search; directly request prohibited credential resources and assert denial before persistence access; assert serialized responses contain neither the `passwordHash` property nor a distinctive password-hash marker value. Preserve the existing fail-closed resource/field policy. | `test(platform): verify workbench http exposure boundary` | HPR-P0-001..003 |
| HPR-P0-009 | COMPLETED — exact task SHA `5ae7905fd51db6efb6f688b025e0dd29a041e200` passed CI run #522; repository verification and deterministic OpenAPI publication succeeded; retained artifact `hidra-api-openapi-5ae7905fd51db6efb6f688b025e0dd29a041e200` (artifact id `11348855890`) embeds the exact SHA and verifies both bearer schemes plus representative protected/OIDC/public operation security requirements; canonical `doc/**` Markdown now triggers CI while legacy `docs/**` remains ignored | CI / OpenAPI | Infra/Test | Close audit Check 9 as an evidence gap, not a code reimplementation. Adjust CI triggering so canonical `doc/` security/roadmap changes do not silently bypass verification, then obtain a successful CI run on the exact task SHA that executes `./mvnw -B -q clean verify`, starts the application, fetches deterministic `/v3/api-docs`, retains the artifact, and verifies both `hidraBearerJwt` and `externalOidcBearerJwt` plus representative operation security references in the generated artifact. | `ci(security): require exact-head openapi evidence` | HPR-P0-008 |
| HPR-P0-010 | COMPLETED — security architecture/trust-boundary docs now map normal API→controller→application→domain/outbound-port flow, explicitly exported cross-module application contracts, the reviewed Workbench→JPA exception, telemetry source/point registration flows, and telemetry query flow; private cross-module internals and OT/deployment/network assumptions remain prohibited or NOT ESTABLISHED | Security Architecture | Doc | Close audit Check 11 by extending `SECURITY_ARCHITECTURE.md` and `TRUST_BOUNDARIES.md` with repository-verified API → controller → application/use-case → module transitions, permitted cross-module application-contract transitions, the reviewed Workbench persistence exception, and the existing telemetry source/point registration/operator API boundary. Keep OT network architecture, deployment perimeter, and unverified infrastructure explicitly NOT ESTABLISHED. | `docs(security): map application trust transitions` | HPR-P0-008 |
| HPR-P0-011 | COMPLETED — threat model now includes explicit telemetry source registration, telemetry point registration, telemetry query, alarm acknowledgement/closure, alarm shelving/unshelving, and alarm-raise abuse scenarios with asset, actor/source, attack path, boundary, impact, verified controls, and residual risk; no SCADA/PLC ingestion transport or unverified vulnerability is invented | Security / Telemetry | Doc | Close audit Check 12 by extending `THREAT_MODEL.md` with existing telemetry source/point registration and operator-action abuse paths. For each threat record asset, actor/source, attack path, trust boundary, impact, current control, and residual risk. Do not invent SCADA transport, telemetry reading-ingestion endpoints, or an unverified vulnerability. | `docs(security): extend operator threat model` | HPR-P0-010 |
| HPR-P0-012 | COMPLETED — owner explicitly accepted the recommended lifecycle baseline on 2026-10-05; `SECRETS_AND_CERTIFICATES.md` now records role-based secret ownership, least privilege, 90-day JWT/database/LDAP rotation, coordinated HS256 emergency invalidation with no claimed dual-key overlap, one-time bootstrap handling, Platform/Infrastructure TLS ownership, enterprise/public CA trust model, protected private-key storage, 45/30/14/7-day expiry thresholds, compromise revocation/replacement, emergency authority, and mandatory recovery evidence; tooling remains pending actual platform implementation | Security / Secrets / Certificates | Doc/Decision | Obtain owner-approved lifecycle decisions required by audit Check 13: secret-store ownership, JWT signing-secret routine/emergency rotation and invalidation sequence, datasource and LDAP credential rotation sequence, TLS termination ownership, certificate authority/trust model, certificate/private-key storage, renewal/expiry monitoring, revocation procedure, emergency authority, and recovery verification. Values/procedures remain TBD until approved. | `docs(security): approve secret and certificate lifecycle` | accountable Security/Operations owner decision |
| HPR-P0-013 | NEXT — owner recommendations were explicitly accepted on 2026-10-05; canonical incident-governance decision capture remains to be executed | Security / Incident Governance | Doc/Decision | Obtain owner-approved incident-governance decisions required by audit Check 13: incident commander/decision authority, SOC/on-call/security escalation contacts, severity taxonomy, notification/escalation timing, external IdP/LDAP/provider escalation contacts, legal/regulatory notification ownership, and evidence-retention ownership. Do not invent names, timelines, or regulatory obligations. | `docs(security): approve incident governance model` | accountable Security/Operations/Business owner decision |
| HPR-P0-014 | PENDING | Security Operations | Doc | After HPR-P0-012 and HPR-P0-013 approvals, replace the applicable TBD sections in `SECRETS_AND_CERTIFICATES.md` and `INCIDENT_RESPONSE.md` with the approved concrete rotation, invalidation, certificate renewal/revocation, compromise escalation, recovery-verification, and closure procedures. Preserve provenance of the approving authority/decision. | `docs(security): operationalize security lifecycle procedures` | HPR-P0-012..013 |
| HPR-P0-015 | PENDING | Repository / Independent Audit | Code/Doc | Re-run the complete P0 verification gate on the exact final remediation SHA: full Maven verification, HTTP Workbench leakage tests, architecture/security tests, PostgreSQL/Flyway verification, application health/startup, deterministic OpenAPI artifact generation/inspection, and documentary re-audit of Checks 1–13. Close the audit gap only if all 13 checks pass; otherwise record failures and keep P1 blocked. | `docs(roadmap): close P0 audit verification gaps` | HPR-P0-008..014 |

### Phase P1 — Production Infrastructure & Survivability

| Code | Status | Domain/Module | Type | Exact execution requirement | Exact commit message | Depends on |
|---|---|---|---|---|---|---|
| HPR-P1-001 | PENDING — BLOCKED BY P0 AUDIT GAP | Runtime Architecture | Doc | Create `doc/architecture/RUNTIME_ARCHITECTURE.md` from approved production decisions only; do not invent deployment technology. | `docs(architecture): define production runtime architecture` | HPR-P0-015 completed |
| HPR-P1-002 | BLOCKED-DECISION | Application Runtime | Infra/Doc | Approve stateless multi-node runtime and load-distribution mechanism; document node state, readiness/liveness integration and failure behavior. | `docs(operations): define application high availability model` | HPR-P1-001 + owner decision |
| HPR-P1-003 | BLOCKED-DECISION | PostgreSQL | Infra/Doc | Approve PostgreSQL replication/failover topology, replication mode, failover authority, connection behavior, maintenance behavior and ownership. | `docs(database): define postgres high availability model` | HPR-P1-001 + owner decision |
| HPR-P1-004 | BLOCKED-DECISION | DR | Infra/Doc | Obtain owner-approved RTO/RPO, backup frequency/retention and WAL/PITR strategy; keep values TBD until approved. | `docs(operations): define disaster recovery objectives` | owner decision |
| HPR-P1-005 | PENDING | DR | Doc | Create `doc/operations/DISASTER_RECOVERY_RUNBOOK.md` with declaration authority, recovery roles, dependency order, restore steps and acceptance checks based on approved objectives. | `docs(operations): add disaster recovery runbook` | HPR-P1-004 |
| HPR-P1-006 | PENDING | HA | Doc | Create `doc/operations/HIGH_AVAILABILITY_ARCHITECTURE.md` covering approved application/database redundancy, failover, connection behavior and maintenance failover. | `docs(operations): add high availability architecture` | HPR-P1-002..003 |
| HPR-P1-007 | PENDING | Deployment | Doc | Create `DEPLOYMENT_RUNBOOK.md` and `ENVIRONMENT_CONFIGURATION.md`; require explicit production profile, secrets, PostgreSQL preparation, Flyway, startup, acceptance and rollback. | `docs(operations): add production deployment runbook` | HPR-P1-001..003 |
| HPR-P1-008 | PENDING | CI / API | Infra | Add real OpenAPI compatibility/breaking-change validation; generation/upload alone is not compatibility validation. | `ci(api): enforce openapi compatibility` | P0 closed |
| HPR-P1-009 | BLOCKED-DECISION | CD | Infra/Doc | After deployment target approval, implement controlled deployment automation, promotion, approval, post-deployment health verification and rollback; document in `CI_CD_RELEASE_GUIDE.md`. | `ci(release): add controlled deployment pipeline` | HPR-P1-007 + deployment target decision |
| HPR-P1-010 | PENDING | Observability | Infra/Doc | Create `OBSERVABILITY_AND_SRE.md` and implement approved alerting from existing Actuator/Prometheus signals; do not invent SLOs. | `docs(operations): establish observability operating model` | HPR-P1-001 |
| HPR-P1-011 | PENDING | Database Operations | Doc | Create `DATABASE_OPERATIONS_RUNBOOK.md` covering Flyway, backup/restore, connection exhaustion, failover, maintenance and migration failures. | `docs(database): add database operations runbook` | HPR-P1-003..005 |
| HPR-P1-012 | PENDING | Survivability Verification | Infra/Doc | Execute and record approved restore/PITR and failover exercises; close P1 only from measured evidence. | `docs(roadmap): close P1 survivability verification` | HPR-P1-005..011 |

### Phase P2 — Canonical Governance, API Contracts & Semantic Integration

| Code | Status | Domain/Module | Type | Exact execution requirement | Exact commit message | Depends on |
|---|---|---|---|---|---|---|
| HPR-P2-001 | PENDING | Governance | Doc | Complete canonical `doc/` governance/index structure and status metadata; preserve `docs/` as evidence. | `docs(governance): complete canonical documentation controls` | P0 closed |
| HPR-P2-002 | PENDING | Architecture | Doc | Create current/target-separated system context, architecture overview, bounded-context map, Hexagonal boundaries, module boundaries, cross-module contracts and technology stack. | `docs(architecture): establish canonical architecture set` | HPR-P2-001 |
| HPR-P2-003 | PENDING | Domain | Doc | Create `UBIQUITOUS_LANGUAGE.md`, domain ownership and focused topology/telemetry/alarm-incident-leak/assets-integrity/simulation-analytics-AI semantic documents. | `docs(domain): establish ubiquitous language baseline` | HPR-P2-001 |
| HPR-P2-004 | PENDING | Modules | Doc | Create one current-state `doc/modules/<module>.md` for each of the 24 implemented modules; do not create current-state module docs for agents/environment/otsecurity. | `docs(modules): add canonical module documentation` | HPR-P2-002..003 |
| HPR-P2-005 | PENDING | API | Code/Doc | Generate and version-control deterministic `doc/api/openapi.yaml`; create API overview, conventions, auth, error, versioning/compatibility and OpenAPI governance docs. | `docs(api): establish versioned api contract` | HPR-P1-008 |
| HPR-P2-006 | PENDING | Database | Doc | Create database architecture, schema ownership, Flyway policy and current generated data dictionary from current migrations/JPA evidence. | `docs(database): establish canonical database documentation` | HPR-P2-001 |
| HPR-P2-007 | PENDING | Semantic Remediation | Code/Doc | Inventory unresolved HMR/HMSR obligations against exact current source; mark each as completed, still required, blocked, or superseded with evidence. | `docs(model-remediation): reconcile remaining semantic obligations` | HPR-P2-003 |
| HPR-P2-008 | PENDING | Semantic Remediation | Code | Execute still-required semantic remediation in dependency order using revalidated HMSR obligations; do not restart completed HMRs without regression evidence. | `fix(model): continue reconciled semantic remediation` | HPR-P2-007 |
| HPR-P2-009 | PENDING | Semantic Remediation | Doc | Transfer permanent semantic decisions from legacy review/roadmaps into `doc/domain/` and `doc/modules/`, then preserve legacy files as execution history. | `docs(model-remediation): canonicalize semantic decisions` | HPR-P2-008 |
| HPR-P2-010 | PENDING | Data Governance | Doc | Create data governance, retention/archival, provenance and legacy-data migration documents without inventing retention values. | `docs(data): establish data governance baseline` | HPR-P2-001 |
| HPR-P2-011 | PENDING | Testing | Doc | Create test strategy, architecture testing, database testing, API testing and requirements traceability documents tied to executable evidence. | `docs(testing): establish verification documentation` | HPR-P2-002..006 |
| HPR-P2-012 | PENDING | Documentation CI | Infra | Add documentation validation for canonical links/status/index drift and deterministic OpenAPI contract checks. | `ci(docs): validate canonical documentation` | HPR-P2-001..011 |
| HPR-P2-013 | PENDING | Governance | Doc | Verify all 24 module docs, canonical indexes, API/database/domain docs and legacy supersession links; close P2. | `docs(roadmap): close P2 canonical governance` | HPR-P2-001..012 |

### Phase P3 — Deferred Industrial Scale / Future Capabilities

| Code | Status | Domain/Module | Type | Exact execution requirement | Exact commit message | Depends on |
|---|---|---|---|---|---|---|
| HPR-P3-001 | DEFERRED | Telemetry / Performance | Doc/Infra | Define approved telemetry throughput, burst, retention, latency, backlog and replay requirements and benchmark current PostgreSQL/JPA. | `perf(telemetry): establish capacity baseline` | P2 closed + approved requirements |
| HPR-P3-002 | DEFERRED | Telemetry / TimescaleDB | Infra | Adopt TimescaleDB only if HPR-P3-001 evidence justifies it; provision extension and convert only approved high-volume tables to hypertables. | `feat(telemetry): introduce timescaledb persistence` | HPR-P3-001 decision |
| HPR-P3-003 | DEFERRED | Telemetry / TimescaleDB | Infra | Add approved compression, retention and continuous aggregates only from verified workload/retention requirements. | `feat(telemetry): add timescale lifecycle policies` | HPR-P3-002 |
| HPR-P3-004 | DEFERRED | Telemetry | Code/Test | Replace per-entity JPA ingestion only where benchmarks prove it inadequate; retain new adapter behind Telemetry ports and verify burst/replay/failure behavior. | `perf(telemetry): add high throughput ingestion adapter` | HPR-P3-001..003 |
| HPR-P3-005 | DEFERRED | Spatial / Topology | Doc | Approve spatial architecture: route ownership, geometry model, CRS/SRID, KP reconciliation and query requirements; current scalar/KP storage remains authoritative until then. | `docs(topology): define spatial persistence architecture` | P2 closed + approved requirements |
| HPR-P3-006 | DEFERRED | Spatial / PostGIS | Infra/Code | Provision PostGIS and authoritative route geometry only after HPR-P3-005 approval; add appropriate spatial indexes. | `feat(topology): introduce postgis route persistence` | HPR-P3-005 |
| HPR-P3-007 | DEFERRED | Spatial / Topology | Code/Test | Reconcile route geometry with existing KP/linear referencing; verify SRID, geometry validity, route/KP consistency and query performance. | `test(topology): verify spatial route semantics` | HPR-P3-006 |
| HPR-P3-008 | DEFERRED | Future Capability Governance | Doc | Keep MQTT/Sparkplug and other audit-classified industrial extensions deferred until a separately approved requirement exists; update CURRENT status only after implementation evidence exists. | `docs(roadmap): reconcile deferred industrial capabilities` | approved requirement |

## 6. Immediate Next Execution

The next executable roadmap code is:

`HPR-P0-013 — docs(security): approve incident governance model`

HPR-P0-012 decision evidence:

- the project owner explicitly accepted the recommended secret/certificate lifecycle decisions on 2026-10-05;
- Security owns policy/risk/emergency authority; Platform/Operations owns production generation/deployment/rotation/verification; Identity/Directory and Database Operations own their respective service credentials;
- no secret-management product is fabricated before the production platform is selected;
- JWT HS256, PostgreSQL, and LDAP routine credential rotation baseline is 90 days, with immediate emergency replacement after credible compromise;
- JWT emergency invalidation uses coordinated replacement/restart under the current single-secret HS256 implementation; no dual-key overlap is claimed;
- administrator bootstrap credentials are one-time provisioning material and must be removed/disabled after provisioning;
- Platform/Infrastructure owns TLS termination; enterprise/private CA is preferred for internal trust and approved public CA for publicly trusted endpoints;
- private keys must remain in the approved certificate/secrets platform or hardware-backed facility and out of Git/images/templates/workstations;
- certificate expiry thresholds are 45/30/14/7 days;
- compromise response requires revocation/replacement, new key material, deployment, rejection of retired material where verifiable, and evidence preservation;
- emergency authority is role-based through Security Incident Commander plus Platform/Operations duty authority, with DB/Identity owner participation as applicable;
- every rotation requires health, authentication, relevant dependency, retired-material, and evidence checks;
- HPR-P0-011 exact head `64dacaf85720df75d4b4079ccb8630215f6b3410` passed CI run #525 before this task began.

The owner also explicitly accepted the previously proposed HPR-P0-013 incident-governance recommendations; HPR-P0-013 remains a separate execution task per the one-HPR-per-instruction rule.

Do not execute HPR-P0-014 or later work as part of HPR-P0-013.

## 7. Original P0 Closure Evidence

HPR-P0-001..007 remain valid evidence that the original source-level P0 defects were remediated and that the executable tree was green under the roadmap gate that existed at the time.

Executable verification anchor:

`359ae6d77bb9bb8f499941634760f4c375abb9ac`

GitHub Actions run #520 established successful Java 21 Maven verification, PostgreSQL/Testcontainers and Flyway execution, application startup/health, deterministic OpenAPI generation, and OpenAPI artifact upload.

That evidence is retained. It is **not** sufficient to satisfy the later independent audit's stronger exact-revision/API/documentation requirements.

## 8. Independent P0 Verification Audit — Gap Reconciliation

Audit baseline:

- pinned SHA: `dcf69e1a4a4b788cd125ba6289384642efc4caa0`
- audit date: 2026-10-05
- result: **FAIL — 7 checks VERIFIED; 6 checks FAILED**
- VERIFIED: Checks 1, 2, 3, 4, 5, 8, 10
- FAILED: Checks 6, 7, 9, 11, 12, 13

### 8.1 Disposition by failed check

| Audit check | Audit classification | Roadmap disposition |
|---|---|---|
| 6 — Workbench leakage regression tests | FAILED — SUPERFICIAL IMPLEMENTATION | HPR-P0-008 adds HTTP/controller/serialization coverage and direct prohibited-resource denial. |
| 7 — Exact `passwordHash` assertion | FAILED — SUPERFICIAL IMPLEMENTATION | HPR-P0-008 asserts both property-name and distinctive hash-marker absence in actual serialized responses. |
| 9 — OpenAPI security schemes | FAILED — NOT IMPLEMENTED in audit verdict, but audit body confirms source implementation exists | HPR-P0-009 treats this as an **exact-SHA generated-evidence gap**: require exact-head CI/artifact evidence and generated-contract inspection; do not reimplement the schemes unless regression evidence shows a code defect. |
| 11 — Security architecture / trust boundaries | FAILED — SUPERFICIAL IMPLEMENTATION | HPR-P0-010 adds current API/application/module, cross-module contract, telemetry/operator, and Workbench-exception boundaries from code evidence. |
| 12 — Threat model | FAILED — SUPERFICIAL IMPLEMENTATION | HPR-P0-011 adds telemetry registration/operator abuse scenarios with explicit asset/actor/path/boundary/impact/control/residual-risk fields. |
| 13 — Secrets / certificates / incident response | FAILED — SUPERFICIAL IMPLEMENTATION | HPR-P0-012 and HPR-P0-013 are owner-decision gates; HPR-P0-014 converts approved decisions into concrete lifecycle/incident procedures. |

### 8.2 Gate semantics

- The audit does **not** demonstrate a current credential-exposure exploit.
- Existing field/resource allowlists, credential protection, persistence-boundary constriction, actor provenance correction, architecture guardrails, and Workbench exposure documentation remain accepted unless regression evidence disproves them.
- P0 is reopened for **verification/completeness closure** only.
- P1 is blocked until HPR-P0-015 establishes all 13 audit checks as passing.
- Owner-dependent procedures must remain blocked rather than being fabricated.
