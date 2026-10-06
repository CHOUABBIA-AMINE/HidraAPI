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
| Production readiness | ESTABLISHED FOR P1 — P0 security/audit verification and P1 production infrastructure/survivability verification are CLOSED; later P2/P3 work does not reopen this claim unless regression evidence is found |
| TimescaleDB | NOT IMPLEMENTED — DEFERRED / TARGET |
| PostGIS | NOT IMPLEMENTED — DEFERRED / TARGET |
| High availability | VERIFIED FOR P1 — repository implementation plus production-equivalent both-node application HA and PostgreSQL single-writer/fencing/recovery evidence reconciled under HPR-P1-012 |
| RTO | APPROVED TARGET — ≤ 60 minutes from DR declaration to service acceptance |
| RPO | APPROVED TARGET — ≤ 5 minutes of recoverable production data |

## 2. Governing Rules

1. Execute P0 before P1. P1 remains the production-readiness gate. P2 governance, documentation, API-contract and semantic-remediation work may proceed under the explicit evidence-block parallel-work exception recorded below when P1 is blocked solely on production-equivalent exercise evidence; this exception does not close P1 or establish production readiness. P3 remains deferred until both P1 and P2 are closed and its own approved requirements exist.
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
17. HPR-P0-015 has closed the independent-audit gap set. Phase P1 may proceed from `HPR-P1-001`, but production readiness remains NOT ESTABLISHED until P1 survivability requirements are completed.
18. Documentation-only changes under canonical `doc/**` or Markdown-only governance files use the lightweight documentation-validation workflow; they do not require the full Maven/PostgreSQL/OpenAPI pipeline solely because documentation changed. Full CI remains required when executable/configuration/migration/test/workflow paths change, when manually dispatched, or when a roadmap closure task explicitly requires full exact-head verification.
19. The 2026-10-06 independent P1 survivability re-audit at `2456aa9849da32254689daa6de4b3dbe0a1ca381` verified 3/12 checks and registers HPR-P1-021..029. These tasks supersede the prior assumption that HPR-P1-015..020 had only exercise blockers; concrete integration defects must be repaired before HPR-P1-012 closure.
20. Owner authorization on 2026-10-06 permits P2 work to proceed while HPR-P1-029 remains BLOCKED only because the remaining P1 blocker is production-equivalent operational evidence requiring an external environment. HPR-P1-029 and HPR-P1-012 remain BLOCKED/OPEN, production readiness remains NOT ESTABLISHED, no missing P1 evidence may be fabricated or inferred from P2 work, and no P3 task may execute until P1 and P2 are both closed.

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
- Application and PostgreSQL HA target architectures, repository implementation artifacts and production-equivalent both-node/failover evidence are verified for P1 under HPR-P1-012.
- DR objectives are owner-approved and verified for P1: intentional PITR evidence records achieved RPO 15 seconds and RTO 37 minutes, both within the approved ≤5 minute / ≤60 minute objectives.
- CI and controlled release automation include survivor, drain-to-zero, rejoin and rollback safety gates; P1 closure retains two-approval governance plus production-equivalent exact-artifact deployment/rollback evidence.
- TimescaleDB is not implemented and remains deferred/target.
- PostGIS is not implemented and remains deferred/target.
- GeoJSON/application geometry is not PostGIS persistence.
- Existing `docs/` contains valuable evidence but also stale and conflicting current-state material.
- Historical independent P0 verification audit at `dcf69e1a4a4b788cd125ba6289384642efc4caa0`: 7 checks VERIFIED; 6 checks FAILED. Those six gaps were remediated by HPR-P0-008..014 and closed by HPR-P0-015.
- Final independent P0 re-audit at closure SHA `bada4bb882b634762756dd115c7d66c864bd0b3b`: **PASS — 13/13 checks VERIFIED**. Exact-head CI run #529 succeeded and its generated OpenAPI artifact was independently inspected.
- Owner-authorized parallel progression is active as of 2026-10-06: P2 may proceed while HPR-P1-029 is blocked on external production-equivalent evidence. This is a sequencing exception only; P1 remains OPEN and production readiness remains NOT ESTABLISHED.

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
| HPR-P0-013 | COMPLETED — owner explicitly accepted the incident-governance baseline on 2026-10-05; `INCIDENT_RESPONSE.md` now records role-based incident command, technical/operations/DB/identity/legal/business ownership, SEV-1..SEV-4 taxonomy, immediate SEV-1 and 30-minute SEV-2 escalation targets, emergency containment authority, provider/database escalation ownership, Legal/Compliance notification authority, evidence ownership/integrity, mandatory SEV-1/2 PIR, and joint closure authority; personal contacts and jurisdiction-specific deadlines remain outside Git | Security / Incident Governance | Doc/Decision | Obtain owner-approved incident-governance decisions required by audit Check 13: incident commander/decision authority, SOC/on-call/security escalation contacts, severity taxonomy, notification/escalation timing, external IdP/LDAP/provider escalation contacts, legal/regulatory notification ownership, and evidence-retention ownership. Do not invent names, timelines, or regulatory obligations. | `docs(security): approve incident governance model` | accountable Security/Operations/Business owner decision |
| HPR-P0-014 | COMPLETED — approved lifecycle/governance decisions are operationalized as platform-neutral runbooks covering routine/emergency JWT rotation and invalidation, PostgreSQL/LDAP rotation, bootstrap handling, certificate renewal/revocation, rotation evidence, recovery acceptance gates, incident activation/evidence/containment/eradication/recovery/communication/closure/PIR; product-specific runtime/secrets/certificate/SIEM commands remain deferred to actual P1 architecture | Security Operations | Doc | After HPR-P0-012 and HPR-P0-013 approvals, replace the applicable TBD sections in `SECRETS_AND_CERTIFICATES.md` and `INCIDENT_RESPONSE.md` with the approved concrete rotation, invalidation, certificate renewal/revocation, compromise escalation, recovery-verification, and closure procedures. Preserve provenance of the approving authority/decision. | `docs(security): operationalize security lifecycle procedures` | HPR-P0-012..013 |
| HPR-P0-015 | COMPLETED — P0 AUDIT GAP CLOSED. Remediation baseline `850ee4770adc2aff50e81682223458ee28af1530` passed CI run #528. Final closure/audit baseline `bada4bb882b634762756dd115c7d66c864bd0b3b` passed exact-head CI run #529 / run id `37326280500`, job `111817745388`, including Java 21 `./mvnw -B -q clean verify`, PostgreSQL/Testcontainers/Flyway validation of 82 migrations, application startup/health, Workbench HTTP regressions, deterministic OpenAPI generation/security assertions, and artifact upload. Independent re-audit at the closure SHA: **PASS — 13/13 VERIFIED**. Exact-head artifact `11352572397` embeds the closure SHA; all 259 generated operations have explicit security declarations. | Repository / Independent Audit | Code/Doc | Re-run the complete P0 verification gate on the exact final remediation SHA: full Maven verification, HTTP Workbench leakage tests, architecture/security tests, PostgreSQL/Flyway verification, application health/startup, deterministic OpenAPI artifact generation/inspection, and documentary re-audit of Checks 1–13. Close the audit gap only if all 13 checks pass; otherwise record failures and keep P1 blocked. | `docs(roadmap): close P0 audit verification gaps` | HPR-P0-008..014 |

### Phase P1 — Production Infrastructure & Survivability

