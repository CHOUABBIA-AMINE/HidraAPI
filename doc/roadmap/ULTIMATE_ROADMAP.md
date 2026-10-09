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
| P2.5 operational digital twin | PLANNED / OPEN — owner-prioritized telemetry-to-simulation-to-decision-to-outcome-to-learning cycle; runtime NOT ESTABLISHED |
| P2 canonical governance | VERIFIED/CLOSED — renewed independent audit 12/12 VERIFIED at 7be1c9cb47ed9b6f73a7692d0a328ad870e2b4d4; exact-head Documentation Validation #136 and production CI #609 PASSED at a8905e32289a583f47b831e0381783e556ae0c8d; earlier 9/12 audit preserved as history |
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
10. Phase P2.5 is the owner-prioritized operational digital twin workstream following P2 closure; its registration does not establish runtime execution. P3 capabilities remain deferred until measured requirements and approved architecture justify adoption.
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
| HPR-P2-005 | COMPLETED — audit Check 9 documentation correction at 4d41f265374156846a86d31f3d30e2b0fe00dd91; exact-head Documentation Validation #131 PASSED; runtime/OpenAPI unchanged | API | Code/Doc | Complete source-backed API conventions and runtime error documentation under the bounded audit amendment below; preserve the generated-envelope limitation and committed contract. | `docs(api): establish versioned api contract` | HPR-P1-008 |
| HPR-P2-006 | COMPLETED — Stage B 1bc3c1bba2d08a0b493e5ece43e834e8d56d04f0; Documentation #134 and Production #608 PASSED; integrated physical dictionary check true, 482 relations and zero unresolved owners | Database | Doc | Complete the current physical table/column dictionary and explicit ownership/FK/migration governance under the bounded audit amendment below; preserve applied SQL and historical evidence. | `docs(database): establish canonical database documentation` | HPR-P2-001 |
| HPR-P2-007 | COMPLETED — exact-source reconciliation established: HMR-005 corrected to completed, HMR-009 confirmed completed/stale carry-over removed, HMR-054 historical blocker resolved by current Party→Topology contract, HMR-050..106 reconciled to 56 still-required + 1 blocked (HMR-080), 0 superseded; legacy roadmap preserved as history | Semantic Remediation | Code/Doc | Inventory unresolved HMR/HMSR obligations against exact current source; mark each as completed, still required, blocked, or superseded with evidence. | `docs(model-remediation): reconcile remaining semantic obligations` | HPR-P2-003 |
| HPR-P2-008 | COMPLETED — owner-authorized closure after final reconciliation preflight; all 57 HMR implementations confirmed by CI #604, zero pending, STILL REQUIRED or BLOCKED; documentation preflight CI #115 passed. P2 phase remains open. | Semantic Remediation | Code | Execute still-required semantic remediation in dependency order using revalidated HMSR obligations; do not restart completed HMRs without regression evidence. | `fix(model): continue reconciled semantic remediation` | HPR-P2-007 |
| HPR-P2-009 | COMPLETED — 123 permanent subject decisions transferred with current source/test/migration evidence; all 24 module inventories and domain/index metadata refreshed; 37-path scope and 83-file Markdown/link checks passed; exact-head documentation CI follows publication | Semantic Remediation | Doc | Transfer permanent semantic decisions from legacy review/roadmaps into `doc/domain/` and `doc/modules/`, then preserve legacy files as execution history. | `docs(model-remediation): canonicalize semantic decisions` | HPR-P2-008 |
| HPR-P2-010 | COMPLETED — five-document governance baseline established; 24-module ownership, approved infrastructure retention, source-backed provenance and TARGET legacy admission separated from unknown business approvals; nine-path and 88-file Markdown checks passed; exact-head documentation CI follows publication | Data Governance | Doc | Create data governance, retention/archival, provenance and legacy-data migration documents without inventing retention values. | `docs(data): establish data governance baseline` | HPR-P2-001 |
| HPR-P2-011 | COMPLETED — six source-backed verification documents established; all 24 module roots and 57 closed HMR obligations linked; ten-path and 94-file Markdown checks passed; test presence, historical CI and uninspected execution/skip evidence remain distinct; exact-head documentation CI follows publication | Testing | Doc | Create test strategy, architecture testing, database testing, API testing and requirements traceability documents tied to executable evidence. | `docs(testing): establish verification documentation` | HPR-P2-002..006 |
| HPR-P2-012 | COMPLETED — maintained canonical metadata/link/index/module/P2 and deterministic OpenAPI validators/tests wired into both CI workflows; snapshot refreshed from verified CI #604 artifact; strict fresh-runtime equality added; local Maven blocked before compilation by Boot-parent/Central DNS; exact-head CI follows publication | Documentation CI | Infra | Add documentation validation for canonical links/status/index drift and deterministic OpenAPI contract checks. | `ci(docs): validate canonical documentation` | HPR-P2-001..011 |
| HPR-P2-013 | COMPLETED — renewed 12/12 audit VERIFIED at 7be1c9cb47ed9b6f73a7692d0a328ad870e2b4d4; exact-head Documentation Validation #136 and production CI #609 PASSED at a8905e32289a583f47b831e0381783e556ae0c8d; P2 VERIFIED/CLOSED | Governance | Doc | Reverify all twelve P2 checks after HPR-P2-005/006 corrections, canonical status/index consistency and exact-head CI; close P2 only with 12/12 VERIFIED. | `docs(roadmap): close P2 canonical governance` | HPR-P2-001..012 |

### Phase P2.5 — Operational Digital Twin and Decision Feedback

**Status: PLANNED / OPEN — requirements registration only; runtime capability NOT ESTABLISHED.**
Stable task prefix: `HPR-P25` (Phase 2.5). Owner instruction on 2026-10-09 explicitly
selects this roadmap update after defining the following product priority:

**Collected telemetry -> analytics/network-state assessment -> issue or safety/security
concern -> real simulation -> proposed decision -> actual decision and measured outcome
-> validated AI learning.**

Hidra must visualize connected pipeline networks carrying gas, oil, hydrogen (H2),
blends and other configured products. The purpose is to prevent a locally beneficial
operator action from causing unacceptable effects elsewhere. Simulation must evaluate
the connected network state and consequences, rather than react to an isolated reading.
Local, geographical and full-network scopes are required; geographic selection alone
is insufficient to establish hydraulic independence.

This phase follows CLOSED P0/P1/P2 and is the next product priority before general P3
industrial scaling. P3 rows stay DEFERRED: TimescaleDB/PostGIS/MQTT adoption is not
implicitly selected. A measured P2.5 prerequisite may separately select the relevant
P3 task with its existing approval gates; Phase 2.5 does not bypass those gates.
Version remains `0.7.0-SNAPSHOT`; no release or deployment is selected.

#### Verified foundation and missing execution boundary

At source baseline `275a5d38e888ad70e33db4081ef03783e9e1e427`, topology has network
models and a working source-visible map/GeoJSON adapter/controller; telemetry has raw
and trusted reading models/application services; simulation has model/scenario/run
records and run queueing. These are reusable foundations, not end-to-end twin proof.
`DigitalTwinReadinessAssessment.runtimeDigitalTwin()` returns false, and
`SimulationSolverPort` only reports availability; no executing implementation was
found. Continuous field synchronization, calibrated state estimation, executing
hydraulic scenarios, automated issue-to-simulation orchestration, operator outcome
feedback and deployed learning are NOT ESTABLISHED by this baseline.
See [simulation/analytics semantics](../domain/SIMULATION_ANALYTICS_AI.md).

#### HPR-P25-001 Requirements and Execution Preflight — 2026-10-09

Owner Next selects this documentation-only preflight after Documentation #142
(run 37948108881) PASSED on registration SHA
`02e86faa48c49c0c83c2ad51ccc4f1c52686f6c4`, tree
`2f0354ef2677706c9d67a267ea3419120086ed21`. The verified local source tree is
identical. Exact message: `docs(twin): define requirements and execution preflight`.
Write only this roadmap and `doc/model-remediation/RECONCILIATION.md`.

**Historical disposition at d65cba7: review delivered; HPR-P25-001 was BLOCKED.**
The approved decisions recorded below supersede that product-direction blocker.
The following original review retains its baseline applicability.
The desired multi-product decision-feedback cycle is approved as a product target;
no first demonstrator, hydraulic mode, solver access or numeric operational acceptance
limits have been supplied. AGENTS.md §19 preserves `0.7.0-SNAPSHOT`. Do not interpret
this preflight as physics approval, a simulator purchase or implementation selection.

##### Exact-source gap and ownership matrix

Paths below are under `src/main/java/dz/sh/hidra/modules/` at the verified parent.
Presence is source evidence, not a current field integration or accuracy claim.

| Requirement | Source evidence | Missing obligation / proposed owner boundary |
|---|---|---|
| Connected physical network | `topology/domain/model/Pipeline.java` has nominal diameter, design pressure, length and units; `topology/infrastructure/persistence/entity/PipelineSegmentJpaEntity.java` has node endpoints, KP, length and direction; nodes/segments are persistence models, not current domain aggregates | Topology owns connectivity/configuration. Establish approved versioned input export and segment-level physical completeness; do not invent existing domain node/segment records. |
| Product identity versus physics | `custody/application/contract/planning/PlanningProductReferenceContract.java` returns id/code/active; `custody/infrastructure/persistence/entity/CustodyBatchJpaEntity.java` has productTypeId | Custody retains catalogue identity; proposed Simulation-owned immutable fluid/composition/property-method inputs need explicit contract approval. A Planning-specific contract is not automatically reusable by Simulation. |
| Operator map | `topology/infrastructure/visualization/JpaTopologyMapVisualizationAdapter.java` and `topology/api/rest/controller/TopologyMapController.java` expose layers/GeoJSON; segment lines derive from endpoints | Reuse map APIs; add product/state/issue overlays and authorization only for demonstrated gaps. Authoritative surveyed routes and an integrated frontend are not proved. |
| Raw and trusted measurements | `telemetry/domain/model/TelemetryReading.java` retains timestamp/sequence/hash/correlation; `telemetry/application/service/TrustedTelemetryReadingApplicationService.java` checks PASSED trust, active point and binding | Telemetry owns measurement trust/provenance; coherent watermarks, freshness policy, units and live acquisition need verified contracts and source evidence. |
| External acquisition | `integration/infrastructure/integration/NoopExternalConnectorGateway.java` returns true for canConnect/canExchange without performing acquisition | Integration owns approved read-only external transport. Those booleans cannot establish source reachability, data synchronization or OT access. |
| Analytics and estimated state | `analytics/domain/model/DigitalTwinReadinessAssessment.java` returns runtimeDigitalTwin false; `analytics/application/service/AnalyticsApplicationService.java` provides analytics use cases | Analytics owns derived read models/state estimates. Physical estimator execution, uncertainty and observability gates are not established; never overwrite operational truth with estimates. |
| Issue detection | `monitoring/application/service/MonitoringRuleApplicationService.java` creates rules; `monitoring/domain/service/DeviationSeverityClassifier.java` classifies percentage differences; LeakDetection has case services | Monitoring/LeakDetection own their issue records. Existing thresholds are not calibrated multi-product detector limits; correlated state-driven detection and cyber-source evidence need explicit scope. |
| Simulation execution | `simulation/application/service/SimulationApplicationService.java` queues runs; `simulation/application/port/out/SimulationSolverPort.java` only has available(referenceId); repository search found no implementation | Simulation owns execution/model/result lifecycle behind ports; implement a real solver adapter, validate snapshots/boundaries and retain diagnostics before claiming hydraulic calculation. |
| Decisions and learning | `simulation/domain/service/SimulationSafetyGuard.java` forbids field actuation; `analytics/domain/model/AnalyticsInsight.java` is advisory-only; Workflow/Audit and simulation recommendations exist | Proposed Workflow-owned decision/action capture with Audit evidence, Analytics-owned outcome evaluation and governed learning, Simulation-owned predictions. Actual action/outcome linkage and learning effectiveness are not established. |

##### Required product and physical-input contract

| Target | Visualization requirement | Simulation qualification before supported status |
|---|---|---|
| Gas | Product/composition labels, pipelines/stations and operating configuration | Approved composition/property method, pressure/temperature/flow basis, compression curves and relevant inventory/boundary behavior; modes selected by owner |
| Oil/liquid | Product/batch labels, pumping configuration and connected routes | Approved fluid density/viscosity/property provenance, elevation/profile and pump characteristics; transient/surge claims require appropriate validated mode |
| H2 | Explicit hydrogen identity, operating configuration and asset restrictions | Validated H2 property/model support plus approved asset/material operating constraints; gas catalogue classification alone is insufficient |
| Blends/other products | Configurable identity and product assignment with clear unsupported markers | Approved composition range, property method and equipment compatibility envelope; prohibit silent default to another fluid's model |

A simulation input must include snapshot identity/time/watermark, topology/configuration
version, asset IDs, nodes/connections, segment lengths/elevations/internal diameter and
approved resistance parameters, fluid/composition/property version, boundary conditions,
active equipment state and current curves, units and flow reference basis, measurement
quality/uncertainty, operating limits and solver product/mode capability. Required versus
optional fields and allowed estimation must be fixed by the selected solver/mode contract.
Existing nominal diameter must not silently become internal diameter; design pressure
must not silently become an approved operating limit. Missing critical inputs invalidate
execution rather than become zero/default values. Numerical limits remain unapproved.

##### Decision register — no fabricated operational policy

| Decision | Current status / required owner evidence | Blocks |
|---|---|---|
| D25-01 first demonstrator | OWNER SELECTED: GZ2 gas pilot. Exact topology, connected boundaries, action cases and data owner are versioned pilot data/qualification inputs, not hardcoded engine constants. All products remain target scope. | Real GZ2 pilot qualification; synthetic development may proceed |
| D25-02 physical modes | OWNER SELECTED: both steady-state and transient for gas; build/verify steady-state before transient. Specific event/model fidelity is established by numerical design and validation. | Qualified mode claims; no transient accuracy inferred from selection |
| D25-03 real engine | OWNER SELECTED: Hidra-owned engine. No external license/access prerequisite for developing our engine; mathematical design, property methods, numerical verification and executable adapter remain required. | HPR-P25-008A..F and umbrella execution evidence |
| D25-04 input ownership | ENGINEERING DESIGN FOR PREFLIGHT: preserve Topology network/configuration, Custody catalogue identity, Telemetry trust and Simulation immutable fluid/model/curve input snapshots; define exact exported contracts during HPR-P25-002 preflight. Owner has selected dynamic data, not a new catalogue owner. | Exact HPR-P25-002 input contracts before source mutation |
| D25-05 measurements/security | UNRESOLVED: acquisition source and interface, read-only access/environment, unit/flow basis/time alignment, freshness/latency/replay policy; independent cybersecurity evidence source if included | HPR-P25-004/006 field claims |
| D25-06 physical acceptance | UNRESOLVED: independent reference dataset, sensor uncertainty, residual/calibration/convergence tolerances per product/mode, estimator observability and valid envelope | HPR-P25-005/009 and closure |
| D25-07 issue/decision policy | UNRESOLVED: issue coverage, limits/severity/false-alarm acceptance, feasible candidate actions, approval authority, simulation budgets and emergency escalation | HPR-P25-006/010/011/012 |
| D25-08 operator and outcomes | UNRESOLVED: frontend repository/environment, pilot approval, actual-action evidence, observation windows and attribution/confounder policy | HPR-P25-013/015/016 |
| D25-09 learning governance | UNRESOLVED: learning objective, admissible data, held-out evaluation, drift thresholds, promotion authority and rollback criteria | HPR-P25-014/017 |

Historical alternatives considered before the own-engine decision (not selected): existing
licensed external engine via supported API/batch/service interface; approved open-source
engine with demonstrated product/mode coverage; purpose-built internal engine with its
own verification and maintenance program. No external engine, vendor, interface or license
is selected or claimed available. The subsequent owner decision selects an internal
engine; these alternatives no longer block product-direction selection. Evaluate capability, reproducibility,
calibration, integration security, failures, operations and licensing before D25-03 closes.

##### Acceptance fixture register — specifications, not executed tests

| Fixture | Required expected behavior / evidence | Acceptance values |
|---|---|---|
| F25-01 product/network map | Gas, oil, H2 and other-product assets/connectivity and switching configurations render consistently; unsupported simulation shown explicitly | Product assignments and reference topology approved by data owner |
| F25-02 trust/replay | Duplicate/out-of-order/stale/invalid-unit/disconnected readings preserve raw provenance and cannot create a falsely fresh trusted state; replay recovery deterministic | D25-05 bounds, clock/unit policy and trusted-reading criteria |
| F25-03 reconstructed state | Known measured/estimated/unknown quantities remain distinct; unobservable or inconsistent boundary state refuses actionable results | D25-06 uncertainty/observability/residual limits |
| F25-04 local harm elsewhere | Candidate improves station A but violates a constraint at connected station B; proposal rejects/modifies action and explains affected assets; disconnected network unaffected | Approved solver reference case, D25-06 accuracy and D25-07 limits |
| F25-05 scope expansion | Geographic cut intersects connected effects; insufficient boundary data expands scope or refuses execution; local/full runs compared | Approved cut/boundary case and comparison tolerance |
| F25-06 real engine failures | Actual adapter executes pinned engine; timeout, unavailable engine, cancellation, invalid product/mode and nonconvergence never become successful predictions | D25-03 interface/timeout contract and resource budget |
| F25-07 issue coverage | Positive/negative process/sensor/security cases distinguish suspect signals, repeat triggers and true issues; urgent alarm path proceeds independently | D25-07 detector and D25-05 security-source policy |
| F25-08 decision and outcome | Reject/modify/apply paths capture authenticated actor and actual action/time; predicted/observed differences include concurrent changes and missing outcomes | D25-08 observation/attribution policy |
| F25-09 learning rollback | Dataset excludes unapplied recommendations as action labels; held-out evaluation gates promotion; adverse update rolls back without relaxing physics/safety limits | D25-09 evaluation and drift criteria |
| F25-10 end-to-end operator pilot | Selected frontend shows the full telemetry/issue/run/proposal/action/outcome trace with authorization and environment versions | D25-01/08 pilot acceptance and full Phase 2.5 coverage matrix |

These fixtures provide reproducible requirements. No numerical hydraulic, detector,
latency or learning acceptance is approved and no fixture execution is claimed here.

##### Bounded implementation envelopes and next selection

All implementation paths remain non-authorized until their exact selected-task preflight.
The following decomposition controls scope; it is not a broad module write allowlist:

- HPR-P25-002: first settle D25-04 and the selected input/product/mode contract. Reuse
  existing Topology/Custody identities. Separate topology export, fluid/version inputs
  and equipment/configuration additions into individually registered stages if required.
  Domain nodes/segments must not be invented from persistence inventory. Exact new files
  and any forward migration name depend on this design decision and are not yet admitted.
- HPR-P25-003/004: bounded map read-model/API work and one approved read-only acquisition
  adapter; preserve owner contracts, unit/trust semantics and strict access boundaries.
- HPR-P25-005..009: bounded estimator, detector, connected-scope resolver, real solver
  adapter and independent calibration tests; no universal physics engine is implied.
- HPR-P25-010..014: bounded issue/run orchestration, candidate evaluation, actual-action
  capture, outcome evaluation and offline learning stages; retain immutable provenance.
- HPR-P25-015..017: integrated frontend evidence, replay/pilot operations and independent
  closure audit; separate frontend changes require their own repository authorization.

Every implementation preflight must name exact source/test/contract/API/docs paths,
forward migration filename (or justified no-migration decision), owner exports and
ArchitectureGuardrailTest changes if required. Validate focused behavior plus Java 21
`./mvnw -B -q clean verify`, applicable PostgreSQL/OpenAPI/architecture checks and
exact-head CI; real solver/field/pilot evidence is additional to mocks and CI.
For this preflight run only canonical documentation/OpenAPI/P1 evidence validation,
whitespace and exact two-file scope; runtime Maven is not required by doc-only rules.

**Current next executable disposition:** owner product/mode/engine decisions are
recorded below; next select HPR-P25-002 documentation-only input-contract preflight
before any implementation. Field values and GZ2 data acceptance gate the actual pilot,
not independent engine development with explicitly synthetic reference fixtures. Supporting decision-record
message: `docs(twin): record approved phase 2.5 input decisions`. Record only decisions
actually supplied by the owner; do not promote proposals to approved policy. Missing
operational/solver data blocks affected later stages, not all independent future review.
No implementation, migration, solver installation, release or deployment is selected.

#### Approved Direction and Dynamic Input Decision Record — 2026-10-09

Owner statements after HPR-P25-001: gas; GZ2 pilot; both steady-state and transient;
Hidra's own simulation engine; input data can change daily. The owner's Next selects
this supporting decision update, exact message
`docs(twin): record approved phase 2.5 input decisions`. Write ONLY the Ultimate
Roadmap and reconciliation. Verified parent d65cba71421ed5c994a8f63864754aa6083b7837,
tree 8991a8b4ceed382cd324a85b554dfd3f061c646e; Documentation #143
(run 37953635173) PASSED. No source/configuration/migration changes occur here.

**Preflight delivery is complete; implementation and field qualification remain pending.**
Do not claim the owner approved numeric tolerances, a physics formulation, actual
GZ2 asset inventory or a field deployment. Those are later technical/data gates.
The former lack of actual GZ2 data must not prevent designing the engine and its
input contracts using representative, explicitly synthetic reference networks.

##### Dynamic data lifecycle

- Topology/configuration: connected network, routes, station/equipment identities and
  valve/compressor configurations; version each effective change, never hardcode GZ2.
- Model/parameter data: gas composition/property input, pipe dimensions/resistance,
  elevations, current equipment curves and approved limits with provenance, units,
  effective periods and validity. Do not assume every parameter changes daily or allow
  unreviewed sensor values to replace structural parameters and safety limits.
- Operating state: timestamped trusted pressure/flow/temperature and equipment states;
  preserve measured/estimated/unknown distinctions, quality, age and source watermark.
- Run inputs: immutable coherent snapshot of all selected versions and times, with
  deterministic content identity. Corrections create a new version/run and cannot
  silently alter previous results. A latest-data view is not a reproducible run input.
- Transient inputs: frozen initial state plus a versioned time-dependent boundary/action
  schedule for the simulation horizon. Distinguish measured history, planned scenario
  actions and forecasts; snapshots do not imply constant conditions throughout a run.
- Outcomes: compare the actual action and measured timeline with the matching prediction,
  accounting for changed configurations/parameters and concurrent actions. State estimation
  and calibration updates must retain their own provenance and validation.

Development may execute explicitly synthetic test cases with known answers and test-only
limits. Reject incomplete/inconsistent actual run inputs. Field synchronization, calibration
and operational recommendations need qualified GZ2 data and approved acceptance policies.
Synthetic success is not GZ2 capacity, safety or operating performance evidence.

##### Own-engine decomposition under HPR-P25-008

The old external-adapter-sized task is insufficient for an internal solver. Register the
following bounded prerequisites; each has its own exact message and later exact-file
preflight. The existing HPR-P25-008 identity/message is retained as the final governed
execution integration. No mathematical method or implementation is selected here.

| Code | Status | Domain/Module | Type | Exact execution requirement | Exact commit message | Depends on |
|---|---|---|---|---|---|---|
| HPR-P25-008A | PENDING | Simulation / Numerical design | Doc | Specify gas equations/assumptions, gas-property methods, units/reference basis, boundary/initial conditions, supported network/equipment behavior, numerical method/convergence/stability plan, test tolerances and independent reference strategy. | `docs(simulation): define internal gas engine numerical design` | HPR-P25-001 decisions; HPR-P25-002 input contract |
| HPR-P25-008B | PENDING | Simulation / Steady-state | Code/Test | Implement verified steady-state pipe/network calculation against analytical or independent synthetic reference cases; expose residuals, conservation, validity and nonconvergence, without claiming GZ2 calibration. | `feat(simulation): implement internal steady state gas solver` | HPR-P25-008A |
| HPR-P25-008C | PENDING | Simulation / Equipment | Code/Test | Integrate approved compressor/valve/regulator behavior and current curve/configuration input contracts; test constraints, flow changes and effects across connected branches. | `feat(simulation): model gas network equipment behavior` | HPR-P25-008B; HPR-P25-002 equipment inputs |
| HPR-P25-008D | PENDING | Simulation / Transient | Code/Test | Implement time-dependent gas-network behavior with initial state and boundary/action timelines; verify conservation, spatial/time refinement, stability and applicable event response within declared fidelity. | `feat(simulation): implement internal transient gas solver` | HPR-P25-008C |
| HPR-P25-008E | PENDING | Simulation / Independent verification | Test/Doc | Independently verify both engine modes against reproducible analytical/manufactured/reference cases, numerical refinement and adverse/failure cases; publish supported range and errors, distinct from GZ2 calibration. | `test(simulation): verify internal gas engine numerical behavior` | HPR-P25-008B..D |
| HPR-P25-008F | PENDING | Simulation / Execution adapter | Code/Test | Bind versioned engine to run lifecycle and immutable input/result contracts; verify actual execution, cancellation/timeouts/resource budgets and reproducibility; keep numerical code isolated from controllers/JPA. | `feat(simulation): integrate internal gas engine execution` | HPR-P25-008E; HPR-P25-007 connected scope |

HPR-P25-008 completes only after all six prerequisites and governed execution evidence.
HPR-P25-009 remains the separate calibration/physical validation gate with actual
qualified GZ2 measurements and current equipment parameters. HPR-P25-005 may design
its estimator before engine completion; claimed physical estimation accuracy requires
verified model execution and independent data. No dependency cycle is introduced:
numerical design/engine verification can use synthetic network inputs; operational scope
and live-state orchestration follow their existing dependencies.

HPR-P25-008A..F preflight messages follow
`docs(twin): register HPR-P25-008X execution preflight`, replacing X with A..F.
All numerical business behavior belongs to Simulation, never the Kernel or technical
Platform. Product/mode support is explicit: internal gas development does not qualify
liquid, H2 or blend calculations. Multi-product visualization/extension targets remain.
No hardcoded GZ2 geometry/limits, autonomous OT actuation or unsafe recommendation is
admitted. Phase closure must account for every added prerequisite and coverage limit.

##### Next bounded preflight

HPR-P25-002 next selection is documentation-only: same two execution memories,
exact supporting message `docs(twin): register HPR-P25-002 execution preflight`.
Settle the technical input ownership/export contracts, version/effective-time semantics,
immutable snapshot schema and narrowly registered source/test/migration stages.
Its design may use synthetic gas networks; do not demand daily pilot values before
creating reusable input contracts. Exact new files/migration names and numerical engine
methods are selected during their bounded preflights, not by this decision record.

#### HPR-P25-002 Input-Contract Execution Preflight — 2026-10-09

Owner Next selects ONLY this documentation-only preflight, exact message
`docs(twin): register HPR-P25-002 execution preflight`. Write ONLY the roadmap and
reconciliation. Verified parent `61c1311f458e6844d28c4b4d162b0916885d01a0`, tree
`774e1806a8953b5582dcb32081256449d5d290de`; Documentation #144
(run 37955963870) PASSED. Mandatory instructions and both execution memories read;
local source tree matches. Source behavior remains unchanged by this preflight.

##### Source revalidation and selected technical boundaries

- Topology exports `SimulationTopologyScopeContract` and
  `SimulationTopologyTargetContract` under its already architecture-exported
  `application/contract/simulation` package. Scope resolution currently supports
  active PIPELINE/PIPELINE_SYSTEM and explicitly rejects SEGMENT_GROUP/FACILITY_NETWORK.
  It checks eligibility, not physical completeness or immutable connected-network data.
- `topology/infrastructure/persistence/entity/TopologySnapshotJpaEntity.java` has
  snapshot code/version and JSONB payload. Do not infer payload schema, numerical
  completeness or immutable capture API from table fields.
- `simulation/infrastructure/persistence/entity/SimulationInputSnapshotJpaEntity.java`
  has scenario/topology/other snapshot references, capturedAt and captureHash; no
  corresponding current SimulationInputSnapshot domain record was found. Preserve this
  entity/table and its historical semantics; the first stage does not remap it.
- Node/segment JPA records and Equipment expose asset connectivity/identity, not complete
  physical curves or internal gas-engine inputs. Custody's Planning product contract
  exports catalogue identity only. Telemetry trust remains owned by Telemetry.
- ArchitectureGuardrailTest already exports Topology's simulation contract package.
  The first stage below imports no foreign module, so no new export/architecture
  allowlist change is needed. Later owner adapters require explicit exact-file review.

Selected engineering boundary: Simulation owns immutable solver input representations
and copies of versioned parameters, not the mutable source-of-truth network/catalogue.
Topology retains connectivity/configuration; Custody retains product identity; Telemetry
retains raw/trusted measurement provenance. Integration handles read-only acquisition.
Adapters use owner-exported DTOs, never foreign domain aggregates/JPA entities. Engine
inputs are prepared through Simulation application orchestration and local ports.
This is an implementation design consistent with existing boundaries, not fabricated
approval of GZ2 operating parameters or a new cross-module catalogue owner.

##### Input decomposition and version semantics

First build an **input manifest**: identities, versions, validity and provenance of the
inputs selected for one calculation. This is a concrete domain contract, not a placeholder
solver or a claim that physical payloads already exist. Later stages add typed payloads,
owner exports and capture/persistence. Distinguish metadata validation from physical
readiness; a valid manifest alone must never authorize an operational recommendation.

Every source version records kind, owner, source identity, immutable revision identity,
payload SHA-256 reference, recordedAt, effectiveFrom and optional effectiveUntil, plus
origin/evidence identity. Use inclusive start/exclusive end intervals. A missing end
means open-ended validity, not that the value is verified forever. Corrections create a
new revision; never update old run inputs. A digest is a reference at the domain stage;
canonical hashing/content verification belongs to capture, not guessed hash strings.

The manifest records identity/schema version, product identity reference, scope identity,
STEADY_STATE or TRANSIENT mode, initial stateAt, capturedAt, source versions, optional
measurement watermark and transient horizonEnd. Versions must be valid at initial
stateAt and recorded by capturedAt; initial state cannot be later than capture. Planned
future conditions belong in the transient schedule, never invented measured history.
Transient runs additionally reference a BOUNDARY_SCHEDULE with horizonEnd > stateAt.
A steady-state manifest has no transient horizon/schedule. A watermark, when supplied,
cannot be later than stateAt. Future forecast/scenario events remain distinguishable
from observations. Do not assign arbitrary freshness/uncertainty limits here.

Base source kinds: TOPOLOGY_CONFIGURATION, FLUID_MODEL, EQUIPMENT_PARAMETERS,
OPERATING_STATE; require exactly one explicit reference for each kind. EQUIPMENT_PARAMETERS
may identify an explicitly empty equipment model for a synthetic pipe-only network,
not an implicit missing/default curve. TRANSIENT adds BOUNDARY_SCHEDULE. Every source
kind has one revision in the initial manifest; an older/newer duplicate cannot silently
win. Richer partitioned inputs require a separately registered schema revision.
Origins: APPROVED_PARAMETER, TRUSTED_TELEMETRY, ESTIMATED, SYNTHETIC, SCENARIO and
FORECAST. Origin is declared evidence metadata, not proof of trust; later capture
resolves source evidence and validates whether the origin is permitted for the kind.
Mark any manifest with a SYNTHETIC source as synthetic; no operational readiness method
is introduced. Engine product/mode capability checks remain a separate execution gate.

##### Bounded HPR-P25-002 implementation stages

The parent HPR-P25-002 stays PENDING until all required stages are implemented and
verified; this preflight completion does not complete product-aware physical inputs.
Each stage retains its own commit and later evidence; no batch is selected.

| Code | Status | Domain/Module | Type | Exact execution requirement | Exact commit message | Depends on |
|---|---|---|---|---|---|---|
| HPR-P25-002A | COMPLETED — implementation eef35dbd35d4558a4ceb57f87d8670fb97c933ea passed full Java 21 CI #612 and Documentation #146; three immutable contracts and 14 JUnit tests present; per-class report not independently retained at this baseline | Simulation / Input manifest | Code/Test | Implement immutable manifest, source-version identities/validity/origin and steady-state/transient metadata invariants under the exact six-file envelope below. | `feat(simulation): establish immutable input manifest contracts` | HPR-P25-002 preflight Documentation CI success |
| HPR-P25-002B | PENDING — payload preflight delivered; B1..B4 registered, none implemented | Simulation / Physical payloads | Code/Test | Define validated typed network/fluid/equipment/initial-state and transient timeline payloads with units, reference basis and missing-data semantics; register exact files and numeric design assumptions before mutation. | `feat(simulation): define versioned physical input payloads` | HPR-P25-002A; selected payload/physics preflight |
| HPR-P25-002C | PENDING | Owner modules / Simulation | Code/Test | Implement bounded owner-exported topology/product/measurement queries and Simulation adapters; preserve eligibility/trust/ownership and reject unavailable physical inputs. | `feat(simulation): resolve versioned owner input references` | HPR-P25-002B; exact owner-contract/export preflight |
| HPR-P25-002D | PENDING | Simulation / Capture | Code/Test | Assemble coherent immutable payload/manifest captures, verify canonical hashes and evidence, persist reproducible revisions and reject incompatible/missing inputs; register forward migration only if source-backed design requires one. | `feat(simulation): capture reproducible network calculation inputs` | HPR-P25-002C; exact capture/schema preflight |

##### HPR-P25-002A exact implementation envelope

The next owner Next after successful preflight Documentation CI selects ONLY
HPR-P25-002A implementation; this preflight already registers its exact scope.
Do not create another documentation-only preflight for A unless evidence changes.

Create ONLY:

1. `src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationInputMode.java`
   — enum STEADY_STATE and TRANSIENT; no implicit mode default.
2. `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputSourceVersion.java`
   — immutable Java record with nested SourceKind/Origin enums and local validation.
3. `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifest.java`
   — immutable Java record with defensive list copy and coherent mode/time/version rules.
4. `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifestTest.java`
   — meaningful positive/negative metadata and temporal/immutability cases.

Update ONLY `doc/roadmap/ULTIMATE_ROADMAP.md` and
`doc/model-remediation/RECONCILIATION.md`. Total authorized scope: six paths.
Canonical Java headers keep Author Abir MEDJERAB and CreatedOn 2025-06-26.
Reuse existing InvalidSimulationValueException; production imports only Java standard
library and local Simulation types. No Spring/JPA/foreign modules, physical calculation,
controller/API, dictionary/OpenAPI regeneration, dependencies, POM, workflows, existing
models/entities or ArchitectureGuardrailTest may change. No migration is needed because
these contracts are not persisted or exposed; later capture has a separate schema gate.
Nested validation/private helpers stay within these three production files.

Minimum behavioral validation: valid synthetic steady-state and transient manifests;
required IDs and positive schema version; valid SHA-256 syntax; effective interval and
captured/recorded/state/watermark order; missing/duplicate source kinds; transient horizon
and schedule requirements; rejection of schedule in steady-state; unmodifiable defensive
copy; synthetic origin detection and preserved original version after replacement input.
Use fixed timestamps; never depend on wall-clock time or actual GZ2 values. Test only
metadata guarantees, not hydraulic accuracy. Origin declarations do not bypass trust.

Commands when A is selected:
`./mvnw -B -q -Dtest=SimulationInputManifestTest,ArchitectureGuardrailTest test`,
then `./mvnw -B -q clean verify`; also canonical documentation/OpenAPI/P1 evidence
validators and `git diff --check`. Require exact-head applicable full CI and report
local toolchain/dependency blocks accurately. Publish with expected-parent lease;
observe CI startup and STOP for owner Next/Fail. Do not implement B..D or the engine.

For B/C/D, first select a supporting exact preflight message
`docs(twin): register HPR-P25-002X execution preflight`, replacing X with B/C/D,
writing these two execution memories only. Later exact files/contracts/migrations are
not authorized by this high-level decomposition. Physical formulation/units/reference
fixtures can be designed with synthetic cases; actual GZ2 data gates field calibration.
HPR-P25-008A numerical design may start after the physical input contract is established;
no solver method is dictated by a manifest. HPR-P25-017 closure includes A..D as well as
008A..F; incomplete inputs cannot be hidden by parent-task metadata completion.

#### HPR-P25-002B Physical-Input Payload Preflight — 2026-10-09

Owner Next selects ONLY `docs(twin): register HPR-P25-002B execution preflight`.
Write ONLY this roadmap and reconciliation. Verified parent
`929073662786da201e051170520013d4d3402ebf`, tree
`67290cdd14ca646819de3e252508e724a6abc0b7`; Documentation #148
(run 37962488460) PASSED. Full optimized #613 remains attached to executable
3e9c19142a750b1aab3ca6fb3afdf5df5906b318. Mandatory instructions/execution memories
read; source tree matches before mutation. No production source is changed here.

##### Source and data-contract findings

SimulationInputManifest and SimulationInputSourceVersion enforce immutable metadata,
source kinds, initial effective-time/capture coherence and mode/schedule references.
They contain no typed pipe geometry, fluid properties, equipment characteristics or
state/timeline values. They cannot prove physical readiness from a digest reference.
Topology's PipelineSegmentJpaEntity exposes endpoints, nullable lengthKm/KP and direction;
TopologyNodeJpaEntity exposes nullable elevationMeters/coordinates. Pipeline nominal
size/design pressure are not internal diameter/approved operating limits. Equipment
identity/attachment is not a measured characteristic curve. No such missing value
may become zero, inferred nominal dimensions or a hardcoded GZ2 parameter.

Simulation owns immutable normalized payloads copied through owner contracts in later
002C. Physical data validity/provenance comes from source versions and actual capture
verification in 002D. Keep Topology truth and existing tables unchanged. Prefer explicit
unit-bearing field names in SI for numerical payloads; retain original units/conversion
provenance in capture. Pressures will be absolute pascals, temperatures kelvin and flows
mass kg/s for the future gas contracts; volumetric readings require explicit reference
conditions/conversion rather than implicit equivalence. These are internal representation
choices, not approval of fluid equations or operating limits.

##### Physical payload stages

Split HPR-P25-002B to preserve bounded implementations. Parent B stays PENDING until
all required payload stages are complete; no stage is executed by this preflight.

| Code | Status | Domain/Module | Type | Exact execution requirement | Exact commit message | Depends on |
|---|---|---|---|---|---|---|
| HPR-P25-002B1 | PENDING | Simulation / Physical network | Code/Test | Implement immutable typed nodes/pipe segments and connected network validation using the exact six-path envelope below. | `feat(simulation): define physical network input contracts` | HPR-P25-002A; successful B preflight documentation CI |
| HPR-P25-002B2 | PENDING | Simulation / Fluid and equipment | Code/Test | Define versioned gas composition/property-method references and current equipment/curve/configuration payloads with explicit units/validity; no property calculation or universal product support is implied. | `feat(simulation): define fluid and equipment input contracts` | HPR-P25-002B1; exact B2 preflight |
| HPR-P25-002B3 | PENDING | Simulation / State and boundaries | Code/Test | Define measured/estimated initial-state quantities and time-dependent boundary/action schedules with quantity basis, time ordering, interpolation declarations and missing-value semantics. | `feat(simulation): define state and boundary timeline inputs` | HPR-P25-002B2; exact B3 preflight |
| HPR-P25-002B4 | PENDING | Simulation / Payload assembly | Code/Test | Bind typed payload/version identities to manifests, validate node/equipment references and steady/transient completeness, preserving synthetic/provenance distinctions; canonical hash verification remains capture scope. | `feat(simulation): assemble consistent physical input payloads` | HPR-P25-002B1..B3; exact B4 preflight |

Gas/property/equipment methods and transient thermal/event fidelity will be specified
in numerical design HPR-P25-008A; payload references must not silently select a method.
Owner-provided SIMONE/SPS and other tool information supplies future benchmark context,
not proof of access, interchangeable models or accuracy. External comparisons require
identical qualified inputs, units and boundary conditions plus independent reference
and measured evidence. H2/liquid/other-product physics requires separate qualification.

##### HPR-P25-002B1 exact next implementation envelope

After successful preflight Documentation CI, the next owner Next selects ONLY B1.
This preflight already registers B1; do not repeat a preflight unless evidence changes.

Create ONLY:

1. `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationNetworkNodeInput.java`
   — immutable Java record: id and required elevationMeters (BigDecimal). Signed/zero
   elevation is valid; missing elevation is not silently converted to sea level.
2. `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPipeSegmentInput.java`
   — immutable Java record: id, fromNodeId, toNodeId, lengthMeters,
   internalDiameterMeters and absoluteRoughnessMeters (BigDecimal). Required positive
   length/diameter; required nonnegative roughness; distinct endpoints and nonblank IDs.
   Endpoint orientation defines a reference direction, not a prohibition of reverse flow.
   No nominal-to-internal diameter inference or hydraulic equation is introduced.
3. `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInput.java`
   — immutable Java record: id, topologyRevisionId, nodes and pipeSegments. Defensive
   copies; at least two nodes and one pipe; unique normalized node/pipe IDs; no null
   entries, dangling endpoints, unused isolated nodes or disconnected components.
   Validate undirected connected scope while preserving each pipe's reference direction.
   Allow cycles and distinct parallel pipes: a meshed network is not a tree.
4. `src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInputTest.java`
   — meaningful synthetic graph/physical-value/immutability tests.

Update ONLY this roadmap and reconciliation. Total six tracked paths; no migration
because records are not persisted/exposed. Reuse InvalidSimulationValueException.
Canonical Java headers retain Author Abir MEDJERAB, CreatedOn 2025-06-26; production
imports only Java standard library and local Simulation types. No changes to existing
manifest/source-version classes, Topology/Custody/Telemetry entities/contracts, schema,
API/OpenAPI/dictionary, dependency/POM/workflow, Kernel/Platform or architecture exports.
Private helpers stay within these three new production records; no utility package.

B1 is a pipe-graph payload, not the complete station/equipment graph or a physical
solver. Compressor/valve links and boundary policies come in later payload stages;
record any required graph schema extension in their preflights. Do not claim all assets
or a disconnected portfolio can be simulated from this connected pipe-only contract.
A geographical selection must include the connected consequence scope or be expanded
by later HPR-P25-007; rejecting a disconnected payload does not select field boundaries.

Minimum tests: valid single pipe, branched/meshed graph and parallel pipes; valid signed
or zero elevation/zero roughness; rejection of blank/null identities, missing numbers,
nonpositive length/diameter, negative roughness, identical endpoints, duplicate IDs,
dangling endpoints, null/empty lists, isolated node/disconnected components; source-list
mutation cannot alter the record and exposed lists are unmodifiable. Replacement revision
preserves earlier physical values. Use explicitly synthetic BigDecimal fixtures and no
current GZ2 values, arbitrary freshness/accuracy bounds or wall-clock dependencies.

When B1 is selected run
`./mvnw -B -q -Dtest=SimulationPhysicalNetworkInputTest,SimulationInputManifestTest,ArchitectureGuardrailTest test`,
then `./mvnw -B -q clean verify`, canonical docs/OpenAPI/P1 validators and whitespace/
exact-scope checks. Report local Java/Maven/dependency limitations accurately. Full
optimized production CI and Documentation CI must start on the published implementation;
stop for owner Next/Fail, do not automatically execute B2 or the numerical engine.

B2/B3/B4 first require exact-file/migration/units/test preflights, messages
`docs(twin): register HPR-P25-002B2 execution preflight`,
`docs(twin): register HPR-P25-002B3 execution preflight`, and
`docs(twin): register HPR-P25-002B4 execution preflight`, two-document scope only.
Parent 002B/002 and phase closure include all four substeps; code or mathematical
choices beyond B1 are not authorized by this envelope. Live GZ2 data remains a
calibration/field-acceptance input; synthetic contract development can proceed.

#### Execution registry

| Code | Status | Domain/Module | Type | Exact execution requirement | Exact commit message | Depends on |
|---|---|---|---|---|---|---|
| HPR-P25-000 | COMPLETED — registration 02e86faa48c49c0c83c2ad51ccc4f1c52686f6c4 passed Documentation #142/run 37948108881; no runtime implementation | Roadmap / Reconciliation | Doc | Register the owner-defined operational twin cycle, ordered tasks, prerequisites, acceptance gates and next requirements preflight. | `docs(roadmap): register phase 2.5 operational digital twin` | P0/P1/P2 closed; owner instruction 2026-10-09 |
| HPR-P25-001 | COMPLETED — requirements/source preflight d65cba71421ed5c994a8f63864754aa6083b7837 passed Documentation #143; owner selected GZ2 gas, both modes, own engine and dynamic versioned inputs; field acceptance remains a later qualification gate | Cross-module requirements | Doc | Inventory exact-source gaps and owners; specify product/use-case coverage, data needs, solver integration options, steady-state/transient scope, operational/security issue classes, tolerances and acceptance fixtures; record unresolved owner decisions and implementation envelopes. | `docs(twin): define requirements and execution preflight` | HPR-P25-000; successful registration documentation CI |
| HPR-P25-002 | PENDING — input-contract preflight delivered; implementation split into HPR-P25-002A..D, all PENDING | Topology / Custody / Simulation | Code/Test | Extend only demonstrated gaps in product-aware connected topology, fluid-property/composition references, equipment characteristics and versioned operating configurations; reject unsupported physics/product combinations. | `feat(topology): establish product aware twin network inputs` | HPR-P25-001 approved input/ownership contracts |
| HPR-P25-003 | PENDING | Topology / Telemetry / API | Code/Test | Provide authorized network map/state APIs with product and asset layers, connectivity, measured/estimated overlays, quality/freshness and issue location; verify the frontend integration contract. | `feat(topology): expose operational twin visualization` | HPR-P25-002 |
| HPR-P25-004 | PENDING | Integration / Telemetry | Code/Test/Config | Implement the approved read-only acquisition adapter and coherent timestamp/unit/asset binding; verify replay, duplicates, late/missing readings, disconnect and recovery while retaining raw provenance and trust rules. | `feat(telemetry): synchronize trusted network measurements` | HPR-P25-001 approved source/access/latency contract; HPR-P25-002 |
| HPR-P25-005 | PENDING | Analytics / Topology / Telemetry | Code/Test | Reconstruct a versioned time-coherent network state using approved physical methods; distinguish measured, estimated and unknown values and observability; retain input/configuration lineage and uncertainty. | `feat(analytics): reconstruct operational network state` | HPR-P25-004; approved estimator/model contract |
| HPR-P25-006 | PENDING | Monitoring / LeakDetection / Risk / Analytics | Code/Test | Implement the approved issue catalogue and correlated detection across the connected network; distinguish sensor/data faults, suspected process faults and safety/security signals, with severity, confidence, supported coverage and evidence. | `feat(monitoring): detect network issues from trusted state` | HPR-P25-005; approved detector thresholds/fixtures |
| HPR-P25-007 | PENDING | Simulation / Topology | Code/Test | Select connected local/regional/full-network simulation scope; derive validated boundary conditions, propagate potential effects and expand scope or reject execution when boundaries are inadequate. | `feat(simulation): resolve connected scope and boundary conditions` | HPR-P25-005; HPR-P25-002 |
| HPR-P25-008 | PENDING | Simulation / Integration | Code/Test/Config | After HPR-P25-008A..F, establish governed execution of Hidra-owned gas engine for both modes with immutable inputs, product/mode checks, diagnostics, failure handling and real execution evidence. | `feat(simulation): execute approved hydraulic solver` | HPR-P25-008A..F; HPR-P25-007 |
| HPR-P25-009 | PENDING | Simulation / Analytics | Test/Doc | Calibrate and validate solver/estimator behavior against approved measured/reference cases and current equipment curves; publish per-product/mode tolerances, residuals, uncertainty and validity envelope. | `test(simulation): validate calibrated network physics` | HPR-P25-008; HPR-P25-005; approved validation data |
| HPR-P25-010 | PENDING | Monitoring / Simulation / Workflow | Code/Test | Automatically trigger governed real simulations from eligible issues using traceable state snapshots, debounce/deduplication, priorities and bounded resource budgets; retain failures and preserve independent alarm handling. | `feat(simulation): orchestrate issue triggered network runs` | HPR-P25-006..009 |
| HPR-P25-011 | PENDING | Simulation / Risk / Analytics | Code/Test | Evaluate candidate operator actions against baseline and affected-network constraints; present expected benefits, adverse effects elsewhere, uncertainty and alternatives; reject infeasible/out-of-validity recommendations. | `feat(simulation): propose network validated operator decisions` | HPR-P25-009..010; approved constraints and candidate action contract |
| HPR-P25-012 | PENDING | Workflow / Audit / Simulation | Code/Test | Record authenticated human approval, rejection/modification and the action actually applied with time/scope/context; preserve advisory boundaries and immutable links to the recommendation and runs. | `feat(workflow): record operator decisions and applied actions` | HPR-P25-011 |
| HPR-P25-013 | PENDING | Analytics / Telemetry / Audit | Code/Test | Compare post-action telemetry with predicted outcomes in an approved observation window; retain actual action, confounders, confidence and attribution limits; detect unexpected effects elsewhere. | `feat(analytics): evaluate observed decision outcomes` | HPR-P25-012; HPR-P25-004 |
| HPR-P25-014 | PENDING | Analytics / Simulation | Code/Test/Doc | Build provenance-controlled learning datasets from actual actions/outcomes; evaluate proposed detector, calibration or recommendation updates offline, version them and require validated promotion/rollback without weakening physical or safety constraints. | `feat(analytics): govern learning from operational outcomes` | HPR-P25-013; approved learning objective/evaluation policy |
| HPR-P25-015 | PENDING | API / Operator Experience | Test/Doc | Demonstrate operator visualization, issue-to-run explanation, candidate comparisons, decision capture and outcome views with the selected frontend; record separate frontend repository/version evidence and accessibility/authorization checks. | `test(twin): verify operator visualization and decision workflow` | HPR-P25-003; HPR-P25-010..014; selected frontend environment |
| HPR-P25-016 | PENDING | Cross-module verification / Operations | Test/Doc | Execute end-to-end historical replay and governed pilot cases including adverse downstream effects, scope expansion, unavailable solver, poor/stale data, unobservable state, nonconvergence and learning rollback; retain exact-source/environment evidence and runbooks. | `test(twin): verify operational decision feedback cycle` | HPR-P25-015; approved pilot/data/environment |
| HPR-P25-017 | PENDING | Governance | Doc | Independently audit all P2.5 gates against actual execution and per-product/mode evidence; close only after exact-head applicable CI and operator acceptance, with unsupported coverage explicitly retained. | `docs(roadmap): close phase 2.5 operational twin verification` | HPR-P25-001..016; HPR-P25-002A..D; HPR-P25-002B1..B4; HPR-P25-008A..F |

#### Boundaries, acceptance gates and unresolved decisions

- **Product coverage:** gas, oil, H2, blends and other configured products are target
  visualization coverage. Validate hydraulic capability separately for each fluid,
  composition and simulation mode; do not claim universal solver support from a
  product catalogue. HPR-P25-001 must choose the first demonstrator and register the
  remaining rollout matrix; phase closure cannot silently omit requested products.
- **State and detection:** define measurable freshness, time alignment, sensor quality,
  topology completeness, estimation/observability and uncertainty limits from approved
  requirements. Use a versioned issue catalogue with positive/negative replay cases,
  false-alarm/missed-detection evidence and an explicit unsupported-issue boundary.
  The ambition to detect all issue kinds is not a current universal detection claim.
- **Physical fidelity:** steady-state versus transient modes, solver(s), licenses,
  integration interface and property methods remain requirements decisions. Pressure
  surges, shutdown evolution, linepack and other time-dependent claims require verified
  appropriate transient execution. Use actual/current performance curves and validate
  calibration with independent reference cases; record invalid/nonconvergent results.
- **Scope and decisions:** every run must retain topology/configuration version,
  measurement watermark, input snapshot, boundary conditions, product properties,
  solver/version, result status and validity. Demonstrate a case where an apparently
  beneficial local action is rejected or modified because it harms another location.
  Evaluate the full connected consequence scope when a local boundary is insufficient.
- **Safety and security:** distinguish process safety events from cybersecurity evidence.
  Trusted security-source integration and an approved response playbook are prerequisites
  for cyber claims. Preserve existing alerts, emergency procedures, SIS and OT separation;
  simulation must not delay urgent protection or treat suspect telemetry as trustworthy.
  Decision proposals remain human-facing; this phase does not authorize direct field
  actuation or bypass SimulationSafetyGuard, Analytics advisory-only or module ownership.
- **Learning:** recommendations alone are not outcome labels. Track the real action,
  timing, other simultaneous changes, observed effects and attribution limits. Validate
  updates offline before governed promotion; support rollback and drift monitoring.
  Physical validity and approved safety constraints remain independently enforced.
- **Operator delivery:** HidraAPI provides backend contracts. An API/map adapter is not
  proof of a delivered frontend; HPR-P25-015 needs explicit integrated frontend evidence.
- **Closure:** require per-task source/tests and exact-head applicable CI, actual solver
  evidence, approved product/mode coverage, operator acceptance, failure/recovery behavior,
  security boundaries and repeatable operational procedures. Mocks alone cannot establish
  field synchronization, hydraulic accuracy, a live twin or AI learning effectiveness.

#### Bounded task selection and write authorization

HPR-P25-000 writes ONLY `doc/roadmap/ULTIMATE_ROADMAP.md` and
`doc/model-remediation/RECONCILIATION.md`. It registers the plan and preserves all
P0/P1/P2 closures, 57 completed HMR implementations and 123 semantic subjects.
HPR-P25-001 is the next recommended task, not executed by this registration.
When selected, it writes ONLY these same two documents and records its source gap
matrix, owner decisions, acceptance fixtures and implementation envelopes in the
roadmap. No source, schema, runtime configuration or dependency change is admitted.

Before each HPR-P25-002..017 mutation, a documentation-only preflight must register
exact file/migration allowlists, owner contracts, product/mode scope, validation
commands and evidence prerequisites in both execution memories. Use supporting
message `docs(twin): register HPR-P25-NNN execution preflight` with NNN replaced by
the selected task number; implementation retains the exact registered message above.
Split a materially larger task into registered bounded stages before implementation.
No cross-module persistence access, new module or dependency is implied by this plan.
Absent required solver/data/access/owner evidence, mark the affected task BLOCKED.
Execute one selected task; observe applicable CI startup and STOP for owner Next/Fail.

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

### Governed Release Preparation — separate from P3

The owner's "Do next" on 2026-10-09 selects registration and preparation of the
bounded 0.6.0 release-alignment candidate described by the preceding release
preflight. This is explicit selection of release preparation under AGENTS.md §19;
P2 closure alone did not authorize it. No tag, GitHub Release, production rollout,
data import or next development-version transition is selected.

| Code | Status | Domain/Module | Type | Exact execution requirement | Exact commit message | Depends on |
|---|---|---|---|---|---|---|
| HPR-REL-001 | COMPLETED — release alignment 09bf1cbf82c20f19c50ebb1ff9e27047c7f34856 verified by Documentation #138 and full HidraAPI CI #610; v0.6.0 tag and GitHub Release published by owner on 2026-10-09 | Release Management | Build/Doc | Align the project version to 0.6.0 and record the completed platform milestone; publish only the five-path release-alignment scope below; require successful exact-SHA documentation and full production CI before release readiness. | `chore(release): prepare 0.6.0 platform milestone` | P0/P1/P2 closed; verified release preflight |
| HPR-REL-002 | COMPLETED — 0.7.0-SNAPSHOT transition ce8867c252fb537d9cca652f9b397a6d35bbc96a verified by Documentation #140 and full HidraAPI CI #611; published v0.6.0 target preserved | Release Management | Build/Doc | Record real v0.6.0 publication and immutable target; advance only the Maven project version to 0.7.0-SNAPSHOT; refresh release/development traceability within the five-path scope below; verify both exact-head CI workflows. | `chore(release): start 0.7.0 development` | HPR-REL-001; v0.6.0 tag/release exist |

HPR-REL-001 write allowlist: pom.xml, VERSIONS.md, PROJECT_STATE.md,
doc/roadmap/ULTIMATE_ROADMAP.md and doc/model-remediation/RECONCILIATION.md.
No source, tests, migrations, dependencies, workflows, generated contract,
dictionary, owner policies, operations scripts or legacy evidence may change.
The exact-HMR identities and all P0/P1/P2/P3 registry rows remain unchanged.

HPR-REL-002 write allowlist: pom.xml, VERSIONS.md, PROJECT_STATE.md,
doc/roadmap/ULTIMATE_ROADMAP.md and doc/model-remediation/RECONCILIATION.md.
The owner's Next on 2026-10-09 selects this post-release transition after the
actual v0.6.0 tag and latest non-draft, non-prerelease GitHub Release were checked.
Release refs/assets are preserved. No source/test/schema/dependency/workflow/API,
production promotion, physical exercise, import or P3 work is admitted.

### Owner-selected Production CI Optimization — 2026-10-09

The owner explicitly pauses Phase 2.5 advancement and selects production CI optimization
because completed runs take too long. This is a separate bounded maintenance task,
not HPR-P25-002B or a deferred industrial capability. Verified parent main
`eef35dbd35d4558a4ceb57f87d8670fb97c933ea`, tree
`7951dd38b554f6260575bd942e8b07d726c53d02`; full CI #612/run 37958649053 and
Documentation #146/run 37958648746 both PASSED. HPR-P25-002A is COMPLETED at
that exact implementation; no individual JUnit/skip report was inspected here.

| Code | Status | Domain/Module | Type | Exact execution requirement | Exact commit message | Depends on |
|---|---|---|---|---|---|---|
| HPR-CI-001 | COMPLETED — optimized full CI #613 and Documentation #147 PASSED at 3e9c19142a750b1aab3ca6fb3afdf5df5906b318; job 726s to 395s, Maven 592s to 281s; log summary 1,277 tests / zero failures, errors or skips | CI / Verification | Infra | Run the unchanged full test suite in two reused isolated JVM forks; retain Surefire reports and count summaries for performance/coverage comparison; preserve all production/API/database/operations gates and verify exact-head CI. | `perf(ci): parallelize isolated test JVM execution` | owner instruction; full #612 green |

Exact write allowlist: `.github/workflows/ci.yml`,
`doc/roadmap/ULTIMATE_ROADMAP.md`, `doc/model-remediation/RECONCILIATION.md`.
No POM, source/test logic, migrations, dependencies, release workflow, runner type,
service image, canonical schema/API or product stage changes are admitted.

Measured GitHub job/step evidence for #612 (job 113915537522): 726 seconds total,
Repository verification 592s, current OpenAPI launch 31s, base OpenAPI generation
51s, service setup 23s, dictionary capture 10s. Maven alone is about 82 percent of
the job. Existing Maven cache is already enabled; caching configuration alone does
not address that observed bottleneck. Testcontainers logs confirm per-class database
containers; source search found no fixed shared PostgreSQL port, test filesystem
writes, ProcessBuilder or global System.setProperty/System.clearProperty calls.
No test-method/thread parallelism is enabled; two forks isolate JVM/static/Spring
state and individual Testcontainers databases, while each fork runs classes normally.
This inspection reduces interference risk but is not a guarantee; exact CI is required.

Retain `clean verify` and all tests. Append only `-DforkCount=2 -DreuseForks=true`
to the current-head Maven command. Do not skip database/integration/architecture tests,
reuse mutable database containers between classes or change historical base selection.
Add always-run Surefire summary/artifact steps showing tests/failures/errors/skips and
XML report count, plus per-class timing evidence. Missing reports are reported rather
than overriding an earlier build failure. Failures in existing steps remain failures.
No reduction in checks or fixed speedup is claimed. Two forks increase concurrent
CPU/memory/container demand; evaluate actual #613-or-later evidence before closure.
Revert fork count to one within this bounded task if resource/interference evidence
requires it; do not silently reduce coverage to achieve a timing target.

Next selection after successful optimization CI: compare wall-clock/Maven timing and
report totals, close HPR-CI-001 only with actual evidence, then resume the recommended
HPR-P25-002B physical-input preflight on owner Next. Do not auto-execute product work.
Observe both applicable workflows starting and STOP for owner Next/Fail.

## 6. Immediate Next Execution

HPR-REL-002 is COMPLETED. Main's 0.7.0-SNAPSHOT transition is verified at
`ce8867c252fb537d9cca652f9b397a6d35bbc96a`, tree
`a5a5dd39214d7f5cfdcee96af878c437103211a5`. Documentation #140 (37941995197)
and full HidraAPI CI #611 (37941995284) both completed SUCCESS on that exact SHA.
Release v0.6.0 is published as the latest normal release, ID 407972853, at
2026-10-09T14:02:18Z. Its lightweight/unsigned tag resolves directly to
`09bf1cbf82c20f19c50ebb1ff9e27047c7f34856`, verified by Documentation #138 and
production CI #610. Preserve that target; a development commit is not a release
tag target. Release publication does not establish production deployment.

Full transition CI #611 passed Java 21 clean verify, OpenAPI equality/compatibility,
current migrated dictionary comparison and retained P1 evidence/operations gates.
Release CI #610 remains attached to the immutable formal release SHA; #611
separately verifies the development transition. The v0.6.0 release-management
cycle and next-development transition are complete under AGENTS.md §19.

Owner instruction on 2026-10-09 selects HPR-P25-000, the documentation-only
registration of Phase 2.5, with exact message
`docs(roadmap): register phase 2.5 operational digital twin`. Current main before
registration is `275a5d38e888ad70e33db4081ef03783e9e1e427`, tree
`fd8dff20c50a0193adbbc321c1a360925ded81b1`; Documentation #141
(run 37944111978) PASSED. Source/runtime remains on the verified 0.7.0 development
line; documentation registration is not solver execution or twin delivery.

HPR-P25-001 requirements/source review is COMPLETED as a preflight deliverable;
Documentation #143/run 37953635173 PASSED on d65cba71421ed5c994a8f63864754aa6083b7837.
The owner selected gas, GZ2, both steady-state/transient modes and a Hidra-owned engine,
then clarified that network/model/operating data changes and must be versioned input.
Owner Next selects ONLY `docs(twin): record approved phase 2.5 input decisions`,
writing the roadmap and reconciliation; this decision update implements no engine.

Owner Next selects HPR-P25-002 documentation-only input-contract preflight, exact
message `docs(twin): register HPR-P25-002 execution preflight`. Documentation #144
(run 37955963870) PASSED on parent 61c1311f458e6844d28c4b4d162b0916885d01a0,
tree 774e1806a8953b5582dcb32081256449d5d290de. Current source remains unchanged.

HPR-CI-001 is COMPLETED: full CI #613/run 37961219860 and Documentation
#147/run 37961220149 PASSED at exact optimization commit
3e9c19142a750b1aab3ca6fb3afdf5df5906b318, tree
664d21d3be2fc3b867032abc7170074ac61adf27. Job elapsed fell from 726s to 395s;
Maven verification fell from 592s to 281s. The optimized-run log summary reports
324 Surefire XML files, 1,277 tests, zero failures/errors/skips, with all existing
OpenAPI/database/P1/operations gates successful. This is a measured single-run
comparison, not a guaranteed duration or an independent archive-level audit.

Owner Next selects ONLY the two-document verification record, exact message
`docs(ci): record verified production CI optimization`. Workflow remains unchanged.
Owner Next now selects ONLY HPR-P25-002B physical-input payload preflight,
exact message `docs(twin): register HPR-P25-002B execution preflight`. Parent
929073662786da201e051170520013d4d3402ebf passed Documentation #148/run 37962488460.
The six-path B1 implementation envelope is registered above; no source changes here.
Next recommended task after successful preflight Documentation CI:
**HPR-P25-002B1 — physical network input contracts**. Execute only B1, no repeat
preflight unless evidence changes. Observe Documentation startup and STOP.
P0/P1/P2 remain CLOSED; P2.5 PLANNED/OPEN; P3 DEFERRED; 57 HMR completions,
123 subjects, formal v0.6.0 and current 0.7.0-SNAPSHOT remain preserved.

### Retained P2 closure disposition

HPR-P2-013 is COMPLETED; Phase P2 is VERIFIED/CLOSED following renewed 12/12
substantive audit and successful exact-head Documentation Validation #136
(run 37932486353) and full production CI #609 (run 37933120029) on
`a8905e32289a583f47b831e0381783e556ae0c8d`. The documentation-only
closure evidence record does not create a new executable baseline.

All HPR-P2-001..013 are COMPLETED. All P3 tasks remain DEFERRED; no P3
execution is automatically selected. Any future P3 work requires a separate
owner-approved requirement and task selection. Formal v0.6.0 release preparation
is a distinct governed workflow under AGENTS.md §19, not authorized by this
P2 verification step. The project remains `0.6.0-SNAPSHOT`; no release,
tag, version, deployment or data-import action occurs here.

Historical preflight, reopened 9/12 audit, pending-CI and earlier closure
statements below retain their dated evidence scope and are superseded by this
final exact-head result. P0/P1 status and their physical applicability remain
unchanged. Preserve 57 HMR completions, 123 semantic subjects and explicit
limitations on business approval, runtime inference, deployed-data acceptance,
OT coverage and future industrial extensions.

### HPR-P2-013 renewed audit and closure metadata implementation — 2026-10-09

Verified parent/audit SHA: `7be1c9cb47ed9b6f73a7692d0a328ad870e2b4d4`, tree
`143314774f1c95eb1393c4a9b2a1737cddd14f65`. Default branch main remained unchanged.
[Documentation #135](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/37931016005)
PASSED at that SHA. AGENTS.md, this roadmap and RECONCILIATION.md were read before
mutation. The owner's separate Next selects metadata implementation after the audit;
this is not an automatic transition from a preflight or a claim of future CI success.

The read-only forensic audit returned PASS, with exactly twelve VERIFIED classifications:

| Check | Requirement / inspected evidence | Observed content and assessment | Status |
|---|---|---|---|
| 1 | Exact legacy supersession notices: docs/data definition/Agents.md, docs/roadmap/model-semantic-remediation.md, docs/ARCHITECTURE.md | All three lines 1–2 contain exact WARNING: SUPERSEDED DOCUMENT and canonical roadmap target; retained legacy statuses do not select current execution | VERIFIED |
| 2 | doc/README.md Authority; DOCUMENTATION_STANDARD Required Metadata/Evidence Rules; DOCUMENT_STATUS_MODEL; DOCUMENT_LIFECYCLE | Canonical/legacy authority, CURRENT/TARGET/HISTORICAL meaning, evidence, ownership, review applicability and preservation/transition rules are substantive | VERIFIED |
| 3 | UBIQUITOUS_LANGUAGE; TOPOLOGY_TELEMETRY; ALARM_INCIDENT_LEAK; linked subject register | Hidra-specific ownership, raw/trusted distinction, graph/self-loop, lifecycle/leak rules and prohibited shortcuts; corroborated source, not generic industry definitions | VERIFIED |
| 4 | Every doc/modules/*.md and actual module source roots | 25 Markdown files: README plus exactly 24 substantive slices, with responsibility/domain/API/persistence/boundary/limits/semantic sections and resolving source links; source roots equal slices | VERIFIED |
| 5 | Legacy HMR-005/031/080 obligations compared with SEMANTIC_DECISIONS and telemetry/topology/planning slices | Compatibility metadata, catalog connections/self-loop rejection and actual Nomination owner contracts/mappings transferred with current source/migration/test evidence; 123 subjects and 57 completed identities retained | VERIFIED |
| 6 | SIMULATION_ANALYTICS_AI; SimulationSafetyGuard/SolverPort; AnalyticsInsight; DigitalTwinReadinessAssessment | Port has no executing implementation in current production source; advisoryOnly true/runtimeDigitalTwin false; actuation/foreign writes rejected; metadata is not ML/solver execution | VERIFIED |
| 7 | Git-tracked doc/api/openapi.yaml and OPENAPI_GOVERNANCE | Generated versioned OpenAPI 3.1.0: 244 paths/263 operations/231 schemas; generation identity retained independently from publication | VERIFIED |
| 8 | ci.yml Enforce canonical OpenAPI snapshot equality; validate_openapi_snapshot.compare_generated | Fresh generated object must match committed object, ignoring only validated x-hidra-ci-source-sha; mismatch raises error; compatibility is a separate gate | VERIFIED |
| 9 | API_CONVENTIONS/ERROR_MODEL versus Workbench/telemetry adapter/context filter/global and scoped advice | Actual distinct pagination/filter/sort/tracing/body/status behavior documented, including IllegalArgument not-found to 400 and scoped conflicts; universal generated error envelope explicitly unestablished | VERIFIED |
| 10 | DATA_DICTIONARY, generator, source-linked ownership and catalog artifact | Reproducible 481 tables + one sequence, 5,840 catalog columns, 1,355 constraints, 3,030 indexes, 155 triggers; 139 migrations/470 JPA reconcile; TelemetryPoint/TopologyConnection/Nomination and exceptions checked; zero unresolved owners | VERIFIED |
| 11 | Actual SCHEMA_OWNERSHIP and FLYWAY_POLICY (not prompt's alternate filename) | Prefix exceptions/non-JPA owners, same/cross-module reference governance, unique version allocation, immutable history, additive/destructive/data preflight, recovery/reversal/forward fix/repair rules are explicit; no invented approved values | VERIFIED |
| 12 | REQUIREMENTS_TRACEABILITY platform/module/HMR matrices and representative actual tests/contracts/migrations | All 209 source links resolve; inspected global 404/403 and Planning 409 tests, semantic/architecture/migration evidence; existing tests, cumulative CI and uninspected per-class reports remain distinct | VERIFIED |

Exact module slices: alarm, analytics, assets, audit, configuration, custody, documents,
hse, identity, incident, integration, integrity, leakdetection, monitoring, notification,
organization, party, planning, reporting, risk, simulation, telemetry, topology, workflow.
No non-module metadata or deferred agents/environment/otsecurity root is counted.
Architecture source inventory is 70 contract Java files in 63 exported packages;
neutral target/evidence extensions are not invented consumer modules. Additional live
P2 data governance requirements were checked through current ownership/provenance/
retention/legacy admission documents: infrastructure policies retain their own scope,
unknown business values and proposed admission remain explicit NOT ESTABLISHED/TARGET.

No unresolved Phase P2 governance or contract defects were identified at the pinned
audit SHA. This substantive PASS is separate from closure publication and its future
CI results. Earlier #127/#606 and 9/12 audit results retain historical scope.
HPR-P2-005 #131 and HPR-P2-006 #134/#608 now substantively correct Checks 9–11;
#608 fresh physical comparison reports reviewed_dictionary_checked=true and zero
unresolved owners. Its executable/migration/JPA applicability is unchanged here.

Actual implementation changes thirteen existing metadata paths within the fifteen-path
maximum: doc/README; governance DOCUMENT_REGISTER/DOCUMENTATION_VALIDATION; database
README/DATABASE_ARCHITECTURE/SCHEMA_OWNERSHIP/FLYWAY_POLICY; API/domain/modules/architecture
README; this roadmap and RECONCILIATION. Architecture inventory files need no change.
No file is created. DATA_DICTIONARY and ownership JSON, API object/manifest/scripts/
workflows, individual modules/catalogue, docs/**, source/tests/resources/SQL/POM, ops,
P1 physical and original generation evidence remain excluded and unchanged.

Audit validation actually passed: all 37 tooling fixtures; canonical validation
(95 documents, 5,050 links, 24 modules, 13 primary P2 rows); offline OpenAPI; physical
dictionary render/check from reviewed Stage A capture with current identical source
bundle and zero unresolved owners; module/source equality; 123 subjects/57 completion
identities; 598 semantic source links and 209 requirements source links. This metadata
publication checks PASSED: all 37 fixtures; canonical validation (95 documents,
5,062 links, 24 modules, 13 primary P2 rows); offline OpenAPI; current dictionary
comparison with zero unresolved owners; exact thirteen-path/excluded tracked blobs,
57 HMR identities/123 subjects/version/P3 preservation and git diff --check.
No local Java/database campaign is claimed.

Publish one exact registered commit with expected-head lease; verify parent/tree/
thirteen files and documentation CI startup. Existing workflow_dispatch must start
full Production CI on this exact closure head. Do not mark HPR-P2-013 COMPLETED or
P2 CLOSED until both exact-head runs pass; final dictionary/OpenAPI gates are mandatory.
Stop for notification after startup. No P3, release/tag/version, deployment/import,
policy approval or new physical campaign executes.

### HPR-P2-006 verification and renewed HPR-P2-013 preflight — 2026-10-09

Verified main: `1bc3c1bba2d08a0b493e5ece43e834e8d56d04f0`, tree
`82e183f0bd671b3ac8ae384bfafd2fadf8522bda`, unchanged when checked.
[Documentation #134](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/37929244701)
and [Production #608](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/37929244240)
completed SUCCESS at that exact SHA. Job 113815603963 succeeded through Maven
verification, current OpenAPI equality, schema capture/final comparison, artifact
retention and base compatibility. The actual collection summary reports
`reviewed_dictionary_checked: true`, 139 migrations, 470 JPA tables, 482 relations,
zero unresolved owners and unchanged source bundle
`86f6ba5923e63c0cd601f7722ca0282e486d59fc92bb120616710fb3cd1799d4`.

Retained Stage B schema artifact ID 11615823318, name
`hidra-database-schema-1bc3c1bba2d08a0b493e5ece43e834e8d56d04f0`, GitHub archive digest
`706b903af40c1d33faea828287a4eab139bb6f413bb8176ea9d5872e7774564a`.
CI summary catalog-document digest `cde91b616f951bd465db0844a69e4472a3eebb45dcff9559e5380d21ace15ac4`;
generated dictionary digest `53e2848cc822e001e21abed5b74858e6557a4c2c2a8a673610ac7f9f05e8b6dc`.
These differ from the Stage A capture because the validated capture source SHA is
part of each generated object; final comparison ignores only its documented Markdown
SHA line. No Stage B archive download/hash recomputation is claimed by this review.
Stage A archive/content was directly inspected before Stage B, and #608 now proves
fresh integrated equality against reviewed committed content. HPR-P2-006 is COMPLETED.

#### Renewed bounded execution

This preflight supersedes historical closure scopes that proposed fixing inventory-only
database/architecture content. Those corrections now exist; do not regenerate or
rewrite the physical dictionary as a status change. Current architecture lists 70
contract files/63 packages, and 24 module slices exist plus their README. These are
preflight inventory observations, not a renewed twelve-check verdict.

1. After preflight documentation CI succeeds and the owner's Next, run ONLY the
   read-only P2 audit against one newly pinned default-branch SHA. Use the existing
   Final Audit P2 benchmark/report structure and the live roadmap as authority.
   Re-read canonical governance, exact three legacy warnings, module contents,
   ubiquitous language, legacy-to-canonical semantic decisions, simulation/analytics/
   AI boundaries, committed OpenAPI, generation/drift gates, API/error conventions,
   physical dictionary/ownership/Flyway policy and requirements traceability.
2. Produce twelve separate classifications with exact paths/sections/source evidence;
   map additional live P2 requirements to the closest check. Check 11 uses the actual
   canonical FLYWAY_POLICY.md rather than inventing the prompt's alternate filename.
   Verify content, not status markers. Keep artifact/CI/source applicability separate.
   Preserve P1 historical applicability without auditing P1 or claiming production
   data approvals, hydraulic/ML execution, or deferred capability implementation.
3. If any check fails, return FAIL plus responsible-scope defects and stop before
   closure. Publish no closure on partial verification. A new defect needing excluded
   files requires a concrete amendment and separate remediation selection.
4. If all twelve are VERIFIED, report PASS at the pinned audit SHA and a concrete
   current-metadata closure patch for separate owner selection. Reconcile stale
   publication-pending/CLOSED summaries through dated supersession; preserve old
   records and generation provenance. Do not infer that a past 12/12 or CI run proves
   the new audit. Do not create or alter runtime or physical evidence.
5. Future closure uses exactly `docs(roadmap): close P2 canonical governance` and
   stays IN PROGRESS/P2 OPEN pending both exact-head closure workflows. Docs-only
   push starts documentation CI but is ignored by production CI; use existing
   workflow_dispatch at the exact closure head. No dispatch connector is exposed
   currently: owner Actions dispatch is required unless a supported authorized
   capability becomes available. Never alter triggers or add dummy executable edits.
   Observe startup and stop for notification; final CLOSED requires both results.

#### Future closure metadata allowlist — fifteen existing paths

Only these paths may receive necessary current status/provenance updates after the
read-only audit and separate closure selection:

- `doc/README.md`
- `doc/governance/DOCUMENT_REGISTER.md`
- `doc/governance/DOCUMENTATION_VALIDATION.md`
- `doc/database/README.md`
- `doc/database/DATABASE_ARCHITECTURE.md`
- `doc/database/SCHEMA_OWNERSHIP.md`
- `doc/database/FLYWAY_POLICY.md`
- `doc/api/README.md`
- `doc/domain/README.md`
- `doc/modules/README.md`
- `doc/architecture/README.md`
- `doc/architecture/CROSS_MODULE_CONTRACTS.md`
- `doc/architecture/BOUNDED_CONTEXT_MAP.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`

This is an exhaustive maximum allowlist, not an instruction to edit every file.
Create no repository file. DATA_DICTIONARY.md and its ownership JSON remain exact
reviewed generation inputs/content; all scripts/workflows/manifest/API snapshot,
individual module docs/permanent catalogue, docs/**, source/tests/resources/SQL/POM,
ops and historical physical evidence are excluded. Preserve every 57-HMR identity,
123 subjects and 0.6.0-SNAPSHOT. No release/tag, version, deployment, import, business
approval, new physical campaign or P3 work is admitted.

#### This supporting publication

Exact message: `docs(roadmap): revise P2 canonical governance closure preflight`.
Only this roadmap and RECONCILIATION.md change now; record HPR-P2-006 verified completion
and renewed preflight disposition. HPR-P2-013 remains PENDING; P2 OPEN/P3 DEFERRED.
Validate canonical links/status, offline OpenAPI, two-path/excluded-blob scope,
57-row/123-subject/version preservation and diff checks. Publish against fresh expected
main; verify immutable parent/tree/files and documentation CI startup, then STOP.
No full production dispatch is needed for this planning commit; it changes no executable
or dictionary inputs and does not claim a new physical comparison.

### HPR-P2-006 Stage B implementation — 2026-10-09

Verified main parent: `8b51b52b2aa31f7a2f0ca2b08a6066387663d092`, tree
`7df0095e2ad73bbb41b143cf8b8906abd7f15bf0`. Documentation #133/run 37927031188
and full Production #607/run 37927031269 PASSED at that exact source. AGENTS.md,
this roadmap and RECONCILIATION.md were read before changes. The owner separately
selected Stage B after the read-only Stage A review.

Reviewed artifact ID 11614189981, name `hidra-database-schema-8b51b52b2aa31f7a2f0ca2b08a6066387663d092`.
Downloaded ZIP SHA-256 matched GitHub:
`1ba744af02d7bcdd6ffd72c5ff66dc231dbda9d40c03bf51441ca0b2ae8cde72`.
All three members were inspected; offline draft regeneration matched byte-for-byte.
Catalog document SHA-256 `ba01997dbdb999d7bd93db4a2358b94fc3ac91d1df0fc301dc3bc365962bc0cb`;
migration/JPA source bundle `86f6ba5923e63c0cd601f7722ca0282e486d59fc92bb120616710fb3cd1799d4`.
Server PostgreSQL 16.15 Debian, psql 16.15 Ubuntu, generator format 1. This proves the
full disposable CI source chain, not current production deployment/data or PG18 schema.

Exactly eight allowed paths change: DATA_DICTIONARY.md, SCHEMA_OWNERSHIP.md,
FLYWAY_POLICY.md, database README, new .github/database-dictionary-ownership.json,
DOCUMENT_REGISTER.md, this roadmap and RECONCILIATION.md. No scripts/workflows,
application/configuration, tests, applied SQL, POM, ops, OpenAPI, legacy or release files.

The physical dictionary covers 481 tables plus one sequence, 5,840 catalog columns,
1,355 constraints, 3,030 indexes and 155 non-internal triggers. All 139 successful
Flyway identities/checksums and 470 JPA table mappings reconcile. There are 5,781
Java mappings including two aliases, 5,779 unique mapped columns. Three retained
physical columns without Java mappings are explicitly explained by HMR-028/HMR-054
source. All 688 captured FKs resolve within one owner; cross-module ID/snapshot/owner
contracts remain distinct from physical FK evidence. TelemetryPoint, TopologyConnection,
Nomination and organization/assets/leak exceptions were reviewed through the full chain.

Twenty-one source-linked metadata overrides resolve the 12 non-JPA relations and
representative naming/legacy exceptions. Final ownership has zero unresolved entries.
No business classifications, approved unit/product pairs, policy rows or historical
mapping values are invented or provisioned. Ownership and Flyway guides specify actual
naming, owner boundaries, new-reference admission, unique version allocation, immutable
history, additive/destructive/data preflight, backup/recovery/reversal feasibility and
exceptional reviewed repair. Historical generation/P1 deployed scope is preserved in
README/Git provenance; it does not certify the new chain in production.

Local render/check and source/ownership coverage PASSED with zero unresolved owners.
All 17 generator fixtures PASSED. Canonical validation PASSED (95 documents, 5,049
relative links, 24 modules, 13 primary P2 rows); offline OpenAPI PASSED (244 paths,
263 operations, 231 schemas). Exact eight-path scope/excluded tracked blobs and
git diff --check PASSED. Fresh full CI must run `--check` with reviewed ownership at the new SHA;
only the validated capture SHA line is ignored. The new JSON is not doc-only and
triggers both workflows under existing paths. No local DB execution is claimed;
previous local Maven DNS failure remains distinct from successful Stage A full CI.

HPR-P2-006 remains IN PROGRESS until both publication workflows and the integrated
physical comparison pass. HPR-P2-013 PENDING; P2 OPEN/P3 DEFERRED. All 57 HMR
completions including HMR-080, 123 semantic subjects and 0.6.0-SNAPSHOT are preserved.
Publish with the exact expected-head lease; verify parent/tree/eight paths, observe
both workflows start and STOP. This current record supersedes older Stage A pending
and database inventory-only status notes without deleting historical evidence.

### HPR-P2-006 Stage A implementation — 2026-10-09

Verified main parent: `04e1cee9b584c0d834cb885faea3fd0046d0ea5f`, tree
`93c7f126fec15904d7e4df87610e9762b6796dde`. Documentation Validation #132,
[run 37922894396](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/37922894396),
completed SUCCESS. AGENTS.md, this roadmap and RECONCILIATION.md were re-read.

Exactly six admitted paths: new `.github/scripts/generate_data_dictionary.py` and
`test_generate_data_dictionary.py`, existing `.github/workflows/ci.yml` and `docs.yml`,
this roadmap and RECONCILIATION.md. No dictionary/policy/ownership metadata, application,
configuration, migration, POM, ops, legacy, OpenAPI or release/version file changes.

The collector requires the designated local disposable Actions database and matching
checkout/source SHA; uses psql ON_ERROR_STOP plus a read-only repeatable-read snapshot;
and captures metadata only. PostgreSQL catalogs supply physical types/nullability,
keys/FKs/actions/validation/indexes/triggers/functions/views/sequence facts. Flyway
history identities/success/checksums are retained after the existing successful full
Flyway/JPA startup, with migration/JPA source hashes. No SQL regex replay or schema
reset is performed. JPA discovery is mapping evidence, not physical schema authority.
Unsupported persistent forms fail; existing read-only association aliases are retained.

Actual local mapping discovery: 139 migrations, 470 distinct JPA table mappings,
5,781 field/column mappings including read-only aliases; source bundle SHA-256
`86f6ba5923e63c0cd601f7722ca0282e486d59fc92bb120616710fb3cd1799d4`.
These are source counts, not predicted catalog relation/physical column totals.
Unknown non-JPA owners remain UNRESOLVED; Stage A renders DRAFT, never CURRENT.

Actual CLI in production CI, after current runtime/OpenAPI equality and before
base-revision startup:

```bash
python3 .github/scripts/generate_data_dictionary.py \
  --collect target/database/schema.json --source-sha "${{ github.sha }}" \
  --render target/database/DATA_DICTIONARY.md
```

When `.github/database-dictionary-ownership.json` exists, the same step adds
`--ownership .github/database-dictionary-ownership.json --check doc/database/DATA_DICTIONARY.md`.
Comparison fails on generated content drift and ignores only the validated capture
source SHA line; it never edits Git. Stage B has not provided that metadata yet.
Offline review uses `--input <captured-schema.json> --source-sha <exact-capture-sha>`
with --render and, for final comparison, --ownership/--check. Source digest drift fails.

CI explicitly supplies PostgreSQL client availability and reports psql version.
The service remains postgres:16. Retained artifact name:
`hidra-database-schema-${{ github.sha }}`; members are schema.json, DATA_DICTIONARY.md
and verification.json under target/database. Artifact identity/digest and actual
engine/source/coverage must be reviewed after successful full CI; none is claimed now.
Both workflows run generator fixtures; Markdown-only validation stays database-free.

Actual local checks: 17 generator tests PASSED; existing documentation-validator
and OpenAPI-validator suites PASSED (10 tests each). Workflow YAML and capture-before-
base ordering checks PASSED. Source mapping discovery PASSED. Canonical validation
PASSED (95 documents, 3,332 relative links, 24 modules, 13 primary P2 rows); offline
OpenAPI validation PASSED (244 paths, 263 operations, 231 schemas). Exact six-path
scope and git diff --check PASSED; excluded tracked blobs remain unchanged.
No PostgreSQL/Docker tools
exist locally, so no real catalog query was exercised. Actual
`bash ./mvnw -B -q clean verify` FAILED before compilation resolving uncached Spring
Boot parent 4.1.1 because repo.maven.apache.org DNS failed; no Java/runtime success
is claimed. These limits do not waive successful full exact-head production CI.

HPR-P2-006 stays IN PROGRESS pending Stage A CI/artifact review and Stage B correction.
HPR-P2-013 stays PENDING; P2 OPEN/P3 DEFERRED. Preserve all 57 HMR completions, all
123 semantic subjects, prior physical/CI provenance and version 0.6.0-SNAPSHOT.
Publish with expected-head lease, verify immutable parent/tree/files and observe both
CI workflows start, then STOP for notification; do not manufacture catalog output.

### HPR-P2-006 concrete database correction preflight — 2026-10-09

Verified main parent: `4d41f265374156846a86d31f3d30e2b0fe00dd91`, tree
`48a46d8d8b0994886e93c154a489b54177620115`. HPR-P2-005 Documentation Validation
#131, [run 37921916637](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/37921916637),
completed SUCCESS at that exact SHA. Required governance/source files were re-read.
HPR-P2-005 is now COMPLETED; prior dated awaiting-CI statements are superseded.

#### Verified evidence and execution limitation

Current source has 139 versioned SQL migrations and 470 module @Entity classes.
Read-only discovery found 470 distinct explicit @Table names, no duplicate names
and no missing @Table declaration among these candidates. This is a Java mapping
inventory, not a migrated PostgreSQL table count or verified final schema.
Actual prefix exceptions include assets using hidra_asset_*, organization using
hidra_org_*, leakdetection using hidra_leak_detection_*, standalone hidra_alarm and
hidra_incident, and risk's hidra_residual_risk_assessment. Do not assume that every
physical name is hidra_<module>_* or that every SQL-created policy table has a JPA entity.

The current execution environment has Java but no psql, postgres, Docker or Maven
command. No full-chain database was created or introspected by this preflight.
Rather than claim a SQL regex inventory is a physical dictionary, Stage A uses the
existing disposable PostgreSQL CI service and full Flyway-enabled test-profile
application startup. Current .github/workflows/ci.yml uses postgres:16 for that
service; PostgreSQL-18 Testcontainers/production evidence has separate applicability.
An export from CI PostgreSQL 16 is source-chain/schema evidence for that engine,
not a claim of current production deployment or PostgreSQL-18 physical verification.

#### Ordered two-stage HPR-P2-006 execution envelope

The owner-selected task remains HPR-P2-006. Each stage requires a separate user
selection; do not execute or combine both automatically. Stage A is a supporting
technical evidence commit; it does not complete the original documentation code.
Stage B uses the original registered HPR-P2-006 implementation message. Preserve
both exact commit identities and their CI/artifact applicability. No HMR restarts.

| Stage | Exact message | Completion boundary |
|---|---|---|
| A — evidence collection tooling/CI | `ci(database): capture canonical schema evidence` | Generator/meaningful tests and bounded CI integration; observe both workflows and STOP. Require successful full exact-head CI and inspect retained schema evidence before Stage B |
| B — physical dictionary and governance | `docs(database): establish canonical database documentation` | Reviewed dictionary, ownership mappings and policy; verification and publication CI remain distinct. No P2 closure |

#### Stage A exact write allowlist — six paths

| Path | Admission |
|---|---|
| `.github/scripts/generate_data_dictionary.py` | NEW: collect read-only PostgreSQL catalog metadata, validate provenance/history/coverage, and deterministically render/check the dictionary from captured metadata |
| `.github/scripts/test_generate_data_dictionary.py` | NEW: meaningful negative/edge fixtures for partial history/catalog, missing/duplicate relations/mappings, composite FKs/checks/indexes, unsupported JPA forms and deterministic rendering; fixture tests are not physical extraction proof |
| `.github/workflows/ci.yml` | Add offline generator tests and one current-schema capture/artifact step immediately after current full-chain runtime generation and before any base-revision application build/start |
| `.github/workflows/docs.yml` | Add offline generator tests and narrow triggers for the new tooling/ownership metadata; do not require a database for Markdown validation |
| `doc/roadmap/ULTIMATE_ROADMAP.md` | Actual scope/validation/CI/artifact disposition; leave HPR-P2-006 IN PROGRESS and P2 OPEN |
| `doc/model-remediation/RECONCILIATION.md` | Current phase/evidence note only; preserve all HMR rows |

Stage A excludes DATA_DICTIONARY.md/schema-policy edits and ownership approval.
New files under target/database are disposable CI artifacts, not tracked deliverables.
No application source/configuration, migrations, POM, ops, legacy docs, OpenAPI or
release/version changes. No new canonical Markdown inventory entry is introduced.

#### Stage A extraction protocol and acceptance

1. Use the existing fresh CI test database after the built current application has
   successfully run Flyway and JPA validation. Do not replay SQL with a home-grown
   statement splitter, disable validation, seed guessed owner mappings, clean a
   non-disposable database or connect to production. Place collection before base
   compatibility generation so historical application startups cannot contaminate it.
2. Explicitly provide PostgreSQL client availability in CI. Use Python standard
   library plus psql with ON_ERROR_STOP and read-only transactions/queries; consume
   connection credentials from the existing isolated test environment, not command
   logs/artifacts. The collector must enforce the designated local disposable target
   and cannot migrate/reset/delete data. No business table rows are exported.
3. Capture server version, exact source SHA, ordered migration/JPA source digests
   and successful Flyway history identities/checksums/scripts. Fail on missing,
   failed, duplicate or mismatched migration identities; verify the full 139-file
   chain rather than assuming initial migrations define current schema.
4. Enumerate all application relations through PostgreSQL catalogs, excluding
   system namespaces explicitly. Capture actual schemas/tables, ordered columns,
   formatted SQL types, nullability, defaults/generated expressions, PK/unique/check/FK
   definitions, validation flags and FK actions, indexes and relevant non-internal
   triggers/function evidence. Record views/other relation kinds distinctly where
   present. Include technical/history and non-JPA owner-policy tables with disposition;
   missing metadata or an unsupported relation must not silently disappear.
5. Reconcile current JPA @Table/@Column/@JoinColumn and embedded/inherited mappings
   against catalog evidence, using startup Hibernate validation as corroboration.
   SQL catalogs control physical facts; source parsing is mapping evidence, not schema
   authority. Unsupported mappings or differences remain explicit failures/decisions,
   not guessed columns. Include per-table source/migration links and module candidates.
6. Define reproducible CLI modes before publication: --collect for catalog JSON,
   --render for a draft Markdown artifact, --check for comparison when a reviewed
   dictionary is established, and --ownership for the reviewed override metadata.
   Record exact commands/options/tool versions in the stage evidence. Stage A may
   flag ownership UNRESOLVED in drafts; it must not label a draft dictionary CURRENT.
7. Retain deterministic JSON metadata, draft Markdown and verification summary under
   target/database and a source-SHA-named Actions artifact with digest/version details.
   Review exact-head successful CI, artifact identity, migration/source digests and
   multi-module samples before committing dictionary contents. Artifact upload alone
   does not verify extraction completeness or policy ownership.
8. Preserve existing CI gates and service version. Run Python tooling tests, both
   canonical validators and diff/scope checks locally; explicitly report unavailable
   local DB/Maven verification. Tool/workflow changes trigger required full production
   CI plus documentation CI; STOP after startup, awaiting notification.

#### Stage B exact write allowlist — eight paths

| Path | Bounded change |
|---|---|
| `doc/database/DATA_DICTIONARY.md` | Replace inventory-only current content with the verified physical dictionary; retain prior generation provenance/history applicability |
| `doc/database/SCHEMA_OWNERSHIP.md` | Actual table/prefix/exception ownership, same/cross-module reference/FK rules and verified limitations |
| `doc/database/FLYWAY_POLICY.md` | Naming/version allocation, additive/destructive/data preflight, history/repair and validation governance |
| `doc/database/README.md` | Current source/artifact/tool provenance, regeneration commands, ownership and applicability |
| `.github/database-dictionary-ownership.json` | NEW: reviewed source-linked ownership/semantic overrides for non-JPA/technical tables and actual prefix exceptions; no business policy values/rows |
| `doc/governance/DOCUMENT_REGISTER.md` | Current database readiness/applicability and still-open P2 |
| `doc/roadmap/ULTIMATE_ROADMAP.md` | Actual HPR-P2-006 implementation/evidence and CI-pending status; leave HPR-P2-013 PENDING |
| `doc/model-remediation/RECONCILIATION.md` | Phase authority note; preserve every completed semantic identity |

No extra canonical dictionary fragments or script/workflow changes are pre-admitted
for Stage B. If captured evidence requires such scope, publish a concrete amendment
before changing excluded paths. Unknown approvals remain BLOCKED-DECISION/TARGET.

#### Stage B dictionary/policy acceptance and drift verification

1. Derive every current physical column/type/nullability/key/constraint/index from
   the reviewed Stage A catalog artifact. Validate source digests against the new
   pinned head; documentation-only head changes do not authorize an old schema after
   executable/migration/JPA drift. Record capture SHA/server/tool/artifact separately
   from documentation publication SHA. Do not call 470 entities the physical table count.
2. Cover every application table, including non-JPA owner-policy and technical tables;
   document actual owner, semantic purpose, JPA/source links, migration evidence and
   explicit exceptions. Interpret constraint/index/trigger facts from SQL/catalogs,
   not invented industry semantics. Unresolved ownership cannot pass final coverage.
3. Cross-check TelemetryPoint, TopologyConnection and Nomination through the complete
   chain, plus representative organization/assets/leak prefix exceptions and non-JPA
   policies. Report actual coverage totals from extraction, not predicted totals.
4. Distinguish same-module FKs from cross-module IDs/snapshots/FKs actually present.
   Specify proposed admission/owner-review rules as governance requirements; do not
   assert they were universally enforced or historically approved merely because
   tables/FKs exist. Cross-module DB references do not transfer aggregate ownership.
5. Specify actual V<version>__<description>.sql naming and unique version allocation,
   immutable applied migrations/checksums/history, additive evolution, destructive
   change/data preflight, reviewed mappings, backup/recovery/reversal feasibility,
   forward corrective migrations and exceptional reviewed repair. Link existing
   runbook/test safeguards; do not invent rollback, production approval or business
   mapping values. No applied migration or database is changed by this task.
6. Use the Stage A tool to regenerate and compare reviewed Markdown/catalog facts.
   The Stage A workflow must activate final dictionary comparison when ownership
   metadata exists; it must fail on column/key/constraint/index/coverage drift while
   ignoring only documented source-provenance differences. Preserve semantic order
   where significant. Partial/unavailable extraction fails; never auto-update Git.
7. Run documentation/OpenAPI validators, generator tests/render/check, source digest,
   JPA/catalog coverage, exact eight-path/excluded-blob checks and diff checks.
   Require documentation CI and fresh full production CI for the integrated schema
   comparison at the Stage B head; docs-only paths do not auto-trigger production CI,
   so explicitly require workflow_dispatch at that exact branch/head if needed.
8. Leave HPR-P2-006 IN PROGRESS until artifact, substantive content and both exact-head
   gates pass. HPR-P2-013 separately reruns all twelve audit checks; no closure or P3
   execution is inferred from successful extraction, dictionary publication or CI.

This preflight creates no tool, catalog export, dictionary, schema rule, migration,
owner policy value or runtime change. HPR-P2-006 remains PENDING, P2 OPEN/P3 DEFERRED;
57 HMR completions, 123 semantic subjects and version 0.6.0-SNAPSHOT remain unchanged.

### HPR-P2-005 API correction implementation — 2026-10-09

Verified main parent: `b39c87dcaf887ada1d22c6a88d1e1f51b9f7ecaa`, tree
`95cfc6a0002a3e548e8a5d199af084f3bb9b0383`. Preflight Documentation Validation
#130, [run 37920910941](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/37920910941),
completed SUCCESS. AGENTS.md, this roadmap and RECONCILIATION.md were re-read.
Exactly the six preflight-admitted existing files change: API_CONVENTIONS.md,
ERROR_MODEL.md, API README, canonical DOCUMENT_REGISTER.md, this roadmap and
RECONCILIATION.md. No new file, contract, executable, test, migration or workflow.

The two guides now describe operation-scoped defaults/clamps/filter/sort/response
shapes; tracing header trim/fallback/context behavior; global advice status/code/body
mapping; all five high-priority scoped advice classes, including Documents 503;
and authentication/filter body limits. Current generated declarations are 258 HTTP
200 and five HTTP 201. The shared generated error envelope remains NOT ESTABLISHED.
Source revalidation corrected the preflight's page-type mapping: Telemetry returns
its nested TelemetryQueryUseCase.Page with hasNext, not kernel.pagination.Page.
No application behavior changed, including existing IllegalArgument not-found 400.

Actual local validation: canonical documentation validation PASSED (95 documents,
3,330 relative links, 24 modules and 13 primary P2 rows), offline OpenAPI integrity PASSED
(244 paths/263 operations/231 schemas), git diff --check PASSED. Exact six-file scope,
source/DTO/advice/contract checks and excluded-file preservation PASSED before
publication. All 57 primary HMR rows and all 111 current/historical HMR table rows
are byte-preserved; the subject catalogue remains 123 entries. Source-linked tests were read, not rerun; no new Java/runtime/physical
success is claimed. The unchanged snapshot still uses its retained generator SHA.

HPR-P2-005 implementation is complete but remains IN PROGRESS pending exact-head
publication Documentation Validation. Audit Check 9 has a source-backed correction;
final twelve-check verdict is not pre-asserted. HPR-P2-006/013 remain PENDING, P2 OPEN,
P3 DEFERRED. Preserve 57 HMR completions, 123 subjects, prior CI/physical provenance
and version 0.6.0-SNAPSHOT. Publish once with an expected-head lease, verify immutable
parent/tree/files, observe documentation CI startup, then STOP for notification.

### HPR-P2-005 concrete API correction preflight — 2026-10-09

Verified parent main: `83b844e22865bf5ad131bce414070c04f801e942`, tree
`150b886dd7fe19ef1c13f4012bf4765fe94e19e8`. Documentation Validation #129,
[run 37920338282](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/37920338282),
completed SUCCESS on that exact SHA. The preflight re-read AGENTS.md, this roadmap,
and current RECONCILIATION.md before mutation. Production/runtime source and the
committed contract are unchanged from the pinned audit; no fresh runtime result
is claimed. The owner has selected preparation/publication, not implementation.

#### Exact implementation write allowlist

| Existing file | Bounded change |
|---|---|
| `doc/api/API_CONVENTIONS.md` | Evidence-backed representative query/pagination/filter/sort rules, page shapes and correlation behavior; scope every rule to its operation/path |
| `doc/api/ERROR_MODEL.md` | Runtime advice mapping/body structure, scoped overrides, filter/framework limits, validation/not-found/conflict/auth/server cases and current generated response counts |
| `doc/api/README.md` | Current correction applicability, owner/source navigation and runtime-versus-generated authority; preserve initial/current generator provenance |
| `doc/governance/DOCUMENT_REGISTER.md` | Reconcile API readiness/applicability with completed correction and still-open P2; preserve historical verification notes |
| `doc/roadmap/ULTIMATE_ROADMAP.md` | Record actual correction/source checks and pending exact-head CI; update only HPR-P2-005 disposition after its required evidence exists |
| `doc/model-remediation/RECONCILIATION.md` | Append current API correction/phase authority note; preserve every HMR row and historical execution envelope |

No new file is admitted. All other tracked paths are excluded, including
`doc/api/openapi.yaml`, application/configuration/test code, migrations, workflows,
ops, database/domain/module docs, legacy docs, POM, VERSIONS.md and PROJECT_STATE.md.
The registered canonical API filenames remain unchanged.

#### Concrete source-to-document mapping

Paths below are read-only implementation evidence at the verified parent; line
ranges are navigation aids, while source content controls any final claim.

| Documentation topic | Exact production evidence | Verified rule/qualification to carry into the correction |
|---|---|---|
| Workbench GET list and POST search | `src/main/java/dz/sh/hidra/platform/workbench/HidraOperationalWorkbenchController.java` (58–85), `HidraOperationalWorkbenchService.java` in the same package (55–57, 106–144, 229–287) | GET uses page/size/q; POST uses OperationalSearchRequest. Missing/negative page becomes 0; missing/nonpositive size becomes 50; positive size clamps at 200. Do not substitute kernel validation rules |
| Workbench filtering/sorting/shape | `src/main/java/dz/sh/hidra/platform/workbench/OperationalSearchRequest.java` (27–34), `OperationalPageResponse.java` (27–35), service predicates/order above | query is trimmed lowercase substring LIKE over approved searchable fields; filters AND string-equality predicates over approved exposed fields, ignoring unknown/null fields. Valid sortBy with case-insensitive desc descends, otherwise ascends; absent/unknown sort creates no order. Page response identifies module/resource/page/size/totalElements/totalPages/items; no universal stable ordering promise |
| Telemetry readings | `src/main/java/dz/sh/hidra/modules/telemetry/api/rest/controller/TelemetryQueryController.java` (46–55), `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/query/JpaTelemetryQueryAdapter.java` (51–62, page helper) | Defaults page=0/size=100; adapter clamps page >=0 and size 1–500. from/to are optional inclusive timestamp filters; state is optional case-insensitive text match, not an invented enum-validation rule; order sourceTimestamp descending. TelemetryQueryUseCase.Page shape, including hasNext, applies to this response |
| Telemetry trend/latest | Same controller (58–70) and query adapter (66–90, unitId helper) | Trend defaults limit=1000, clamps 1–10000, returns a list in ascending sourceTimestamp order; it is not paged. Unknown point and absent latest reading throw IllegalArgumentException, so the generic missing-resource 404 must not be promised for these paths |
| Kernel pagination scope | `src/main/java/dz/sh/hidra/kernel/pagination/PageRequest.java` (25–50), `src/main/java/dz/sh/hidra/kernel/pagination/Page.java` | Kernel PageRequest rejects negative pages and sizes outside 1–200, normalizes sorting. Describe only actual usages; do not impose its admission policy on Workbench or telemetry adapters |
| Correlation metadata | `src/main/java/dz/sh/hidra/platform/web/PlatformHeaders.java` (27–28), `HidraRequestContextFilter.java` in the same package (43–95) | Highest-precedence OncePerRequestFilter trims supplied IDs, generates UUID fallback for absent/blank values, populates logging/MDC, sets both response headers and clears context in finally. IDs are tracing metadata, not authenticated actor identity; no unsupported format/uniqueness/async/error-dispatch guarantee |
| Global controller advice | `src/main/java/dz/sh/hidra/platform/exception/HidraGlobalExceptionHandler.java` (43–133) | Domain 422; Platform/unexpected 500; NoSuchElement 404; IllegalArgument/validation/constraint 400; Authentication 401; AccessDenied 403 when handled by this advice. ProblemDetail plus code/path/timestamp, optional correlationId/requestId, validation fieldErrors and configured optional exception detail; no universal guarantee for exceptions handled elsewhere |
| Scoped overrides | `src/main/java/dz/sh/hidra/modules/planning/api/rest/PlanningRevisionApiExceptionHandler.java`; `src/main/java/dz/sh/hidra/modules/alarm/api/rest/AlarmSuppressionApiExceptionHandler.java`; `src/main/java/dz/sh/hidra/modules/assets/api/rest/AssetsApiExceptionHandler.java`; `src/main/java/dz/sh/hidra/modules/workflow/api/rest/WorkflowTransitionApiExceptionHandler.java`; `src/main/java/dz/sh/hidra/modules/documents/api/rest/DocumentsContentApiExceptionHandler.java` | All five use highest-precedence advice scoped to named controllers. Planning/Alarm/Assets conflicts 409; Workflow conflict 409/denial 403; Documents invalid content 400/not-found 404/storage failure 503. Document exact codes and actual bodies, which do not inherit every global extension |
| Authentication/filter boundary | `src/main/java/dz/sh/hidra/platform/configuration/HidraSecurityConfiguration.java`; existing `doc/api/AUTHENTICATION_AUTHORIZATION.md` | JWT resource-server chains are distinct from MVC controller advice; do not claim global ProblemDetail fields for all authentication/filter failures. Describe source-proven mappings and mark universal filter error-body contract NOT ESTABLISHED; no unrelated security audit |
| Generated contract and counts | `doc/api/openapi.yaml`; `.github/scripts/validate_openapi_snapshot.py`; `.github/workflows/ci.yml` fresh equality step | 244 paths, 263 operations, 231 schemas; 258 HTTP-200 and five HTTP-201 response declarations, no declared 4xx/5xx. Runtime limits/defaults not encoded in generated schemas must be labelled source-derived; contract bytes/provenance remain unchanged |

#### Test evidence and claim limits

Read existing tests as corroboration: global handler test has missing-resource 404
and authorization 403 assertions; Planning handler test asserts stable 409 code;
Workbench service test covers ignored non-approved filter/sort fields and approved
search strings; context-filter test concerns caller actor metadata separation.
These files are respectively under `src/test/java/dz/sh/hidra/platform/exception`,
`modules/planning/api/rest`, `platform/workbench` and `platform/web` with the matching
production-class name plus Test. Their existence is not a fresh passing result,
complete endpoint coverage or proof of every normalization behavior. No new test or
runtime execution is required solely to change prose; claims come from inspected source.

#### Implementation acceptance and validation

1. Both affected API guides must contain substantive client-oriented tables/rules,
   current applicability/source links, explicit generated/runtime separation and
   scoped examples. Avoid blanket REST/error/pagination conventions not in source.
2. ERROR_MODEL must correct 254 to 258 HTTP-200 declarations and retain the five
   HTTP-201 declarations. State the runtime ProblemDetail fields and exceptions
   accurately without changing the unestablished shared generated-error limitation.
3. Verify every documented query default, clamp, filter, sort and page/list shape
   against its actual controller, adapter and committed schema. Verify every error
   code/status/extension against the responsible advice, including scoped 503.
   Generic not-found 404 must not override the observed IllegalArgument 400 paths.
4. Update API index/register applicability, then record HPR-P2-005 correction and
   its actual verification point. It stays pending CI at publication; never claim
   new exact-head success before GitHub reports it. HPR-P2-006/013 remain PENDING.
5. Run `PYTHONDONTWRITEBYTECODE=1 python3 .github/scripts/validate_docs.py`,
   `PYTHONDONTWRITEBYTECODE=1 python3 .github/scripts/validate_openapi_snapshot.py`,
   `git diff --check`, exact six-path scope verification and source/contract spot
   checks. Confirm identical excluded-file blobs, 57 HMR identities, 123 subjects
   and version 0.6.0-SNAPSHOT. No full Maven/production CI solely for these docs.
6. Publish one implementation commit with the registered message and expected-head
   lease, verify its immutable parent/tree/files and observe Documentation Validation
   startup, then STOP for the owner's Next/failure notification. If scope or source
   requires executable changes, stop and amend the preflight before mutation.

This preflight does not change API behavior or close the audit. P2 stays OPEN and
P3 stays DEFERRED. The historical successful production CI remains valid at its
original SHA; no new runtime, deployment, import, release or version result is claimed.

### Independent P2 audit analysis and bounded correction plan — 2026-10-09

Audit baseline: `c7d580cc7988f069bb939eb682664200e043c9bf`, default branch main,
tree `e4feb1e63596f6759e57bd663d412a233d54900e`. The owner requested this roadmap
amendment after the read-only twelve-check report returned FAIL: 9/12 VERIFIED.
Files existing, inventory counts, roadmap markers and passing structural CI are
not sufficient proof of substantive documentation completeness.

| Audit checks | Classification | Execution disposition |
|---|---|---|
| 1–8 and 12 | VERIFIED | Preserve authority, 24 module slices, semantics, capability boundaries, committed OpenAPI, drift gate and source traceability; do not restart implementation absent regression |
| 9 — API conventions/error model | FAILED — SUPERFICIAL IMPLEMENTATION | Reopen HPR-P2-005 for documentation correction |
| 10 — current data dictionary | FAILED — SUPERFICIAL IMPLEMENTATION | Reopen HPR-P2-006 for physical dictionary completion |
| 11 — schema ownership/migration policy | FAILED — SUPERFICIAL IMPLEMENTATION | Reopen HPR-P2-006 for explicit governance rules |
| Final P2 gate | FAIL | Reopen HPR-P2-013; require twelve substantive VERIFIED results |

The missing generated shared error envelope is an acknowledged live-benchmark
limitation, not a new runtime implementation requirement. The registered names
`SIMULATION_ANALYTICS_AI.md` and `FLYWAY_POLICY.md` are valid canonical replacements
for different filenames in the audit prompt. Neither naming difference is a defect.
Check 11 already has meaningful immutability, validation, controlled execution,
backup/history and forward-fix safeguards; complete its missing rules rather than
claiming Flyway governance is wholly absent. Specific SQL data preflights already
exist, but do not replace a documented general change-admission policy.

#### HPR-P2-005 bounded correction — audit Check 9

Affected documents: `doc/api/API_CONVENTIONS.md` and `doc/api/ERROR_MODEL.md`.
Preflight may admit their containing index and canonical status/register metadata
only where needed for current applicability; enumerate every path before mutation.

Required evidence and acceptance:

1. Document representative operation-specific page/size/filter/sort fields,
   defaults, limits and response shapes from actual controllers/DTOs/contract.
   `kernel/pagination/PageRequest.java` uses zero-based pages, sizes 1–200 and
   sorting normalization; do not promote those rules to all endpoints without evidence.
2. Describe actual `PlatformHeaders` and `HidraRequestContextFilter` handling of
   X-Correlation-Id/X-Request-Id, fallback generation and response headers, with scope.
3. Map `HidraGlobalExceptionHandler` ProblemDetail fields/extensions and evidenced
   400/401/403/404/422/500 cases; describe validation fieldErrors and scoped 409
   `PlanningRevisionApiExceptionHandler` behavior. Do not claim controller advice
   governs every security/filter/module path; identify evidenced exceptions/unknowns.
4. Reconcile the stale HTTP-200 count (254) with the committed snapshot (258),
   retaining five HTTP-201 operations and 263 operations overall at this baseline.
5. Separate runtime source evidence from generated OpenAPI: the latter documents
   success responses and does not establish a universal generated error envelope.
   Do not manually edit openapi.yaml or change executable behavior to close this check.
6. Validate links/metadata and source-contract consistency; require successful
   documentation CI on the correction commit. Runtime source/contract changes are
   outside this bounded task and require separate admission if genuinely necessary.

#### HPR-P2-006 bounded correction — audit Checks 10 and 11

Core affected documents: `doc/database/DATA_DICTIONARY.md`,
`doc/database/SCHEMA_OWNERSHIP.md` and `doc/database/FLYWAY_POLICY.md`.
The preflight must define a reproducible extraction/verification method and exact
allowlist. It may admit a maintained dictionary generator and focused validation
only if required, with explicit paths, metadata/index registration and CI impact.
Do not use an unverified text parser as authoritative final schema evidence.

Required evidence and acceptance:

1. Describe the current physical schema after the ordered Flyway chain, not only
   initial CREATE TABLE statements or JPA class names. Include actual schema/table,
   owner, columns, SQL/JPA types, nullability, PK/FK, unique/check constraints,
   important indexes and semantic purpose; retain source and applicability links.
2. Reconcile complete module/table coverage and later ALTER/drop/rename changes
   against current mappings. Current evidence is 139 migrations and 470 entities
   across 24 modules; entity count is not table count. Include owner-policy,
   technical or other migration-created tables as applicable; do not omit tables
   merely because no module @Entity represents them. Report unresolved differences.
3. Record an exact repeatable generation command/tool/version and source baseline.
   If actual ordered-schema extraction is unavailable, report the blocker and
   publish the concrete prerequisite; do not call an inventory a complete dictionary.
4. Cross-check at minimum TelemetryPoint, TopologyConnection and Nomination against
   current JPA and all relevant migrations, then validate repository-wide coverage.
5. Map actual physical tables/prefixes and exceptions to accountable owners.
   Define same-module and cross-module FK/reference admission, lifecycle/ownership
   constraints, approval requirements and application-contract boundaries. Describe
   current SQL accurately; any newly proposed rule remains TARGET pending owner
   approval rather than silently asserting that all existing SQL complies.
6. Specify migration naming/version allocation and duplicate prevention using the
   real V<version>__<description>.sql chain; preserve applied-file/history immutability.
   Define additive evolution expectations, destructive/data-affecting preflight,
   reviewed mappings, backup/rollback planning, forward-fix and exceptional repair.
   Link existing runbook and populated-data safeguards instead of inventing approvals.
7. Verify configuration, history handling and actual database-test/CI scope.
   Run documentation validation and the extraction checks. If tooling/tests/workflows
   change, run their required checks and full exact-head CI; docs-only correction
   follows the existing lightweight protocol. No applied SQL or runtime schema change.

#### HPR-P2-013 renewed closure gate

After the two correction tasks pass applicable CI, rerun all twelve checks against
one newly pinned SHA. Require explicit evidence and 12/12 VERIFIED, reconcile current
canonical indexes/status metadata and the reconciliation phase status, preserve all
legacy evidence, and require both documentation and full production CI on the
resulting closure implementation SHA. Passing CI alone cannot close failed content
checks. Keep P2 OPEN until the substantive and exact-head gates both pass; observe
CI startup and stop for the owner's Next/failure notification under the current
execution protocol. P3 remains DEFERRED; no release, version, deployment or import.


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


## HPR-P2-008 Batch 7 preflight disposition — 2026-10-06

Owner `Next` selects Workflow execution: HMR-055, HMR-061, HMR-066, HMR-081,
HMR-099. Full baseline CI #579 (37520044638) and documentation CI #71
(37520044656) passed at b6cdb1e2640be5e1990161f2b4d8e61bf2fd1156.

HMR-055 is BLOCKED by proposed WF-PREREQ-01: the workflow-purpose catalog family
is undefined and authoritative target, actor and assignment owner contracts are
missing. The actual start/task/action service is outside the legacy file allowlist.
AGENTS.md section 3.2 rule 9 requires stopping before mutating the HMR. No production
scope is admitted and no Batch 7 implementation is claimed.

The concrete eight-part contract proposal and required scope/migration/test admission
are recorded in `doc/model-remediation/RECONCILIATION.md`, Batch 7 preflight.
Resolve the prerequisite before admitting HMR-055; do not advance to HMR-061 or
Batch 8. Totals: 17 implementations, 38 still required, two blocked (080 and 055).
Documentation validation and diff whitespace checks passed; documentation CI trigger
pending at commit preparation. Stop after observing the documentation CI trigger.


## HPR-P2-008 Batch 7 contract and scope admission — 2026-10-06

Owner `Go ahead` accepts WF-PREREQ-01 from cdd0fb6. Documentation CI #72
passed on that exact head; unchanged production code has full successful CI #579
at b6cdb1e. The prerequisite is resolved by the following bounded contract and
admitted execution envelope, before any HMR production mutation.

Execute HMR-055, 061, 066, 081, 099 in the attached owner-selected order. The owner
selected this five-task batch, an explicit exception to the normal 2..4 envelope.
Each retains its exact legacy message, own HMSR obligations, scope, migration,
focused test and individual semantic commit. Append supporting admission/history
commits independently. Publish the chained commits once and stop after CI trigger.

WORKFLOW_PURPOSE is now the explicit purpose family; new starts and bindings require
active purpose/target-type entries. Legacy incompatible/null purposes must be
corrected by owners before migrations can pass; no automatic retagging or seeded
business values. Definitions are ACTIVE/version-matched, exact active bindings
mandatory, and any supplied current step must belong to the definition. No invented
initial-step algorithm: an omitted step remains omitted until explicitly selected.

Public WorkflowOwnedTargetLookup exports only module, supported catalog codes and
neutral target snapshots. Planning owns the first implementation: PLAN_REVISION and
PLANNING_PLAN (both described by Workflow DDD). Revision targets must be DRAFT or
SUBMITTED; plan targets DRAFT or SUBMITTED. Missing/duplicate resolvers deny starts.
Alarm suppression and responsibility-change operation references have no persisted
request object and remain unsupported for new starts; do not manufacture targets.
Other target owners/types remain denied until exact owner implementations are admitted.

Identity exports active/unlocked actor snapshots and permission evaluation through
its existing evaluator, including effective employee reference for Organization.
Writes bind actor IDs to CurrentSecurityContext. Callers cannot grant permissions
or supply authoritative username/display snapshots. Organization exports live ACTIVE
units in their half-open effective interval and ACTIVE employee assignment membership;
only ACTIVE employees qualify. Explicit actor assignment is authoritative; a unit pool
uses live employee membership, with no authority derived from role labels. A claimed
pool task requires the same current eligible claimant. No new role/delegation policy
or permission names. Pooled execution additionally requires step.allowClaim; unsupported
role-only and target-owner assignment strategies fail closed. Missing/inactive next-step
rules deny transitions atomically instead of generating unassigned tasks.

Transition conditions/callbacks remain unavailable. Such configuration is denied for
ACTIVE definitions, including activation checks; draft evidence may remain but cannot
execute. Steps must be distinct and in the same definition, decisions unique per source.
Generic actions are restricted to COMMENT, with task ownership required when supplied;
START/ASSIGN/CLAIM/COMPLETE and all decisions stay with authoritative producers.
Sequences are allocated under the instance lock and unique. Actions and history are
insert-only; database updates/deletes/truncation denied. Terminal task evidence is immutable.
History optional links are checked for coherent Workflow ownership and stay optional.

Legacy HMR allowlists remain valid except superseded migration filenames below.
Additional exact write scope (repository relative) per HMR:

### HMR-055 additional exact allowlist
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowExecutionOwnership.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/port/out/WorkflowConfigurationPort.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/target/WorkflowOwnedTargetLookup.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowConfigurationAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowInstance.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowInstanceRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/workflow/WorkflowActorContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/WorkflowActorQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/workflow/WorkflowOrganizationContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/query/WorkflowOrganizationQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanningWorkflowTargetLookup.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/main/resources/db/migration/V20261006_014__hmr_055_workflow_instance.sql`

### HMR-061 additional exact allowlist
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTransition.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowTransitionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowDefinitionRepositoryAdapter.java`
- `src/main/resources/db/migration/V20261006_015__hmr_061_workflow_transition.sql`

### HMR-066 additional exact allowlist
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowTask.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowExecutionOwnership.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowTaskRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/query/JpaWorkflowQueryAdapter.java`
- `src/main/resources/db/migration/V20261006_016__hmr_066_workflow_task.sql`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java`

### HMR-081 additional exact allowlist
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowAction.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowActionRepositoryAdapter.java`
- `src/main/resources/db/migration/V20261006_017__hmr_081_workflow_action.sql`

### HMR-099 additional exact allowlist
- `src/main/java/dz/sh/hidra/modules/workflow/domain/model/WorkflowStateHistory.java`
- `src/main/java/dz/sh/hidra/modules/workflow/infrastructure/persistence/adapter/JpaWorkflowStateHistoryRepositoryAdapter.java`
- `src/main/resources/db/migration/V20261006_018__hmr_099_workflow_state_history.sql`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/WorkflowExecutionPostgresTest.java`

All five HMRs additionally admit this roadmap, RECONCILIATION.md, legacy remediation
roadmap and Workflow DDD. Existing registered dedicated semantic tests remain allowed.
No published migration may change. Forward migrations 014..018 replace the unused
backdated 055/061/066/081 registrations; 018 explicitly authorizes HMR-099's new
append-only/coherence database guards. Existing invalid rows cause migration failure
with constraint/guard identity, requiring owner reconciliation; do not fabricate history.

Validation: compile, each dedicated semantic test, WorkflowTransitionApplicationServiceTest,
WorkflowExecutionPostgresTest, ArchitectureGuardrailTest, complete test and clean verify.
Local Maven Central parent availability/JDK/Docker limits must be reported accurately.
Do not proceed to attached Batch 8. No implementation is claimed by this admission.

### Batch 7 HMR-055 — IMPLEMENTED, CI PENDING

Owner-bound starts enforce active definition/version and exact binding, governed purpose/type, current-step coherence and owner target/actor snapshots; nonterminal uniqueness and same-definition/version database guards. Planning target registry denies unsupported/ambiguous owners. Eight focused behavior checks passed with temporary stubs; local Maven blocked by uncached parent, not a JUnit/PostgreSQL pass.

### Batch 7 HMR-061 — IMPLEMENTED, CI PENDING

Distinct same-definition steps and unique source decisions are protected in configuration persistence and PostgreSQL. Unsupported conditions/callbacks/COMMENT cannot attach to ACTIVE definitions or survive activation; runtime remains fail closed. Three focused behavior checks passed with temporary stubs; PostgreSQL validation pending CI.

### Batch 7 HMR-066 — IMPLEMENTED, CI PENDING

Actionable assignment, live actor/unit membership, catalog eligibility, actor/time pairs and chronology are enforced. Terminal task evidence is immutable; generic creation is starter-bound, missing next-step rules fail closed, and execution/query paths no longer authorize by username snapshots. Seven focused behavior checks passed with temporary stubs; existing transition fixtures updated for new owner dependencies.

### Batch 7 HMR-081 fixture scope extension

Before updating the existing transition fixture for live Identity permission evaluation,
admit `src/test/java/dz/sh/hidra/modules/workflow/application/service/WorkflowTransitionApplicationServiceTest.java`
to HMR-081 as well as HMR-066. This is a dependency fixture repair, not a new semantic
task; owner-authorized live permission evaluation supersedes caller permission sets.

### Batch 7 HMR-081 — IMPLEMENTED, CI PENDING

Generic recording permits comments only; configured transitions exclusively produce decisions using live Identity authority. Optional task ownership and conditional evidence are enforced; canonical actor snapshots and server-owned locked sequences replace caller evidence. Action persistence is insert-only with unique monotonic sequence and immutable database guards. Five focused behavior checks passed with temporary stubs; existing permission regression fixture updated.

### Batch 7 HMR-099 — IMPLEMENTED, CI PENDING

Mandatory status/actor display evidence fails fast. History persistence inserts and flushes without upsert; optional task/step/action/reason references are checked for instance/definition and action evidence coherence. Database guards prohibit update/delete/truncate. Four focused behavior checks passed with temporary stubs; ten PostgreSQL/Hibernate cases added for CI, not locally executed.


## HPR-P2-008 Batch 7 implementation and validation disposition — 2026-10-06

WF-PREREQ-01 is resolved by owner `Go ahead` and the explicit contract/scope admission
above. HMR-055, 061, 066, 081, 099 are independently IMPLEMENTED — CI PENDING.
Starts use owner-controlled targets/actors and exact governed configuration; transition
configuration is coherent and fail-closed; tasks use live assignment eligibility and
immutable terminal evidence; generic action decisions cannot bypass configured transitions;
actions/history insert without upsert and have database immutability/coherence guards.

Five new forward migrations 014..018 preserve published history. Invalid legacy data
requires owner reconciliation; unsupported target owners and assignment strategies deny
execution. No automatic catalog retagging, business seeding or cross-module FK.

Validation: 273 actual source/temporary API units compiled and 27 dedicated behavior
checks passed with temporary stubs. Ten PostgreSQL/Hibernate concurrency/immutability
cases added for CI. Maven compile, focused tests, test and clean verify attempted but
blocked before compilation by uncached Boot parent 4.1.1/Maven Central DNS. Host JDK17,
Docker/PostgreSQL absent. Syntax, admitted scope, documentation and diff checks passed.
Details are in RECONCILIATION.md. These checks are not Maven/JUnit/PostgreSQL pass claims.

Current totals: 22 implementations, 34 still required, one blocked (080).
Next proposed owner-selected batch: attached Batch 8 Planning (HMR-064, HMR-065),
only after green final Batch 7 CI and fresh dependency/scope admission. Publish the
commit chain once; observe final-head CI trigger and stop without awaiting completion.


## HPR-P2-008 Batch 7 CI #580 guardrail repair admission — 2026-10-07

Owner `Fail` selects repair of CI run 37525353444 at
f6d833c6362e57192f7719125b7d59b4ee938ae1. Repository verification ran
736 tests, one failure, zero errors and zero skipped. The sole failure is
ForensicRemediationClosureTest.crossModulePrivateImportsRemainClosed: its duplicate
export registry lacks the three public contract packages admitted for HMR-055 and
already present in ArchitectureGuardrailTest. The Workflow semantic/PostgreSQL tests
reported no failures. OpenAPI publication/compatibility steps were skipped after failure.

Before test mutation, admit exact repair scope:
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`

Exact repair message: `test(workflow): align forensic guardrail with admitted owner contracts`.
Add only Workflow target, Identity Workflow actor and Organization Workflow contract
package exports, preserving all private-package checks. No new production boundary,
semantic implementation, migration, broad exception or disabled assertion. Validate
all five source-scanning forensic checks, export-registry parity, focused Maven tests
and clean verify; record environment blocks honestly. Publish replacement head and
stop after CI trigger. Batch 8 remains gated on successful complete replacement CI.


### Batch 7 CI #580 repair implementation — 2026-10-07

The three HMR-055 public contract package exports are now mirrored in
ForensicRemediationClosureTest. No production/schema changes or broad exception.
All five actual forensic source-scanning methods passed with temporary JUnit APIs;
forensic and architecture registries match at 28 exact packages. Focused Maven tests
and clean verify remain locally blocked by uncached Boot parent 4.1.1. Canonical
Markdown validation and git diff checks passed. Complete replacement CI pending
at preparation; publish and stop after trigger. Do not start Batch 8.


## HPR-P2-008 Batch 8 preflight split — 2026-10-07

Owner Next selects attached Planning Batch 8 (HMR-064/HMR-065, SCC-04).
Exact repaired head ec63af0414d7fa85b9200d4bd181ac799bd072ed is green:
production CI #581/run 37570918095 and Documentation #74/run 37570918094.
Batch 7 HMR-055, 061, 066, 081, 099 are now COMPLETED with complete CI evidence.

Fresh source/HMSR/DDD review confirms both Planning obligations remain required.
HMR-065 reveals PL-PREREQ-01: no admitted Planning-facing Topology/Identity/Organization
owner boundaries; REGION/NETWORK scopes listed in the DDD have no Topology owner
entity/resolver. The candidate Organization scope registry contract uses different
identity semantics and cannot silently replace native Topology identity.

Under AGENTS.md §3.2.9 and HMRB-030, split and stop before production mutation.
HMR-065 is BLOCKED; HMR-064 stays STILL REQUIRED, held for coordinated SCC-safe
admission. Current totals: 22 implemented, 33 still required, two blocked (065, 080).
No Planning implementation or schema changes are claimed.

The concrete six-part PL-PREREQ-01 proposal, exact additional paths, migration order
and tests are recorded in `doc/model-remediation/RECONCILIATION.md`, Batch 8 preflight.
Proposed scope resolves existing Topology IDs only, denies unsupported REGION/NETWORK,
binds creator to authenticated eligible Identity actor, validates optional owner unit,
adds existing catalog-family checks, and establishes same-plan nullable revision pointers
after revision uniqueness. No new taxonomy, cross-module FK or lifecycle redesign.

This documentation-only preflight is admitted to exactly:
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`

Exact supporting commit: `docs(planning): record Batch 8 owner-contract preflight`.
Run canonical documentation checks and git diff checks. Publish and stop after observing
documentation CI trigger; production CI ignores this scope. Acceptance/amendment of the
recorded contract proposal and fresh canonical scope admission are required before the
Planning semantic commits. Do not execute Batch 9.

Preflight validation: all 82 canonical Markdown files passed UTF-8/nonempty/conflict
checks; git diff --check passed. Only the two admitted documentation paths changed.
No Maven test run is required for this documentation-only change. Documentation CI
trigger pending at commit preparation; production CI remains at verified #581.


## HPR-P2-008 Batch 8 contract and scope admission — 2026-10-07

Owner Next accepts PL-PREREQ-01 recorded at 4a32665. Exact current main is that
preflight-only head; Documentation #75 passed. Production CI #581 at ec63af0 passed;
all intervening changes are the two Markdown paths, with no production/schema/test
change. This is the green production baseline for coordinated SCC-04 execution.

The six-part contract proposal is accepted as recorded, including unsupported-scope
denial, authenticated eligible creator, optional live owner unit, catalog families,
approved immutability and SCC-safe correlated pointers. PL-PREREQ-01 is resolved.
Execute HMR-064 then HMR-065 as independent exact semantic commits; advance main once.
Do not execute Batch 9; stop after final-head CI trigger. Published migrations immutable.
Legacy unused backdated migration registrations are replaced by the forward paths below.
Exact per-HMR write scope (no unlisted paths):

### HMR-064 admitted files

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Planning.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandController.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/UpdatePlanRevisionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanRevisionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanningCatalogEligibilityPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanRevision.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanRevisionStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanRevisionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanningCatalogEligibilityAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanRevisionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanRevisionJpaRepository.java`
- `src/main/resources/db/migration/V20261007_001__hmr_064_planning_plan_revision.sql`
- `src/test/java/dz/sh/hidra/modules/planning/api/rest/controller/PlanRevisionCommandControllerTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/application/service/PlanRevisionUpdateApplicationServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/PlanningSemanticPostgresTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanRevisionSemanticRemediationTest.java`

### HMR-065 admitted files

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Planning.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/planning/PlanningCreatorContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/PlanningCreatorQueryService.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/planning/PlanningResponsibleUnitContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/infrastructure/query/PlanningResponsibleUnitQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/request/CreateOperationalPlanRequest.java`
- `src/main/java/dz/sh/hidra/modules/planning/api/rest/response/OperationalPlanResponse.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/command/CreateOperationalPlanCommand.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/dto/OperationalPlanSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/in/CreateOperationalPlanUseCase.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/OperationalPlanRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/OperationalPlanApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/OperationalPlan.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/OperationalPlanStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaOperationalPlanRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/OperationalPlanJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/OperationalPlanJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTopologyScopeContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/PlanningTopologyScopeQueryService.java`
- `src/main/resources/db/migration/V20261007_002__hmr_065_planning_operational_plan.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/PlanningCreatorQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/infrastructure/query/PlanningResponsibleUnitQueryAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/PlanningSemanticPostgresTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/OperationalPlanSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/PlanningTopologyScopeQueryServiceTest.java`

Validation: compile, both dedicated semantic tests, existing PlanRevision update/controller
tests, new owner and PostgreSQL tests, architecture/forensic guards, full test and clean
verify. Report local dependency/JDK/DB blocks accurately. No release-version change.

### Batch 8 HMR-064 — IMPLEMENTED, CI PENDING

Positive per-plan revision numbers, nullable validated base lineage, active REVISION_REASON and approved metadata/persistence/database immutability enforced. Forward V20261007_001; four focused checks passed using temporary API/assertion stubs, not Maven/JUnit. Five PostgreSQL cases registered; local compile/focused Maven blocked by uncached Boot 4.1.1 parent. Full database validation pending CI.

### Batch 8 HMR-065 — IMPLEMENTED, CI PENDING

Required French name/scope type, unique plan code, active PLAN_TYPE, owner-controlled Topology/Identity/Organization references and same-plan nullable revision pointers enforced. Creation binds authenticated eligible actor and snapshots owner display values; unsupported REGION/NETWORK denied. Forward V20261007_002; twelve focused HMR-065/owner/catalog checks passed with temporary API stubs. Nine combined PostgreSQL cases registered; Maven compile/focused/full test/clean verify blocked before compilation by uncached Boot 4.1.1/Maven Central DNS. Full CI pending.


## HPR-P2-008 Batch 8 final disposition — 2026-10-07

PL-PREREQ-01 resolved by owner Next and canonical scope admission. HMR-064 and HMR-065
independently IMPLEMENTED — CI PENDING. Positive/unique revision identity, optional lineage,
exact active catalog families and approved immutability are protected. Plans use owner
scope/actor/unit contracts and nullable same-plan revision pointers with SCC-safe forward
migrations 20261007_001/002. Only PIPELINE_SYSTEM/PIPELINE/FACILITY new scopes are supported;
REGION/NETWORK deny until owned resolvers exist. No published migration/version changes.

Sixteen dedicated behavior checks and five actual forensic source checks passed with
temporary API/assertion stubs; 150 source/API units compiled. Nine PostgreSQL cases
registered for CI. Maven compile, both focused sets, full test and clean verify attempted
but blocked before compilation by Boot parent 4.1.1/Maven Central DNS. These local checks
are not Maven/JUnit/PostgreSQL success claims. Both export registries match 31 exact
packages; Java syntax/headers, canonical Markdown, admitted scopes and git diff passed.
Detailed commands/results are in RECONCILIATION.md. Totals: 24 implemented, 32 still
required, one blocked (080). Publish chain atomically and stop after CI trigger.

Next proposed owner scope: attached Batch 9 Documents (HMR-067, HMR-068, HMR-084),
gated on green final Batch 8 CI and fresh source/owner-contract/write-scope admission.
Supporting final validation documentation is admitted to this roadmap and reconciliation
only; exact message `docs(planning): record Batch 8 validation disposition`.


## HPR-P2-008 Batch 9 preflight split — 2026-10-07

Owner Next selects attached Documents Batch 9 (HMR-067, 068, 084). Exact head
f56d55c0247875c5640ed61375da6b0896db9da6 is unchanged after git fetch. CI #582's
single verification job 112867542873 completed successfully, including repository and
OpenAPI compatibility gates (run 37643327683). Documentation #76 passed. Batch 8
HMR-064/HMR-065 are now COMPLETED with green CI; previous pending entries are historical.

Fresh HMSR/DDD/current-source review confirms all three Documents corrections remain
required. DOC-PREREQ-01: Documents-facing Identity/Workflow owner contracts and a typed
owner target registry are absent; the present no-op resolver accepts everything and is
unwired. Current writes copy caller IDs directly. Binary content is stored before version
creation, so new validation/uniqueness failures also need admitted rollback cleanup.

AGENTS.md §3.2.9 requires splitting and stopping before mutation. HMR-067, HMR-068,
HMR-084 are BLOCKED pending the concrete eight-part contract/target/storage proposal in
`doc/model-remediation/RECONCILIATION.md`, Batch 9 preflight. It proposes authenticated
eligible actors, owner-resolved supported target tuples, optional Workflow existence,
exact catalog families, SCC-safe current/link pointers and safe new-blob rollback cleanup.
No single universal target FK, invented approval policy or global closed taxonomy.
Totals: 24 implemented, 29 still required, four blocked (067, 068, 080, 084).

This documentation-only preflight is admitted to exactly this roadmap and the canonical
reconciliation. Exact supporting message: `docs(documents): record Batch 9 owner-contract preflight`.
No production/test/SQL/catalog/release change. All 82 canonical Markdown files passed
UTF-8/nonempty/conflict checks; git diff --check passed. No Maven test run required for
this docs-only change. Publish once and stop after Documentation CI trigger; full production
CI ignores this scope. Acceptance/amendment and fresh canonical combined SCC-05 admission
are required before three independent semantic commits. Do not execute Batch 10.


## HPR-P2-008 Batch 9 accepted contract and exact scope — 2026-10-07

Owner Next accepts DOC-PREREQ-01 at 37ddb90. Exact current main is that docs-only
preflight head; Documentation #77 passed. Production CI #582's sole verification job
completed successfully at f56d55c, including OpenAPI gates. Intervening changes are
only the two admitted Markdown files; this is the green production baseline.

DOC-PREREQ-01's eight-part actor/owner/Workflow/catalog/SCC/storage decision is admitted.
Combined three-task batch HMR-067 -> HMR-068 -> HMR-084 explicitly supersedes legacy
pair/solo envelope selection while preserving each obligation and exact semantic commit.
No new owner taxonomy or lifecycle state machine; supported target subset and fail-closed
unknown/ambiguous owners as proposed. Forward 003/004/005 replace unused backdated names.
Published migrations remain immutable. No release-version change or Batch 10 execution.

Storage cleanup is registered for known transaction rollback, including commit failures
with a confirmed rollback. Unknown commit outcomes must preserve content and log owner
reconciliation rather than deleting a possibly committed referenced object. Cleanup failure
must preserve the original exception and remain visible; do not pretend blob storage and
PostgreSQL commit atomically. Validate auth/document/version/date metadata before storage.

Exact write scopes follow; existing fixture/test paths retained. No unlisted files:

### HMR-067 exact files

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Documents.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/documents/api/DocumentsApi.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/DocumentsContentApiExceptionHandler.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/DocumentsRestApi.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/controller/DocumentsController.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/controller/SpringDocumentsController.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/mapper/DocumentsGeneratedRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/mapper/DocumentsRestMapper.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/LinkDocumentToTargetRequest.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/RegisterDocumentRequest.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/UploadDocumentBinaryVersionRequest.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/UploadDocumentVersionRequest.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentResponse.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentTargetLinkResponse.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentVersionResponse.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/command/LinkDocumentToTargetCommand.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/command/RegisterDocumentCommand.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/command/UploadDocumentBinaryVersionCommand.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/command/UploadDocumentVersionCommand.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/contract/target/DocumentsOwnedTargetLookup.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentTargetLinkSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentVersionContentDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentVersionSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/mapper/DocumentsApplicationMapper.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/DownloadDocumentVersionContentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/LinkDocumentToTargetUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/RegisterDocumentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/UploadDocumentBinaryVersionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/UploadDocumentVersionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentAuditEventPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentBinaryStoragePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIdentityReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentIntegrationReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentStorageObjectRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLinkRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentVersionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentsCatalogEligibilityPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentTargetLookupService.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/exception/DocumentContentTransferException.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/exception/DocumentsBoundaryViolationException.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/exception/DocumentsDomainException.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/exception/InvalidDocumentValueException.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/Document.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentStorageObject.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/policy/DocumentsBoundaryPolicy.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/service/DocumentStorageMetadataGuard.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentAccessLevel.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentExternalSyncStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentExtractionStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentExtractionType.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentId.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentPrincipalType.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentReviewStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentStorageStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentVersionStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/DocumentsInfrastructure.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/configuration/DocumentsModuleConfiguration.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/integration/DocumentsExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/integration/NoopDocumentsExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/DocumentsPersistence.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentStorageObjectRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentTargetLinkRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentVersionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentsCatalogEligibilityAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentAccessGrantJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentCatalogEntryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentCatalogTranslationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentExternalReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentExtractionRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentRetentionRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentReviewReferenceJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentStorageObjectJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentTargetLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentAccessGrantJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentCatalogTranslationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentExternalReferenceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentExtractionRecordJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentRetentionRecordJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentReviewReferenceJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentStorageObjectJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentTargetLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/DocumentStorageAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/storage/NoopDocumentStorageAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/documents/DocumentsActorContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/DocumentsActorQueryService.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/DocumentsPlanningTargetLookup.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/service/DocumentsTopologyTargetLookup.java`
- `src/main/resources/db/migration/V20261007_003__hmr_067_documents_document.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/api/rest/controller/SpringDocumentsControllerContentTransferTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentTargetLookupServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/persistence/DocumentsSemanticPostgresTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/storage/LocalDocumentBinaryStorageAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/DocumentsActorQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/application/service/DocumentsPlanningTargetLookupTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/application/service/DocumentsTopologyTargetLookupTest.java`

### HMR-068 exact files

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Documents.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/request/UploadDocumentVersionRequest.java`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentVersionResponse.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/command/UploadDocumentVersionCommand.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentVersionContentDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentVersionSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/DownloadDocumentVersionContentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/in/UploadDocumentVersionUseCase.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentVersionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentVersion.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/value/DocumentVersionStatus.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentVersionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentVersionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentVersionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/documents/DocumentsApprovalReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/DocumentsApprovalReferenceQueryService.java`
- `src/main/resources/db/migration/V20261007_004__hmr_068_documents_document_version.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/api/rest/controller/SpringDocumentsControllerContentTransferTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/persistence/DocumentsSemanticPostgresTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentVersionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/DocumentsApprovalReferenceQueryServiceTest.java`

### HMR-084 exact files

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Documents.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/documents/api/rest/response/DocumentTargetLinkResponse.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/dto/DocumentTargetLinkSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/port/out/DocumentTargetLinkRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentsApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/documents/domain/model/DocumentTargetLink.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/adapter/JpaDocumentTargetLinkRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/entity/DocumentTargetLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/mapper/DocumentsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/persistence/repository/DocumentTargetLinkJpaRepository.java`
- `src/main/resources/db/migration/V20261007_005__hmr_084_documents_document_target_link.sql`
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/persistence/DocumentsSemanticPostgresTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/semantic/DocumentTargetLinkSemanticRemediationTest.java`

Validation: compile, each semantic target, registered owner/registry tests, binary controller/
storage and rollback tests, DocumentsSemanticPostgresTest, both architecture/forensic guards,
full test and clean verify. Report local dependency/JDK/Docker constraints accurately.
Chain all commits then publish main once; observe CI trigger and stop without waiting.

### Batch 9 HMR-067 — IMPLEMENTED, CI PENDING

Required title/creator display, active exact document catalogs, code uniqueness and same-document current-version pointers enforced. Registration binds authenticated eligible Identity actor and canonical owner snapshots; neutral registry supports Topology/Planning and denies missing/ambiguous owners. Forward V20261007_003; 11 focused methods passed with temporary APIs, four PostgreSQL cases added. Compile/focused Maven blocked before compilation by uncached Boot 4.1.1 parent; full CI pending.

### Batch 9 HMR-068 — IMPLEMENTED, CI PENDING

Required upload metadata, positive per-document unique numbers, nullable existing supersession and owner-controlled Identity/Workflow references enforced. Generic upload derives authenticated uploader display. Binary prevalidates metadata and registers known-rollback new-blob cleanup; failed cleanup preserves original error, unknown commit outcome preserves content and logs reconciliation. Forward V20261007_004; 10 focused owner/version/cleanup methods passed with temporary APIs; nine combined PostgreSQL cases include transactional storage rollback and confirmed commit failure. Local focused Maven blocked by uncached Boot 4.1.1 parent; full CI pending.

### Batch 9 HMR-084 — IMPLEMENTED, CI PENDING

Required target module, active exact DOCUMENT_LINK_ROLE and owner-controlled target resolution enforced. Authenticated linking actor and canonical owner snapshots replace caller identity/display claims; optional version must belong to linked document, protected by composite FK. Forward V20261007_005; four focused methods passed with temporary APIs and three PostgreSQL cases added (12 combined). Both public export registries match 34 exact packages; all five forensic scans passed with temporary APIs. Focused Maven blocked by uncached parent; full CI pending.


## HPR-P2-008 Batch 9 final disposition — 2026-10-07

DOC-PREREQ-01 resolved by owner Next and the accepted combined scope. HMR-067, HMR-068
and HMR-084 independently IMPLEMENTED — CI PENDING. Document/version/code/catalog and
same-document pointer/link invariants are enforced. New registration/upload/link writes
use authenticated eligible Identity actors and owner-resolved targets/snapshots; optional
Workflow approval IDs resolve through the Workflow owner. Unknown/ambiguous target owners
deny. No new approval lifecycle, primary-link policy or cross-module FK.

Forward migrations 20261007_003/004/005 preserve published history; invalid legacy rows
abort for owner reconciliation. Binary prevalidation occurs before storage. Newly created
content is cleaned on known rollback (including confirmed commit failure) while database
metadata rolls back. Cleanup errors preserve the original error and are logged. Unknown
commit outcomes preserve content for reconciliation; no claim of atomic blob/database commit.

219 actual source/API units compiled; 25 focused behavior methods and five actual forensic
source checks passed with temporary APIs. Twelve PostgreSQL/Spring-JDBC cases registered
for CI. Maven compile/focused/full test/clean verify blocked by uncached Boot parent 4.1.1
and Maven Central DNS before compilation; no Maven/JUnit/PostgreSQL pass claimed. Both
export registries match 34 exact packages; 31 changed Java syntax/header checks, scopes,
canonical Markdown and git diff passed. Detailed validation is in RECONCILIATION.md.
Totals: 27 implemented, 29 still required, one blocked (080). Publish once and stop after
CI trigger. Supporting final validation scope is exactly this roadmap and reconciliation;
message `docs(documents): record Batch 9 validation disposition`.

Next proposed owner scope: attached Batch 10 Audit evidence HMR-083, HMR-095, HMR-101,
HMR-102, gated on green final Batch 9 CI and fresh source/owner/scope admission.

## HPR-P2-008 Batch 10 preflight split — 2026-10-07

Owner Next selects attached Audit evidence Batch 10 (HMR-083, 095, 101, 102).
Exact production main 6f147586583e61c0ac8212bb8dd0db14d635dabd has green CI #583
(run 37656134940, job 112911441374), including Maven repository verification and
OpenAPI publication/base/backward compatibility. Batch 9 HMR-067/068/084 are now
COMPLETED; their prior CI-pending entries are historical preparation records.

Fresh HMSR-097/112/118/119 and source review confirms owner-reference, export
self-auditing, sanitation/size and append-only gaps. Audit has no wired owner
implementations or concrete payload budget; generic saves permit merges. The legacy
083 migration would precede published history. Four tasks are BLOCKED — AUD-PREREQ-01
before any production mutation, per AGENTS.md §3.2.9. Current reconciliation totals:
27 implemented, 25 still required, five blocked (080, 083, 095, 101, 102).

The concrete reviewable proposal is in `doc/model-remediation/RECONCILIATION.md`,
Batch 10 preflight. Proposed decision: admit 083 → 095 → 101 → 102 as a four-task
exception to the legacy solo audit scopes; owner-controlled Documents/Workflow existence
contracts; REQUESTED-only scope with no inferred approval/execution/unmasking;
65,536 UTF-8 byte JSON budgets and depth 32; recursive sensitive-key redaction and
explicit credential-pattern rejection; one transactional EXPORT access-evidence row
per export request; exact active Audit catalog families; persist/flush plus DB
UPDATE/DELETE denial for event/access/before-after evidence; masked raw-text rejection.
Numeric budgets are new proposed owner choices, not existing requirements. No new
Audit taxonomy or before/after producer. Historical evidence is not rewritten.

Proposed forward migrations V20261007_006/007/008/009 replace stale/not-yet-authorized
names only after acceptance. Exhaustive per-HMR paths and validation targets must be
registered before mutation, preserving exact legacy semantic commit messages.
Only this canonical roadmap and reconciliation may change in this preflight.
Exact supporting commit: `docs(audit): record Batch 10 evidence preflight`.
No production, SQL, tests, API, catalog or version change; no Batch 11 execution.
Accept or amend AUD-PREREQ-01, then admit exact scope and implement independently.


## HPR-P2-008 Batch 10 accepted policy and exact scope — 2026-10-07

Owner Next accepts AUD-PREREQ-01 at ae149677bc31e179fa6828da3599a16a1a850e07.
Current main is that documentation-only preflight; Documentation #79 passed. Production
CI #583 at 6f147586583e61c0ac8212bb8dd0db14d635dabd is green; intervening scope is
exactly two Markdown files. The accepted nine-part decision includes 65,536 UTF-8 bytes,
JSON depth 32, owner existence, REQUESTED-only writes, transactional access evidence,
active exact catalogs and persist/flush plus DB UPDATE/DELETE protection. Approval,
execution/unmasking and future evidence lifecycle remain outside scope.

AUD-PREREQ-01 is resolved. Explicitly admit attached four-task batch 083 → 095 → 101 → 102
as the bounded combined exception to legacy solo HMRB-044/HMRB-053. Each retains its
HMSR obligations, exact commit and independent status. Published migrations immutable;
replace only unused registrations with forward 006/007/008/009 names below.
One atomic main advancement after validation; observe CI starting and stop. No Batch 11.
Supporting scope message: `docs(audit): admit Batch 10 policy and evidence scope`.
Exact paths per semantic commit (no unlisted paths):

### HMR-083 admitted files

Source: `HMSR-097`; exact commit `fix(audit): remediate semantic review AuditExportRequest`.

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Audit.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/request/RequestAuditExportRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditExportRequestResponse.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditExportRequestSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditCatalogEligibilityPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditDocumentReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditExportRequestRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditWorkflowReferencePort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditInputPolicy.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditExportRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/policy/AuditBoundaryPolicy.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/service/AuditSensitiveDataGuard.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/AuditOwnerReferenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditCatalogEligibilityAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditExportRequestRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditExportRequestJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditExportRequestJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/contract/audit/AuditDocumentReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/contract/audit/package-info.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/AuditDocumentReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/audit/AuditWorkflowReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/audit/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/AuditWorkflowReferenceQueryService.java`
- `src/main/resources/db/migration/V20261007_006__hmr_083_audit_export_request.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/persistence/AuditSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditExportRequestSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/application/service/AuditDocumentReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/AuditWorkflowReferenceQueryServiceTest.java`

### HMR-095 admitted files

Source: `HMSR-112`; exact commit `fix(audit): remediate semantic review AuditEvent`.

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Audit.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/request/RecordAuditEventRequest.java`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditEventResponse.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/command/RecordAuditEventCommand.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditEventSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/in/RecordAuditEventUseCase.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditCatalogEligibilityPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditEventRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditInputPolicy.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditEvent.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/policy/AuditBoundaryPolicy.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/service/AuditSensitiveDataGuard.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/value/AuditEventStatus.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditCatalogEligibilityAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditEventRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditEventJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditEventJpaRepository.java`
- `src/main/resources/db/migration/V20261007_007__hmr_095_audit_event.sql`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/persistence/AuditSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditEventSemanticRemediationTest.java`

### HMR-101 admitted files

Source: `HMSR-118`; exact commit `fix(audit): remediate semantic review AuditAccessRecord`.

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Audit.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/api/rest/response/AuditAccessRecordResponse.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/dto/AuditAccessRecordSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditAccessRecordRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditAccessRecord.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditAccessRecordRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditAccessRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditAccessRecordJpaRepository.java`
- `src/main/resources/db/migration/V20261007_008__hmr_101_audit_access_record.sql`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/persistence/AuditSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditAccessRecordSemanticRemediationTest.java`

### HMR-102 admitted files

Source: `HMSR-119`; exact commit `fix(audit): remediate semantic review AuditBeforeAfterValue`.

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Audit.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/audit/application/port/out/AuditBeforeAfterValueRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/AuditInputPolicy.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/model/AuditBeforeAfterValue.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/policy/AuditBoundaryPolicy.java`
- `src/main/java/dz/sh/hidra/modules/audit/domain/service/AuditSensitiveDataGuard.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditBeforeAfterValueRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/entity/AuditBeforeAfterValueJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/mapper/AuditPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/repository/AuditBeforeAfterValueJpaRepository.java`
- `src/main/resources/db/migration/V20261007_009__hmr_102_audit_before_after_value.sql`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/persistence/AuditSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/AuditBeforeAfterValueSemanticRemediationTest.java`

Validation: compile; each dedicated semantic test; shared PostgreSQL integration tests;
owner queries, existing Audit contract tests and architecture/forensic checks; full test
and clean verify. Report dependency/runtime blocks without claiming simulated API checks
are Maven/JUnit/PostgreSQL results. No release-version change.

### Batch 10 HMR-083 — IMPLEMENTED, CI PENDING

Required export metadata, active EXPORT_PURPOSE, owner-controlled optional Workflow/Documents references, bounded sanitized filters and one transactional EXPORT access record implemented. Generic writes admit REQUESTED only and persist/flush without merge. Forward V20261007_006; eight focused tests, two owner tests and four PostgreSQL/Spring/JPA tests prepared. Local Maven compile/focused blocked before compilation by uncached Boot 4.1.1 parent; production CI pending.

### Batch 10 HMR-095 — IMPLEMENTED, CI PENDING

Required source/target module and target type, active exact event/category/optional severity/reason families, bounded sanitized payload/free text and persist/flush insertion enforced. Forward V20261007_007 adds optional catalog FKs, family guards and immutable event UPDATE/DELETE denial. Five focused and four added PostgreSQL/JPA/concurrency checks prepared; local focused Maven blocked by uncached Boot parent; CI pending.

### Batch 10 HMR-101 — IMPLEMENTED, CI PENDING

Access records use persist/flush without merge; populated optional AuditEvent/export IDs must exist. Forward V20261007_008 supplies nullable local FKs and UPDATE/DELETE denial. Three focused and four added PostgreSQL/JPA/concurrency/orphan checks prepared; local focused Maven blocked by uncached Boot parent; CI pending.

### Batch 10 HMR-102 — IMPLEMENTED, CI PENDING

Required fieldPath, masked/sensitive raw-text exclusion, optional exact active MASK_REASON and existing parent event enforced. Hash-only evidence and changed=false remain legal. Persist/flush insertion plus V20261007_009 local FKs/checks/UPDATE/DELETE denial preserve immutable rows. Six focused and five added PostgreSQL/JPA/concurrency/legacy checks prepared. Temporary API type compilation passed; local focused Maven blocked by uncached Boot parent; CI pending.


## HPR-P2-008 Batch 10 final disposition — 2026-10-07

AUD-PREREQ-01 accepted by owner Next and resolved within the registered four-task scope.
HMR-083/095/101/102 are independently IMPLEMENTED — CI PENDING, preserving exact
semantic messages and allowed paths. Current totals: 31 implemented (four CI pending),
25 still required, one blocked HMR-080. Previous 27 have green production CI through #583.

Export request creation uses strict bounded sanitized JSON, exact active purpose,
owner-proven optional references and one transactional access-evidence row. REQUESTED
only; approval/execution/unmasking are not inferred. Event/access/before-after records
use persist/flush and DB UPDATE/DELETE denial, with required/family/reference/masking
checks. Existing published migrations unchanged; additive forward 006/007/008/009.
No taxonomy seed, before/after producer, release-version change or Batch 11 work.

Local Maven compile/focused/full/verify blocked before execution by uncached Boot 4.1.1
parent; online compile confirms Maven Central DNS failure. 177 temporary-API source
units compiled; 16 actual Java domain/text/hash/recursive-policy checks and five forensic
source scans passed using temporary external APIs. 34 changed Java files syntax parsed;
headers/scope/36 exact public exports/82 canonical Markdown checks passed. These do not
constitute real Maven/JUnit/Jackson/Spring/Hibernate/PostgreSQL verification. 24 dedicated
semantic/owner tests and 17 integration cases await CI. Details and parsing/secret-detection
limits are recorded in reconciliation; stored jsonb textual size may exceed compact JSON.

Supporting message: `docs(audit): record Batch 10 validation disposition`.
One final main advancement; observe production CI triggering and stop. Next proposed
owner scope: attached Batch 11 HMR-056/HMR-071, gated on green Batch 10 production CI and
fresh source/owner/exhaustive-scope admission. Do not execute automatically.


## HPR-P2-008 Batch 11 preflight split — 2026-10-07

Owner Next selects attached Integration evidence HMR-056/HMR-071. Exact production
main 712ec827e905f92b8f280a7ac8fec95eaf5cc8c9 is green at CI #584, run 37667837697,
job 112951543842, including repository verification and OpenAPI publication/base/
backward compatibility. Batch 10 HMR-083/095/101/102 are now COMPLETED; earlier pending
preparation notes are historical.

Fresh review resolves HMSR-067's stale missing-family claim: current Integration DDD
explicitly names MESSAGE_TYPE/PAYLOAD_FORMAT, so active exact-family guards require
no invented taxonomy. Optional job/endpoint lookup and endpoint/system coherence
remain unimplemented. HMSR-084 failure/trio/local-reference guards remain absent;
there is no Integration-facing Identity resolver contract or authenticated manual
write rule. INT-PREREQ-01 is split out under AGENTS.md §3.2.9 before mutation.
HMR-071 BLOCKED; HMR-056 STILL REQUIRED and unexecuted pending combined scope.
Current totals: 31 implemented, 24 still required, two blocked (071, 080).

The concrete proposal is in `doc/model-remediation/RECONCILIATION.md`, Batch 11 preflight:
admit 056 → 071 with individual exact commits; optional local reference FKs and
endpoint/system composite correlation; active existing catalog families; Identity-owned
IntegrationResolverContract using existing ACTIVE/unlocked eligibility; authenticated
actor match on new manual trios; complete-trio shape and preservation of historical
resolver evidence. No terminal-status/manual equivalence, new resolution/replay API,
payload cardinality, cross-module FK or release-version change.

Proposed forward migrations V20261007_010/011 replace only unused backdated registrations
after acceptance. Exhaustive file allowlists and validation targets must be admitted
before production mutation. Only canonical roadmap and reconciliation change now.
Supporting exact message: `docs(integration): record Batch 11 resolver preflight`.
Accept or amend INT-PREREQ-01, then admit scope and implement independently. No Batch 12.


## HPR-P2-008 Batch 11 accepted resolver and exact scope — 2026-10-07

Owner Next accepts INT-PREREQ-01 at 2188e822192f5857ee0120008ba4722a8d5e828c.
Exact current main is that docs-only preflight, Documentation #81 green. Production CI
#584 at 712ec827e905f92b8f280a7ac8fec95eaf5cc8c9 is green; intervening changes are
only canonical roadmap/reconciliation. This is the green production baseline.

The eight-part decision is accepted: existing active MESSAGE_TYPE/PAYLOAD_FORMAT,
optional local references, correlated endpoint ownership, Integration-facing Identity
resolver contract, authenticated eligible actor on new manual trios, complete-trio
shape and unchanged historical provenance. SQL freezes already recorded manual trios,
including concurrent updates; no new terminal-status/manual equivalence, payload-mode
rule, replay/resolution producer, foreign FK or permission taxonomy.

INT-PREREQ-01 is resolved. Admit 056 → 071 as an explicit combined exception to legacy
solo HMRB-023/HMRB-035. Preserve each HMSR, exact message, allowed paths and independent
status/commit. Replace only unused migration registrations with forward 010/011 below.
Publish one final branch advancement; observe production CI triggering and stop. No
Batch 12 or release-version change. Supporting exact scope commit:
`docs(integration): admit Batch 11 resolver and reference scope`.
Exhaustive independent semantic write paths (no unlisted paths):

### HMR-056 admitted files

Source `HMSR-067`; exact commit `fix(integration): remediate semantic review IntegrationExchangeMessage`.

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Integration.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/integration/api/rest/response/IntegrationExchangeMessageResponse.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/dto/IntegrationExchangeMessageSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationExchangeMessageRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationExchangeMessage.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationExchangeMessageRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationExchangeMessageJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationExchangeMessageJpaRepository.java`
- `src/main/resources/db/migration/V20261007_010__hmr_056_integration_exchange_message.sql`
- `src/test/java/dz/sh/hidra/modules/integration/infrastructure/persistence/IntegrationSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationExchangeMessageSemanticRemediationTest.java`

### HMR-071 admitted files

Source `HMSR-084`; exact commit `fix(integration): remediate semantic review IntegrationDeadLetterRecord`.

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Integration.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/integration/IntegrationResolverContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/integration/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/IntegrationResolverQueryService.java`
- `src/main/java/dz/sh/hidra/modules/integration/application/port/out/IntegrationDeadLetterRecordRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integration/domain/model/IntegrationDeadLetterRecord.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationDeadLetterRecordRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/entity/IntegrationDeadLetterRecordJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/mapper/IntegrationPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/repository/IntegrationDeadLetterRecordJpaRepository.java`
- `src/main/resources/db/migration/V20261007_011__hmr_071_integration_dead_letter_record.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/IntegrationResolverQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/integration/infrastructure/persistence/IntegrationSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/integration/semantic/IntegrationDeadLetterRecordSemanticRemediationTest.java`

Validation: compile, both dedicated semantic tests, new Identity owner test, shared
PostgreSQL integration cases, existing IntegrationJobRun/controller tests, architecture/
forensic guards, full test and clean verify. Report local Maven/runtime blocks accurately.
Published migration bytes remain immutable.

### Batch 11 HMR-056 — IMPLEMENTED, CI PENDING

Optional run/endpoint existence, correlated endpoint/system ownership and active exact existing MESSAGE_TYPE/PAYLOAD_FORMAT catalogs enforced on saves. Forward V20261007_010 adds nullable/composite FKs and catalog guards without rewriting legacy evidence. Five dedicated and five PostgreSQL cases prepared. Local compile/focused Maven blocked before execution by uncached Boot parent; CI pending.

### Batch 11 HMR-071 — IMPLEMENTED, CI PENDING

Required failure evidence, all-or-none manual trio and optional local references enforced. New manual evidence requires authenticated eligible Identity actor; recorded provenance is immutable without historical actor revalidation. Forward V20261007_011 supplies nullable FKs/checks and concurrent provenance guard. Eight focused methods, one Identity owner method and five added PostgreSQL cases prepared. Temporary API type compilation passed; local focused Maven blocked by uncached Boot parent; CI pending.


## HPR-P2-008 Batch 11 final disposition — 2026-10-07

Accepted INT-PREREQ-01 is resolved within registered 056 → 071 scope. Independent
HMR-056/HMSR-067 and HMR-071/HMSR-084 semantic commits are IMPLEMENTED — CI PENDING.
Current totals: 33 implemented (two CI pending), 23 still required, one blocked HMR-080.
Previous 31 have green production CI #584. ExchangeMessage optional references,
endpoint/system coherence and active exact catalogs are enforced; DeadLetterRecord
required evidence, optional references and complete manual provenance are enforced.
New manual provenance requires an authenticated eligible Identity actor. Existing
complete provenance cannot be altered and historical actor lifecycle is not revalidated.
Forward 010/011 only; no catalog seed, foreign FK, status matrix or release-version change.

Local Maven compile/focused/existing/full/verify are blocked before execution by uncached
Boot 4.1.1 parent; online compile confirms Maven Central DNS failure. 158 temporary-API
source units compiled; four actual behavior checks and five forensic source scans passed
with temporary external APIs. 14 changed Java files syntax parsed; headers, independent
9/16-path semantic scopes, 37 matching exact public exports and 82 canonical Markdown
checks passed. These do not establish real Maven/JUnit/Spring/JPA/PostgreSQL verification.
14 dedicated semantic/owner methods and 10 database cases await CI; the latter test the
Integration base plus these forward migrations, not the full Flyway chain. Reconciliation
records exact validation disposition and limits.

Supporting exact message: `docs(integration): record Batch 11 validation disposition`.
Publish scope admission, two semantic commits and this validation disposition; advance
main once after exact-tree checks, observe production CI started, then stop. Next proposed
owner scope: attached Batch 12 Reporting HMR-057/HMR-093, gated on green Batch 11 CI and
fresh source/owner/exhaustive-scope admission. Do not execute automatically.


## HPR-P2-008 Batch 12 Reporting prerequisite preflight — 2026-10-07

Current exact main: 609f3b78adacf929dd31f630083d6189621a16d5. Production CI #585
(run 37674577775) completed SUCCESS. Batch 11 HMR-056/HMR-071 are now CI-confirmed;
33 implemented items have green production CI. Owner Next selects attached Batch 12
HMR-057/HMSR-068 then HMR-093/HMSR-110. Neither semantic task is implemented here.

### Live evidence and prerequisite REP-PREREQ-01

AGENTS.md section 3.2.9 requires splitting out an unregistered prerequisite before
semantic mutation. The following current source evidence makes scope admission necessary:

- ReportingApplicationService.queueReportRun already resolves request and definition,
  checks their correspondence, and asks ReportingWorkflowApprovalContract for approval
  when required. These HMSR-068 claims are partly stale. It still omits explicit queueable
  state for non-approval requests, active-definition/access revalidation, template lineage
  and required parameter evidence. The service itself is absent from HMR-057's allowlist.
- V20261004_013__hmr_013_reporting_report_definition.sql already implements a database
  run queue gate requiring ACTIVE definition, matching access-policy scope, approved
  request shape when required, ACTIVE version and active template of the same definition.
  Its trigger also runs on FK-column updates, requiring care to preserve historical
  reproducibility when unrelated run updates bind those columns again.
- V20260929_002__enforce_same_module_reference_integrity_b.sql still points run request
  fk_hra111_reporting_019 and artifact run fk_hra111_reporting_009 to catalog entries.
  It also points parameter-value request fk_hra111_reporting_012 to catalog entries.
  The latter blocks ordinary required-parameter evidence and is an upstream prerequisite
  beyond the original HMR-057 list. All three corrections require forward migrations;
  published SQL must remain immutable and invalid historical rows must abort migration.
- Required parameter definitions/values currently have JPA repositories but no application
  query port. Values carry TEXT/NUMBER/BOOLEAN/DATE/DATE_TIME/JSON/REFERENCE and the DDD
  says one matching value field and required values before queueing. Default definitions
  alone do not constitute recorded concrete request parameter evidence.
- generateReportArtifact does not load a run. ReportOutputArtifact does not enforce at
  least one normalized document/storage ID at construction. HMR-093 authorizes no SQL
  and omits the service from its exact paths despite the required FK repair.
- Documents exposes target and Audit-specific contracts only; the Audit contract looks
  up Document metadata, not storage objects. The listed legacy Documents outbound ports
  are not exported lookups for Reporting. Documents owns both DocumentRepositoryPort
  and DocumentStorageObjectRepositoryPort and can expose distinct existence checks.

### Concrete decision proposed for owner acceptance

1. Admit 057 then 093 as attached Batch 12, preserving individual exact semantic messages,
   independent tests/statuses/commits, and one final branch advancement.
2. Extend HMR-057 to the authoritative Reporting service and an internal application
   query boundary for template lineage and required parameters, implemented through
   Reporting-owned persistence. Preserve current owner contracts and private boundaries.
3. For a new queue operation require ACTIVE definition; allow SUBMITTED or APPROVED
   requests for non-approval definitions, and APPROVED plus Workflow-owned approval for
   approval-required definitions. Reuse Identity-owned access checks for restricted
   definitions with persisted requester/scope evidence. Rejected/cancelled/draft/already
   queued/running/completed requests cannot initiate a fresh queue through this path.
   This does not introduce a new request transition or promise duplicate-run prevention.
4. New queues require the existing ACTIVE version/active template policy and matching
   request/run/template definition lineage. Historical persisted runs retain their exact
   version even after retirement; unrelated updates must not reapply new-queue lifecycle
   eligibility. Always preserve relational coherence; do not rewrite historical IDs.
5. Every active required parameter definition needs concrete request evidence matching
   its definition and code, with exactly the value field required by its recorded valueType.
   Blank text/reference/JSON is absent, while numeric zero and false are valid. Defaults
   are not silently materialized. No new expression engine or parameter taxonomy is added.
6. HMR-057's forward migration also corrects parameter-value request FK as a narrowly
   admitted prerequisite, as well as run request FK, lineage and explicit terminal rules:
   COMPLETED requires completedAt; FAILED requires nonblank failureReason. Proposed name
   V20261007_012__hmr_057_reporting_report_run.sql replaces the unused backdated name.
   Preserve existing forward history and fail closed on invalid legacy data.
7. Add Documents-owned application.contract.reporting.ReportingDocumentReferenceContract
   with distinct documentExists and storageObjectExists queries. Documents implements
   these through its own ports. Existence is the admitted rule; no unstated lifecycle,
   provider-active, binary-content availability or document/storage pairing rule follows.
8. HMR-093 checks run existence and at least one normalized Documents reference; if both
   references are supplied, validate both. Preserve checksum requirements. Artifact
   creation need not wait for COMPLETED. Add the correct same-module run FK and reference
   shape CHECK in V20261007_013__hmr_093_reporting_report_output_artifact.sql. No Documents
   cross-module FK, private import or storage access is permitted.
9. After acceptance, register exhaustive independent paths before production mutation,
   including service, internal query port/adapter, Documents contract/query/owner test,
   both export registries, focused semantic and PostgreSQL tests, and affected existing
   Reporting service/controller fixtures. No extra production scopes are implied.
10. Validate new queue eligibility, access/approval denial, template mismatch, missing
    or empty required values including false/zero, historical template retirement,
    terminal invariants, Documents reference distinctions, FK correction, invalid legacy
    abort and relevant concurrent writes. Run focused/existing/architecture/full verify
    gates and report local dependency/runtime limits. Observe final production CI started
    and stop; do not advance to Batch 13 automatically.

Disposition: HMR-057 and HMR-093 BLOCKED pending REP-PREREQ-01 acceptance and exhaustive
scope admission. Current totals: 33 implemented, 21 still required, three blocked
(HMR-057, HMR-080, HMR-093). Only canonical roadmap/reconciliation change in this preflight.
Exact supporting message: `docs(reporting): record Batch 12 execution preflight`.
Next action: accept or amend REP-PREREQ-01, then admit exact scope and implement 057/093.


## HPR-P2-008 Batch 12 accepted execution and exact scope — 2026-10-07

Owner Next accepts the full REP-PREREQ-01 proposal at 5e13fda49b4ccfa3cd6940548a8c4b4d4d4875b9.
Documentation #83 passed there; production CI #585 passed on its production parent
609f3b78adacf929dd31f630083d6189621a16d5. Preflight changes only canonical documentation.
REP-PREREQ-01 is resolved; admit attached combined 057 → 093, including narrow required
parameter request FK prerequisite, new Documents-specific public lookup and forward
012/013 migrations. Preserve all ten accepted decisions, individual source HMSR,
exact semantic commits/statuses/tests. No release change or Batch 13 implementation.
Register supporting exact scope message: `docs(reporting): admit Batch 12 execution scope`.
Exhaustive independent paths follow; no unlisted files are admitted.

### HMR-57 admitted paths

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Reporting.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/request/QueueReportRunRequest.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportRunResponse.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/command/QueueReportRunCommand.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportRunSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/in/QueueReportRunUseCase.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportQueueEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportRunRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportRun.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunMode.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/value/ReportRunStatus.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportQueueEvidenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportRunRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportRunJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportRunJpaRepository.java`
- `src/main/resources/db/migration/V20261007_012__hmr_057_reporting_report_run.sql`
- `src/test/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/ReportingSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportQueueEvidenceSemanticTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportRunSemanticRemediationTest.java`

### HMR-93 admitted paths

- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `docs/data definition/Reporting.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/documents/application/contract/reporting/ReportingDocumentReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/contract/reporting/package-info.java`
- `src/main/java/dz/sh/hidra/modules/documents/application/service/ReportingDocumentReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/api/rest/response/ReportOutputArtifactResponse.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/dto/ReportOutputArtifactSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/port/out/ReportOutputArtifactRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/reporting/domain/model/ReportOutputArtifact.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/adapter/JpaReportOutputArtifactRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/entity/ReportOutputArtifactJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/mapper/ReportingPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/repository/ReportOutputArtifactJpaRepository.java`
- `src/main/resources/db/migration/V20261007_013__hmr_093_reporting_report_output_artifact.sql`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/documents/application/service/ReportingDocumentReferenceQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/ReportingSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportOutputArtifactSemanticRemediationTest.java`

Validation: compile, dedicated Run/Artifact/QueueEvidence/Document owner tests, shared
PostgreSQL cases, existing Reporting request/definition tests, architecture/forensic,
full test and clean verify. Record dependency/runtime blocks. Publish ordered exact
trees and advance main once; observe production CI started and stop.

### Batch 12 HMR-057 — IMPLEMENTED, CI PENDING

Queue eligibility/access/approval, exact template lineage, concrete required parameters and terminal evidence enforced. Forward 012 corrects run/parameter request FKs and guards lineage/history. Eight Run, four QueueEvidence and nine PostgreSQL cases prepared; local runtime validation follows; CI pending.

### Batch 12 HMR-093 — IMPLEMENTED, CI PENDING

Existing run and nonblank Documents reference evidence required; every supplied reference is independently owner-validated. Forward 013 corrects artifact/run FK without Documents FK. Six focused methods, one Documents owner method and five additional PostgreSQL cases prepared; CI pending.


## HPR-P2-008 Batch 12 final disposition — 2026-10-07

Accepted REP-PREREQ-01 is resolved within combined 057 → 093, preserving independent
HMR/HMSR obligations, exact commits and scopes. HMR-057 and HMR-093 are IMPLEMENTED —
CI PENDING. Totals: 35 implemented (two CI pending), 21 still required, one blocked HMR-080.
Previous 33 green through production CI #585; preflight Documentation #83 passed.

Authoritative queue checks definition/request/template lineage, active new-queue policy,
request status, Workflow approval, restricted Identity access and required concrete typed
parameters. SQL 012 corrects run/parameter request FKs, guards lineage and terminal evidence,
and preserves retired-version historical updates. Output registration validates existing
run and each supplied distinct Documents-owned reference. SQL 013 corrects artifact/run FK
and reference shape. No foreign-module FK, taxonomy, new transition, executor or duplicate
run guarantee. Parameters are evaluated at queue time; no immutability claim is introduced.

Local Maven compile/focused/existing/full/verify blocked before execution by uncached Boot
4.1.1 parent; online compile confirms Maven Central DNS failure. 189 temporary-API source
units compiled; 13 actual domain/typed-field behavior checks and five forensic source scans
passed with temporary external APIs. 17 Java files syntax parsed; headers, 13/16-path
independent scopes, 38 matching exact exports and 82 canonical Markdown checks passed.
These do not establish real Maven/JUnit/Spring/JPA/PostgreSQL verification. 19 dedicated
methods and 14 PostgreSQL cases await CI. Database fixtures test base/HRA-111 relevant
clauses/HMR-013 plus forward 012/013, not complete Flyway history. Reconciliation records
validation commands, boundaries and actual results. Published SQL remains immutable.

Supporting exact message: `docs(reporting): record Batch 12 validation disposition`.
Publish scope admission, two semantic commits and this record as exact trees; advance main
once, observe production CI started, then stop. Next proposed owner scope: attached Batch 13
Simulation HMR-078/HMR-079 after green Batch 12 CI and fresh source/owner/exact admission.
Do not execute automatically. Project version remains 0.6.0-SNAPSHOT.

## Batch 12 CI #586 regression repair — 2026-10-08

Production CI #586 / run 37683362119 on `7a5d98a8defa92fb5151dcddba34a90f1c958125` ran 895 tests:
one failure and three errors. Per AGENTS.md section 3.2.8 and owner `next`, repair only
Batch 12 regressions; do not begin Batch 13. Admit the following exact repair paths:

- `src/test/java/dz/sh/hidra/InternalReferenceIntegrityMigrationTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/semantic/ReportQueueEvidenceSemanticTest.java`
- `src/test/java/dz/sh/hidra/modules/reporting/infrastructure/persistence/ReportingSemanticPostgresIntegrationTest.java`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`

Exact supporting commit: `test(reporting): repair Batch 12 CI fixtures and reference inventory`.
Materialize required-parameter mocks before starting repository stubbing, provide explicit
mask_sensitive_values in the access-policy SQL fixture, and include the three corrected
Reporting FK replacements in the original 551-reference inventory. Assert exact child/parent
tables, validation and removal of superseded names; preserve the classified count rather than
lowering it. No production source, published migration, release or semantic obligation changes.
Validation: all three changed Java tests syntax-parse successfully. The replacement endpoint
map matches published SQL exactly, all published migration bytes are unchanged, and the
five-file scope/whitespace checks pass. Focused test, compile and clean verify attempts stop
before execution because Spring Boot parent 4.1.1 is absent from the offline Maven cache.
These source checks do not establish a JUnit/Mockito/PostgreSQL pass.
Full PostgreSQL/Mockito verification remains replacement CI pending; confirm it started and
stop until `next` or `fail`. Batch 13 remains gated on green production CI.

### Batch 12 CI #587 parameter-fixture repair — 2026-10-08

CI #587 / run 37752314699 on `0276365b307250cca365bad21e09ed17142b2010`
ran 895 tests: two assertion failures and no errors. The prior SQL fixture and FK
inventory regressions are resolved. ReportQueueEvidenceSemanticTest now reaches its
positive assertions; default Mockito nullable Boolean false supplies an unintended
second populated field for non-BOOLEAN fixtures. Production's exactly-one-field
predicate must remain intact.

Admit only ReportQueueEvidenceSemanticTest and these two canonical records for exact
supporting commit `test(reporting): preserve nullable parameter fixture fields`.
Use spies over real ReportParameterValueJpaEntity instances initialized with absent
fields NULL. Keep selected false/zero values concrete, reject extra fields and assert
initial nullable Boolean absence for every value type. No production, migration,
release, semantic obligation or Batch 13 change.

Validation: changed test syntax and whitespace checks pass. Twenty-two checks executed
against the actual entity and actual concrete-value predicate with temporary external
annotation/repository APIs: NULL absence, all seven selected types including zero/false,
and rejection of two fields. This is not Mockito/JUnit/Spring/JPA runtime verification.
Focused Maven test is blocked before execution by uncached offline Boot 4.1.1 parent.
Replacement full CI remains pending. Confirm it started, then stop until `next` or `fail`.
Batch 13 HMR-078/HMR-079 remains gated on green production CI.

## HPR-P2-008 Batch 13 Simulation prerequisite preflight — 2026-10-08

Owner `next` selects proposed HMR-078/HMSR-092 followed by HMR-079/HMSR-093.
Exact main d843015b362abbc6929a6de03a8578d907d8fdfb is green in production CI #588
(run 37754846776). Batch 12's two implementations and both fixture repairs are now
CI-confirmed. There are 35 implemented subjects with green production verification.
No Batch 13 production mutation is performed in this preflight.

### SIM-PREREQ-01 — concrete live owner-boundary and write-scope gap

AGENTS.md section 3.2.9 states: "If an HMR reveals an unregistered prerequisite, SCC
complication, owner-contract gap, cross-module lifecycle dependency, migration-order
conflict, or materially larger semantic redesign, split it out and stop before mutating
that HMR." The live evidence requires admission before semantic implementation:

- HMR-078 requires PIPELINE/SEGMENT/FACILITY/EQUIPMENT/NODE/CONNECTION target validation.
  Existing SimulationTopologyScopeContract/TopologySimulationScopeQueryService resolves
  only PIPELINE_SYSTEM and PIPELINE as model scopes with ACTIVE eligibility; it is not
  a candidate-target contract. Extending its model-scope vocabulary implicitly would
  change another semantic task. Topology owns the relevant repositories, including
  PipelineSegmentJpaRepository and TopologyNodeJpaRepository where public domain ports
  are absent. Simulation must not import these private persistence types.
- JpaSimulationCandidateChangeRepositoryAdapter currently maps and saves without catalog
  family or target lookup. Domain targetType/afterValue normalize blank to null. Existing
  HRA-111 parent candidate and generic change-type FKs are present and should be retained.
- SimulationApplicationService publishes PUBLISHED records directly. It has no Audit
  publication interface, catalog checks or optional candidate validation. Its path is
  absent from HMR-079's original exact list, despite the required publication correction.
  Both recommendation repositories currently admit generic saves without audit evidence.
- Recommendation run/type FKs exist. Optional candidate/confidence FKs are absent. Title,
  description and createdAt are not domain-guarded. Simulation catalog active/family fields
  and Audit-owned RecordAuditEventUseCase/AuditInputPolicy already exist for reuse.
- Audit exports Organization, Alarm and Risk-specific contracts, but no Simulation contract.
  RiskRegisterAuditContractAdapter demonstrates active EVENT_TYPE/EVENT_CATEGORY resolution
  and delegation to Audit-owned event recording. No provisioned Simulation publication
  EVENT_TYPE was found; missing taxonomy must fail closed, not silently omit evidence.
- Latest migration is V20261007_013. Unexecuted V20261004_078/079 registrations are
  backdated relative to published history and must be replaced with forward versions.

### Concrete proposal for owner acceptance

1. Admit attached 078 -> 079 with independent exact semantic messages, source reviews,
   tests and statuses. Preserve the descriptive no-actuation boundary and existing
   PUBLISHED lifecycle; introduce no adoption workflow, solver policy or field command.
2. Add distinct Topology-owned application.contract.simulation.SimulationTopologyTargetContract
   with scalar type/ID existence resolution, implemented inside Topology through its own
   six repositories. Supported exact types are PIPELINE, SEGMENT, FACILITY, EQUIPMENT,
   NODE and CONNECTION. Missing/unsupported/blank targets fail closed. Existence is the
   admitted rule; no unstated ACTIVE lifecycle rule or new snapshots are imposed. Leave
   model-scope resolution unchanged. The existing exported Topology package suffices.
3. Enforce nonblank targetType/afterValue in the change domain and active exact
   SIMULATION_CHANGE_TYPE at the write boundary. Retain the parent candidate FK; add
   field/family SQL integrity with fail-closed legacy preflight in
   V20261008_001__hmr_078_simulation_candidate_change_integrity.sql. Topology stays scalar,
   with no foreign-module FK. Catalog eligibility applies to new/reference-changing use;
   historical inactive catalog records are not rewritten. Lock catalog reads to serialize
   concurrent eligibility changes; preserve family coherence of referenced catalog rows.
4. Enforce recommendation title/description/createdAt before persistence. Require active
   exact SIMULATION_RECOMMENDATION_TYPE and, when supplied, SIMULATION_CONFIDENCE_LEVEL;
   resolve optional candidate fail closed. Add nullable local candidate/confidence FKs,
   required-field/family guards and legacy abort in
   V20261008_002__hmr_079_simulation_recommendation_integrity.sql. Do not invent candidate/run
   equality or completed-run prerequisites absent from HMSR-093.
5. Add Audit-owned application.contract.simulation.SimulationRecommendationAuditContract
   and an Audit integration adapter. Scalar publication evidence includes recommendation,
   run, optional candidate, type, actor when supplied and actual publication time. Keep
   publisher optional, never invent an actor, and use AuditInputPolicy for emitted content.
   Include no raw credentials, tokens or unrestricted sensitive description payloads.
6. Use an explicit transactional publication operation that persists/flushes a new
   PUBLISHED recommendation and invokes that Audit owner interface in the same transaction.
   Generic save must not create or transition into PUBLISHED without this path. Missing
   taxonomy, denied references or Audit failure rolls publication back. No REQUIRES_NEW,
   asynchronous best-effort logging, fabricated historical publication or duplicate-audit
   guarantee is substituted for successful publication evidence. Keep existing lifecycle
   states and optional export metadata. Compatibility constructors lacking the new owner
   dependency must fail closed for publication.
7. Provision active Audit EVENT_TYPE/SIMULATION_RECOMMENDATION_PUBLISHED and reuse or
   provision EVENT_CATEGORY/BUSINESS through Audit-owned forward
   V20261008_003__provision_simulation_recommendation_audit_taxonomy.sql, following the
   existing Risk creation taxonomy pattern. Conflicting/inactive owner taxonomy requires
   reconciliation rather than automatic reactivation. Preserve all published SQL bytes.
8. After acceptance, register exhaustive independent exact paths before mutation:
   078 domain/repository adapter/port as needed, Simulation catalog query, new Topology
   target contract and owner adapter, focused change/owner tests and forward 001;
   079 domain/publication port/adapter/service and affected fixtures, new Audit contract,
   package-info/integration adapter and owner tests, both architecture export registries,
   recommendation and transactional PostgreSQL tests, forward 002/003. Shared progress
   scope is canonical roadmaps/reconciliation, legacy semantic register and Simulation/Audit
   data definitions. No unlisted production paths or release changes are implied.
9. Validate required fields, every supported/missing/unsupported target, exact catalog
   family/eligibility, optional reference behavior, actual successful Audit emission,
   generic-publication rejection and transactional rollback. PostgreSQL tests cover local
   FKs, invalid legacy abort without fabricated repair and relevant concurrent catalog/
   publication writers. Run focused/existing/architecture/full verify and report actual
   dependency/runtime limits. Confirm final production CI started and stop.

Disposition: HMR-078 and HMR-079 BLOCKED pending SIM-PREREQ-01 acceptance/exhaustive admission.
Current totals: 35 CI-confirmed implementations, 19 still required, three blocked
(HMR-078, HMR-079, HMR-080). This preflight changes only Ultimate Roadmap and canonical
RECONCILIATION.md. Exact supporting commit: `docs(simulation): record Batch 13 execution preflight`.
Documentation validation is the applicable CI for this docs-only commit; no production
verification is claimed for unimplemented Batch 13 work. Next action: accept or amend this
concrete proposal, then admit exhaustive scopes and implement 078/079 in individual commits.


## HPR-P2-008 Batch 13 accepted execution envelope — 2026-10-08

Owner `next` accepts SIM-PREREQ-01 on preflight fe8fe8452cda4055607884a64387ed0f8cb89355.
Production baseline CI #588 is green; documentation CI #87 passed on the preflight.
Attached execution order is HMR-078/HMSR-092 -> HMR-079/HMSR-093, retaining individual
exact semantic commits and validation. The accepted proposal above governs behavior.
Replace the unexecuted backdated migration registrations with forward 001/002 below;
forward 003 is Audit-owned taxonomy prerequisite attached to HMR-079. No published SQL is edited.

### Exhaustive HMR-078 write scope

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/data definition/Simulation.md`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationCandidateChange.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationCandidateChangeRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/repository/SimulationCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationTopologyTargetContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/integration/SimulationTopologyTargetContractAdapter.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationCandidateChangeSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/integration/SimulationTopologyTargetContractAdapterTest.java`
- `src/main/resources/db/migration/V20261008_001__hmr_078_simulation_candidate_change_integrity.sql`

Exact commit: `fix(simulation): remediate semantic review SimulationCandidateChange`.

### Exhaustive HMR-079 write scope

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/data definition/Simulation.md`
- `docs/data definition/Audit.md`
- `src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationRecommendation.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationRecommendationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/simulation/application/service/SimulationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/adapter/JpaSimulationRecommendationRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/simulation/SimulationRecommendationAuditContract.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/simulation/package-info.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/SimulationRecommendationAuditContractAdapter.java`
- `src/test/java/dz/sh/hidra/modules/simulation/semantic/SimulationRecommendationSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/SimulationSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/SimulationRecommendationAuditContractAdapterTest.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/main/resources/db/migration/V20261008_002__hmr_079_simulation_recommendation_integrity.sql`
- `src/main/resources/db/migration/V20261008_003__provision_simulation_recommendation_audit_taxonomy.sql`

Exact commit: `fix(simulation): remediate semantic review SimulationRecommendation`.

Focused verification: SimulationCandidateChangeSemanticRemediationTest,
SimulationTopologyTargetContractAdapterTest, SimulationRecommendationSemanticRemediationTest,
SimulationRecommendationAuditContractAdapterTest, SimulationSemanticPostgresIntegrationTest,
existing Simulation tests and both architecture registries; compile, test and clean verify.
No release change, later HMR, direct Topology mutation or cross-module FK is admitted.

### Batch 13 HMR-078 implementation result — 2026-10-08

HMSR-092 live review recovered independently. Enforced targetType/afterValue, locked
SIMULATION_CHANGE_TYPE family/active eligibility on new references, and Topology-owned
lookup for PIPELINE/SEGMENT/FACILITY/EQUIPMENT/NODE/CONNECTION. No model-scope vocabulary
change, actuation or foreign-module FK. Existing candidate/type FKs retained. Forward 001
rejects invalid legacy fields/family without repair and prevents family reclassification
while permitting unchanged inactive historical references. Compatibility writes fail closed.
Validation: seven Java files parsed; actual Simulation domain/entities/mapper/repositories
and changed adapters type-compiled on Java 17 with dependency stubs; 30 actual-boundary
harness checks passed. Maven compile/focused tests cannot resolve Boot parent 4.1.1 offline;
wrapper invoked with bash because checkout executable bit is absent. No real Spring,
JUnit or PostgreSQL pass claimed. Exact-head Java 21 CI pending final 079 publication.
Current subjects: 35 CI-confirmed + one implemented pending CI + 20 still required/in
progress + one blocked HMR-080. Next attached task HMR-079; stop after final CI starts.

### Batch 13 HMR-079 implementation result — 2026-10-08

HMSR-093 source review recovered independently. Domain enforces title/description/createdAt.
Write boundary resolves run and optional candidate without inventing candidate/run equality
or completed-run eligibility. Locked catalog reads enforce exact recommendation/confidence
families and new-reference active eligibility, preserving unchanged inactive history.
Forward 002 adds nullable candidate/confidence local FKs, required-content checks, family
guards and fail-closed legacy preflight. Forward 003 provisions/reuses active Audit taxonomy,
rejecting duplicates/inactive rows without reactivation or historical evidence fabrication.

SimulationRecommendationRepositoryPort.publish creates a new PUBLISHED row using persist/
flush, captures actual publication time and invokes Audit's scalar publication contract in
the same required transaction. Generic PUBLISHED save is rejected. Legacy implementations
without audited publication fail closed. Audit emits recommendation/run/optional candidate/
type/supplied actor/time through AuditInputPolicy and real event recording, excludes raw
descriptions, and must return a nonblank receipt. Missing taxonomy, invalid references,
Audit failure and duplicate IDs abort publication; no best-effort/REQUIRES_NEW path.
No direct target-module write, actuation or foreign-module FK is introduced.

Validation actually performed: 12 changed Java files parsed; changed production boundaries
plus actual Simulation domain/entities/mapper/application/repository types and Audit command/
DTO/catalog types compiled on Java 17 with dependency stubs. Twenty-eight HMR-079 boundary
harness checks passed; combined batch count 58. Stubs do not constitute Spring/JUnit/Jackson
sanitation/PostgreSQL execution. Focused unit tests and real PostgreSQL migration/concurrency/
Spring-JPA tests are added, including successful actual Audit service/event persistence and
rollback of both flushed records on injected failure, missing taxonomy and competing same-ID
publishers. No real integration pass is claimed locally.

Attempted bash mvnw -o -q compile (-DskipTests), focused/existing/architecture test, test, and
clean verify: all stop before compilation because Boot parent 4.1.1 is not cached. Only Java
17 is installed; PostgreSQL/Docker are absent. Java 21 full verification belongs to exact-head
CI. Canonical Markdown and whitespace/scope checks passed. Independent 078 semantic commit
d15e78f4c37c8a5915d28e2aa13d9c080a109224 precedes this exact 079 semantic commit. Publish once
on main, confirm final production CI started, then stop until owner `next` or `fail`.
Current total: 37 implemented (35 CI-confirmed, two awaiting CI), 19 still required, one
blocked HMR-080, 57 evaluated. Next HPR-P2-008 batch requires fresh admission and green CI;
no later HMR is automatically admitted by Batch 13.


## HPR-P2-008 Batch 13 CI #589 migration-fixture repair — 2026-10-08

Owner `fail` requests repair of failed exact head 9863f11ed20ee5eec415756f55f4add9d2b0084f.
CI #589 (37760863845), job 113256645699: 916 tests, zero failures, eight errors,
all SimulationSemanticPostgresIntegrationTest setup errors: PostgreSQL reports
"LOCK TABLE can only be used in transaction blocks". The fixture executes migration
files through an autocommit connection, unlike Flyway's transactional execution.
Admit only these exact repair paths:

- `src/test/java/dz/sh/hidra/modules/simulation/infrastructure/persistence/SimulationSemanticPostgresIntegrationTest.java`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`

Exact supporting message: `test(simulation): run migration fixtures transactionally`.
Execute each migration on its own explicit JDBC transaction; commit successful files,
roll back failures and retain the original exception. Keep ad hoc SQL helpers unchanged.
Do not edit published migrations or production Java, and do not begin another batch.

### CI #589 repair result

Only the migration-file test helper changes: disable JDBC autocommit before executing
the complete file, commit success, rollback failure, preserve the original exception
and suppress any rollback failure. This preserves atomic migration rollback and table
locks while leaving production/migration bytes intact. Eight checks on the extracted
actual helper using JDBC proxies passed (success ordering, rollback, runtime failure,
connection closure and original exception preservation); Java syntax passed. These
checks do not claim actual PostgreSQL/Spring execution. Focused command
`bash mvnw -o -q -Dtest=SimulationSemanticPostgresIntegrationTest test` remains blocked
before compilation by uncached Boot parent 4.1.1. Canonical documentation, whitespace
and exact three-file scope checks passed. Publish repair, confirm new CI started, stop.
Batch 13 remains implementation-only pending green exact-head production verification;
HPR-P2-008 next batch remains gated. No new files, release or production behavior change.


## HPR-P2-008 Batch 14 Risk prerequisite preflight — 2026-10-08

Owner `next` selects attached "00 - Batchs Roadmap.txt" row 14: HMR-058/HMSR-069
RiskAssessment and HMR-077/HMSR-091 RiskEvidenceLink. Exact production baseline
d53b616de28abcd680da827e09ea7677bc7e4a31 is green in CI #590 (37762171060).
Batch 13 HMR-078/079 and the migration-fixture repair are now CI-confirmed.
This preflight mutates documentation only; no Batch 14 production change is claimed.

### RISK-PREREQ-01 — live unregistered owner/lifecycle prerequisites

AGENTS.md section 3.2.9 requires: "If an HMR reveals an unregistered prerequisite, SCC
complication, owner-contract gap, cross-module lifecycle dependency, migration-order
conflict, or materially larger semantic redesign, split it out and stop before mutating
that HMR." Independently recovered HMSR-069 and HMSR-091 plus current source show:

- HMR-058's original exact scope omits RiskApplicationService, assessment-scope commands/
  ports, scoring and approval use cases, public owner contracts and migrations. There is
  no pre-authorized assessment migration. Implementing all five obligations is materially
  larger than adding field checks to the existing record.
- createRiskAssessment creates only a DRAFT parent from optional scalar scopeId; no
  RiskAssessmentScope child is created. Scope and matrix exist as JPA-owned auxiliary
  models, but no corresponding domain/application creation path is present. The parent
  has scoring values but no selected matrix/cell provenance. RiskMatrixJpaEntity has no
  methodology binding from which an automatic matrix-selection rule can be inferred.
- Generic assessment save admits arbitrary scoring, APPROVED/ACTIVE state and later
  mutation. No actual assessment scoring or approval use case exists. Draft scoring
  fields are intentionally optional; do not require complete scoring at creation.
- Approval requires validated evidence, authenticated Identity/Workflow authority,
  reviewer/approver snapshots, actual approval time, Audit evidence and immutability.
  Existing RiskWorkflowPort/RiskAuditEventPort only expose boolean available(id); they
  cannot attest an assessment-specific approval or record approval evidence. Existing
  RiskRegisterAuditContract is creation-specific and must remain so.
- HMR-077's exact scope omits its active application addition path and owner adapters.
  RiskEvidenceLink accepts blank module/type and its JPA adapter merely saves. The
  owner-specific Risk lookup interfaces expose only available(id), with no module/type
  dispatch implementation wired to the active path.
- NoopRiskExternalEvidenceResolver always returns true; source references show only
  its declaration and interface, not a bean/caller validating the active add-evidence
  path. It must not become the implementation of owner validation.
- Existing exported Risk Organization/Topology contracts can be reused for assessment
  scopes. Identity's WorkflowActorContract and Workflow's Planning-specific orchestration
  are not Risk approval interfaces; existence-only Workflow references are insufficient.
- Assessment approval depends on validated RiskEvidenceLink while evidence links depend
  on an existing assessment. This is a lifecycle dependency, not a reason to create a
  database cross-module FK or to approve an assessment before its evidence is validated.
- Latest published migration is V20261008_003. Unexecuted V20261004_077 is backdated.

### Concrete proposal for owner acceptance

1. Preserve Batch 14's two semantic subjects, but explicitly admit execution order
   HMR-077 -> HMR-058. Existing assessments permit evidence validation first; approval
   must consume that validated boundary. Retain each source review, exhaustive exact
   write scope, tests/status and separate exact semantic commit. HMR-058's lifecycle
   work is reviewed as one governed aggregate change; no later batch is included.
2. HMR-077: require nonblank evidenceModule/evidenceType/evidenceId at the domain and
   persistence boundaries. Add a Risk-owned typed evidence registry with explicit
   owner-provided contracts. Initial supported module/type pairs resolve these existing
   source models (type tokens are the exact model names in this table):

   | Module | Evidence type |
   |---|---|
   | monitoring | MonitoringEvaluation |
   | monitoring | RiskSignal |
   | alarm | Alarm |
   | incident | Incident |
   | hse | HseCase |
   | integrity | IntegrityCase |
   | assets | MaintenanceWorkOrder |
   | simulation | SimulationRun |
   | telemetry | TelemetryReading |
   | custody | CustodyTransferTicket |
   | documents | Document |
   | audit | AuditEvent |

   Providers execute inside source owners and return scalar existence/eligibility plus
   available canonical snapshots. Unknown/ambiguous/unavailable module/type fails closed;
   explicit aliases require registration, never wildcard lookup or a permissive fallback.
   Neutral historical evidence need not be ACTIVE unless its owner already defines an
   eligibility rule. Do not invent universal source lifecycle requirements, mandatory
   snapshots or duplicate-link uniqueness. Route both application addition and JPA save
   through validation; remove/deny the always-true fallback. No foreign-owner JPA import
   into Risk and no foreign-module FK. Forward
   V20261008_004__hmr_077_risk_evidence_identity_integrity.sql rejects invalid legacy
   tuples without rewriting them and retains the existing assessment FK.
3. HMR-058 creation: accept a required nonempty structured scope list, create parent
   and authoritative child scopes atomically, and owner-resolve each supported scope.
   Initial scope vocabulary reuses existing Organization/Topology ownership: ORGANIZATION_UNIT,
   PIPELINE_SYSTEM, PIPELINE, FACILITY, EQUIPMENT. Other DDD examples fail closed until
   their exact owner contracts are admitted; do not alias STATION or infer object type
   from a bare ID. Retain optional parent scopeId as legacy convenience metadata, never
   as a substitute for child rows. Existing scalar-only callers must supply structured
   scopes; no synthetic legacy child is created. Add commit-time same-module protection
   for at least one scope and deletion/reparenting races. Invalid legacy scope absence
   requires explicit reconciliation, not inferred scope type.
4. Require exact eligible RISK_ASSESSMENT_TYPE and the repository's preserved spelling
   RISK_METHODLOGY. Populated inherent/residual likelihood/consequence/rating/confidence
   references must match their exact catalog families with local nullable FK integrity.
   Eligibility applies to new/changed use; preserve coherent historical inactive values.
   Drafts may remain unscored and validFrom <= validTo remains the admitted time rule.
5. Add an explicit matrix-scoring operation: caller selects inherent cell and optional
   residual cell from actual Risk-owned matrices; persist selected cell/matrix/version
   provenance and derive scores/ratings from those cells, never free caller numbers or
   an assumed multiplication formula. Require a same-assessment existing control/treatment
   context for residual scoring. Validate selected versions and preserve approved scoring
   provenance against cell edits, deletion or matrix reversion. Do not invent a matrix/
   methodology association absent from source; matrix choice is explicit, not inferred.
6. Add explicit submission/approval operations using Risk-specific Identity and Workflow
   owner contracts. Reuse configured Workflow transition authority and actual authenticated
   actor resolution; no caller-supplied permission sets, fabricated reviewer identity,
   automatic permission grants or universal transition matrix. Approval must attest the
   exact Risk assessment target and an actual configured approval decision, validated
   evidence and scopes, canonical reviewer/approver snapshots and actual approval time.
   No generic repository save may create/transition to APPROVED/ACTIVE.
7. Add Audit-owned RiskAssessmentAuditContract, recording sanitized scalar approval
   evidence in the same transaction as Workflow decision/assessment persistence. Return
   a real receipt for auditReferenceId. Missing authority/taxonomy/owner reference or Audit
   failure aborts the operation. Provision/reuse active EVENT_TYPE/RISK_ASSESSMENT_APPROVED
   and EVENT_CATEGORY/BUSINESS; reject conflicting/inactive taxonomy. Approved/ACTIVE
   assessments and their scopes/scoring/evidence associations are immutable in place;
   new review/revision records preserve the old evidence. No REQUIRES_NEW, fake historical
   approvals or foreign-module FKs.
8. Forward V20261008_005__hmr_058_risk_assessment_governance.sql carries local scope/
   scoring/reference/immutability guards with legacy preflight. Forward
   V20261008_006__provision_risk_assessment_audit_taxonomy.sql is Audit-owned. Published
   SQL bytes remain unchanged. Before mutation, register exhaustive paths for API/command/
   DTO/mappers, scope/scoring/approval ports and services, JPA adapters/entities/repositories,
   source-owner contracts/providers, architecture exports, required taxonomy and focused
   unit/PostgreSQL/Spring/concurrency tests. Existing API shapes are preserved additively
   where feasible; generated OpenAPI compatibility is mandatory. Do not claim closure
   based solely on field checks while governance paths remain absent.
9. Validate every admitted owner/type and denied unknown/missing references; required
   tuple/scope/catalog behavior; matrix-derived scores and residual context; authenticated
   permission/target denial; successful real Audit approval; transaction rollback; approved
   immutability and concurrent mutation/deletion. Run focused/existing/architecture,
   full Java 21 verification and OpenAPI compatibility. Publish final semantic chain once,
   confirm production CI started, stop until owner notification.

Disposition: HMR-058/HMR-077 BLOCKED pending RISK-PREREQ-01 acceptance and exact-scope
admission. Counts: 37 CI-confirmed implementations, 17 still required, three blocked
(HMR-058, HMR-077, HMR-080), 57 evaluated. This preflight changes only Ultimate Roadmap
and canonical RECONCILIATION.md. Exact supporting commit:
`docs(risk): record Batch 14 execution preflight`. Applicable CI is documentation-only.
Next action: accept/amend this concrete scope, then register exhaustive files and implement
HMR-077 followed by HMR-058; no production PASS is claimed by this preflight.


## HPR-P2-008 Batch 14 accepted execution envelope — 2026-10-08

Owner `next` accepts RISK-PREREQ-01 on d71e725cdc20d25a9c3322318f4b34107fcab51a.
CI #590 is green on d53b616de28abcd680da827e09ea7677bc7e4a31. Explicit order:
HMR-077/HMSR-091 -> HMR-058/HMSR-069, with one exact semantic commit each.
The accepted nine-part proposal governs behavior. A Risk-owned public neutral lookup
contract is implemented by owner providers inside their modules (same pattern as
WorkflowOwnedTargetLookup); Risk imports no owner-private model/repository. Scoring
provenance is an assessment-owned companion row, preserving the existing 34-field
assessment API while recording explicit cell/matrix/version and residual context.
Creation gains a structured scope list; absent legacy-only scope input fails closed.
Approval refers to an actual prior Workflow approval action as review evidence, executes
the configured final approval transition using the authenticated actor, and records Audit
in one transaction. No caller-supplied reviewer snapshot or permission grant is accepted.

### Exhaustive HMR-077 scope

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/data definition/Risk.md`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskEvidenceLink.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/RiskOwnedEvidenceLookup.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/package-info.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskEvidenceLookupPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskEvidenceLinkRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/integration/NoopRiskExternalEvidenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskEvidenceLinkSemanticRemediationTest.java`
- `src/main/resources/db/migration/V20261008_004__hmr_077_risk_evidence_identity_integrity.sql`

Exact commit: `fix(risk): remediate semantic review RiskEvidenceLink`.

### Exhaustive HMR-058 scope

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/data definition/Risk.md`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `docs/data definition/Audit.md`
- `src/main/java/dz/sh/hidra/modules/risk/domain/model/RiskAssessment.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/command/RiskAssessmentScopeInput.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/command/CreateRiskAssessmentCommand.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/command/ScoreRiskAssessmentCommand.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/command/ApproveRiskAssessmentCommand.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/in/RiskAssessmentGovernanceUseCase.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/port/out/RiskAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/risk/application/service/RiskAssessmentGovernanceService.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/request/CreateRiskAssessmentRequest.java`
- `src/main/java/dz/sh/hidra/modules/risk/api/rest/controller/RiskAssessmentGovernanceController.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskAssessmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/adapter/JpaRiskEvidenceLinkRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskAssessmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/entity/RiskAssessmentScoringJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskAssessmentScoringJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskAssessmentScopeJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskEvidenceLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskMatrixCellJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/persistence/repository/RiskMatrixJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/risk/infrastructure/integration/RiskAssessmentWorkflowTargetLookup.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/risk/RiskActorContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/RiskActorQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/risk/RiskAssessmentApprovalContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/risk/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/RiskAssessmentApprovalService.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/risk/RiskAssessmentAuditContract.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskAssessmentAuditContractAdapter.java`
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskAssessmentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/risk/infrastructure/persistence/RiskGovernancePostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/RiskAssessmentApprovalServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/application/service/RiskActorQueryServiceTest.java`
- `src/test/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskAssessmentAuditContractAdapterTest.java`
- `src/main/resources/db/migration/V20261008_005__hmr_058_risk_assessment_governance.sql`
- `src/main/resources/db/migration/V20261008_006__provision_risk_assessment_audit_taxonomy.sql`

Exact commit: `fix(risk): remediate semantic review RiskAssessment`.

Validate focused evidence/assessment/owner tests, PostgreSQL/Spring/concurrency tests,
existing Risk and architecture tests, Maven compile/test/clean verify and OpenAPI
compatibility. Report real local limitations. Publish final chain once, confirm CI
started, stop. No release change or later semantic task is admitted.


### HMR-077 implementation — owner-validated Risk evidence

HMSR-091 independently revalidated. Domain rejects blank identity components. The
public typed Risk contract is implemented inside all eleven source owners, covering
twelve exact types. Registry rejects unsupported, ambiguous, missing or mismatched
source references and uses available canonical snapshots. Application addition and
transactional JPA save both validate; legacy constructors and no-op fallback deny writes.
Forward V20261008_004 rejects invalid legacy identity without repairs or foreign FKs.
No duplicate uniqueness, mandatory snapshots or universal ACTIVE rule is introduced.
Focused JUnit tests cover required tuple, missing/ambiguous owners, snapshots and each
owner provider's exact repository dispatch. Seventeen real domain/registry harness
checks passed; Java typed compilation of actual owner entities/providers/domain passed
using dependency API stubs. These are not Maven/JUnit/Spring/PostgreSQL execution.
`bash mvnw -o -q -Dtest=RiskEvidenceLinkSemanticRemediationTest test` is blocked
before compilation by uncached Spring Boot parent 4.1.1. Java 21/real PostgreSQL and
full OpenAPI verification remain production CI obligations. HMR-077 implementation
is staged first; HMR-058 remains in progress and no production PASS is claimed.


## HPR-P2-008 Batch 14 implementation result — 2026-10-08

RISK-PREREQ-01 was accepted by owner `next`; the exact registered scopes govern both
subjects. HMSR-091 and HMSR-069 were independently re-read. The successful baseline
is CI #590 on d53b616de28abcd680da827e09ea7677bc7e4a31, followed by docs-only
preflight d71e725cdc20d25a9c3322318f4b34107fcab51a. No new batch is included.

| Subject | Implementation | Verification disposition |
|---|---|---|
| HMR-077 / RiskEvidenceLink | 93df7f22ab566d0894e00fca27dd047e1053b560; exact `fix(risk): remediate semantic review RiskEvidenceLink` | Implemented; final-head production CI pending |
| HMR-058 / RiskAssessment | This semantic commit; exact `fix(risk): remediate semantic review RiskAssessment` | Implemented; final-head production CI pending |

### Assessment obligations and enforcement

- Creation uses required nonempty structured scopes, owner-resolved through Organization
  or Topology, and persists parent/children in one transaction. Supported types are exactly
  ORGANIZATION_UNIT, PIPELINE_SYSTEM, PIPELINE, FACILITY and EQUIPMENT. Legacy scalar
  `scopeId` remains convenience metadata. The assessor snapshot is canonical Identity
  metadata; supplied assessor ID, when present, must match the authenticated principal.
- Exact eligible RISK_ASSESSMENT_TYPE and preserved RISK_METHODLOGY are checked.
  Nullable likelihood/consequence/rating/confidence use exact Risk families and local
  FKs; coherent unchanged inactive historical references remain usable.
- Explicit scoring selects actual active matrix cells and derives likelihood, consequence,
  score and rating without a universal multiplication formula. An assessment-owned
  companion row preserves inherent/residual cell, matrix and version provenance.
  Residual scoring requires an existing same-assessment CONTROL or TREATMENT_PLAN.
  No matrix/methodology association is invented; drafts may remain unscored.
- Submission is explicit DRAFT -> UNDER_REVIEW. Approval resolves the actual authenticated
  Identity actor and delegates the configured final APPROVE decision to Workflow's existing
  transition engine, which checks current actor/assignment/live configured permission.
  The instance must target this exact `risk`/`RISK_ASSESSMENT` assessment. An actual prior
  APPROVE action on that same instance supplies the reviewer evidence. Caller permission
  sets and reviewer/approver snapshots are never accepted. Workflow task/instance locks
  follow the existing engine's order. Nonfinal decisions and incoherent action evidence
  abort the transaction.
- Approval requires at least one currently owner-validated evidence link, valid scopes
  and coherent scoring provenance when populated. Audit owns sanitized scalar approval
  evidence and returns the real auditReferenceId. Workflow execution, Audit persistence
  and the assessment update join the same REQUIRED transaction. Any failure rolls back.
- Generic assessment save cannot create scope-less rows, score freely, set approval
  metadata or enter APPROVED/ACTIVE. Approved parent rows, scope/scoring/evidence
  associations, selected matrix/cell provenance and residual reference integrity are
  protected against edits/deletion/reparenting and relevant TRUNCATE bypasses.
  Revisions use new assessment rows; approved history is not rewritten.

### Forward migrations and API

V20261008_004 enforces complete evidence identity; V20261008_005 supplies same-module
assessment catalog FKs/family eligibility, deferred scope/score completeness, serialized
association guards, scoring provenance, residual context and approved immutability.
V20261008_006 provisions/reuses active Audit EVENT_TYPE/RISK_ASSESSMENT_APPROVED and
EVENT_CATEGORY/BUSINESS, rejecting inactive/conflicting taxonomy. Legacy missing scopes,
existing scoring without genuine provenance and existing approvals requiring historical
owner reconciliation abort migration; no inferred child, score, approval or data repair
is performed. Published migration bytes and cross-module FK policy remain unchanged.

Creation adds `scopes` to the existing request/command with source-compatible legacy
constructors, which yield an empty list and fail closed at execution. The existing assessment response and
34-field domain shape are preserved. New explicit endpoints have stable
operation IDs: POST /api/v1/risk/assessments/{id}/score, /submit and /approve. Existing
MapStruct record mapping carries the same structured scope type. Full generated OpenAPI
compatibility remains a CI gate. Configure an active owner-managed Workflow target type
RISK_ASSESSMENT, binding/purpose, review/final approval route, assignments and permission
using the existing Workflow administration boundary before exercising approval. This
change supplies no automatic permission grant, workflow definition or fabricated action.

### Actual local validation and limits

- 17 evidence domain/registry harness checks passed; 27 assessment aggregate behavior
  checks passed, including missing scopes/catalogs, owner snapshots, cell-derived scoring,
  residual-context denial, submission, evidence-before-approval, actual owner metadata/
  receipt handling, Audit failure preventing parent save and approved mutation denial.
  Harness owners/repositories are controlled in-memory substitutes; they do not prove
  Spring transaction rollback or PostgreSQL enforcement.
- Actual changed production/domain/owner Java and five focused JUnit test sources type
  compiled with temporary framework/dependency API stubs on available JDK 17. Syntax
  parsing and whitespace/exhaustive scope checks passed. This is not Java 21 Maven/JUnit
  execution and does not validate framework behavior.
- Focused JUnit tests cover each evidence owner/type's missing and present dispatch,
  scopes/catalogs/scoring/approval, Identity eligibility/canonical metadata, Workflow
  exact target/review/permission denial/final-action coherence, and Audit sanitation.
  Ten PostgreSQL/Testcontainers cases were added for migration/legacy/catalog/score/
  immutability, residual context, scope deletion races, waiting evidence/cell mutations,
  and real Spring/JPA assessment+Audit commit/rollback. The transactional Workflow-owner
  receipt in the isolated aggregate test is explicitly a fixture; configured Workflow
  execution is covered separately through the owner/core tests. These tests are not
  claimed as locally executed because Docker/PostgreSQL are unavailable.
- Local Maven compile, focused tests, full tests and clean verify were all attempted
  offline and blocked before compilation by uncached Spring Boot parent 4.1.1. P1 closure
  evidence validation passed; infrastructure/version/release artifacts are unchanged.
  Exact-head CI must run real Java 21, PostgreSQL/Spring, architecture and OpenAPI gates.

Current totals: 39 implementations (37 CI-confirmed through #590; two Batch 14 CI-pending),
17 STILL REQUIRED, one BLOCKED (HMR-080), 57 evaluated. Publish the chained semantic
commits to existing main once using expected-head protection; confirm production CI
started and stop for owner `next`/`fail`. No release, tag, PR or final HPR-P2-008 PASS.


## HPR-P2-008 Batch 14 / CI #591 repair admission — 2026-10-08

Owner `fail` selects repair only. Actual CI #591 / run 37768160388 on
76dbbd721822e3e8e109d02c0b82a4ed2d6e9285 ran 955 tests: one failure, two errors,
zero skipped. P1/runtime/HA/PITR/release/observability/database procedure gates passed.
The mapper contract fixture cannot instantiate java.util.List introduced by structured
assessment scopes (HMR-058). Both Spring context errors stem from eleven owner evidence
adapters sharing implicit bean name riskEvidenceQueryAdapter (HMR-077). OpenAPI was
skipped after test failure. No migration/scoring/evidence/approval test failure is reported.

Repair: explicitly owner-qualify all eleven evidence provider bean names; preserve typed
registry/list injection and all owner semantics. Extend the exact mapper fixture to construct
nonempty parameterized lists and nested records, retaining every component/method-count
assertion and rejecting unsupported types. Add provider bean-name uniqueness regression
to the existing focused evidence test. No new semantic subject, migration, API shape,
authority/lifecycle policy, release or later batch is admitted.

Exhaustive repair scope:

- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/documents/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/audit/infrastructure/integration/RiskEvidenceQueryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/GeneratedBoundaryMapperContractTest.java`
- `src/test/java/dz/sh/hidra/modules/risk/semantic/RiskEvidenceLinkSemanticRemediationTest.java`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`

Exact supporting commit: `fix(risk): repair Batch 14 CI integration`.
Validate focused mapper/evidence checks, canonical docs, Java syntax/type and exact
scope/whitespace. Attempt real Maven tests; state local dependency limitations. Publish
on existing main with expected-SHA protection, confirm replacement CI started, stop.
Batch 14 remains CI-pending; no new batch may start before the repaired head is green.


### CI #591 repair result

All eleven evidence provider classes now use explicit owner-prefixed Spring component
names; the typed provider registry is unchanged. The mapper fixture constructs two
distinct populated scope records using generic type metadata. Existing exact component
equality and 114-mapping coverage assertions remain intact; empty/dropped/reordered
lists and unsupported raw/wildcard/Set types are not silently accepted. The focused
evidence test now asserts explicit nonblank distinct bean names for every owner.
Twenty checks on the actual repaired fixture/provider annotations passed, including
nonempty/distinct scope generation, exact component equality, changed-list rejection,
unsupported-type denial and eleven owner-specific component names. Actual production
and focused test Java type/syntax checks passed with temporary dependency API stubs.
These checks are not a full Spring context or generated MapStruct execution.
Focused Maven mapper/evidence/application/authentication tests were attempted offline
and remain blocked before compilation by uncached Boot parent 4.1.1. Canonical Markdown,
whitespace and the exact fifteen-file scope passed. No new files, published SQL edits,
API/lifecycle changes, release or later batch. Confirm replacement exact-head CI started
and stop; Batch 14 is implementation-only until all production gates are green.


## HPR-P2-008 Batch 14 / CI #592 compatibility-base repair — 2026-10-08

Owner `fail` authorizes this supporting repair only. Actual run 37769772026 on
aa0539ea672ff4e29f01872026d727aef067d3e1 passed Java 21 Maven clean verify and
current-head OpenAPI generation. The failed step is historical-base OpenAPI generation:
GitHub push event.before selected 76dbbd721822e3e8e109d02c0b82a4ed2d6e9285, which
still contains the bean collision already corrected on current main. Compatibility
comparison therefore never ran. HMR-077/058 remain CI-pending until the full gate passes.

Repair push/manual CI base selection using successful completed production CI runs
for .github/workflows/ci.yml on main, requiring the candidate SHA to be an ancestor of
the requested event base. Ignore documentation runs, failures, foreign branches and
non-ancestors. Latest successful applicable history identifies CI #590 /
d53b616de28abcd680da827e09ea7677bc7e4a31, before Batch 14. Preserve PR exact target-base
behavior. Missing/invalid base, GitHub lookup failure or absence of a verified ancestor
fails closed; never substitute current HEAD, patch historical source, or skip comparison.
Continue generating the genuine historical application and running the unchanged OpenAPI
compatibility checker. Add Actions read permission only for provenance lookup.

Exhaustive scope:

- `.github/workflows/ci.yml`
- `.github/scripts/resolve_openapi_base.py`
- `.github/scripts/test_resolve_openapi_base.py`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`

Exact supporting message: `fix(ci): select a verified OpenAPI compatibility base`.
Validate resolver behavior with real temporary Git ancestry and controlled GitHub history,
workflow YAML/shell syntax, unchanged comparator, canonical docs and exact five-file scope.
No production Java, Flyway, API contract, release, PR or next batch is included. Publish
main with expected-SHA protection, confirm new production CI triggered, stop.


### CI #592 repair result

The resolver now obtains completed successful production-CI history using read-only
Actions access and verifies candidate ancestry against the requested push/manual base.
PR comparison retains its exact target SHA. Fourteen Python regressions passed using
real temporary Git graphs and controlled history, including broken-parent fallback,
verified-parent preservation, exclusion of docs/foreign/current-head runs, pagination,
invalid/missing history and lookup failure, exact PR behavior and GitHub output. A
separate check using actual retrieved GitHub success history and actual repository Git
ancestry selected d53b616de28abcd680da827e09ea7677bc7e4a31 / CI #590 for this repair.
Python compilation, workflow YAML and all embedded Bash syntax passed. Every workflow
step except base resolution is byte-for-byte structurally unchanged, including genuine
historical application generation and the existing backward-compatibility comparator.
Canonical docs, whitespace and exact five-file scope passed. Production Java/Flyway/API
bytes are unchanged; local Maven rerun is unnecessary for this Python/workflow-only
repair, and current-head Java 21 clean verify already passed in actual CI #592.
Historical application generation and compatibility execution with the selected base
remain replacement-CI gates; no completed green run or Batch 14 closure is claimed.
Publish this supporting commit on main, confirm new CI triggered, stop for owner
notification. No later HMR, PR, tag or release is included.

## HPR-P2-008 Batch 15 Incident execution preflight — 2026-10-08

Owner `next` selects row 15 of attached `00 - Batchs Roadmap.txt`: HMR-062/HMSR-074,
HMR-090/HMSR-107, HMR-091/HMSR-108 and HMR-092/HMSR-109. Exact main baseline
`cfc7798477c70d10e1c3e0afd4dd7e1b42676898` passed full CI #593 / run
`37772142318`. Job `Java 21 Maven verification` passed repository verification,
current and historical OpenAPI generation, backward compatibility and artifact upload,
as well as all registered infrastructure checks. Batch 14 HMR-077/058 and both CI
repairs are now CI-confirmed. This task is a documentation-only preflight.

### INC-PREREQ-01 — independently recovered live gaps

AGENTS.md section 3.2.9 requires: "If an HMR reveals an unregistered prerequisite, SCC
complication, owner-contract gap, cross-module lifecycle dependency, migration-order
conflict, or materially larger semantic redesign, split it out and stop before mutating
that HMR." Current source and each of the four individual source reviews establish:

| Subject | Live evidence and scope gap |
|---|---|
| HMR-062 / Incident | `IncidentApplicationService.openIncident` copies caller creator/catalog/asset/unit data. `Incident` checks presence but not detected/reported ordering or resolved/closed state coupling. `JpaIncidentRepositoryAdapter.save` directly maps/saves. No Incident-specific Identity, Organization, Topology or Workflow owner contract is exported in `ArchitectureGuardrailTest`. Original scope does not admit those owners or a migration. |
| HMR-090 / IncidentClosure | `closeIncident` creates/saves a closure without loading the parent, validating RESOLVED, changing CLOSED/closedAt, checking resolution/evidence or declaring a coherent transaction. Its original scope excludes that live application service, parent locking and resolution/evidence lookup. `IncidentResolutionJpaRepository` and `IncidentEvidenceLinkJpaRepository` expose only generic JpaRepository methods. No pre-authorized closure migration exists. |
| HMR-091 / IncidentRelatedIncident | `fk_hra111_incident_013` in published V20260929_001 incorrectly references `hidra_incident_catalog_entry`; both identifiers must reference `hidra_incident`. Domain `selfRelationship()` reports but does not prohibit self-links; createdAt is unchecked. Adapter directly saves without family or pair policy. Legacy V20261004_091 is backdated behind published V20261008_006. |
| HMR-092 / IncidentResponseAction | `recordResponseAction` directly saves without loading Incident or invoking `canReceiveResponseAction()`. Adapter has no catalog/lifecycle validation. Nonblank description is not enforced. Original scope excludes the service, parent lock and transaction protection needed against a concurrent close. |

`NoopIncidentExternalReferenceResolver` returns true for every lookup, including typed
Topology and Workflow existence. It is not evidence of owner validation and must not
be wired as the implementation of any admitted authoritative path. Source review of
`IncidentModuleConfiguration.defaults()` finds a generic evidence-required boolean,
not an executable classification/severity policy or Workflow approval attestation.

The Incident DDD (`docs/data definition/Incident.md`, sections 4, 6.8, 6.9 and 6.11)
requires resolved-only closure, a resolution, reviewed evidence and responsible-owner
snapshot, with additional evidence/approval conditions determined by policy. Current
`IncidentCatalogEntryJpaEntity` has only family/code/active/order/system/timestamps;
no minor-severity, closure-approval or inverse-direction metadata can be inferred.
Do not interpret an arbitrary severity code or ID as an approved threshold, require
universal true RCA/follow-up flags, or invent bidirectional relationship semantics.

### Concrete execution proposal for acceptance

1. Admit Batch 15 as the coordinated Incident aggregate correction in order
   HMR-062 -> HMR-091 -> HMR-092 -> HMR-090. Closure executes last after validated parent
   persistence and response-action serialization exist. Retain four source reviews,
   individual exact scopes, tests, statuses and separate exact commits:

   | HMR | Exact semantic commit |
   |---|---|
   | HMR-062 | `fix(incident): remediate semantic review Incident` |
   | HMR-091 | `fix(incident): remediate semantic review IncidentRelatedIncident` |
   | HMR-092 | `fix(incident): remediate semantic review IncidentResponseAction` |
   | HMR-090 | `fix(incident): remediate semantic review IncidentClosure` |

2. HMR-062 preserves the 37-field aggregate and enums. Enforce exact active families
   INCIDENT_CLASSIFICATION, INCIDENT_SEVERITY and populated INCIDENT_PRIORITY for new
   or changed references; preserve valid historical snapshots without treating them as
   authorization. Enforce detectedAt <= reportedAt (there is no estimated-time flag),
   resolvedAt only for RESOLVED/CLOSED, closedAt only for CLOSED and the required
   responsible-owner snapshot for CLOSED. Preserve the DDD cancelledAt/CANCELLED coupling
   and CLOSED immutability; do not invent other timestamp ordering. Creator identity
   binds to the actual authenticated eligible Identity actor, never caller snapshots.
   Validate populated responsible actor/unit, typed Topology and Workflow references
   through narrowly exported owner contracts with canonical scalar snapshots. Unknown,
   missing or ambiguous owner/type fails closed. No foreign-module JPA import or FK.
   Guard application and adapter saves; generic save must not bypass closure governance.
3. Introduce Incident-specific owner interfaces under each owner's
   `application.contract.incident` with providers executing inside Identity, Organization,
   Topology and Workflow. Reuse owner-controlled queries/policies internally rather than
   exporting foreign domain/JPA objects. Initial typed assets are PIPELINE, SEGMENT,
   FACILITY, EQUIPMENT, NODE and CONNECTION, following existing Topology ownership;
   do not silently alias STATION or accept unsupported neutral type strings. Workflow
   existence alone never proves permission, target correlation or a closure decision.
   Architecture exports and focused owner-contract/denial tests must be explicitly scoped.
4. HMR-091 uses forward V20261008_008 to replace only the erroneous related-side FK,
   preserving the correct incident/type FKs. Fail legacy preflight on orphan/self-link/
   invalid catalog/timestamp rows without deleting or rewriting evidence. Enforce
   non-self IDs, createdAt and active RELATED_INCIDENT_RELATIONSHIP_TYPE for writes.
   Register an explicit Incident-owned relationship policy keyed by catalog type,
   defining directional versus symmetric semantics and any reciprocal type; absent or
   conflicting configuration rejects creation. Symmetric links use one canonical pair
   and reject a reverse duplicate. Directional links reject exact duplicates and
   explicitly configured reciprocal duplicates; never collapse unrelated PARENT/CHILD
   or MERGED_INTO relationships without a configured rule. Lock parent pairs in stable
   identifier order and enforce admitted database uniqueness for races. Existing
   historical conflicting pairs require operator reconciliation, not automatic merging.
5. HMR-092 loads/locks the actual parent and rejects missing/DRAFT/CLOSED/CANCELLED/MERGED
   according to `canReceiveResponseAction()`. Enforce nonblank description and active
   RESPONSE_ACTION_TYPE. Serialize action insertion against closure using the same parent
   lock/transaction boundary at application and persistence paths. Preserve optional
   timestamps/outcomes and neutral targets; no invented terminal-result requirement.
   Validate populated Organization/Identity references through their owner contracts.
   Recording never issues physical equipment commands. Forward V20261008_009 adds
   same-module/catalog and lifecycle guards with fail-closed legacy preflight.
6. HMR-090 closes only a locked RESOLVED Incident with a valid resolution record and
   policy-required persisted evidence. Require nonblank closureSummary, resolutionVerified
   and evidenceReviewed, eligible authenticated closing actor and responsible-owner
   snapshot. Define an explicit Incident-owned closure policy keyed by classification
   and severity with evidence and Workflow-approval requirements; no default permissive
   policy and no invented minor threshold. Missing policy fails closed. When configured,
   require Workflow-owned authority for this exact Incident/closure target and actual
   recorded approval; reject mismatched/unused existence-only references. Root-cause and
   follow-up requirements are driven by resolution/policy evidence, not blanket booleans.
   Save the immutable closure and CLOSED parent/closedAt in one REQUIRED transaction.
   Prevent duplicate closure, replay, generic-save bypass and concurrent action/close;
   failure rolls back both records and any Workflow operation performed in that transaction.
   No fabricated resolution/evidence/approval or automatic permission grants.
7. Authorize forward filenames, never change published SQL:

   | Owner task | Proposed forward migration |
   |---|---|
   | HMR-062 | `V20261008_007__hmr_062_incident_reference_lifecycle_integrity.sql` |
   | HMR-091 | `V20261008_008__hmr_091_incident_relationship_integrity.sql` |
   | HMR-092 | `V20261008_009__hmr_092_incident_response_action_integrity.sql` |
   | HMR-090 | `V20261008_010__hmr_090_incident_closure_governance.sql` |

   Companion local policy/evidence tables may be introduced with these forward migrations
   where required; no synthesized legacy policies or data repair by inference. Policy
   metadata must have explicit family/active/consistency constraints, and missing policy
   remains a clear denial. Recheck the actual migration tail before admission.
8. Before production mutation, register exhaustive per-HMR paths covering the original
   subject files plus IncidentApplicationService, domain/persistence/catalog validation,
   parent lock APIs, same-module resolution/evidence repositories and ports, policy
   persistence, required owner contracts/providers, architecture exports, API/command/mapper
   paths actually changed and focused tests. Preserve existing OpenAPI shapes where feasible;
   any additive operation has an explicit use case and contract. No later batch, release,
   version change, PR or cross-module FK is included.
9. Validate each HMSR independently: wrong/inactive family and missing/unsupported owner;
   temporal/status invariants and snapshot preservation; correct related-side FK, self-link,
   missing time and pair/inverse races; forbidden parent action states; absent resolution/
   evidence/policy, false confirmations and wrong Workflow actor/target; successful closure
   with identical parent/record close time; rollback, duplicate close, closed immutability
   and concurrent close/action. Use unit/owner/architecture tests plus real PostgreSQL
   integration/transaction/concurrency tests. Run registered compile/focused/full tests,
   Java 21 clean verify and generated OpenAPI compatibility. Publish the four-commit chain
   once; confirm final-head CI started and stop for owner notification.

### Current disposition and validation

All four Batch 15 HMRs are BLOCKED pending INC-PREREQ-01 acceptance and exhaustive
scope registration; no Incident production code has changed. Totals: 39 CI-confirmed
implementations, 13 STILL REQUIRED and five BLOCKED (HMR-062/090/091/092/080), 57 evaluated.
HMR-080's independent Party prerequisite remains unchanged.

This preflight's exact write scope is only `doc/roadmap/ULTIMATE_ROADMAP.md` and
`doc/model-remediation/RECONCILIATION.md`. Exact supporting commit:
`docs(incident): record Batch 15 execution preflight`.

Validation: canonical Markdown UTF-8/nonempty/conflict-marker validation and
`git diff --check`; no Maven/runtime/database test is claimed for this documentation
change. Applicable push CI is Documentation Validation; production CI #593 remains the
verified source baseline. Stop after documentation CI is triggered. Next registered
action: accept/amend INC-PREREQ-01, register exact paths and implement Batch 15 in the
order above. No semantic completion or full HPR-P2-008 PASS is claimed by this preflight.

## HPR-P2-008 Batch 15 accepted execution envelope — 2026-10-08

Owner `next` accepts INC-PREREQ-01 on fde6cbf001c5fac9575f5a30ec01c414304d9ede.
Documentation CI #94 (37773681973) passed. The production tree is unchanged from
green Java 21/full/OpenAPI CI #593 at cfc7798477c70d10e1c3e0afd4dd7e1b42676898.
Admit order HMR-062 -> HMR-091 -> HMR-092 -> HMR-090, four independent exact semantic
commits with one atomic final branch advance. No later batch or PR. The accepted
preflight governs policy configuration, missing-policy denial, owner contracts and
forward migrations 007..010. No production completion is claimed at admission.

### Exhaustive HMR-062 scope

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/data definition/Incident.md`
- `src/main/java/dz/sh/hidra/modules/incident/application/service/IncidentApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentReferencePolicyPort.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/incident/domain/model/Incident.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/integration/NoopIncidentExternalReferenceResolver.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/integration/IncidentReferencePolicyAdapter.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/IncidentCatalogValidation.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/incident/IncidentActorContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/incident/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/IncidentActorQueryService.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/IncidentActorContractTest.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/incident/IncidentOrganizationContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/incident/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/IncidentOrganizationQueryService.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/IncidentOrganizationContractTest.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/incident/IncidentTopologyContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/incident/package-info.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/integration/IncidentTopologyQueryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/topology/semantic/IncidentTopologyContractTest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/incident/IncidentWorkflowContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/incident/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/IncidentWorkflowQueryService.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/IncidentWorkflowContractTest.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/incident/infrastructure/persistence/IncidentSemanticPostgresIntegrationTest.java`
- `src/main/resources/db/migration/V20261008_007__hmr_062_incident_reference_lifecycle_integrity.sql`

### Exhaustive HMR-091 scope

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/data definition/Incident.md`
- `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentRelatedIncident.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentRelatedIncidentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentRelatedIncidentJpaRepository.java`
- `src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentRelatedIncidentSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/incident/infrastructure/persistence/IncidentSemanticPostgresIntegrationTest.java`
- `src/main/resources/db/migration/V20261008_008__hmr_091_incident_relationship_integrity.sql`

### Exhaustive HMR-092 scope

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/data definition/Incident.md`
- `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentResponseAction.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/service/IncidentApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentResponseActionRepositoryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentResponseActionSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/incident/infrastructure/persistence/IncidentSemanticPostgresIntegrationTest.java`
- `src/main/resources/db/migration/V20261008_009__hmr_092_incident_response_action_integrity.sql`

### Exhaustive HMR-090 scope

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/data definition/Incident.md`
- `src/main/java/dz/sh/hidra/modules/incident/domain/model/IncidentClosure.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/service/IncidentApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/incident/application/port/out/IncidentClosureEvidencePort.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentClosureEvidenceAdapter.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/adapter/JpaIncidentClosureRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/incident/infrastructure/persistence/repository/IncidentClosureJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/IncidentWorkflowQueryService.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/IncidentWorkflowContractTest.java`
- `src/test/java/dz/sh/hidra/modules/incident/semantic/IncidentClosureSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/incident/infrastructure/persistence/IncidentSemanticPostgresIntegrationTest.java`
- `src/main/resources/db/migration/V20261008_010__hmr_090_incident_closure_governance.sql`

Each task recovers its own source HMSR review and validates focused tests. Production
CI owns actual Java 21 Maven/PostgreSQL/Spring/OpenAPI validation when this host cannot
resolve the build parent. Temporary API compilation is not a Maven or integration pass.

### Batch 15 HMR-062 implementation result — 2026-10-08

Catalog families and active new-reference eligibility, owner-controlled Identity/Organization/Topology/Workflow references, authenticated creator and canonical creation snapshots, temporal/state coupling and responsible-owner snapshot for CLOSED are enforced. Generic saves cannot establish CLOSED and existing CLOSED parents are immutable. A parent pessimistic lock is exported for coordinated lifecycle writes. Noop reference resolver now denies. Forward 007 preserves existing FKs and adds nullable priority integrity and local catalog/state guards. Production sources compiled against temporary framework APIs; eight real domain/fallback checks passed. Nine focused owner/domain methods and two real PostgreSQL tests are prepared. Local Maven compilation is blocked before execution by uncached Spring Boot 4.1.1 parent and offline resolution; no JUnit/database pass is claimed.

Exact semantic commit: `fix(incident): remediate semantic review Incident`.
Implementation complete; production Java 21/Maven/PostgreSQL/OpenAPI CI pending.

### Batch 15 HMR-091 implementation result — 2026-10-08

Forward 008 corrects only the related-side HRA-111 FK to Incident. Domain rejects normalized self-links and missing creation time. Exact active relationship catalog and explicit direction/reciprocal policy are required; no catalog-code heuristics or seeded policy. Symmetric pairs canonicalize; exact/configured inverse duplicates reject under stable parent locking and database uniqueness. Relationship evidence is append-only and used policy is immutable. Legacy invalid references, direction or duplicate evidence fail preflight without rewrite. Production sources compiled against temporary framework APIs; three real domain checks passed. Three focused methods and five additional PostgreSQL tests (including concurrent inverse insertion) prepared; database/runtime execution remains CI obligation.

Exact semantic commit: `fix(incident): remediate semantic review IncidentRelatedIncident`.
Implementation complete; production Java 21/Maven/PostgreSQL/OpenAPI CI pending.

### Batch 15 HMR-092 implementation result — 2026-10-08

The application and adapter lock and validate the real Incident parent before response writes, rejecting DRAFT/CLOSED/CANCELLED/MERGED and missing parents. Nonblank description and exact active RESPONSE_ACTION_TYPE are enforced. Optional performer/unit references resolve through their owners; performer snapshot is canonical. Target/timestamp/result optionality is preserved and no equipment command is issued. Forward 009 serializes database inserts/updates with parent closure and validates local catalog/description integrity without rewriting legacy records. Sources compiled against temporary framework APIs. Three dedicated unit methods and two additional PostgreSQL tests prepared; actual Maven/PostgreSQL execution remains pending CI.

Exact semantic commit: `fix(incident): remediate semantic review IncidentResponseAction`.
Implementation complete; production Java 21/Maven/PostgreSQL/OpenAPI CI pending.

### Batch 15 HMR-090 implementation result — 2026-10-08

Formal closure validates locked RESOLVED parent, exactly one coherent persisted resolution, reviewed evidence and explicit classification/severity policy. Required RCA and corrective/preventive follow-up rules consume real rows and flags; no minor-severity heuristics or automatic policy seeds. Eligible authenticated closer and canonical snapshot replace caller identity. Optional/required Workflow approval attests exact Incident and INCIDENT_CLOSURE purpose, configured binding, actual final APPROVE action and completed approving task. Immutable closure and CLOSED parent share a server-owned microsecond timestamp in one REQUIRED transaction. Deferred database consistency, duplicate prevention, historical policy/evidence immutability and truncate denial prevent generic-save bypass and close/action races. Transactional services remain proxyable. Sources and focused test signatures compiled against temporary APIs; 19 actual domain/application behavior checks passed with controlled repository fixtures. Five closure and two Workflow unit methods plus eight PostgreSQL/JPA/concurrency methods prepared (17 integration methods total). Local Maven compile, focused tests, full tests and clean verify all stopped before execution at uncached Boot 4.1.1 offline parent resolution. Real Java 21/Spring/PostgreSQL/OpenAPI verification remains final-head CI obligation.

Exact semantic commit: `fix(incident): remediate semantic review IncidentClosure`.
Implementation complete; production Java 21/Maven/PostgreSQL/OpenAPI CI pending.

## HPR-P2-008 Batch 15 final implementation disposition — 2026-10-08

Accepted INC-PREREQ-01 now has four separate semantic implementations in the admitted
order HMR-062 -> HMR-091 -> HMR-092 -> HMR-090. All original source HMSR obligations
remain independently traceable. Exhaustive scopes and four forward migrations 007..010
are recorded in the accepted envelope. No foreign-module relational FK is introduced.

| Task | Semantic commit / result |
|---|---|
| HMR-062 / Incident | 0697a4e644bf3f965eb38b925b4a1162e4cae619; owner references/catalog/state invariants implemented |
| HMR-091 / IncidentRelatedIncident | cdec336bcbf67567da8ca99833986639d2a2e0db; corrected FK and explicit pair policy implemented |
| HMR-092 / IncidentResponseAction | 4395aaf1753504b166c15086c290e04845cf9fcf; locked parent lifecycle and action eligibility implemented |
| HMR-090 / IncidentClosure | This exact semantic commit; atomic closure/parent/evidence governance implemented |

Local validation: changed production and dedicated unit signatures compile using real
repository/domain source and temporary framework APIs; 19 actual domain/application
behavior checks pass with controlled repository fixtures; Java syntax parsing, exact
per-task scope checks, published-migration byte preservation and canonical Markdown
validation plus git diff --check are required before publication. Eight owner/domain
unit classes and 17 real PostgreSQL integration methods cover the admitted subjects,
including concurrent inverse links, duplicate closure, late response denial and real
JPA commit/rollback. Those JUnit/PostgreSQL/Spring tests have NOT run locally: all four
Maven targets (compile, focused, test, clean verify), invoked via bash ./mvnw -o because
the checkout wrapper is not executable, fail before test execution at the uncached
Spring Boot 4.1.1 parent. This host has Java 17 and no Docker/PostgreSQL tooling.

Deployment limitation: relationship and closure policy tables intentionally have no
synthetic defaults. Operators must approve exact family/direction and classification/
severity policy rows before the affected operations are enabled. Missing policy denies
the operation. Legacy invalid references, missing policy for existing formal closure,
and incoherent evidence fail migration preflight; no automatic data rewrite is claimed.
Workflow must have a real configured INCIDENT target and INCIDENT_CLOSURE purpose/binding
and a completed final approval by the eligible closer when supplied/required.

Current totals: 43 implementations (39 CI-confirmed, four Batch 15 CI pending),
13 STILL REQUIRED, one BLOCKED (HMR-080), 57 evaluated. Publish this four-commit chain
on existing main atomically and confirm final full CI started, then stop until owner
next/fail. Do not mark semantic verification closed until that run is green. No PR,
release, tag, version change, HPR-P2-008 final PASS or Batch 16 implementation is included.
Next attached batch, after green CI and owner next: HMR-069/070/072, with fresh admission.

Final Batch 15 local checks completed: 82 canonical Markdown files validated; all
119 published migration files unchanged byte-for-byte; 42 changed Java files parsed;
both explicit architecture export registries, canonical headers, transactional
proxyability and each exact write scope validated; git diff --check passed. Prepared
22 dedicated unit methods and 17 PostgreSQL integration methods. The initial HMR-062
unit set contained ten methods (the earlier nine-method summary undercounted it).
No prepared test is represented as an executed Maven/JUnit/PostgreSQL pass.

## HPR-P2-008 Batch 15 CI #594 repair admission — 2026-10-08

CI run 37777352374 on 8d7b73ef07074379f2ceb6336938ee6f858e3fe4 failed only
InternalReferenceIntegrityMigrationTest.installsAndValidatesEveryClassifiedSameModuleForeignKey:
995 tests, one failure, zero errors/skips; inventory expected 551 but observed 550.
HMR-091 forward 008 correctly replaces fk_hra111_incident_013 with
fk_hmr091_related_incident (related_incident_id -> hidra_incident.id), but the
inventory test includes only the three earlier Reporting replacements. Admit a
supporting repair under AGENTS.md section 3.2.8: include the Incident replacement,
verify its exact owner/table endpoints and validation, and assert the superseded FK
is absent. Preserve the 551 historical obligations and every published migration.

Exact supporting commit: `test(incident): reconcile HMR-091 foreign key inventory`.
Exhaustive write scope:
- `src/test/java/dz/sh/hidra/InternalReferenceIntegrityMigrationTest.java`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`

Validation target: `bash ./mvnw -o -B -q -Dtest=InternalReferenceIntegrityMigrationTest test`,
Java syntax parsing, replacement inventory/source checks and git diff --check.
No production change, new migration, PR, next batch or final PASS is authorized.
Replacement CI must be triggered on main, then execution pauses for owner next/fail.

Repair validation completed: Java syntax parsing and four-replacement inventory/forward
SQL endpoint checks passed; the historical 551 expectation remains unchanged and no
migration file changed. Focused Maven test invocation stopped before execution because
the Spring Boot 4.1.1 parent is uncached in offline mode. CI #594 did execute 995 tests
with one inventory failure; that result does not establish repaired-head success.
Publication triggers replacement production CI; Batch 15 remains CI pending until
the repaired head passes. No subsequent batch is started.

## HPR-P2-008 Batch 16 execution preflight — 2026-10-08

Owner `next` selects HMR-069/HMSR-081, HMR-070/HMSR-082 and HMR-072/HMSR-085.
Exact current main `e2e92bae7d69c54a46fa92702b539858404bf7ce` passed full CI #595
(run 37779319871). Java 21 repository verification, current and historical OpenAPI
generation, backward compatibility, upload and all P1 artifact checks passed.
Documentation CI #96 (37779319926) also passed. This closes the Batch 15 CI gate:
HMR-062/090/091/092 are now CI-confirmed, including the HMR-091 inventory repair.
This task performs a documentation-only preflight; production source remains unchanged.

### WORK-PREREQ-01 — live owner-contract and migration-order gaps

AGENTS.md section 3.2.9 requires: "If an HMR reveals an unregistered prerequisite, SCC
complication, owner-contract gap, cross-module lifecycle dependency, migration-order
conflict, or materially larger semantic redesign, split it out and stop before mutating
that HMR." Each recovered HMSR and current source establish these concrete gaps:

| Task / review | Live defect and scope gap |
|---|---|
| HMR-069 / HMSR-081 | `MaintenanceWorkOrder` normalizes blank title to null; `AssetsApplicationService.createMaintenanceWorkOrder` copies plan/recommendation/assignment/creator/Workflow IDs. `JpaMaintenanceWorkOrderRepositoryAdapter.save` maps directly to save. No optional maintenance-plan FK exists. The original write scope omits Identity, Integrity and Workflow owner interfaces/providers and both architecture export registries. |
| HMR-070 / HMSR-082 | `CustodyApplicationService.createTransferTicket` accepts optional batch/calculation, issuer and Workflow IDs; `JpaCustodyTransferTicketRepositoryAdapter.save` directly saves arbitrary populated approval/Audit references. Optional batch/calculation FKs and Custody-specific Identity/Workflow/Audit contracts are absent from current source and the original write scope. Creation currently leaves Audit and approver fields null; no fabricated approval or Audit record may fill them. |
| HMR-072 / HMSR-085 | `IntegrityApplicationService.createIntegrityAssessment` copies optional programme, assessor and Workflow IDs. `JpaIntegrityAssessmentRepositoryAdapter.save` maps directly to save. Optional programme FK and Integrity-specific Identity/Workflow context contracts are absent from the original write scope. Reviewer/approver/Audit creation fields remain null; HMSR-085 does not establish a current Audit-population defect. |

Existing `AssetsOrganizationUnitReferenceContract.exists` and its Organization-owned
`AssetsOrganizationUnitReferenceQueryService` can serve HMR-069's unit existence
obligation without a new Organization contract. Existing Identity Workflow-actor policy
can be reused internally by new Identity-owned providers, but its Workflow-specific
export must not become a client-owned actor implementation. Existing Workflow queries
and `WorkflowConfigurationPort` are owner internals, not exported client context
attestation. `IntegrityAssetsRecommendationPort` is an Integrity outbound port; it does
not attest Integrity-owned recommendation provenance to Assets. No foreign JPA import,
permissive Noop or raw cross-module SQL is an acceptable replacement for these owners.

Migration tail is published `V20261008_010__hmr_090_incident_closure_governance.sql`.
The legacy V20261004_069/070/072 names are backdated and absent. Authorize forward
011/012/013 below before implementation; never alter published migrations or enable
out-of-order migration to conceal the ordering conflict.

### Concrete Batch 16 execution proposal for acceptance

1. Admit three independent task commits in attached order HMR-069 -> HMR-070 -> HMR-072,
   retaining separate reviews, scopes, migrations, validation results and statuses.
   This envelope authorizes reference-integrity corrections, not new business approval
   orchestration or lifecycle redesign. Exact semantic messages remain:

   | Task | Exact semantic message | Forward migration |
   |---|---|---|
   | HMR-069 | `fix(assets): remediate semantic review MaintenanceWorkOrder` | `V20261008_011__hmr_069_assets_maintenance_work_order.sql` |
   | HMR-070 | `fix(custody): remediate semantic review CustodyTransferTicket` | `V20261008_012__hmr_070_custody_custody_transfer_ticket.sql` |
   | HMR-072 | `fix(integrity): remediate semantic review IntegrityAssessment` | `V20261008_013__hmr_072_integrity_integrity_assessment.sql` |

2. HMR-069 rejects null/blank title before persistence and preserves optional description,
   assignment, priority and timestamps. Add nullable maintenance_plan_id -> Assets-owned
   maintenance plan FK with validated legacy preflight and ON DELETE RESTRICT. Preserve
   existing maintained-asset and generic work-order-type FKs. Resolve populated Integrity
   recommendation through an Integrity-owned scalar contract, unit through the existing
   Organization contract and actors through Identity. Require Workflow owner attestation
   of the exact work-order ID/module/type and a valid definition target binding when
   a reference is populated. No invented plan/asset correlation, timestamp ordering,
   work-order-number uniqueness or unsupported catalog-family rule.
3. HMR-070 adds nullable local batch and quantity-calculation FKs with fail-closed legacy
   validation and ON DELETE RESTRICT. Validate populated issuer/approver through Identity;
   validate populated Workflow through its owner against the exact ticket context. Resolve
   populated auditReferenceId through an Audit-owned query and verify the actual Audit
   event targets that ticket. Keep scalar references, null creation approval/Audit values
   and optionality. Do not synthesize Audit events, approvals, number uniqueness, temporal
   rules, approval actor/time coupling or batch/period correlation not established by HMSR.
4. HMR-072 adds nullable program_id -> Integrity programme FK with validated legacy
   preflight and ON DELETE RESTRICT. Validate populated actor references through Identity
   and Workflow against the exact assessment context. Preserve unresolved methodologyId,
   optional programme and existing assessment-type catalog integrity. No invented title
   domain invariant, assessment-number uniqueness, approval state machine or current
   Audit-population obligation. Retain Audit scalar ownership for any separately admitted
   future population flow.
5. Every owner contract returns narrow scalar/boolean evidence. Providers query only their
   own ports/persistence. Identity validates referenced real actor eligibility; actor
   snapshots and caller strings are not authority. Workflow attestation checks target
   module, target ID, actual WORKFLOW_TARGET_TYPE and configured definition target/purpose
   binding; mere instance existence is insufficient. Do not infer business approval from
   existence or seed permissive Workflow configuration. Undefined or mismatched configured
   target types deny. Workflow start/transition support for these new clients is outside
   this batch; unsupported start targets remain denied by the existing owner registry.
6. Guard the authoritative repository saves so application and direct adapter writes cannot
   bypass reference checks. Preserve valid unchanged historical provenance without using
   it for fresh authorization; check all new/changed populated owner references. Do not
   replace legacy evidence automatically or accept an owner lookup failure. Add exact
   package exports to both architecture test registries, preserving every existing rule.
7. Prepare focused domain/adapter/owner tests and real PostgreSQL migration tests for null
   optional values, missing local references, legacy orphan rollback, wrong actor/owner,
   wrong Workflow module/type/target/binding, unavailable owner, wrong Audit ticket target,
   successful valid references and historical preservation. Verify local FK race behavior
   through actual PostgreSQL. No stubs/mock outputs count as database or Maven evidence.
8. Run each registered compile/focused/full-test/clean-verify target. If this Java 17 host
   with no Docker and uncached Spring Boot 4.1.1 parent cannot run them, record that exact
   limitation and require production Java 21/PostgreSQL/full OpenAPI CI. Publish the three
   commits on existing main once, confirm final-head CI triggered, then stop for next/fail.
   No PR, release, tag, version change, HPR-P2-008 final PASS or Batch 17 is included.

### Proposed exhaustive per-HMR write scopes

The following paths are proposed for acceptance, not permission for production mutation
in this preflight. Retained original client paths are an allowlist; change only necessary
files. Shared documentation/architecture paths may recur across individual commits.

#### HMR-069 proposed scope

- `docs/data definition/Assets.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/request/CreateMaintenanceWorkOrderRequest.java`
- `src/main/java/dz/sh/hidra/modules/assets/api/rest/response/MaintenanceWorkOrderResponse.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/command/CreateMaintenanceWorkOrderCommand.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/dto/MaintenanceWorkOrderSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/in/CreateMaintenanceWorkOrderUseCase.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/port/out/MaintenanceWorkOrderRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/model/MaintenanceWorkOrder.java`
- `src/main/java/dz/sh/hidra/modules/assets/domain/value/MaintenanceWorkOrderStatus.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/JpaMaintenanceWorkOrderRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/entity/MaintenanceWorkOrderTaskJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/mapper/AssetsPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintenanceWorkOrderJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/repository/MaintenanceWorkOrderTaskJpaRepository.java`
- `src/main/resources/db/migration/V20261008_011__hmr_069_assets_maintenance_work_order.sql`
- `src/test/java/dz/sh/hidra/modules/assets/semantic/MaintenanceWorkOrderSemanticRemediationTest.java`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/MaintenanceWorkOrderReferenceValidation.java`
- `src/test/java/dz/sh/hidra/modules/assets/infrastructure/persistence/MaintenanceWorkOrderSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/assets/MaintenanceWorkOrderActorReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/MaintenanceWorkOrderActorReferenceQueryService.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/MaintenanceWorkOrderActorReferenceContractTest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/assets/MaintenanceWorkOrderWorkflowReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/MaintenanceWorkOrderWorkflowReferenceQueryService.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/MaintenanceWorkOrderWorkflowReferenceContractTest.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/contract/assets/MaintenanceRecommendationReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/contract/assets/package-info.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/MaintenanceRecommendationReferenceQueryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/integrity/semantic/MaintenanceRecommendationReferenceContractTest.java`

#### HMR-070 proposed scope

- `docs/data definition/Custody.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/request/CreateCustodyTransferTicketRequest.java`
- `src/main/java/dz/sh/hidra/modules/custody/api/rest/response/CustodyTransferTicketResponse.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/command/CreateCustodyTransferTicketCommand.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/dto/CustodyTransferTicketSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/in/CreateCustodyTransferTicketUseCase.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/port/out/CustodyTransferTicketRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/custody/domain/model/CustodyTransferTicket.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/JpaCustodyTransferTicketRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/entity/CustodyTransferTicketJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/mapper/CustodyPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyTransferTicketJpaRepository.java`
- `src/main/resources/db/migration/V20261008_012__hmr_070_custody_custody_transfer_ticket.sql`
- `src/test/java/dz/sh/hidra/modules/custody/semantic/CustodyTransferTicketSemanticRemediationTest.java`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/adapter/CustodyTransferTicketReferenceValidation.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/persistence/CustodyTransferTicketSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/custody/CustodyTransferTicketActorReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/custody/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/CustodyTransferTicketActorReferenceQueryService.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/CustodyTransferTicketActorReferenceContractTest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/custody/CustodyTransferTicketWorkflowReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/custody/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/CustodyTransferTicketWorkflowReferenceQueryService.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/CustodyTransferTicketWorkflowReferenceContractTest.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/custody/CustodyTicketAuditReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/contract/custody/package-info.java`
- `src/main/java/dz/sh/hidra/modules/audit/application/service/CustodyTicketAuditReferenceQueryService.java`
- `src/test/java/dz/sh/hidra/modules/audit/semantic/CustodyTicketAuditReferenceContractTest.java`

#### HMR-072 proposed scope

- `docs/data definition/Integrity.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/request/CreateIntegrityAssessmentRequest.java`
- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/response/IntegrityAssessmentResponse.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/command/CreateIntegrityAssessmentCommand.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/dto/IntegrityAssessmentSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/CreateIntegrityAssessmentUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityAssessmentRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityAssessment.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityAssessmentStatus.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaIntegrityAssessmentRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityAssessmentJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityAssessmentScopeJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/mapper/IntegrityPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/IntegrityAssessmentJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/IntegrityAssessmentScopeJpaRepository.java`
- `src/main/resources/db/migration/V20261008_013__hmr_072_integrity_integrity_assessment.sql`
- `src/test/java/dz/sh/hidra/modules/integrity/semantic/IntegrityAssessmentSemanticRemediationTest.java`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/IntegrityAssessmentReferenceValidation.java`
- `src/test/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/IntegrityAssessmentSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/integrity/IntegrityAssessmentActorReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/integrity/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/IntegrityAssessmentActorReferenceQueryService.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/IntegrityAssessmentActorReferenceContractTest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/integrity/IntegrityAssessmentWorkflowReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/integrity/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/IntegrityAssessmentWorkflowReferenceQueryService.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/IntegrityAssessmentWorkflowReferenceContractTest.java`

### Preflight disposition

HMR-069/070/072 are BLOCKED pending WORK-PREREQ-01 acceptance; no production Java,
SQL, application configuration or OpenAPI shape changed. HMR-080's independent Party
prerequisite remains blocked. Totals: 43 CI-confirmed implementations, 10 STILL REQUIRED,
four BLOCKED, 57 evaluated. Batch 15 is confirmed by exact-head CI #595; this is not
HPR-P2-008 final PASS.

Exact preflight scope: `doc/roadmap/ULTIMATE_ROADMAP.md` and
`doc/model-remediation/RECONCILIATION.md` only. Exact supporting message:
`docs(platform): record Batch 16 execution preflight`.
Validate UTF-8/nonempty/conflict-marker-free canonical Markdown, exact two-file scope,
unchanged production tree and git diff --check. Trigger applicable documentation CI on
main, then stop. Next registered action: owner next accepts WORK-PREREQ-01 and the
proposed exhaustive scopes, subject to a fresh green production baseline; execute the
three separate semantic tasks above. Owner amendment may narrow the proposal.

Preflight checks completed: the exact documentation-workflow Python validator passed
for all 82 canonical Markdown files; exact two-file write scope, unchanged production/
CI/POM tree, independent proposed scopes and git diff --check passed. No Maven,
PostgreSQL or new production test was executed for this documentation-only change.

## HPR-P2-008 Batch 16 accepted execution envelope — 2026-10-08

Owner next accepts WORK-PREREQ-01 and all three exhaustive scopes recorded in preflight
commit a8233b68fe80688965236aa1ec3b4ac11c980990. Documentation CI #97 / run 37781458719
passed. Its production tree is byte-identical to full green Java 21/PostgreSQL/OpenAPI
CI #595 at e2e92bae7d69c54a46fa92702b539858404bf7ce. Execute HMR-069 -> HMR-070 -> HMR-072
with separate exact semantic commits and forward migrations 011/012/013. The preflight
scope lists now constitute each task's exhaustive write allowlist. Only necessary paths
will change. Owner contracts, preserved optionality/history, fail-closed context checks
and all explicitly excluded semantic inventions in WORK-PREREQ-01 remain binding.
All three tasks are In Progress; HMR-080 remains independently blocked. Publish the
chain once on main, confirm full CI started, then stop. No PR or later batch is authorized.

### Batch 16 HMR-069 implementation result — 2026-10-08

Source HMSR-081 and current creation/domain/adapter/owner/Flyway evidence independently
recovered before this task. Required title, nullable local plan integrity and owner-controlled recommendation/unit/actor/Workflow context validation now guard authoritative transactional saves. Existing parent rows are pessimistically locked before historical-reference comparison; unchanged historical owner references are preserved and fresh or changed references fail closed. Forward 011 adds a validated plan FK and nonblank-title check without rewriting legacy rows. No unsupported type-family, assignment, temporal or uniqueness rules added. Changed production sources and 21 focused test signatures compiled with temporary framework/JUnit/Mockito APIs; 11 actual domain/reference behavior checks passed using controlled owner fixtures. Six real PostgreSQL cases prepared, including legacy rollback and concurrent parent deletion. Focused Maven invocation stopped before compilation/test execution at uncached offline Spring Boot 4.1.1 parent.

Exact semantic message: `fix(assets): remediate semantic review MaintenanceWorkOrder`.
Implementation complete; actual production Java 21/Spring/PostgreSQL/OpenAPI verification
is pending final-head CI. No prepared test or temporary API compile is an executed
Maven/JUnit/PostgreSQL pass. No foreign-module FK, PR, release or later batch is included.

### Batch 16 HMR-070 implementation result — 2026-10-08

Source HMSR-082 and current creation/domain/adapter/owner/Flyway evidence independently
recovered before this task. Nullable batch/calculation references now fail closed through Custody-owned checks and validated forward 012 FKs. Identity validates new/changed issuer and approver IDs; Workflow attests exact ticket context and configured binding; Audit owner resolves populated evidence for the exact Custody ticket. Locked transactional adapter saves prevent bypass while unchanged historical provenance remains preserved. Creation retains null approval/Audit values and optionality. No invented approval coupling, transition/temporal rule or cross-module FK. Changed production and 21 focused unit signatures compiled against temporary APIs; nine actual reference behavior checks passed with controlled owner fixtures. Four real PostgreSQL tests prepared, including legacy orphan rollback and concurrent parent deletion. Focused Maven stopped before execution at uncached offline Boot 4.1.1 parent.

Exact semantic message: `fix(custody): remediate semantic review CustodyTransferTicket`.
Implementation complete; actual production Java 21/Spring/PostgreSQL/OpenAPI verification
is pending final-head CI. No prepared test or temporary API compile is an executed
Maven/JUnit/PostgreSQL pass. No foreign-module FK, PR, release or later batch is included.

### Batch 16 HMR-072 implementation result — 2026-10-08

Source HMSR-085 and current creation/domain/adapter/owner/Flyway evidence independently
recovered before this task. Nullable programme membership now fails closed to Integrity-owned records with validated forward 013 FK. Identity validates new/changed assessor, reviewer and approver references; Workflow validates the exact assessment module/type/ID and configured purpose binding. Locked transactional adapter saves preserve unchanged historical provenance and reject missing/changed owners. Methodology, title domain semantics, optional programme, lifecycle and current null Audit metadata retain their original contracts. Changed production and 18 focused unit signatures compiled with temporary APIs; eight actual domain/reference checks passed using controlled owner fixtures. Four real PostgreSQL tests prepared for nullable links, orphan rollback and parent-delete races. Focused Maven plus compile/full-test/clean-verify targets stop before execution at uncached offline Spring Boot 4.1.1 parent.

Exact semantic message: `fix(integrity): remediate semantic review IntegrityAssessment`.
Implementation complete; actual production Java 21/Spring/PostgreSQL/OpenAPI verification
is pending final-head CI. No prepared test or temporary API compile is an executed
Maven/JUnit/PostgreSQL pass. No foreign-module FK, PR, release or later batch is included.

## HPR-P2-008 Batch 16 final implementation disposition — 2026-10-08

Accepted WORK-PREREQ-01 is implemented in three independently scoped semantic commits:

| Task / source review | Exact semantic commit | Actual changed scope |
|---|---|---|
| HMR-069 / HMSR-081 | b3c67ecce4e09758468e428d5534a0fbf3f8659a | 25 files; work-order title/plan and owner references, forward 011 |
| HMR-070 / HMSR-082 | 8ee951a2f35a8f194ebec823cb99df9cf20d28cf | 24 files; ticket evidence and owner references, forward 012 |
| HMR-072 / HMSR-085 | This exact semantic commit | 20 files; programme/actor/Workflow references, forward 013 |

Validation actually completed locally: changed production and all 60 prepared unit
method signatures plus 14 PostgreSQL method signatures compiled against temporary
framework/JUnit/Mockito/Testcontainers APIs. Twenty-eight real domain/client-reference
behavior checks and twenty real Identity/Workflow/Audit provider checks passed using
controlled owner repository fixtures. These 48 checks are not Maven/JUnit/Spring or
PostgreSQL execution. Fifty changed Java files parsed; canonical headers, proxyability
and all eight deliberate owner package exports in both architecture registries checked.
All 123 published migrations are byte-identical to baseline; only forward 011/012/013
are new. The exact per-task scopes and 82 canonical Markdown files validated, and
whitespace checks passed. No current API/command/DTO/REST shape changed.

Each task's focused Maven command, plus compile/full-test/clean-verify targets, was
invoked through bash ./mvnw -o -B -q. All stopped before compilation/test execution at
the uncached Spring Boot 4.1.1 parent. This host has Java 17 and no Docker/PostgreSQL;
no local Java 21, database migration, Spring transaction or OpenAPI run is claimed.
Prepared PostgreSQL cases exercise actual base-table and forward SQL, optional/missing
parents, legacy orphan rollback and parent-delete/reference races. Full baseline Flyway
and owner bean/architecture/runtime/contract validation remain actual production CI gates.

Deployment boundary: Workflow reference contracts require actual MAINTENANCE_WORK_ORDER,
CUSTODY_TRANSFER_TICKET or INTEGRITY_ASSESSMENT target catalogs and an active exact
configured definition/purpose binding. No permissive configuration or approval evidence
is seeded. Unsupported workflow starts remain denied by the existing owner registry;
new workflow start/approval orchestration is outside this admitted reference batch.
Unchanged historical owner references are preserved; new/changed references require real
owner eligibility/context. Missing owners, configuration or lookup failures fail closed.
Legacy local orphans or blank work-order titles require operator reconciliation; no
automatic data rewriting is introduced. Approval/Audit creation nullability and each
HMSR's explicitly unresolved taxonomy/lifecycle/temporal rules remain preserved.

Totals: 46 implementations (43 CI-confirmed plus three Batch 16 CI pending), 10 STILL
REQUIRED and one independent BLOCKED (HMR-080), 57 evaluated. Advance existing main
once to the final three-commit head with expected-SHA protection, confirm full CI started,
then stop for owner next/fail. No semantic verification closure before green final-head
CI, no HPR-P2-008 final PASS, PR, tag, release, version bump or later batch execution.
Next action after green CI and owner next: fresh preflight of the next attached batch.

## HPR-P2-008 Batch 17 HSE execution preflight — 2026-10-08

Owner next selects row 17 of the current attached `00 - Batchs Roadmap.txt`:
HMR-082/HMSR-096, HMR-096/HMSR-113 and HMR-097/HMSR-114. The attached source was
read in full and confirms HSE lifecycle after Batch 16. Exact main baseline
68e330562b03cf92c5500b99ffceca1fd024d083 passed full CI #596 / run 37783579023.
Java 21 repository verification, all infrastructure checks, current/base OpenAPI
generation, backward compatibility and artifact upload passed. Documentation CI #98
(run 37783579385) passed. HMR-069/070/072 are now CI-confirmed; this task is a
documentation-only preflight and introduces no HSE production mutation.

### HSE-PREREQ-01 — independently recovered live scope and policy gaps

AGENTS.md section 3.2.9 requires: "If an HMR reveals an unregistered prerequisite, SCC
complication, owner-contract gap, cross-module lifecycle dependency, migration-order
conflict, or materially larger semantic redesign, split it out and stop before mutating
that HMR." All three source reviews and current source have been recovered separately:

| Subject | Live evidence and prerequisite |
|---|---|
| HMR-082 / HseCase | `HseApplicationService.closeHseCase` directly constructs/saves closure without loading a case, invoking `HseCaseClosureGuard`, updating CLOSED/closedAt or inserting status history. Original scope excludes this service and atomic closure coordination. `HseCaseRepositoryPort` has no locked lookup, and `JpaHseCaseRepositoryAdapter.save` directly merges. Identity/Organization/Workflow owner contracts and architecture exports require admitted scope. |
| HMR-096 / HseClosure | `JpaHseClosureRepositoryAdapter.save` directly merges standalone closure evidence. The 11-field model and correct local case FK do not supply lifecycle authority. Original scope excludes service, locked parent, closure/history coordination and owning-case adapter. No registered transaction or concurrency protection couples the records. |
| HMR-097 / CAPA | `HseApplicationService.createHseCapa` copies the parent/action/owner/work-order/task IDs without loading the parent or enforcing catalog-family semantics. `JpaHseCorrectivePreventiveActionRepositoryAdapter.save` directly merges. Its scope excludes the service, catalog-policy definition and owner providers. Generic action-type FK proves row existence, not intended family. No authoritative family name is present in the live DDD or provisioning. |

The live `HseCaseClosureGuard` rejects null parent, CLOSED/CANCELLED parent and false
impactAssessed/capaCompleted/evidenceReviewed flags. It does not require RESOLVED,
regulatoryReviewed=true, a nonblank closure summary, or particular CAPA states. Those
extra rules must not be invented. `HseCaseStatusHistory` is explicitly append-only in
the HSE DDD. Current status-history JPA repository exposes only generic persistence;
no application status-history creation path accompanies closure.

HSE catalog rows use `catalog_name`, not an inferred fixed CAPA-family enum. The module
configuration defaults are generic booleans, not field-to-family policy. The target
Noop resolver returns true; it cannot serve as actual owner evidence. Neutral Incident,
Audit and polymorphic target IDs/snapshots remain neutral as HMSR-096 directs; this
batch does not convert them to mandatory live-reference checks without domain policy.

Migration tail is published V20261008_013. Original V20261004_082/096/097 are absent
and backdated. New atomic lifecycle/policy infrastructure and forward migrations
014/015/016 must be admitted before implementation; published SQL remains immutable.

### Concrete coordinated execution proposal for acceptance

1. Admit HMR-082 -> HMR-096 -> HMR-097 in attached order. Preserve each independent HMSR,
   exact scope, validation result, status and separate semantic commit. HMR-082 restores
   the application closure/lifecycle operation; HMR-096 routes standalone closure saves
   through that same operation and reinforces stored evidence. HMR-097 validates CAPA.

   | Task | Exact semantic message | Proposed forward migration |
   |---|---|---|
   | HMR-082 | `fix(hse): remediate semantic review HseCase` | `V20261008_014__hmr_082_hse_case_lifecycle.sql` |
   | HMR-096 | `fix(hse): remediate semantic review HseClosure` | `V20261008_015__hmr_096_hse_closure_atomic_evidence.sql` |
   | HMR-097 | `fix(hse): remediate semantic review HseCorrectivePreventiveAction` | `V20261008_016__hmr_097_hse_capa_reference_catalog_integrity.sql` |

2. HMR-082 adds a locked parent lookup and an HSE-owned `HseClosureLifecyclePort` with
   `JpaHseClosureLifecycleAdapter` coordinating one REQUIRED transaction. The application
   close path loads the actual case and calls the existing guard before writing evidence.
   The coordinator revalidates the locked case, persists closure, updates CLOSED/closedAt
   and writes old-status -> CLOSED history with one server-owned microsecond timestamp.
   All three writes commit or roll back together. Preserve supported nonterminal statuses
   admitted by the current guard; do not impose RESOLVED-only closure. Keep regulatory
   review optional under existing policy. Make the transactional service proxyable.
3. Bind the closing actor to the authenticated eligible Identity actor and canonical
   display evidence through a narrow Identity-owned HSE contract. Caller snapshots are
   not authority. Validate populated new/changed reporter/responsible-unit/Workflow
   references through Identity/Organization/Workflow owners. Optional Workflow context
   must target the actual HSE case with configured target/purpose binding; instance
   existence never proves approval. Preserve valid historical scalar snapshots on reads
   and unchanged references; no live owner refresh of historical evidence.
4. Generic case saves cannot independently establish CLOSED or alter the recorded closed
   lifecycle tuple. Preserve unrelated fields and existing enum/required-field semantics.
   Add append-only status-history protection and fail-closed stored lifecycle/evidence
   consistency checks, with legacy preflight that reports incoherent rows instead of
   manufacturing timestamps, history, flags or actor evidence. Keep existing case-type/
   severity catalog FKs; their exact families and priority remain unresolved pending
   authoritative HSE taxonomy evidence. No external relational FK or new REST shape.
5. HMR-096 routes `HseClosureRepositoryPort.save` through the same lifecycle coordinator,
   never through generic merge. The coordinator uses owned JPA repositories directly so
   the closure adapter does not create a dependency cycle. Require the loaded guard and
   exact parent/closure/history timestamp and actor coherence on every authoritative
   closure path. Closure evidence/status history cannot be overwritten or erased.
   Concurrent/replayed closure is rejected by parent serialization and closed-lifecycle
   guard. Do not add a one-closure-row-per-case SQL uniqueness rule unless separately
   adopted by HSE design; HMSR-113 does not establish that invariant. Real transaction
   tests must demonstrate rollback, matching case/history and one successful concurrent
   authoritative close. Do not equate boolean attestations with independently persisted
   impact/CAPA/evidence findings; this batch enforces the actual existing guard.
6. HMR-097 loads/locks the owning case before CAPA creation and revalidates parent
   existence in adapter saves. Apply only evidenced case-lifecycle eligibility rules;
   currently none narrows CAPA creation to a status subset. Do not inherit Incident's
   DRAFT/CLOSED/CANCELLED exclusions. Keep the CAPA lifecycle, optional completion/
   verification metadata, title domain semantics and numbering constraints unchanged.
7. Establish explicit HSE-owned field-to-catalog-family policy for the CAPA action-type
   role. An internal field-role key is not a fabricated catalog family name. Forward 014
   may create the unseeded `hidra_hse_catalog_field_policy`; an operator must approve the
   actual existing catalog_name used for CAPA. `HseCatalogFieldPolicy` and forward 016
   require one active explicit mapping and exact family membership/eligibility for new
   or changed action-type references. Missing/ambiguous/ineligible configuration denies;
   never guess a family from entry code/ID or seed permissive defaults. Protect used
   mapping identity from retroactive reassignment. Valid unchanged historical catalog
   references remain readable after deactivation.
8. Forward 016 validates legacy CAPA family membership against the explicit mapping.
   If legacy CAPA exists without a mapping, it fails rather than silently classifying it.
   Flyway's earlier committed 014 creates the empty policy table, allowing an operator
   to provision approved metadata before retrying 016; no synthetic row is inserted by
   migrations. Incoherent existing lifecycle records may block 014 itself and require
   operator reconciliation supported by real evidence. No automatic data rewrite.
9. Validate populated CAPA owner/verifier through Identity, unit through Organization,
   linked work order through Assets and Workflow task through Workflow. Expose only
   scalar/boolean owner contracts, with provider internals remaining within each owner.
   Workflow task validation must resolve the actual task's instance and the intended
   HSE case or CAPA target context with explicit type/binding; no arbitrary task existence
   shortcut. Do not invent a work-order/case correlation or assignment requirement.
   HSE records CAPA evidence; no physical equipment command or foreign workflow mutation.
10. Add exact exported packages to both architecture registries. Focused tests cover each
    guard rejection, eligible closure of supported statuses, null/unknown case, rollback
    at every write boundary, concurrent closes, matching history/timestamps, direct-save
    bypass, immutable evidence, correct/wrong/missing CAPA policy, legacy migration
    rollback, eligible/missing owners, wrong task/context, preserved optional/history
    semantics and deliberately unsupported stronger rules. Run actual PostgreSQL/Spring
    transaction/concurrency and full Java 21 clean verify/OpenAPI gates in CI. Local
    API-stub compilation or controlled fixtures never substitute for runtime evidence.
11. Execute each task's registered compile/focused/full-test/clean-verify targets; record
    any uncached Boot 4.1.1/Java 17/no-Docker limitation honestly. Publish the three exact
    commits on existing main once, confirm final-head production CI started and stop for
    owner next/fail. No PR, release, version change, HPR-P2-008 final PASS or Batch 18.

### Proposed exhaustive per-HMR write scopes

These proposed scopes require acceptance before production mutation. Existing client
paths remain allowed; change only necessary files. Shared lifecycle/owner/tests may
recur where a task independently reinforces the admitted operation.

#### HMR-082 proposed scope

- `docs/data definition/Hse.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/hse/api/rest/request/CloseHseCaseRequest.java`
- `src/main/java/dz/sh/hidra/modules/hse/api/rest/request/OpenHseCaseRequest.java`
- `src/main/java/dz/sh/hidra/modules/hse/api/rest/response/HseCaseResponse.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/command/CloseHseCaseCommand.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/command/OpenHseCaseCommand.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/dto/HseCaseSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/in/CloseHseCaseUseCase.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/in/OpenHseCaseUseCase.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseCaseRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCase.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/service/HseCaseClosureGuard.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/value/HseCaseSourceType.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/value/HseCaseStatus.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseCaseRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseEvidenceLinkJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseStatusHistoryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/mapper/HsePersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseCaseEvidenceLinkJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseCaseJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseCaseStatusHistoryJpaRepository.java`
- `src/main/resources/db/migration/V20261008_014__hmr_082_hse_case_lifecycle.sql`
- `src/test/java/dz/sh/hidra/modules/hse/semantic/HseCaseSemanticRemediationTest.java`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `src/main/java/dz/sh/hidra/modules/hse/application/service/HseApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseClosureLifecyclePort.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseClosureLifecycleAdapter.java`
- `src/test/java/dz/sh/hidra/modules/hse/infrastructure/persistence/HseLifecycleSemanticPostgresIntegrationTest.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseClosureJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseClosureJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/HseCaseReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/configuration/HseCatalogFieldPolicy.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/hse/HseActorContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/hse/package-info.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/HseActorQueryService.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/HseActorContractTest.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/hse/HseOrganizationReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/contract/hse/package-info.java`
- `src/main/java/dz/sh/hidra/modules/organization/application/service/HseOrganizationReferenceQueryService.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/HseOrganizationReferenceContractTest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/hse/HseWorkflowReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/hse/package-info.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/HseWorkflowReferenceQueryService.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/HseWorkflowReferenceContractTest.java`

#### HMR-096 proposed scope

- `docs/data definition/Hse.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseClosureRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseClosure.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseClosureRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseClosureJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/mapper/HsePersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseClosureJpaRepository.java`
- `src/main/resources/db/migration/V20261008_015__hmr_096_hse_closure_atomic_evidence.sql`
- `src/test/java/dz/sh/hidra/modules/hse/semantic/HseClosureSemanticRemediationTest.java`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `src/main/java/dz/sh/hidra/modules/hse/application/service/HseApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseCaseRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseCaseJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseClosureLifecyclePort.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseClosureLifecycleAdapter.java`
- `src/test/java/dz/sh/hidra/modules/hse/infrastructure/persistence/HseLifecycleSemanticPostgresIntegrationTest.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/service/HseCaseClosureGuard.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseCaseStatusHistoryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseStatusHistoryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseCaseRepositoryAdapter.java`
- `src/test/java/dz/sh/hidra/modules/hse/semantic/HseCaseSemanticRemediationTest.java`

#### HMR-097 proposed scope

- `docs/data definition/Hse.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseCorrectivePreventiveActionRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCorrectivePreventiveAction.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseCorrectivePreventiveActionRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCorrectivePreventiveActionJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/mapper/HsePersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseCorrectivePreventiveActionJpaRepository.java`
- `src/main/resources/db/migration/V20261008_016__hmr_097_hse_capa_reference_catalog_integrity.sql`
- `src/test/java/dz/sh/hidra/modules/hse/semantic/HseCorrectivePreventiveActionSemanticRemediationTest.java`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `src/main/java/dz/sh/hidra/modules/hse/application/service/HseApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseCaseRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseCaseJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseClosureLifecyclePort.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseClosureLifecycleAdapter.java`
- `src/test/java/dz/sh/hidra/modules/hse/infrastructure/persistence/HseLifecycleSemanticPostgresIntegrationTest.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/command/CreateHseCapaCommand.java`
- `src/main/java/dz/sh/hidra/modules/hse/application/port/in/CreateHseCapaUseCase.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/HseCapaReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/configuration/HseCatalogFieldPolicy.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/repository/HseCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseCaseRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/contract/hse/HseWorkOrderReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/contract/hse/package-info.java`
- `src/main/java/dz/sh/hidra/modules/assets/application/service/HseWorkOrderReferenceQueryService.java`
- `src/test/java/dz/sh/hidra/modules/assets/semantic/HseWorkOrderReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/hse/HseWorkflowReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/HseWorkflowReferenceQueryService.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/HseWorkflowReferenceContractTest.java`

### Current disposition and preflight validation

HMR-082/096/097 are BLOCKED pending HSE-PREREQ-01 acceptance; no HSE production path,
SQL or runtime configuration changed. HMR-080 remains independently blocked. Totals:
46 CI-confirmed implementations, seven STILL REQUIRED and four BLOCKED, 57 evaluated.
HPR-P2-008 is not finally closed. Next attached batch after this HSE batch is row 18,
HMR-098 / IntegrityCase, subject to its own fresh admission and green baseline.

This preflight's exact write scope is only `doc/roadmap/ULTIMATE_ROADMAP.md` and
`doc/model-remediation/RECONCILIATION.md`. Exact supporting message:
`docs(hse): record Batch 17 execution preflight`.

Validate canonical UTF-8/nonempty/conflict-marker-free Markdown, exact two-file scope,
unchanged production/CI/POM tree and git diff --check. No Maven/PostgreSQL test is
claimed for this documentation change. Trigger applicable Documentation Validation
on main, then stop. Owner next accepts HSE-PREREQ-01 and proposed exhaustive scopes,
subject to fresh baseline validation; an amendment may narrow the design first.

Preflight checks completed: exact documentation-workflow validation passed for all
82 canonical Markdown files. Exact two-file scope, unchanged production/CI/POM bytes,
independent proposed scopes/migration registration and git diff --check passed. No
production, database or Maven verification was run for this documentation-only change.


## HPR-P2-008 Batch 17 accepted execution envelope — 2026-10-08

Owner `next` accepts HSE-PREREQ-01 on preflight
91c4cbe45bab2786af9581f7130e0960c2ed1e0a. Main remains that head;
Documentation CI #99 (37785917783) passed. Production is byte-identical to
68e330562b03cf92c5500b99ffceca1fd024d083, successful full CI #596 (37783579023).
The accepted proposal and exhaustive proposed scopes above are now admitted as
execution scopes, in order HMR-082 -> HMR-096 -> HMR-097. Forward 014/015/016
and each exact semantic message remain independent; no published migration rewrite,
PR, release or later batch is admitted. Each task uses only required admitted paths.
Record independent implementation results below; final-head CI must start before
pausing for owner `next` or `fail`. Do not claim final CI success before it exists.


### Batch 17 HMR-082 implementation result — 2026-10-08

HMSR-096 recovered independently from the live source review. Application closure loads
and pessimistically locks the owning case before calling the existing guard. The new
REQUIRED lifecycle coordinator revalidates under that lock, binds the authenticated
eligible Identity actor, validates optional exact HSE Workflow context, and persists
closure, CLOSED/closedAt and old-status -> CLOSED history with one server microsecond
timestamp. No RESOLVED-only or regulatoryReviewed requirement is invented. Boolean
attestations remain the existing guard inputs, not proof of independently stored findings.

Generic parent saves cannot establish CLOSED or change a recorded closed tuple. New or
changed reporter/unit/Workflow references use narrow owner contracts; unchanged historical
snapshots remain readable without owner refresh. Neutral Incident/Audit/target references
and existing case-type/severity FKs remain unchanged; exact case catalog families are still
unresolved. Forward 014 creates unseeded field-family metadata and enforces deferred
case/closure/history coherence, closed tuple immutability and append-only status history.
Legacy incoherence aborts migration without data repair. No cross-module FK is introduced.

Validation: production HSE/owner sources and focused test signatures compiled on Java 17
against temporary dependency APIs. Fourteen actual domain/application checks passed with
controlled ports. Focused owner/domain and PostgreSQL tests are prepared, not executed
locally. Maven compilation stops before execution at uncached Spring Boot parent 4.1.1
in offline mode; Java 21/Docker/PostgreSQL are unavailable locally. Real runtime/full
verification remains final-head CI responsibility. Scope, preserved SQL and Markdown
checks are required before publication. HMR-082 implementation Completed pending CI;
HMR-096 and HMR-097 now admitted/in progress; HMR-080 remains blocked.


### Batch 17 HMR-096 implementation result — 2026-10-08

HMSR-113 independently recovered. Every closure repository save now delegates to the
HSE lifecycle coordinator; it never merges evidence. New closure/history records use
EntityManager.persist and flush inside the REQUIRED transaction, using own JPA repositories
and no circular repository-port dependency. Forward 015 serializes closure insertion on
the parent, enforces the existing attestation guard, checks actual old status in closure
history, and denies closure overwrite/delete/truncate and case truncate. No new uniqueness,
RESOLVED-only, regulatory flag or summary domain rule is introduced.

Four focused closure unit methods and four additional real PostgreSQL/Spring-JPA methods
are prepared, including rollback after all three flushed writes, exact shared time/actor/
correlation, concurrent one-winner closure, replay rejection and immutable evidence. These
have not run locally. Changed production and focused test signatures compiled against
temporary APIs. Twelve actual coordinator/delegation checks passed with controlled owned
repositories; this does not demonstrate database rollback or lock behavior. Focused Maven
execution stops before tests at uncached Boot 4.1.1 offline parent resolution. Real runtime
verification remains final-head CI. HMR-096 implementation Completed pending CI; next
admitted task HMR-097. HPR-P2-008 remains open and HMR-080 independently blocked.


### Batch 17 HMR-097 implementation result — 2026-10-08

HMSR-114 recovered independently. Application and adapter load/lock the real owning HSE
case before CAPA writes. Current policy does not impose a case-status subset. Locked
catalog validation requires explicit CAPA_ACTION_TYPE field-role metadata and exact
catalog_name membership, with active mapping/entry eligibility for new/changed types.
Missing or ambiguous policy denies instead of guessing a family. Valid unchanged inactive
history remains readable and writable without live snapshot refresh. New/changed owner
and verifier use Identity; unit uses Organization; work order uses Assets; Workflow task
must resolve its actual HSE_CASE/HSE_CAPA instance context and configured type/purpose
binding. Fresh owner/unit snapshots are canonical. No assignment, approval, completion/
verification-state or work-order/case correlation rule is invented.

Forward 016 checks legacy family/parent integrity, locks parent/mapping/catalog on writes,
protects used mapping/family identity from reassignment, and prevents policy truncation.
Existing CAPA without an approved mapping blocks 016. Forward 014 remains independently
committed so an operator can provision actual approved metadata and retry; no mapping is
seeded, guessed or automatically repaired. Closed/closure legacy incoherence can separately
block 014 and requires reconciliation from real evidence. Published SQL is unchanged.

Validation performed: all 37 changed Java files parsed; changed production/owner boundaries
and 30 focused unit plus 13 PostgreSQL/Spring-JPA test method signatures compiled on Java
17 against temporary APIs. Thirty-nine actual domain/application/coordinator/CAPA checks
passed with controlled ports/repositories (14 + 12 + 13); this is not real transaction,
JUnit, Spring, Hibernate or PostgreSQL verification. All 126 published migration files
are byte-identical to the baseline. Canonical 82 Markdown validation and independent
write scopes passed. git diff --check is required before committing/publishing.

Attempted bash mvnw -o -B -q compile (-DskipTests), focused HSE/owner/architecture tests,
full test and clean verify all stop before execution because Spring Boot parent 4.1.1 is
uncached offline. Java 21, Docker and PostgreSQL are unavailable locally. Real runtime,
full Flyway, architecture and OpenAPI compatibility verification remains final-head CI.
No test pass is fabricated from expected database behavior.

HMR-082 semantic commit: 2a8438d1b0577d9a932697a386feb51507ea6b17 (30 changed files).
HMR-096 semantic commit: 404f0e5e41d48d656bb5196f47acd7ebfe9c0455 (10 changed files).
HMR-097 retains its own exact semantic commit. Advance main once to the final chain,
confirm production CI starts, then stop for owner next/fail; no PR is created.

| Subject | Current status | Evidence disposition |
|---|---|---|
| HMR-082 / HseCase | Completed implementation | Pending final-head CI |
| HMR-096 / HseClosure | Completed implementation | Pending final-head CI |
| HMR-097 / HseCorrectivePreventiveAction | Completed implementation | Pending final-head CI |
| HMR-080 | Blocked | Independent unresolved prerequisite |

Current total: 49 implementations (46 CI-confirmed, three awaiting final-head CI),
seven STILL REQUIRED and one BLOCKED, 57 evaluated. HPR-P2-008 remains open.
Next attached batch is row 18, HMR-098 / IntegrityCase, subject to fresh admission,
green baseline and owner next. No later task executes in this envelope.

## HPR-P2-008 Batch 18 IntegrityCase execution preflight — 2026-10-08

Owner `next` selects attached row 18, HMR-098 / IntegrityCase, as the next solo subject.
Current main is cfb3681ef1c79b4416336a3533cbc0599b4fd6b2. Exact-head full production
CI #597 (37789260852) passed, including Java 21 repository verification, PostgreSQL,
all production infrastructure checks, deterministic current/base OpenAPI generation,
backward compatibility and artifact upload. Documentation CI #100 passed on that head.
Batch 17 HMR-082/096/097 are therefore CI-confirmed implementations. This does not
independently close HPR-P2-008 or any physical survivability evidence obligation.

### IC-PREREQ-01 — independently recovered scope and taxonomy gaps

Live HMSR-115 (`docs/roadmap/model-semantic-review.md`, section 128) requires nullable
primary-defect existence, explicit case-type family semantics and preserved owner
boundaries. Its temporal rule remains openedAt <= closedAt, with no stronger universal
status/time coupling, close/resolve use case or primary-defect topology equality rule.

Current evidence:

| Path | Live finding |
|---|---|
| `integrity/application/service/IntegrityApplicationService.java` | openIntegrityCase copies primaryDefectId, caseTypeId and external references without validating a supplied defect or type family. This application service is absent from the original HMR-098 scope. Adding the own defect port affects the two IntegrityProgram constructor fixtures, also absent from that scope. |
| `integrity/infrastructure/persistence/adapter/JpaIntegrityCaseRepositoryAdapter.java` | save directly merges through the JPA repository. There is no write-boundary reference validator or locked existing-case read. |
| `V20260611_013__create_integrity_tables.sql` and HRA-111 | Case-type generic catalog FK exists. primaryDefectId is nullable/indexed but has no local FK. Generic catalog existence cannot establish case-type family membership. |
| `docs/data definition/Integrity.md`, live Integrity catalog Java and provisioning SQL | No authoritative case-type catalog_name or case field-family policy is defined. INTEGRITY_PROGRAM_TYPE is an existing programme family and cannot be reused as case taxonomy merely because it exists. Severity family also remains unresolved. |
| `identity/.../contract/integrity`, `organization/.../contract/integrity`, `workflow/.../contract/integrity` | Organization has a reusable owner-controlled existence contract. Identity and Workflow currently export assessment-specific contracts, not case-specific reference authority. WorkflowAssessment matching cannot attest an IntegrityCase target. |
| `topology/application/contract`, existing Topology providers | Narrow typed providers exist for other consumer contexts, but no IntegrityCase-specific exported contract/provider exists. Direct imports of Topology domain/JPA/repositories into Integrity remain forbidden. |
| `integrity/infrastructure/integration/NoopIntegrityExternalReferenceResolver.java` | Always-true methods are not actual owner proof. The new authoritative case path must not use that fallback as validation. |

Original HMR-098 scope has 18 paths, excludes the application service, owner providers,
field-policy definition, catalog/defect validation repositories and architecture admission.
Its unexecuted V20261004_098 registration predates the already published migration tail
V20261008_016. Published SQL cannot be backdated or edited. AGENTS.md §3.2.9 requires
stopping before production mutation when these unregistered prerequisites appear.
HMR-098 is BLOCKED on IC-PREREQ-01; this preflight changes only the two canonical
execution-memory documents and prepares the concrete remedy below.

### Concrete solo execution proposal for acceptance

1. Admit HMR-098/HMSR-115 alone, retaining exact semantic commit
   `fix(integrity): remediate semantic review IntegrityCase`. Preserve the existing
   20-field REST/application/domain/JPA shape, enum, required values and ordering rule.
   No automatic close/resolve, status-history lifecycle orchestration, forced CLOSED
   timestamp, defect status restriction, case-number uniqueness, required optional
   actor/defect/source/unit/workflow, or defect-to-case topology equality is invented.
2. Add optional primary-defect lookup at the application opening boundary using the
   Integrity-owned repository port. Unknown populated IDs fail before case save. Adapter
   writes revalidate the defect under a shared row lock, retain null optionality and lock
   an existing case before validating changed references. Nullable ON DELETE RESTRICT FK
   independently prevents dangling references and parent-delete races. Do not import
   other modules' persistence types into Integrity. Make any transactional service
   proxyable and retain existing programme/assessment constructor behavior through
   explicitly updated fixtures.
3. Establish an explicit unseeded Integrity-owned field-policy table
   `hidra_integrity_catalog_field_policy`, with unique field_role and actual catalog_name.
   Internal role `CASE_TYPE` is a field identifier, not a fabricated catalog family.
   An operator must approve/provision the actual existing case-type family. Policy and
   catalog shared locks require exactly one configured mapping and exact family membership.
   New/changed references require active mapping/entry. Missing/ambiguous/ineligible mapping
   denies; no code/ID/family heuristics or permissive defaults. Protect a used mapping and
   referenced catalog family from reassignment/deletion/truncation that would invalidate
   historical cases. Valid unchanged inactive references remain readable and writable.
   severityId retains its current optional shape; no exact severity family is invented.
4. Introduce a narrow Topology-owned scalar reference contract/provider for case targets.
   Adopt the existing owner lookup vocabulary PIPELINE, SEGMENT, FACILITY, EQUIPMENT,
   NODE and CONNECTION, using real owned repository reads with exact type/id identity.
   Unknown/unsupported type or missing target denies new/changed linkage. Do not silently
   alias different namespaces or add speculative target types. Fresh target code snapshots
   come from Topology; unchanged historical snapshots remain readable without owner refresh.
   This is reference proof at write time, not a cross-module relational lifetime guarantee.
5. Validate populated new/changed openedByActorId via a narrow Identity-owned case
   eligibility contract, responsibleOrganizationUnitId through the existing Organization
   Integrity reference contract, and workflowInstanceId through a new case-specific Workflow
   contract. Workflow attests the actual integrity module/case ID, configured active
   WORKFLOW_TARGET_TYPE with code INTEGRITY_CASE, supplied active WORKFLOW_PURPOSE and
   configured definition/type/purpose binding. Existence alone is insufficient; context
   proof is not approval. Do not seed taxonomy, start Workflow or invent a purpose name,
   approval/completion or actor-authentication requirement for this optional scalar field.
6. sourceIncidentId/sourceHseCaseId remain neutral optional historical source context.
   HMSR-115 and the DDD do not establish a current source-state/existence dependency for
   this opening behavior; do not invent mandatory live source checks or foreign FKs.
   All Topology, Identity, Organization, Workflow, HSE and Incident references remain
   scalars/snapshots. Case saves never write another owner's lifecycle or assets.
7. Replace the unexecuted historical migration registration with these independent forward
   files inside the single HMR-098 semantic commit:

   | Forward file | Purpose |
   |---|---|
   | `V20261008_017__hmr_098_integrity_case_catalog_field_policy.sql` | Create explicit unseeded owner field-policy metadata before the validation migration. |
   | `V20261008_018__hmr_098_integrity_case_reference_integrity.sql` | Legacy parent/family/ordering preflight, validated nullable primary-defect FK, catalog eligibility/used-policy guards and existing temporal-order reinforcement. |

   Existing cases without an approved mapping must block 018 instead of receiving a
   guessed family. Flyway's earlier committed 017 allows operator-approved metadata
   provisioning before retry. Legacy orphan/wrong-family/time inconsistency requires
   reconciliation from real evidence, with no manufactured defect, time, actor or taxonomy.
   No published migration is rewritten and the existing mandatory case-type FK stays.
8. Add focused application/adapter/domain/owner tests: optional-null and missing/valid defect,
   explicit mapping/family/eligibility failure, canonical fresh target and historical snapshots,
   wrong typed Topology/Workflow context, optional actor/unit rejection, unchanged inactive
   history, and preserved programme/assessment behavior. Real PostgreSQL/Spring tests cover
   migration failure without rewrite, mapping-provisioning retry, nullable FK/delete races,
   concurrent catalog reclassification and reference-write rollback. Run compile, focused/
   existing/architecture tests, full test and clean verify; report actual local limitations.
   Publish once on main, confirm final production CI starts, then stop for owner next/fail.

### Exhaustive proposed HMR-098 write scope

Only needed paths from this scope may change after acceptance. Original historical scope
is superseded for this execution only; unaffected files need no cosmetic changes.

- `docs/data definition/Integrity.md`
- `docs/roadmap/model-semantic-remediation.md`
- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/request/OpenIntegrityCaseRequest.java`
- `src/main/java/dz/sh/hidra/modules/integrity/api/rest/response/IntegrityCaseResponse.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/command/OpenIntegrityCaseCommand.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/dto/IntegrityCaseSummaryDto.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/in/OpenIntegrityCaseUseCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/IntegrityCaseRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/model/IntegrityCase.java`
- `src/main/java/dz/sh/hidra/modules/integrity/domain/value/IntegrityCaseStatus.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/JpaIntegrityCaseRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityCaseJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/entity/IntegrityCaseStatusHistoryJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/mapper/IntegrityPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/IntegrityCaseJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/IntegrityCaseStatusHistoryJpaRepository.java`
- `src/test/java/dz/sh/hidra/modules/integrity/semantic/IntegrityCaseSemanticRemediationTest.java`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `src/main/resources/db/migration/V20261008_017__hmr_098_integrity_case_catalog_field_policy.sql`
- `src/main/resources/db/migration/V20261008_018__hmr_098_integrity_case_reference_integrity.sql`
- `src/main/java/dz/sh/hidra/modules/integrity/application/service/IntegrityApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/integrity/application/port/out/PipelineDefectRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/IntegrityCaseReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/configuration/IntegrityCatalogFieldPolicy.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/IntegrityCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/repository/PipelineDefectJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/integrity/IntegrityCaseTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/integrity/package-info.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/integration/IntegrityCaseTopologyReferenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/contract/integrity/IntegrityCaseActorReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/identity/application/service/IntegrityCaseActorReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/contract/integrity/IntegrityCaseWorkflowReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/workflow/application/service/IntegrityCaseWorkflowReferenceQueryService.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/integrity/semantic/IntegrityProgramSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/IntegrityCaseSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/integration/IntegrityCaseTopologyReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/identity/semantic/IntegrityCaseActorReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/workflow/semantic/IntegrityCaseWorkflowReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/organization/semantic/IntegrityOrganizationUnitReferenceContractTest.java`

### Current disposition and preflight validation

HMR-098 is BLOCKED pending IC-PREREQ-01 acceptance. HMR-080 remains independently
BLOCKED. Current totals: 49 CI-confirmed implementations, six STILL REQUIRED and two
BLOCKED, 57 evaluated. HPR-P2-008 remains open. After accepted HMR-098 implementation,
follow the next attached row under fresh admission; do not select a later legacy code
merely because it appears numerically adjacent.

This preflight's exact write scope is only `doc/roadmap/ULTIMATE_ROADMAP.md` and
`doc/model-remediation/RECONCILIATION.md`. Exact supporting commit:
`docs(integrity): record Batch 18 execution preflight`.

Validate all canonical Markdown as UTF-8/nonempty/conflict-marker-free, exact two-file
scope, unchanged production/tests/CI/POM/migrations and git diff --check. No Maven or
PostgreSQL execution is claimed for this docs-only change. Trigger applicable Documentation
Validation on main, confirm it starts, then stop. Owner next accepts IC-PREREQ-01 and
this concrete design/exhaustive scope, subject to fresh green-baseline verification;
an amendment may narrow the proposal first. Production implementation has not started.

Preflight checks completed: all 82 canonical Markdown files passed the exact documentation
workflow validation. Exact two-file scope and unchanged production/tests/CI/POM/published
migrations were verified; git diff --check passed. Forty-two proposed execution paths
are explicitly registered, including forward 017/018 and required constructor fixtures.
No production implementation or Maven/PostgreSQL test is claimed by this preflight.

## HPR-P2-008 Batch 18 accepted execution envelope — 2026-10-08

Owner next accepts IC-PREREQ-01 on preflight 15a97be510bb227536c5682be17f703e14d7fe8d.
Main still matches that head; Documentation CI #101 (37791702797) is green. Production
remains byte-identical to cfb3681ef1c79b4416336a3533cbc0599b4fd6b2, full CI #597 green.
The proposal above and its exhaustive 42-path scope are now admitted for HMR-098 alone.
Exact semantic message and forward 017/018 stay registered; no published SQL, foreign
FK, later HMR, branch, PR or release is admitted. Use only needed paths. Record actual
local verification and final-head CI start before stopping for owner next/fail.

### Batch 18 HMR-098 implementation result — 2026-10-08

HMSR-115 recovered independently and IC-PREREQ-01 accepted. Opening commands validate
populated optional primaryDefectId through the own repository before case persistence.
The transactional adapter locks an existing case and validates the supplied defect via
shared own-row lookup, explicit CASE_TYPE field-to-family metadata and exact catalog
membership. Fresh/changed type references require active mapping and entry; no catalog
family, ID or code is guessed or seeded. Valid unchanged inactive history retains its
stored provenance. Unknown defect and invalid references fail closed before saving.

New/changed Topology linkage resolves through a narrow owner export for PIPELINE,
SEGMENT, FACILITY, EQUIPMENT, NODE and CONNECTION. Actual owner ID/code is used;
unsupported namespaces and missing typed targets deny. Fresh code snapshots are canonical;
unchanged history is never refreshed, and snapshot overwrite is rejected. Populated new/
changed actor and unit use Identity/Organization. Workflow validates the actual Integrity
case ID/module, active INTEGRITY_CASE target type and purpose plus configured binding.
Context does not prove approval. Neutral optional HSE/Incident sources, optional severity,
all 20 fields, stable statuses and current timestamp ordering retain their semantics.
No defect status/topology equality, required optional reference, stronger CLOSED/time
coupling, Workflow start, actor-authentication mandate or cross-module FK is invented.

Forward 017 creates unseeded owner policy metadata. Forward 018 validates legacy family,
nullable defect provenance and temporal order, adds validated ON DELETE RESTRICT local
defect integrity and time-order reinforcement, and guards taxonomy eligibility/used mapping
and catalog identity/family. Used taxonomy cannot be reassigned, erased or truncated.
The existing mandatory case-type FK stays. Existing cases without approved metadata block
018; operator-approved configuration after independently committed 017 allows retry.
Legacy orphan/wrong-family/time errors require evidence-backed reconciliation; migrations
never fabricate or rewrite historical records. Fresh case writes require approved mapping
as an explicit deployment prerequisite. Noop reference resolver is not used as owner proof.

Validation actually performed: all 23 changed Java files parsed. Changed production/owner
sources, real Integrity domain/entities/mappers and 30 focused unit plus 12 PostgreSQL/
Spring-JPA method signatures compiled on Java 17 against temporary dependency APIs.
Fifty-three actual source-level behavior checks passed with controlled ports/repositories:
30 domain/application/adapter and 23 real owner-provider checks. These do not constitute
JUnit, Spring, Hibernate, database locking, rollback or PostgreSQL execution. Twenty-six
new focused unit methods plus four existing programme methods and twelve actual PostgreSQL
methods are prepared; constructor fixtures preserve prior programme behavior. PostgreSQL
coverage includes optional/local reference rejection, fail-closed legacy rollback without
fabricated repair, mapping-provisioning retry, inactive history, taxonomy protection,
ordering, defect deletion and catalog/mapping races, and actual JPA rollback after flush.

All 129 previously published SQL migrations are byte-identical to the baseline. Exact
admitted write scope, both architecture exports, canonical Markdown validation and
whitespace checks are performed before publication. Maven compile, focused/owner/existing/
architecture tests, full test and clean verify were attempted via bash mvnw -o -B -q;
all stop before compilation/test execution at uncached Spring Boot parent 4.1.1 offline
resolution. Java 21, PostgreSQL and Docker are absent locally. Actual Java 21/full Flyway/
Spring/PostgreSQL/architecture/OpenAPI validation remains the final-head CI obligation.

| Subject | Current disposition | Evidence |
|---|---|---|
| HMR-098 / IntegrityCase | Completed implementation | Pending final-head CI |
| IC-PREREQ-01 | Accepted and implemented | Explicit metadata/contracts/admitted scope |
| HMR-080 | Blocked | Independent unresolved prerequisite |

Current total: 50 implementations (49 CI-confirmed, one awaiting CI), six STILL REQUIRED
and one BLOCKED, 57 evaluated. HPR-P2-008 is not finally closed. Exact semantic message:
`fix(integrity): remediate semantic review IntegrityCase`. Publish once to main, confirm
production CI starts, then stop for owner next/fail. No branch/PR/release or later task.
Next is attached row 19, subject to fresh admission and green baseline; no numerically
adjacent legacy subject is automatically selected by this solo envelope.

## HPR-P2-008 Batch 19 Planning targets and Monitoring execution preflight — 2026-10-08

Owner next selects attached row 19: HMR-094 / PlanTarget followed by HMR-103 /
PlanActualDeviation. This preflight starts from main
0fe3b6b72535ac5211007a2ebc16cc19d22454a6, a documentation-only child of
863d113fbee88f71ff7e2c3b593b415e5b70d07a. Full production CI #598
(37794566250) passed on that production-identical parent; Documentation Validation
#102 passed there and #103 (37794886938) passed on current main. HMR-098 is now a
CI-confirmed implementation. These facts do not close HPR-P2-008 or substitute for
physical survivability evidence.

### PTMD-PREREQ-01 — independently recovered write-policy and owner-contract gaps

HMSR-111 and HMSR-120 were independently recovered from
`docs/roadmap/model-semantic-review.md`, sections 124 and 133, and checked against
current production, DDD, repository contracts, architecture exports and migrations.

| Current source | Finding |
|---|---|
| `planning/domain/model/PlanTarget.java` | Existing 22-field record permits blank topologyAssetType and lacks target-type-driven value checks. Optional nomination/scenario/point references remain nullable. |
| `planning/infrastructure/persistence/adapter/JpaPlanTargetRepositoryAdapter.java` | save merges directly, without own parent/revision compatibility, locked reference validation, catalog-family or value-policy checks. There is no authoritative PlanTarget creation service to extend. |
| `docs/data definition/Planning.md`, sections 6.7 and catalog definition | TARGET_TYPE is an explicit authoritative family. Numeric targets require targetValue and unitId. Non-numeric representations depend on target semantics; there is no per-entry representation configuration in live catalog/schema. Dynamic catalog IDs/codes must not be guessed into numeric/text classifications. |
| `topology/application/contract/planning/PlanningTopologyScopeContract.java` and provider | Existing plan scope provider resolves PIPELINE_SYSTEM, PIPELINE and FACILITY. It does not cover all existing typed asset namespaces used elsewhere by owner providers. Scope and target contracts must retain their distinct meanings. |
| `telemetry/application/port/in/TelemetryQueryUseCase.java` | Reading/time-series API has no point-by-ID or trusted-reading-by-ID authority. Existing MonitoringTelemetryPointReferenceContract provides owner-controlled point existence, but is insufficient for trusted reading identity/point provenance or a Planning code snapshot. |
| `planning/application/port/in/PlanningQueryUseCase.java` and application/port/out/PlanningQueryPort.java | Public query API exposes targets as views, but current architecture admits narrowly exported contract packages. An internal domain-returning output port is not a legal Monitoring import. No narrow Monitoring target authority exists. |
| `monitoring/application/service/DeviationApplicationService.java` | Live record path copies mandatory target and optional evaluation/Telemetry IDs without owner validation. This service is excluded from original HMR-103 scope. |
| `monitoring/infrastructure/persistence/adapter/JpaPlanActualDeviationRepositoryAdapter.java` | Alternate direct saves bypass authoritative reference checks. Supplied evaluation requires an own lookup and context validation; MonitoringEvaluation exists as an own JPA entity/repository. |
| Published migration tail / historical HMR registrations | Latest published migration is V20261008_018. Unexecuted V20261004_094 and V20261004_103 registrations are backdated; forward migrations must be explicitly registered without editing published SQL or enabling out-of-order execution. |

Original HMR-094 and HMR-103 scopes contain 12 and 10 paths respectively. They omit
required owner providers, representation-policy metadata, own parent lookup repositories,
the live deviation service, actual PostgreSQL tests and architecture admission. AGENTS.md
section 3.2 rule 9 says: "If an HMR reveals an unregistered prerequisite, SCC complication,
owner-contract gap, cross-module lifecycle dependency, migration-order conflict, or materially
larger semantic redesign, split it out and stop before mutating that HMR."
HMR-094 and dependent HMR-103 are therefore BLOCKED on PTMD-PREREQ-01 acceptance;
this supporting preflight changes only the two canonical execution-memory documents.

### Concrete proposed two-commit execution envelope

1. Admit exactly HMR-094 then HMR-103 in attached order, one independent semantic commit
   each: `fix(planning): remediate semantic review PlanTarget`, then
   `fix(monitoring): remediate semantic review PlanActualDeviation`. Their dependency is
   one-way: Monitoring consumes a Planning-owned scalar contract. Introducing that export
   does not make Planning depend on Monitoring implementation or create an SCC. Publish
   the final chained batch head to main once under an expected-SHA lease; no branch/PR.
2. HMR-094 preserves the existing 22-field API/domain/JPA shape, stable target statuses,
   optional references, validity-order behavior and tolerated equality. HMSR-111 explicitly
   declines a stronger strict interval rule despite the older DDD notation. No arbitrary
   tolerance sign/order, lifecycle transition, unit owner validation, numeric recomputation,
   target creation endpoint or universal ACTIVE-target requirement is invented.
3. Require nonblank topologyAssetType in the domain. At the transactional adapter boundary,
   lock an existing target, validate the own revision, optional nomination and scenario, and
   require supplied own references to belong to that same revision. Shared parent locks and
   validated nullable local FKs with revision compatibility prevent dangling references and
   parent-reassignment/delete races. Reinforcement must not weaken existing HMR-064 approved
   revision immutability or alter another aggregate's lifecycle.
4. Resolve exact TARGET_TYPE catalog membership. Introduce Planning-owned unseeded
   `hidra_planning_target_value_policy` keyed by the actual target-type entry ID, with an
   explicit representation kind NUMERIC or TEXT and active flag. These are technical policy
   discriminators, not hard-coded business target IDs/codes or new catalog entries. Numeric
   policy requires a numeric value and nonblank unit; TEXT policy requires nonblank text.
   Do not forbid an additional representation absent an explicit owner exclusivity rule.
   Missing/inactive fresh policy or wrong-family entry fails closed. Shared policy/catalog
   locks and database guards protect used identity, family and representation from mutation,
   deletion or truncation. Valid unchanged inactive history remains readable and can retain
   its existing mapping; changed type/value semantics must meet the configured policy.
5. Policy installation and validation are separate forward migrations: 019 commits an empty
   metadata structure; 020 fails if legacy targets lack approved policy or have orphan,
   cross-revision, family, value or required-type inconsistencies. Owner-approved actual
   representation mappings may then be provisioned before retrying 020. No guessed seed,
   automatic historical repair, fabricated parent or data rewrite is allowed. Existing
   generic target-type and revision FKs remain; local nullable references gain integrity.
6. Add a narrow Topology-owned PlanningTargetTopologyReferenceContract/provider in the
   already exported Planning package. Resolve actual owner identities for PIPELINE_SYSTEM,
   PIPELINE, SEGMENT, FACILITY, EQUIPMENT, NODE and CONNECTION, using the appropriate
   existing owner repositories. Unsupported/wrong namespace, mismatched identity or missing
   asset denies new/changed linkage; do not alter existing plan-scope behavior or alias types.
   Fresh code snapshot comes from the owner. Unchanged historical snapshots are retained
   without live refresh and cannot be overwritten as arbitrary caller-supplied provenance.
7. Add a narrow Telemetry-owned Planning point contract/provider returning actual ID/code.
   A populated new/changed point must resolve through its real owner; null stays legal.
   Fresh supplied point linkage stores the actual code snapshot and unchanged history is
   retained. No cross-module FK or consumer import of Telemetry persistence/domain occurs.
8. HMR-094 exports a narrow Planning-owned MonitoringPlanTargetReferenceContract/provider
   with exact target ID, revision, topology namespace/ID, optional point ID and existing
   status/value/validity context as needed. It resolves through own persistence/ports; absent
   target denies. Historical target query remains readable, and an arbitrary new status/time
   eligibility matrix is not imposed. Register only the new narrow contract packages in
   both architecture suites; do not export internal application services/output ports.
9. HMR-103 introduces DeviationReferenceValidation at the live record path and an own
   transactional adapter validator for direct saves. Mandatory Planning target must resolve
   exactly before persistence. New/changed populated Telemetry point uses existing owner
   existence authority; new MonitoringTrustedReadingReferenceContract/provider resolves an
   actual trusted reading and scalar point/trust provenance. Source trust eligibility remains
   Telemetry's existing governed meaning; do not invent a stricter trust-level policy or
   fabricate reading evidence. If a reading and point are both supplied, their identities
   must agree. Null optional reading/point/evaluation remains legal. Existing stored source
   snapshots are not refreshed during unrelated historical updates.
10. Supplied Monitoring evaluation must exist. Validate each populated evaluation context
    (plan revision, topology type/ID and point) against the resolved target/deviation/evidence
    context; missing optional context is not fabricated or made universally mandatory.
    Lock the own evaluation reference for the write and reinforce nullable local evaluation
    FK/context protection in forward 021. Do not require successful/completed evaluation,
    update evaluation counters, generate evidence or start another lifecycle. Preserve
    optional expectedFlowStateId and neutral unit/topology snapshots without inventing
    obligations HMSR-120 explicitly declines. Existing severity fallback, 20-field shape,
    status/severity enums and numeric fields keep their behavior; no arithmetic recomputation,
    rounding/zero-denominator policy or universal non-null value matrix is admitted.
11. Keep own-module database race protection and cross-owner checks distinct: external
    contracts establish reference evidence at write time, not relational lifetime guarantees.
    No Planning-to-Topology/Telemetry or Monitoring-to-Planning/Telemetry FK is authorized.
    No always-true Noop validator is accepted as owner evidence. Make transactional classes
    proxyable; record operations validate before save and roll back failed flushed writes.
12. Prepare focused unit/domain/application/adapter and actual owner-provider tests plus real
    PostgreSQL/Spring-JPA migration/rollback/race tests. Cover missing policies/retry, numeric
    and text shapes, wrong family, inactive history, missing/wrong typed asset, optional-null
    references, cross-revision parents and reparent/delete races, canonical snapshots, unknown
    target/point/reading/evaluation, reading-point mismatch, populated evaluation coherence,
    direct-save bypass rejection and preserved severity behavior. Run compile, both focused
    semantic suites, owner suites, architecture suites, full test and clean verify. Distinguish
    source/fixture checks from real JUnit, Java 21, Spring and PostgreSQL execution.

| Subject | Forward migrations | Focused suite | Exact semantic commit |
|---|---|---|---|
| HMR-094 | V20261008_019__hmr_094_planning_target_value_policy.sql; V20261008_020__hmr_094_planning_plan_target_integrity.sql | PlanTargetSemanticRemediationTest | fix(planning): remediate semantic review PlanTarget |
| HMR-103 | V20261008_021__hmr_103_monitoring_plan_actual_deviation_integrity.sql | PlanActualDeviationSemanticRemediationTest | fix(monitoring): remediate semantic review PlanActualDeviation |

### Exhaustive proposed HMR-094 scope

Only needed paths below may change in its semantic commit after acceptance. Original
historical scope/migration registration is superseded for this execution only.

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/data definition/Planning.md`
- `docs/roadmap/planning.md`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/PlanTargetRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/PlanTarget.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/value/PlanTargetStatus.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaPlanTargetRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/PlanTargetJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanTargetJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/PlanTargetReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/configuration/PlanningTargetValuePolicy.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanningCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanRevisionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/NominationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/PlanScenarioJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/contract/monitoring/MonitoringPlanTargetReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/contract/monitoring/package-info.java`
- `src/main/java/dz/sh/hidra/modules/planning/application/service/MonitoringPlanTargetReferenceQueryService.java`
- `src/main/java/dz/sh/hidra/modules/topology/application/contract/planning/PlanningTargetTopologyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/topology/infrastructure/integration/PlanningTargetTopologyReferenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningTelemetryPointReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/PlanningTelemetryPointReferenceQueryService.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/PlanTargetSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/PlanTargetSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/MonitoringPlanTargetReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/topology/infrastructure/integration/PlanningTargetTopologyReferenceContractTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/PlanningTelemetryPointReferenceContractTest.java`
- `src/main/resources/db/migration/V20261008_019__hmr_094_planning_target_value_policy.sql`
- `src/main/resources/db/migration/V20261008_020__hmr_094_planning_plan_target_integrity.sql`

### Exhaustive proposed HMR-103 scope

Only needed paths below may change in its independent semantic commit after HMR-094.
No alteration of HMR-094 production files is admitted by this downstream envelope.

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `docs/roadmap/model-semantic-remediation.md`
- `docs/data definition/Monitoring.md`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/port/out/PlanActualDeviationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/domain/model/PlanActualDeviation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/JpaPlanActualDeviationRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/entity/PlanActualDeviationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/mapper/MonitoringPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/repository/PlanActualDeviationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/application/service/DeviationReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/PlanActualDeviationReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/repository/MonitoringEvaluationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/monitoring/MonitoringTrustedReadingReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/service/MonitoringTrustedReadingReferenceQueryService.java`
- `src/test/java/dz/sh/hidra/modules/monitoring/semantic/PlanActualDeviationSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/PlanActualDeviationSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/MonitoringTrustedReadingReferenceContractTest.java`
- `src/main/resources/db/migration/V20261008_021__hmr_103_monitoring_plan_actual_deviation_integrity.sql`

### Current disposition and preflight validation

| Subject | Disposition | Reason |
|---|---|---|
| HMR-098 / Batch 18 | COMPLETED — CI #598 GREEN | Full production run passed on 863d113fbee88f71ff7e2c3b593b415e5b70d07a. |
| HMR-094 / HMSR-111 | BLOCKED — PTMD-PREREQ-01 | Policy, owner-provider and forward-migration scope awaiting acceptance. |
| HMR-103 / HMSR-120 | BLOCKED — PTMD-PREREQ-01 | Depends on admitted HMR-094 export plus live record/owner/evaluation scope. |
| HMR-080 | BLOCKED | Independent unresolved prerequisite remains. |

Current totals: 50 CI-confirmed implementations, four STILL REQUIRED (HMR-100/104/105/106)
and three BLOCKED (HMR-080/094/103), 57 evaluated. HPR-P2-008 remains open. Earlier
snapshot totals remain historical; this is the current reconciliation disposition.

This preflight's exact write scope is only `doc/roadmap/ULTIMATE_ROADMAP.md` and
`doc/model-remediation/RECONCILIATION.md`. Exact supporting commit:
`docs(planning): record Batch 19 execution preflight`.

Validate all canonical Markdown with the repository documentation workflow, exact two-file
scope, git diff --check and byte-identical production/tests/CI/POM/published migrations.
No production implementation, Maven test or PostgreSQL execution is claimed here.
Documentation-only edits trigger Documentation Validation, not the production workflow.
Confirm the applicable documentation run starts, then stop for owner next/fail. Owner next
accepts PTMD-PREREQ-01 and this concrete ordered two-commit design/exhaustive scope,
subject to fresh baseline verification. An amendment can narrow the proposal first.

Preflight checks completed: all 82 canonical Markdown files passed repository workflow
validation; exact two-file scope and git diff --check passed. Production, tests, CI, POM
and all 131 published SQL migrations remain byte-identical to baseline. HMR-094 has
35 explicit proposed paths and HMR-103 has 20, with independent messages and forward
migrations. No implementation or runtime test is claimed by this supporting commit.

## HPR-P2-008 Batch 19 accepted execution — HMR-094 — 2026-10-08

PTMD-PREREQ-01 is **ACCEPTED**: the owner's explicit Next accepted the complete
twelve-part preflight design, independent file scopes, forward migrations and ordered
two-commit envelope. Acceptance is already granted; no repeat acceptance is required.
The prior preflight BLOCKED/awaiting-acceptance disposition is historical and superseded.

Fresh GitHub main is 8670579fe1a2945191fbb32ea5147355d3343d50. Its production,
tests, build and workflow trees match 863d113fbee88f71ff7e2c3b593b415e5b70d07a.
Production CI #598 (37794566250) and exact preflight documentation run #104
(37796482087) were independently observed completed/success.

HMSR-111 section 124 was independently recovered from the current review blob
0ca320f4783ec26a46ce7c9469c9ef850596cf09 and checked against current PlanTarget,
its mapper/entity/repository, parents, owner providers and published SQL. HMSR-120
section 133 was recovered independently for the downstream task before mutation.

| Subject | Current status | Evidence |
|---|---|---|
| HMR-094 / HMSR-111 | IMPLEMENTED — FINAL CI PENDING | 22-field shape retained; required topology namespace, locked own parents/revision compatibility, exact TARGET_TYPE, owner-approved NUMERIC/TEXT policy, canonical fresh owner snapshots and historical snapshot retention. |
| HMR-103 / HMSR-120 | IN PROGRESS — ACCEPTED | Downstream independent commit; only its admitted scope may change. |
| HMR-080 | BLOCKED | Existing unresolved owner contract is unchanged. |

Forward 019 creates empty value-policy metadata keyed by actual catalog entry ID.
Forward 020 checks legacy mappings and integrity before installing validated local
FKs, composite nullable nomination/scenario revision FKs, shape checks and metadata
race protection. Published migrations are unchanged. Missing legacy mappings stop
020; an owner must provision actual approved metadata before retry. No seed, repair,
cross-module FK, business classification switch or exclusivity rule is introduced.

New narrow contracts are Topology Planning target identity, Telemetry Planning point
identity/code and Planning Monitoring target context. Only the two new public package
exports are admitted in both architecture suites; implementation packages remain private.
Noop validators are not used. Approved revision immutability remains in force.

Prepared validation: seven PlanTarget semantic methods, three actual owner-provider
methods, and ten PostgreSQL/Spring-JPA methods covering migration abort/retry,
nullable and cross-revision parents, approved revision immutability, inactive history,
canonical snapshots, flushed rollback, direct-save denial and concurrent delete,
reparent and metadata mutation. These are real JUnit/Testcontainers/Spring-JPA test
sources, not evidence that runtime execution has passed.

Required commands:

- `./mvnw -q -DskipTests compile`
- `./mvnw -q -Dtest=PlanTargetSemanticRemediationTest test`
- `./mvnw -q -Dtest=MonitoringPlanTargetReferenceContractTest,PlanningTargetTopologyReferenceContractTest,PlanningTelemetryPointReferenceContractTest,PlanTargetSemanticPostgresIntegrationTest test`
- `./mvnw -q -Dtest=ArchitectureGuardrailTest,ForensicRemediationClosureTest test`
- `./mvnw -q test`
- `./mvnw -q clean verify`

Current environment assessment: this session exposes GitHub and JavaScript orchestration,
but no shell/Java/Maven/PostgreSQL execution tool. No command above has been executed
here; dependency cache or DNS failures from prior environments are not assumed.
Scope, scalar-owner imports, field-shape retention, forward names, whitespace and
documentation checks can be inspected directly. Runtime results remain pending
production CI; CI runs `./mvnw -B -q clean verify` on Java 21 with PostgreSQL/Docker.
Do not describe source checks as Java compilation, ArchUnit or PostgreSQL execution.

The two exact semantic messages remain `fix(planning): remediate semantic review PlanTarget`
then `fix(monitoring): remediate semantic review PlanActualDeviation`. Chain both
commits and advance main once under the expected-head lease. Observe production CI
start and stop for owner Next/Fail. HPR-P2-008 remains open, 0.6.0-SNAPSHOT remains
unchanged, and this work supplies no physical survivability evidence.

## HPR-P2-008 Batch 19 independent execution — HMR-103 — 2026-10-08

PTMD-PREREQ-01 remains ACCEPTED. HMR-094 is the independent ordered parent semantic
commit; HMR-103 changes only its own admitted production/test paths and execution memory.
HMSR-120 section 133 was checked against the current 20-field domain/entity/mapper,
live record command/service, own evaluation repository and actual Telemetry authority.

| Subject | Current status | Evidence |
|---|---|---|
| HMR-094 / HMSR-111 | IMPLEMENTED — FINAL CI PENDING | Independent Planning commit with forward 019/020 and narrow target export. |
| HMR-103 / HMSR-120 | IMPLEMENTED — FINAL CI PENDING | Live record and direct-save validation, mandatory Planning owner lookup, fresh optional point/reading owner evidence and locked evaluation context; forward 021. |
| HMR-080 | BLOCKED | Independent unresolved owner contract remains. |

DeviationReferenceValidation is an application-owned validation boundary implemented
by the own persistence validator. Both the proxyable transactional live record service
and the transactional direct-save adapter validate before saveAndFlush. Mandatory target
identity resolves through the Planning scalar contract. Fresh/changed populated Telemetry
references resolve through their owners; populated reading and point identities agree.
Each populated evaluation revision/topology/point context is compared with available
resolved target/deviation/reading evidence. Missing optional context is not fabricated.

The 20-field shape, optional expected flow state/unit and neutral topology snapshots,
numeric behavior and existing severity fallback are preserved. Unchanged historical
owner evidence and snapshots are not refreshed. No successful-evaluation requirement,
counter update, arithmetic recalculation or new Telemetry trust threshold is introduced.

Forward 021 fails on orphan/inconsistent local evaluation history without rewriting
records. Its nullable local FK and shared evaluation/context guards protect deletion,
reassignment and truncation races. It contains no Monitoring-to-Planning/Telemetry FK.
External contracts establish write-time evidence, not cross-owner lifetime guarantees.

Six focused deviation methods, one actual trusted-reading owner-provider method and
eight PostgreSQL/Spring-JPA methods are prepared. Tests cover live-path/direct-save
denial, optional nulls, unknown target/point/reading/evaluation, point disagreement,
evaluation coherence, fallback severity, migration abort, flushed rollback and races.

Additional required commands:

- `./mvnw -q -Dtest=PlanActualDeviationSemanticRemediationTest test`
- `./mvnw -q -Dtest=MonitoringTrustedReadingReferenceContractTest,PlanActualDeviationSemanticPostgresIntegrationTest test`

The compile, both architecture suites, full test and clean verify commands from the
HMR-094 record also apply to the complete final tree. No shell/Java/Maven/PostgreSQL
execution tool is exposed in this session, so no required Maven/JUnit/database command
has run locally and none is reported passed. Static source/scope/forward-migration and
documentation checks are separate from runtime verification.

Current reconciliation: 50 CI-confirmed implementations, two IMPLEMENTED — FINAL CI
PENDING (HMR-094/103), four STILL REQUIRED (HMR-100/104/105/106), and one BLOCKED
(HMR-080), 57 evaluated. Pending implementations are not counted as CI-confirmed.
HPR-P2-008 remains open. Project version remains 0.6.0-SNAPSHOT; no new physical
survivability evidence is claimed. Publish the chained two-commit head to main once
under the expected 8670579fe1a2945191fbb32ea5147355d3343d50 lease, verify both
published trees and confirm production CI starts, then STOP for owner Next/Fail.
Attached row 20 is only a future recommendation subject to its own admission; it is not
executed by Batch 19. A failure is repaired in the responsible scope before advancing.

Static preparation checks completed in the available JavaScript environment: exact
independent allowlists, nonempty content, added-line whitespace, canonical Java headers,
balanced source delimiters, owner-contract-only cross-module imports, retained 22/20
domain field counts and same-module forward FK endpoints passed. All 82 canonical
Markdown files passed the documentation workflow's nonempty/conflict-marker checks.
These checks do not execute javac, Maven, ArchUnit, Spring or PostgreSQL.

HMR-094 exact independent parent commit: `3845b7d30637ec9fc7306a527a0ae569b501fa89`;
verified tree: `30d59eb45882dd01b52956b0ce63e00a2ef72f3a`. Its exact 31 changed paths and
every prepared UTF-8 file content were compared with GitHub's immutable tree/blobs;
no other baseline blob changed. The downstream tree will be checked independently
before the single main ref advancement.

## HPR-P2-008 Batch 19 CI #599 repair — HMR-094 — 2026-10-08

Owner Fail authorizes diagnosis and repair of the responsible Batch 19 scope.
Fresh main remains 74ea302432eff8434466d7162134b99923ce3161. Production CI #599
(37800524622), job 113391171981, failed in production Java compilation before test,
migration, JPA, race or OpenAPI execution. Documentation CI #105 passed.

The HMR-094 PlanningTargetTopologyReferenceQueryAdapter switch expression was the
receiver of Optional.filter. Its untyped default Optional.empty caused Java to infer
an Optional of a captured Object type. The filter therefore could not call Asset.id
or Asset.code, and its result could not satisfy Optional<Asset>. This was a source
compilation defect in HMR-094, not missing owner policy data or an HMR-103 failure.

Repair: give the switch result an explicit local Optional<Asset> target type, then
apply the existing identity/nonblank-code filter. Seven typed namespaces, unknown
namespace denial, owner repositories and snapshot meaning remain identical.
Exact registered semantic message remains
`fix(planning): remediate semantic review PlanTarget`; this is a narrow follow-up
repair, not an amendment or rewrite of either published Batch 19 commit.

Exact write scope is the provider above plus
`doc/roadmap/ULTIMATE_ROADMAP.md` and `doc/model-remediation/RECONCILIATION.md`.
No test, migration, POM, workflow, Monitoring implementation or later task changes.

Current environment now exposes a shell and Java 17 compiler module. The original
actual provider and scalar contract were compiled against temporary dependency stubs
and reproduced the same inference failure. The repaired actual source compiled with
`java com.sun.tools.javac.Main`; its temporary executable smoke covered all seven
namespaces and unknown/alias/missing/mismatched/blank-code/null denial. The provider's
actual Git diff passed `git diff --check`. These checks do not constitute Java 21,
real Spring/JPA, repository JUnit, ArchUnit or PostgreSQL execution.

A fresh Maven preflight using the exact current POM/wrapper attempted
`./mvnw -q -DskipTests compile` and exited 1: Spring Boot parent 4.1.1 is absent
from the local cache and repo.maven.apache.org has temporary DNS resolution failure.
Only Java 17 is installed. Required focused/owner/architecture/full-test/clean-verify
runtime results remain pending replacement Java 21/PostgreSQL CI; no pass is inferred
from this isolated stub check.

HMR-094 is IMPLEMENTED — CI REPAIR PENDING; HMR-103 remains IMPLEMENTED — FINAL CI
PENDING because CI #599 did not reach its tests. Reconciliation remains 50 CI-confirmed,
two pending implementations, four STILL REQUIRED Alarm subjects and HMR-080 BLOCKED.
HPR-P2-008 remains open; version and physical survivability evidence are unchanged.
Publish this exact three-file follow-up under the current-head lease, verify its tree,
confirm replacement production CI starts, then STOP for owner Next/Fail. No Batch 20.

## HPR-P2-008 Batch 19 CI closure and Batch 20 execution preflight — 2026-10-08

### Verified position and attached selection

Owner Next requests continuation after the responsible HMR-094 repair. Fresh GitHub
main is `0cc3e5c6c4b675880a634b3a073905f4119a36b7`, tree
`b7bd9cc4992596b838a3544eea4523a156498bdd`. Production CI #600
([run 37801562605](https://github.com/CHOUABBIA-AMINE/HidraAPI/actions/runs/37801562605))
completed SUCCESS on that exact head. Its Java 21 repository-verification step ran
`./mvnw -B -q clean verify`; Docker/Testcontainers and PostgreSQL execution are
visible in the job log. Deterministic OpenAPI publication and compatibility also passed.
Quiet Maven output does not provide a per-method count; none is invented.

HMR-094 / HMSR-111 and HMR-103 / HMSR-120 are now COMPLETED — CI #600 PASSED.
Their independently published semantic commits remain
`3845b7d30637ec9fc7306a527a0ae569b501fa89` and
`74ea302432eff8434466d7162134b99923ce3161`; the narrow HMR-094 repair is
`0cc3e5c6c4b675880a634b3a073905f4119a36b7`. PTMD-PREREQ-01 was already
accepted and is not reopened. Reconciliation is 52 CI-confirmed implementations,
four still-required Alarm subjects and one separately blocked HMR-080, 57 evaluated.

The owner attachment `00 - Batchs Roadmap.txt` was recovered in full: row 20 is
Alarm lifecycle, HMR-100 then HMR-104, HMR-105 and HMR-106. It requests atomic
creation, acknowledgement, closure, shelving, expiry and lifecycle events.
This selects the next preflight; it does not override the prerequisite stop rule.

### Live HMSR recovery and prerequisite disposition

HMSR-117 section 130, HMSR-121 section 134, HMSR-122 section 135 and HMSR-123
section 136 were recovered independently against current source at the exact head.
The Alarm DDD is supporting evidence; its stale no-implementation opening is not
current implementation truth.

| HMR | Live evidence | Remaining obligation |
|---|---|---|
| HMR-100 / HMSR-117 | 37-field Alarm; required French title only normalized; catalog FKs prove existence only; raise saves no event; ack/close save evidence only | Correct title and ALARM_TYPE/ALARM_SEVERITY/optional ALARM_PRIORITY membership; atomic RAISED event and coherent acknowledgement/closure. |
| HMR-104 / HMSR-121 | 9-field acknowledgement; local Alarm FK; live service neither loads Alarm nor updates snapshots/events | Locked eligibility, evidence + state/snapshot + exactly one event; multiple acknowledgements remain legal. |
| HMR-105 / HMSR-122 | 10-field closure; local Alarm FK; no one-closure uniqueness; live guard permits uncleared ESCALATED | Clear-before-close unless cancelled, terminal snapshot/event and one closure per Alarm. |
| HMR-106 / HMSR-123 | 11-field shelving; live creation checks end time but not eligibility/family; no partial uniqueness; explicit unshelve only | Intrinsic interval, SHELVING_REASON family, serialized one-ACTIVE rule, state/event synchronization and deterministic expiry. |

The four legacy scopes do not together register the needed application-owned lifecycle
event port/model/adapter, PostgreSQL/Spring tests or shelving expiry path. HMR-104/105
omit the live shared service. The legacy 20261004 migrations would be inserted behind
the published 20261008_021 tail. Existing suppression writes share the Alarm row and
currently read it without the common lifecycle lock, so adding isolated shelving locks
would leave a lost-update race.

AGENTS.md §3.2.9 requires: "If an HMR reveals an unregistered prerequisite, SCC
complication, owner-contract gap, cross-module lifecycle dependency, migration-order
conflict, or materially larger semantic redesign, split it out and stop before mutating
that HMR." Section 3.2.10 keeps lifecycle orchestration solo by default unless this
roadmap explicitly authorizes combined execution.

Register **ALRM-PREREQ-01 — PROPOSED / AWAITING OWNER ACCEPTANCE** for the complete
envelope below. All four HMRs remain BLOCKED for execution pending acceptance;
none is implemented by this preflight. No cross-module SCC is introduced: shared
lifecycle machinery is owned entirely by Alarm. Explicit acceptance admits this
four-commit lifecycle envelope as the §3.2.10 exception. Owner Next in response to
this published proposal accepts ALRM-PREREQ-01 and its full design/scope/validation;
record that acceptance before implementation and do not ask for it again.

This supporting documentation-only commit is registered as
`docs(alarm): record Batch 20 execution preflight`. Its entire write scope is
`doc/roadmap/ULTIMATE_ROADMAP.md` and
`doc/model-remediation/RECONCILIATION.md`; no production/test/migration/version
changes are authorized in the preflight.

### Concrete proposed four-commit design

1. Execute only HMR-100, HMR-104, HMR-105, HMR-106, in that order with the exact
   messages below. Shared-file overlap is explicitly admitted only for the incremental
   obligations named under each HMR. HMR-100's acknowledgement/closure obligations
   are satisfied by dependent HMR-104/105 in the final tree; do not mark HMR-100
   complete on its intermediate creation-only tree. Track each HMR independently and
   count completion only after green final-head production CI. Chain all commits and
   update main once under the expected-head lease; direct publication, no PR.
2. Preserve Alarm/acknowledgement/closure/shelving 37/9/10/11-field domain and API/JPA
   shapes, source/state/status vocabularies, optional snapshots and correlation fields.
   Require nonblank titleFr before persistence. Enforce exact catalog membership
   ALARM_TYPE, ALARM_SEVERITY, optional ALARM_PRIORITY and SHELVING_REASON by
   Alarm-owned locked lookup and database reinforcement. No guessed IDs, catalog
   seeds, new closure-reason family or universal active-catalog requirement is invented.
   Historical inactive entries retain correct-family meaning; family mutation/deletion
   of used entries cannot invalidate records.
3. Introduce the 15-field AlarmLifecycleEvent domain representation and an append-only
   application outgoing port with a JPA adapter/mapper. Application services must not
   import infrastructure entities/repositories. Create one RAISED event atomically with
   fresh Alarm persistence, including direct new-Alarm adapter saves, without a second
   service-level event write. Obtain initial event actor evidence from an Alarm outgoing
   actor port implemented with CurrentActorResolver: authenticated actor/name when
   available, otherwise the existing server-owned SYSTEM_ACTOR_ID for trusted internal
   creation. The port does not accept a browser-selected actor. Existing ack/close/shelve
   REST attribution remains authenticated and unchanged. Initial event identity is stable
   per Alarm; repeated save must not append another RAISED event.
4. Make application lifecycle entry points proxyable and transactional. Lock the owning
   Alarm before reading lifecycle eligibility and keep evidence, Alarm snapshot and event
   writes in one REQUIRED transaction. Direct acknowledgement/closure/shelving adapter
   saves enforce the same operation, not a bypass and not a duplicate orchestration.
   For existing evidence identity, exact replay is a no-op with no second event; changed
   immutable acknowledgement/closure evidence is rejected. Evidence adapters own the
   write operation; services load/guard and delegate, without separately appending the
   same action. Lifecycle event IDs derived from operation/evidence identity and the
   existing event primary key protect retries. Event persistence flushes before success;
   any failure rolls back the entire operation.
5. HMR-104 loads/fails closed on missing or terminal Alarm, updates acknowledgedAt,
   acknowledgedByActorId, lastUpdatedAt and updatedAt, and writes one ACKNOWLEDGED
   action event. RAISED/ACTIVE/ACKNOWLEDGED move to ACKNOWLEDGED; existing CLEARED,
   ESCALATED, SHELVED or SUPPRESSED state remains meaningful while acknowledgement
   snapshots advance. Do not reopen a cleared Alarm or drop a visibility overlay.
   The event's previous/new states record the actual states even for a same-state action.
   CLOSED/CANCELLED or populated closedAt deny fresh acknowledgement. Multiple fresh
   acknowledgement evidence IDs remain legal; no UNIQUE(alarm_id) on that table.
6. HMR-105 loads/locks Alarm, rejects an existing closure and terminal Alarm, and requires
   actual clear evidence for normal closure: CLEARED state or populated clearedAt.
   ESCALATED alone is not a clear-before-close exception. CANCELLED closure is the sole
   DDD exception and sets AlarmState.CANCELLED; other closures set CLOSED. Set closedAt,
   lastUpdatedAt and updatedAt and append one CLOSED or CANCELLED event as appropriate.
   Persist one closure with UNIQUE(alarm_id); concurrent attempts cannot both succeed.
   Do not invent a mandatory requiresReview-to-workflow coupling or closure reason family.
   Clearing/escalation endpoints are not introduced. Closing a shelved/suppressed record
   with clear evidence is legal; later visibility release/expiry must not reopen it.
7. HMR-106's explicit eligibility policy admits RAISED, ACTIVE, ACKNOWLEDGED or ESCALATED
   only while nonterminal and without clearedAt or closedAt. CLEARED, CLOSED, CANCELLED,
   existing SHELVED and SUPPRESSED deny fresh shelving. This is the proposed narrow
   active/open policy, not a claim that the old review already specified this exact matrix.
   Validate shelving reason and shelvedUntil > shelvedAt intrinsically and in PostgreSQL.
   Serialize creation under the Alarm lock and a partial unique index on ACTIVE alarm_id.
   Set currentState=SHELVED, lastUpdatedAt/updatedAt, and append one SHELVED event.
8. Explicit unshelve checks the locked Alarm/evidence relationship and ACTIVE status.
   Finish the shelving with COMPLETED, actual unshelvedAt and supplied trusted actor,
   updating Alarm and appending exactly one UNSHELVED action event. Restore from the
   authoritative SHELVED event's previousState, with later cleared/acknowledged/closed
   evidence taking precedence; never guess a historical previousState or synthesize an
   event. Preserve terminal or subsequently changed state. Fresh rows always have a
   source event; missing legacy source evidence fails closed if restoration needs it.
   Event metadata may carry evidence identity without changing the field shape.
9. Add a scheduled server-owned shelving expiry trigger and a proxyable transactional
   per-record orchestrator. Select candidates with status ACTIVE and shelvedUntil <= asOf,
   then lock Alarm followed by shelving and recheck due/status. Mark EXPIRED, record
   unshelvedAt=shelvedUntil and the server-owned scheduler actor, update lastUpdatedAt
   consistently without moving it backwards, and append one UNSHELVED action event at
   the contractual due instant. Delayed evaluation retains the due instant as evidence.
   Retries, two workers and manual-unshelve races yield one finish/event. Closed/cancelled
   Alarm state remains terminal. This is bounded scheduler evaluation, not a guarantee
   that a stopped process executes a job at an exact wall-clock nanosecond.
10. All admitted Alarm lifecycle writers use a common Alarm-first lock order, followed by
    own evidence row and catalog locks as needed. Narrow suppression changes only
    coordinate its existing create/release/expiry writes with this order and reject fresh
    ALARM-scoped suppression while ACTIVE shelving exists, preventing conflicting
    visibility overlays. Shelving likewise denies an ACTIVE ALARM-scoped suppression.
    Broad-scope suppression, Workflow approval, Audit evidence and established expiry
    policy remain intact. No suppression feature redesign is admitted. Recheck under
    lock after candidate discovery; never hold a shelving/suppression lock and then wait
    for Alarm. Regression tests must prove existing suppression behavior and terminal
    preservation, plus overlap/race denial.
11. Add only forward 022/023/024 below. Validate existing required titles/families,
    duplicate RAISED events, duplicate closures, shelving interval/family/ACTIVE
    duplicates and dangling same-module references before constraints. Fail closed on
    violations with actionable diagnostics; do not rewrite historical data, fabricate
    missing lifecycle events, clear timestamps or actors, or seed guessed classifications.
    Preserve valid legacy snapshots and event gaps as history; fresh admitted writes
    establish lifecycle evidence. Append-only event guards reject update/delete/truncate.
    Do not promise arbitrary raw SQL has application actor/eligibility evidence.
12. Cross-module references remain neutral scalars/snapshots. These reviewed lifecycle
    operations depend on the own Alarm/catalog and security actor boundary; they do not
    decide from live Monitoring/Telemetry/Planning/Topology/Organization/Workflow/Incident
    facts, so no new owner lookup is invented merely to refresh a snapshot. In particular,
    optional reviewWorkflowInstanceId does not become an approval prerequisite. Do not
    invoke NoopAlarmExternalReferenceResolver as validation evidence. Any implementation
    needing an actual live owner fact must stop and separately register that contract.
    No private-module imports, cross-module FKs, OT actuation, POM/version change,
    API route expansion or HMR-080 work is admitted.

### Independent exact scopes and semantic messages

These lists supersede the legacy write allowlists only after ALRM-PREREQ-01 acceptance.
Each list is exhaustive; listed files may be created/updated as necessary, no others.
Canonical memory updates in each semantic commit record that HMR independently.
The legacy DDD/register are supporting evidence and are not rewritten by this batch.

#### HMR-100 — fix(alarm): remediate semantic review Alarm

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmLifecycleEventRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmLifecycleActorPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmLifecycleEvent.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/service/AlarmLifecycleGuard.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/security/AlarmLifecycleActorAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmLifecycleEventRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/validation/AlarmCatalogValidation.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/entity/AlarmLifecycleEventJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/mapper/AlarmPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmLifecycleEventJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmSuppressionJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/service/AlarmSuppressionApplicationAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestrator.java`
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/security/AlarmLifecycleActorAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/service/AlarmSuppressionApplicationAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmSuppressionExpiryOrchestratorTest.java`
- `src/main/resources/db/migration/V20261008_022__hmr_100_alarm_lifecycle_integrity.sql`

#### HMR-104 — fix(alarm): remediate semantic review AlarmAcknowledgement

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmAcknowledgementRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmAcknowledgementRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmAcknowledgementJpaRepository.java`
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmAcknowledgementSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmAcknowledgementSemanticPostgresIntegrationTest.java`

#### HMR-105 — fix(alarm): remediate semantic review AlarmClosure

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/Alarm.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/service/AlarmLifecycleGuard.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmClosureRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmClosureRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmClosureJpaRepository.java`
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmClosureSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmClosureSemanticPostgresIntegrationTest.java`
- `src/main/resources/db/migration/V20261008_023__hmr_105_alarm_closure_integrity.sql`

#### HMR-106 — fix(alarm): remediate semantic review AlarmShelving

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/in/ManageAlarmShelvingUseCase.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/port/out/AlarmShelvingRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/alarm/application/service/AlarmShelvingApplicationService.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/model/AlarmShelving.java`
- `src/main/java/dz/sh/hidra/modules/alarm/domain/policy/AlarmShelvingPolicy.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/adapter/JpaAlarmShelvingRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/validation/AlarmCatalogValidation.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/repository/AlarmShelvingJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmShelvingExpiryJob.java`
- `src/main/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmShelvingExpiryOrchestrator.java`
- `src/test/java/dz/sh/hidra/modules/alarm/semantic/AlarmShelvingSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/persistence/AlarmShelvingSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmShelvingExpiryJobTest.java`
- `src/test/java/dz/sh/hidra/modules/alarm/infrastructure/scheduling/AlarmShelvingExpiryOrchestratorTest.java`
- `src/main/resources/db/migration/V20261008_024__hmr_106_alarm_shelving_integrity.sql`

HMR-100 owns creation/title/catalog/event machinery and the narrow suppression lock
coordination. HMR-104 owns acknowledgement service/adapter/snapshot behavior.
HMR-105 owns closure service/adapter/snapshot/eligibility and uniqueness.
HMR-106 owns shelving/unshelving/expiry and extends the existing own catalog validator
only with SHELVING_REASON. The shared mapper/event/lock interfaces are prepared by
HMR-100 for these exact downstream operations; their presence is not early completion
of a downstream HMR.

### Forward migration authorization proposed

| HMR | Forward migration | Required behavior |
|---|---|---|
| HMR-100 | `V20261008_022__hmr_100_alarm_lifecycle_integrity.sql` | Required title, exact type/severity/optional priority families with metadata race guards, append-only lifecycle history and fresh initial-event uniqueness; preserve published local FKs and valid history. |
| HMR-104 | None | Existing own Alarm FK suffices; acknowledgement multiplicity remains legal. Runtime transaction/lock/event coherence is mandatory. |
| HMR-105 | `V20261008_023__hmr_105_alarm_closure_integrity.sql` | Fail on existing duplicate closures; install one-closure-per-Alarm uniqueness without deleting/merging evidence. |
| HMR-106 | `V20261008_024__hmr_106_alarm_shelving_integrity.sql` | Fail on invalid intervals/families/ACTIVE duplicates; intrinsic interval and partial ACTIVE uniqueness plus used shelving-family guards. |

All filenames are under `src/main/resources/db/migration/`. Existing published SQL
remains byte-for-byte unchanged, including the HMR-094/103 policy/integrity migrations.
An empty database does not justify guessed production catalog provisioning. Deployment
against invalid historical records intentionally stops for owner remediation outside
this implementation; no migration silently manufactures missing evidence.

### Admitted validation proposal

Run compile and each HMR's focused test independently after preparing that HMR:

- `./mvnw -q -DskipTests compile`
- `./mvnw -q -Dtest=AlarmSemanticRemediationTest test`
- `./mvnw -q -Dtest=AlarmAcknowledgementSemanticRemediationTest test`
- `./mvnw -q -Dtest=AlarmClosureSemanticRemediationTest test`
- `./mvnw -q -Dtest=AlarmShelvingSemanticRemediationTest test`

On the final exact tree run:

- `./mvnw -q -Dtest=AlarmLifecycleActorAdapterTest,AlarmSuppressionApplicationAdapterTest,AlarmSuppressionExpiryOrchestratorTest,AlarmSuppressionPolicyTest,AlarmSuppressionApprovalServiceTest,AlarmSuppressionPersistenceMigrationTest,SpringAlarmControllerActorAttributionTest,AlarmRestMapperTest,AlarmSuppressionControllerTest,AlarmSuppressionRoutePermissionTest,AlarmShelvingExpiryJobTest,AlarmShelvingExpiryOrchestratorTest test`
- `./mvnw -q -Dtest=AlarmSemanticPostgresIntegrationTest,AlarmAcknowledgementSemanticPostgresIntegrationTest,AlarmClosureSemanticPostgresIntegrationTest,AlarmShelvingSemanticPostgresIntegrationTest test`
- `./mvnw -q -Dtest=ArchitectureGuardrailTest,ForensicRemediationClosureTest test`
- `./mvnw -q test`
- `./mvnw -q clean verify`
- `git diff --check` for each prepared semantic diff.

Focused tests must cover correct/incorrect family, missing/blank title, optional priority,
unchanged snapshots, one initial event, actor attribution, unknown/terminal Alarm denial,
repeated legal acknowledgements, visibility/clear/escalation preservation, clear versus
cancel closure, duplicate closure, strict shelving interval, exact eligibility matrix,
active overlap, manual finish and deterministic due boundary.

PostgreSQL/Spring-JPA tests must invoke the actual transactional service proxies and
direct adapters, flush the real Alarm/evidence/event writes, inject event failure and
prove complete rollback. Migration cases test valid history retention, invalid title/family,
duplicate initial events/closures/ACTIVE shelving and malformed intervals causing abort,
retry after independently supplied valid owner data, published migration immutability,
append-only update/delete/truncate denial and nullable own-reference integrity. Do not
execute guessed automatic fixes as retry setup.

Race cases require two real transactions/connections: ack versus close; close versus
close; shelve versus shelve; expiry versus explicit unshelve; two expiry workers;
shelving versus ALARM-scoped suppression; suppression release/expiry versus lifecycle
writes; and catalog family mutation/delete versus target writes. Verify evidence/event
cardinality, terminal preservation, lock-order completion and absence of partial state.
An in-memory fake or a test that only searches SQL strings is not runtime race evidence.

No new exported owner package is proposed. Both architecture suites must still prove
module ownership and application-to-infrastructure isolation. Current security attribution
and prior suppression approval/Audit tests are regression gates, not live owner facts
fabricated by a permissive stub.

This preflight is documentation-only: no production Maven/JUnit/Spring/PostgreSQL
execution is needed or claimed for it. Exact-head CI #600 is prior production evidence,
not execution of the proposed Alarm tests. Reassess local Java/Maven/dependency/DNS/
Docker/PostgreSQL availability at implementation time and report actual limitations.

### Publication and next owner action

Validate the two-document diff and all canonical Markdown using the documentation
workflow's exact checks; publish only this supporting commit to main under the
`0cc3e5c6c4b675880a634b3a073905f4119a36b7` lease, compare its immutable tree/blobs,
confirm Documentation Validation starts and STOP without waiting for completion.
Production CI ignores documentation-only pushes; do not falsely claim a new production
run for this preflight.

Next after owner acceptance: execute the four independent semantic commits above,
observe production CI start and STOP. A Fail diagnoses and repairs its responsible
scope before any further task. HPR-P2-008 remains open, HMR-080 remains separately
owner-contract blocked, project version remains 0.6.0-SNAPSHOT, and no physical
survivability evidence or phase closure is claimed. No release/version action or later
batch is included.

Pre-publication validation: all 82 canonical Markdown files passed nonempty and
merge-conflict-marker checks against the prepared tree; the actual two-file Git diff
passed `git diff --check` (exit 0). Exact independent scope/message/forward-name
checks passed. No production source changed and no proposed Alarm runtime test ran.

## HPR-P2-008 Batch 20 acceptance — 2026-10-08

Owner Next after published preflight 05ec5a5866930e62bb5e70c82606b445ae168ef1
ACCEPTS ALRM-PREREQ-01 in full: twelve-part design, four independent exact scopes,
forward 022/023/024 and runtime validation. The combined lifecycle exception is
explicitly admitted. Do not ask for acceptance again. Main is unchanged at that
preflight; production baseline 0cc3e5c6c4b675880a634b3a073905f4119a36b7 has green
CI #600; documentation CI #107 passed. HMR-100 then 104 then 105 then 106 are
IN PROGRESS. HPR-P2-008 remains open and HMR-080 separately blocked.

## HPR-P2-008 Batch 20 independent execution — HMR-100 — 2026-10-08

ALRM-PREREQ-01 is ACCEPTED. HMSR-117 section 130 was recovered against the live
37-field model, service/mapper/entity, own catalog and event persistence, published
local FKs and suppression writers before mutation. Required French title and exact
ALARM_TYPE/ALARM_SEVERITY/optional ALARM_PRIORITY are enforced. Inactive valid
family history is preserved; no guessed catalog data is supplied. New direct/live
Alarm persistence appends exactly one RAISED event with security/server-owned actor
evidence, stable initial identity and flushed atomic rollback. Application uses own
event/actor ports; no infrastructure/private-module imports or cross-module FKs.

Forward 022 aborts on invalid titles/families, orphan own evidence or duplicate
initial events, installs priority integrity and catalog mutation locks/guards, and
rejects event update/delete/truncate. Published SQL is unchanged. Valid historical
event gaps remain history; no event or snapshot is fabricated. Suppression
create/release/expiry now lock Alarm before own evidence; scalar candidate queries
avoid stale managed objects after a wait. ALARM-scoped suppression rejects ACTIVE
shelving while broad suppression, Workflow approval and Audit semantics remain.

HMR-100 is IMPLEMENTED — DEPENDENT HMR-104/105 AND FINAL CI PENDING. Its two
remaining lifecycle obligations are completed in their separate ordered scopes, not
claimed complete by this intermediate creation commit. Focused/actor and real
PostgreSQL/Spring-JPA sources cover creation/live/direct paths, wrong/missing family,
inactive history, replay, flushed event-failure rollback, migration abort/retry,
append-only evidence and catalog mutation race. Suppression regression sources are
adapted to the locked scalar discovery and overlap denial.

Fresh environment: ./mvnw cannot execute because its published mode is 100644;
invocation via bash mvnw -q -DskipTests compile exited 1 before compilation: uncached
Spring Boot parent 4.1.1 plus repo.maven.apache.org temporary DNS failure. Java 17
is installed; Java 21 and Docker are absent. An offline focused Maven attempt also
failed before tests on the missing parent. No repository JUnit/Spring/PostgreSQL/
ArchUnit result is claimed. Isolated actual Alarm core sources compiled on Java 17
against temporary dependency stubs (exit 0); this is a limited source/type check,
not the admitted Java 21 Maven build. Scope/header/whitespace and all 82 canonical
Markdown checks passed. Runtime validation remains pending final production CI.

Exact message: fix(alarm): remediate semantic review Alarm. Version remains
0.6.0-SNAPSHOT, HPR-P2-008 open, HMR-080 blocked and physical survivability unchanged.

## HPR-P2-008 Batch 20 independent execution — HMR-104 — 2026-10-08

ALRM-PREREQ-01 remains ACCEPTED. HMSR-121 section 134 was independently recovered
against the live nine-field acknowledgement, service and local FK. Live service and
direct evidence adapter lock the owning Alarm and fail closed on unknown or terminal
state. The adapter atomically flushes acknowledgement, current Alarm snapshots and
one stable ACKNOWLEDGED action event. CLEARED/ESCALATED/SHELVED/SUPPRESSED states
remain meaningful; older evidence cannot overwrite the latest actor/time snapshot.
Exact evidence replay is a no-op; changed existing evidence is rejected. Multiple
acknowledgement IDs remain legal, with no one-row uniqueness or new migration.

HMR-104 is IMPLEMENTED — FINAL CI PENDING. Three focused and three PostgreSQL/
Spring-JPA tests are prepared for direct/live paths, replay, unknown/terminal denial,
visibility preservation, flushed event-failure rollback and a real lock wait against
concurrent closure. These runtime suites have not passed locally. The fresh focused
Maven command is attempted; the unchanged missing Boot 4.1.1 parent/DNS limitation
blocks repository execution before tests. Isolated actual core compilation against
temporary dependency stubs passed on Java 17; exact scope/header/whitespace and
canonical Markdown checks passed. This is not Java 21/Maven/JUnit/ArchUnit/database
execution evidence. HMR-100's acknowledgement obligation is now implemented through
this separate commit; its closure obligation remains for HMR-105.

Exact message: fix(alarm): remediate semantic review AlarmAcknowledgement. Preserve
0.6.0-SNAPSHOT, HPR-P2-008 open, HMR-080 blocked and physical survivability evidence.

## HPR-P2-008 Batch 20 independent execution — HMR-105 — 2026-10-08

ALRM-PREREQ-01 remains ACCEPTED. HMSR-122 section 135 was recovered independently
against ten-field closure, local FK, live service, own guard and absent uniqueness.
Live/direct closure now locks Alarm, rejects unknown/terminal or already closed
evidence, requires actual CLEARED state or clearedAt for normal closure, and treats
CANCELLED as the sole clear-before-close exception. ESCALATED alone no longer
bypasses clearing. Closure, terminal snapshot/closedAt and one CLOSED/CANCELLED
event flush in one transaction. Exact replay is a no-op; mutation is rejected.
Optional review workflow remains optional even when requiresReview is true; no
closure reason family, cross-owner FK or upstream lookup policy is invented.

Forward 023 aborts on duplicate or orphan historical closures and installs UNIQUE
(alarm_id), preserving all evidence. Three focused and five actual PostgreSQL/
Spring-JPA tests are prepared for live cancellation/clear denial, flushed rollback,
duplicate migration abort, close-versus-close and ack-versus-close races. They are
not locally executed passes. The focused Maven command is attempted on this tree;
repository execution remains blocked by uncached Boot parent/DNS. Actual core
source compilation against temporary dependency stubs passed on Java 17, with
scope/header/whitespace/all canonical Markdown checks; this is not real Maven,
JUnit, Spring, PostgreSQL or ArchUnit validation.

HMR-105 is IMPLEMENTED — FINAL CI PENDING. HMR-100's dependent acknowledgement and
closure obligations are now implemented in the separate HMR-104/105 commits, but
none of these pending implementations increases the 52 CI-confirmed total. Exact
message: fix(alarm): remediate semantic review AlarmClosure. HPR-P2-008 stays open,
0.6.0-SNAPSHOT unchanged; HMR-080 and physical survivability disposition unchanged.

## HPR-P2-008 Batch 20 independent execution — HMR-106 — 2026-10-08

ALRM-PREREQ-01 remains ACCEPTED. HMSR-123 section 136 was recovered independently
against the live eleven-field shelving, service/port/mapper/entity, published own
FKs and absent expiry path before mutation. Intrinsic end-after-start, exact locked
SHELVING_REASON and the accepted open/uncleared state matrix are enforced. Direct
and live shelving/finish paths lock Alarm before evidence, synchronize snapshots
and append one stable SHELVED/UNSHELVED action event atomically. Active shelving
and ALARM-scoped suppression are mutually denied on fresh writes under the same
parent lock. Frozen creation evidence and exactly-once finishing are protected.

Restoration uses the recorded source state, with later clear/terminal evidence
preserved and acknowledgement precedence restricted to un-escalated underlying
states; escalation remains meaningful as in HMR-104. Missing historical source
evidence fails closed when restoration needs it. Optional unshelved actor remains
optional; required event actor comes from the trusted security/server boundary.
No fields, API routes, upstream owner facts or guessed historical events are added.

Expiry discovers scalar IDs and crosses the transactional adapter proxy separately
for each row. It rechecks due/status after Alarm-then-evidence locks, records EXPIRED
and unshelvedAt/event occurrence at shelvedUntil, preserves terminal/current state
and never moves lastUpdatedAt backwards. Retries, parallel workers and manual finish
races cannot append a second finish event. An individual failure rolls back and is
logged; other due rows may progress, while invalid legacy evidence remains denied.
The existing Spring scheduler invokes this path with a server-owned actor. No claim
of execution at an exact wall-clock nanosecond during process downtime is made.

Forward 024 aborts on invalid intervals/family/own references or ACTIVE duplicates,
adds strict interval and partial ACTIVE uniqueness, and guards used shelving reason
identity/family/deletion/truncation. All published migrations remain byte-for-byte
unchanged. Four focused, thirteen PostgreSQL/Spring-JPA and three scheduler tests
are prepared. Actual runtime sources cover live/direct paths, event-failure flushed
rollback, migration abort, missing legacy source denial, reason deletion/family races,
shelve-versus-shelve, expiry workers, manual finish-versus-expiry, shelving-versus-
suppression and suppression release/expiry-versus-cancellation. These are prepared
real test sources, not local runtime passes.

### Final-tree validation and truthful limits

All ten admitted Maven targets were attempted through bash mvnw (the published
wrapper mode is 100644): compile; all four focused classes; the exact security/
suppression/API/scheduler regression group; the four PostgreSQL integration classes;
both architecture suites; full test; clean verify. Every command exited 1 before
compilation/tests because Spring Boot parent 4.1.1 is uncached and Maven Central DNS
resolution fails. Java 17 is installed, Java 21 and Docker unavailable. No real
Maven/JUnit/Spring/PostgreSQL/ArchUnit pass or test-method execution count is claimed.

Actual changed production sources, including suppression coordination, and all eleven
new test classes compiled in an isolated Java 17 check against temporary external
dependency stubs (exit 0). An executable smoke over the actual adapters/domain with
temporary in-memory repositories passed creation/replay, acknowledgement/cancellation,
shelving overlap, expiry boundary/retry and restoration. This check has no transaction/
database/JUnit semantics and does not replace CI. Exact authorized scopes, headers,
37/9/10/11 and 15 event field counts, owner-neutral imports, forward migration names,
all 82 canonical Markdown checks and each actual Git diff whitespace check passed.
No POM/workflow/route/private-module/previous migration modification is included.

| Subject | Status after preparation | Independent semantic commit / tree |
|---|---|---|
| HMR-100 / HMSR-117 | IMPLEMENTED — FINAL CI PENDING | 2c1693390e943356346c23d611222cee18609979 / 23f90c246df353ca5c31a1fc6a39736ca3071bc6 |
| HMR-104 / HMSR-121 | IMPLEMENTED — FINAL CI PENDING | 4373b97ad1e6cd5908bd05591e9b494499818af6 / 13f67c0cd1133d3ddb388ebcbdff9d1db8c4d2a4 |
| HMR-105 / HMSR-122 | IMPLEMENTED — FINAL CI PENDING | 0445518741bc62ba35ed117a81b1c143ea1b0171 / 69dac5de4e0356ffe586c37852baefe928adbac8 |
| HMR-106 / HMSR-123 | IMPLEMENTED — FINAL CI PENDING | Ordered final commit uses fix(alarm): remediate semantic review AlarmShelving; exact published tree is independently checked before main advancement. |
| HMR-080 | BLOCKED | Unresolved Party-to-Planning owner contract remains separate. |

Current reconciliation: 52 CI-confirmed plus four implemented pending final-head
production CI, one blocked HMR-080, 57 evaluated. Pending work is not counted as
CI-confirmed. HPR-P2-008 remains OPEN. Project version stays 0.6.0-SNAPSHOT; no
new physical survivability evidence, phase closure, release or later task is claimed.

Publish the four independent chained commits by advancing main once under expected
head 05ec5a5866930e62bb5e70c82606b445ae168ef1, compare every immutable tree/blob
and confirm production CI starts, then STOP. Do not wait for completion. Owner Next
checks that CI; Fail diagnoses and repairs only its responsible scope before advancing.

## HPR-P2-008 Batch 20 CI #601 repair admission — 2026-10-08

Owner Next checks the published Batch 20 gate; it does not select a later batch.
Main remains `3841da6332d8abda073c50871e4c28348cf3f4df`. Production CI #601
(run 37810221486) FAILED: 1,232 tests, one failure, zero errors/skips. Documentation
CI #108 passed. The sole failure is DomainPersistenceMirrorGuardrailTest: historical
HRA-061 retirement still prohibits AlarmLifecycleEvent's record/port/adapter/mapper.
Accepted ALRM-PREREQ-01 explicitly restored that split; the current
AlarmShelvingPolicy.restorationState consumes the domain event as authoritative
previous-state evidence. This now satisfies the classification's REAL_DOMAIN rule.
Do not delete the accepted lifecycle machinery or broadly disable the guard.

Admit a supporting responsible-scope repair under AGENTS.md section 3.2.8.
Preserve the historical 51/343 classification and its 394-member cohort verbatim;
apply exactly one live reclassification, alarm.AlarmLifecycleEvent, to effective
52 REAL_DOMAIN / 342 retired pairs. Require its domain/JPA split, port, adapter,
repository, mapper and live shelving-domain consumer. All other retirement guards
remain enforced. This is inventory reconciliation, not a lifecycle redesign.

Exact supporting commit: `test(alarm): reconcile lifecycle event mirror disposition`.
Exhaustive write scope:

- `src/test/java/dz/sh/hidra/DomainPersistenceMirrorGuardrailTest.java`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`

Validation: focused mirror/architecture/forensic tests, full test and clean verify;
historical/effective inventory and negative mutation probes; git diff --check and
canonical Markdown checks. Reassess Java/Maven/DNS/Docker availability. No production
code, published migration, legacy classification, API, POM or workflow change is
admitted. Keep all four independent semantic commits. Batch 20 remains implemented
but NOT CI-confirmed; 52 confirmed + four awaiting repaired-head CI + HMR-080 blocked.
HPR-P2-008 remains open, version 0.6.0-SNAPSHOT unchanged, no physical-survivability
or release claim. Publish once under the current-head lease, verify the exact tree,
confirm replacement production CI starts, then STOP without waiting for completion.

### Repair validation and publication disposition

Fresh local commands all exited 1 before compilation/test execution:
`bash mvnw -B -q -Dtest=DomainPersistenceMirrorGuardrailTest test`;
`bash mvnw -B -q -Dtest=DomainPersistenceMirrorGuardrailTest,ArchitectureGuardrailTest,ForensicRemediationClosureTest test`;
`bash mvnw -B -q test`; `bash mvnw -B -q clean verify`.
Spring Boot parent 4.1.1 is uncached; repo.maven.apache.org has temporary DNS failure.
Only Java 17 is available; Java 21 and Docker remain absent. No local Maven/JUnit/
Spring/PostgreSQL/ArchUnit pass or repaired production success is claimed.

The actual guard body compiled and executed via the installed Java 17 compiler
module with temporary assertion/annotation stubs outside the repository. The sole
compatibility substitution in that temporary copy was getFirst() -> get(0);
production test source retains Java 21 getFirst(). Positive historical/effective
inventory, all pair paths, mapper and live domain-consumer checks passed. Three
independent copied-fixture negative probes rejected unrelated retired AlarmComment
revival, missing AlarmShelvingPolicy and missing approved lifecycle event port.
This is limited source/guard logic evidence, not repository JUnit execution.

Exact three-file scope and git diff --check passed; all 82 canonical Markdown files
passed nonempty/conflict-marker checks. Historical classification and the entire
production/migration/API/POM/workflow tree remain unchanged. Publish the registered
supporting repair with parent/lease 3841da6332d8abda073c50871e4c28348cf3f4df.
Keep 52 confirmed + four implementations pending repaired-head CI; do not advance
to another batch. Observe replacement production CI start, then STOP.

## HPR-P2-008 Batch 20 confirmation and Nomination ownership preflight — 2026-10-08

Owner Next rechecks the published repair. Main is unchanged at
`74ef372c82a790cdad63a77038fd60afb0de9c44`, tree
`547c934bd5f655438942fec176a2e619784bd8bb`.
Production CI #602 (37831581087) PASSED on this exact head. Its Java 21 repository
verification (`./mvnw -B -q clean verify`), deterministic current/base OpenAPI
generation, backward compatibility and artifact upload all passed.
Documentation CI #109 (37831581203) also passed. This is new exact-head CI evidence,
not a local Maven pass or new physical-survivability evidence.

| HMR / review | Current disposition | Independent semantic commit |
|---|---|---|
| HMR-100 / HMSR-117 | COMPLETED — CI #602 GREEN | 2c1693390e943356346c23d611222cee18609979 |
| HMR-104 / HMSR-121 | COMPLETED — CI #602 GREEN | 4373b97ad1e6cd5908bd05591e9b494499818af6 |
| HMR-105 / HMSR-122 | COMPLETED — CI #602 GREEN | 0445518741bc62ba35ed117a81b1c143ea1b0171 |
| HMR-106 / HMSR-123 | COMPLETED — CI #602 GREEN | 3841da6332d8abda073c50871e4c28348cf3f4df |

The separate inventory repair is 74ef372c82a790cdad63a77038fd60afb0de9c44.
Current reconciliation is **56 CI-confirmed, zero pending CI, zero STILL REQUIRED,
one BLOCKED HMR-080, 57 evaluated**. Historical preparation/repair notes remain
provenance; this section supersedes their pending-CI counts.

### Solo HMR-080 live prerequisite recovery

Recover HMSR-094 section 107, the seven HMR-080 obligations and Planning DDD
section 6.5 against this exact source before selecting production execution.
The 26-field Nomination still accepts nonpositive quantity, an equal start/end
and missing audit timestamps. Its direct JPA adapter only maps/saves. Existing
same-module FKs prove revision/catalog existence, not semantic ownership; no
revision-scoped code uniqueness or dedicated HMR-080 tests/migration are present.
NominationJpaRepository and PlanScenarioJpaRepository now expose shared parent
lookups from Batch 19. This does not implement Nomination's remaining obligations.

| Reference | Current evidence | Decision or contract still required |
|---|---|---|
| shipperPartyId / counterpartyId | Party exports only Topology/Assets contracts; no party.application.contract.planning | Register a Party-owned Planning scalar lookup, with canonical shipper code evidence; no Party private imports or cross-module FK. |
| productTypeId | Required reference; existing FK points at PlanningCatalogEntry; DDD defines no PRODUCT_TYPE family | Identify the authoritative product owner and identifier store, then authorize its public contract and persistence alignment. |
| quantityUnitId | Required reference; existing FK points at PlanningCatalogEntry; no QUANTITY_UNIT family | Identify the authoritative quantity-unit owner/store and eligibility semantics. |
| rateUnitId | Optional reference; DDD defines no RATE_UNIT family or definitive owner | Identify its authoritative owner/store; preserve optionality and do not infer it from quantityUnitId. |
| scenarioId | Optional same-module PlanScenario; owning revision available in current source | Reconcile the local edge and same-revision compatibility before changing application/schema constraints. |
| source/destination assets | Existing Topology-owned typed Planning lookup available | Review reuse and snapshot preservation in the final implementation scope. |
| contractReferenceId | No canonical contract-master owner established | Retain the neutral optional scalar; do not fabricate an owner or approval rule. |

The concrete recommended Party boundary is
`party.application.contract.planning.PlanningPartyReferenceContract`, returning
only optional scalar Party ID/code evidence from a Party-owned provider. No Party
ACTIVE/shipper-role restriction is inferred from an existence obligation.
Existing historical snapshots remain historical; fresh references use owner evidence.
This is a proposed boundary, not an admitted production file scope.

**NOM-OWNER-01 — BLOCKED / OWNER DECISION REQUIRED.** Supply the authoritative
module/catalog or external owner and ID store for productTypeId, quantityUnitId
and rateUnitId, with applicable eligibility semantics. HMSR-094 section 107.7 says
the correction "must not invent a new Planning taxonomy merely to satisfy the
existing FK shape." Thus guessed Planning families, guessed Telemetry ownership,
permissive resolvers and unseeded metadata pretending to establish a business
owner are not substitutes for this decision. A bare Next does not supply it.

After that decision, register a complete solo HMR-080 prerequisite amendment:
exact owner contracts/providers, architecture exports, independent exhaustive file
scope, fresh snapshot/historical policy, same-revision scenario integrity, forward
migration filename after published 024, and focused/owner/PostgreSQL rollback/race/
architecture/full-test/clean-verify checks. Preserve its exact semantic message
`fix(planning): remediate semantic review Nomination`. Do not reuse the legacy
20261004 migration name behind the current tail or modify any published migration.
No HMR-080 production work or complete implementation scope is authorized here.

### Documentation publication

Register this supporting preflight as
`docs(planning): record Nomination ownership preflight`.
Entire write scope: `doc/roadmap/ULTIMATE_ROADMAP.md` and
`doc/model-remediation/RECONCILIATION.md`. Update the current counts/independent
statuses, preserve historical evidence, and validate the two-file diff with
`git diff --check` plus the documentation workflow's UTF-8/nonempty/conflict-marker
checks over all canonical Markdown. No Maven/runtime test is required or claimed
for this documentation-only tree. Exact executable validation remains CI #602.

Publish once under expected head 74ef372c82a790cdad63a77038fd60afb0de9c44,
verify immutable tree/blobs, observe Documentation Validation start and STOP
without waiting. Production CI ignores this documentation-only push; no new
production run is claimed. HPR-P2-008 remains OPEN; HPR-P2-009 is not selected.
Keep 0.6.0-SNAPSHOT, physical-survivability disposition and release state unchanged.

Pre-publication validation passed: exact two-document scope, git diff --check,
and all 82 canonical Markdown UTF-8/nonempty/conflict-marker checks. Production,
test, migration, API, POM, workflows and legacy evidence are byte-for-byte unchanged.
No local runtime test or HMR-080 implementation is claimed by this preflight.

## HPR-P2-008 solo HMR-080 ownership acceptance and execution preflight — 2026-10-08

Owner explicitly answered **Apply and next** to the ownership recommendation.
This ACCEPTS **NOM-OWNER-01**: Custody owns product identities selected from
hidra_custody_catalog_entry.id; Telemetry owns quantity/rate identities selected from
hidra_telemetry_unit.id; Party owns shipper/counterparty identities. New/changed
values require owner-approved eligibility; valid unchanged historical references
and snapshots remain legal after deactivation. Unit roles/compatibility and historical
ID mappings require explicit approval rather than name/factor guesses. The optional
same-revision Scenario edge is reconciled locally; contractReferenceId stays neutral.
No external enterprise product master was designated. Do not ask for this ownership
acceptance again. This decision supersedes NOM-OWNER-01's previous ownership blocker.

Exact main remains d073ae13f9a79726f41839c4dec7f49fc6d57f8d, tree
0c4e346b0cd3f4677dc43a240c654155e18d57a5. Documentation CI #110
(37834172227) passed. Only two canonical documents changed since repaired production
74ef372c82a790cdad63a77038fd60afb0de9c44, whose full CI #602 passed.
Thus the executable baseline remains green and identical. HMSR-094 section 107,
all seven HMR-080 obligations, Planning DDD 6.5, the current 26-field domain/JPA,
direct adapter, own-parent/catalog lookups and published SQL were recovered again.

### Prerequisite disposition and next registered execution

The accepted ownership decision is not implemented by a provider yet. The legacy
scope lacks the Custody/Telemetry/Party exports/providers, owner approval metadata,
transactional direct-save validation, PostgreSQL tests and architecture/inventory
reconciliation. Its 20261004 migration would precede the published 20261008_024 tail.
AGENTS.md section 3.2.9 requires splitting an unregistered owner-contract or
migration-order prerequisite before production mutation; section 3.1 keeps this solo.

Register **NOM-EXEC-01 — PROPOSED SOLO EXECUTION ENVELOPE** below. This task applies
the ownership decision and prepares/publishes the complete two-document preflight;
it does not start production implementation. Owner Next after this published
preflight selects and accepts the complete solo envelope, including explicit
mapping-only historical migration and exact scopes/validation, then executes only
HMR-080. That Next does not reopen NOM-OWNER-01. If scope becomes materially larger
or actual owner metadata introduces another decision, stop before affected mutation.

Exact semantic commit: `fix(planning): remediate semantic review Nomination`.
One standalone HMR-080 commit, direct main publication with expected-head lease,
no PR, no batch, no unrelated semantic completion or phase closure.

### Complete proposed design

1. Preserve all 26 Nomination fields, existing optionality, status enum and API/JPA
   shape. Require quantity > 0, periodStart < periodEnd, createdAt and updatedAt.
   Keep the historical HRA-051 marker cohort intact; additional semantic guards
   are not added as historical marker comments. Do not invent rate positivity,
   rate/rateUnitId pairing, lifecycle transitions or timestamp ordering obligations.
2. Make JpaNominationRepositoryAdapter.save proxyable and REQUIRED transactional.
   Lock an existing Nomination for update, then resolve/lock its mandatory revision
   and optional scenario using the current shared lookups; supplied scenario must
   have the same revision. No approved-revision editing rule is invented. Preserve
   downstream PlanTarget's existing composite Nomination/revision FK and published
   uq_hmr094_nomination_revision. Code identity is unique per revision, backed by
   an application check excluding the current ID and a database UNIQUE(revision_id,code).
3. Require exact NOMINATION_TYPE membership on every save through Planning's locked
   catalog lookup. New/changed type IDs must be active; unchanged correct-family
   inactive history remains legal. Reinforce exact family and fresh eligibility in
   PostgreSQL, with metadata mutation/write race guards. Do not seed catalog values.
4. Export Custody-owned PlanningProductReferenceContract with optional scalar product
   identity/code and eligibility evidence. Its own infrastructure provider resolves
   the actual Custody catalog entry plus explicit Planning-product approval metadata.
   A generic catalog row alone is not a product approval. Use an empty, owner-approved
   per-ID product policy rather than inventing PRODUCT_TYPE or Planning catalog families.
   The approved policy establishes eligibility for Planning consumption; it does not
   redefine the existing Custody product usages or declare enterprise master ownership.
5. Export Telemetry-owned PlanningUnitReferenceContract in its existing Planning
   contract package. Resolve the quantity and optional rate unit together, returning
   scalar ID/code/symbol/dimension and separate approval/active evidence. Owners
   approve QUANTITY/RATE consumption roles and explicit quantity/rate ID pairs.
   This metadata qualifies usage of actual TelemetryUnit identities; it is not a
   new unit taxonomy. Resolve/lock distinct units in sorted ID order, then roles/
   pair metadata in a stable order. No conversion is performed. Never infer rate
   compatibility from a code, dimension label, factor, or matching symbol alone.
   A supplied rate unit requires an approved pair; a new/changed pair must be active.
   Only new/changed unit IDs require active unit/role eligibility. A valid unchanged
   inactive unit may remain when the other reference changes and the owner has
   explicitly approved the new pair. Missing approvals fail closed.
6. Export Party-owned PlanningPartyReferenceContract returning optional scalar ID/code.
   Its own provider uses a shared Party lookup for a new/changed supplied identity.
   Resolve multiple Party IDs in stable sorted order. Do not require active status,
   commercial role or contract ownership merely to satisfy existence semantics.
   A fresh shipper uses the canonical Party code; unchanged shipper reference retains
   its historical snapshot. Counterparty stays optional and gains no new snapshot field.
7. Reuse the existing Topology-owned typed Planning lookup for source/destination.
   Each optional asset reference must contain both type and ID when either is supplied;
   fresh references obtain canonical owner code, unchanged references retain snapshots.
   Clear the related snapshot when removing its reference. Unsupported/missing fresh
   assets fail closed. The existing contract supplies lookup evidence; no new claim
   of global Topology deletion serialization or durable cross-module FK is made.
8. Centralize reference validation at the actual direct-save adapter boundary, so
   callers cannot bypass it. Application/domain code imports no owner persistence.
   New/changed product/unit references use Custody/Telemetry contracts; unchanged
   historical owner identities do not require reactivation or snapshot refresh.
   Changing quantity alone does not change unit identity. Preserve neutral contract
   scalar and all other historical evidence. Adapter flushes before successful return;
   owner rejection or persistence failure leaves no partial Nomination write.
   Existing source has no dedicated Nomination create API/use case; do not add one.
9. Install only the forward metadata/integrity migrations below. Published migrations
   stay byte-for-byte unchanged. Owner policy tables and mapping evidence start EMPTY.
   Own metadata FKs may reference only their own module's table; no cross-module FK.
   Owners must explicitly provision product approvals, unit roles/pairs and reviewed
   legacy mappings. No sample catalog rows, guessed approvals or inferred mappings.
10. Reconcile the scalar-FK inventory explicitly: fk_hra111_planning_010 (product)
    and fk_hra111_planning_011 (quantity unit) become owner-contract obligations, not
    same-module database relationships. Preserve the historical 551 obligation count;
    current structural HRA-111/replacement inventory is 549 plus these two named,
    tested ownership reclassifications. Do not manufacture replacement cross-module
    FKs or simply lower the historical constant. Assert the exact retired constraints
    and columns, absence of replacement cross-module FKs, all remaining validated
    own constraints, and runtime rejection through the actual owner contracts.
11. Both architecture registries explicitly add only
    party.application.contract.planning and custody.application.contract.planning;
    telemetry.application.contract.planning is already exported. Keep exports in sync,
    retain application-to-infrastructure isolation, forbid private owner imports and
    test named provider wiring. Existing generic reader/security policy is unchanged.
    Do not recreate retired Product/Unit domain mirrors just for repository plumbing.
12. Keep NominationScheduleLine, OperationalPlan, PlanTarget and all other product/unit
    consumers outside this correction. Their ownership harmonization is separate
    future scope, not silently executed here. No new REST route, POM/workflow change,
    release, physical-survivability assertion, HPR-P2-009 or HPR-P2-008 closure.

### Exact independent HMR-080 write scope

This proposed scope supersedes the legacy allowlist only after NOM-EXEC-01 acceptance.
Listed paths may be created/updated as needed; no others. Legacy DDD/register and
classification inventories remain preserved evidence.

- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `src/main/java/dz/sh/hidra/modules/planning/application/port/out/NominationRepositoryPort.java`
- `src/main/java/dz/sh/hidra/modules/planning/domain/model/Nomination.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/JpaNominationRepositoryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/NominationReferenceValidation.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/entity/NominationJpaEntity.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/mapper/PlanningPersistenceMapper.java`
- `src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/repository/NominationJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/planning/PlanningProductReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/custody/application/contract/planning/package-info.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/custody/infrastructure/persistence/repository/CustodyCatalogEntryJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/PlanningUnitReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/application/contract/planning/package-info.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/integration/PlanningUnitReferenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/telemetry/infrastructure/persistence/repository/TelemetryUnitJpaRepository.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/planning/PlanningPartyReferenceContract.java`
- `src/main/java/dz/sh/hidra/modules/party/application/contract/planning/package-info.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/integration/PlanningPartyReferenceQueryAdapter.java`
- `src/main/java/dz/sh/hidra/modules/party/infrastructure/persistence/repository/PartyJpaRepository.java`
- `src/main/resources/db/migration/V20261008_025__hmr_080_nomination_owner_reference_policies.sql`
- `src/main/resources/db/migration/V20261008_026__hmr_080_planning_nomination_integrity.sql`
- `src/test/java/dz/sh/hidra/modules/planning/semantic/NominationSemanticRemediationTest.java`
- `src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/NominationSemanticPostgresIntegrationTest.java`
- `src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/PlanningProductReferenceQueryAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/telemetry/infrastructure/integration/PlanningUnitReferenceQueryAdapterTest.java`
- `src/test/java/dz/sh/hidra/modules/party/infrastructure/integration/PlanningPartyReferenceQueryAdapterTest.java`
- `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`
- `src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`
- `src/test/java/dz/sh/hidra/InternalReferenceIntegrityMigrationTest.java`

Existing PlanRevision/PlanScenario/PlanningCatalog repositories and Topology lookup
are read/reused without changes. DomainInvariantGuardrailTest remains unchanged;
new semantic guards must preserve its historical marker-count expectations.

### Forward migration design and historical transition

| Migration | Proposed responsibility |
|---|---|
| V20261008_025__hmr_080_nomination_owner_reference_policies.sql | Install empty Custody product approval, Telemetry unit roles/pairs, and Planning per-Nomination legacy reference-mapping evidence. No data classifications or updates. |
| V20261008_026__hmr_080_planning_nomination_integrity.sql | Fail-closed preflight; only explicitly approved reference-ID transition; remove the two misowned Planning-catalog FKs; install intrinsic/code/family/scenario integrity and validate all own constraints. |

025 creates only these bounded metadata stores:

- hidra_custody_planning_product_policy: actual catalog_entry_id primary key,
  active and nonblank approval_reference; own FK to hidra_custody_catalog_entry.
- hidra_telemetry_planning_unit_role: (unit_id, usage_role) primary key, usage_role
  QUANTITY or RATE, active and approval_reference; own FK to hidra_telemetry_unit.
- hidra_telemetry_planning_unit_pair: (quantity_unit_id, rate_unit_id) primary key,
  active and approval_reference; own unit FKs. Both roles must resolve through
  the owner provider. These are explicit approved compatibility facts.
- hidra_planning_nomination_reference_mapping: (nomination_id, field_name) key,
  field_name PRODUCT/QUANTITY_UNIT/RATE_UNIT, exact legacy_id, canonical_owner_id
  and nonblank approval_reference; own Nomination FK, no cross-module target FK.
  Mappings are per record/field, not guessed global aliases.

A nonempty historical deployment pauses Nomination writes, migrates through 025,
then provisions independently reviewed approvals/mappings before requesting 026.
Even identical old/new IDs require explicit mapping evidence; coincident names/IDs
do not establish equivalence. Tests may insert clearly identified owner-approved
fixtures; the production migrations contain none.

026 locks migration inputs/affected records against concurrent changes and validates
every existing positive quantity, strict interval, timestamp, revision/code identity,
exact nomination family, same-revision scenario, coherent optional asset pair and
complete mapping for each populated product/unit field. It verifies each mapped
target exists in the accepted owner store with approved role/product/pair metadata.
Valid inactive approvals/owner rows remain legal for historical mappings; active
fresh eligibility is enforced by the runtime contract. Missing/ambiguous approvals,
wrong legacy identity, unknown targets, incompatible units, invalid historical rows
or duplicates abort with actionable diagnostics and no data/constraint partial commit.

Only after complete preflight, 026 removes fk_hra111_planning_010 and _011 and applies
the reviewed mapping to those three scalar ID fields. This explicit owner-approved
transition is authorized by NOM-EXEC-01; it is not an automatic data repair. Preserve
every other column, including topology/Party snapshots, numeric values and timestamps.
Null optional rate unit stays null. Retain mapping evidence. No deduplication, implicit
identity mapping or automatic correction of intrinsic historical violations.

Reuse uq_hmr094_scenario_revision to add the nullable Nomination
(scenario_id,revision_id) -> PlanScenario(id,revision_id) own composite FK. Retain
existing revision/nomination-type/PlanTarget relationships. Owner metadata own FKs
restrict deletion of registered product/unit identities; metadata identity/approval
history must not be deleted/truncated or remapped after successful transition.
Active flags may change with write serialization; an active-to-inactive change does
not rewrite historical Nomination evidence. Do not promise arbitrary raw SQL supplies
application-level Party/Topology evidence or a durable cross-module constraint.

### Admitted implementation validation

Reassess local Java 21, Maven/dependency/DNS, Docker and PostgreSQL before implementation.
Run the actual commands; do not substitute prepared fixtures/stubs for runtime evidence.

- `./mvnw -q -DskipTests compile`
- `./mvnw -q -Dtest=NominationSemanticRemediationTest test`
- `./mvnw -q -Dtest=PlanningProductReferenceQueryAdapterTest,PlanningUnitReferenceQueryAdapterTest,PlanningPartyReferenceQueryAdapterTest test`
- `./mvnw -q -Dtest=NominationSemanticPostgresIntegrationTest,PlanTargetSemanticRemediationTest,PlanTargetSemanticPostgresIntegrationTest test`
- `./mvnw -q -Dtest=ArchitectureGuardrailTest,ForensicRemediationClosureTest,DomainInvariantGuardrailTest,DomainPersistenceMirrorGuardrailTest,InternalReferenceIntegrityMigrationTest test`
- `./mvnw -q test`
- `./mvnw -q clean verify`
- `git diff --check`

Focused checks: quantity sign, strict period, audit timestamps, all 26 fields/statuses,
same-revision scenario, duplicate revision/code (same code in another revision legal),
exact active fresh NOMINATION_TYPE and inactive unchanged history; unapproved/wrong
product/units/pairs, optional rate unit, no inferred rate pairing, Party/Topology
missing/fresh/unchanged snapshots, neutral contract and direct-save flush rollback.
Owner tests exercise real provider queries/wiring and missing/inactive role/pair facts.

Real PostgreSQL/Spring-JPA tests must use proxied direct saves and actual owner
providers, not permissive mocked contracts as evidence. Cover empty database,
staged 025 provisioning, 026 exact approved ID transition with all other columns
unchanged, absent/mismatched/ambiguous mapping, unknown/incompatible owner targets,
invalid quantities/intervals/duplicate codes/scenarios, migration rollback including
the old FK/ID state, explicit valid owner fixture retry, immutable mapping/policy
history and family guards. Two connections/transactions test duplicate code races,
revision/scenario changes, NOMINATION_TYPE family/active mutation, owner deletion
and eligibility deactivation against fresh writes. Check stable owner lock order,
one winner where appropriate, complete rollback and valid historical replay.
Keep prior PlanTarget validation/dependent FK behavior and the 551 = 549 + 2 inventory
reconciliation in the same verification tree; do not defer known guardrail drift.

### Preflight publication and next action

Exact supporting commit: `docs(planning): record solo Nomination execution preflight`.
This supporting task writes ONLY the two canonical documents. HMR-080 remains
**BLOCKED — NOM-EXEC-01 technical envelope acceptance pending**, not implemented.
Ownership is ACCEPTED; no repeat ownership question. Count remains 56 CI-confirmed
plus one blocked, 57 evaluated. HPR-P2-008 stays OPEN; version 0.6.0-SNAPSHOT unchanged.

Validate the two-document diff and all canonical Markdown with the documentation
workflow checks. Publish once under d073ae13f9a79726f41839c4dec7f49fc6d57f8d lease,
verify immutable trees/blobs, confirm Documentation Validation starts and STOP
without waiting for completion. Production CI ignores documentation-only pushes.
Next selects the standalone HMR-080 envelope above; implementation records acceptance,
retains exact semantic message, publishes with expected-head lease and stops once
production CI starts. A Fail repairs the responsible scope before later work.

Pre-publication checks passed: exact two-document scope, git diff --check, all
82 canonical Markdown UTF-8/nonempty/conflict-marker checks, identical preflight
sections, 31 unique proposed scope paths, forward 025/026 naming and existing
PlanTarget validation targets. No production/test/migration/POM/workflow or legacy
file changed. This documentation-only task claims no new Maven/runtime result.

## HPR-P2-008 solo HMR-080 execution acceptance — 2026-10-08

Owner Next after 3df2ec4548eec8922f122c44bf0a1ac18d2256f3 ACCEPTS NOM-EXEC-01
in full: twelve-part design, 31-path scope, owner contracts/policies, explicit approved
historical mapping-only transition, forward 025/026, inventory reconciliation and
runtime validation. NOM-OWNER-01 remains accepted. No repeat acceptance is needed.
Main is unchanged at that preflight; documentation CI #111 passed. Executable tree
is identical to green CI #602 on 74ef372c82a790cdad63a77038fd60afb0de9c44.
HMSR-094 and current 26-field source/schema/owner boundaries were recovered before
mutation. HMR-080 is IN PROGRESS, not completed. HPR-P2-008 stays open.

## HPR-P2-008 solo HMR-080 implementation — 2026-10-08

Exact semantic commit: `fix(planning): remediate semantic review Nomination`.
Parent/expected-head lease: 3df2ec4548eec8922f122c44bf0a1ac18d2256f3.
NOM-OWNER-01 and the complete NOM-EXEC-01 envelope are accepted. HMR-080 is
IMPLEMENTED — PRODUCTION CI PENDING. Current count: 56 CI-confirmed, one pending,
zero blocked/still-required, 57 evaluated. Earlier blocker/preflight states are history.

Preserved all 26 fields, status values, persistence mirror and neutral optional
contract reference. Positive quantity, strict period and audit timestamps are
intrinsic. Direct saves are REQUIRED transactional, lock existing records, resolve
revision/exact nomination family/same-revision scenario, enforce revision/code
uniqueness and flush validated records. Exported owner providers join that transaction:
Custody actual-ID product approval, Telemetry QUANTITY/RATE roles and explicit pairs,
and optional Party identity/code. Owner rows and approval facts are locked; unit and
Party identity locks use stable order. Fresh references require eligible evidence;
unchanged valid history retains inactive references and canonical stored snapshots.
Fresh Party/Topology snapshots come from their owners. No private-module imports or
cross-module database FKs were introduced.

025 installs four empty approval/mapping stores. 026 locks inputs, fails closed on
invalid history or missing/wrong approved mappings, then removes only the two misowned
Planning-catalog FKs and transitions the three scalar owner IDs. All other columns
remain unchanged. The own scenario composite FK, intrinsic/code constraints, exact
family guard and immutable approval/mapping history are installed. Published migrations
and prior PlanTarget integrity remain unchanged. Nonempty deployments still require
staged 025 provisioning by owners before 026; no guessed or seeded mappings exist.
The historical inventory is explicitly reconciled as 551 = 549 structural constraints
+ two owner-contract reclassifications, without replacing them with cross-module FKs.

Validation attempts (tracked wrapper is nonexecutable, so invoked through bash):

- `bash mvnw -q -DskipTests compile`
- `bash mvnw -q -Dtest=NominationSemanticRemediationTest test`
- `bash mvnw -q -Dtest=PlanningProductReferenceQueryAdapterTest,PlanningUnitReferenceQueryAdapterTest,PlanningPartyReferenceQueryAdapterTest test`
- `bash mvnw -q -Dtest=NominationSemanticPostgresIntegrationTest,PlanTargetSemanticRemediationTest,PlanTargetSemanticPostgresIntegrationTest test`
- `bash mvnw -q -Dtest=ArchitectureGuardrailTest,ForensicRemediationClosureTest,DomainInvariantGuardrailTest,DomainPersistenceMirrorGuardrailTest,InternalReferenceIntegrityMigrationTest test`
- `bash mvnw -q test`
- `bash mvnw -q clean verify`

All seven exited 1 before compilation: uncached Spring Boot parent 4.1.1 and
`repo.maven.apache.org: Temporary failure in name resolution`. Java 17.0.20 is local;
Java 21, Docker and PostgreSQL executables are absent. No local Maven/JUnit/Spring-JPA,
PostgreSQL migration/rollback/race or OpenAPI success is claimed. Dedicated real-owner
PostgreSQL/Spring-JPA tests are prepared for CI, including fail-closed staged migrations,
exact historical transition, immutable metadata, rollback after flush, eligibility and
parent/owner races, duplicate-code one-winner and dependent PlanTarget constraints.

Supplementary checks passed: Java 17 source type checks of actual changed production
and all five new test files against temporary external dependency stubs; actual domain/
mapper JVM smoke (11 checks, no JPA/database); admitted scope/header/UTF-8/Markdown/
whitespace checks, retained historical marker counts and static contract boundaries.
These supplementary checks do not replace the required runtime gates. Keep version
0.6.0-SNAPSHOT and HPR-P2-008 OPEN; no physical-survivability claim. Publish once,
verify the exact immutable tree, confirm production CI starts and STOP without waiting.
A subsequent Next reviews that CI; Fail repairs this scope before any later task.


## HPR-P2-008 solo HMR-080 CI #603 repair — 2026-10-08

User Fail authorizes repair of the responsible Nomination scope. Main is unchanged
at 41904baff2becbdd03e19d3b2da005a28ad8d8a0, tree
07fb8d1e0d400d6c897f7f0cf023fbcabde75d27. Production CI #603
(37838748435) FAILED: Java 21 compiled and ran 1,263 tests, zero assertion failures,
four errors and zero skipped. OpenAPI publication/compatibility was skipped after
the verification failure. Documentation CI #112 passed on that implementation head.

Three errors were Mockito UnfinishedStubbing in PlanningUnitReferenceQueryAdapterTest:
the unit fixture helper stubbed another mock inside an unfinished repository when/
thenReturn. Prepare each mocked unit before repository stubbing; retain all eligibility,
role/pair and lock-order assertions. The fourth error was a missing required
PlanTarget topology_asset_code in NominationSemanticPostgresIntegrationTest's dependent
FK fixture. Supply the explicit owner-approved test snapshot and retain both downstream
revision-change/deletion assertions. These are fixture corrections only.

Repair write scope: those two test files and the two canonical documents. No production
Java, published migration, POM, version, workflow or legacy file changes. Reuse the exact
HMR-080 semantic message: `fix(planning): remediate semantic review Nomination`.
Expected-head lease is the failed 41904baff2becbdd03e19d3b2da005a28ad8d8a0.

Reattempted compile, focused semantic, owner-provider, PostgreSQL/PlanTarget, guardrail,
full-test and clean-verify Maven targets through bash mvnw: all fail before compilation
because the Boot 4.1.1 parent remains uncached and Maven Central DNS resolution fails.
Local Java remains 17; Docker/PostgreSQL executables are absent. Isolated actual-source
Java type checks with temporary external dependency stubs passed for production and all
five new test files; this is not Mockito/Spring/JPA/PostgreSQL runtime success. Exact
four-file scope, retained assertions, canonical documentation and git diff --check
passed. Production runtime confirmation is pending on the repaired head.

Keep HMR-080 IMPLEMENTED — REPAIRED CI PENDING, 56 CI-confirmed plus one pending,
57 evaluated. HPR-P2-008 stays OPEN; version 0.6.0-SNAPSHOT unchanged; no physical
survivability claim. Publish once with the lease, verify the exact tree, confirm new
production CI starts and STOP without waiting. Do not execute any later task.


## HPR-P2-008 solo HMR-080 CI confirmation — 2026-10-09

Owner Next selects confirmation of the repaired Nomination head. GitHub main is
617c2eec812e3a5734957ee9fa0360f6f5613032, tree
e450c699544f99e96f2b447fc04215f7f4c5f344, unchanged since repair publication.
Production CI #604 (37841205677) completed SUCCESS on that exact SHA. Java 21
repository verification, deterministic current OpenAPI generation, base-revision
OpenAPI generation, compatibility enforcement and artifact upload all passed.
The compatibility log reports no supported breaking changes. This confirms the
admitted semantic/provider/architecture/full-test and PostgreSQL/Spring-JPA
migration/rollback/race suites in the Java 21 clean-verify tree. Documentation
CI #113 (37841205659) passed on the same SHA.

HMR-080 / HMSR-094 is COMPLETED — CI #604 PASSED. Implementation commit:
41904baff2becbdd03e19d3b2da005a28ad8d8a0; separate fixture repair:
617c2eec812e3a5734957ee9fa0360f6f5613032. CI #603's four fixture errors are
superseded by the repaired-head success. Earlier pending/blocked sections are history.
Current HMR-050..106 reconciliation: 57 evaluated, 57 CI-confirmed implementations,
zero pending, zero STILL REQUIRED and zero BLOCKED. HPR-P2-008 remains IN PROGRESS;
this confirms the last HMR implementation, not final governance/phase closure.

Supporting commit: `docs(planning): record Nomination CI confirmation`. Authorized
write scope is ONLY doc/roadmap/ULTIMATE_ROADMAP.md and
 doc/model-remediation/RECONCILIATION.md. Validate exact two-file scope, canonical
Markdown UTF-8/nonempty/conflict checks and git diff --check; publish once with
expected-head lease 617c2eec812e3a5734957ee9fa0360f6f5613032 and verify the exact
immutable tree. The executable tree must remain identical to green CI #604.
Documentation-only publication starts Documentation Validation; production CI
ignores these paths. Confirm documentation CI starts and STOP without waiting.

No new local Maven/runtime execution is required or claimed for this two-document
confirmation. Prior local Java/dependency/DNS/Docker limitations remain accurately
recorded; GitHub Java 21 CI supplies runtime evidence. Version remains
0.6.0-SNAPSHOT. No physical survivability evidence, release, later HPR execution or
HPR-P2-008 completion is claimed. Next recommended work is an explicit HPR-P2-008
final reconciliation/closure preflight; do not execute HPR-P2-009 automatically.


## HPR-P2-008 final reconciliation preflight — 2026-10-09

Owner Next after 66712d6fc6f5a383bb2f826311d7a469a531fbb4 selects this supporting
preflight only. Current main is unchanged at that SHA, tree
6a93358bfa5c977507876025b3201eac2f535304. Documentation CI #114 (37889505086)
passed. Production baseline 617c2eec812e3a5734957ee9fa0360f6f5613032, tree
e450c699544f99e96f2b447fc04215f7f4c5f344, has green CI #604 (37841205677):
Java 21 clean verify, current/base OpenAPI generation and compatibility enforcement.
A complete GitHub tree comparison found only the two canonical documents changed
between production baseline and main; every executable/test/migration blob is identical.

### Bounded current-source reconciliation

| Check | Verified result |
|---|---|
| Canonical HMR-050..106 register | 57 unique contiguous HMR codes, 57 unique HMSR references, all COMPLETED |
| Current implementation inventories | 57 subject domain records and 57 corresponding JPA entities exist |
| Dedicated semantic test inventory | All 57 subject SemanticRemediationTest files exist in their module semantic packages |
| Owner-contract architecture exports | Both guardrail inventories match exactly: 63 existing exported packages |
| Published Flyway inventory | 139 versioned migrations, no duplicate version identifiers |
| Runtime gate | Exact production baseline passed CI #604; documentation-only descendants preserve that tree |
| Reconciled outstanding work | Zero STILL REQUIRED, BLOCKED or pending-CI HMRs in this register |
| Version | 0.6.0-SNAPSHOT unchanged |

This is a register/source/evidence preflight, not a fresh independent business-rule
acceptance audit of every legacy review. It introduces no new implementation or
runtime result. No concrete regression was found by these bounded checks; completed
HMRs remain closed unless new regression evidence appears.

### Canonical drift corrected

Twelve current register rows retained stale pending-CI prose despite later green
verification; their evidence now points to cumulative CI #604. Corrected the current
HMR-054 implementation-required sentence, HMR-080 exclusion, one-blocked progress
summary and ambiguous historical baseline heading. The malformed HPR-P2-009 registry
row contained a stale Batch 6 HPR-P2-008 status appended after its seven proper fields;
removed that appended fragment while preserving P2-009 PENDING and its dependency.
Historical batch preparation, local environment limits, repair and earlier blocker
sections remain evidence; they do not override the current register.

### Remaining closure and deployment boundaries

HPR-P2-008 remains IN PROGRESS/OPEN under the user's explicit instruction. This
preflight does not mark it completed, unlock HPR-P2-009, execute a later HPR, change a
release/version, or claim physical survivability. Final closure is a separate explicit
decision; 57 CI-confirmed HMR implementations alone must not silently close governance.
The successor HPR-P2-009 permanent-decision transfer remains PENDING, as do subsequent
P2 governance/documentation gates. No successor files are written by this preflight.

Owner-approved policy/mapping provisioning remains a deployment prerequisite where
historical data exists: PlanTarget 019/020 and Nomination 025/026 intentionally fail
closed without approved facts. Green empty-database/test fixtures do not assert that
an actual populated deployment has approved mappings. Preserve published migrations;
never seed guessed mappings, repair historical records silently or add cross-module FKs.

### Supporting publication scope and checks

Exact supporting commit: `docs(model-remediation): record final reconciliation preflight`.
Write ONLY doc/roadmap/ULTIMATE_ROADMAP.md and doc/model-remediation/RECONCILIATION.md.
Validate 57-row completeness/source/test identities, matching exports, unique migration
versions, seven-field P2 registry rows, canonical Markdown UTF-8/nonempty/conflict checks
and git diff --check. Publish once with expected-head lease
66712d6fc6f5a383bb2f826311d7a469a531fbb4; verify exact immutable tree/blobs and all other
files unchanged. Confirm Documentation Validation starts and STOP without waiting.
Production CI ignores this documentation-only push. No local Maven run is required or
claimed for this two-document task. Next recommended decision is the explicit
HPR-P2-008 closure disposition; this preflight provides evidence without granting it.


## HPR-P2-008 owner-authorized closure — 2026-10-09

After the final preflight, the assistant explicitly asked whether Next authorizes
closing HPR-P2-008 and supersedes the earlier instruction not to mark it complete.
The owner's subsequent Next is the answer accepting that closure disposition.
This supersedes the earlier keep-open restriction for HPR-P2-008 only. It does not
authorize executing HPR-P2-009 or closing the P2 phase.

Current main/expected-head lease: 51eb83ce2cb764483d5af581fa5970560bc9e0b2.
Preflight tree: 0853ced905805c39ea8d08533615e59d57bdd9e1. Documentation
CI #115 (37890008753) passed on that exact SHA. Production baseline remains
617c2eec812e3a5734957ee9fa0360f6f5613032, tree
e450c699544f99e96f2b447fc04215f7f4c5f344, with CI #604 (37841205677) PASSED:
Java 21 clean verify, current/base OpenAPI generation and backward compatibility.
Only the two canonical documents differ from that green executable tree.

HPR-P2-008 is COMPLETED. The registered HMR-050..106 obligations are reconciled:
57 evaluated, 57 CI-confirmed implementations, zero pending, zero STILL REQUIRED,
zero BLOCKED and zero superseded. The final preflight checked all 57 subject domain
records, 57 JPA entities, 57 dedicated semantic tests, 63 matching exported contract
packages, 139 uniquely versioned migrations and the current seven-field P2 registry.
Each independent HMR commit and repair remains in history; this closure does not
squash or replace semantic implementations. Completed HMRs remain closed absent
concrete regression evidence. Earlier open/pending/preflight states are historical.

Exact registered closure commit: `fix(model): continue reconciled semantic remediation`.
This closure changes ONLY doc/roadmap/ULTIMATE_ROADMAP.md and
 doc/model-remediation/RECONCILIATION.md. No production/test/migration/POM/workflow,
legacy document, tag or release is changed. Validate the two-document scope, 57-row
register/source identities, P2 registry shape/statuses, canonical Markdown and
git diff --check; verify exact immutable published tree/blobs and all other files
unchanged. Publish once with the expected-head lease above. Documentation Validation
starts for this documentation-only push; production CI ignores these paths. Confirm
documentation CI starts and STOP without waiting for completion.

Closure is semantic-remediation execution governance, not physical survivability
or production-data acceptance. Populated PlanTarget/Nomination deployments still
require independently approved owner policies/mappings before their fail-closed
migrations; no guessed facts or silent historical repair are authorized. The recorded
local Maven/Java/Docker limits remain unchanged; no new local runtime result is claimed.
Version remains 0.6.0-SNAPSHOT. P2 stays OPEN through its remaining governance gates.

Next registered task: HPR-P2-009 — `docs(model-remediation): canonicalize semantic decisions`.
Its HPR-P2-008 dependency is now satisfied; status remains PENDING. A subsequent Next
selects a fresh current-source preflight and bounded write scope for that task. This
closure neither performs the permanent-decision transfer nor modifies its successor
files automatically.


## HPR-P2-009 semantic canonicalization preflight — 2026-10-09

### Selection, gates and publication boundary

The owner's Next after HPR-P2-008 closure selects this current-source preflight.
HPR-P2-008 is COMPLETED; HPR-P2-009 remains PENDING and its permanent transfer has
not started. P2 remains OPEN. This proposal does not select HPR-P2-010..013 or P3.
A subsequent Next accepts the complete bounded implementation envelope below;
execute only HPR-P2-009, using its exact registered commit:
`docs(model-remediation): canonicalize semantic decisions`.

Verified main/expected-head lease: 5f98dc1c5e6a329a54b64a912caa2cd5c5a8989c,
tree 5cf152ed59ba92f97125b1cbe2bd866c061048c5. Closure documentation CI #116
(37891083272) PASSED on that head. Production baseline remains
617c2eec812e3a5734957ee9fa0360f6f5613032, tree
e450c699544f99e96f2b447fc04215f7f4c5f344, with CI #604 (37841205677) PASSED.
Its Java 21 clean verify and current/base OpenAPI compatibility are prior exact-head
runtime evidence, not new tests performed by this documentation task.

### Recovered evidence and reason for the envelope

The legacy semantic review register contains 123 unique HMSR subjects: 19 APPROVED
and 104 REVISE, with no DEFER/REMOVE entries. All 123 subject domain source files
exist at this head. The legacy remediation register maps 104 subject HMRs
(HMR-003..106); HMR-001/002 are supporting preparation. The canonical execution
register covers the later 57 (HMR-050..106), all CI-confirmed. These are different
populations: do not claim that the 57-row reconciliation independently audited all
104 remediations or that historical APPROVED alone proves a current invariant.

The current source inventory has 24 modules, 148 domain/model Java files, 470 JPA
entity files and 70 application/contract Java files excluding package-info.
The last closure verification matched 63 exported contract packages across the two
architecture inventories and 139 unique Flyway versions. Recompute these inventories
at the implementation parent; do not use counts as substitutes for source review.

Canonical domain documents retain the HPR-P2-003 baseline and module documents the
HPR-P2-004 baseline. Planning now exports
`src/main/java/dz/sh/hidra/modules/planning/application/contract/monitoring/MonitoringPlanTargetReferenceContract.java`,
where the old module document described no export. The Alarm domain description
allows ESCALATED as sufficient for normal close, while current
`src/main/java/dz/sh/hidra/modules/alarm/domain/service/AlarmLifecycleGuard.java`
requires CLEARED state or populated clearedAt unless explicitly cancelled, and
rejects already closed alarms. Root navigation and the governance register retain
pre-closure remediation wording. These concrete drifts justify refreshing the full
existing domain/module set rather than copying stale review conclusions.

### Complete implementation design

1. Recover each of the 123 detailed HMSR reviews and its historical disposition;
   map the 104 REVISE subjects to their HMR identifiers. Reconcile every durable
   statement with current domain/entity, application write paths, repository guards,
   owner contracts/providers, migrations and relevant executable tests. Where source
   does not establish the proposed rule, document the uncertainty rather than invent
   a rule or open a new implementation obligation without concrete evidence.
2. Create `doc/domain/SEMANTIC_DECISIONS.md` as the durable semantic decision register.
   Include one unique traceability entry per reviewed module.subject, source HMSR,
   HMR where applicable, canonical decision location and current source/test evidence.
   Separate lasting rules from historical review verdicts and execution results.
   Index newer source types in module inventories without inventing HMSR identifiers.
3. Transfer the cross-module invariants into the existing domain ownership/language
   and focused domain documents. Public scalar owner contracts, module-private
   persistence, local integrity/composite keys and explicit ownership remain distinct;
   no cross-module database FKs or private-module imports are proposed.
4. Refresh all 24 existing module documents and their index against current source.
   Describe actual domain types, exported contracts, inbound owner evidence and
   controlled write paths with navigable evidence; retain current versus target
   boundaries. Module summaries and the semantic register must cross-link rather
   than duplicate contradictory decision narratives.
5. Explain exact catalog membership, active fresh references versus valid unchanged
   historical mappings, canonical owner snapshots and approval metadata only where
   current source enforces them. Document PlanTarget numeric/text policy and Nomination
   owner-approved product/quantity-role mappings as fail-closed deployment prerequisites;
   no guessed mapping, exclusivity, unit pair, seed or historical repair is authorized.
6. Transfer Planning revision/parent contexts and the Monitoring target scalar
   contract, Telemetry point/reading agreement and trusted-reading evidence. Explain
   populated optional-reference contexts and historical snapshot preservation without
   promoting optional references to mandatory ones or claiming universal trust.
7. Transfer Alarm raised-state/lifecycle, acknowledgement, clear/close/cancel,
   suppression approval and shelving-expiry behavior from the actual orchestration
   and guard paths. Keep Incident, LeakDetection and Monitoring responsibilities
   separate. Correct the identified close-rule drift with precise source evidence.
8. Transfer authenticated actor provenance, append-only/immutable evidence, guarded
   repositories, transactional writes and owner-provider validation where implemented.
   Workflow activation/callback/approval rules, document storage rollback and unknown
   commit limits must follow actual code/tests; do not promise stronger guarantees.
9. Preserve analytics/simulation advisory boundaries and all deferred AI inference,
   autonomous actuation, digital-twin, TimescaleDB/PostGIS and industrial protocol
   claims. Documentation transfer is neither physical survivability nor populated
   production-data acceptance, and introduces no new operational SLO or retention value.
10. Update root navigation and the documentation register for the new decision register
    and refreshed source applicability. Supply metadata directly or through containing
    indexes: status, owner authority, exact verified parent, evidence, current/target,
    unresolved decisions and verification point. Do not invent named business owners.
11. Preserve every legacy `docs/**` file byte-for-byte as review/execution history.
    Preserve all HMR commits, migration history and the closed 57-row reconciliation.
    Update only current canonical execution memory for HPR-P2-009 results; do not
    rewrite historical batch/preflight prose as if it were current. Keep version
    0.6.0-SNAPSHOT and remaining P2/P3 statuses unchanged.
12. Stop before any code, test, migration, database dictionary, architecture/API,
    operational procedure or workflow change becomes necessary. Record concrete
    out-of-scope findings and reconcile the task envelope before expanding it.
    This is one documentation commit, not reopened semantic implementation batches.

### Exhaustive future implementation write scope — 37 paths

Only the following paths are admitted for the subsequent HPR-P2-009 implementation;
all existing paths are updates, and SEMANTIC_DECISIONS.md is the sole new file:

- `doc/README.md`
- `doc/governance/DOCUMENT_REGISTER.md`
- `doc/domain/ALARM_INCIDENT_LEAK.md`
- `doc/domain/ASSETS_INTEGRITY.md`
- `doc/domain/DOMAIN_OWNERSHIP.md`
- `doc/domain/README.md`
- `doc/domain/SIMULATION_ANALYTICS_AI.md`
- `doc/domain/TOPOLOGY_TELEMETRY.md`
- `doc/domain/UBIQUITOUS_LANGUAGE.md`
- `doc/domain/SEMANTIC_DECISIONS.md`
- `doc/modules/README.md`
- `doc/modules/alarm.md`
- `doc/modules/analytics.md`
- `doc/modules/assets.md`
- `doc/modules/audit.md`
- `doc/modules/configuration.md`
- `doc/modules/custody.md`
- `doc/modules/documents.md`
- `doc/modules/hse.md`
- `doc/modules/identity.md`
- `doc/modules/incident.md`
- `doc/modules/integration.md`
- `doc/modules/integrity.md`
- `doc/modules/leakdetection.md`
- `doc/modules/monitoring.md`
- `doc/modules/notification.md`
- `doc/modules/organization.md`
- `doc/modules/party.md`
- `doc/modules/planning.md`
- `doc/modules/reporting.md`
- `doc/modules/risk.md`
- `doc/modules/simulation.md`
- `doc/modules/telemetry.md`
- `doc/modules/topology.md`
- `doc/modules/workflow.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`

### Admitted validation and completion evidence

Before implementation, reverify main, its documentation CI and the executable green
baseline; changed source requires fresh reconciliation. Recover all detailed reviews,
not just register summaries. Before publication, verify 123 unique traceability rows,
19/104 historical dispositions, 104 HMR mappings, all 24 module documents and exact
current inventory/exports. Validate each documented invariant against its cited source
and focused tests/migrations; a path-existence check alone does not validate meaning.
Check Markdown relative links and referenced anchors/source paths in the touched set,
metadata coverage, current/target discipline and absence of unsupported guarantees.
Run the existing documentation workflow's UTF-8/nonempty/conflict-marker validation
for all canonical Markdown (currently 82 files; the proposed new register makes 83),
`git diff --check`, and exhaustive 37-path allowlist/legacy-byte-preservation checks.
No Maven runtime rerun is required for a documentation-only tree; report prior CI and
new documentation checks separately, without inheriting unperformed test claims.

Complete HPR-P2-009 only after the durable transfer and these checks actually pass.
Publish the exact registered implementation commit directly to main once with its
fresh expected-head lease. Verify exact immutable parent/tree/blobs and unchanged
files; confirm Documentation Validation has started, then STOP without waiting.
Production CI ignores the documentation-only scope. Do not automatically execute
HPR-P2-010; a new Next is required. If documentation CI fails, repair its responsible
scope before any successor.

### This preflight's narrow supporting publication

Exact supporting commit: `docs(model-remediation): record semantic canonicalization preflight`.
Write ONLY `doc/roadmap/ULTIMATE_ROADMAP.md` and
`doc/model-remediation/RECONCILIATION.md`. The 37-path transfer is proposed, not
performed or accepted by this preflight alone. Validate the measured review/HMR/source
inventory, unique scope, seven-field P2 registry/statuses, all 82 canonical Markdown
files and `git diff --check`. Verify exact published tree/blobs and unchanged files;
advance main once using the preflight lease above. Confirm documentation CI starts
and STOP. No new runtime test, physical evidence, tag or release is claimed.


## HPR-P2-009 accepted execution — 2026-10-09

The owner's Next after publication of preflight
44d4fe773d69ed51dd90820140c8d9e7aee6cba2 accepts its twelve-part design and
exhaustive 37-path scope. Execute HPR-P2-009 only, exact commit
`docs(model-remediation): canonicalize semantic decisions`. Main is unchanged at
that preflight parent, tree 46281ca048114fa73cf428ed0f197f3e407e43d4;
documentation CI #117 (37891746514) PASSED. Production CI #604 (37841205677)
remains PASSED on executable baseline 617c2eec812e3a5734957ee9fa0360f6f5613032.
The accepted transfer is documentation-only; HPR-P2-008 stays COMPLETED, P2 stays
OPEN and no successor is selected. Execution results follow below.


### Completed transfer and validation

HPR-P2-009 is COMPLETED within its accepted documentation scope. Created only
`doc/domain/SEMANTIC_DECISIONS.md`, with 123 unique subject decision entries,
19 APPROVED / 104 REVISE historical dispositions and all 104 subject-HMR mappings.
Recovered the detailed legacy reviews and reconciled the transferred rules with
current domain/JPA, application/repository guard, owner contract, migration and test
evidence. Historical verdicts are provenance, not new governance statuses. Every
REVISE subject links its existing dedicated semantic test; these tests were inspected,
not newly executed. The completed 57-row HPR-P2-008 register remains byte-preserved.

Refreshed all 24 module inventories with linked domain/policy/service/use-case/API,
JPA entity/repository and owned/imported contract evidence. Updated the existing
seven domain documents, root navigation and documentation register at source parent
44d4fe773d69ed51dd90820140c8d9e7aee6cba2. Corrected Alarm normal close eligibility,
made Telemetry trust promotion evidence explicit, transferred Planning/Monitoring
reference/policy context, kept direct versus ordinary Identity grant contracts
separate, and documented owner snapshots, append-only evidence and binary rollback
limits. Subject-specific optionality/history exceptions remain explicit.

Current inventory checks PASSED: 24 modules; 148 domain/model Java files comprising
124 actual model types plus 24 package descriptors; 470 JPA entities; 70 contract
Java files excluding package-info; 63 matching architecture exports; 139 unique
Flyway versions. AlarmLifecycleEvent is the single added model beyond the historical
catalogue and receives no invented HMSR identifier.

Documentation validation PASSED: 83 canonical Markdown files valid UTF-8, nonempty
and free of conflict markers; 123 unique traceability rows and decision anchors;
19/104 disposition totals and 104 exact historical HMR mappings; all 24 linked
current model/entity/export inventories; 2,719 existing relative links/anchors in
the touched set; inherited/direct metadata; exact 37-path allowlist with one new
file; unchanged HMR register rows and unrelated HPR statuses; seven-field P2 registry;
`git diff --check`. The local checks were run with the temporary
`p2-009-validate.py` inspection script and the documentation workflow's equivalent
UTF-8/nonempty/conflict checks; no new repository test or CI workflow was introduced.
The link check resolves file/heading destinations; semantic meaning was reviewed
separately against the cited source and tests rather than inferred from path existence.

No local Maven/runtime test was required or run for this docs-only tree; production
CI #604 remains prior exact executable evidence, not a fresh validation result.
Preserved all source/configuration/tests/POM, published migrations, API/database/
architecture/operations sets and every legacy `docs/**` byte. In particular, the
HPR-P2-006 generated dictionary retains its own historical applicability and is not
regenerated under this scope. Version remains 0.6.0-SNAPSHOT. No release/tag, guessed
policy mapping, silent historical repair or physical survivability claim is introduced.
Approved mappings and populated-deployment acceptance remain separate owner facts;
no new business uncertainty is resolved by a historical APPROVED label.

Exact implementation commit: `docs(model-remediation): canonicalize semantic decisions`.
Publication uses expected-head lease 44d4fe773d69ed51dd90820140c8d9e7aee6cba2,
one parent and one advancement of main. Verify immutable published parent/tree/blobs
and unchanged files against the exact local index before publication. Confirm
Documentation Validation starts, then STOP without waiting for its completion;
production CI ignores these documentation-only paths. A failed documentation run
must be repaired before successor work.

Next registered task: HPR-P2-010 — `docs(data): establish data governance baseline`.
It remains PENDING. A subsequent Next selects its current-source preflight after
checking this transfer's documentation CI, not automatic retention/provenance design
or P2 closure. HPR-P2-011..013 remain PENDING and P3 DEFERRED.


## HPR-P2-010 data-governance preflight — 2026-10-09

### Selection and verified gates

The owner's Next after HPR-P2-009 publication selects this current-source preflight.
HPR-P2-010 remains PENDING; the data-governance set has not been created. A subsequent
Next accepts the complete documentation design and nine-path implementation scope
below, selecting only HPR-P2-010 and its exact registered implementation commit:
`docs(data): establish data governance baseline`.

Verified main/expected-head lease: a5eba6e4a3d8e9761c12cc0d6edcaf25422eaa3b,
tree 32b1cfda4869ce6214a5a08a2c5674946d77b8e6. Documentation CI #118
(37894279307) PASSED on that exact head. Production baseline remains
617c2eec812e3a5734957ee9fa0360f6f5613032, tree
e450c699544f99e96f2b447fc04215f7f4c5f344, with prior production CI #604
(37841205677) PASSED. Subsequent commits are documentation-only. No new runtime
result or physical survivability evidence is established by this preflight.
HPR-P2-001 dependency is satisfied; HPR-P2-008/009 stay COMPLETED, P2 OPEN,
HPR-P2-011..013 PENDING and P3 DEFERRED. Version remains 0.6.0-SNAPSHOT.

### Current-source findings

No canonical data-governance set exists yet. Required documentation metadata and
current/target discipline follow doc/governance/DOCUMENTATION_STANDARD.md and
DOCUMENT_LIFECYCLE.md. Current module ownership and durable semantic rules are
already in doc/modules/ and doc/domain/SEMANTIC_DECISIONS.md; data governance must
cross-link them rather than create conflicting ownership or repeat legacy verdicts.

Retention has several distinct evidence populations:

| Population | Observed authoritative evidence | Preflight interpretation |
|---|---|---|
| Operational recovery | ops/production/postgres/pgbackrest/pgbackrest.conf.tpl, repo1 time-based full retention 35 days | Existing approved P1 recovery control, not a row-level business retention period |
| Monthly recovery points | ops/production/postgres/pgbackrest/RETENTION_POLICY.md and check-monthly-retention.sh; HIDRA-P1-BACKUP-RETENTION-001, October 2026 start, one completed full point per UTC month, count ceiling 12, latest-point age no more than 35 days | Preserve BOOTSTRAP versus MATURE coverage and the approved policy; configuration alone is not storage independence or new restore proof |
| Metrics | ops/production/observability/prometheus/prometheus.service.tpl; --storage.tsdb.retention.time=30d | Existing metrics storage control, not Audit/business data policy |
| Logs | ops/production/observability/loki/loki.yml; retention_period 2160h with compactor retention enabled | Existing 90-day log control; deletion configuration does not establish business-record disposal approval |
| Audit business evidence | AuditRetentionPolicyJpaEntity: retentionDays, archiveAfterDays, legalHoldSupported, purgeAllowed and validity; AuditRetentionPolicyPort only exposes available(referenceId) | Metadata/API shape is present; approved per-category values and implemented enforcement must be proven separately |
| Document business evidence | DocumentRetentionRecordJpaEntity: retainUntil, legalHold/reason, archive object/time and disposal metadata | Persistence fields do not prove an archive worker, legal-hold enforcement or authorized deletion |

The Audit retention port and Audit/Document retention repositories are declarations
in current source; this bounded search did not establish a retention/disposal worker
from those declarations. Implementation must examine wider service/configuration/test
paths before making a stronger absence or enforcement claim. Archive lifecycle flags,
backup expiry, append-only evidence and storage cleanup are different operations.
Do not transplant infrastructure retention into Telemetry, Audit, Documents, employee,
Custody or any other business records.

Provenance evidence includes Telemetry raw/assessment/trusted linkage, canonical owner
snapshots, Planning/Monitoring revision/reference contexts, Analytics dataset lineage
and data-source entities, captured versions/watermarks, document checksums and binary
rollback behavior, Integration exchange/dead-letter provenance, and append-only
Audit/Workflow/Alarm evidence. Each proves its coded scope, not universally complete
lineage, authenticated provenance for every row, or external source authenticity.

Legacy docs/roadmap/data-provisioning.md remains paused/blocked historical execution
material. docs/data-provisioning/source-inventory.md, target-inventory.md and
source-classification.md retain provisional source identities, limitations and owner/
security gates; some evidence concerns HyFloAPI source artifacts rather than approved
HidraAPI import data. Inventory presence, Git access, filename chronology or structural
row counts are not import eligibility. HPR-P2-008/009 completion does not automatically
approve a dataset, source-target mapping or import; no legacy HDP/HMS task is selected.

### Complete proposed documentation design

1. Establish doc/data/README.md as CURRENT index for the bounded data-governance set,
   with exact source parent/date, business-module accountability, evidence links,
   current versus target/deferred separation and explicitly unresolved owner decisions.
   Named human owners, legal retention duties and dataset approval are not invented.
2. DATA_GOVERNANCE.md records ownership for all 24 existing modules, distinguishing
   operational truth, reference/master data, raw/trusted evidence, derived outputs,
   configuration/security material and historical records where source supports it.
   Link actual module/semantic authority and the existing security controls. Record
   unknown classification/approval facts explicitly; do not manufacture sensitivity
   labels, policy IDs, access rights or a new enterprise classification taxonomy.
3. RETENTION_ARCHIVAL.md separates approved infrastructure controls from business
   policy metadata and actual enforcement. Preserve the exact 35-day repo1, monthly
   repo2 policy with bootstrap/mature boundary, Prometheus 30d and Loki 2160h controls
   with source/policy applicability. Cross-link prior P1 evidence at its actual
   historical deployment/schema baseline, without claiming this task proves physical
   independence/restorability or mature coverage. Business durations, triggers,
   legal-hold/disposal approvals and enforcement remain NOT ESTABLISHED where absent.
4. Identify metadata versus actual archive/purge/retention adapters, schedulers and
   tests through current-source checks. Append-only evidence protection is not a
   complete retention engine. Describe owner decisions needed before destructive
   disposal as governance prerequisites, not implemented code or a newly authorized
   purge operation. No arbitrary expiration, guessed legal period or new policy seed.
5. DATA_PROVENANCE.md gives an evidence matrix for acquisition/source IDs, correlation,
   raw/assessment/trusted reading links, revision/parent context, source watermark and
   captured dataset/report/simulation versions, canonical snapshots, actor evidence,
   checksum/hash and append-only lifecycle. State exactly which owner/write path
   verifies each fact. Hash metadata does not alone prove authenticity; snapshots are
   historical context and their controlled preservation differs from live refresh.
6. LEGACY_DATA_MIGRATION.md records the current no-import baseline and a TARGET
   governed sequence: immutable source identity and permitted read-only inspection,
   source-owner/confidentiality approval, dataset-level classification/precedence,
   explicit field/unit/identifier mapping and UNMAPPED disposition, owner-approved
   policies, validation/dry-run/reconciliation, controlled loading, rollback/recovery
   evidence and retained provenance. These are documented admission requirements,
   not approved datasets, executable ETL or permission to run legacy SQL/scripts.
7. Distinguish Flyway schema migration from business-data provisioning. Applied
   migrations/history remain immutable; startup checks must not be bypassed. Planning
   target and Nomination migrations fail closed pending approved actual-ID policies/
   product/unit mappings; do not seed guessed facts or silently repair history.
   Link the canonical database policy with its stated baseline, not a claim that
   its older 82-migration dictionary describes the current 139-version chain.
8. Update root navigation and DOCUMENT_REGISTER.md to register the five-file set and
   its exact applicability. Keep legacy docs/** and all existing semantic, database,
   API, security and operational evidence byte-preserved. No new operational standard,
   legal opinion, release, POM/code/migration/configuration/workflow or imported dataset.
9. Record acceptance, validation and completion only in canonical execution memory
   after the actual transfer. Complete only HPR-P2-010; do not execute HPR-P2-011/012,
   regenerate the dictionary, unblock legacy HDP/HMS tasks, close P2 or run P3.
   A concrete need for an unadmitted implementation change requires scope reconciliation
   before that change; documenting an unestablished business fact alone is not a blocker.

### Exhaustive future implementation write scope — nine paths

Create only:

- `doc/data/README.md`
- `doc/data/DATA_GOVERNANCE.md`
- `doc/data/RETENTION_ARCHIVAL.md`
- `doc/data/DATA_PROVENANCE.md`
- `doc/data/LEGACY_DATA_MIGRATION.md`

Update only:

- `doc/README.md`
- `doc/governance/DOCUMENT_REGISTER.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`

### Admitted validation and publication

At implementation, verify fresh main/documentation CI and the green executable
baseline. Reinspect the retention policy/configuration/checker, business metadata
and any relevant runtime workers, provenance owner/write paths, prior P1 evidence
and legacy inventory/classification applicability. Resolve all evidence links/anchors;
check 24-module ownership coverage, all five new documents/index navigation and
required metadata. Every numeric retention assertion needs its existing authoritative
source and limited population; no business duration or legal obligation is guessed.
Label unresolved approvals, enforcement and import facts explicitly. Ensure all HMR
rows, unrelated HPR statuses and historical evidence are unchanged.

Run canonical UTF-8/nonempty/conflict checks (currently 83 Markdown files; five new
files make 88), relative link/anchor checks for the touched set, git diff --check and
exhaustive nine-path/legacy preservation checks. Documentation-only scope requires
no Maven/runtime rerun; prior CI and new documentation validation remain distinct.
Use exact registered implementation message, one commit and a fresh expected-head
lease to publish directly to main once. Verify exact immutable parent/tree/blobs and
all other files unchanged. Confirm Documentation Validation starts and STOP without
waiting; production CI ignores this docs-only scope. If it fails, repair before any
successor. Next after implementation selects HPR-P2-011 preflight only after its CI check.

### This preflight's supporting scope

Exact supporting commit: `docs(data): record governance baseline preflight`.
Write ONLY doc/roadmap/ULTIMATE_ROADMAP.md and
 doc/model-remediation/RECONCILIATION.md; no doc/data/ file is created now.
Validate identical envelope text, nine unique future paths, P2 registry/statuses,
all 83 canonical Markdown files, git diff --check and exact two-document scope.
Verify published immutable tree/blobs and unchanged files, advance main once using
the lease above, confirm documentation CI starts and STOP without waiting.
HPR-P2-010 stays PENDING until accepted implementation actually completes.


## HPR-P2-010 accepted execution — 2026-10-09

The owner's Next after preflight b36733fc05e789613485606e1e1dd1731b11af53
accepts the complete nine-part design and nine-path write scope. Execute only
HPR-P2-010, exact commit `docs(data): establish data governance baseline`.
Main is unchanged at that parent, tree 75b696c41115ad8b070e47699714afea30ac9053;
documentation CI #119 (37894804566) PASSED. Production CI #604 remains PASSED
on executable baseline 617c2eec812e3a5734957ee9fa0360f6f5613032. No dataset
approval/import, runtime policy implementation or successor execution is selected.

## HPR-P2-010 completed governance baseline — 2026-10-09

Accepted scope implemented as one exact-message commit:
`docs(data): establish data governance baseline`.
Verified publication parent `b36733fc05e789613485606e1e1dd1731b11af53`, tree
`75b696c41115ad8b070e47699714afea30ac9053`; parent Documentation Validation #119
(run 37894804566) passed. The executable baseline remains
`617c2eec812e3a5734957ee9fa0360f6f5613032`, tree
`e450c699544f99e96f2b447fc04215f7f4c5f344`, with production CI #604
(run 37841205677) passed. Those prior results were rechecked, not rerun locally.

Created exactly `doc/data/README.md`, `DATA_GOVERNANCE.md`, `RETENTION_ARCHIVAL.md`,
`DATA_PROVENANCE.md` and `LEGACY_DATA_MIGRATION.md` under that same directory.
Updated only `doc/README.md`, `doc/governance/DOCUMENT_REGISTER.md`, this roadmap
and `doc/model-remediation/RECONCILIATION.md`: nine paths, five new/four updated.
The index and register establish the CURRENT documentation baseline at the verified
parent; existing semantic/database/API/security/operational documents keep their own
applicability. All legacy evidence, published migrations and runtime files are preserved.

### Implementation and evidence limits

- Source-backed ownership covers all 24 implemented modules without invented human
  stewards, confidentiality classes or grants. Path-specific references/actors and
  history/snapshot preservation follow the existing permanent semantic decisions.
- Approved P1 backup, metrics and log controls are sourced separately from business
  retention metadata. The monthly checker distinction is explicit: BOOTSTRAP coverage
  permits extra distinct months within the cap; exact set equality applies in MATURE.
  Prior operator/P1 exercise evidence retains its deployed SHA and 82-migration scope.
- The bounded source/configuration/test search did not establish an application
  business retention/disposal worker wired to the Audit/Documents metadata. This does
  not assert that external deployed tools are absent. Append-only controls and rollback
  binary cleanup do not establish business archival, legal holds or disposal approval.
- Provenance identifies actual owner/write paths and their limits. Legacy inventories
  remain historical, with later structural extraction qualifying older access limits;
  counts/filenames/hash identity do not establish source ownership or dataset precedence.
  The TARGET admission sequence creates no importer and authorizes no SQL/data loading.
- Schema migrations remain distinct from import approval. Current 139-version source
  does not silently refresh the older database dictionary. Planning policy/catalog
  admission remains fail-closed; no mapping/value is fabricated or historical row repaired.

### Actual local validation

`python3 /workspace/scratch/4115643ef669/p2-010-validate.py` passed: exactly nine
allowed paths; five new documents; all 88 canonical Markdown files UTF-8/nonempty and
free of conflict markers; 100 relative links/anchors resolve; exactly 24 ownership
rows match actual module roots; 139 unique migration versions; numeric infrastructure
retention claims match existing source configuration. HMR register rows and unrelated
HPR statuses are byte-preserved; P2 rows retain seven fields; project version remains
`0.6.0-SNAPSHOT`. `git diff --check` passed. Exact-tree publication checks additionally
require all other tracked blobs unchanged. No Maven/runtime, database/import,
deployment or physical evidence campaign was performed by this documentation task.

Only HPR-P2-010 is completed. Business durations, source-owner/reuse approvals,
canonical dataset precedence, complete business enforcement and import acceptance
remain NOT ESTABLISHED. No legacy HDP/HMS task is unblocked, no release/tag is created,
HPR-P2-011..013 remain PENDING, P3 DEFERRED and P2 OPEN. Publish main once using the
expected-head lease, verify immutable commit/tree/blob identity, confirm Documentation
Validation starts and STOP without waiting for completion. Production CI ignores
this documentation-only scope. A later Next selects HPR-P2-011 preflight after the
exact-head documentation result is checked; a failure must be repaired first.

## HPR-P2-011 verification-documentation preflight — 2026-10-09

### Selection and exact baseline

The owner's Next after HPR-P2-010 selects this preflight only. Main is unchanged at
`50fd7f9cb97343a6495c2dff5bf54175145bba39`, tree
`67f4b002c6450729402d9a5a8fc351b68662665e`. Exact-head Documentation Validation
#120 (run 37896521355) PASSED. Production CI #604 (run 37841205677) PASSED on
executable baseline `617c2eec812e3a5734957ee9fa0360f6f5613032`, tree
`e450c699544f99e96f2b447fc04215f7f4c5f344`. Its job steps confirm repository
clean verify, operational-artifact validation, OpenAPI generation/base selection,
compatibility and artifact upload succeeded. These are prior CI results checked now,
not a new local execution, per-class report inspection or physical exercise.

HPR-P2-011 remains PENDING; no testing set is created now. A subsequent Next after
this published preflight accepts the complete design and exhaustive scope below for
one implementation commit: `docs(testing): establish verification documentation`.
HPR-P2-012/013 remain PENDING, P3 DEFERRED and P2 OPEN.

### Current evidence recovered

- `pom.xml` declares Java 21, Spring Boot parent 4.1.1, JUnit/Spring testing,
  Spring Security testing, Testcontainers PostgreSQL and ArchUnit 1.4.2, and a
  Surefire plugin. No Failsafe or JaCoCo plugin or numeric coverage gate is declared
  in the inspected POM/workflows. Do not invent a separate integration-test phase,
  coverage percentage, mutation gate or test count from file counts.
- Static inventory: 324 test-tree Java files; 323 filenames ending Test.java/Tests.java
  and one OrganizationMandatoryReferenceMigrationSupport.java helper; tests occur
  under all 24 module roots. All 47 directly annotated Testcontainers classes use
  disabledWithoutDocker=true. No direct @Disabled annotation was found. These are
  source counts, not discovered/executed test cases or proof that nothing can skip.
- `.github/workflows/ci.yml` runs `./mvnw -B -q clean verify` with Java 21 and a
  PostgreSQL 16 service. The test profile is actually
  `src/main/resources/application-test.properties`, not src/test/resources. It
  defaults to localhost PostgreSQL and permits datasource overrides; individual
  containers override their own URLs. HidraApplicationTests uses postgres:18-alpine,
  Flyway validation and JPA validate; focused containers include postgres:16-alpine.
  Do not turn these distinct paths into a claim that every test uses one DB/version.
- `ArchitectureGuardrailTest`, `ForensicRemediationClosureTest`,
  `DomainPersistenceMirrorGuardrailTest`, `DomainInvariantGuardrailTest` and
  `modules/GeneratedBoundaryMapperContractTest` enforce distinct boundaries through
  ArchUnit, source/reflection and generated-mapper checks. Owner-provider tests are
  a separate contract population, not substitutes for live transaction evidence.
- `InternalReferenceIntegrityMigrationTest` exercises Flyway and classified local
  foreign keys, including orphan fail-closed/rollback checks. Focused
  PlanTargetSemanticPostgresIntegrationTest, PlanActualDeviationSemanticPostgresIntegrationTest
  and NominationSemanticPostgresIntegrationTest execute selected migrations, direct
  SQL and real Spring/JPA transactions, rollback and controlled race cases. Some
  owner-contract inputs are stubbed in those fixtures; database/transaction reality
  does not imply a complete live cross-module end-to-end environment.
- HTTP/controller tests include standalone MockMvc and mocks; security/authorization,
  request context, workbench exposure, document transfer and generated contracts have
  different scopes. DocumentContentTransferServiceTest distinguishes confirmed
  rollback from unknown/committed outcomes. Presence of a controller test is not a
  blanket assertion that the full security filter chain or every route was exercised.
- CI generates sorted OpenAPI JSON with source-SHA provenance and representative
  security assertions; `.github/scripts/resolve_openapi_base.py` uses the exact PR
  target or a successful production-CI ancestor for push/dispatch, with ancestry and
  fail-closed selection. It runs test_resolve_openapi_base.py before selection and
  openapi_compatibility.py against generated base/current contracts. The canonical
  `doc/api/openapi.yaml` retains its own older HPR-P2-005 generation provenance; this
  task neither refreshes it nor asserts exact current snapshot equality.
- `.github/workflows/docs.yml` checks canonical Markdown UTF-8/nonempty/conflicts,
  not links/status/index drift or canonical-vs-generated OpenAPI equality. Production
  CI ignores doc/**, docs/** and Markdown-only scope. Its explicit uploaded artifact
  is the generated OpenAPI contract; do not describe a configured Surefire report
  upload or invent per-test results without actual retained report evidence.
- P1 scripts in ops/production/ validate artifacts/procedures and CI checks
  `.github/scripts/validate_p1_closure.py`. The retained P1 exercise distinguishes
  repository-verified from operator-supplied observations at deployed
  `66f6d7f12d1f7d52f8725cd4747cf4c777bfd29a` with 82 migrations. A static artifact
  check or PostgreSQL fixture is not new HA/DR/physical retention evidence for the
  current 139-version migration chain.

### Proposed implementation design — ten parts

1. Create a six-document CURRENT testing set with inherited index metadata: source
   parent/date, status, repository/module owner authority, actual evidence and
   explicit unresolved facts. No invented named testing authority, production
   acceptance or executed result. Separate implemented checks from TARGET additions.
2. TEST_STRATEGY.md documents the actual test populations and their limits: pure
   domain/application/unit, owner-provider contracts, source/ArchUnit/reflection,
   generated mappers, standalone HTTP/security, PostgreSQL migration, Spring-JPA
   transaction/race, full application context, CI OpenAPI and operational artifact
   checks. Distinguish source presence, selected local run, exact-head CI, skipped
   classes, deployment acceptance and retained physical/operator evidence.
3. Give source-backed commands for focused selections, full test and clean verify;
   describe Java/Maven prerequisites, dependency availability, Docker/images, isolated
   disposable PostgreSQL and actual test profile overrides. The current preflight
   and docs-only implementation require no Maven/runtime rerun; prior dependency/DNS
   limitations are historical, not a diagnosis of the present environment. Any later
   attempted command must report actual exit/results/skips and exact limitation.
   Never point destructive schema-reset tests at production or disguise a skip as pass.
4. ARCHITECTURE_TESTING.md explains actual rules and their owning tests, the narrow
   exported contract/allowlist mechanism, kernel/framework and application/API/JPA
   boundaries, reviewed workbench exception, owned persistence models and DTO mapper
   contracts. Describe rule-test mechanisms and scope accurately; package/file counts
   are not dynamic behavioral or universal semantic coverage.
5. DATABASE_TESTING.md separates full Flyway/context validation from targeted SQL
   migration fixtures and real Spring/JPA adapter tests, including explicit mocked
   owner inputs. Link concrete orphan/policy fail-closed, replay/snapshot/optionality,
   transaction rollback and both-order race examples. Test fixtures are approved test
   facts, not production policy seeds; passing empty fixtures does not prove populated
   deployments have approved mappings. Preserve all applied migrations/history.
6. API_TESTING.md distinguishes standalone MockMvc/controller tests, security/context
   and permission tests, full application startup and runtime contract generation.
   Record current compatibility-base resolver/checker, representative security
   assertions and artifact provenance, plus limitations of its supported comparison.
   Canonical OpenAPI and older API/error documents retain their own applicability;
   no contract regeneration, endpoint change or universal error-envelope claim.
7. REQUIREMENTS_TRACEABILITY.md gives a source-backed evidence matrix for completed
   platform gate categories and permanent module semantics. Include all 57 closed
   HMR-050..106 obligations with their HMSR/subject, canonical decision/module,
   exact test source, relevant migration/contract evidence and historical CI scope
   where actually established. Reference the 123-subject semantic catalogue rather
   than duplicate it as a new execution register. Verify uniqueness/contiguity;
   review disposition, HMR completion, test presence and actual execution remain
   different facts. Explicitly mark missing/partial/runtime-unverified evidence.
8. Cover all 24 implemented module roots by actual evidence navigation; link existing
   module/domain/data/database/API/security/operations sources without editing them.
   Preserve the closed 57-row HMR register and all unrelated HPR statuses. Do not
   infer exhaustive branch/endpoint/performance/load/OT safety coverage, AI/solver
   execution, imported production acceptance or mature physical backup coverage.
9. Update doc/README.md and DOCUMENT_REGISTER.md to register all six testing documents
   with exact source applicability. Existing docs/** and canonical historical evidence
   stay byte-preserved. No code, test, configuration, POM, migration, workflow, dataset,
   release/tag or extra testing artifact is admitted by this documentation task.
10. Record owner acceptance, actual validation and completion in both canonical
    execution memories after transfer. Complete only HPR-P2-011. Documentation drift
    enforcement belongs to HPR-P2-012, final closure to HPR-P2-013; neither is executed
    now. Unknown coverage/enforcement alone is a documented gap, not a new runtime
    requirement or a reason to manufacture validation evidence.

### Exhaustive future implementation write scope — ten paths

Create only:

- `doc/testing/README.md`
- `doc/testing/TEST_STRATEGY.md`
- `doc/testing/ARCHITECTURE_TESTING.md`
- `doc/testing/DATABASE_TESTING.md`
- `doc/testing/API_TESTING.md`
- `doc/testing/REQUIREMENTS_TRACEABILITY.md`

Update only:

- `doc/README.md`
- `doc/governance/DOCUMENT_REGISTER.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`

### Admitted implementation validation and publication

Recheck current main, exact-head documentation CI and the green executable baseline
before implementation. Recover actual POM/workflow/test profile, source inventory,
architecture/owner/HTTP/database examples, compatibility logic and prior P1 applicability.
Verify six-document metadata, 24-module navigation and all 57 HMR traceability rows;
resolve all touched-set relative links/anchors and cited source paths. Do not equate
source counts or a job success with per-class execution/no skips without reports.

Validate canonical UTF-8/nonempty/conflict checks (88 currently; six new files make
94), git diff --check, exhaustive ten-path scope, untouched legacy/source/configuration
and published migration preservation. HMR rows and unrelated HPR statuses remain
unchanged; P2 rows retain seven columns; project version stays 0.6.0-SNAPSHOT.
Documentation-only scope requires no Maven/runtime rerun. If a runtime check is
actually attempted, record its real results and prerequisites instead of importing
old local limitations. No deployment/import/physical campaign is authorized here.

Use `docs(testing): establish verification documentation`, one commit, direct main
publication once with a fresh expected-head lease. Verify immutable parent/tree/blobs
and all other files unchanged. Confirm Documentation Validation starts, then STOP
without waiting for completion. Production CI ignores this docs-only scope. A later
Next selects HPR-P2-012 preflight after this implementation's exact-head documentation
result; failure requires repair of the responsible scope first.

### This preflight's supporting execution

Registered supporting message: `docs(testing): record verification documentation preflight`.
Write ONLY `doc/roadmap/ULTIMATE_ROADMAP.md` and
`doc/model-remediation/RECONCILIATION.md`; no doc/testing/ document is created now.
Check identical envelope text, ten unique future paths, preserved HMR/HPR rows, all
88 canonical Markdown files, git diff --check and exact two-file scope. Publish one
supporting commit directly to main using expected head
`50fd7f9cb97343a6495c2dff5bf54175145bba39`; verify immutable tree/blobs and every
other tracked file unchanged. Confirm Documentation Validation starts and STOP.
HPR-P2-011 remains PENDING until accepted implementation actually completes.

### Actual preflight validation

`python3 /workspace/scratch/4115643ef669/p2-011-preflight-validate.py` PASSED:
identical preflight envelopes in both memories; exactly two modified files; ten unique
future paths with six new documents; all 88 canonical Markdown files UTF-8/nonempty
and free of conflict markers; cited test classes/helpers resolve; all HMR/HPR table
rows unchanged and P2 row shape preserved. `git diff --check` PASSED. No testing set,
Maven/runtime check, source/test/configuration change, migration or operational
execution was performed. Published-tree validation additionally verifies that every
other tracked blob is byte-identical to the parent. The registered implementation
remains PENDING and requires the subsequent acceptance described above.

## HPR-P2-011 accepted execution — 2026-10-09

The owner's Next after preflight `35d9d949aa773a4d754c22d00181330f579f752b` accepts its full ten-part design
and ten-path scope. Main is unchanged at that parent, tree
`8ee453938d3c20565fee270c0938bed6e5fdfd9c`; Documentation Validation #121
(run 37897171261) PASSED. Production CI #604 (37841205677) remains PASSED on
`617c2eec812e3a5734957ee9fa0360f6f5613032`. Execute only the registered
`docs(testing): establish verification documentation`; no runtime/test/CI changes,
per-class execution claims, physical campaign or successor task are selected.

## HPR-P2-011 completed verification documentation — 2026-10-09

Accepted ten-part design implemented within the exhaustive ten-path scope, exact
commit `docs(testing): establish verification documentation`. Verified parent
`35d9d949aa773a4d754c22d00181330f579f752b`, tree
`8ee453938d3c20565fee270c0938bed6e5fdfd9c`; parent Documentation Validation #121
(run 37897171261) PASSED. Production CI #604 (37841205677) PASSED on executable
baseline `617c2eec812e3a5734957ee9fa0360f6f5613032`, tree
`e450c699544f99e96f2b447fc04215f7f4c5f344`. These prior results were rechecked;
no Maven/runtime or per-class report/skip inspection was performed locally.

Created exactly six documents in doc/testing/: README.md, TEST_STRATEGY.md,
ARCHITECTURE_TESTING.md, DATABASE_TESTING.md, API_TESTING.md and
REQUIREMENTS_TRACEABILITY.md. Updated only doc/README.md,
doc/governance/DOCUMENT_REGISTER.md, doc/roadmap/ULTIMATE_ROADMAP.md and
doc/model-remediation/RECONCILIATION.md. Canonical navigation/register now records
CURRENT testing documentation with the verified source parent and inherited owner
metadata. Only HPR-P2-011 is completed; its registry row and immediate next execution
are updated. All legacy files and untouched semantic/API/database/data/security/
operations documents keep their own applicability and byte-preserved evidence.

### Actual implementation and limits

- Strategy documents source-backed focused, architecture/contract, database, full-test
  and clean-verify commands, prerequisites and actual test-profile paths. Inventory
  records 324 Java files, 323 test-named files, one helper and 47 Docker-optional
  Testcontainers classes, not executed case counts or coverage percentages.
- Architecture identifies actual ArchUnit/source/reflection/mapper assertions and
  exact exported owner boundaries; the transitional dependency map is currently
  empty. Static rules and contract examples do not imply universal runtime semantics.
- Database distinguishes full Flyway/context from selected SQL and real Spring/JPA
  fixture paths, mocked owner inputs, fail-closed policy/orphan behavior, historical
  replay, transaction rollback and controlled race orderings. Existing migration
  history and production mapping admission are unchanged; fixtures do not approve
  populated deployments or repeat HA/DR/physical recovery evidence.
- API distinguishes standalone MockMvc/mocks, direct OIDC metadata, permission/filter
  fixtures and complete context. Runtime OpenAPI generation/security checks, bounded
  compatibility and exact-PR/successful-production-ancestor base logic are documented.
  Canonical OpenAPI retains older generation provenance and is not regenerated.
- Requirements traceability links all 24 actual module roots, the 57 unique contiguous
  HMR-050..106 identities/HMSR subjects and exact semantic tests/migrations/contracts.
  The 123-subject permanent catalogue remains authority; this is evidence navigation,
  not a second execution register. Cumulative prior CI #604 applies as historical
  scope; uninspected per-class executed/skipped results are explicitly unestablished.

### Actual local validation

`python3 /workspace/scratch/4115643ef669/p2-011-validate.py` PASSED: exactly ten
allowed paths with six new documents; all 94 canonical Markdown files UTF-8/nonempty
and free of conflict markers; 390 touched-set relative links/anchors resolve; exactly
24 module rows match current roots; 57 traceability rows match permanent catalogue
identities/anchors and dedicated test sources; static 324/323/47 source counts match;
139 migration versions remain unique. Required six-document metadata is present.
HMR register rows and unrelated HPR rows are preserved; P2 rows retain seven columns;
project version stays 0.6.0-SNAPSHOT. `git diff --check` PASSED. Exact published-tree
checks require every other tracked blob unchanged. No runtime/schema reset/import,
deployment, physical campaign, test/workflow/POM/configuration change or release/tag.

Complete endpoint/branch/performance/OT safety coverage, universal multi-owner
end-to-end validation, current deployed-data/import acceptance and new physical
survivability evidence remain NOT ESTABLISHED by this task. Prior P1 evidence keeps
its deployed SHA, 82-migration scope and repository/operator distinctions. HPR-P2-012
and HPR-P2-013 remain PENDING, P3 DEFERRED and P2 OPEN. Publish one exact-message
commit to main with the expected-head lease; verify immutable commit/tree/blobs,
confirm Documentation Validation starts and STOP without waiting. Production CI
ignores this docs-only scope. A later Next selects HPR-P2-012 preflight after the
exact-head documentation result is checked; failure requires responsible-scope repair.

## HPR-P2-012 canonical-validation preflight — 2026-10-09

### Selection and verified baseline

The owner's Next after HPR-P2-011 selects this preflight only. Current main is
unchanged at `6cc9974b54e9d5c82334a62214a76f89519e7aef`, tree
`e2d1696dc2a36da65afbd332964505ad0b1813da`. Documentation Validation #122
(run 37898624297) PASSED. Production CI #604 (37841205677) PASSED at executable
baseline `617c2eec812e3a5734957ee9fa0360f6f5613032`, tree
`e450c699544f99e96f2b447fc04215f7f4c5f344`. Those existing results were checked;
no fresh Maven/runtime or physical campaign was performed for this preflight.

HPR-P2-012 stays PENDING. A subsequent Next after this published proposal accepts
its full twelve-part design and exhaustive 21-path implementation scope, exact commit
`ci(docs): validate canonical documentation`. HPR-P2-013 stays PENDING, P3 DEFERRED
and P2 OPEN; this preflight creates no validator, workflow change or snapshot refresh.

### Current validation and concrete drift recovered

- `.github/workflows/docs.yml` currently checks doc/ Markdown UTF-8/nonempty/conflict
  markers only. It triggers on doc/** and Markdown, not validation-script/manifest
  changes. Production CI ignores doc/**, docs/** and Markdown but runs on workflow/
  script changes. Its full checkout permits Git ancestry/provenance checks.
- A read-only audit of all 94 canonical Markdown files resolved 3,194 current inline
  relative Markdown links/anchors with zero missing targets/anchors. That local audit
  is not yet a maintained CI checker, semantic approval or external-link availability
  evidence. Ordinary CURRENT/HISTORICAL statuses coexist with bold formatting,
  eleven preserved P1 operating/status phrases and roadmap ACTIVE control metadata.
  Historical P1 descriptions must not be rewritten to satisfy a new parser.
- The canonical snapshot is sorted compact JSON plus newline, valid as the existing
  YAML 1.2 JSON subset. It records generator
  `6822f3ce79b305e1c22f48ff20ef0b0bd2c7f135`, 240 paths, 259 operations and
  227 schemas. Its file SHA-256 is
  `97f601447dc5291afe9bc1775c8ea30b04eb1d2e412e8a3a4e04ce28f03b50d8`.
  This checksum is a file digest, not the ZIP artifact digest or Git blob SHA.
- Retrieved exact successful CI #604 OpenAPI artifact ID 11577329466,
  name hidra-api-openapi-617c2eec812e3a5734957ee9fa0360f6f5613032. The downloaded
  ZIP SHA-256 was independently matched to GitHub metadata:
  `2ff35c384c54b2d49e50aa2a9e646a67c62427e8afcc8db92e17e5368a91b334`.
  ZIP contains exactly hidra-api.json; source-SHA provenance equals the executable
  baseline. Initial direct-download HTTP 403 was resolved with an ordinary HTTP
  client User-Agent; artifact retrieval/digest validation ultimately succeeded.
- The retained generated JSON SHA-256 is
  `1ee03ad711b79c7ef42767a6aa3c685f4857800f09f03002deeac49e4e2d23d4`.
  Deterministic compact conversion of that exact object has SHA-256
  `ba9511455ebabb51c704ef510d08f4fdc766f29d8def0a34ef7a8f1acf86d9fd`.
  It has 244 paths, 263 operations and 231 schemas. Ignoring only source-SHA
  provenance, it differs from the canonical snapshot in paths/components. Four paths
  are added: OIDC evaluate and Risk assessment approve/score/submit; no paths removed
  or existing path objects changed. Four schemas are added and one existing schema
  differs. These are generated evidence differences, not hand-authored API changes.
- Actual command `python3 .github/scripts/openapi_compatibility.py
  doc/api/openapi.yaml /workspace/scratch/4115643ef669/hidra-openapi-ci604.json`
  PASSED: no supported breaking changes detected. That bounded compatibility result
  does not imply equality or complete OpenAPI/JSON Schema compatibility coverage.
  Current API governance explicitly treats executable/snapshot divergence as a
  defect requiring generated reconciliation, not silently accepted historical drift.

### Accepted-on-Next proposed design — twelve parts

1. Introduce standard-library Python canonical documentation and OpenAPI validators,
   each with meaningful positive/negative unittest fixtures. Add one reviewed JSON
   validation manifest and a canonical governance validation guide. These files
   implement bounded structural/governance checks, not business approval, physical
   survivability, arbitrary semantic coverage or a new legal/operational standard.
2. Preserve existing UTF-8/nonempty/conflict checks across all canonical Markdown.
   Resolve inline/reference-style local Markdown links and fragments, including
   duplicate GitHub-style heading anchors, formatting, percent encoding and explicit
   IDs where present. Ignore fenced examples and external/mailto links without
   networking; reject missing/case-mismatched targets and repository escapes.
   Code examples/artifact paths are not indiscriminately treated as hyperlinks.
3. Validate declared document statuses/metadata via the containing-index inheritance
   allowed by DOCUMENTATION_STANDARD.md. Handle bold status formatting; preserve
   HISTORICAL evidence and explicitly admit exact eleven legacy P1 status phrases
   with path-specific rationale, plus the roadmap ACTIVE control format. Unknown
   statuses must fail; never globally whitelist arbitrary text, scan historical
   appended execution paragraphs as current status or rewrite P1 evidence.
4. The reviewed manifest declares the canonical document inventory, required domain/
   index/register entry points, 24 implemented module roots/documents and their
   navigation obligations. Compare actual files/source module roots/index targets,
   reject missing/duplicate/unregistered current module documents and inconsistent
   CURRENT/register/index claims. Treat security/operations directories without a
   README according to their actual register/navigation, not fabricated indexes.
   Inventory updates require deliberate manifest/document review, not automatic
   acceptance of new files or broad permanent exemptions.
5. Check the primary roadmap P2 registry for unique codes, seven-column shape, allowed
   task statuses and coherent selected-next/closure claims, together with canonical
   register/index readiness. Do not freeze all historical prose or clone execution
   statuses into another register. Preserve unrelated HMR/HPR rows; this task completes
   only HPR-P2-012 and leaves HPR-P2-013 as the next preflight. Closure consistency
   checks must permit a later deliberately admitted HPR-P2-013 completion.
6. Recover the exact verified CI #604 generated object and refresh doc/api/openapi.yaml
   solely by sorted compact JSON conversion plus newline. Record generator/run/
   artifact/digest provenance and update API index/overview counts to 244/263/231.
   Preserve old HPR-P2-005 provenance as historical generation evidence. Do not
   hand-edit endpoint/schema/security content, label this docs/CI commit as the
   generator, or silently retain the known 240/259/227 snapshot mismatch.
7. OpenAPI validation rejects malformed/duplicate-key/nonfinite JSON, invalid source
   SHA/provenance, unsupported snapshot structure and unresolved local references.
   Enforce exact deterministic snapshot serialization, expected generated-source
   identity, reviewed manifest file digest/counts and representative bearer/public/
   protected/OIDC security assertions. Clearly limit this to the repository's JSON
   snapshot subset and supported structural checks, not full OpenAPI certification.
8. In production CI, compare the freshly generated object with the committed canonical
   object after removing ONLY x-hidra-ci-source-sha for semantic equality; validate
   each actual source SHA separately against its expected generation context. Preserve
   every other field/list/security/description. Reordered object keys are irrelevant;
   arrays and all semantic values remain significant. Genuine difference fails closed
   and requires an artifact-derived, reviewed snapshot/provenance refresh, never an
   ignore list or compatibility-as-equality shortcut. Retain existing compatibility
   base resolver, its tests, supported compatibility check and artifact upload.
9. Wire validators and their tests into documentation CI and production CI. Broaden
   documentation triggers to the exact validation scripts/tests/manifest and workflow
   paths; retain normal doc/Markdown triggers, read-only permissions and concurrency.
   Documentation CI stays offline/no Maven for structural/snapshot checks. Production
   CI additionally verifies fresh runtime snapshot equality after generation, retains
   Java 21/PostgreSQL/full clean verify and the existing HA/release/backup/observability/
   maintenance evidence gates. No release.yml or deployment changes are admitted.
10. Document current control coverage, commands, manifest maintenance, historical
    status handling and generated-only snapshot refresh in DOCUMENTATION_VALIDATION.md.
    Update root/governance/API navigation and only the four admitted testing documents'
    affected CI/snapshot claims. Preserve their HPR-P2-011 test/traceability identities
    and prior execution limits; change governance applicability explicitly rather
    than presenting refreshed CI as new per-class/physical evidence.
11. Run both standard-library test suites and actual full-repository validators,
    including negative cases for paths/anchors/case/escapes/status/index/module/registry
    drift and OpenAPI source/refs/security/serialization/real semantic differences.
    Validate old-to-refreshed supported compatibility and exact object/digest fidelity.
    Assess current Java/Maven/dependency/Docker prerequisites and attempt the admitted
    clean verify; record real results/skips or exact environmental limitations, not
    earlier assumed DNS/dependency failures. Do not turn a pre-compilation block into
    a test failure or claim per-class no skips without actual report evidence.
12. Record acceptance, exact checks and only HPR-P2-012 completion in canonical memory.
    Preserve legacy docs/**, all runtime Java/tests/POM/configuration, migrations and
    other canonical evidence. Publish one exact-message commit to main once using a
    fresh expected-head lease; verify immutable parent/tree/blobs and all other files
    unchanged. This implementation changes workflow/scripts, so BOTH documentation
    and production CI must start. Confirm startup and STOP, never wait for completion
    or execute HPR-P2-013/P3 automatically. A failure requires responsible-scope repair.

### Exhaustive future implementation write scope — 21 paths

Create only:

- `.github/scripts/validate_docs.py`
- `.github/scripts/test_validate_docs.py`
- `.github/scripts/validate_openapi_snapshot.py`
- `.github/scripts/test_validate_openapi_snapshot.py`
- `.github/documentation-validation.json`
- `doc/governance/DOCUMENTATION_VALIDATION.md`

Update only:

- `.github/workflows/docs.yml`
- `.github/workflows/ci.yml`
- `doc/api/openapi.yaml`
- `doc/api/README.md`
- `doc/api/API_OVERVIEW.md`
- `doc/api/OPENAPI_GOVERNANCE.md`
- `doc/README.md`
- `doc/governance/README.md`
- `doc/governance/DOCUMENT_REGISTER.md`
- `doc/testing/README.md`
- `doc/testing/TEST_STRATEGY.md`
- `doc/testing/API_TESTING.md`
- `doc/testing/REQUIREMENTS_TRACEABILITY.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`

No production Java/test/resource/POM/migration, release workflow, dependency, source
import, tag/release or physical campaign. Existing compatibility/resolver scripts
are preserved. Only the known generated snapshot and admitted metadata/CI statements
are refreshed; no mass rewrite of historical canonical/legacy documentation.

### Admitted validation and publication

Before implementation, verify fresh main, exact-head documentation CI and successful
executable baseline/artifact provenance. Reacquire the approved artifact if needed;
check ZIP/member/source/digests and no extraction path escape before using its object.
Run `python3 .github/scripts/test_validate_docs.py` and
`python3 .github/scripts/test_validate_openapi_snapshot.py`, then actual full canonical
and OpenAPI validators with their documented CLI. Run supported old/new compatibility
and compare refreshed bytes/digest to the approved generated object. Attempt
`./mvnw -B -q clean verify` with accurate result/environment/report limits. Workflow
wiring/trigger/permission checks must prove both structural and fresh-runtime gates
are invoked; do not weaken existing production verification or disable failures.

Canonical count is currently 94 Markdown documents; the new guide makes 95. Verify
all relative links/anchors, document/index/status inheritance and reviewed legacy
status exceptions, 24 module roots, 57 HMR and 123 subject identities preserved,
primary P2 registry shape/next gate, exhaustive 21-path scope, version
0.6.0-SNAPSHOT, git diff --check and every other tracked blob unchanged. New tests
must exercise actual validation failures, not merely mirror implementation text.
No execution success is fabricated when prerequisites fail. Implementation publication
uses `ci(docs): validate canonical documentation`; confirm both CI workflows start
then STOP. A later Next selects HPR-P2-013 preflight only after both exact-head gates
are checked; its closure is not executed by this task.

### This preflight's supporting execution and actual checks

Registered supporting message: `ci(docs): record canonical validation preflight`.
Write ONLY doc/roadmap/ULTIMATE_ROADMAP.md and
 doc/model-remediation/RECONCILIATION.md. No future implementation file is changed
or created now. Validate identical envelopes, 21 unique future paths (six new/fifteen
existing), all 94 canonical Markdown files, preserved HMR/HPR rows and P2 row shape,
exact two-file scope and git diff --check. Read-only 3,194 inline relative links and
artifact retrieval/digest/object comparison plus supported compatibility checks above
actually ran; snapshot refresh, validators and Maven/runtime execution did not.
Publish one supporting commit directly to main using expected head
`6cc9974b54e9d5c82334a62214a76f89519e7aef`; verify immutable tree/blobs and every
other file unchanged, confirm Documentation Validation starts and STOP. Production
CI ignores this preflight's two-document scope. HPR-P2-012 remains PENDING.

### Actual supporting validation result

`python3 /workspace/scratch/4115643ef669/p2-012-preflight-validate.py` PASSED:
identical envelopes; exactly two modified memories; 21 unique future paths with six
new/fifteen existing; all 94 canonical Markdown files UTF-8/nonempty/conflict-free;
all HMR/HPR rows unchanged and primary P2 row shape preserved. Project version remains
0.6.0-SNAPSHOT. `git diff --check` PASSED. No proposed validator/test/manifest/guide,
workflow or snapshot was created/changed. Exact publication checks additionally
require all other tracked blobs unchanged. Current source/artifact comparison,
3,194-link read-only audit and supported old/generated compatibility results are
recorded above; no Maven/runtime or physical execution is claimed.

## HPR-P2-012 accepted execution — 2026-10-09

The owner's Next accepts the complete twelve-part design and 21-path scope after
preflight `508337351eef03b82e2c6078a7c8523013efd063`, tree
`048cf9a026e60d5be286c5f60c114669df7b70a8`. Main is unchanged; Documentation
Validation #123 (37900777368) PASSED. Production CI #604 remains PASSED at
`617c2eec812e3a5734957ee9fa0360f6f5613032`. Retained artifact ZIP/member/source
and all recorded digests were independently checked again before snapshot refresh.
Exact implementation message: `ci(docs): validate canonical documentation`.
No P2 closure, source/runtime policy changes, release or physical campaign selected.

### HPR-P2-012 actual implementation checks — 2026-10-09

Both maintained standard-library Python suites PASSED: 20 tests covering isolated
positive estates/contracts and actual negative validation failures. The canonical
validator PASSED for 95 documents, 3,215 relative links, 24 modules and 13 primary
P2 rows. Offline snapshot integrity and retained CI #604 generated-object equality
PASSED: 244 paths, 263 operations, 231 schemas. The unchanged compatibility checker
PASSED against the preserved prior snapshot with no supported breaking changes.
Workflow YAML parsed; all prior production steps, their order and job/trigger/service/
permission/concurrency settings were preserved. Both workflows run the new tests and
offline validators; production additionally requires fresh runtime snapshot equality.

Local `./mvnw -B -q clean verify` could not start because the tracked wrapper is
nonexecutable. The actual `bash ./mvnw -B -q clean verify` attempt FAILED before
compilation: uncached Spring Boot parent 4.1.1 could not resolve because
repo.maven.apache.org DNS failed. Only Java 17 is installed; Java 21 is required.
No local Java/test/JPA/runtime success is claimed. Existing CI #604 remains historical
generation evidence; new exact-head Java 21/PostgreSQL production CI is required.

Implementation scope is exactly six new and fifteen existing admitted paths.
Project version remains 0.6.0-SNAPSHOT. All executable source/test/resource/POM,
published migrations, legacy and other documentation blobs remain unchanged;
57 HMR register and 123 semantic-review subject identities are preserved.
HPR-P2-012 implementation is recorded complete, with new CI results pending.
HPR-P2-013 remains pending; P2 open, P3 deferred and physical evidence unestablished.
Publication uses one expected-head update from
`508337351eef03b82e2c6078a7c8523013efd063`, with exact message
`ci(docs): validate canonical documentation`. Verify immutable tree/blobs and confirm
both workflows start, then STOP without waiting for completion. A later Next checks
both exact-head results before HPR-P2-013 preflight; it does not execute closure now.

## HPR-P2-013 canonical-governance closure preflight — 2026-10-09

### Selection and verified baseline

The owner's instruction selects PREFLIGHT ONLY. Main was rechecked unchanged at
`e4dba168c9e612a5fd49d50b155fa3b2d8d64e40`, tree
`1cb256871d8afe77c02ab7b98b58ef3a80f303ce`. GitHub independently confirms both
exact-head runs completed successfully: Documentation Validation #124 (37909982710)
and production CI #605 (37909982823). Their jobs/steps passed, including Java 21
clean verify, PostgreSQL verification, fresh OpenAPI snapshot equality and supported
compatibility. This is CI evidence, not a new local runtime or physical campaign.
AGENTS.md and both canonical execution memories were read before mutation.

HPR-P2-001..012 are COMPLETED. The primary reconciliation has exactly 57 COMPLETED
HMR-050..106 rows, including HMR-080. The permanent catalogue retains 123 subject
rows. HPR-P2-013 stays PENDING, P2 OPEN and every P3 code DEFERRED. Project version
remains 0.6.0-SNAPSHOT. This supporting publication does not execute closure.

### Actual read-only review and concrete closure defect

- Both maintained Python test suites passed: ten tests each, twenty total.
  `python3 .github/scripts/validate_docs.py` passed for 95 canonical Markdown
  documents, 3,215 local links, 24 modules and 13 primary P2 rows.
  `python3 .github/scripts/validate_openapi_snapshot.py` passed offline integrity:
  244 paths, 263 operations and 231 schemas. External-link uptime, complete narrative
  truth and comprehensive OpenAPI certification are outside these checks.
- Current production source/test/resources/POM and ops/production blobs are unchanged
  from the HPR-P2-009 semantic parent `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`
  and CI #604 source `617c2eec812e3a5734957ee9fa0360f6f5613032`.
  The 24 module inventories and domain evidence therefore retain their exact source
  applicability; CI #605 adds current execution evidence without inventing per-class
  report/skip inspection. Canonical legacy review/remediation links resolve and their
  subordinate authority is explicit in SEMANTIC_DECISIONS.md and DOCUMENT_REGISTER.md.
- A real current-state database-documentation defect remains. The five HPR-P2-006
  database documents describe 82 migrations and 469 entities, while direct current
  filesystem/annotation enumeration finds 139 unique versioned SQL migrations and
  470 module @Entity classes across 24 modules. Risk has 25 entities; the old dictionary
  lists 24 and omits RiskAssessmentScoringJpaEntity. Its global migration inventory
  omits 57 current files; current tail is
  `V20261008_026__hmr_080_planning_nomination_integrity.sql`.
  Per-module filename lists also need regeneration. Filename matching is descriptive,
  not proof of exclusive table/semantic ownership.
- Structural CI intentionally does not certify these database inventory claims; its
  passing result cannot waive this defect. HPR-P2-013 must reconcile it before closure.
  The original P1 82-migration deployed/recovery scope remains historical evidence and
  must not be changed to 139 or presented as acceptance of the current deployed schema.
- Current root/register/validation guide still say HPR-P2-012 exact-head results follow
  publication. Preserve those dated records as history and add the now-verified #124/
  #605 result separately. Supersession must be explicit; do not mass-rewrite prior
  execution paragraphs or alter P1 evidence to make current summaries look uniform.

### Proposed accepted-on-Next bounded design

1. Recheck fresh main and this preflight's exact-head documentation result before
   implementation. Revalidate #124/#605 identities, primary P2 prerequisites, source
   inventory and current contracts. Changed source or additional narrative defects
   require a revised bounded proposal before expanding the write scope.
2. Refresh only the five current database documents from source: enumerate all 139
   unique forward SQL filenames in version order, all 470 @Entity classes by owning
   module, corrected module totals and current tail. Regenerate the complete dictionary
   and descriptive filename lists; verify exact set equality against source, duplicates
   and RiskAssessmentScoringJpaEntity. Record the actual verified source parent/date.
   Preserve HPR-P2-006/P1 generation and 82-migration physical provenance explicitly
   as historical. Do not infer physical table/column/type/constraint details from class
   names, execute migrations, repair data or claim deployment acceptance.
3. Add a source-backed closure review to the root/index/register and API/domain/module
   indexes: all 24 implemented modules, canonical navigation/status inheritance,
   architecture/domain/API/database/data/testing readiness and legacy supersession.
   Keep the permanent 123-subject catalogue, all 57 completion identities and retained
   legacy estate unchanged. No invented human owner or business approval is admitted.
4. Update the validation guide/register with verified HPR-P2-012 CI evidence and exact
   bounds. Keep the canonical API object and generation provenance unchanged: CI #604
   generated the committed object; CI #605 freshly checked semantic equality. Strict
   equality excludes only x-hidra-ci-source-sha and does not waive compatibility.
5. Record only HPR-P2-013 implementation completion and a P2 closure-verification gate
   in the primary roadmap and reconciliation. Replace immediate-next execution with
   the pending exact-head closure verification; do not select a P3 task. Final P2
   VERIFIED/CLOSED disposition requires both successful workflows on the resulting
   closure SHA, checked on a later owner notification/Next. Do not pre-assert that
   future results passed. Preserve P0/P1 disposition and their original applicability.
6. Keep unknown business retention/policy mappings, per-class no-skips coverage,
   production-data/import acceptance, comprehensive endpoint/performance/OT coverage,
   runtime inference/actuation and fresh physical survivability explicitly unestablished
   by P2 closure. TimescaleDB, PostGIS and unimplemented industrial/AI extensions stay
   DEFERRED/TARGET. Existing P1 verification is not reopened absent regression evidence.
7. Run both maintained suites, full canonical and offline snapshot validators,
   exact database set/count checks, 24-module/source linkage, 123-subject and 57-HMR
   identity preservation, P2/P3 registry/gate checks, all other tracked-blob comparison,
   version check and git diff --check. Attempt admitted clean verify with accurate
   environmental limits; no local execution result substitutes for exact-head CI.
   No new maintained validator or test is needed for this bounded documentation refresh.
8. Publish one exact registered implementation commit,
   `docs(roadmap): close P2 canonical governance`, using fresh expected-head lease.
   Verify immutable parent/tree/blobs and exactly the thirteen admitted paths. Confirm
   Documentation Validation starts. Full production CI is ALSO mandatory for closure:
   docs-only push is ignored by ci.yml, so use its existing workflow_dispatch on this
   exact main SHA. The current GitHub connector exposes no dispatch action; if that
   remains so, the owner must run HidraAPI CI through GitHub Actions. Do not change
   workflow triggers or use a dummy executable change to force CI. Until dispatch is
   confirmed and both exact-head results subsequently pass, closure verification stays
   pending. Stop after startup observation; never wait for completion or execute P3.

### Exhaustive future implementation write scope — thirteen existing paths

Update only:

- `doc/README.md`
- `doc/governance/DOCUMENT_REGISTER.md`
- `doc/governance/DOCUMENTATION_VALIDATION.md`
- `doc/database/README.md`
- `doc/database/DATABASE_ARCHITECTURE.md`
- `doc/database/SCHEMA_OWNERSHIP.md`
- `doc/database/FLYWAY_POLICY.md`
- `doc/database/DATA_DICTIONARY.md`
- `doc/api/README.md`
- `doc/domain/README.md`
- `doc/modules/README.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`

Create no repository files. Preserve all individual module docs, permanent semantic
catalogue, API snapshot, validation manifest/scripts/tests/workflows, historical P1
database/operations evidence, all docs/**, runtime source/tests/resources/POM and
migrations. No tag, release, version change, deployment, import, policy approval,
physical campaign or P3 execution. A defect beyond this allowlist requires re-preflight.

### Supporting preflight publication and actual validation

Register supporting exact message:
`docs(roadmap): record P2 canonical governance closure preflight`.
This preflight appends the identical envelope ONLY to
doc/roadmap/ULTIMATE_ROADMAP.md and doc/model-remediation/RECONCILIATION.md.
No future implementation file is refreshed now, and all primary HPR/HMR statuses
remain unchanged. Validate exact two-file scope, identical envelopes, thirteen unique
existing future paths, unchanged registry rows/catalogue/source/migrations/version,
both Python suites, actual canonical/snapshot validators and git diff --check.
Publish one supporting commit to main with expected head
`e4dba168c9e612a5fd49d50b155fa3b2d8d64e40`, verify immutable parent/tree/blobs and all
other files unchanged, confirm Documentation Validation starts, then STOP. Production
CI ignores this preflight's two-document scope and is not required for the supporting
proposal. A later Next accepts only the bounded HPR-P2-013 design after checking this
preflight's documentation result; it does not select P3. No Maven/runtime or physical
execution was performed in this preflight.

Actual supporting checks PASSED on 2026-10-09: twenty maintained Python tests;
canonical validation (95 documents, 3,215 links, 24 modules, 13 P2 rows); offline
OpenAPI integrity (244/263/231); identical appended envelopes; exact two-file tracked
scope; thirteen unique existing future paths; all HPR/HMR rows unchanged; 123 catalogue
subjects and 57 COMPLETED reconciliation rows preserved; version 0.6.0-SNAPSHOT;
git diff --check. Database/source inventory and unchanged executable-blob comparisons
above actually ran. No local Maven/runtime/physical result is claimed.

## HPR-P2-013 closure scope re-preflight — 2026-10-09

### Accepted selection, verified gates and responsible-scope stop

The owner's Next selected the previously published bounded HPR-P2-013 design.
GitHub main is unchanged at `f9fe69e5e4e24c928d61b2b73da7e59882a7b558`, tree
`1fd98b130c5a597f36c9241e927081893c1eb521`. Exact preflight Documentation Validation
#125 (37913003387) PASSED. HPR-P2-012 Documentation Validation #124 (37909982710)
and production CI #605 (37909982823) were independently rechecked PASSED at
`e4dba168c9e612a5fd49d50b155fa3b2d8d64e40`. AGENTS.md, the current roadmap and
reconciliation control this work.

The accepted preflight explicitly requires: "A defect beyond this allowlist requires
re-preflight." Current source review found a second closure defect outside its
thirteen-path scope. Therefore no accepted implementation path has been refreshed,
no HPR-P2-013 completion is recorded and closure execution stops before mutation.
This supporting revision updates only the two execution memories. It is not the
registered `docs(roadmap): close P2 canonical governance` implementation commit.

### Additional current-source evidence

`doc/architecture/CROSS_MODULE_CONTRACTS.md` describes its old HPR-P2-002 surface as
CURRENT and lists 22 contract names. Actual current source contains 70 Java files
under application/contract, excluding package-info, in 63 unique packages. The source
package set exactly equals EXPORTED_CROSS_MODULE_PACKAGE_PREFIXES in
`src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java`. Forty-eight current file
names are absent from the old architecture table. Those 70 files are an inventory
unit, not a claim that all files are interfaces or independent operational contracts.

`doc/architecture/BOUNDED_CONTEXT_MAP.md` repeats the old relationship surface while
claiming it derives from current source/allowlist. Its module-root list is still correct,
but contract relationships need refresh. Architecture README applicability must record
the new verification without changing historical HPR-P2-002 provenance.

Three exported package suffixes are neutral extension surfaces, not module consumers:
documents.application.contract.target (DocumentsOwnedTargetLookup),
workflow.application.contract.target (WorkflowOwnedTargetLookup), and
risk.application.contract.evidence (RiskOwnedEvidenceLookup). An updated table must
distinguish these from actual named module-consumer packages and must not invent
implemented modules named target or evidence. Source presence/allowlisting is not
proof of provider availability or all owner interactions in a deployed system.

The previously recovered database defect is unchanged: 139 unique forward migrations,
470 @Entity classes, Risk 25 versus the old 24, and 57 missing migration filenames.
Database refresh remains necessary. Original P1 deployed/recovery scope stays 82 and
must not be relabelled as current-schema physical acceptance.

### Revised accepted-on-Next proposal

The complete eight-part bounded design in the immediately preceding HPR-P2-013
preflight remains proposed, with these explicit amendments:

1. Add exactly three existing architecture files to the implementation allowlist:
   README.md, CROSS_MODULE_CONTRACTS.md and BOUNDED_CONTEXT_MAP.md under
   doc/architecture/. All other previously admitted thirteen paths remain unchanged.
2. Regenerate the complete architecture contract file/package inventory directly from
   current Java package declarations and source filenames; verify 70 unique files and
   exact 63-package agreement with architecture enforcement. Include direct evidence
   links, owner, named consumer or neutral extension role; retain convention/boundary
   rules without misclassifying target/evidence packages. Refresh bounded-context
   relationships from the same verified package set, keeping the 24-module list intact.
   Record source parent/date and preserve original architecture-baseline provenance.
   No Java, architecture enforcement, export permission or runtime behavior changes.
3. Verify refreshed architecture table/source set equality, duplicate/missing entries,
   exact owner/consumer/neutral relationships and current database dictionary equality.
   Retain all prior canonical/snapshot tests, source/preservation/version checks and
   evidence limits. New source defects beyond these sixteen paths require re-preflight.
4. Implementation still uses exactly `docs(roadmap): close P2 canonical governance`.
   HPR-P2-013 implementation completion is distinct from P2 final VERIFIED/CLOSED
   disposition, which remains pending until both documentation and full production CI
   succeed on that resulting SHA. The docs-only production workflow requires existing
   workflow_dispatch; no dispatch tool is currently exposed, so owner GitHub Actions
   dispatch remains necessary unless an authorized supported capability becomes
   available. Do not alter workflows or add a dummy executable change. Confirm startup
   and STOP; no waiting or P3 execution is authorized.

### Exhaustive revised future implementation scope — sixteen existing paths

Update only:

- `doc/README.md`
- `doc/governance/DOCUMENT_REGISTER.md`
- `doc/governance/DOCUMENTATION_VALIDATION.md`
- `doc/database/README.md`
- `doc/database/DATABASE_ARCHITECTURE.md`
- `doc/database/SCHEMA_OWNERSHIP.md`
- `doc/database/FLYWAY_POLICY.md`
- `doc/database/DATA_DICTIONARY.md`
- `doc/api/README.md`
- `doc/domain/README.md`
- `doc/modules/README.md`
- `doc/roadmap/ULTIMATE_ROADMAP.md`
- `doc/model-remediation/RECONCILIATION.md`
- `doc/architecture/README.md`
- `doc/architecture/CROSS_MODULE_CONTRACTS.md`
- `doc/architecture/BOUNDED_CONTEXT_MAP.md`

Create no repository files. All other previously stated preservation/exclusion rules
remain unchanged: individual module docs, 123-subject semantic catalogue, API snapshot,
manifest/scripts/tests/workflows, historical P1 evidence, docs/**, all runtime
source/tests/resources/POM and migrations remain untouched. No policy/business
approval, release/tag/version change, deployment/import or physical campaign.

### Actual checks and supporting publication

Read-only source enumeration and 63-package set comparison above PASSED. Both
maintained Python suites PASSED (twenty tests). Canonical validation PASSED for
95 documents, 3,215 links, 24 modules and 13 P2 rows; offline snapshot validation
PASSED at 244 paths, 263 operations and 231 schemas. Structural success does not
certify the stale architecture/database narrative; it cannot waive either defect.

Actual local `./mvnw -B -q clean verify` could not start: tracked wrapper is not
executable. `bash ./mvnw -B -q clean verify` then failed BEFORE compilation resolving
uncached Spring Boot parent 4.1.1 because repo.maven.apache.org DNS failed. Installed
Java is 17.0.20, while Java 21 is required. No local Java/JPA/runtime test result or
physical campaign is claimed. Prior exact-head production CI #605 remains successful.

Supporting exact message:
`docs(roadmap): revise P2 canonical governance closure preflight`.
Append this identical revised envelope only to the two canonical execution memories;
leave all primary HPR/HMR rows and all other tracked blobs unchanged. Verify sixteen
unique existing future paths, unchanged 57 completed HMR identities/123 subjects,
version 0.6.0-SNAPSHOT, maintained validators and git diff --check. Publish once with
fresh expected-head lease from `f9fe69e5e4e24c928d61b2b73da7e59882a7b558`, verify
immutable parent/tree/blobs, confirm Documentation Validation starts, then STOP.
This two-document supporting revision needs no full production dispatch. A subsequent
Next after its exact-head documentation result accepts the complete revised design
and sixteen-path scope. HPR-P2-013 stays PENDING, P2 OPEN and all P3 tasks DEFERRED.

## HPR-P2-013 accepted implementation — 2026-10-09

The owner's Next accepts the original eight-part proposal plus the revised four-part
amendment and sixteen-path allowlist. Main is unchanged at `00c4fda266b2dfd175cca37ad789dc9462a5af0b`, tree
`3fd8da771d07eb0ce270e74d44b6b057fbb646ee`; exact-head Documentation Validation
#126 PASSED. Only the sixteen admitted existing documentation files are changed.
Exact message: `docs(roadmap): close P2 canonical governance`.

Database dictionary/global and per-module inventories are regenerated from current
source: 139 unique migrations, 470 entities, Risk 25 and current 026 tail. Architecture
inventory is regenerated from 70 Java files in exactly 63 exported packages; context
relationships distinguish module consumers from three neutral owner-lookup extensions.
Root/register/validation/API/domain/module indexes record verified #124/#605 evidence
and current closure limits. Historical architecture/database/P1 provenance is preserved.

All HPR-P2-001..012 and 57 HMR completions retain their identities; only HPR-P2-013
implementation is completed. P2 remains OPEN pending both successful exact-head CI
workflows on this implementation SHA. P3 stays DEFERRED; no next P3 is selected.
Publication verification must confirm immutable parent/tree/blobs and exact scope.
Documentation CI starts automatically; full production CI must be workflow-dispatched
on the exact resulting SHA. Current connector exposes no dispatch tool, so owner
GitHub Actions dispatch is necessary. Stop after startup observation, without waiting.

### HPR-P2-013 actual implementation validation

Both maintained Python suites PASSED: twenty tests. Full canonical validation PASSED
for 95 documents, 3,289 relative links, 24 modules and 13 primary P2 rows. Offline
OpenAPI integrity PASSED at 244 paths, 263 operations and 231 schemas; snapshot bytes
are unchanged. Independent source checks PASSED for the complete global/per-module
139-migration and 470-entity dictionary, module ownership totals and 70-file/63-package
architecture tuples/consumer/neutral-extension relationships. Exactly sixteen existing
admitted documentation files changed; all other tracked blobs, original P1 physical
provenance, legacy evidence, 57 HMR identities, 123 semantic subjects and version
0.6.0-SNAPSHOT are unchanged. Only the primary HPR-P2-013 implementation row changed;
final P2 closure remains OPEN pending both exact-head CI gates. git diff --check PASSED.

Actual ./mvnw -B -q clean verify could not start because the tracked wrapper is not
executable. Actual bash ./mvnw -B -q clean verify FAILED before compilation resolving
uncached Spring Boot parent 4.1.1 due to repo.maven.apache.org DNS failure. Installed
Java is 17.0.20; Java 21 is required. No local Java/JPA/runtime success is claimed.
Successful production CI #605 is prior exact-head evidence, not verification of this
future publication. Documentation CI startup and mandatory owner-dispatched full CI
must be reported separately; no future success or final P2 closure is pre-asserted.

## HPR-P2-013 final exact-head verification — 2026-10-09

The owner's "Next it pass" selects final verification, not P3 execution. GitHub main
was independently rechecked unchanged at implementation commit
`22a9b34225242c52fd502e421570af8b46879e4e`, tree
`baf49159b6203cded89666cb5fe3b548e7516bad`.

Both required exact-head gates PASSED:

- Documentation Validation #127, run 37914336195, on the implementation SHA;
- full production CI #606, run 37915669392, on the same implementation SHA.

GitHub reports completed/success for both. Production job and every reported step
succeeded, including Java 21 clean verify, PostgreSQL-backed repository verification,
retained P1 artifact/evidence gates, fresh OpenAPI generation, canonical snapshot
semantic equality and supported backward compatibility. CI success does not assert
per-class no-skips inspection, complete endpoint/performance/OT coverage, current
deployed-data acceptance, owner policy approval or a fresh physical campaign.

Final disposition: HPR-P2-001..013 COMPLETED; P2 VERIFIED/CLOSED at the exact
implementation SHA above. All 57 reconciled HMR implementations remain completed,
including HMR-080; the permanent catalogue retains 123 subjects. Current source
inventories remain 24 modules, 139 migrations, 470 entities and 70 contract Java files
in 63 exported packages. P0/P1 disposition and original physical provenance are
unchanged. Version remains 0.6.0-SNAPSHOT. No release/tag/version/deployment/import.

This result supersedes the prior P2 OPEN/pending-CI statements in dated implementation,
preflight and index records for the verified closure. It does not rewrite their
historical applicability, extend P1's 82-migration physical evidence or promote
unknown business/operational/AI claims. CURRENT status means the documented scope,
not unlimited production acceptance.

All P3 codes remain DEFERRED. There is no automatically selected executable task.
HPR-P3-001 capacity requirements and HPR-P3-005 spatial requirements are separate
future owner selections; neither is executed or approved by this closure record.
TimescaleDB/PostGIS and industrial extensions remain DEFERRED/TARGET until their own
requirements, measurements and architecture decisions authorize implementation.

Supporting exact message: `docs(roadmap): record verified P2 closure`.
Write ONLY doc/roadmap/ULTIMATE_ROADMAP.md and
 doc/model-remediation/RECONCILIATION.md. Preserve all other tracked blobs and
historical paragraphs. This documentation-only evidence record does not create a
new executable closure baseline or require repeating the already successful full
CI #606. The resulting supporting commit needs Documentation Validation startup
and subsequent result verification; a failure requires responsible-scope repair.
Publish once with expected-head lease from
`22a9b34225242c52fd502e421570af8b46879e4e`, verify immutable parent/tree/blobs and
confirm Documentation Validation starts, then STOP without waiting or executing P3.

Actual supporting validation PASSED: full canonical validator (95 documents, 3,289
links, 24 modules, 13 P2 rows), offline OpenAPI integrity (244/263/231), identical
final-verification envelopes, exact two-file scope, all other tracked blobs unchanged,
57 completed HMR rows/123 catalogue subjects/version preservation, unchanged P3
DEFERRED registry and git diff --check. No local runtime/physical campaign was rerun;
new exact-head production evidence is the successful CI #606 described above.

## HPR-P2-013 renewed final exact-head verification — 2026-10-09

The owner's Next selected verification of the two previously required exact-head
closure gates, not a P3 task or a software release. Main was independently checked
at `a8905e32289a583f47b831e0381783e556ae0c8d` (closure metadata commit
`docs(roadmap): close P2 canonical governance`), with tree
`20fd066722ef96dca9a6f345bacb579ba0f52af2`.

Both GitHub Actions runs completed SUCCESS on this exact implementation SHA:

- Documentation Validation #136, run 37932486353, push event;
- HidraAPI CI #609, run 37933120029, workflow_dispatch event.

The production job `Java 21 Maven verification` completed SUCCESS. GitHub reports
successful steps for Java 21 setup, repository verification, canonical validators,
OpenAPI publication and snapshot equality, migrated database dictionary capture
and retention, base-revision OpenAPI generation and backward compatibility,
alongside the retained P1 release/HA/backup/operations evidence gates.
These observed CI results satisfy the final exact-head gates; they do not assert
per-class no-skips inspection, production-data/import acceptance, unknown business
policy approvals, comprehensive endpoint/performance/OT coverage, a new physical
survivability exercise or deployed AI/actuation.

The separately pinned renewed substantive audit verified 12/12 checks at
`7be1c9cb47ed9b6f73a7692d0a328ad870e2b4d4`. HPR-P2-005 and HPR-P2-006
corrections remain completed; all HPR-P2-001..013 are now COMPLETED and Phase P2
is VERIFIED/CLOSED. Preserve all 57 completed HMR identities and 123 semantic
subjects. P0/P1 disposition and historical physical applicability are unchanged.
All P3 tasks remain DEFERRED. No P3, release/tag/version, deployment, data import
or owner-policy action is authorized or executed by this closure record.

This final verification supersedes dated OPEN/pending-CI and the prior 9/12 audit
as CURRENT execution disposition without rewriting historical evidence. The
project version remains `0.6.0-SNAPSHOT`. Only the two canonical execution
memories are refreshed by this evidence record. The successful production CI
#609 remains attached to the exact executable/metadata implementation SHA;
a documentation-only evidence commit requires documentation validation but
does not imply that full production CI reran on the new documentation-only SHA.


## HPR-REL-001 bounded release-alignment preparation — 2026-10-09

The owner's "Do next" selects registration and preparation following the read-only
release preflight; this authorization is distinct from P2 closure. AGENTS.md,
Ultimate Roadmap and reconciliation were re-read at unchanged GitHub main
ac6cbb4b1cd584b0c780dd208543f41ebe326e06, tree
4ed7be83ee5a25dd1ecc63feea053e48f8ccbc0f, before changes. The isolated local
baseline was reconstructed and its complete tree matched that GitHub tree.

The preflight inspected pom.xml, VERSIONS.md, PROJECT_STATE.md, completed phase
records, current main/CI, all returned Git tag refs and GitHub Releases, ci.yml,
release.yml, OpenAPI configuration and maintained validators. The only published
tag/release is v0.5.0 at 492d9916369a58e60c1a437647411e7c5a523990. The existing
0.6.0-SNAPSHOT development line and completed architecture/stabilization milestone
justify preparing 0.6.0. This is release preparation, not a completed formal release.

Registered exact message: `chore(release): prepare 0.6.0 platform milestone`.
Write ONLY pom.xml, VERSIONS.md, PROJECT_STATE.md,
doc/roadmap/ULTIMATE_ROADMAP.md and doc/model-remediation/RECONCILIATION.md.
The POM project version becomes 0.6.0; dependencies and release-plugin settings
remain unchanged. Version history and project state distinguish the candidate,
last formal release, semantic anchor and pending exact-SHA verification.
HPR-REL-001 remains IN PROGRESS pending successful candidate CI.

The semantic milestone anchor a8905e32289a583f47b831e0381783e556ae0c8d has successful
Documentation #136 and production #609. Current parent documentation #137 passed.
Those results are inherited evidence, not verification of the candidate. Publish
one preparation commit from the verified parent with an expected-head lease;
verify immutable parent/tree/changed paths and observe both documentation and full
HidraAPI CI startup. The POM change activates the existing full-CI push trigger.
STOP after startup; wait for owner Next/failure notification before verification.
Required candidate gates include Java 21 clean verify, canonical documentation,
fresh OpenAPI identity/equality and backward compatibility, current migrated
physical dictionary comparison and the existing P1 evidence/operations gates.

P0/P1/P2 disposition, 57 completed HMR identities including HMR-080, 123 permanent
subjects, P3 DEFERRED rows, legacy records and physical evidence applicability are
preserved. OpenAPI stays v1. No source/test/schema/migration/workflow/configuration,
contract/dictionary/owner-policy, data import, physical campaign or deployment
change is selected. No tag or GitHub Release is created; these require separate
explicit authorization under AGENTS.md §19. A future v0.6.0 tag must target the
successfully verified alignment SHA, never the snapshot-version milestone anchor.
Do not advance to 0.7.0-SNAPSHOT until formal release exists or separately directed.


Local preparation validation PASSED: 37 maintained Python tests (10 documentation,
10 OpenAPI, 17 dictionary); canonical validator (95 documents, 5,062 links,
24 modules, 13 P2 rows); offline OpenAPI integrity (244 paths, 263 operations,
231 schemas); P1 closure evidence validator; exact five-file allowlist; unchanged
P0/P1/P2/P3 primary registry rows and 57 completed HMR identities; unchanged
permanent semantic catalogue; only the POM project version changed; all other
tracked blobs unchanged; git diff --check. These are local/static checks.

Actual `bash ./mvnw -B -q clean verify` FAILED before compilation resolving the
uncached Spring Boot parent 4.1.1 because repo.maven.apache.org DNS is unavailable.
Installed Java is 17.0.20; Java 21 is required. No local Java/runtime verification
success is claimed. Full GitHub CI on the future alignment SHA is mandatory;
release readiness and HPR-REL-001 completion remain PENDING.


## HPR-REL-001 exact-candidate verification — 2026-10-09

The owner's Next selects verification and recording of the prepared candidate,
not tag/release publication or deployment. GitHub main was independently checked
unchanged at release-alignment commit 09bf1cbf82c20f19c50ebb1ff9e27047c7f34856,
tree d46f669bda1bf74e2fe35ede3d57aa554d0c1919. AGENTS.md, Ultimate Roadmap and
reconciliation were read from that SHA before mutation. POM is 0.6.0.

Both required exact-candidate runs completed SUCCESS:

- Documentation Validation #138, run 37936829457;
- HidraAPI CI #610, run 37936829442.

Production job 113840861520, Java 21 Maven verification, and every reported step
succeeded. This includes Java 21 clean verify, canonical validators, retained P1
HA/release/backup/observability/database-operations gates, deterministic OpenAPI
publication, fresh-runtime canonical equality, base-revision generation and
backward compatibility, migrated dictionary capture/comparison and artifact retention.
The actual job log records OpenAPI equality (244 paths, 263 operations, 231 schemas)
and dictionary reviewed_dictionary_checked true, unresolved_owners [], 139
migrations, 470 JPA mappings, 482 relations and candidate source SHA. The source
bundle remains 86f6ba5923e63c0cd601f7722ca0282e486d59fc92bb120616710fb3cd1799d4.

GitHub artifact metadata confirms both retained, unexpired exact-SHA artifacts:
OpenAPI ID 11620670294, archive digest
6dc81efc2fb323be97b65de48a259fe985b610c893bd8f92108f41ce2eb6f2da;
database schema ID 11620720084, archive digest
43d17f190289d191571032501b41c19ab904cab7db73a4da9a9d888a06b32fb5.
These are observed GitHub metadata, not locally downloaded/rehashed archives.
Per-class execution/skip reports were not independently inspected. CI success
adds no production-data/import acceptance, unknown policy approval, new physical
exercise, comprehensive performance/OT coverage or autonomous AI claim.

HPR-REL-001 is COMPLETED and the candidate is READY FOR AUTHORIZED PUBLICATION.
The only observed published tag/release remains v0.5.0 on
492d9916369a58e60c1a437647411e7c5a523990. No v0.6.0 tag/release/deployment occurs.
The future tag target remains the verified alignment SHA 09bf1cbf82c20f19c50ebb1ff9e27047c7f34856,
not this supporting evidence-record commit or snapshot-version milestone a8905e3.
POM stays 0.6.0; no 0.7.0-SNAPSHOT transition is authorized. P0/P1/P2 CLOSED,
P3 DEFERRED, 57 HMR completions and 123 subjects remain unchanged. Dated preparation
PENDING statements retain historical scope and are superseded by this result.

Supporting exact message: `docs(release): record verified 0.6.0 candidate`.
Write ONLY VERSIONS.md, PROJECT_STATE.md, doc/roadmap/ULTIMATE_ROADMAP.md and
doc/model-remediation/RECONCILIATION.md. All other tracked blobs remain unchanged.
Validate canonical docs, offline OpenAPI integrity, P1 evidence, exact four-file
scope, candidate/POM identity and preserved phase/HMR/catalogue records. Publish
with expected-head lease from the alignment SHA, verify immutable parent/tree/paths,
observe Documentation Validation startup and STOP. Full CI #610 remains pinned
to the alignment SHA; a docs-only evidence record does not require another full
production run. Explicit publication authorization is the next release prerequisite
under AGENTS.md §19; no executable next task is automatically selected.


## HPR-REL-002 published release record and development transition — 2026-10-09

The owner's Next selects recording the published v0.6.0 release and advancing
main to 0.7.0-SNAPSHOT under AGENTS.md §19. Before changes, GitHub main was checked
unchanged at 045f062f782b0f5240f255853db2e9025e69d631, tree
292b31f10bc0f4b6cb9b664cf79cb5210ca5e185. The local complete tree matched that
remote tree. AGENTS.md, Ultimate Roadmap and reconciliation were re-read, together
with POM, version history and project state. Parent Documentation #139 PASSED.

The actual latest GitHub Release is HidraAPI v0.6.0 / tag v0.6.0, ID 407972853,
published by owner 2026-10-09T14:02:18Z, non-draft and non-prerelease. The tag ref
is lightweight/unsigned and resolves directly to
09bf1cbf82c20f19c50ebb1ff9e27047c7f34856. Full production CI #610 (37936829442)
PASSED on that exact release alignment SHA. The release and verified tag remain
immutable targets of this task; no tag rewrite, replacement, asset modification,
new publication or production deployment is performed.

Registered exact message: `chore(release): start 0.7.0 development`.
Write ONLY pom.xml, VERSIONS.md, PROJECT_STATE.md,
doc/roadmap/ULTIMATE_ROADMAP.md and doc/model-remediation/RECONCILIATION.md.
Only the POM project version changes from 0.6.0 to 0.7.0-SNAPSHOT. Version history
records the actual formal release SHA/date/tag form and separates the current
development transition from the release and semantic milestone anchors. Project
state and canonical execution memories register this selected transition and
pending exact-head verification. HPR-REL-001 remains completed; HPR-REL-002 is
IN PROGRESS until both transition workflows pass.

Validate canonical documentation, offline OpenAPI and P1 evidence; exact five-file
scope and POM-only version difference; preserved P0/P1/P2/P3 registry rows,
57 completed HMR identities, 123 semantic subjects and historical provenance;
all other tracked blobs unchanged; git diff --check. Prior release CI #610 and
parent documentation #139 are inherited evidence, not verification of the new
snapshot. Publish once with expected-head lease, verify parent/tree/blobs and
observe Documentation Validation and full HidraAPI CI startup through existing
triggers, then STOP for Next/failure notification without waiting for completion.
The next selected stage verifies those exact-transition gates including Java 21
clean verify, OpenAPI equality/compatibility, migrated dictionary comparison and
retained P1 evidence/operations gates. No later task executes automatically.

P0/P1/P2 remain CLOSED; all P3 tasks stay DEFERRED. No source/test/configuration,
migration/schema, contract/dictionary/owner-policy or operations change occurs.
No private-data import, policy approval, physical campaign, deployed AI inference
or production promotion is authorized. Dated candidate/unpublished/version
statements retain historical scope and are superseded by this actual release and
current development disposition. Deployment remains a separate governed workflow.


Local transition validation PASSED: canonical documentation (95 documents,
5,062 links, 24 modules, 13 P2 rows), offline OpenAPI integrity (244 paths,
263 operations, 231 schemas), P1 closure evidence, exact five-file scope,
POM-only project version difference, preserved phase registries/57 HMR identities/
permanent semantic catalogue, identical transition records and git diff --check.
Every other tracked blob is unchanged; no new test campaign is claimed.

Actual `bash ./mvnw -B -q clean verify` FAILED before compilation resolving the
uncached Spring Boot parent 4.1.1 because repo.maven.apache.org DNS is unavailable.
Installed Java is 17.0.20; Java 21 is required. The new transition requires its
own full GitHub CI and documentation results; no future success is pre-asserted.


## HPR-REL-002 final exact-transition verification — 2026-10-09

The owner's Next selects verification and recording of the development transition.
GitHub main was rechecked unchanged at ce8867c252fb537d9cca652f9b397a6d35bbc96a,
tree a5a5dd39214d7f5cfdcee96af878c437103211a5; the local full tree matched.
AGENTS.md, Ultimate Roadmap and reconciliation were read from that exact SHA before
mutation. POM is 0.7.0-SNAPSHOT. Both required transition runs completed SUCCESS:

- Documentation Validation #140, run 37941995197;
- full HidraAPI CI #611, run 37941995284.

Production job 113858453078, Java 21 Maven verification, and every reported step
succeeded. Java 21 clean verify, canonical validators, retained P1 evidence/HA/
release/backup/observability/database-operations gates, fresh OpenAPI publication,
canonical runtime equality, base generation and backward compatibility, migrated
dictionary comparison/capture and artifact retention all passed. Actual job logs
record OpenAPI runtime equality (244 paths, 263 operations, 231 schemas), 139
migrations, 470 JPA mappings, 482 relations, reviewed_dictionary_checked true,
zero unresolved owners and source_sha ce8867c252fb537d9cca652f9b397a6d35bbc96a.
The source bundle remains 86f6ba5923e63c0cd601f7722ca0282e486d59fc92bb120616710fb3cd1799d4.
No artifact archive or per-class execution/skip report was independently inspected
here; cumulative CI success does not imply unbounded endpoint/performance/OT
coverage, policy approval, import/data acceptance or new physical exercises.

HPR-REL-002 is COMPLETED. Formal v0.6.0 still resolves to verified release SHA
09bf1cbf82c20f19c50ebb1ff9e27047c7f34856; GitHub latest release remains published,
non-draft and non-prerelease at 2026-10-09T14:02:18Z. The lightweight/unsigned tag
is unchanged. Main's verified development baseline is ce8867c252fb537d9cca652f9b397a6d35bbc96a,
version 0.7.0-SNAPSHOT. Release-management completion under AGENTS.md §19 does not
assert production deployment. P0/P1/P2 CLOSED, P3 DEFERRED, all 57 HMR completions
and 123 semantic subjects remain preserved. Dated PENDING preparation statements
retain historical scope and are superseded by this exact-transition result.

Supporting exact message: `docs(release): record verified 0.7.0 development`.
Write ONLY VERSIONS.md, PROJECT_STATE.md, doc/roadmap/ULTIMATE_ROADMAP.md and
doc/model-remediation/RECONCILIATION.md; every other tracked blob/POM remains
unchanged. Validate canonical documentation, offline OpenAPI, P1 evidence, exact
four-file scope, identical verification records and preserved phase/HMR/catalogue
identities. Publish once with expected-head lease from the transition SHA, verify
immutable parent/tree/files, observe Documentation Validation startup and STOP.
Full #611 remains attached to the executable transition SHA; a documentation-only
supporting record does not require repeating full CI or become a replacement
release tag/transition target. No further version, release, P3 capability,
deployment/import/policy or physical campaign is selected. A future workstream
requires its own owner task selection and applicable requirements.

Actual local registration checks PASSED: canonical documentation validation (95 documents,
5,063 relative links, 24 modules, 13 P2 rows), offline OpenAPI validation
(244 paths, 263 operations, 231 schemas), P1 closure evidence validation and
`git diff --check`. Exactly two documentation files changed. These checks do not
establish runtime twin implementation; exact-head Documentation CI follows publication.

Actual local HPR-P25-001 checks PASSED: canonical documentation (95 documents,
5,063 relative links, 24 modules, 13 P2 rows), offline OpenAPI (244 paths,
263 operations, 231 schemas), P1 closure evidence and git diff --check.
Exactly two documentation files change; exact-head Documentation CI follows
publication. No runtime, solver or acceptance-fixture execution is claimed.

Actual decision-record validation PASSED: canonical documentation (95 documents,
5,063 relative links, 24 modules, 13 P2 rows), offline OpenAPI (244 paths,
263 operations, 231 schemas), P1 closure evidence and git diff --check.
Exactly two documentation files change; prior phase/HMR registry identities are
preserved. Exact-head Documentation CI follows publication; no engine execution
or GZ2 physical validation is claimed.

Actual HPR-P25-002 preflight checks PASSED: canonical documentation (95 documents,
5,063 relative links, 24 modules, 13 P2 rows), offline OpenAPI (244 paths,
263 operations, 231 schemas), P1 closure evidence and git diff --check.
Exact two-document scope and retained phase/HMR rows verified; four new 002A..D
identities registered. No runtime/source/physics change is claimed; Documentation
CI follows publication.


## HPR-P25-002A Immutable Input Manifest Implementation — 2026-10-09

Owner Next selects HPR-P25-002A after preflight Documentation #145/run
37956618791 PASSED on exact parent e968ca3f829a53e5b1addcfde17357d7e4e55125,
tree 84732e31d06452d85cf60d4d44f5bf77f1a45992. Mandatory instruction/roadmap/
reconciliation reads completed; source tree matched before mutation. Exact message:
`feat(simulation): establish immutable input manifest contracts`.

Created ONLY src/main/java/dz/sh/hidra/modules/simulation/domain/value/SimulationInputMode.java,
src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputSourceVersion.java,
src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifest.java and
src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifestTest.java.
Updated ONLY the Ultimate Roadmap and reconciliation; six tracked paths total.
No migration, persistence/API exposure, owner export, dependency or workflow change.

Contracts enforce explicit mode/schema/product/scope identities; SHA-256 syntax and
canonical lowercase reference; required source/evidence/revision metadata; effective
start-inclusive/end-exclusive intervals; initial-state/capture/watermark/recorded
time coherence; exactly one reference per required kind; transient future horizon
and boundary schedule; steady-state exclusion of both; defensive immutable lists.
Any synthetic source marks the input synthetic. Declared origins/digests are not
verified trust/content; no readiness method or hydraulic calculation is introduced.
Corrections/replacements leave previously constructed input manifests unchanged.

Fourteen JUnit tests prepared for positive synthetic modes, replacement/defensive
immutability, validity boundaries/open ends, expired/future sources, post-capture
revisions, missing/duplicate/null source kinds, horizon/schedule combinations,
initial/capture/watermark ordering, identity/schema/mode/time requirements, source
evidence, digest syntax/normalization and synthetic-origin detection.

Actual validation:
- Direct ./mvnw focused test invocation failed with permission denied because the
  checked-out wrapper is not executable. No chmod/file-mode change was made.
- bash ./mvnw -B -q -Dtest=SimulationInputManifestTest,ArchitectureGuardrailTest test
  and bash ./mvnw -B -q clean verify both BLOCKED before compilation by uncached
  Spring Boot parent 4.1.1 and repo.maven.apache.org temporary DNS failure.
  Local runtime is Java 17.0.20, not the required Java 21. No Maven/JUnit pass claimed.
- Standalone production contracts plus existing local exception classes compiled
  using Java 17's jdk.compiler/com.sun.tools.javac.Main (javac launcher absent).
  A temporary external smoke harness passed valid steady/transient, synthetic flag,
  defensive copy/unmodifiable selection, missing schedule, forbidden steady schedule,
  duplicate source and exclusive effective-end checks. This is not execution of the
  14 JUnit tests, ArchitectureGuardrailTest or full Java 21 verification.
- Canonical documentation validation PASSED (95 documents, 5,063 relative links,
  24 modules, 13 P2 rows), offline OpenAPI PASSED (244 paths, 263 operations,
  231 schemas), retained P1 closure evidence PASSED and git diff --check PASSED.

HPR-P25-002A remains IN PROGRESS pending exact-head applicable full CI;
HPR-P25-002 remains PENDING and B/C/D/engine stages are not executed.
After successful A CI, next recommendation is HPR-P25-002B documentation-only
physical-payload preflight, exact message
`docs(twin): register HPR-P25-002B execution preflight`; same two execution memories.
Vendor tool information supplied by owner can guide later benchmark requirements;
no external engine access, Sonatrach installation evidence, equivalence or license
is inferred, and no vendor engine/model is imported in this stage.

Publish with expected-parent lease, verify tree/parent/exact scope, observe both
applicable Documentation and full production CI startup, then STOP for owner Next/Fail.
P0/P1/P2 CLOSED, P2.5 OPEN, P3 DEFERRED, 57 HMR completions, 123 semantic subjects,
formal v0.6.0 and development 0.7.0-SNAPSHOT remain preserved.

Actual HPR-CI-001 local checks PASSED: workflow YAML and every shell block parsed;
all original steps/settings/order retained except two-fork Maven flags; summary
aggregation/order/empty-report behavior exercised with temporary XML fixtures;
37 canonical validator tests PASSED (10 documentation, 10 OpenAPI, 17 dictionary).
Canonical docs (95 documents, 5,063 links, 24 modules, 13 P2 rows), offline
OpenAPI (244 paths, 263 operations, 231 schemas), P1 evidence and whitespace
checks PASSED. Exact three-file scope and retained phase/HMR rows verified.
No optimized runner/Maven duration is available locally; exact-head full CI
will provide concurrency, resource and speedup evidence after publication.


## HPR-CI-001 Verified Optimization Result — 2026-10-09

Owner Next selects evidence reconciliation before resuming Phase 2.5. Main remains
3e9c19142a750b1aab3ca6fb3afdf5df5906b318, tree
664d21d3be2fc3b867032abc7170074ac61adf27. Mandatory AGENTS/roadmap/
reconciliation reads completed; local tree matches. Full CI #613/run 37961219860
and Documentation #147/run 37961220149 completed SUCCESS on this exact SHA.
All full-CI steps succeeded, including Java 21 clean verify with two isolated reused
forks, current OpenAPI equality, historical backward compatibility, migrated
dictionary comparison and existing P1/operations gates. HPR-CI-001 COMPLETED.

| Measured step | Baseline #612 | Optimized #613 | Observed reduction |
|---|---|---|---|
| Verification job elapsed | 726 seconds (12m06s) | 395 seconds (6m35s) | 331 seconds / 45.6 percent |
| Maven repository verification | 592 seconds (9m52s) | 281 seconds (4m41s) | 311 seconds / 52.5 percent |

Timings come from GitHub job/step timestamps: baseline job 113915537522 and
optimized job 113924240920. The unchanged test source set and retained original
workflow gates were checked at optimization publication. Optimized logs directly
report 324 Surefire XML files and 1,277 tests, failures 0, errors 0, skipped 0.
Test report upload succeeded; artifact ID 11631059984, size 1,631,791 bytes,
digest sha256:8337e0167dd7f1e911e0d8f5027746d59b4de2a88d7c6a56ceae4cfefdb1b2cc,
name hidra-test-reports-3e9c19142a750b1aab3ca6fb3afdf5df5906b318, retained
until 2026-10-23T16:49:24Z. Artifact metadata verified through GitHub.
The binary archive was not independently downloaded/parsed; counts are the executed
workflow log summary. Baseline #612 did not publish this report, so an independent
per-class baseline-versus-optimized XML comparison is not claimed.

Runtime OpenAPI equality verified 244 paths, 263 operations and 231 schemas.
Dictionary log evidence reports 139 migrations, 470 JPA mappings, 482 relations,
reviewed_dictionary_checked true, unresolved_owners [] and source_sha equal to
3e9c19142a750b1aab3ca6fb3afdf5df5906b318. Source bundle remains
86f6ba5923e63c0cd601f7722ca0282e486d59fc92bb120616710fb3cd1799d4.
No per-class manifest test count is inferred from the aggregate alone.

This single before/after comparison supports observed improvement, not a fixed SLO
or causal timing guarantee across variable runner/cache conditions. No failures or
skips are reported in the optimized run; no further fork increase or workflow
change is selected. Continue retaining diagnostics for future regressions.

Supporting exact message: `docs(ci): record verified production CI optimization`.
Write ONLY doc/roadmap/ULTIMATE_ROADMAP.md and doc/model-remediation/RECONCILIATION.md.
No source/test/workflow/POM/schema/API/dependency change or full-runtime rerun is
needed solely for this evidence record. Full #613 remains pinned to the optimized
executable commit, not this documentation-only record. Validate canonical docs,
offline OpenAPI, retained P1 evidence, whitespace and exact two-file scope.
Publish with expected-parent lease, verify parent/tree/files, observe Documentation
Validation startup and STOP. Next recommended owner selection is HPR-P25-002B
physical-input preflight / `docs(twin): register HPR-P25-002B execution preflight`.
P0/P1/P2 CLOSED, P2.5 OPEN, P3 DEFERRED, 57 HMR completions, 123 subjects,
formal v0.6.0 and current 0.7.0-SNAPSHOT remain preserved.

Actual supporting verification checks PASSED: canonical documentation (95 documents,
5,063 links, 24 modules, 13 P2 rows), offline OpenAPI (244 paths, 263 operations,
231 schemas), P1 evidence and git diff --check. Exact two-document scope and
retained phase/HMR identities verified. Supporting Documentation CI follows publication.


## HPR-P25-002B Physical Payload Preflight — 2026-10-09

Owner Next selects ONLY `docs(twin): register HPR-P25-002B execution preflight`.
Write ONLY the Ultimate Roadmap and reconciliation. Parent main
929073662786da201e051170520013d4d3402ebf, tree
67290cdd14ca646819de3e252508e724a6abc0b7 passed Documentation #148/run 37962488460.
Read AGENTS/roadmap/reconciliation before mutation; source tree matches. Full #613
remains pinned to optimized executable 3e9c19142a750b1aab3ca6fb3afdf5df5906b318.

Revalidated manifest/source-version metadata, Topology node/segment/equipment and
owner boundaries. Manifest has no typed physical payload; nullable Topology values
are not complete geometry or operating policy. No nominal-diameter/internal-diameter
or design-pressure/operating-limit substitution. Physical inputs are immutable
Simulation-owned copies; owner truth/trust remains in Topology/Custody/Telemetry.
Explicit SI quantity names and conversion/reference provenance apply to future gas
contracts; no physics method or arbitrary accuracy/field threshold is selected.

Register four PENDING substeps 002B1 physical pipe graph, B2 fluid/equipment, B3
initial state/boundary schedule, B4 consistency assembly; exact messages and
dependencies are in the roadmap. Parent B/002 remain PENDING; no source implemented.
Future gas-engine methods require numerical design; commercial tool information
is benchmark context, not model/access/equivalence evidence. GZ2 data qualifies
field calibration, not synthetic contract development. Other products remain targets.

Next owner Next after successful preflight Documentation CI selects ONLY B1, exact
message `feat(simulation): define physical network input contracts`. Create ONLY
src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationNetworkNodeInput.java,
src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPipeSegmentInput.java,
src/main/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInput.java,
src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInputTest.java.
Update ONLY both execution memories: total six tracked paths. Existing exception and
local Simulation/Java types only; canonical author/date headers. No migration,
persistence/API/schema/dependency/workflow/foreign-module/architecture change.

Node requires identity/elevationMeters; pipe requires identity/distinct endpoints,
positive length/internal diameter and nonnegative absolute roughness in meters.
Network requires id/topologyRevisionId, defensive node/pipe lists, unique normalized
IDs, valid endpoints and one connected undirected component with no isolated nodes.
Cycles and distinct parallel pipes allowed; reference orientation permits reverse
flow. No hydraulic equations or station/equipment graph completeness is claimed.

Tests use synthetic BigDecimal networks for valid pipe/branch/mesh/parallel cases,
missing/invalid dimensions/IDs, duplicates/dangling endpoints, disconnected/isolated
graphs and defensive immutability/replacement revisions. Required commands when B1
is selected: ./mvnw -B -q -Dtest=SimulationPhysicalNetworkInputTest,SimulationInputManifestTest,ArchitectureGuardrailTest test,
then ./mvnw -B -q clean verify and canonical docs/OpenAPI/P1/scope checks; accurately
report local limitations. Observe both implementation workflows starting and STOP.
No repeat B1 preflight unless evidence changes; B2/B3/B4 need their own exact-file
preflights using their registered messages. Phase closure includes all four substeps.

Validate this two-document preflight with canonical docs/OpenAPI/P1/whitespace and
retained registry checks. Publish with expected-parent lease, verify tree/parent/files,
observe Documentation startup and STOP. P0/P1/P2 CLOSED, P2.5 OPEN, P3 DEFERRED,
57 HMR completions, 123 subjects, v0.6.0 and 0.7.0-SNAPSHOT preserved.

Actual B preflight local checks PASSED: canonical documentation (95 documents,
5,063 links, 24 modules, 13 P2 rows), offline OpenAPI (244 paths, 263 operations,
231 schemas), P1 evidence and whitespace. Exact two-document scope and retained
phase/HMR rows verified; four unique B1..B4 identities registered. No physical
source/calculation is implemented; supporting Documentation CI follows publication.
