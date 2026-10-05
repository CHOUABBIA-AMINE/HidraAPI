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
| Production readiness | NOT ESTABLISHED — P0 security remediation is closed; P1 survivability is still incomplete |
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

### Phase P1 — Production Infrastructure & Survivability

| Code | Status | Domain/Module | Type | Exact execution requirement | Exact commit message | Depends on |
|---|---|---|---|---|---|---|
| HPR-P1-001 | NEXT | Runtime Architecture | Doc | Create `doc/architecture/RUNTIME_ARCHITECTURE.md` from approved production decisions only; do not invent deployment technology. | `docs(architecture): define production runtime architecture` | P0 closed |
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

`HPR-P1-001 — docs(architecture): define production runtime architecture`

## 7. P0 Closure Evidence

P0 security remediation is **CLOSED**.

Executable verification anchor:

`359ae6d77bb9bb8f499941634760f4c375abb9ac`

GitHub Actions:

- workflow: `HidraAPI CI`
- run: `#520`
- run id: `37308707618`
- job: `Java 21 Maven verification`
- job id: `111758552934`
- conclusion: `success`

Verified gates:

1. PostgreSQL 16 service became healthy.
2. Java 21 setup completed.
3. `./mvnw -B -q clean verify` completed successfully, covering the repository test suite including architecture/security regressions.
4. Flyway repeatedly validated all 82 migrations in database-backed tests; full-schema migration paths successfully reached version `v20261005.001`.
5. Application startup verification succeeded.
6. The CI OpenAPI step launched the built application with the test profile and polled `/actuator/health` successfully.
7. `/v3/api-docs` was fetched and deterministically normalized.
8. OpenAPI artifact `hidra-api-openapi-359ae6d77bb9bb8f499941634760f4c375abb9ac` was uploaded successfully as artifact id `11344898070`.

Exact-head equivalence:

- the next commit, HPR-P0-006 head `ad32650f71900a60c0ed7ee3123191ad26c688ed`, changed only:
  - `doc/roadmap/ULTIMATE_ROADMAP.md`
  - `doc/security/INCIDENT_RESPONSE.md`
  - `doc/security/SECRETS_AND_CERTIFICATES.md`
  - `doc/security/SECURITY_ARCHITECTURE.md`
  - `doc/security/THREAT_MODEL.md`
  - `doc/security/TRUST_BOUNDARIES.md`
- all six files are Markdown; no production source, tests, build files, migrations, runtime configuration, or CI workflow changed after the green verification anchor.
- this HPR-P0-007 closure changes only this Markdown roadmap.
- therefore the final P0 closure head has the same executable tree as the successful verification anchor. GitHub Actions did not create a run for HPR-P0-006 because `.github/workflows/ci.yml` ignores `docs/**` and `**/*.md` pushes.

P0 closure does **not** establish enterprise production readiness. Phase P1 production infrastructure and survivability remains required.

Do not execute HPR-P1-002 or later work as part of HPR-P1-001.