| Code | Status | Domain/Module | Type | Exact execution requirement | Exact commit message | Depends on |
|---|---|---|---|---|---|---|
| HPR-P1-001 | COMPLETED — canonical runtime architecture created from exact-head repository evidence; current Spring Boot/JVM, PostgreSQL/Flyway, stateless HTTP security, production-profile externalization, Actuator/Prometheus, in-process cache and STOMP broker are recorded separately from approved targets and unresolved deployment/HA/DR decisions; no deployment technology was invented | Runtime Architecture | Doc | Create `doc/architecture/RUNTIME_ARCHITECTURE.md` from approved production decisions only; do not invent deployment technology. | `docs(architecture): define production runtime architecture` | HPR-P0-015 completed |
| HPR-P1-002 | COMPLETED — owner accepted the application HA baseline on 2026-10-05: minimum two active HidraAPI nodes, product-neutral managed load distribution, no REST session affinity, readiness-driven traffic admission/removal, graceful drain/shutdown, no correctness dependency on node-local cache, and no clustered realtime claim until the current in-process STOMP broker is replaced/externalized; deployment product remains unselected | Application Runtime | Infra/Doc | Approve stateless multi-node runtime and load-distribution mechanism; document node state, readiness/liveness integration and failure behavior. | `docs(operations): define application high availability model` | HPR-P1-001 + owner decision |
| HPR-P1-003 | COMPLETED — owner-approved PostgreSQL HA model documented: one writable primary, at least one local synchronous streaming standby where approved latency permits, optional remote asynchronous standby for DR separation, one stable application database endpoint, Database Operations-controlled failover/switchover, explicit fencing/split-brain prevention, reconnect-based application recovery, and no assumption that replicas replace backups; no HA product selected | PostgreSQL | Infra/Doc | Approve PostgreSQL replication/failover topology, replication mode, failover authority, connection behavior, maintenance behavior and ownership. | `docs(database): define postgres high availability model` | HPR-P1-001 + owner decision |
| HPR-P1-004 | COMPLETED — owner-approved DR objectives documented: RTO ≤ 60 minutes, RPO ≤ 5 minutes, continuous WAL archiving/PITR, successful daily backup coverage, 35-day operational backup retention, retained monthly recovery points subject to enterprise records policy, independent backup storage/failure domain, role-based recovery authority, and mandatory restore/PITR evidence; no backup product selected | DR | Infra/Doc | Obtain owner-approved RTO/RPO, backup frequency/retention and WAL/PITR strategy; keep values TBD until approved. | `docs(operations): define disaster recovery objectives` | owner decision |
| HPR-P1-005 | COMPLETED — platform-neutral DR runbook created from the approved HPR-P1-004 objectives, covering declaration/containment, role activation, recovery-point selection, PostgreSQL restore + WAL/PITR, single-writer verification, stable endpoint restoration, secrets/configuration, HidraAPI startup, Flyway/JPA validation, security/read-write acceptance, traffic restoration, RTO/RPO measurement, abort/escalation conditions, and mandatory evidence; vendor-specific commands remain deferred | DR | Doc | Create `doc/operations/DISASTER_RECOVERY_RUNBOOK.md` with declaration authority, recovery roles, dependency order, restore steps and acceptance checks based on approved objectives. | `docs(operations): add disaster recovery runbook` | HPR-P1-004 |
| HPR-P1-006 | COMPLETED — canonical product-neutral HA architecture consolidates the approved application and PostgreSQL models: minimum two active HidraAPI nodes, readiness-based traffic admission, stateless REST/no sticky-session correctness, node-local cache constraints, realtime clustering restriction, one writable PostgreSQL primary with local synchronous standby where latency permits, optional remote asynchronous standby, stable database endpoint, controlled/fenced failover, maintenance drain/switchover, and explicit degraded-mode/acceptance evidence; implementation products and measured failover remain pending | HA | Doc | Create `doc/operations/HIGH_AVAILABILITY_ARCHITECTURE.md` covering approved application/database redundancy, failover, connection behavior and maintenance failover. | `docs(operations): add high availability architecture` | HPR-P1-002..003 |
| HPR-P1-007 | COMPLETED — product-neutral production deployment runbook and environment configuration created from repository evidence and approved runtime/HA/security decisions; they require exact artifact identity, explicit production profile, externalized secrets, stable PostgreSQL endpoint, controlled Flyway/JPA validation, serialized migration authority, readiness-based admission, acceptance checks, evidence capture and rollback/abort criteria; hosting/traffic/secret-manager/TLS/CD products remain unselected | Deployment | Doc | Create `DEPLOYMENT_RUNBOOK.md` and `ENVIRONMENT_CONFIGURATION.md`; require explicit production profile, secrets, PostgreSQL preparation, Flyway, startup, acceptance and rollback. | `docs(operations): add production deployment runbook` | HPR-P1-001..003 |
| HPR-P1-008 | COMPLETED — CI now generates OpenAPI for the exact current revision and actual Git base revision, then executes a repository-owned backward-compatibility checker that fails on supported breaking changes including removed paths/operations/parameters/responses/content types, newly required request inputs, incompatible request/response schema changes, new response enum values, and public-to-authenticated operation changes; OpenAPI artifact publication remains after the gate | CI / API | Infra | Add real OpenAPI compatibility/breaking-change validation; generation/upload alone is not compatibility validation. | `ci(api): enforce openapi compatibility` | P0 closed |
| HPR-P1-009 | BLOCKED-DECISION — independent P1 audit confirms deployment automation is not implemented; retained as the original design-stage task and operationally superseded by HPR-P1-018 after HPR-P1-013 selects the production target | CD | Infra/Doc | After deployment target approval, implement controlled deployment automation, promotion, approval, post-deployment health verification and rollback; document in `CI_CD_RELEASE_GUIDE.md`. | `ci(release): add controlled deployment pipeline` | HPR-P1-007 + deployment target decision |
| HPR-P1-010 | BLOCKED-DECISION — operating model is complete, but independent P1 audit confirms executable alerting/SRE policy is not implemented; retained as the design-stage task and operationally completed only through HPR-P1-019 after HPR-P1-013 selects the monitoring/alert-routing platform and owners approve SLIs/SLOs/thresholds | Observability | Infra/Doc | Create `OBSERVABILITY_AND_SRE.md` and implement approved alerting from existing Actuator/Prometheus signals; do not invent SLOs. | `docs(operations): establish observability operating model` | HPR-P1-001 |
| HPR-P1-011 | COMPLETED — canonical product-neutral database operations runbook created for PostgreSQL/Flyway operations, covering pre-change checks, schema migration authority, migration failure handling, connection-pool exhaustion, primary/standby failover and switchover, backup/WAL/PITR operations, maintenance, credential rotation coordination, data-integrity incidents, observability/evidence requirements, and stop/escalation criteria; no HA/backup/monitoring product or vendor command was invented | Database Operations | Doc | Create `DATABASE_OPERATIONS_RUNBOOK.md` covering Flyway, backup/restore, connection exhaustion, failover, maintenance and migration failures. | `docs(database): add database operations runbook` | HPR-P1-003..005 |
| HPR-P1-012 | COMPLETED — P1 CLOSED. HPR-P1-021..029 are complete; canonical production-equivalent evidence is retained in `doc/operations/P1_SURVIVABILITY_EXERCISE_EVIDENCE_2026-10-06.md`. Final reconciliation verifies all 12 independent P1 audit checks: preserved checks 1/7/8 plus remediated-and-exercised checks 2–6 and 9–12. Measured RPO 15s ≤5m and RTO 37m ≤60m; both-node REST HA, PostgreSQL single-writer/fencing/Hikari recovery, exact-schema PITR, controlled deployment/rollback, alert delivery/recovery, database maintenance acceptance and independent repo2/bootstrap retention evidence are retained. This closure commit is valid only with successful exact-head full CI, including production artifact validators, Java 21 `clean verify`, PostgreSQL/Flyway startup verification and deterministic OpenAPI compatibility/publication gates. | Survivability Verification | Infra/Doc | Close P1 only after all repository defects identified by the 2026-10-06 re-audit are remediated and the integrated production-equivalent exercise evidence is retained. | `docs(roadmap): close P1 survivability verification` | HPR-P1-021..029 |
| HPR-P1-013 | COMPLETED — owner accepted the production technology stack on 2026-10-05 and subsequently approved the remaining P1 owner values: 35-day operational retention plus 12 monthly recovery points retained for 12 months under `HIDRA-P1-BACKUP-RETENTION-001`, two-reviewer GitHub production approval, independent monitoring, 30-day Prometheus and 90-day operational-log retention, defined alert-routing domains, and the production-equivalent exercise strategy. Actual deployed hostnames/endpoints/versions remain evidence rather than architecture decisions. | Production Infrastructure Decisions | Decision/Doc | Obtain and record the concrete production infrastructure selections required to implement P1: deployment/runtime target, traffic-distribution mechanism, PostgreSQL HA/promotion/fencing/stable-endpoint mechanism, backup/WAL/PITR tooling and protected storage, secret/configuration injection mechanism, monitoring/alert-routing/logging platform, and the controlling enterprise policy identifier plus exact monthly backup recovery-point retention/hold rule. Preserve approved RTO/RPO and do not invent products or retention values. | `docs(operations): approve production infrastructure stack` | accountable Platform/DB/Security/Operations owner decisions |
| HPR-P1-014 | COMPLETED — added a pre-bean Spring EnvironmentPostProcessor production startup guard registered through `META-INF/spring.factories`; production intent now requires the production profile and mandatory datasource/CORS/JWT configuration, rejects disabled/non-JWT production authentication, and requires a bootstrap password when bootstrap is enabled. Automated negative tests cover production-intent + wrong profile and each mandatory production input, while non-production startup remains unaffected. `HIDRA_ENVIRONMENT=production` is now the explicit independent production-hosting marker paired with `SPRING_PROFILES_ACTIVE=production`. | Runtime Safety | Code/Test | Enforce production-context startup safety so an omitted or wrong Spring profile cannot silently fall back to dev/default behavior in an approved production deployment; validate required production configuration and add automated negative tests for missing/wrong profile and mandatory inputs. | `fix(runtime): enforce production startup profile` | HPR-P1-013 deployment/runtime decision |
| HPR-P1-015 | COMPLETED — production-equivalent both-node loss/rejoin evidence retained by HPR-P1-029;  repository implementation now binds the approved Linux VM/systemd/HAProxy design: two REST nodes, readiness-based HAProxy health admission, no configured REST stickiness, systemd SIGTERM/restart semantics, drain/rejoin scripts, node-specific runtime roles, and an explicit single-active realtime backend. `hidra.platform.realtime.enabled` now actually conditions STOMP broker creation, allowing node B realtime to be disabled. Static CI validation and a destructive production-equivalent one-node-loss evidence script are included. Actual two-VM node-loss evidence is still required before this HPR can be called measured/verified and remains an HPR-P1-012 closure prerequisite. | Application HA | Infra/Code/Test/Doc | Implement the approved two-node HidraAPI runtime on the selected deployment/traffic platform, readiness-based admission/removal, graceful drain/replacement, and safe node-local-state behavior. Resolve production treatment for local cache, background executors, and realtime (shared mechanism, explicit single-active mode, or disabled mode) and demonstrate one-node loss without hidden sticky-session correctness. | `feat(runtime): implement application high availability` | HPR-P1-013 + HPR-P1-002 + HPR-P1-006 |
| HPR-P1-016 | COMPLETED — production-equivalent Patroni failover/single-writer/fencing/Hikari recovery evidence retained by HPR-P1-029;  repository implementation now binds Patroni + three-member TLS-authenticated etcd + HAProxy stable write endpoint to the approved PostgreSQL HA model. HAProxy routes only to the node whose Patroni `/primary` health check succeeds. Production Hikari settings now explicitly expose connection/validation/idle/max-lifetime/keepalive controls with documented transaction-failure responsibility. Static CI validation plus a destructive controlled Patroni switchover harness verify single-primary topology, endpoint writability, former-primary demotion, new connection establishment and HidraAPI readiness recovery. Actual production-equivalent failover/switchover evidence is still required before this HPR is measured/verified. | PostgreSQL HA / Connections | Infra/Code/Test/Doc | Implement the selected PostgreSQL primary/standby replication, promotion/fencing and stable-endpoint mechanism; define explicit Hikari/JDBC recovery settings including approved max-lifetime/keepalive/idle behavior where applicable; document interrupted-transaction responsibility; execute controlled failover and prove connection replacement, single-writer authority, endpoint redirection and application read/write recovery. | `feat(database): implement postgres failover recovery` | HPR-P1-013 + HPR-P1-003 + HPR-P1-011 |
| HPR-P1-017 | COMPLETED — production-equivalent backup/PITR, repo2 restore and bootstrap retention evidence retained by HPR-P1-029;  pgBackRest now uses repo1 for daily 35-day operational recovery and repo2 for owner-approved monthly retention under `HIDRA-P1-BACKUP-RETENTION-001`: one repo2 full backup per month, count-based retention of 12 full backups, WAL archived to all configured repositories with async archiving, monthly continuity/age checks, and PITR harness selection of repo1 or repo2. Repository binding is complete; production-equivalent proof of repo/storage independence, retained repo2 restore, WAL continuity, intentional PITR and measured RPO/RTO remains required. | Backup / WAL / PITR | Infra/Test/Doc | Bind the approved DR model to the selected backup and storage implementation with executable backup, WAL archive, restore, recovery-target/PITR, service-control and validation procedures; enforce the 35-day operational window and the owner-approved monthly retention/hold rule; verify protected-copy independence and produce a restorable current-schema backup chain. | `feat(database): implement backup and pitr operations` | HPR-P1-013 + HPR-P1-004..005 + HPR-P1-011 |
| HPR-P1-018 | COMPLETED — production-equivalent exact-artifact deployment/rollback and two-approval governance evidence retained by HPR-P1-029;  added manual controlled release automation with exact-SHA main ancestry and exact-SHA full-CI success gates, immutable JAR + SHA-256 manifest, GitHub `production` Environment boundary, sequential HAProxy drain/deploy/rejoin, mandatory production/Vault runtime preflight, configurable authenticated database-backed acceptance, and schema-aware rollback gating. The systemd runtime now requires the Vault-rendered secret file. Static CI validates release artifacts and `CI_CD_RELEASE_GUIDE.md` documents required production variables/secrets/protection. Repository source cannot prove required-reviewer protection is configured, and no production-equivalent deployment/rollback exercise has yet run. | CD / Release | Infra/Test/Doc | Complete former HPR-P1-009 after deployment-target approval: implement controlled release promotion, approval, exact-artifact deployment, explicit production-profile/configuration validation, post-deployment health/security/database acceptance, rollback gates and `doc/operations/CI_CD_RELEASE_GUIDE.md`; retain executable evidence from the selected target. | `ci(release): add controlled deployment pipeline` | HPR-P1-013..014 + HPR-P1-007 |
| HPR-P1-019 | COMPLETED — production-equivalent alert firing/delivery/acknowledgement/resolve evidence retained by HPR-P1-029;  owner approved the P1 SLI/SLO/threshold baseline on 2026-10-05. Repository implementation now provides Prometheus scrape configuration and executable alert rules for application availability/redundancy, HTTP 5xx/latency, CPU/JVM, Hikari, PostgreSQL HA/replication lag, pgBackRest backup/WAL continuity and monitoring self-health; Alertmanager routing templates for operations/database/security; Grafana Prometheus/Loki datasource provisioning; Loki baseline configuration; a repository-owned pgBackRest metrics exporter; static CI validation; and a synthetic Alertmanager routing exercise. Live production-equivalent rule firing, receiver delivery/escalation, resolve behavior, Loki ingestion and no-secret evidence are still required before this HPR is measured/verified. | Observability / SRE | Infra/Test/Doc | Complete former HPR-P1-010 after observability decisions: approve concrete SLIs/SLOs/evaluation windows and numeric thresholds, configure collection/dashboards/log routing as applicable, implement executable alert rules and on-call/escalation routing for application/HA/database/backup/security conditions, and verify representative alert firing, delivery and recovery without secret leakage. | `feat(operations): implement production alerting` | HPR-P1-013 + HPR-P1-010 operating model |
| HPR-P1-020 | COMPLETED — production-equivalent diagnostic/maintenance and authenticated pre/post application acceptance evidence retained by HPR-P1-029;  added executable read-only PostgreSQL inspection procedures for sessions, blocking locks, long-running queries/transactions, VACUUM/ANALYZE state, index usage, capacity, replication/slots and Flyway history; a composite health report also records Patroni topology and pgBackRest health. Guarded maintenance scripts provide explicit VACUUM (ANALYZE, VERBOSE), REINDEX INDEX CONCURRENTLY and targeted pg_terminate_backend procedures only with operator opt-in. CI statically validates all SQL/shell artifacts and the database runbook now cross-links the selected Patroni/pgBackRest implementation. Actual execution against production-equivalent PostgreSQL and controlled maintenance/post-acceptance evidence remains required before measured verification. | Database Operations | Infra/Test/Doc | Make the database runbook executable for the selected PostgreSQL/backup/HA stack: add reviewed maintenance and inspection commands for sessions, locks, long-running work, VACUUM/ANALYZE strategy, index/bloat/capacity checks, replication/backup health and Flyway failure diagnosis; validate the commands in production-equivalent infrastructure and cross-link them to HA/backup procedures. | `docs(database): operationalize database procedures` | HPR-P1-013 + HPR-P1-016..017 + HPR-P1-011 |
| HPR-P1-021 | COMPLETED — both committed pgBackRest backup services now bind the rendered `/etc/pgbackrest/pgbackrest.conf` path through `HIDRA_PGBACKREST_CONFIG`; PITR acceptance no longer uses the audited migration-count threshold and instead requires an approved exact successful Flyway `version|checksum` manifest, compares the restored history byte-for-byte, and retains digest/history evidence. A repository helper captures the trusted baseline without committing credentials or environment-specific data. Production-equivalent scheduled backup/WAL/PITR execution remains HPR-P1-029 evidence scope. | Backup / PITR | Infra/Test/Doc | Bind `HIDRA_PGBACKREST_CONFIG` into both committed pgBackRest systemd backup services through the approved external configuration mechanism; keep secrets external; harden restore/PITR validation so current-schema acceptance verifies the exact applied Flyway version/checksum set rather than only a migration count; preserve repo1/repo2 selection and WAL continuity checks. | `fix(database): bind scheduled backup configuration` | HPR-P1-017 |
| HPR-P1-022 | COMPLETED — HAProxy PostgreSQL primary health checks now match Patroni's required mutual-TLS REST contract: TLS is applied only to port-8008 health checks, the Patroni server certificate is CA-validated and hostname-verified per node, and HAProxy presents an externally rendered client certificate/private-key PEM while Patroni remains `verify_client: required`. Static validation now rejects the former plain-HTTP form and verifies the Patroni/HAProxy TLS contract. The controlled switchover harness records Patroni dynamic configuration and exactly-one-primary authority context before/after role change plus former-primary demotion; actual partition/fencing exercise evidence remains HPR-P1-029 scope. | PostgreSQL HA | Infra/Test/Doc | Make HAProxy primary health checks compatible with Patroni REST mutual TLS and required client authentication; validate the rendered HAProxy/Patroni transport contract; strengthen failover verification to record exactly one writable primary, former-primary demotion and the selected fencing/split-brain-prevention evidence. | `fix(database): align patroni health checks with tls` | HPR-P1-016 |
| HPR-P1-023 | COMPLETED — the application HA exercise now proves both nodes are directly ready before disruption, continuously issues a caller-selected authenticated representative REST request through HAProxy during each one-node loss/rejoin window, fails on any business-request interruption, verifies the restarted node directly becomes ready again before the other node is stopped, and retains separate continuity evidence. Authentication is supplied only through an external curl config and no business endpoint/credential is invented. Actual production-equivalent execution remains HPR-P1-029 evidence scope; P1 realtime remains explicitly single-active and is not claimed HA. | Application HA | Infra/Test/Doc | Strengthen the two-node HA exercise so it proves both nodes are initially admitted, continuously probes a representative authenticated REST operation during each node loss, verifies survivor continuity, waits for the restarted node to rejoin before the next loss, and retains per-node removal/rejoin evidence without claiming realtime clustering. | `test(runtime): strengthen application ha exercise` | HPR-P1-015 |
| HPR-P1-024 | COMPLETED — implementation SHA `117da3131daa4b98a55098573c4ee933a1c012f9` passed exact-head full CI #551 / run id `37428720127`. The failover harness now requires authenticated database-backed HidraAPI acceptance before/after role change, records `hikaricp.connections.creation` before failover, requires the application-managed Hikari creation count to increase during recovery, records recovery elapsed time, and explicitly preserves caller/use-case responsibility for interrupted or in-doubt transactions rather than claiming transparent retry. Actual production-equivalent failover execution remains HPR-P1-029 scope. | PostgreSQL / Application Recovery | Code/Test/Infra | Extend the database failover exercise to prove HidraAPI-managed Hikari connection replacement through the stable endpoint using authenticated database-backed application acceptance before and after role change; record recovery timing and interrupted-transaction responsibility without inventing transparent transaction retry. | `test(database): verify application failover recovery` | HPR-P1-022 |
| HPR-P1-025 | COMPLETED — implementation SHA `c692ab684ef79c8295d3216adafd6daf5dc1db46` passed exact-head full CI #552 / run id `37429745480`. Controlled rollout now verifies the surviving REST node before target drain, waits for target HAProxy sessions to reach zero before installation, verifies local readiness plus authenticated database-backed acceptance before rejoin, waits for HAProxy to report the rejoined node UP, and applies equivalent acceptance/rejoin gates to schema-compatible rollback. GitHub `production` Environment two-reviewer enforcement remains an external repository setting and is NOT ESTABLISHED by the available connector; production-equivalent deployment/rollback evidence remains HPR-P1-029 scope. | Release / Deployment | Infra/Test/Doc | Harden controlled rollout so the surviving application node is verified before draining the target, drain completion/active-request safety is explicitly checked before installation, rejoin is verified before proceeding, rollback follows the same availability gates, and repository evidence records the required GitHub production Environment reviewer protection without fabricating settings that cannot be observed. | `fix(release): enforce availability preserving rollout` | HPR-P1-018 + HPR-P1-023 |
| HPR-P1-026 | COMPLETED — implementation SHA `472811292c271241c94647e71deb1ffe59086d0c` passed exact-head full CI #553 / run id `37430996964`. Prometheus now has an authenticated bearer-token-file path for protected HidraAPI metrics, production explicitly enables the `http.server.requests` histogram required by p95 rules, both application and PostgreSQL HAProxy configurations expose dedicated monitoring-only Prometheus exporters, Prometheus scrapes both proxies, Alertmanager evaluates security/database/backup domains before the generic critical route, and static/native validation asserts those bindings. Live rule firing, receiver delivery and resolve evidence remain HPR-P1-029 scope. | Observability / SRE | Infra/Test/Doc | Complete production observability wiring: provide an approved authenticated scrape path for protected HidraAPI metrics without exposing them publicly, enable the HTTP histogram required by latency rules, expose/scrape HAProxy metrics used by availability rules, correct Alertmanager route precedence so critical database/security/backup alerts reach their domain receivers as approved, and extend static/native validation for the resulting Prometheus/Alertmanager/HAProxy configuration. | `fix(operations): wire production observability signals` | HPR-P1-019 |
| HPR-P1-027 | COMPLETED — implementation SHA `cafdc42270085ddf513a23a514f437ef39f7b842` passed exact-head full CI #555 / run id `37433450282`. Database operations now include PostgreSQL index validity/readiness/liveness inspection, extension-free dead-tuple/object-size/index-ratio space-risk triage without assuming unapproved extensions, host/filesystem byte+inode capacity capture outside SQL, and a guarded maintenance-validation harness requiring authenticated database-backed HidraAPI acceptance before and after the selected action. Actual production-equivalent diagnostic/maintenance execution remains HPR-P1-029 evidence scope. | Database Operations | Infra/Test/Doc | Complete database health inspection coverage with PostgreSQL index validity/readiness and a reviewed bloat/space-risk method that does not assume unapproved extensions; add host/filesystem capacity evidence outside SQL; retain guarded maintenance semantics and validate diagnostic/maintenance procedures with post-maintenance acceptance in production-equivalent infrastructure. | `fix(database): complete operational health inspections` | HPR-P1-020 |
| HPR-P1-028 | COMPLETED — implementation SHA `e12a2205f397e68edf1bda96211a99966577d501` passed exact-head full CI #559 / run id `37435329469`. Repo2 validation now distinguishes bootstrap from mature policy coverage, requires one distinct completed full recovery point for each required UTC calendar month from the approved 2026-10 policy start, rejects duplicate/missing required months, and retains the 12-point mature horizon; Prometheus now runs with executable `30d` TSDB retention and Loki with `90d` retention plus compactor deletion. Protected-copy independence and retained repo2 restorability remain HPR-P1-029 measured evidence scope. | Retention / Archival | Infra/Test/Doc | Enforce the approved P1 retention controls in executable configuration: distinguish bootstrap from mature monthly coverage and verify distinct monthly repo2 recovery points up to the required 12-month horizon; bind Prometheus 30-day and Loki 90-day retention/deletion settings; verify repo2 protected-copy independence and retained recovery-point restorability in the later production-equivalent exercise. | `fix(operations): enforce p1 retention controls` | HPR-P1-021 + HPR-P1-026 |
| HPR-P1-029 | COMPLETED — production-equivalent evidence campaign executed 2026-10-06 against deployed HidraAPI SHA `66f6d7f12d1f7d52f8725cd4747cf4c777bfd29a`. Repository reconciliation confirms the authoritative 82-migration tail. Retained operator evidence demonstrates both-node REST continuity/rejoin, Patroni single-writer failover with watchdog fencing and Hikari recovery, intentional PITR with marker assertions, RPO 15s and RTO 37m within approved objectives, exact-artifact deployment/rollback with two approvals, alert delivery/acknowledgement/resolve including security routing, guarded database maintenance with authenticated acceptance, and independent repo2 restore plus October 2026 bootstrap retention. Canonical evidence: `doc/operations/P1_SURVIVABILITY_EXERCISE_EVIDENCE_2026-10-06.md`. | Survivability Exercise | Infra/Test/Doc | Execute and retain one governed production-equivalent P1 evidence campaign after HPR-P1-021..028: scheduled backup/WAL operation; intentional current-schema PITR with pre-target marker present/post-target marker absent, exact Flyway validation, achieved RPO and declaration-to-service-acceptance RTO; two-node application loss/rejoin with continuous representative REST requests; PostgreSQL failover with single-writer/fencing/stable-endpoint and HidraAPI/Hikari recovery; exact-artifact deployment plus approved rollback; representative alert firing/domain delivery/resolve; database diagnostic/maintenance acceptance; and retention/independent-repo restore evidence. Record immutable environment/tool versions and evidence identifiers. | `test(operations): execute p1 survivability exercises` | HPR-P1-021..028 |

### P1 Independent Audit Remediation Baseline — 2026-10-05

Independent audit baseline: `f46f6c1ed7e324f66a0a26422a6dfa6da8bc3689`.

Verdict: **FAIL — 2 / 12 checks VERIFIED**.

Verified controls preserved:

- Check 1 — concrete RTO/RPO: **VERIFIED**;
- Check 8 — OpenAPI breaking-change gate: **VERIFIED**.

The audit does not invalidate completed architecture/runbook HPRs. It proves that design-stage completion is not equivalent to infrastructure implementation or measured survivability. P1 therefore adds HPR-P1-013..020 as implementation remediation before HPR-P1-012 closure.

| Audit check | Audit status | Roadmap disposition |
|---|---|---|
| 2 — executable backup/restore/PITR | FAILED — SUPERFICIAL | HPR-P1-013 selects tooling; HPR-P1-017 implements executable backup/WAL/PITR procedures; HPR-P1-012 measures recovery |
| 3 — DR exercise validation | FAILED — NOT IMPLEMENTED | HPR-P1-017 establishes usable recovery chain; HPR-P1-012 executes measured restore/PITR |
| 4 — PostgreSQL HA topology | FAILED — SUPERFICIAL | HPR-P1-013 selects mechanism; HPR-P1-016 implements replication/promotion/fencing/stable endpoint and validates failover |
| 5 — application redundancy/statelessness | FAILED — SUPERFICIAL | HPR-P1-015 implements selected two-node traffic/admission/drain model and resolves local-state/realtime behavior |
| 6 — connection-pool failover resiliency | FAILED — SUPERFICIAL | HPR-P1-016 owns explicit connection-recovery settings and failover reconnection evidence |
| 7 — safe production profile enforcement | FAILED — SUPERFICIAL | HPR-P1-014 adds production-context startup guard and negative tests |
| 9 — deployment automation | FAILED — NOT IMPLEMENTED | HPR-P1-013 selects target; HPR-P1-018 implements controlled CD and release truthfulness |
| 10 — alerting/SRE | FAILED — SUPERFICIAL | HPR-P1-013 selects platform; HPR-P1-019 approves/implements SLIs/SLOs/thresholds/routing and verifies delivery |
| 11 — database operations | FAILED — SUPERFICIAL | HPR-P1-020 adds executable maintenance/inspection procedures after HA/backup tooling exists |
| 12 — P1 backup retention | FAILED — SUPERFICIAL | HPR-P1-013 identifies controlling policy and exact monthly rule; HPR-P1-017 enforces it in selected storage/tooling |

HPR-P1-012 closure evidence must include, at minimum:

- exact deployed/runtime/database/backup/monitoring implementation versions or immutable identifiers;
- current-schema baseline including all 82 Flyway migrations;
- at least two simultaneously active HidraAPI nodes and observed readiness-based traffic removal/rejoin;
- PostgreSQL controlled failover with verified fencing/single-writer authority and stable-endpoint redirection;
- observed Hikari/JDBC connection replacement and documented interrupted-transaction behavior;
- a retained backup restore plus PITR to an intentionally selected point;
- measured RTO and RPO against the approved ≤60 minute / ≤5 minute objectives;
- post-recovery Flyway/JPA, authentication/authorization, representative read/write and security acceptance;
- executed production-profile guard failure tests;
- controlled CD promotion/approval/rollback evidence;
- representative production alert firing, delivery/escalation and recovery evidence;
- database maintenance/inspection procedure validation;
- verified enforcement of 35-day operational backup retention plus the exact approved monthly retention/hold rule;
- exact-head full verification evidence for the final closure SHA.

### P1 Independent Survivability Re-Audit — 2026-10-06

Audit baseline: `2456aa9849da32254689daa6de4b3dbe0a1ca381`.

Verdict: **FAIL — 3 / 12 checks VERIFIED**.

Preserved VERIFIED checks:

- Check 1 — concrete RTO/RPO;
- Check 7 — safe production profile enforcement;
- Check 8 — OpenAPI breaking-change CI gate.

Exact-head workflow evidence:

- documentation validation run `37389104565`: **SUCCESS**;
- no exact-head full CI run exists for the audit SHA because the audit SHA is documentation-only under the approved workflow split;
- parent executable SHA `719c8bf06f3f54c5b0fa2c7a4369ae08ac7e0251` passed full CI run `37388204860`; unchanged executable content is useful supporting evidence but is not mislabeled as exact-audit-SHA full CI.

The re-audit found both **repository defects** and **missing measured exercises**. Therefore HPR-P1-012 must not be attempted directly. HPR-P1-021..028 repair the executable/configuration gaps first; HPR-P1-029 then executes the integrated production-equivalent evidence campaign.

| Audit check | Re-audit status | Roadmap disposition |
|---|---|---|
| 2 — executable restore/PITR | FAILED — SUPERFICIAL | HPR-P1-021 binds scheduled pgBackRest configuration and exact Flyway recovery acceptance; HPR-P1-029 proves scheduled backup/WAL + restore/PITR |
| 3 — DR exercise validation | FAILED — NOT IMPLEMENTED | HPR-P1-029 executes intentional PITR with marker assertions and measured approved RTO/RPO |
| 4 — PostgreSQL HA topology | FAILED — SUPERFICIAL | HPR-P1-022 repairs Patroni mTLS/HAProxy primary checks and failover/fencing verification; HPR-P1-029 executes it |
| 5 — application redundancy/statelessness | FAILED — SUPERFICIAL | HPR-P1-023 strengthens continuous service and rejoin evidence; HPR-P1-029 executes two-node loss/rejoin |
| 6 — connection-pool failover resiliency | FAILED — SUPERFICIAL | HPR-P1-024 adds authenticated database-backed HidraAPI/Hikari recovery evidence; HPR-P1-029 executes it |
| 7 — safe production profile | VERIFIED | Preserve HPR-P1-014; reopen only on regression |
| 8 — OpenAPI compatibility gate | VERIFIED | Preserve HPR-P1-008; reopen only on regression |
| 9 — deployment automation | FAILED — SUPERFICIAL | HPR-P1-025 adds survivor/drain/rejoin/rollback gates and approval-control evidence; HPR-P1-029 exercises deployment/rollback |
| 10 — alerting/SRE | FAILED — SUPERFICIAL | HPR-P1-026 repairs scrape/histogram/HAProxy/routing integration; HPR-P1-029 proves firing, delivery and resolve |
| 11 — database operations | FAILED — SUPERFICIAL | HPR-P1-027 completes index/space inspection and production-equivalent maintenance acceptance |
| 12 — retention/archival | FAILED — SUPERFICIAL | HPR-P1-028 binds monthly/Prometheus/Loki retention; HPR-P1-029 proves independent retained restore and mature-policy evidence |

P1 remains **OPEN** and production readiness remains **NOT ESTABLISHED**.

### P1 Final Survivability Closure — 2026-10-06

Closure task: `HPR-P1-012 — docs(roadmap): close P1 survivability verification`.

Final disposition: **PASS — 12 / 12 P1 checks VERIFIED**.

| Check | Final status | Closure evidence |
|---:|---|---|
| 1 — concrete RTO/RPO | VERIFIED | Approved ≤60m / ≤5m objectives; measured HPR-P1-029 RTO 37m and RPO 15s |
| 2 — executable restore/PITR | VERIFIED | HPR-P1-021 exact Flyway acceptance + HPR-P1-029 intentional current-schema PITR |
| 3 — DR exercise validation | VERIFIED | Pre/post markers, selected target, accepted service timestamp and measured RTO/RPO retained |
| 4 — PostgreSQL HA topology | VERIFIED | Patroni single-writer switchover, former-primary demotion and watchdog fencing retained |
| 5 — application redundancy/statelessness | VERIFIED | Both application nodes failed/rejoined independently with zero dropped authenticated REST requests |
| 6 — connection-pool failover resiliency | VERIFIED | Hikari connection replacement and application recovery observed after PostgreSQL role change |
| 7 — safe production profile | VERIFIED | Preserved HPR-P1-014 startup-guard implementation and regression coverage |
| 8 — OpenAPI compatibility gate | VERIFIED | Preserved HPR-P1-008 compatibility gate; final closure requires exact-head full CI |
| 9 — deployment automation | VERIFIED | Availability-preserving exact-artifact rollout/rollback exercised with two approvals |
| 10 — alerting/SRE | VERIFIED | Warning/critical/database/security firing, delivery, acknowledgement and resolved-state evidence retained |
| 11 — database operations | VERIFIED | Reviewed maintenance executed with authenticated application acceptance before/after |
| 12 — retention/archival | VERIFIED | 35-day/12-month policy implementation plus independent repo2 restore and October-2026 bootstrap recovery point retained |

Canonical production-equivalent evidence: `doc/operations/P1_SURVIVABILITY_EXERCISE_EVIDENCE_2026-10-06.md`.

Evidence provenance remains explicit: repository-bound SHA/migration/configuration facts are independently verified from GitHub; deployed runtime versions, JAR digest, approvals, infrastructure logs, receiver identifiers and measured operational timestamps are retained as operator-supplied evidence from the production-equivalent environment.

P1 is **CLOSED** only when this closure SHA passes the repository's full exact-head CI gate. A failed exact-head gate reopens HPR-P1-012 for repair and prohibits a production-readiness claim.

### Phase P2 — Canonical Governance, API Contracts & Semantic Integration

| Code | Status | Domain/Module | Type | Exact execution requirement | Exact commit message | Depends on |
|---|---|---|---|---|---|---|
| HPR-P2-001 | COMPLETED — canonical `doc/` entry/index governance and documentation register established; current/target/historical authority is explicit and legacy `docs/` remains preserved as evidence | Governance | Doc | Complete canonical `doc/` governance/index structure and status metadata; preserve `docs/` as evidence. | `docs(governance): complete canonical documentation controls` | P0 closed |
| HPR-P2-002 | COMPLETED — canonical current/target-separated architecture set established from live package, ArchUnit, runtime/configuration and P1 infrastructure evidence; historical architecture material remains preserved and subordinate | Architecture | Doc | Create current/target-separated system context, architecture overview, bounded-context map, Hexagonal boundaries, module boundaries, cross-module contracts and technology stack. | `docs(architecture): establish canonical architecture set` | HPR-P2-001 |
| HPR-P2-003 | COMPLETED — canonical ubiquitous language, domain ownership and focused topology/telemetry, alarm-incident-leak, assets-integrity, and simulation-analytics-AI semantic baselines established from exact current source; AI/autonomous inference remains explicitly NOT ESTABLISHED | Domain | Doc | Create `UBIQUITOUS_LANGUAGE.md`, domain ownership and focused topology/telemetry/alarm-incident-leak/assets-integrity/simulation-analytics-AI semantic documents. | `docs(domain): establish ubiquitous language baseline` | HPR-P2-001 |
| HPR-P2-004 | COMPLETED — canonical current-state documents created for all 24 implemented module roots from live domain/API/application/persistence/contract inventories; no current-state docs created for agents/environment/otsecurity | Modules | Doc | Create one current-state `doc/modules/<module>.md` for each of the 24 implemented modules; do not create current-state module docs for agents/environment/otsecurity. | `docs(modules): add canonical module documentation` | HPR-P2-002..003 |
| HPR-P2-005 | COMPLETED — deterministic OpenAPI 3.1 contract version-controlled from exact executable P1 closure CI artifact; canonical API overview, conventions, authentication/authorization, error-model limitation, versioning/compatibility and OpenAPI-governance documents established; shared machine-readable error envelope remains explicitly NOT ESTABLISHED | API | Code/Doc | Generate and version-control deterministic `doc/api/openapi.yaml`; create API overview, conventions, auth, error, versioning/compatibility and OpenAPI governance docs. | `docs(api): establish versioned api contract` | HPR-P1-008 |
| HPR-P2-006 | COMPLETED — canonical current database architecture, schema ownership, Flyway policy and generated persistence dictionary established from 82 current Flyway migrations, 469 current module JPA persistence entities, production configuration and closed P1 PostgreSQL/backup evidence; stale pre-closure DB stage documents retained as historical provenance | Database | Doc | Create database architecture, schema ownership, Flyway policy and current generated data dictionary from current migrations/JPA evidence. | `docs(database): establish canonical database documentation` | HPR-P2-001 |
| HPR-P2-007 | COMPLETED — exact-source reconciliation established: HMR-005 corrected to completed, HMR-009 confirmed completed/stale carry-over removed, HMR-054 historical blocker resolved by current Party→Topology contract, HMR-050..106 reconciled to 56 still-required + 1 blocked (HMR-080), 0 superseded; legacy roadmap preserved as history | Semantic Remediation | Code/Doc | Inventory unresolved HMR/HMSR obligations against exact current source; mark each as completed, still required, blocked, or superseded with evidence. | `docs(model-remediation): reconcile remaining semantic obligations` | HPR-P2-003 |
| HPR-P2-008 | IN PROGRESS — HMR-050 and attached Batches 1..6 implemented; 17 completed, 39 still-required HMRs and HMR-080 blocked in the HMR-050..106 register. Baseline production CI #576 passed; final Batch 6 CI pending. Local full Maven validation blocked by Maven Central DNS/uncached parent, Java 17 and absent Docker. | Semantic Remediation | Code | Execute still-required semantic remediation in dependency order using revalidated HMSR obligations; do not restart completed HMRs without regression evidence. | `fix(model): continue reconciled semantic remediation` | HPR-P2-007 |
| HPR-P2-009 | PENDING | Semantic Remediation | Doc | Transfer permanent semantic decisions from legacy review/roadmaps into `doc/domain/` and `doc/modules/`, then preserve legacy files as execution history. | `docs(model-remediation): canonicalize semantic decisions` | HPR-P2-008 | IN PROGRESS — HMR-050 and attached Batches 1..6 implemented; 17 completed, 39 still-required HMRs and HMR-080 blocked in the HMR-050..106 register. Baseline production CI #576 passed; final Batch 6 CI pending. Local full Maven validation blocked by Maven Central DNS/uncached parent, Java 17 and absent Docker. | Semantic Remediation | Code | Execute still-required semantic remediation in dependency order using revalidated HMSR obligations; do not restart completed HMRs without regression evidence. | `fix(model): continue reconciled semantic remediation` | HPR-P2-007 |
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

P0 security/audit verification is closed. P1 production infrastructure and survivability verification is closed subject to successful exact-head full CI on the HPR-P1-012 closure SHA.

Next executable task after that exact-head gate succeeds:

`HPR-P2-008 — fix(model): continue reconciled semantic remediation`

The historical P1 evidence-block parallel-work exception is no longer needed for sequencing because HPR-P1-029 is complete. P3 remains deferred until P2 closes and its own approved requirements exist.

### P1 evidence-block parallel progression authorization — 2026-10-06

- owner explicitly authorized continuing roadmap work without fake production-equivalent data while HPR-P1-029 was BLOCKED at that historical point;
- this authorization changes sequencing only; it is not a survivability waiver and not a production-readiness approval;
- HPR-P1-029 subsequently completed the governed production-equivalent campaign and retained measured evidence before HPR-P1-012 closure;
- P2 documentation, governance, API-contract and semantic-remediation work may execute in normal P2 dependency order;
- no P2 artifact may be cited as substitute evidence for P1 HA, DR, deployment, alert delivery, database-maintenance, retention or RTO/RPO verification;
- P3 remains deferred until both P1 and P2 are closed.

### HPR-P1-029 completion evidence — 2026-10-06

- canonical retained evidence: `doc/operations/P1_SURVIVABILITY_EXERCISE_EVIDENCE_2026-10-06.md`;
- deployed authoritative repository SHA: `66f6d7f12d1f7d52f8725cd4747cf4c777bfd29a` on `main`;
- repository reconciliation confirms 82 Flyway migrations with tail `V20261004_049__hmr_049_risk_risk_register.sql` then `V20261005_001__provision_risk_register_created_audit_taxonomy.sql`;
- operator-supplied measured evidence records RPO **15 seconds** and RTO **37 minutes**, both within approved P1 objectives;
- both-node application HA, PostgreSQL single-writer/fencing/Hikari recovery, exact-artifact deployment/rollback, alert delivery/acknowledgement/resolve, database maintenance acceptance and independent repo2 restore/bootstrap retention are retained in the canonical evidence document;
- HPR-P1-029 is **COMPLETED**; HPR-P1-012 remains responsible for final P1 closure and production-readiness disposition.

### HPR-P1-029 initial blocked execution evidence — 2026-10-06 (historical)

- execution base SHA: `24d1571e43ae5d1a0467c0b050e343df3f1f2481`;
- HPR-P1-021..028 repository remediation is complete and exact-head verified; the remaining requirement is measured production-equivalent execution, not another static repository implementation pass;
- no production-equivalent HidraAPI/HAProxy VM endpoints or authorized SSH/sudo service-control access are available in the current execution context, so the destructive two-node loss/rejoin campaign cannot be run;
- no rendered Patroni configuration, approved cluster/candidate identity, stable PostgreSQL endpoint credentials, or authorized database role-change access are available here, so single-writer/fencing and HidraAPI/Hikari recovery cannot be measured;
- no protected pgBackRest repository access, isolated recovery host/PGDATA, approved PITR target timestamp, current-schema Flyway evidence file, PostgreSQL recovery start/stop commands, or pre-target/post-target marker data are available here, so retained-repo restore, PITR, achieved RPO and declaration-to-service-acceptance RTO cannot be measured;
- no production-equivalent exact-artifact deployment target, previous approved rollback artifact, GitHub `production` approval history, or authorized runtime deployment credentials are available here, so deployment/rollback evidence cannot be retained;
- no live Prometheus/Alertmanager/Loki endpoints or human receiver/acknowledgement evidence are available here, so warning/critical/database/security alert firing, delivery, acknowledgement, recovery and resolved notification cannot be verified;
- no approved production-equivalent maintenance target plus external authenticated database-backed acceptance credential is available here, so the required database health/low-risk maintenance acceptance cannot be exercised;
- exact deployed versions/immutable identifiers for Java, HAProxy, PostgreSQL, Patroni, etcd, pgBackRest, Prometheus, Alertmanager, Grafana, Loki, the deployed HidraAPI SHA/JAR digest, backup failure-domain identifiers and retained evidence IDs are environment evidence and are not present in repository source;
- no destructive command was executed, no secret was requested for inclusion in Git, and no RTO/RPO/HA/deployment/alert/maintenance/retention result is fabricated;
- HPR-P1-029 remains the next task and must be resumed in the approved production-equivalent environment. HPR-P1-012 and all P2 work remain blocked.

### HPR-P1-028 retention enforcement evidence

- implementation SHA: `e12a2205f397e68edf1bda96211a99966577d501`;
- exact-head full CI #559 / run id `37435329469`: **SUCCESS**;
- repo2 validation distinguishes bootstrap from mature monthly coverage and requires distinct required UTC calendar months under `HIDRA-P1-BACKUP-RETENTION-001`;
- mature coverage is bounded to the latest 12 required monthly recovery points; missing or duplicate required months fail validation;
- Prometheus executable service configuration enforces `--storage.tsdb.retention.time=30d`;
- Loki enforces `retention_period: 2160h` with compactor retention/deletion and a filesystem delete-request store;
- physical independence of repo2 and successful restore from a retained repo2 point remain measured HPR-P1-029 evidence requirements.

### HPR-P1-027 database operations completion evidence

- implementation SHA: `cafdc42270085ddf513a23a514f437ef39f7b842`;
- exact-head full CI #555 / run id `37433450282`: **SUCCESS**;
- index inspection now records `indisvalid`, `indisready` and `indislive` from PostgreSQL catalogs;
- bloat/space-risk triage remains extension-free and is explicitly not misrepresented as an exact physical-bloat percentage;
- host/filesystem bytes and inode capacity are captured separately from PostgreSQL object-size SQL;
- guarded maintenance validation requires a selected approved action plus authenticated database-backed HidraAPI acceptance before and after maintenance;
- actual production-equivalent diagnostic/maintenance execution remains HPR-P1-029 evidence scope.

### HPR-P1-026 observability wiring evidence

- implementation SHA: `472811292c271241c94647e71deb1ffe59086d0c`;
- exact-head full CI #553 / run id `37430996964`: **SUCCESS**;
- protected HidraAPI metrics remain authenticated; Prometheus uses an externally rendered bearer-token file rather than public Actuator exposure;
- production enables the HTTP request histogram required by the p95 Prometheus rule;
- both application and PostgreSQL HAProxy configurations expose dedicated monitoring-only Prometheus exporters and the Prometheus template scrapes both;
- Alertmanager route ordering now gives security/database/backup ownership precedence over generic critical routing;
- static/native observability validation covers authenticated scraping, histogram binding, HAProxy exporters and route order;
- actual production-equivalent scrape success, rule firing, recipient delivery and resolve/recovery evidence remain HPR-P1-029 scope.

### HPR-P1-025 availability-preserving rollout evidence

- implementation SHA: `c692ab684ef79c8295d3216adafd6daf5dc1db46`;
- exact-head full CI #552 / run id `37429745480`: **SUCCESS**;
- each target deployment verifies the surviving REST node before drain;
- drain waits for the target node's active HAProxy sessions to reach zero before installation; realtime sessions are also drained for the single-active realtime node;
- successful deployment requires local readiness, authenticated database-backed acceptance and HAProxy UP state before rejoin is accepted;
- schema-compatible rollback follows equivalent readiness/acceptance/rejoin gates;
- required GitHub `production` Environment reviewer protection is documented owner policy but cannot be observed through the available connector and is therefore not fabricated as verified;
- actual approval-history plus production-equivalent exact-artifact deployment/rollback evidence remain HPR-P1-029 scope.

### HPR-P1-024 application failover recovery evidence

- implementation SHA: `117da3131daa4b98a55098573c4ee933a1c012f9`;
- exact-head full CI #551 / run id `37428720127`: **SUCCESS**;
- the database failover harness now requires a caller-approved authenticated database-backed HidraAPI read acceptance before and after role change;
- authentication is externalized through `HIDRA_APP_CURL_CONFIG` and is not committed or printed;
- authenticated Actuator `hikaricp.connections.creation` is recorded before failover and must increase during recovery, providing application-managed pool replacement evidence rather than inference from fresh `psql` clients;
- recovery elapsed time is recorded from controlled switchover initiation until both application acceptance and Hikari replacement are observed;
- interrupted/in-doubt transactions remain explicitly not treated as successful and are not blindly retried;
- actual production-equivalent failover timing and application recovery evidence remain HPR-P1-029 scope.

### HPR-P1-023 application HA exercise hardening evidence

- first exact-head full CI #549 / run id `37395477029`: **FAILED** at `Validate production application HA runtime artifacts`; diagnosis found malformed generated content in `validate-runtime-artifacts.sh`, while `verify-application-ha.sh` retained the intended direct-node, authenticated continuity and rejoin logic; PostgreSQL, Maven and later stages were skipped;
- repair replaces the corrupted runtime validator with a clean fail-fast validator covering two REST nodes, single-active realtime, readiness routing, no stickiness, service lifecycle, HA exercise inputs/continuity functions and shell syntax; HPR-P1-024 remains blocked until the repaired exact head passes full CI;

- execution base SHA: `9d6130bf6e0c33e5aeaebe3e3c8b7fcde103d220`;
- pre-task exact-head full CI #548 / run id `37394622465`: **SUCCESS**;
- the destructive exercise now requires direct node base URLs so both nodes are individually proven ready before the first loss and after each restart;
- `HIDRA_HA_ACCEPTANCE_URL` and external `HIDRA_HA_ACCEPTANCE_CURL_CONFIG` provide a caller-approved authenticated representative REST acceptance without embedding credentials or inventing a business endpoint;
- a continuous probe loop runs across each stop/restart window and any failed representative REST request fails the exercise;
- node 1 must be directly ready again before node 2 may be stopped, and vice versa;
- realtime remains explicitly single-active and is not included in the REST HA claim;
- actual production-equivalent node-loss/rejoin evidence remains HPR-P1-029 scope.

### HPR-P1-022 Patroni/HAProxy TLS alignment evidence

- first exact-head full CI #547 / run id `37394262823`: **FAILED** at `Validate production PostgreSQL HA artifacts`; diagnosis found malformed generated content in `validate-postgres-ha-artifacts.sh`, not a Patroni/HAProxy runtime-contract failure; Maven and later stages were skipped;
- repair replaces the corrupted validator with a clean fail-fast static validator using fixed-string assertions for the mTLS contract and shell syntax validation for the failover harness; HPR-P1-023 remains blocked until the repaired exact head passes full CI;

- execution base SHA: `b630e6f29bcedbc882346495f3187673a37ca193`;
- pre-task exact-head full CI #546 / run id `37393293127`: **SUCCESS**;
- Patroni REST remains configured with `certfile`, `keyfile`, `cafile` and `verify_client: required`;
- HAProxy `/primary` checks now use `check-ssl`, `verify required`, the rendered Patroni REST CA, an externally rendered client certificate/private-key PEM, and per-node `verifyhost` values while PostgreSQL data traffic remains independently configured;
- static HA validation rejects the former plain port-8008 HTTP check and asserts the mutual-TLS contract on both PostgreSQL nodes;
- the failover harness now records `patronictl show-config` and exactly-one-primary cluster authority context before and after controlled switchover and still verifies former-primary demotion;
- controlled switchover is not mislabeled as a network-partition/fencing test; HPR-P1-029 retains that measured evidence requirement.

### HPR-P1-021 backup/PITR binding evidence

- execution base SHA: `58d2e7d463d5570fc6d6f20e899d162600a939ab`;
- pre-task documentation validation #22 / run id `37392680174`: **SUCCESS**;
- both daily repo1 and monthly repo2 systemd services now provide the configuration path required by their backup scripts;
- the bound path is `/etc/pgbackrest/pgbackrest.conf`, consistent with the repository pgBackRest exporter default; rendered environment-specific values remain outside Git;
- `capture-flyway-history.sh` captures the successful versioned Flyway `version|checksum` set from an approved current-schema source database into retained exercise evidence;
- `verify-pitr-restore.sh` requires that manifest and fails unless the restored successful versioned Flyway history matches it exactly; the former count-only `>= 82` acceptance gate is removed;
- actual scheduled backup/WAL execution and production-equivalent PITR remain HPR-P1-029 scope.

### HPR-P1-017 exact-head verification evidence

- repaired exact-head SHA: `719c8bf06f3f54c5b0fa2c7a4369ae08ac7e0251`;
- full CI #544 / run id `37388204860`: **SUCCESS**;
- documentation validation #20 / run id `37388204991`: **SUCCESS**;
- roadmap duplicate-tail corruption introduced during the prior HPR-P1-017 status replacement was removed without changing the approved HPR status or closure requirements.

### HPR-P1-017 retention-binding repair evidence

- failed exact-head full CI #543 / run id `37387950297` isolated the defect to static pgBackRest/PITR validation;
- application HA and PostgreSQL HA validations passed; Maven verification did not start;
- root cause: `verify-pitr-restore.sh` validated `HIDRA_PITR_REPO=1|2` but the actual pgBackRest restore command omitted `--repo="${repo}"`;
- repair adds the selected repository explicitly to the restore command, making repo2 monthly retained-point PITR validation executable rather than cosmetic.

### HPR-P1-017 monthly-retention binding evidence

- owner-value baseline SHA: `d134d7b0e9ea98d0ad9b8aa9fc131def81c235f3`;
- exact-head documentation validation #18 / run id `37386339414`: **SUCCESS**;
- pgBackRest repo1 remains daily operational backup with 35-day time retention;
- pgBackRest repo2 is now the monthly retained recovery-point repository with count-based retention of 12 full backups under `HIDRA-P1-BACKUP-RETENTION-001`;
- monthly systemd timer/service runs an explicitly repo2-targeted full backup annotated with the policy identifier;
- daily full backup is explicitly repo1-targeted so monthly retention is not polluted by daily backup cadence;
- `archive-async=y` is enabled; pgBackRest archive-push writes WAL to all configured repositories;
- monthly health validation checks a completed repo2 full backup, latest age <=35 days, observed inter-month gap <=35 days and no more than 12 retained full backups after expiration;
- PITR harness now accepts `HIDRA_PITR_REPO=1|2`, enabling explicit restore validation from a retained monthly repo2 point;
- production-equivalent repo2 storage independence, retained monthly restore, WAL continuity and RPO/RTO remain measured-evidence requirements.

### P1 owner-value approval evidence — 2026-10-05

- owner accepted the recommended remaining P1 values after HPR-P1-020;
- monthly retention is now one recovery point per month for 12 months / 12 monthly points, policy id `HIDRA-P1-BACKUP-RETENTION-001`, subject to any stricter SONATRACH Records/Legal/Compliance policy;
- minimum application topology remains two active Linux VM/systemd nodes behind HAProxy, with REST active-active and realtime single-active;
- PostgreSQL remains two Patroni-managed data nodes with a three-member etcd quorum and HAProxy stable endpoint;
- Vault production secret hierarchy/least privilege and `/run/hidra/hidra-secrets.env` remain required;
- GitHub Environment `production` requires at least two reviewers: Platform/Operations and Application/Release owner;
- deployment acceptance must use an authenticated harmless read-only database-backed path;
- Prometheus operational metrics retention is 30 days; Loki operational logs retention is 90 days; security/audit records remain under enterprise policy;
- alert routing must distinguish default operations, critical operations, Database Operations and Security Incident response; critical/security must reach paging/on-call;
- formal RPO/RTO remain <=5m / <=60m; engineering exercise targets are <2m / <30m where achievable;
- actual hostnames, endpoint URLs, receiver addresses, credentials and exact deployed versions are not invented and remain HPR-P1-012 evidence inputs.

### HPR-P1-020 database operations implementation evidence

- execution base SHA: `68e1b9f5d4b29078c8918ce61c5732b99b00874e`;
- pre-task exact-head full CI #541 / run id `37379496810`: **SUCCESS**;
- pre-task documentation validation #16 / run id `37379496826`: **SUCCESS**;
- added read-only SQL inspection procedures for sessions, blocking chains, long-running work, VACUUM/ANALYZE history/dead tuples, index usage, database/schema/table capacity, replication/slots and Flyway history;
- added composite `database-health-report.sh` that runs the SQL inspections and records Patroni topology plus pgBackRest check/info through the approved stable-endpoint/tooling model;
- added guarded `VACUUM (ANALYZE, VERBOSE)`, `REINDEX INDEX CONCURRENTLY` and targeted `pg_terminate_backend` procedures requiring explicit operator opt-in;
- index inspection does not infer that an unused index is safe to drop; maintenance remains reviewed Database Operations action;
- routine autovacuum remains the default; blanket manual vacuum/reindex scheduling is not introduced;
- static CI validates required SQL content and shell syntax/guardrails;
- database runbook now cross-links concrete Patroni failover and executable database-operations procedures;
- no production-equivalent command execution or post-maintenance acceptance is claimed; HPR-P1-020 remains IMPLEMENTED-PENDING-PRODUCTION-EQUIVALENT-VALIDATION.

### HPR-P1-019 production alerting implementation evidence

- execution base SHA: `deb24ec6d3707b94bae7704581ad30877516fc78`;
- pre-task exact-head full CI #540 attempt 2: **SUCCESS**;
- pre-task exact-head documentation validation #15 attempt 3: **SUCCESS**;
- owner approved the P1 SLO/threshold baseline on 2026-10-05: 99.9% monthly availability excluding approved maintenance; HTTP 5xx, p95 latency, node redundancy, CPU/JVM, Hikari, PostgreSQL replication, backup and monitoring thresholds are now explicit;
- selected stack is Prometheus + Alertmanager + Grafana + Loki;
- Prometheus scrape template covers HidraAPI, HAProxy, Patroni and the repository-owned pgBackRest metrics exporter;
- executable Prometheus rules implement application/HA/database/backup/monitoring alerts with owner/domain/severity labels;
- Alertmanager routes critical, database/backup and security domains to separate controlled receiver placeholders; no real endpoint or credential is committed;
- pgBackRest exporter emits latest backup age and pgBackRest stanza-check success without secret-bearing labels;
- Grafana datasource provisioning binds Prometheus and Loki; Loki baseline config is included without claiming Loki HA;
- static CI validates the observability package and performs syntax checks where promtool/amtool are available;
- synthetic routing script submits warning/critical/database/security alerts to Alertmanager for production-equivalent route testing;
- no live receiver delivery, escalation, resolve event, Loki ingestion or no-secret runtime evidence is claimed; HPR-P1-019 remains IMPLEMENTED-PENDING-LIVE-EXERCISE.

### HPR-P1-018 controlled release implementation evidence

- execution base SHA: `4923d8cc3a770e108e9171feb3d53a833fa7300f`;
- pre-task full CI run #539 / run id `37361134307`: **SUCCESS**;
- pre-task documentation validation run #14 / run id `37361134225`: **SUCCESS**;
- added `.github/workflows/release.yml` with validate-only and deploy-production modes;
- release identity requires a full 40-character SHA reachable from main plus successful exact-SHA HidraAPI CI;
- the exact candidate JAR is built once, SHA-256 hashed, manifested, uploaded, downloaded and digest-verified before deployment;
- deploy job targets GitHub Environment production; required-reviewer protection is an external repository setting and cannot be proven by repository source through the available connector;
- runtime application secrets remain Vault-backed on the VMs; workflow does not transport them, while systemd/install preflight requires non-empty /run/hidra/hidra-secrets.env with HIDRA_SECRETS_SOURCE=vault;
- rollout drains/deploys hidra-api-2 before realtime-active hidra-api-1, checks local readiness, executes a configured authenticated database-backed acceptance request, rejoins HAProxy and checks HA readiness;
- schema-change mode is explicit; automatic artifact rollback is allowed only when previous-artifact schema compatibility is explicitly declared;
- rollback never edits Flyway history or implicitly reverses database schema;
- static CI validates workflow/scripts/systemd release controls;
- no live production-equivalent deployment or rollback exercise is claimed; HPR-P1-018 remains IMPLEMENTED-PENDING-ENVIRONMENT-AND-EXERCISE.

### HPR-P1-017 backup/WAL/PITR implementation evidence

- execution base SHA: `9e1503154e59c7de5b06ca00bb045c2328770c1b`;
- pre-task full CI run #538 / run id `37357922054`: **SUCCESS**;
- pre-task documentation validation run #13 / run id `37357922064`: **SUCCESS**;
- pgBackRest configuration template uses an independent repository host/path and `repo1-retention-full-type=time` with `repo1-retention-full=35` for the approved operational window;
- PostgreSQL archive integration is explicit via pgBackRest `archive-push` and `archive-get`;
- daily systemd full-backup timer/service and backup-health script are provided; health validation rejects missing backups and a latest completed backup older than 24 hours;
- timestamp PITR harness restores to an empty isolated PGDATA using `--type=time --target=<approved timestamp> --target-action=promote`, starts the recovery instance using operator-supplied approved commands, validates queryability and inspects `flyway_schema_history`;
- PITR validation requires at least the currently audited 82 successful Flyway migrations;
- static CI checks the pgBackRest/WAL/retention/scripts and explicitly prevents inventing a monthly retention duration;
- unresolved blocker remains the controlling enterprise/SONATRACH monthly recovery-point policy identifier and exact hold duration/rule;
- real repository independence, continuous WAL coverage and restore/PITR success remain production-equivalent exercise evidence, so HPR-P1-017 is IMPLEMENTED-PENDING-POLICY-AND-EXERCISE rather than closed.

### HPR-P1-016 PostgreSQL failover implementation evidence

- execution base SHA: `9053572efc804bcba44b14cd3aec4fa8d4900746`;
- pre-task full CI run #537 / run id `37356537131`: **SUCCESS**;
- pre-task documentation validation run #12 / run id `37356537237`: **SUCCESS**;
- added renderable Patroni template using a three-member etcd3 DCS, TLS/mTLS placeholders, PostgreSQL streaming replication, checksums, replication slots and `pg_rewind`;
- synchronous replication remains explicitly parameterized because the approved policy is synchronous local replication where measured latency permits; no unsupported latency decision was invented;
- added three-member TLS-authenticated etcd configuration template;
- added HAProxy PostgreSQL write-endpoint template that checks Patroni `/primary` and exposes only the authoritative primary to HidraAPI;
- production Hikari connection timeout, validation timeout, idle timeout, max lifetime and keepalive are explicit/externalized; idle=600000 ms, maxLifetime=1800000 ms, keepalive=120000 ms are engineering lifecycle baselines, not SLO/capacity claims;
- documented that interrupted/in-doubt transactions are not blindly retried and require explicit use-case idempotency semantics;
- added static CI validation of Patroni/etcd/HAProxy/Hikari artifacts;
- added destructive controlled Patroni switchover harness that records topology, requires exactly one primary, verifies reversible write capability through the stable endpoint before/after role change, verifies former-primary demotion and waits for HidraAPI readiness recovery;
- the production-equivalent role-change exercise has not been executed by this repository-only task; HPR-P1-016 remains IMPLEMENTED-PENDING-EXERCISE and HPR-P1-012 must require measured evidence.

### HPR-P1-015 application HA implementation evidence

- execution base SHA: `c6c9037dad3277d418a8f1140df9092ba6bdfd84`;
- pre-task full CI run #536 / run id `37354762079`: **SUCCESS**;
- added concrete Linux VM/systemd + HAProxy runtime artifacts under `ops/production/`;
- HAProxy REST backend declares exactly two HidraAPI nodes, round-robin balancing, readiness health checks, and no configured cookie/stick-table affinity;
- HAProxy realtime backend is deliberately single-active on `hidra-api-1`, matching the owner-approved P1 operating mode;
- `HidraRealtimeConfiguration` now uses `@ConditionalOnProperty(hidra.platform.realtime.enabled)`, so realtime is actually disabled on the REST-only secondary node;
- systemd unit uses SIGTERM so Spring graceful shutdown remains authoritative and restarts failed processes with `Restart=on-failure`;
- drain/rejoin scripts use the HAProxy runtime socket for planned maintenance;
- static validation checks backend cardinality, readiness checking, no sticky-session directives, node realtime roles and shell syntax; full CI now executes it;
- destructive verification script stops one systemd node at a time over SSH, probes readiness through HAProxy, restores the node and captures timestamped evidence;
- the destructive exercise has not been run by this repository-only task; HPR-P1-015 therefore remains IMPLEMENTED-PENDING-EXERCISE and HPR-P1-012 must not treat configuration presence as measured survivability.

### HPR-P1-014 production startup safety evidence

- execution base SHA: `7469a424c84d3b4454ff8db2eda27d660a8e437c`;
- pre-task documentation validation run #10 / run id `37354173438`: **SUCCESS**;
- added `dz.sh.hidra.platform.configuration.HidraProductionStartupGuard` as a Spring `EnvironmentPostProcessor`;
- registered guard in `META-INF/spring.factories` so validation occurs before normal bean creation;
- production hosting contract now uses `HIDRA_ENVIRONMENT=production` as an independent production-intent marker and requires active Spring profile `production`;
- guard validates non-empty datasource URL/username/password, production CORS origins when CORS is enabled, JWT authentication mode, externalized HMAC secret, and bootstrap password when bootstrap is enabled;
- negative unit tests cover wrong profile and missing mandatory production inputs; a complete production contract passes and ordinary dev runtime remains unaffected;
- exact-head full CI is required because this HPR changes executable code/resources/tests; completion must not be treated as verified until that run succeeds.

### HPR-P1-013 production infrastructure decision evidence

Owner acceptance date: 2026-10-05.

Approved stack:

- HidraAPI runtime: Linux virtual machines with systemd-managed Java 21 services;
- application traffic distribution: HAProxy;
- PostgreSQL HA: Patroni-managed PostgreSQL with etcd quorum for leader/coordination state;
- stable application-facing PostgreSQL endpoint: HAProxy;
- database backup/WAL/PITR: pgBackRest;
- backup repository/storage: protected storage independent from the live PostgreSQL primary/standby nodes and failure path;
- runtime secrets/configuration: HashiCorp Vault for secret material, with non-secret configuration externalized through the approved runtime mechanism;
- metrics collection: Prometheus;
- alert evaluation/routing: Alertmanager;
- dashboards: Grafana;
- centralized application logs: Grafana Loki;
- realtime for P1: single-active realtime operation while REST remains multi-node; no clustered realtime HA claim until a shared broker/mechanism is separately approved and verified;
- cache for P1: retain Spring process-local cache only for non-authoritative optimization; do not introduce Redis solely for P1;
- operational backup retention remains the already-approved 35-day window.

Still unresolved:

- controlling enterprise/SONATRACH records-policy identifier for monthly retained recovery points;
- exact monthly backup recovery-point retention/hold duration/rule under that policy.

No monthly duration is inferred or fabricated. HPR-P1-017 may implement pgBackRest/WAL/PITR mechanics and 35-day operational retention, but it cannot be marked complete until the monthly policy rule is supplied and enforced.

### P1 independent audit ingestion evidence

- audit date: 2026-10-05 (Africa/Algiers);
- audited SHA: `f46f6c1ed7e324f66a0a26422a6dfa6da8bc3689`;
- audit scope: Phase P1 only;
- audit verdict: **FAIL — 2/12 VERIFIED**;
- verified: Check 1 RTO/RPO and Check 8 OpenAPI breaking-change gate;
- failed as superficial: Checks 2, 4, 5, 6, 7, 10, 11, 12;
- failed as not implemented: Checks 3 and 9;
- exact-head documentation validation run #8 / run id `37347444647`: **SUCCESS**;
- audit correctly did not treat documentation validation as infrastructure survivability evidence;
- audit reports no exact-head full CI at `f46f6c1ed7e324f66a0a26422a6dfa6da8bc3689`; earlier full-CI evidence remains historical and does not close P1;
- roadmap response: add HPR-P1-013..020 and block HPR-P1-012 until implementation and measured evidence exist.

### Final P0 closure evidence

- remediation baseline: `850ee4770adc2aff50e81682223458ee28af1530`;
- remediation CI: run #528 / run id `37324883370`: **SUCCESS**;
- final closure/audit baseline: `bada4bb882b634762756dd115c7d66c864bd0b3b`;
- exact-head closure CI: run #529 / run id `37326280500`: **SUCCESS**;
- exact-head verification job: `111817745388`, `Java 21 Maven verification`: **SUCCESS**;
- `./mvnw -B -q clean verify`: successful;
- PostgreSQL/Testcontainers verification: successful;
- Flyway validated 82 migrations and exercised full-schema migration to `v20261005.001`;
- application startup and health polling: successful;
- Workbench HTTP/controller/serialization regression coverage: successful under the Maven verification gate;
- deterministic OpenAPI generation/security assertions: successful;
- exact-head OpenAPI artifact id `11352572397`;
- artifact name `hidra-api-openapi-bada4bb882b634762756dd115c7d66c864bd0b3b`;
- artifact digest `sha256:ccc13480776b1adb9f36002aaac2aa3d4339cb2bcedc75f95b12dc89c2004880`;
- artifact `x-hidra-ci-source-sha` exactly matches `bada4bb882b634762756dd115c7d66c864bd0b3b`;
- both `hidraBearerJwt` and `externalOidcBearerJwt` are present as HTTP bearer JWT schemes;
- all 259 generated OpenAPI operations have explicit security declarations;
- independent P0 re-audit at the exact closure SHA: **PASS — 13/13 checks VERIFIED**.

P0 is CLOSED for security/audit verification. Production readiness remains **NOT ESTABLISHED** because P1 runtime architecture, HA, DR, deployment, observability, and database-operations survivability are still incomplete.

### HPR-P1-001 runtime-architecture evidence

- execution base SHA: `912b76396651c197d4db9ecccb21cd3de6a83e86`;
- P0 closure CI run #529 / run id `37326280500`: **SUCCESS**;
- latest pre-task head CI run #530 / run id `37328378048`: **SUCCESS**;
- repository runtime is Java 21 / Spring Boot 4.1.1 with PostgreSQL and Flyway;
- HTTP security is stateless; production-sensitive configuration is externalized;
- Actuator readiness/liveness and Prometheus-format metrics are configured;
- common cache is process-local Spring `simple` cache and realtime STOMP uses the in-process simple broker;
- no production Docker/Kubernetes/Helm/IaC deployment definition is established by repository evidence;
- multi-node/load distribution, PostgreSQL HA, RTO/RPO, backup/WAL/PITR, DR, deployment target, TLS product/placement, secret-manager product, network zones and production monitoring platform remain decision-required or NOT ESTABLISHED.


### HPR-P1-002 application-HA decision evidence

- execution base SHA: `ce662955a9615443be094859a68d10c029e94324`;
- pre-task exact-head CI: run #531 / run id `37330294217`: **SUCCESS**;
- owner acceptance date: 2026-10-05;
- approved application service level: minimum two simultaneously active HidraAPI nodes;
- approved traffic model: product-neutral managed load distribution with no REST session affinity;
- only ready nodes may receive new traffic; unready/failed nodes must be removed from new traffic;
- liveness is the process recovery signal and is distinct from dependency-sensitive readiness;
- planned maintenance requires traffic drain followed by graceful shutdown;
- current process-local Spring `simple` cache must not hold correctness-critical shared state across nodes;
- current in-process STOMP simple broker is not approved as clustered realtime transport; clustered realtime requires an external/shared broker or equivalent approved cross-node mechanism before HA is claimed;
- deployment/load-balancer/broker/cache products remain intentionally unselected;
- HPR-P1-003 and HPR-P1-004 owner recommendations were accepted in the same decision turn, but those HPRs remain unexecuted.


### HPR-P1-003 PostgreSQL-HA decision evidence

- execution base SHA: `4b4fc46d9ff78b8bd77a706ff1e36a7c1fc4ee3a`;
- pre-task exact-head CI: run #532 / run id `37338086447`: **SUCCESS**;
- owner acceptance date: 2026-10-05;
- approved topology: one writable primary plus at least one local synchronous streaming standby where approved latency permits;
- an additional remote asynchronous standby is permitted as the DR replication tier, but HPR-P1-003 does not define DR objectives or declare that tier sufficient for DR;
- HidraAPI must connect through a stable database endpoint rather than node-specific topology knowledge;
- normal failover/switchover authority belongs to Database Operations; emergency action follows the approved incident-command authority with Database Operations/Platform participation;
- failover must prevent dual-primary/split-brain conditions through the selected infrastructure's fencing/authority mechanism;
- application recovery after database role change is connection/retry based; in-flight transaction survival is not claimed;
- replicas are not backups; backup retention, WAL/PITR and RTO/RPO remain HPR-P1-004 scope;
- no PostgreSQL HA manager, proxy, virtual IP, DNS, load balancer, cloud database service, or orchestration product is selected by this HPR.


### CI documentation-validation protocol adjustment

Owner-approved on 2026-10-05 after HPR-P1-003:

- documentation-only changes no longer consume the full Java/PostgreSQL/OpenAPI verification pipeline solely because canonical `doc/**` changed;
- full CI ignores `doc/**`, legacy `docs/**`, and Markdown-only paths on push/pull request when no executable/configuration/workflow path changed;
- a separate lightweight documentation workflow validates canonical Markdown presence, UTF-8 readability, non-empty files, and unresolved merge-conflict markers;
- changes to workflow files themselves are not documentation-only and therefore continue to trigger full CI;
- `workflow_dispatch` remains available for explicit full verification;
- roadmap tasks that explicitly require full exact-head verification, including survivability/closure gates, still require the full pipeline.


### HPR-P1-004 disaster-recovery objective evidence

- execution base SHA: `1fa7f25e6985287cf6548653b99312afcdb9bce0`;
- pre-task documentation validation: run #1 / run id `37340736184`: **SUCCESS**;
- pre-task one-time full CI after workflow split: run #534 / run id `37340736111`: **SUCCESS**;
- owner acceptance date: 2026-10-05;
- approved production RTO: **≤ 60 minutes**, measured from formal DR declaration to acceptance of the recovered HidraAPI production service;
- approved production RPO: **≤ 5 minutes**, measured as the maximum acceptable gap between the selected recovery point and the latest production data that must be recoverable;
- continuous PostgreSQL WAL archiving is required to support PITR and the approved RPO;
- backup coverage requires at least one successful recoverable base/full backup per 24-hour period plus the WAL required to reach approved PITR targets;
- operational backup retention is **35 days**;
- monthly recovery points are retained according to applicable enterprise/records-governance policy; HPR-P1-004 does not invent a longer monthly-retention duration where policy evidence is absent;
- backup copies must be operationally independent of the live primary/standby failure domain; a replica is not a backup;
- DR declaration authority follows the approved incident-governance model; Database Operations owns database restore/PITR execution, Platform/Operations owns runtime recovery, and service acceptance requires the accountable operational/business authority;
- recovery evidence must include selected recovery point, achieved data-loss interval, elapsed recovery time, database consistency, Flyway/JPA validation, application health, authentication/authorization, representative read/write acceptance, and residual issues;
- no backup, object-storage, archive, snapshot, orchestration, or DR product is selected by this HPR.


### HPR-P1-005 disaster-recovery runbook evidence

- execution base SHA: `354c34323e75e8a3684b0d811182cea9a06e591d`;
- pre-task lightweight documentation validation: run #2 / run id `37342061352`: **SUCCESS**;
- runbook is derived strictly from approved HPR-P1-004 objectives;
- RTO remains **≤ 60 minutes** and RPO remains **≤ 5 minutes**;
- recovery sequence covers declaration/containment, role activation, recovery-point selection, database restore, WAL/PITR, single-writer validation, stable endpoint restoration, secrets/configuration, application startup, Flyway/JPA validation, authentication/authorization, representative read/write acceptance, traffic restoration, and RTO/RPO evidence;
- explicit stop/abort conditions prevent recovery from an untrusted point, dual-primary state, failed schema validation, failed security acceptance, or unknown write authority;
- no backup, storage, PostgreSQL HA, load-balancer, orchestration, secret-manager, or DR product/command is invented;
- measured restore/PITR execution remains HPR-P1-012 scope and production DR readiness remains NOT ESTABLISHED.


### HPR-P1-006 high-availability architecture evidence

- execution base SHA: `f79b7875194f50460741179f63880ed176673844`;
- pre-task lightweight documentation validation: run #3 / run id `37342929433`: **SUCCESS**;
- application HA model: minimum two simultaneously active HidraAPI nodes behind a product-neutral managed load-distribution boundary;
- REST correctness must not depend on sticky sessions; traffic goes only to ready nodes and planned maintenance drains traffic before graceful shutdown;
- process-local `simple` cache cannot hold correctness-critical shared state;
- clustered realtime HA remains NOT ESTABLISHED while the in-process STOMP simple broker remains; an approved external/shared cross-node mechanism is required before such a claim;
- PostgreSQL HA model: exactly one writable primary, at least one local streaming standby using synchronous replication where approved latency permits, optional remote asynchronous standby, and one stable application-facing endpoint;
- Database Operations controls database failover/switchover; failover must fence/prevent dual-writer state;
- HidraAPI recovers database connectivity through reconnect to the stable endpoint; in-flight transaction survival/global transparent retry is not claimed;
- application and database maintenance procedures are coordinated but product-neutral;
- no runtime, load-balancer, proxy, HA manager, fencing, broker, cache, VM/container, cloud, or orchestration product is selected;
- measured failover and degraded-capacity evidence remains HPR-P1-012 scope.


### HPR-P1-007 production-deployment documentation evidence

- execution base SHA: `59c8b913e703e08386bab49850405e307d16002d`;
- pre-task lightweight documentation validation: run #4 / run id `37343323013`: **SUCCESS**;
- deployment requires an identified verified HidraAPI artifact/revision and explicit `SPRING_PROFILES_ACTIVE=production`;
- production datasource configuration uses the approved stable database endpoint and externalized credentials;
- production startup must preserve Flyway validation/clean-disabled behavior and JPA `ddl-auto=validate`;
- deployment must prevent uncontrolled concurrent schema migration from multiple application nodes;
- nodes are admitted to traffic only after required health/readiness and security/database acceptance checks pass;
- rollback distinguishes application-artifact rollback from database-schema recovery and forbids rewriting applied Flyway migrations;
- runtime secret values must not be committed to Git or deployment evidence;
- hosting/orchestration, load-distribution, secret-manager, TLS termination, broker/cache, and CD products remain unselected;
- HPR-P1-009 remains blocked on deployment-target approval.


### HPR-P1-008 OpenAPI compatibility evidence

- execution base SHA: `656a42256d3849c78e1b93d3792e7cceceed4123`;
- pre-task lightweight documentation validation: run #5 / run id `37343927263`: **SUCCESS**;
- full CI now checks out complete Git history so the actual compatibility base revision is available;
- for pull requests the comparison base is the PR base SHA; for pushes it is the event `before` SHA; manual runs use the current parent commit;
- CI generates the current OpenAPI contract from the current application and independently builds/starts the exact base revision to generate the baseline contract;
- repository-owned `.github/scripts/openapi_compatibility.py` fails CI on supported breaking changes instead of treating artifact generation/upload as compatibility evidence;
- covered break classes include removed paths/operations/parameters, new required parameters/request body/properties, request enum contraction, schema type changes, removed response codes/content types/properties, required-response guarantee weakening, response enum expansion, and public-to-authenticated operation tightening;
- compatibility validation runs before OpenAPI artifact upload;
- no API compatibility result is claimed until the post-commit full CI run completes successfully.


### HPR-P1-010 observability/SRE evidence

- execution base SHA: `39da2f1bf9bf4e6305b29a6b5bfd34a287bc6fd2`;
- pre-task exact-head full CI / OpenAPI compatibility run #535 / run id `37344473469`: **SUCCESS**;
- existing application signals are limited to Spring Boot Actuator health/info/metrics/Prometheus surfaces, readiness/liveness probes, JVM/Spring/Hikari-generated metrics, environment/application metric tags, and correlation/request/actor-enriched application logging;
- repository search found no current custom Micrometer `MeterRegistry`/`Counter`/`Timer`/`Gauge` instrumentation to claim as existing;
- canonical operating model defines collection domains, dashboards/views, alert condition classes, severity/ownership routing, evidence, incident linkage, HA/DR observability requirements, and production validation expectations;
- executable alerting is NOT IMPLEMENTED because no production metrics collector, alert manager/router, dashboard platform, log aggregation/SIEM product, on-call integration, numeric alert thresholds, or SLO/error-budget values are owner-approved;
- no Prometheus-rule syntax, Grafana/SIEM product, paging integration, numeric latency/error-rate threshold, availability target, or retention duration was invented;
- HPR-P1-010 remains BLOCKED-DECISION until the required platform/threshold decisions are approved, after which a narrow implementation commit must bind the documented conditions to the selected tooling.


### HPR-P1-011 database-operations runbook evidence

- execution base SHA: `b79648f5fa1e2a337e29213aad5b3a08447329db`;
- pre-task lightweight documentation validation: run #7 / run id `37347126292`: **SUCCESS**;
- runbook is derived from HPR-P1-003 PostgreSQL HA, HPR-P1-004 DR objectives, HPR-P1-005 DR procedure, HPR-P1-007 deployment controls, and current production datasource/Flyway/JPA configuration;
- current production pool defaults are recorded as implementation defaults only: Hikari maximum 30, minimum idle 10, connection timeout 30s, validation timeout 5s; no capacity threshold is inferred from them;
- Flyway remains migration authority with validate-on-migrate and clean disabled; applied migrations are immutable and migration repair/override is not authorized as a shortcut;
- database operations cover connection exhaustion, failover/switchover, backup/WAL/PITR, maintenance, migration failures, credential rotation coordination, data-integrity incidents and evidence retention;
- no Patroni/Pgpool/HAProxy/pgBackRest/Barman/cloud database/monitoring platform or vendor-specific command is invented;
- HPR-P1-012 remains responsible for measured restore/PITR and failover execution and cannot close while HPR-P1-009/HPR-P1-010 blockers or survivability evidence gaps remain.

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
- The historical P1 block was satisfied by HPR-P0-015. P1 may proceed from HPR-P1-001; this does not establish production readiness.
- Owner-dependent procedures must remain blocked rather than being fabricated.

## 9. Independent P0 Re-Audit Closure

Historical failed-audit baseline: `dcf69e1a4a4b788cd125ba6289384642efc4caa0`

Remediation baseline: `850ee4770adc2aff50e81682223458ee28af1530`

Final closure/audit baseline: `bada4bb882b634762756dd115c7d66c864bd0b3b`

Final result: **PASS — 13/13 checks VERIFIED**.

Exact closure evidence:

- GitHub Actions run #529 / run id `37326280500`: **SUCCESS**;
- verification job `111817745388`: **SUCCESS**;
- exact-head OpenAPI artifact `11352572397` was independently inspected;
- artifact source SHA matches the closure SHA;
- all 259 generated operations contain explicit security declarations.

The failed-audit findings are closed by HPR-P0-008..015. Original verified checks remain preserved, and the six failed checks have direct remediation and exact-head closure evidence.

P0 is therefore closed for security/audit verification. This does not imply P1 production survivability, automated production secret/certificate operations, OT segmentation, or full production readiness.

## User-authorized P2 Batch 1 — 2026-10-06

The owner authorized the six-task execution order HMR-051, HMR-059, HMR-073,
HMR-074, HMR-075, HMR-076, with one independent HMR commit per task and shared
final full CI. This explicit six-task scope supersedes the previous solo sequence
for these tasks only. Baseline `7068b44af8d269bc0f06fd6c647197f27c233004`
is green in full CI #568 / run `37471765441`. Organization items have no SCC or
unfinished dependencies on the intervening HMRs. HMR-059 follows HMR-051.
Adapted write scope includes the Organization-owned Leak Detection contract/service,
its architecture export, canonical progress records, focused tests and forward migrations.
Completed historical tasks and HMR-080's blocker remain preserved.
After this batch, the next proposed scope is HMR-052 + HMR-060; do not execute it here.

## User-authorized P2 Batch 2 — 2026-10-06

The owner resumed the proposed HMR-052 + HMR-060 scope with `next`.
Baseline main `201e21b16a6bef5c2a346d4107c912c0ed237531` is green in full
CI #570 / run `37476746534`. This two-task envelope executes under HPR-P2-008
in the order HMR-052 then dependent HMR-060, retaining the exact registered
HMR commit messages, independent source reviews HMSR-061/HMSR-072 and tests.
No SCC or cross-module owner-contract prerequisite is introduced.

Adapted write scope admits:

- HMR-052: JpaNotificationMessageRepositoryAdapter, NotificationMessageJpaRepository,
  NotificationMessageSemanticRemediationTest, NotificationMessageIntegrityMigrationTest,
  and forward migration V20261006_005__hmr_052_notification_message_composition.sql.
  The forward filename replaces the unexecuted historical V20261004_052 registration;
  existing applied migrations remain untouched. Database rendering/version/input guards
  are part of the exact-version and required-variable obligations.
- HMR-060: NotificationDeliveryAttempt, JpaNotificationDeliveryAttemptRepositoryAdapter,
  NotificationDeliveryAttemptSemanticRemediationTest, NotificationDeliveryAttemptIntegrityMigrationTest,
  and newly admitted V20261006_006__hmr_060_notification_attempt_evidence.sql.
  The migration is needed for concurrent-writer channel consistency and immutable
  delivery evidence; a check-before-merge alone cannot close the append-only obligation.
- Shared progress records: this roadmap, doc/model-remediation/RECONCILIATION.md,
  docs/roadmap/model-semantic-remediation.md and docs/data definition/Notification.md.

One final ref advance publishes the two semantic commits followed by progress
reconciliation. Final Batch 2 CI validates integration and remains pending at
publication. Follow owner instruction: confirm CI started, then stop until `next`
or `fail`. Next proposed scope is HMR-053 alone after a green Batch 2 head;
HMR-080's owner-contract blocker remains preserved.

### Batch 2 regression repair — HMR-052 / CI #571

CI #571 ran 639 tests with one PostgreSQL parameter/column ambiguity error and no
assertion failures. Admit V20261006_007__hmr_052_qualify_message_validator_parameter.sql,
the existing NotificationMessageIntegrityMigrationTest and these two canonical progress
records for a narrow HMR-052 repair. Preserve every published migration. The replacement
function retains its signature and qualifies only the ambiguous message identifier.
Do not advance to HMR-053 until the repaired final head is green. Per owner instruction,
stop after confirming replacement CI has started; await `next` or `fail`.

## HPR-P2-008 solo execution admission — HMR-053 / 2026-10-06

Owner `next` resumes the registered HMR-053 scope. Exact main baseline
`d61bec9eeee6b2c3c3a9d5ea887b28b664355753` is green in full CI #572 /
run `37480926311`. Source HMSR-062 and current production/schema were revalidated.
No SCC or cross-module ownership dependency exists for this trust operation.

Exact commit: `fix(telemetry): remediate semantic review TrustedTelemetryReading`.
The original repository-only allowlist is expanded to implement the required application
trust boundary: TrustTelemetryReadingUseCase, TelemetryTrustEvidencePort,
TrustedTelemetryReadingApplicationService, TelemetryTrustPolicy and
JpaTelemetryTrustEvidenceAdapter. Also admit the existing trusted repository adapter,
TrustedTelemetryReadingSemanticRemediationTest, TrustedTelemetryReadingIntegrityMigrationTest,
and V20261006_008__hmr_053_trusted_telemetry_gate.sql. The forward filename replaces
unexecuted historical V20261004_053; all published migration bytes remain unchanged.
Progress scope includes this roadmap, canonical RECONCILIATION.md, the legacy semantic
remediation register and Telemetry data definition.

Policy is explicit: PASSED assessment with its MEDIUM/HIGH/CERTIFIED level, ACTIVE
point and active QUALITY_CODE. Capture applicable [validFrom, validTo) binding;
multiple applicable roles require explicit binding selection. Preserve optional
unit/batch provenance and historical snapshots. No Topology relational ownership is added.
Final task CI is pending at publication. As instructed, confirm CI started and stop;
await `next` or `fail`. Next proposed solo scope is HMR-054 only after green CI and
live owner-contract revalidation; HMR-080 remains blocked.

## HPR-P2-008 solo execution admission — HMR-054 / 2026-10-06

Owner `next` resumes HMR-054 from exact green main
`f676e278357ac7bcf2bc8bf55830b16cb324bd73`, full CI #573 / run `37483724317`.
HMSR-063, current enum/classification usage and schema were revalidated. Existing
Party-owned TopologyPartyReferenceContract/query service resolves the historical
owner blocker and is exported by both architecture tests. No new Party contract is needed.

Exact commit: `fix(topology): remediate semantic review Equipment`.
Admit the registered Equipment domain/JPA/type/mapper/repository-adapter scope,
deletion of EquipmentKind, EquipmentSemanticRemediationTest, new
EquipmentIntegrityMigrationTest and the existing TopologyOperationalScopeTargetQueryServiceTest
constructor fixture. Admit forward V20261006_009__hmr_054_equipment_catalog_and_attachments.sql
instead of unexecuted V20261004_054. Preserve all published migration bytes.
Shared progress scope: this roadmap, canonical RECONCILIATION.md, legacy semantic
remediation and Topology roadmaps, and Topology data definition.

EquipmentType identity/code becomes the sole active classification. Old enum strings
are preserved in unmapped legacy_equipment_kind columns; new rows do not require an
enum kind. Conflicting legacy classifications or orphan attachments fail preflight
without data rewriting. Add same-module nullable attachment FKs and validate populated
manufacturer Party identity through its owner while retaining snapshots.

Final task CI is pending at publication. Confirm CI started, then stop as instructed.
Next proposed compatible scope: HMR-063 + HMR-086..089 after green CI and fresh
batch admission/dependency review; HMR-080 remains blocked.

### HMR-054 CI #574 guardrail inventory correction

CI #574 / run `37485895694` on `674914cccf97aa4903df2f6f0e69562e9ce074da`
ran 656 tests with one failure and no errors. DomainInvariantGuardrailTest retained
its pre-HMR-054 required-marker count despite deliberate removal of the duplicated
Equipment.equipmentKind field/guard. Admit a narrow correction to that existing
architecture test plus these canonical records: 583 -> 582 required markers and
610 -> 609 total markers. Ordering (24), self-reference (3), touched records (114)
and the mandatory equipmentTypeId guard remain unchanged. No production or migration
change is required. Standalone execution of the actual inventory test passed;
focused Maven was blocked by the uncached Spring Boot 4.1.1 parent POM.
Replacement exact-head CI is pending; confirm it started and await `next` or `fail`.

## HPR-P2-008 Batch 5 execution admission — 2026-10-06

Owner `next` authorizes HMR-063 + HMR-086..089, matching the supplied 20-batch plan.
Baseline main `89a7c3bcb57377dd720f47721f4a836b03856ca2` has successful CI #575
(run 37487331948) and documentation validation #66. HMR-054's pending CI note
is superseded by this observed green baseline.

One semantic commit per HMR, in order 063, 086, 087, 088, 089; one atomic final
main advancement and shared final CI gate. Do not continue to Batch 6.

Revalidated HMSR-075/101/104/105/106 obligations remain applicable. No SCC is
involved. Admit the following precise additions to their legacy write scopes:

- HMR-063: Organization-owned `application/contract/identity/IdentityEmployeeReferenceContract.java`
  and `application/service/IdentityEmployeeReferenceQueryService.java`; register that
  exact contract export in ArchitectureGuardrailTest and ForensicRemediationClosureTest;
  PostgreSQL UserIntegrityMigrationTest; forward V20261006_010 user uniqueness migration.
- HMR-086: DelegationStatus.java and AuthorizationDelegationGrantIntegrityMigrationTest;
  forward V20261006_011 delegation reason/validity/reference migration.
- HMR-087: HidraPrincipal.java, AuthenticatedPrincipalInput.java,
  AuthenticationCompletionApplicationService.java, AuthenticationSessionLifecycleApplicationService.java,
  DirectAuthenticationApplicationService.java, IdentityAuthenticationWebMapper.java,
  LdapAuthenticationProvider.java, IdentityOidcJwtAuthenticationConverter.java;
  LoginSessionIntegrityMigrationTest and forward V20261006_012 session evidence migration.
- HMR-088: UserPermissionGrantJpaEntity.java and UserPermissionGrantIntegrityMigrationTest;
  forward V20261006_013 direct-grant validity/reason/status migration.
- HMR-089: IdentityAdministrationCommandApplicationService.java and its focused
  UserRoleGrantSemanticRemediationTest. Preserve optional role-grant reason/end and SUSPENDED state.
- Each HMR may update these canonical execution/reconciliation documents independently.

Preserve published migrations, neutral scopes and optional cross-module IDs.
No cross-module FK. Legacy incompatible rows fail closed; never fabricate reasons,
end dates, historical session classification or termination timestamps.
Local runtime is Java 17 without Docker; attempt registered Maven validation and
report actual limitations. Stop once final-head CI is observed started; await `next` or `fail`.

## HPR-P2-008 Batch 5 execution result — 2026-10-06

HMR-063 and HMR-086..089 implemented; independent exact-message semantic commits
preserved. Four forward migrations V20261006_010..013, nine dedicated test classes,
Organization-owned Employee contract and matching architecture exports added.
Source-level domain/session behavior and JPA mapper round trips passed in isolation;
full Maven/JUnit/PostgreSQL verification is pending GitHub CI for the final head.
Detailed limitations and legacy reconciliation requirements are recorded in
`doc/model-remediation/RECONCILIATION.md`.

HPR-P2-008 remains IN PROGRESS: 40 still-required tasks plus blocked HMR-080 remain.
Next proposed scope: **HMR-085 — AuthorizationDecision**, Batch 6 in the owner plan.
Do not execute automatically. Confirm final-head CI has started and await `next` or `fail`.

## HPR-P2-008 Batch 6 preflight disposition — HMR-085 / 2026-10-06

Exact main baseline `925feec7c022a3603afbb4c2fcff47010a64323e` has successful
CI #576 and documentation validation #67. Owner `next` selects HMR-085.

HMR-085 is **BLOCKED — EVALUATION CONTRACT PREREQUISITE**. Source inspection
found no ABAC JSON grammar/executor, trusted assertion context in permission evaluation,
or obligation-execution contract. The legacy write list omits the defective application
flow and the required supporting files. Preserve current production behavior until this
prerequisite is settled; do not claim partial RBAC remediation as complete HMSR-100 closure.

A concrete constrained-JSON/evidence-port/trusted-assertion/configuration proposal is
recorded in `doc/model-remediation/RECONCILIATION.md`, under Batch 6 preflight.
Current HMR-050..106 totals: 16 completed, 39 still required, two blocked (080 and 085).
Next selected task remains HMR-085 after prerequisite resolution; Batch 7 is not admitted
by this execution. No production source or migration change made by this preflight.

## HPR-P2-008 Batch 6 scope admission — HMR-085 / 2026-10-06

Owner `Next` accepts the constrained JSON evaluator prerequisite proposed in
RECONCILIATION.md. Execute HMR-085 alone, exact commit
`fix(identity): remediate semantic review AuthorizationDecision`. Baseline main
341a79a has successful documentation CI #68; its unchanged production tree was
verified by full CI #576 at 925feec7. The previous preflight block is resolved.

The eight numbered prerequisite contracts in the Batch 6 preflight are admitted.
Additional exact write allowlist (paths relative to Identity production/test root):

- `src/main/java/dz/sh/hidra/modules/identity/application/service/IdentityAuthorizationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/model/VerifiedAuthorizationAssertion.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthorizationEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthorizationAssertionPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/port/out/AuthorizationDecisionSettingsPort.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/policy/AuthorizationEvidence.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/service/AuthorizationPolicyEvaluator.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/service/AuthorizationExpressionEvaluator.java`
- `src/main/java/dz/sh/hidra/modules/identity/domain/policy/AuthorizationJson.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/adapter/JpaAuthorizationEvidenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/persistence/entity/AuthorizationDecisionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/SpringAuthorizationContextAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraOidcAuthenticationToken.java`
- `src/main/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityOidcJwtAuthenticationConverter.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/AuthorizationDecisionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/AuthorizationEvidencePostgresTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/AuthorizationAssertionTrustTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/IdentityOidcJwtAuthenticationConverterTest.java`

Documentation allowlist: this roadmap, RECONCILIATION.md, legacy semantic
remediation roadmap and Identity DDD. No migrations or cross-module contracts.
Validation: compile, AuthorizationDecisionSemanticRemediationTest,
AuthorizationEvidencePostgresTest, AuthorizationAssertionTrustTest, complete test
suite and clean verify. One semantic commit, one final-head CI observation; stop
after CI is triggered as instructed by the owner. Do not start Batch 7.

Constructor-wiring regression coverage additionally admits
`src/test/java/dz/sh/hidra/modules/identity/infrastructure/security/HidraAuthorizationOwnershipTest.java`.

Runtime assertion reachability admission: the existing external OIDC chain serves only
completion. Add a self-bound POST `/api/v1/identity/authentication/oidc/evaluate`
using the existing decoder/converter and existing permission request/response.
Additional exact write scope:
- `src/main/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityOidcAuthorizationController.java`
- `src/main/java/dz/sh/hidra/platform/configuration/HidraSecurityConfiguration.java`
- `src/main/java/dz/sh/hidra/platform/configuration/HidraOpenApiSecurityConfiguration.java`
- `src/test/java/dz/sh/hidra/modules/identity/api/rest/controller/IdentityOidcAuthorizationControllerTest.java`
- `src/test/java/dz/sh/hidra/platform/configuration/HidraOpenApiSecurityConfigurationTest.java`
The route cannot evaluate another user; claims remain infrastructure-only and never
become generic Spring authorities. Additive OpenAPI change; retain ordinary routes.

## HPR-P2-008 Batch 6 implementation — 2026-10-06

HMR-085 / HMSR-100 is implemented in its exact-message semantic commit.
Three obligations are discharged: graph/policy evaluation with explicit deny
precedence; truthful deterministic evidence including verified external mappings;
and the existing optional persistence switch. The accepted prerequisite and
runtime OIDC route scopes above supersede the historical preflight block.

No migration, cross-module FK or raw-claim authority was added. Unsupported
obligations/resource context and approval-dependent mappings fail closed.
Ten standalone semantic checks passed; framework source compilation with temporary
API stubs and Java syntax checks passed. Maven compile/test/clean verify remain
blocked by Maven Central DNS/uncached Boot parent, Java 17 and absent Docker.
New PostgreSQL/trust/API regressions require final-head CI, pending at preparation.

Current reconciliation: 17 implementations, 39 still required, one blocked (080).
Next recommended owner scope: Batch 7 Workflow execution (055, 061, 066, 081, 099),
subject to green Batch 6 CI and fresh admission. Stop after CI trigger observation.

## HPR-P2-008 Batch 6 CI #577 repair — HMR-085 / 2026-10-06

Owner `Fail` authorizes repair of failed run 37518264529 at 03db23c4.
Production compilation passed; test compilation failed before any tests ran because
HidraAuthorizationOwnershipTest supplied an OIDC-only context adapter to the
unchanged five-argument LdapAuthenticationProvider constructor.
Exact repair commit: `fix(identity): repair LDAP authorization ownership test fixture`.
Write scope: the already admitted HidraAuthorizationOwnershipTest.java and this
roadmap plus RECONCILIATION.md. Remove only the extra LDAP constructor argument;
retain the OIDC context adapter. No production/migration changes or Batch 7 work.
Validation: check constructor wiring and Java syntax, attempt focused ownership
test/full verification, then observe replacement final-head CI trigger and stop.

Repair validation: Java test-source parsing and four constructor-arity checks
passed, as did git diff --check. Focused Maven ownership/OIDC tests and clean
verify remain blocked locally by uncached Boot parent 4.1.1. Replacement CI
pending at preparation; no test execution success claimed.

## HPR-P2-008 Batch 6 CI #578 repair — 2026-10-06

Owner `Fail` selects failed run 37518717960 at fb47ae27. Its repository
`clean verify` gate and current-head OpenAPI publication passed. The failure is
historical-base OpenAPI packaging at previous commit 03db23c4: `-DskipTests`
skips test execution but still compiles that revision's broken LDAP fixture.
Exact repair commit: `fix(ci): skip historical tests when building OpenAPI base`.
Additional exact write scope: `.github/workflows/ci.yml`, this roadmap and
`doc/model-remediation/RECONCILIATION.md`. Use `-Dmaven.test.skip=true` only
for the isolated base-revision application package. Preserve full current-head
clean verify, exact previous-SHA selection and the OpenAPI compatibility gate.
Validate workflow YAML/bash and scope, attempt base packaging locally, publish
and stop after confirming replacement final-head CI has triggered. No Batch 7.

Repair validation: workflow YAML/bash syntax and checks preserving current-head
clean verify, exact previous-SHA comparison and compatibility enforcement passed.
Local packaging remains blocked by the uncached Boot parent; git diff --check
passed. CI #578 provides successful repository clean verify evidence at fb47ae27;
complete replacement CI remains pending at preparation.
