# HidraAPI Semantic Remediation Reconciliation

## Status

CURRENT — all 57 reconciled HMR implementations COMPLETED; permanent 123-subject semantic transfer complete. HPR-P2-005/006 corrections are COMPLETED; renewed P2 audit PASS with 12/12 VERIFIED at 7be1c9cb47ed9b6f73a7692d0a328ad870e2b4d4. HPR-P2-013 is COMPLETED and P2 VERIFIED/CLOSED after exact-head Documentation Validation #136 and production CI #609 PASSED at a8905e32289a583f47b831e0381783e556ae0c8d. Phase P2.5 is PLANNED/OPEN; HPR-P25-000 registration CI #142 PASSED and HPR-P25-001 source/requirements preflight is COMPLETED following owner decisions: GZ2 gas, both modes, own engine and dynamic versioned inputs; field qualification remains pending; operational twin runtime remains NOT ESTABLISHED. P3 remains DEFERRED; prior 9/12 FAIL and #127/#606 retain historical applicability.

## Historical HPR-P2-007 Verification Baseline

Repository: `CHOUABBIA-AMINE/HidraAPI`

Exact baseline: `aae829778d610b2912869844babfdcef82068138`

Legacy source register: `docs/roadmap/model-semantic-remediation.md`

The legacy register is preserved as execution history. This canonical reconciliation controls P2 execution disposition together with `doc/roadmap/ULTIMATE_ROADMAP.md`.

## Reconciliation Rules

HPR-P2-007 does not execute semantic remediation. It revalidates whether legacy HMR/HMSR obligations still require execution.

A legacy item is marked:

- **COMPLETED** only when current source/migration/test evidence demonstrates the registered obligation has already been implemented;
- **STILL REQUIRED** when no current evidence proves completion and the underlying source subject still exists;
- **BLOCKED** when a required owner-controlled contract/decision is still absent;
- **SUPERSEDED** only when current architecture/source makes the recorded obligation inapplicable. No HMR-050..106 item met that bar at this baseline.

Absence of the originally registered migration alone is not enough to prove an obligation is unresolved when no migration was required. For that reason the reconciliation also checks the registered dedicated semantic test and exact current architecture/contracts.

## Corrected Legacy Dispositions

### HMR-005 — telemetry.TelemetryPoint

**COMPLETED — legacy status is stale.**

Current evidence includes:

- `src/main/resources/db/migration/V20261004_005__hmr_005_telemetry_telemetry_point.sql`;
- `src/test/java/dz/sh/hidra/modules/telemetry/semantic/TelemetryPointSemanticRemediationTest.java`;
- `TelemetryPoint` rejects blank French names;
- application uniqueness pre-check exists for device + code;
- database unique index exists for `(device_id, code)`;
- persistence adapter validates active catalog families;
- optional unit reference resolves to an active TelemetryUnit;
- SIGNAL_TYPE `valueShape` and POINT_TYPE `numericUnitExempt` drive numeric/text/boolean and unit compatibility.

Therefore HMR-005 must not be re-executed absent concrete regression evidence.

### HMR-009 — simulation.SimulationModel

**COMPLETED — legacy carry-over blocker note is stale.**

The HMR-009 record itself is already `Completed`, with registered migration/test/application/Topology-contract evidence. The later legacy sentence saying “HMR-009 remains unresolved” contradicts the record and is not execution authority.

HMR-009 must not be reopened absent concrete regression evidence.

### HMR-054 — topology.Equipment

**COMPLETED — HMR-054 implementation and repaired CI #575 supersede the historical baseline below.**

The exact current tree contains:

`src/main/java/dz/sh/hidra/modules/party/application/contract/topology/TopologyPartyReferenceContract.java`

That Party-owned contract is deliberately exported to Topology and is already architecture-allowlisted. The historical “no suitable exported owner lookup” prerequisite is therefore no longer a blocker.

Current Equipment source, dedicated semantic test and V20261006_009 implement the
registered remediation. The former implementation-required sentence is superseded
by repaired CI #575 and cumulative current-tree CI #604; do not reopen HMR-054.

### HMR-080 — planning.Nomination

**COMPLETED — exact-head production CI #604 PASSED.**

The accepted standalone execution exports Party, Custody product and Telemetry unit
contracts, validates direct saves and installs forward 025/026. The previous absent
owner-contract blocker is superseded. Implementation 41904baff2becbdd03e19d3b2da005a28ad8d8a0
and fixture repair 617c2eec812e3a5734957ee9fa0360f6f5613032 are confirmed by CI #604.
The owner-authorized HPR-P2-008 closure is recorded below; P2 remains open.

## Reconciled Outstanding Register

| HMR | HMSR | Subject | Canonical disposition | Exact-current evidence |
|---|---|---|---|---|
| HMR-050 | HMSR-059 | integrity.IntegrityProgram | COMPLETED — HPR-P2-008 | legacy migration not required after current-schema revalidation; dedicated semantic test added; active `INTEGRITY_PROGRAM_TYPE` family enforced; Organization-owned Integrity contract validates populated owner unit; no cross-module FK |
| HMR-051 | HMSR-060 | leakdetection.LeakDetectionCase | COMPLETED — HPR-P2-008 | Topology and optional Organization references validated on every case save; snapshot preserved; no migration because primary-candidate FK already exists; owner contract and architecture export added. |
| HMR-052 | HMSR-061 | notification.NotificationMessage | COMPLETED — HPR-P2-008 | Recipient/request composite FK; exact-version/template FK and pre-dispatch composition guard; required-input schema checked for sendable states; inputs/version frozen; active NOTIFICATION_PRIORITY eligibility; V20261006_005; dedicated unit and PostgreSQL tests added. |
| HMR-053 | HMSR-062 | telemetry.TrustedTelemetryReading | COMPLETED — HPR-P2-008 | Telemetry trust application use case derives values/provenance/binding from locked source evidence; PASSED plus MEDIUM/HIGH/CERTIFIED, ACTIVE point and active QUALITY_CODE required; composite assessment/reading identity and optional unit/batch FKs; snapshot preservation; V20261006_008; focused unit and PostgreSQL tests added; CI #604 passed. |
| HMR-054 | HMSR-063 | topology.Equipment | COMPLETED — HPR-P2-008 | EquipmentType identity/code is sole active classification; EquipmentKind deleted from domain/JPA; forward V20261006_009 preserves legacy strings, rejects conflicting classification/orphan attachments and adds nullable same-module FKs; manufacturer checked by existing Party contract; snapshots preserved; focused tests added; CI #604 passed. |
| HMR-055 | HMSR-064 | workflow.WorkflowInstance | COMPLETED — CI #581 GREEN | Owner-bound starts enforce active definition/version and exact binding, governed purpose/type, current-step coherence and owner target/actor snapshots; nonterminal uniqueness and same-definition/version database guards. Planning target registry denies unsupported/ambiguous owners. Eight focused behavior checks passed with temporary stubs; local Maven blocked by uncached parent, not a JUnit/PostgreSQL pass. |
| HMR-056 | HMSR-067 | integration.IntegrationExchangeMessage | COMPLETED — CI #585 PASSED | Optional run/endpoint existence, correlated endpoint/system ownership and active exact existing MESSAGE_TYPE/PAYLOAD_FORMAT catalogs enforced on saves. Forward V20261007_010 adds nullable/composite FKs and catalog guards without rewriting legacy evidence. Five dedicated and five PostgreSQL cases prepared. Local compile/focused Maven blocked before execution by uncached Boot parent; CI #604 passed. |
| HMR-057 | HMSR-068 | reporting.ReportRun | COMPLETED — CI #588 GREEN | Queue eligibility/access/approval, exact template lineage, concrete required parameters and terminal evidence enforced. Forward 012 corrects run/parameter request FKs and guards lineage/history. Eight Run, four QueueEvidence and nine PostgreSQL cases prepared; local runtime validation follows; Full production CI #588 passed. |
| HMR-058 | HMSR-069 | risk.RiskAssessment | COMPLETED — CI #593 GREEN | Atomic structured scopes, matrix provenance, authenticated Workflow/Audit approval and approved immutability; Java 21 clean verify and OpenAPI compatibility passed at cfc7798477c70d10e1c3e0afd4dd7e1b42676898. |
| HMR-059 | HMSR-071 | leakdetection.LeakEscalationReference | COMPLETED — HPR-P2-008 | Optional candidate validated before save and protected by V20261006_001 nullable same-module FK with fail-closed orphan preflight; no case-primary equality rule. |
| HMR-060 | HMSR-072 | notification.NotificationDeliveryAttempt | COMPLETED — HPR-P2-008 | Channel/message composite FK; create-only EntityManager.persist plus PK race protection; update/delete/truncate rejected; permanent/cancelled automatic retry rejected; V20261006_006; dedicated unit and PostgreSQL tests added. |
| HMR-061 | HMSR-073 | workflow.WorkflowTransition | COMPLETED — CI #581 GREEN | Distinct same-definition steps and unique source decisions are protected in configuration persistence and PostgreSQL. Unsupported conditions/callbacks/COMMENT cannot attach to ACTIVE definitions or survive activation; runtime remains fail closed. Three focused behavior checks passed with temporary stubs; PostgreSQL validation confirmed by CI #604. |
| HMR-062 | HMSR-074 | incident.Incident | COMPLETED — CI #595 GREEN | Batch 15 and inventory repair passed full Java 21/PostgreSQL/OpenAPI CI at e2e92bae7d69c54a46fa92702b539858404bf7ce. |
| HMR-063 | HMSR-075 | identity.User | COMPLETED — HPR-P2-008 | Nonblank username enforced in domain; PostgreSQL named username/email uniqueness, nullable-email semantics and fail-closed legacy preflight; optional Employee resolved through Organization-owned contract; no cross-module FK. V20261006_010 and focused tests added; final CI #604 passed. |
| HMR-064 | HMSR-076 | planning.PlanRevision | COMPLETED — CI #582 GREEN | Positive per-plan revision numbers, nullable validated base lineage, active REVISION_REASON and approved metadata/persistence/database immutability enforced. Forward V20261007_001; four focused checks passed using temporary API/assertion stubs, not Maven/JUnit. Five PostgreSQL cases registered; local compile/focused Maven blocked by uncached Boot 4.1.1 parent. Full database validation confirmed by CI #604. |
| HMR-065 | HMSR-077 | planning.OperationalPlan | COMPLETED — CI #582 GREEN | Required French name/scope type, unique plan code, active PLAN_TYPE, owner-controlled Topology/Identity/Organization references and same-plan nullable revision pointers enforced. Creation binds authenticated eligible actor and snapshots owner display values; unsupported REGION/NETWORK denied. Forward V20261007_002; twelve focused HMR-065/owner/catalog checks passed with temporary API stubs. Nine combined PostgreSQL cases registered; Maven compile/focused/full test/clean verify blocked before compilation by uncached Boot 4.1.1/Maven Central DNS. Full CI #604 passed. |
| HMR-066 | HMSR-078 | workflow.WorkflowTask | COMPLETED — CI #581 GREEN | Actionable assignment, live actor/unit membership, catalog eligibility, actor/time pairs and chronology are enforced. Terminal task evidence is immutable; generic creation is starter-bound, missing next-step rules fail closed, and execution/query paths no longer authorize by username snapshots. Seven focused behavior checks passed with temporary stubs; existing transition fixtures updated for new owner dependencies. |
| HMR-067 | HMSR-079 | documents.Document | COMPLETED — CI #583 GREEN | Required title/creator display, active exact document catalogs, code uniqueness and same-document current-version pointers enforced. Registration binds authenticated eligible Identity actor and canonical owner snapshots; neutral registry supports Topology/Planning and denies missing/ambiguous owners. Forward V20261007_003; 11 focused methods passed with temporary APIs, four PostgreSQL cases added. Compile/focused Maven blocked before compilation by uncached Boot 4.1.1 parent; full CI #583 passed. |
| HMR-068 | HMSR-080 | documents.DocumentVersion | COMPLETED — CI #583 GREEN | Required upload metadata, positive per-document unique numbers, nullable existing supersession and owner-controlled Identity/Workflow references enforced. Generic upload derives authenticated uploader display. Binary prevalidates metadata and registers known-rollback new-blob cleanup; failed cleanup preserves original error, unknown commit outcome preserves content and logs reconciliation. Forward V20261007_004; 10 focused owner/version/cleanup methods passed with temporary APIs; nine combined PostgreSQL cases include transactional storage rollback and confirmed commit failure. Local focused Maven blocked by uncached Boot 4.1.1 parent; full CI #583 passed. |
| HMR-069 | HMSR-081 | assets.MaintenanceWorkOrder | COMPLETED — CI #596 GREEN | Batch 16 passed full Java 21/PostgreSQL/OpenAPI verification at 68e330562b03cf92c5500b99ffceca1fd024d083. |
| HMR-070 | HMSR-082 | custody.CustodyTransferTicket | COMPLETED — CI #596 GREEN | Batch 16 passed full Java 21/PostgreSQL/OpenAPI verification at 68e330562b03cf92c5500b99ffceca1fd024d083. |
| HMR-071 | HMSR-084 | integration.IntegrationDeadLetterRecord | COMPLETED — CI #585 PASSED | Required failure evidence, all-or-none manual trio and optional local references enforced. New manual evidence requires authenticated eligible Identity actor; recorded provenance is immutable without historical actor revalidation. Forward V20261007_011 supplies nullable FKs/checks and concurrent provenance guard. Eight focused methods, one Identity owner method and five added PostgreSQL cases prepared. Temporary API type compilation passed; local focused Maven blocked by uncached Boot parent; CI #604 passed. |
| HMR-072 | HMSR-085 | integrity.IntegrityAssessment | COMPLETED — CI #596 GREEN | Batch 16 passed full Java 21/PostgreSQL/OpenAPI verification at 68e330562b03cf92c5500b99ffceca1fd024d083. |
| HMR-073 | HMSR-087 | organization.EmployeeAssignment | COMPLETED — HPR-P2-008 | Assignment service resolves same-module OrganizationUnit and rejects missing or non-ACTIVE units before save; existing employee/unit/position FKs retained; no migration. |
| HMR-074 | HMSR-088 | organization.OrganizationDelegation | COMPLETED — HPR-P2-008 | JPA responsibility_assignment_id is mandatory; V20261006_002 aborts on legacy null rows before SET NOT NULL; existing same-module FK preserved; real PostgreSQL focused tests added. |
| HMR-075 | HMSR-089 | organization.OrganizationHierarchySnapshot | COMPLETED — HPR-P2-008 | JPA captured_by_employee_id is mandatory; V20261006_003 aborts on legacy null rows before SET NOT NULL; existing same-module FK preserved; real PostgreSQL focused tests added. |
| HMR-076 | HMSR-090 | organization.ShiftAssignment | COMPLETED — HPR-P2-008 | JPA organization_unit_id is mandatory; V20261006_004 aborts on legacy null rows before SET NOT NULL; existing same-module FK preserved; real PostgreSQL focused tests added. |
| HMR-077 | HMSR-091 | risk.RiskEvidenceLink | COMPLETED — CI #593 GREEN | Typed owner evidence validation and named provider beans verified by Java 21 clean verify and OpenAPI compatibility at cfc7798477c70d10e1c3e0afd4dd7e1b42676898. |
| HMR-078 | HMSR-092 | simulation.SimulationCandidateChange | COMPLETED — CI #590 GREEN | Accepted SIM-PREREQ-01; required change values, active exact catalog and Topology target lookup; forward 001. |
| HMR-079 | HMSR-093 | simulation.SimulationRecommendation | COMPLETED — CI #590 GREEN | Required content, exact catalogs, nullable local references and transactional Audit-owned publication; forward 002/003. |
| HMR-080 | HMSR-094 | planning.Nomination | COMPLETED — CI #604 PASSED | NOM-OWNER-01/NOM-EXEC-01 accepted; implementation 41904baff2becbdd03e19d3b2da005a28ad8d8a0 and fixture repair 617c2eec812e3a5734957ee9fa0360f6f5613032; full Java 21 verification, PostgreSQL/Spring-JPA tests and OpenAPI compatibility passed; forward 025/026. |
| HMR-081 | HMSR-095 | workflow.WorkflowAction | COMPLETED — CI #581 GREEN | Generic recording permits comments only; configured transitions exclusively produce decisions using live Identity authority. Optional task ownership and conditional evidence are enforced; canonical actor snapshots and server-owned locked sequences replace caller evidence. Action persistence is insert-only with unique monotonic sequence and immutable database guards. Five focused behavior checks passed with temporary stubs; existing permission regression fixture updated. |
| HMR-082 | HMSR-096 | hse.HseCase | COMPLETED — CI #597 GREEN | Accepted Batch 17 implementation passed full Java 21/PostgreSQL/OpenAPI CI on cfb3681ef1c79b4416336a3533cbc0599b4fd6b2. |
| HMR-083 | HMSR-097 | audit.AuditExportRequest | COMPLETED — CI #584 GREEN | Required export metadata, active EXPORT_PURPOSE, owner-controlled optional Workflow/Documents references, bounded sanitized filters and one transactional EXPORT access record implemented. Generic writes admit REQUESTED only and persist/flush without merge. Forward V20261007_006; eight focused tests, two owner tests and four PostgreSQL/Spring/JPA tests prepared. Local Maven compile/focused blocked before compilation by uncached Boot 4.1.1 parent; production CI #584 passed. |
| HMR-084 | HMSR-098 | documents.DocumentTargetLink | COMPLETED — CI #583 GREEN | Required target module, active exact DOCUMENT_LINK_ROLE and owner-controlled target resolution enforced. Authenticated linking actor and canonical owner snapshots replace caller identity/display claims; optional version must belong to linked document, protected by composite FK. Forward V20261007_005; four focused methods passed with temporary APIs and three PostgreSQL cases added (12 combined). Both public export registries match 34 exact packages; all five forensic scans passed with temporary APIs. Focused Maven blocked by uncached parent; full CI #583 passed. |
| HMR-085 | HMSR-100 | identity.AuthorizationDecision | COMPLETED — CI #579 PASSED | Transactional graph, bounded ABAC, verified mappings, deterministic evidence and configurable persistence; Batch 6 implementation below. |
| HMR-086 | HMSR-101 | identity.AuthorizationDelegationGrant | COMPLETED — HPR-P2-008 | Required nonblank delegation reason and validTo carried through domain/JPA/mapper; DelegationStatus narrowed to ACTIVE/REVOKED/EXPIRED; optional Role and Permission validated with nullable same-module FKs; no XOR rule; V20261006_011 fails closed on legacy evidence; focused tests added; final CI #604 passed. |
| HMR-087 | HMSR-104 | identity.LoginSession | COMPLETED — HPR-P2-008 | AuthenticationProtocol sessionType and independent endedAt carried through domain/JPA/mapper; exact ExternalIdentity propagated from LDAP/OIDC through principal/input/completion; terminal lifecycle preserves lastSeenAt and prior termination; V20261006_012 requires explicit legacy protocol evidence; no inferred historical termination; focused tests added; final CI #604 passed. |
| HMR-088 | HMSR-105 | identity.UserPermissionGrant | COMPLETED — HPR-P2-008 | Domain and PostgreSQL enforce nonblank grantReason, bounded validTo and ACTIVE/REVOKED/EXPIRED for direct permission grants including emergency records; shared GrantStatus and optional role-grant reason/end remain unchanged; V20261006_013 and focused tests added; final CI #604 passed. |
| HMR-089 | HMSR-106 | identity.UserRoleGrant | COMPLETED — HPR-P2-008 | Authoritative ordinary role-grant application flow requires ACTIVE User before Role lookup/save; inactive states reject without implicit emergency bypass; optional reason/end and shared SUSPENDED role status preserved; focused tests added; no migration required; final CI #604 passed. |
| HMR-090 | HMSR-107 | incident.IncidentClosure | COMPLETED — CI #595 GREEN | Batch 15 and inventory repair passed full Java 21/PostgreSQL/OpenAPI CI at e2e92bae7d69c54a46fa92702b539858404bf7ce. |
| HMR-091 | HMSR-108 | incident.IncidentRelatedIncident | COMPLETED — CI #595 GREEN | Batch 15 and inventory repair passed full Java 21/PostgreSQL/OpenAPI CI at e2e92bae7d69c54a46fa92702b539858404bf7ce. |
| HMR-092 | HMSR-109 | incident.IncidentResponseAction | COMPLETED — CI #595 GREEN | Batch 15 and inventory repair passed full Java 21/PostgreSQL/OpenAPI CI at e2e92bae7d69c54a46fa92702b539858404bf7ce. |
| HMR-093 | HMSR-110 | reporting.ReportOutputArtifact | COMPLETED — CI #588 GREEN | Existing run and nonblank Documents reference evidence required; every supplied reference is independently owner-validated. Forward 013 corrects artifact/run FK without Documents FK. Six focused methods, one Documents owner method and five additional PostgreSQL cases prepared; Full production CI #588 passed. |
| HMR-094 | HMSR-111 | planning.PlanTarget | COMPLETED — CI #600 PASSED | Independent semantic commit 3845b7d30637ec9fc7306a527a0ae569b501fa89 plus narrow repair 0cc3e5c6c4b675880a634b3a073905f4119a36b7; Java 21 clean verify and OpenAPI green on repaired Batch 19 final tree. |
| HMR-095 | HMSR-112 | audit.AuditEvent | COMPLETED — CI #584 GREEN | Required source/target module and target type, active exact event/category/optional severity/reason families, bounded sanitized payload/free text and persist/flush insertion enforced. Forward V20261007_007 adds optional catalog FKs, family guards and immutable event UPDATE/DELETE denial. Five focused and four added PostgreSQL/JPA/concurrency checks prepared; local focused Maven blocked by uncached Boot parent; CI #584 passed. |
| HMR-096 | HMSR-113 | hse.HseClosure | COMPLETED — CI #597 GREEN | Accepted Batch 17 implementation passed full Java 21/PostgreSQL/OpenAPI CI on cfb3681ef1c79b4416336a3533cbc0599b4fd6b2. |
| HMR-097 | HMSR-114 | hse.HseCorrectivePreventiveAction | COMPLETED — CI #597 GREEN | Accepted Batch 17 implementation passed full Java 21/PostgreSQL/OpenAPI CI on cfb3681ef1c79b4416336a3533cbc0599b4fd6b2. |
| HMR-098 | HMSR-115 | integrity.IntegrityCase | COMPLETED — CI #598 GREEN | Accepted IC-PREREQ-01 implementation passed full production CI on 863d113fbee88f71ff7e2c3b593b415e5b70d07a. |
| HMR-099 | HMSR-116 | workflow.WorkflowStateHistory | COMPLETED — CI #581 GREEN | Mandatory status/actor display evidence fails fast. History persistence inserts and flushes without upsert; optional task/step/action/reason references are checked for instance/definition and action evidence coherence. Database guards prohibit update/delete/truncate. Four focused behavior checks passed with temporary stubs; ten PostgreSQL/Hibernate cases added for CI, not locally executed. |
| HMR-100 | HMSR-117 | alarm.Alarm | COMPLETED — CI #602 GREEN | Accepted ALRM-PREREQ-01; independent Batch 20 lifecycle commit with focused and PostgreSQL/Spring-JPA sources; Java 21 clean verify and OpenAPI compatibility passed at repaired head 74ef372c82a790cdad63a77038fd60afb0de9c44. |
| HMR-101 | HMSR-118 | audit.AuditAccessRecord | COMPLETED — CI #584 GREEN | Access records use persist/flush without merge; populated optional AuditEvent/export IDs must exist. Forward V20261007_008 supplies nullable local FKs and UPDATE/DELETE denial. Three focused and four added PostgreSQL/JPA/concurrency/orphan checks prepared; local focused Maven blocked by uncached Boot parent; CI #584 passed. |
| HMR-102 | HMSR-119 | audit.AuditBeforeAfterValue | COMPLETED — CI #584 GREEN | Required fieldPath, masked/sensitive raw-text exclusion, optional exact active MASK_REASON and existing parent event enforced. Hash-only evidence and changed=false remain legal. Persist/flush insertion plus V20261007_009 local FKs/checks/UPDATE/DELETE denial preserve immutable rows. Six focused and five added PostgreSQL/JPA/concurrency/legacy checks prepared. Temporary API type compilation passed; local focused Maven blocked by uncached Boot parent; CI #584 passed. |
| HMR-103 | HMSR-120 | monitoring.PlanActualDeviation | COMPLETED — CI #600 PASSED | Independent semantic commit 74ea302432eff8434466d7162134b99923ce3161; live/direct-save owner/evaluation validation and forward 021 verified by final repaired-head CI. |
| HMR-104 | HMSR-121 | alarm.AlarmAcknowledgement | COMPLETED — CI #602 GREEN | Accepted ALRM-PREREQ-01; independent Batch 20 lifecycle commit with focused and PostgreSQL/Spring-JPA sources; Java 21 clean verify and OpenAPI compatibility passed at repaired head 74ef372c82a790cdad63a77038fd60afb0de9c44. |
| HMR-105 | HMSR-122 | alarm.AlarmClosure | COMPLETED — CI #602 GREEN | Accepted ALRM-PREREQ-01; independent Batch 20 lifecycle commit with focused and PostgreSQL/Spring-JPA sources; Java 21 clean verify and OpenAPI compatibility passed at repaired head 74ef372c82a790cdad63a77038fd60afb0de9c44. |
| HMR-106 | HMSR-123 | alarm.AlarmShelving | COMPLETED — CI #602 GREEN | Accepted ALRM-PREREQ-01; independent Batch 20 lifecycle commit with focused and PostgreSQL/Spring-JPA sources; Java 21 clean verify and OpenAPI compatibility passed at repaired head 74ef372c82a790cdad63a77038fd60afb0de9c44. |

## Reconciliation Totals

- legacy HMR records parsed: **104**;
- HMR-005 corrected from stale planned status to **COMPLETED**;
- HMR-009 confirmed **COMPLETED** and removed as a carry-over blocker;
- HMR-050..106 evaluated: **57**;
- HMR-050..106 **STILL REQUIRED**: **0**;
- HMR-050..106 **BLOCKED**: **0**;
- HMR-050..106 **IMPLEMENTED during HPR-P2-008**: **57**, all CI-confirmed through #604, zero pending;
- HMR-050..106 **SUPERSEDED**: **0**;
- HMR-054 completed; repaired CI #575 is green;
- HMR-080 completed with repaired-head CI #604 passed; HMR-055 prerequisite resolved and implemented in Batch 7.

## HPR-P2-008 Progress

- HMR-050 — **COMPLETED** at the first HPR-P2-008 execution step.
- Batch 7 **COMPLETED — CI #581 GREEN**: HMR-055, 061, 066, 081, 099; exact repaired head ec63af0414d7fa85b9200d4bd181ac799bd072ed. Batch 8 preflight split below; no Planning implementation claimed.
- Current remaining: **0 STILL REQUIRED, 0 BLOCKED, 0 pending CI**; all 57 implementations are CI-confirmed through #604 on 617c2eec812e3a5734957ee9fa0360f6f5613032. NOM-OWNER-01/NOM-EXEC-01 and HMR-080 are resolved. HPR-P2-008 is COMPLETED following the owner's closure authorization; HPR-P2-009 has transferred the lasting decisions into canonical domain/module docs. HPR-P2-010 remains PENDING.

- HMR-051 — **COMPLETED**: Topology and optional Organization references validated on every case save; snapshot preserved; no migration because primary-candidate FK already exists; owner contract and architecture export added.

- HMR-059 — **COMPLETED**: Optional candidate validated before save and protected by V20261006_001 nullable same-module FK with fail-closed orphan preflight; no case-primary equality rule.

- HMR-073 — **COMPLETED**: Assignment service resolves same-module OrganizationUnit and rejects missing or non-ACTIVE units before save; existing employee/unit/position FKs retained; no migration.

- HMR-074 — **COMPLETED**: JPA responsibility_assignment_id is mandatory; V20261006_002 aborts on legacy null rows before SET NOT NULL; existing same-module FK preserved; real PostgreSQL focused tests added.

- HMR-075 — **COMPLETED**: JPA captured_by_employee_id is mandatory; V20261006_003 aborts on legacy null rows before SET NOT NULL; existing same-module FK preserved; real PostgreSQL focused tests added.

- HMR-076 — **COMPLETED**: JPA organization_unit_id is mandatory; V20261006_004 aborts on legacy null rows before SET NOT NULL; existing same-module FK preserved; real PostgreSQL focused tests added.

## HPR-P2-008 Execution Rule

HPR-P2-008 may execute only the `STILL REQUIRED` items, in dependency-safe order, with a fresh preflight before each mutation or registered batch.

Before each HMR:

1. re-read its HMSR obligations;
2. verify exact current source and architecture contracts;
3. verify the registered write scope still fits;
4. decide whether a migration is actually required from current schema;
5. verify SCC/dependency ordering;
6. verify owner-controlled contracts exist;
7. preserve one HMR semantic test/commit boundary;
8. do not restart HMR-005, HMR-009 or any other completed HMR without concrete regression evidence.

HMR-080's owner-contract prerequisite and accepted solo execution are resolved by
NOM-OWNER-01/NOM-EXEC-01 and CI #604. No remaining HMR in this register may be
restarted without concrete regression evidence. HPR-P2-008 closure is owner-authorized
and recorded below; HPR-P2-009 permanent transfer is recorded in its accepted execution section. No completed HMR is reopened.

## Legacy Preservation

`docs/roadmap/model-semantic-remediation.md` is intentionally not rewritten by HPR-P2-007. Its historical statuses, batches and execution notes remain provenance. Where it conflicts with this exact-source reconciliation, this canonical document and the Ultimate Roadmap govern P2 execution.

## Batch 1 validation disposition

User-authorized Batch 1 implements HMR-051, HMR-059 and HMR-073..076 in six
independent semantic commits. Baseline full CI #568 passed at `7068b44`.
The six focused semantic test classes plus PostgreSQL migration regression tests
are present. Local Maven execution cannot resolve Spring Boot 4.1.1 from Maven
Central (DNS failure); local Java is 17, not the required 21, and Docker is absent.
No local Java/PostgreSQL test success is claimed. The final batch full CI on Java 21
is the integration and migration gate; if red, repair this batch before proceeding.
Four new migrations follow V20261005_001 and preserve all pre-existing migrations.

## Batch 1 repaired exact-head gate

Full CI #570 / run `37476746534` succeeded on
`201e21b16a6bef5c2a346d4107c912c0ed237531`. CI #569 exposed one missing export
in ForensicRemediationClosureTest; the narrow repair added the same approved
Organization LeakDetection contract export already present in ArchitectureGuardrailTest.
This green head is the Batch 2 baseline.

## Batch 2 execution and validation disposition

HMR-052 and HMR-060 implemented in independent semantic commits. The final
Batch 2 CI is pending; task completion here records implementation, not a passed CI run.
No existing migration was edited. V20261006_005 and V20261006_006 follow the
Batch 1 migrations and fail closed on incompatible legacy rows without rewriting evidence.

Validation performed locally:

- `git diff --check`: passed.
- Java compiler syntax parsing: all eight changed/new Java files passed; no dependency/type-check claim.
- Standalone compilation/execution of the actual NotificationDeliveryAttempt domain:
  all seven statuses enforce the permanent-failure automatic-retry rule.
- Maven compile, focused four-class test run, full test and clean verify attempted
  using `bash ./mvnw -o -q ...`: all stopped before build/test execution because
  Spring Boot 4.1.1 parent POM is absent from the local cache. The available Java is 17;
  required Java 21 and PostgreSQL/Testcontainers verification remain for GitHub CI.

Rendering-variable contract: the selected version's JSON object may contain a
`required` array of nonblank variable names. Each requires a matching, nonblank
message-owned value snapshot before READY/SCHEDULED/dispatch progression.
Malformed required contracts fail closed. This checks required input presence;
it is not a general JSON Schema type validator or a template renderer.
The current create API has no variable payload: templates with required inputs
need DRAFT staging and persisted variables before promotion. The create path
rejects them rather than sending incomplete messages. Template-free messages
remain supported. No new rendering endpoint, retry workflow, attempt-number,
provider, cross-module or OT-control policy is introduced.

Next registered proposed scope: HMR-053 alone, only after the final Batch 2 head is green.
Per owner instruction, stop once final-head CI has started; resume on `next` or `fail`.

## Batch 2 CI #571 corrective follow-up

CI #571 / run `37479293974` on `657d5d7cf7803e62f820ea9dfde5cf308ecb7640`
ran 639 tests: 0 assertion failures, 1 error, 0 skipped. PostgreSQL reported
SQLSTATE `42702` in NotificationMessageIntegrityMigrationTest: the required-variable
lookup used ambiguous `message_id` instead of the function-qualified parameter.

V20261006_007 replaces only hmr052_message_valid with the qualified parameter;
all previously published migrations and signatures remain unchanged. The PostgreSQL
regression reproduces the original error, installs the forward repair, proves promotion
with the matching input succeeds, and proves another message's inputs cannot satisfy it.
Existing message integrity tests now apply both the original and corrective migrations.

Local Java syntax and migration-preservation checks passed. Focused Maven execution
was attempted but stopped at the uncached Spring Boot 4.1.1 parent POM; no local
PostgreSQL or full-suite success is claimed. Replacement exact-head CI remains pending.
Stop once replacement CI starts, as instructed by the owner.

## HMR-053 execution — 2026-10-06

Baseline main `d61bec9eeee6b2c3c3a9d5ea887b28b664355753` passed full CI #572 /
run `37480926311`, closing the Batch 2 regression gate. The owner resumed with `next`.
HMSR-062 was re-read against current Java/JPA/schema before this solo execution.

Implemented TrustTelemetryReadingUseCase and TelemetryTrustEvidencePort with a
Telemetry-owned application service and infrastructure evidence loader. Trust policy
accepts MEDIUM, HIGH and CERTIFIED (the DDD's trusted-level examples), always retains
the assessment's level, and requires PASSED, ACTIVE point and active QUALITY_CODE.
The catalog currently exposes active as its lifecycle eligibility flag.
The effective unit comes from the point; ingestion batch and raw values/timestamp
come from the source reading. Optional populated unit/batch references must exist.

Applicable bindings use active=true and [validFrom, validTo) at the operation's trust
instant. A single applicable binding is selected automatically; multiple roles require
an explicit applicable binding ID; no binding preserves an empty topology snapshot.
Source/assessment/point/catalog locks plus point-based binding mutation serialization
protect capture. The downstream record preserves captured snapshots on later rebind
or point retirement. New code introduces no Topology FK or arbitrary topology tuple.
V20261006_008 adds composite provenance and optional same-module FKs, a direct-write
creation guard, and rejects rewriting captured trusted evidence. Preflight validates
historical identity/trust/quality family; it does not pretend current point lifecycle or
current bindings prove a historical capture. Inconsistent provenance aborts migration.

Validation:

- Java syntax: all eight new/changed Java files passed compiler parsing.
- Actual application/domain trust operation compiled and executed in a standalone
  Java 17 harness with annotation stubs and in-memory ports: every trust level,
  assessment status, point lifecycle and explicit binding selection passed.
  This does not validate Spring transactions or JPA/PostgreSQL behavior.
- `git diff --check`, published migration preservation and unique version ordering passed.
- Maven compile, focused two-class tests, full tests and clean verify attempted via
  `bash ./mvnw -o -q ...`; all stopped at uncached Spring Boot 4.1.1 parent POM.
  PostgreSQL migration tests are present but await Java 21/Testcontainers CI.

Implementation recorded as completed; exact-head CI remains pending. Confirm CI
started, then stop until the owner sends `next` or `fail`. Next proposed solo task
is HMR-054 after green CI, with live Party-owner-contract revalidation first.

## HMR-054 execution — 2026-10-06

Baseline main `f676e278357ac7bcf2bc8bf55830b16cb324bd73` passed full CI #573 /
run `37483724317`. Owner resumed with `next`. Source HMSR-063 was re-read;
existing TopologyPartyReferenceContract/query service and both architecture export
allowlists resolve the legacy Party-owner-contract blocker.

Equipment.equipmentTypeId and the Topology-owned EquipmentType identity/code now
provide the single active classification source. EquipmentKind and both mapped kind
properties were removed; lifecycle enums remain. V20261006_009 renames the two
legacy kind columns to legacy_equipment_kind, preserves every stored string, and
allows null for new catalog-backed rows. These columns are unmapped historical
metadata. The migration aborts on conflicting equipment/type classification rather
than choosing a new classification; owner reconciliation is required for such data.
Existing type references remain protected, and nullable facility/node/pipeline-segment
attachments gain same-module FKs with DELETE RESTRICT. No attachment cardinality
rule is introduced. Manufacturer identities are checked through Party's existing
existence contract on every Equipment save; code/name snapshots are preserved.
No cross-module FK, Party lifecycle rule or equipment lifecycle matrix is added.

Validation:

- Compiler syntax parsing: eight changed/new Java files passed.
- Actual Equipment domain compiled/executed in a standalone Java 17 harness:
  catalog type identity works without EquipmentKind; snapshots remain intact.
- `git diff --check`, cross-module export scan, migration preservation and version ordering passed.
- Maven compile, focused three-class tests, full tests and clean verify attempted via
  `bash ./mvnw -o -q ...`; stopped at uncached Spring Boot 4.1.1 parent POM.
  Real PostgreSQL regression tests are added; Java 21 integration remains for CI.

Implementation is recorded as completed; final exact-head CI is pending. Confirm CI
started and stop until `next` or `fail`. Next proposed scope is HMR-063 plus HMR-086..089,
subject to fresh admission/dependency checks; HMR-080 remains blocked.

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

## Batch 5 implementation and validation disposition — 2026-10-06

HMR-063, HMR-086, HMR-087, HMR-088 and HMR-089 implemented in five independent
semantic commits, preceded by the explicit Batch 5 admission. Shared final CI pending.
Current totals: 16 completed, 40 still required, one blocked (HMR-080) in HMR-050..106.
The attached sequence now has 5/20 batches and 15/55 HMR implementations complete.

Validation actually performed:

- `git diff 89a7c3b --check`: passed.
- Java compiler syntax parsing: 34 changed Java sources passed.
- Actual Identity domain plus AuthenticationSessionLifecycleApplicationService compiled
  with Java 17's compiler module and temporary Spring annotation stubs; actual-domain
  invalid-value and session external-identity/protocol/logout/expiry/activity/idempotency
  behavior checks passed. This is not full Java 21/Spring validation.
- Actual Identity JPA entities and mapper compiled with temporary Jakarta annotation
  stubs; delegation reason and session protocol/end mapper round trips passed. This does
  not establish Hibernate or PostgreSQL correctness.
- `bash ./mvnw -q -DskipTests compile`: blocked before compilation by Maven Central DNS
  failure resolving the uncached Spring Boot 4.1.1 parent POM.
- Focused nine-class tests, full test and clean verify attempted with offline Maven:
  blocked before execution by the same uncached parent. Java 21 and Docker are absent.
  No JUnit/PostgreSQL/full-build pass is claimed locally.

Nine dedicated semantic/migration test classes are present for the final GitHub gate.
All four migrations are forward additions after V20261006_009; published migrations
are unchanged. V20261006_011 and V20261006_012 permit operator-supplied columns for
explicit legacy reconciliation before replay; they never fabricate delegation reasons,
end dates, session protocol or historical endedAt. Incompatible existing delegation,
session and direct-permission rows will intentionally block rollout until reconciled.

Next: HMR-085, matching Batch 6 of the supplied plan, only on owner `next` after the
Batch 5 gate. Observe final-head CI started, then stop; await `next` or `fail`.

## Batch 6 / HMR-085 preflight — 2026-10-06

Baseline main: `925feec7c022a3603afbb4c2fcff47010a64323e`.
CI #576 (run 37498438487) and documentation validation #67 both succeeded.
Owner `next` selected HMR-085 / HMSR-100. No production mutation performed.

### Disposition: BLOCKED — authorization evaluation contract prerequisite

This is a new preflight finding, not a reopened completed task. Current source proves:

- `IdentityAuthorizationApplicationService.evaluate` unconditionally denies with
  NO_GRANT_MATCHED and unconditionally saves the resulting decision.
- `AuthorizationPolicyEvaluator` constructs permit/deny records but performs no
  policy-expression or graph evaluation.
- `EvaluatePermissionQuery` and `EvaluatePermissionRequest` expose user, permission,
  resource identity and neutral scope only. They have no verified assertion/context input.
- The OIDC converter validates issuer/subject and resolves an ExternalIdentity, but
  creates its principal from internally calculated permissions and does not preserve
  the verified group/role/permission claims needed for assertion-only mappings.
- Identity DDD sections 6.12 and 7.3 define JSON policy expressions and evaluation order,
  but do not define a JSON grammar, attribute resolution, CONSTRAIN/obligation execution
  contract, or policy conflict handling. Current production source has no expression evaluator.
- `IdentityModuleConfiguration.authorizationDecisionPersistenceEnabled` exists in a
  framework-neutral record but is not bound into this evaluation path.

The registered legacy HMR-085 write list omits the actual defective application
service, evidence-query adapter, trusted-assertion transport, expression evaluator,
configuration binding and their regression tests. Merely rewriting the decision
record cannot discharge HMSR-100's three obligations. AGENTS.md section 3.2 rule 9
requires splitting an unregistered prerequisite before mutating the HMR.

### Proposed concrete prerequisite contract — TARGET, not implemented

Recommended implementation is an Identity-owned, constrained JSON evaluator, with no
SpEL, scripts, SQL expressions or dynamic class access. This is a proposal and grants
no runtime authority until admitted/implemented.

1. Resolve an ACTIVE, unlocked User and ACTIVE requested Permission from Identity.
   Unknown/disabled subjects or permissions fail closed with truthful reason codes.
2. Introduce an application-owned authorization evidence port. A transactional JPA
   adapter resolves direct permission grants, direct roles, group memberships/roles,
   role-permission grants, active policy versions/rules and required subject attributes.
   Capture one evaluation instant; use `[validFrom, validTo)` and exact neutral scope
   identity. Null/GLOBAL grant scope covers operations; a scoped grant cannot authorize
   a different scope/reference. Snapshot labels never establish identity.
3. Direct explicit DENY overrides every permit path. Evaluate direct permissions,
   direct roles and inherited group roles in DDD order, preserving every participating
   grant/link ID. Require ACTIVE role/group/membership/grant state and valid intervals.
4. External assertions may participate only when authenticated/validated infrastructure
   supplies their provider, exact ExternalIdentity, subject and allowlisted claim values,
   bound to the evaluated user. Never accept claim authority from EvaluatePermissionRequest
   or treat stored external attribute snapshots as current login assertions.
   Require ACTIVE provider, LINKED external identity and eligible mapping mode.
   SYNC_MEMBERSHIP uses actual synchronized local membership; ASSERTION_ONLY and
   DIRECT_GRANT use verified assertions. MANUAL_APPROVAL and REQUIRES_LOCAL_APPROVAL
   do not confer authority without explicit approval evidence; DISABLED never confers it.
5. JSON expression version 1 uses bounded operators: `eq`, `in`, `exists`, `all`, `any`,
   `not`. Each predicate identifies a permitted namespace/attribute and a typed literal;
   compound expressions contain child predicates. Reject unknown fields/operators,
   excessive depth/size, missing required attributes and type mismatch. Do not execute
   arbitrary expressions. Only repository-owned subject attributes and explicitly
   trusted resource/context evidence are eligible; absent evidence is INDETERMINATE.
6. Evaluate eligible policy rules in deterministic priority/ID order. Explicit policy
   DENY overrides permits; unknown/unsupported conditions or unresolved obligations
   produce INDETERMINATE rather than silently permitting. CONSTRAIN can restrict an
   existing grant; its obligation cannot be declared satisfied without executor evidence.
   Policy-only PERMIT requires all relevant constraints/obligations to be resolved.
7. Construct a decision with reasonCode/reasonMessage and deterministic JSON evidence
   for matched grant IDs, policy-rule IDs and external mapping/claim provenance.
   Preserve neutral resource references and omit bearer tokens/secrets/raw sensitive claims.
8. Expose the existing persistence flag through an infrastructure configuration adapter,
   retaining its current default `true`. Evaluation is identical with persistence off;
   only the save is skipped. No new high-risk-operation taxonomy is invented.

Required scope admission: IdentityAuthorizationApplicationService, application evidence
and settings ports, JPA evidence adapter, constrained expression evaluator, trusted
assertion model and authentication adapters, runtime configuration binding, focused
AuthorizationDecisionSemanticRemediationTest plus graph/mapping/policy PostgreSQL and
assertion-spoofing regression tests. No cross-module FK or destructive migration.

Validation of this preflight: source/DDD/allowlist inspection and `git diff --check`.
No application tests or production-implementation success claimed. HMR-085 remains
blocked and selected; do not automatically advance to Batch 7. The prerequisite
contract must be settled before implementing the complete HMR-085 scope.

## Batch 6 / HMR-085 implementation — 2026-10-06

Owner `Next` accepted the concrete prerequisite contract. Baseline docs-only main
341a79a passed docs CI #68; unchanged production source passed full CI #576
at 925feec7. Previous preflight block is resolved, with scope admitted before mutation.

HMSR-100 closure:
- Replaced unconditional denial with ACTIVE/unlocked user and permission eligibility,
  direct permissions/roles, group-role inheritance, verified external group/role/permission
  mapping paths, exact scope identity, half-open validity windows and ABAC rules.
- Explicit DENY dominates grants and policies. Ineligible approval-dependent mappings
  do not grant authority. SYNC_MEMBERSHIP requires an actual eligible local membership
  and active provider/mapping; stored EXTERNAL_ASSERTION memberships never stand in
  for current claims. LDAP supports synchronized local evidence; its existing verifier
  supplies no live group claims, so assertion-only LDAP authority is not manufactured.
- Added a self-bound OIDC evaluation endpoint on the existing decoder/converter chain.
  Only configured claim paths are captured; the user/provider/external identity/subject
  and assertion expiry are rechecked against current Identity state. Claims in request
  details, ordinary tokens or stored snapshots never confer mapped authority.
- Recorded sorted JSON grant-chain and policy-rule IDs, external mapping provenance
  and value hashes. No bearer credentials or raw sensitive claim values are persisted.
  JPA evidence columns use Hibernate JSON binding; no schema change required.
- Bound `hidra.identity.authorization-decision-persistence-enabled` (default true).
  Disabling it skips saving while retaining identical evaluation semantics.

JSON grammar and policy behavior are specified in Identity DDD below. Rule priority
then ID determines evaluation order; deny precedence is independent of that order.
CONSTRAIN uses subject/resource/action selectors and a required matching context
condition to restrict existing authority. Unsupported obligations remain INDETERMINATE;
no obligation executor or high-risk-operation taxonomy is invented. Repository-owned
subject attributes and neutral requested identifiers are available; absent live resource
state/context evidence cannot be supplied by callers and remains INDETERMINATE.

Validation:
- `bash ./mvnw -q -DskipTests compile`: blocked before source compilation, Maven Central
  DNS failure resolving Spring Boot parent 4.1.1.
- Focused semantic/PostgreSQL/trust commands, complete `test`, and `clean verify`
  attempted with `-o`: blocked by the same uncached parent. JDK is 17, Docker absent.
- All changed Java files parsed with the JDK compiler. Selected actual production
  classes compiled with temporary framework API stubs; 10 semantic behavior checks
  executed successfully using temporary annotation/assertion stubs. These are not
  Maven/JUnit, framework integration or PostgreSQL success claims.
- Added 10 semantic cases, 9 PostgreSQL graph/JSON cases, 3 assertion-trust cases,
  2 OIDC API cases and existing constructor/OpenAPI regression updates for CI.
- `git diff --check`: passed. Final-head GitHub CI pending at commit preparation.

Current totals: **17 implemented, 39 still required, one blocked (HMR-080)**.
Next owner-selected scope is attached Batch 7 workflow execution; it requires green
Batch 6 CI and a fresh scope/dependency admission. Do not execute automatically.

### HMR-085 CI #577 repair — 2026-10-06

Run 37518264529 at 03db23c4 failed in Maven testCompile. Production
compilation succeeded, but tests were not executed. The OIDC fixture constructor
update also passed SpringAuthorizationContextAdapter into the unchanged LDAP
provider constructor in HidraAuthorizationOwnershipTest. Removed that extra
LDAP argument, retaining the required OIDC adapter. Production semantics unchanged.

All test sources parsed with the JDK compiler; four LDAP/OIDC constructor calls
checked against actual production declaration arities. git diff --check passed.
Focused ownership/OIDC Maven tests and clean verify attempted with -o; blocked
by uncached Spring Boot parent 4.1.1 (local Maven Central DNS remains unavailable).
These syntax/arity checks are not JUnit success claims. Replacement final-head CI
pending at preparation; Batch 7 remains gated on green Batch 6 verification.

### HMR-085 CI #578 historical OpenAPI build repair — 2026-10-06

At fb47ae27, CI run 37518717960 passed Repository verification
(`./mvnw -B -q clean verify`) and published the current-head OpenAPI document.
It failed generating the comparison base at previous commit 03db23c4 because
`-DskipTests package` still compiled the known broken historical LDAP test fixture.
This is a base-build failure, not a failure of the repaired head's tests.

Changed only isolated historical packaging to `-Dmaven.test.skip=true package`.
Current-head clean verify still runs all tests; exact previous-SHA worktree
selection and OpenAPI compatibility enforcement remain required. No model changes.
Workflow YAML/bash syntax and preserved-gate checks passed; git diff --check passed.
Local Maven packaging was attempted with -o and remains blocked by uncached
Boot parent 4.1.1. Replacement exact-head CI pending at preparation. Batch 7
remains gated until the complete CI workflow succeeds.


## Batch 7 preflight — workflow execution / 2026-10-06

Owner `Next` selects attached Batch 7: HMR-055, HMR-061, HMR-066, HMR-081,
HMR-099, in that order. Baseline main b6cdb1e2640be5e1990161f2b4d8e61bf2fd1156
is green: full CI #579 / run 37520044638 and documentation CI #71 /
run 37520044656 succeeded. The five HMSR source reviews and corresponding HMR
obligations were recovered independently against current code. No task is implemented
by this preflight; 061, 066, 081 and 099 remain STILL REQUIRED.

### WF-PREREQ-01 — PROPOSED, NOT ADMITTED

HMR-055 / HMSR-064 is blocked before production mutation. The Workflow DDD lists
nine catalog families, none for workflow purpose, while DefinitionTargetBinding
requires workflowPurposeId. HMSR-064 explicitly prohibits inventing its family.
WorkflowApplicationService.startWorkflowInstance currently copies caller-supplied
configuration, target and actor data without binding/version/owner validation.
WorkflowEligibilityLookupPort, WorkflowIdentitySnapshotPort and
WorkflowOrganizationSnapshotPort are availability placeholders, not authoritative
owner lookups. NoopWorkflowExternalReferenceResolver returns true and is not wired
into starts. Identity exports no Workflow actor eligibility contract; Organization's
existing consumer-specific reference contracts do not define Workflow assignment
or pool membership authority. Cross-module approval mutation contracts do not supply
a generic owner-controlled target lookup. A username/display/role snapshot cannot
stand in for live authority.

AGENTS.md section 3.2 rule 9 requires an owner-contract gap to be split out and the
HMR stopped before mutation. HMR-055's legacy file allowlist also omits the actual
WorkflowApplicationService and required owner contracts. This is a semantic contract
and admission prerequisite, not a Maven or CI failure.

Proposed resolution, requiring explicit roadmap admission before implementation:

1. Define WORKFLOW_PURPOSE as the controlled catalog family and require an active
   purpose for new starts and active bindings. This is a proposal, not an existing
   taxonomy. Reconcile legacy null/wrong-family purposes with an explicit data plan;
   do not silently retag generic catalog rows or seed invented business values.
2. Define a fail-closed target resolver registry keyed by module and governed target
   type code. Each supported target owner must export an explicit lookup contract
   returning existence/eligibility and neutral snapshots. Missing or ambiguous
   resolvers deny starts; no cross-module FK, private entity import or no-op success.
   Inventory supported owner/type combinations before admitting their exact files.
3. Define an Identity-owned Workflow actor contract resolving current active,
   unlocked actors and canonical snapshots, binding execution to the authenticated
   actor. Use existing governed permission policies; snapshots and caller actor IDs
   must not grant authority. Define an Organization-owned assignment contract for
   current unit availability and eligible pool membership. Specify claim/delegation
   policy explicitly; do not invent permission names or infer authority from labels.
4. HMR-055 must validate ACTIVE definition/version, exact active target/purpose
   binding, active WORKFLOW_TARGET_TYPE, and a current step in that definition.
   Preserve neutral external target IDs. Enforce one nonterminal instance per
   target/module/type/purpose tuple with a database uniqueness guard, including
   concurrent starts. Do not invent an initial-step selection rule.
5. HMR-061 must enforce same-definition distinct steps and unique configured
   (definition, from-step, decision). Unsupported expressions/callbacks must not
   become executable configuration; preserve fail-closed runtime behavior without
   adding an ungoverned interpreter or callback mechanism.
6. HMR-066 must validate actionable assignment through live owner contracts,
   active WORKFLOW_PRIORITY/WORKFLOW_ASSIGNMENT_MODE families, paired actor/time
   fields and chronology, plus terminal immutability. Remove username-snapshot
   authority from both transition execution and available-action queries. Explicit
   pooled assignment policy must govern any unassigned task creation.
7. HMR-081 must route decision actions through configured transitions; generic
   recording must not bypass decision authority. Validate optional supplied task
   ownership, required reason/comment evidence and actor display snapshots. Allocate
   action sequence under the instance lock with database uniqueness, never from a
   caller. Resolve actors/units through their owners without cross-module FKs.
8. HMR-099 must reject blank mandatory evidence and insert history without upsert.
   Enforce append-only persistence and validate supplied optional task/step/action/
   reason coherence; keep optional references optional. Do not introduce a new
   mandatory history producer or fabricated lifecycle events.

Scope admission must include actual application/transition/query services, catalog
and binding repository interfaces/adapters, explicit public owner contracts and
adapters, architecture exports/guardrails, model/API/mapping changes and focused
semantic/PostgreSQL concurrency/immutability tests. New migrations must follow the
current maximum V20261006_013; never edit published migrations or use legacy
backdated filenames. Any HMR-099 database trigger migration needs explicit added
scope because its legacy allowlist authorizes no migration. Preserve one exact-message
semantic commit per HMR and the owner's selected five-task batch exception.

The prerequisite remains PROPOSED. No production scope, taxonomy, resolver support
or migration is admitted by this documentation commit. Next selected work remains
WF-PREREQ-01/HMR-055; do not skip to 061 or Batch 8. After contract resolution,
perform fresh dependency/file admission and exact-head green CI verification.

Validation: documentation workflow's UTF-8/nonempty/conflict checks and
`git diff --check` passed. No Java, database or workflow behavior changed, so no new
Maven test result is claimed. Documentation-only CI trigger pending at preparation.
Current totals: **17 implemented, 38 still required, two blocked (080 and 055)**.

### Batch 7 HMR-055 implementation

Owner-bound starts enforce active definition/version and exact binding, governed purpose/type, current-step coherence and owner target/actor snapshots; nonterminal uniqueness and same-definition/version database guards. Planning target registry denies unsupported/ambiguous owners. Eight focused behavior checks passed with temporary stubs; local Maven blocked by uncached parent, not a JUnit/PostgreSQL pass.
Validation is recorded at the final Batch 7 disposition; CI pending.

### Batch 7 HMR-061 implementation

Distinct same-definition steps and unique source decisions are protected in configuration persistence and PostgreSQL. Unsupported conditions/callbacks/COMMENT cannot attach to ACTIVE definitions or survive activation; runtime remains fail closed. Three focused behavior checks passed with temporary stubs; PostgreSQL validation pending CI.
Validation is recorded at the final Batch 7 disposition; CI pending.

### Batch 7 HMR-066 implementation

Actionable assignment, live actor/unit membership, catalog eligibility, actor/time pairs and chronology are enforced. Terminal task evidence is immutable; generic creation is starter-bound, missing next-step rules fail closed, and execution/query paths no longer authorize by username snapshots. Seven focused behavior checks passed with temporary stubs; existing transition fixtures updated for new owner dependencies.
Validation is recorded at the final Batch 7 disposition; CI pending.

### Batch 7 HMR-081 implementation

Generic recording permits comments only; configured transitions exclusively produce decisions using live Identity authority. Optional task ownership and conditional evidence are enforced; canonical actor snapshots and server-owned locked sequences replace caller evidence. Action persistence is insert-only with unique monotonic sequence and immutable database guards. Five focused behavior checks passed with temporary stubs; existing permission regression fixture updated.
Validation is recorded at the final Batch 7 disposition; CI pending.

### Batch 7 HMR-099 implementation

Mandatory status/actor display evidence fails fast. History persistence inserts and flushes without upsert; optional task/step/action/reason references are checked for instance/definition and action evidence coherence. Database guards prohibit update/delete/truncate. Four focused behavior checks passed with temporary stubs; ten PostgreSQL/Hibernate cases added for CI, not locally executed.
Validation is recorded at the final Batch 7 disposition; CI pending.


## Batch 7 final implementation and validation — 2026-10-06

Owner `Go ahead` accepts the preflight proposal. Canonical admission resolves
WF-PREREQ-01 with explicit WORKFLOW_PURPOSE, live Identity/Organization contracts,
a fail-closed owner target registry and exact added file/migration scopes. Unsupported
operation-reference targets remain denied; no owners, taxonomies or legacy evidence
are fabricated. Five semantic commits independently complete 055, 061, 066, 081, 099
in owner-selected order. Their implementation notes above are current disposition;
prior preflight BLOCKED/PROPOSED statements remain historical evidence.

Forward migrations 014..018 are additive and ordered after the previous maximum.
Legacy invalid purposes, duplicate nonterminal instances/decisions/sequences, missing
assignments, incoherent references or evidence cause migration failure for owner
reconciliation. No published migration was changed. No external FK was introduced.

Validation performed:
- `bash ./mvnw -q -DskipTests compile`: failed resolving Spring Boot parent 4.1.1;
  Maven Central DNS `Temporary failure in name resolution`, before source compilation.
- Offline focused tests (five semantic classes, WorkflowExecutionPostgresTest,
  WorkflowTransitionApplicationServiceTest, ArchitectureGuardrailTest), complete `test`
  and `clean verify`: attempted, blocked by the same uncached parent.
- Host Java 17, no javac executable, no Docker or PostgreSQL server. JDK source-launch
  compiler compiled 273 actual source/temporary API units, including owner implementations,
  persistence adapters, changed transition fixture and new PostgreSQL test sources.
  Java 21 List.getFirst was substituted only in scratch copies for host compilation.
- 27 dedicated behavior checks executed successfully with temporary annotation/assertion
  APIs. These are not Maven/JUnit, framework integration or PostgreSQL success claims.
- Ten PostgreSQL/Hibernate cases registered for CI: competing nonterminal starts,
  contiguous concurrent action sequences, action immutability, transition composition/
  decision uniqueness, unsupported activation, task pairs/catalogs/terminal immutability,
  optional immutable history, incoherent history, JPA history overwrite denial, and
  legacy purpose reconciliation failure without retagging.
- Canonical documentation UTF-8/nonempty/conflict checks, Java source syntax parsing,
  exact admitted file-scope checks and `git diff --check`: passed.

Final-head GitHub production/documentation CI trigger pending at preparation. Publish
all chained commits atomically and stop after observing trigger; do not wait for CI
completion or execute Batch 8 automatically. Current totals: **22 implemented,
34 still required, one blocked (HMR-080)**.


### Batch 7 CI #580 forensic export repair — 2026-10-07

Run 37525353444 at f6d833c failed Repository verification with **736 tests,
one failure, zero errors, zero skipped**. The sole failure is the source-scanning
ForensicRemediationClosureTest.crossModulePrivateImportsRemainClosed. Its export
registry omitted the three admitted public packages:
- `workflow.application.contract.target`
- `identity.application.contract.workflow`
- `organization.application.contract.workflow`

ArchitectureGuardrailTest already registers these explicit HMR-055 boundaries.
Added the same exact packages to the forensic registry. Its import scanner,
private-package denial and assertions remain intact. Production code and migrations
are unchanged. All other tests, including the new Workflow semantic/PostgreSQL
cases, reported no failures in CI #580; complete verification is still not green.
OpenAPI publication and compatibility gates were skipped after the test failure.

All five actual forensic source-scanning methods compiled and passed using the JDK
source-launch compiler with temporary JUnit annotation/assertion APIs. Both export
registries now match at 28 exact packages. This is not a Maven/JUnit pass claim.
Focused ForensicRemediationClosureTest/ArchitectureGuardrailTest and clean verify
attempted with `bash ./mvnw -o -q`; both blocked before compilation by the uncached
Spring Boot 4.1.1 parent. Documentation UTF-8/nonempty/conflict and git diff checks
passed. Exact replacement CI trigger pending at commit preparation; stop after
observing the trigger. Batch 8 remains gated on successful complete replacement CI.

## Batch 8 preflight and concrete owner-contract proposal — 2026-10-07

Selected scope: attached Batch 8, HMR-064/HMSR-076 and HMR-065/HMSR-077,
SCC-04. Exact production baseline ec63af0414d7fa85b9200d4bd181ac799bd072ed:
CI #581 (run 37570918095) and Documentation #74 (37570918094) completed
successfully. This closes Batch 7's CI-pending disposition; preceding entries are
historical preparation/repair records.

### Live evidence and split

HMR-064 remains required: PlanRevision does not require a positive revision number;
its repository save lacks reason-family/base-lineage enforcement; metadata update can
change an APPROVED revision. Published schema has no per-plan revision-number uniqueness
or nullable base-lineage FK. Its four registered obligations remain valid.

HMR-065 remains required: OperationalPlan normalizes required nameFr/scope type to
null; creation trusts incoming scope, creator and responsible-unit IDs. Existing owner
contracts are consumer-specific: Topology organization/risk contracts, Identity Workflow
actor contract, Organization Workflow unit contract. The registered Organization scope
query returns an Organization scope registry ID, not the native Topology scope ID required
by Planning, so substituting it would change identity meaning.

Planning DDD lists PIPELINE_SYSTEM, PIPELINE, FACILITY, REGION, NETWORK. Topology has
owner repositories/resolvers for the first three; no REGION/NETWORK owner entity/resolver
is evidenced. Existing ACTIVE responsibility assignability is not evidence that Planning
must require ACTIVE topology. A Planning-facing owner boundary and unsupported-scope
policy must be explicitly admitted. Catalog PLAN_TYPE and REVISION_REASON families are
already defined; no new taxonomy is needed.

PL-PREREQ-01 blocks HMR-065. AGENTS.md §3.2.9 and HMRB-030 require splitting before
mutation. HMR-064 stays STILL REQUIRED, held with the coordinated pair until a fresh
SCC-safe scope admission. No Java, SQL, tests, catalog rows or release version changed.
Current register totals: **22 implemented, 33 still required, two blocked (065, 080)**.

### Proposed decision for the next execution

1. Export `topology.application.contract.planning.PlanningTopologyScopeContract`:
   `Optional<Scope> resolve(String scopeType, String scopeId)`, with Scope containing
   canonical id/code/name. Topology-owned `PlanningTopologyScopeQueryService` reads its
   own repositories. Accept existing PIPELINE_SYSTEM, PIPELINE, FACILITY identities;
   deny missing/unknown scopes and REGION/NETWORK until their owners are implemented.
   Do not alias them to other objects or seed invented data. Validate existence without
   imposing a new ACTIVE-only Planning lifecycle rule. Planning stores owner display
   snapshots; snapshots never grant identity or eligibility.
2. Export `identity.application.contract.planning.PlanningCreatorContract`:
   `Optional<Creator> eligibleCreator(String actorId, Instant at)`, canonical Creator ID.
   Identity-owned `PlanningCreatorQueryService` delegates internally to the existing
   live ACTIVE/unlocked actor eligibility policy. Creation additionally binds creator
   to CurrentSecurityContext's authenticated principal; reject caller impersonation.
   No new Workflow permission/role requirement or Identity database FK.
3. Export `organization.application.contract.planning.PlanningResponsibleUnitContract`:
   `Optional<Unit> availableUnit(String unitId, Instant at)`, canonical Unit ID/name.
   Organization-owned `PlanningResponsibleUnitQueryAdapter` delegates internally to
   its existing ACTIVE, half-open validity policy. Validate only populated responsible
   units; do not require membership or promote the optional reference to mandatory.
4. HMR-064: positive revision number; concurrency-safe UNIQUE(plan_id, revision_number);
   nullable self FK for base_revision_id and normalized no-self-reference check; exact
   active REVISION_REASON family on writes. Deny metadata updates of persisted APPROVED
   revisions under the existing lock, backed by a database guard against approved-row
   mutation/deletion. Do not invent additional editable-state transitions or require
   base lineage to be same-plan where the governing review does not specify it.
5. HMR-065: require nameFr/scope type; unique plan code; active PLAN_TYPE on writes;
   owner checks above. Introduce UNIQUE(plan_id,id) on revisions before composite nullable
   current/approved FKs `(id,current_revision_id)` / `(id,approved_revision_id)` referencing
   revision `(plan_id,id)`. Existing parent revision->plan FK remains. Create plan with
   null pointers, create revision, then assign pointer; no circular insert or external FK.
   No new product owner, closed-period policy or approval lifecycle orchestration.
6. Additive forward migrations proposed:
   `V20261007_001__hmr_064_planning_plan_revision.sql`, then
   `V20261007_002__hmr_065_planning_operational_plan.sql`, subject to fresh maximum-version
   check. Fail on invalid legacy numbers, duplicates, orphan lineage, wrong families or
   cross-plan pointers; no silent renumbering, retagging or fabricated owner data.

### Proposed exact scope extension and validation

Retain each legacy HMR allowlist and exact commit message. Before implementation,
admit canonical roadmap/reconciliation updates to each HMR, replace unused backdated
migration registrations with the forward names above, and register these additional
exact paths (prefix src/main/java/dz/sh/hidra/modules/):

| HMR | Additional production path |
|---|---|
| 064 | planning/application/port/out/PlanningCatalogEligibilityPort.java |
| 064 | planning/infrastructure/persistence/adapter/JpaPlanningCatalogEligibilityAdapter.java |
| 065 | topology/application/contract/planning/PlanningTopologyScopeContract.java |
| 065 | topology/application/service/PlanningTopologyScopeQueryService.java |
| 065 | identity/application/contract/planning/PlanningCreatorContract.java |
| 065 | identity/application/service/PlanningCreatorQueryService.java |
| 065 | organization/application/contract/planning/PlanningResponsibleUnitContract.java |
| 065 | organization/infrastructure/query/PlanningResponsibleUnitQueryAdapter.java |

HMR-064 additionally admits its existing application-service/controller fixture tests;
HMR-065 must admit `src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` and
`src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java` for the same three exact
public package exports in both registries. Add
`src/test/java/dz/sh/hidra/modules/planning/infrastructure/persistence/PlanningSemanticPostgresTest.java`
to both HMRs for additive schema, competing uniqueness, approved immutability, catalog
families and correlated-pointer cases. HMR-065 admits dedicated owner query tests at
`src/test/java/dz/sh/hidra/modules/topology/application/service/PlanningTopologyScopeQueryServiceTest.java`,
`src/test/java/dz/sh/hidra/modules/identity/application/service/PlanningCreatorQueryServiceTest.java`,
and `src/test/java/dz/sh/hidra/modules/organization/infrastructure/query/PlanningResponsibleUnitQueryAdapterTest.java`.
No private cross-module imports or broad architecture exceptions.

Validation after admission: Maven compile; registered PlanRevision and OperationalPlan
semantic tests; existing PlanRevision update/controller tests; new owner and PostgreSQL
tests; both architecture/forensic guards; full test and clean verify including OpenAPI CI.
Local environment blocks remain reportable; temporary API checks are not Maven or DB passes.

This is a concrete proposal, not production authorization or implementation. Next owner
decision is acceptance or amendment of PL-PREREQ-01, followed by fresh exact-head baseline
verification and canonical scope admission. Do not advance to Batch 9. For this docs-only
preflight, publish once and stop after documentation CI is observed triggered.
### Batch 8 HMR-064 implementation — 2026-10-07

Positive per-plan revision numbers, nullable validated base lineage, active REVISION_REASON and approved metadata/persistence/database immutability enforced. Forward V20261007_001; four focused checks passed using temporary API/assertion stubs, not Maven/JUnit. Five PostgreSQL cases registered; local compile/focused Maven blocked by uncached Boot 4.1.1 parent. Full database validation pending CI.
### Batch 8 HMR-065 implementation — 2026-10-07

Required French name/scope type, unique plan code, active PLAN_TYPE, owner-controlled Topology/Identity/Organization references and same-plan nullable revision pointers enforced. Creation binds authenticated eligible actor and snapshots owner display values; unsupported REGION/NETWORK denied. Forward V20261007_002; twelve focused HMR-065/owner/catalog checks passed with temporary API stubs. Nine combined PostgreSQL cases registered; Maven compile/focused/full test/clean verify blocked before compilation by uncached Boot 4.1.1/Maven Central DNS. Full CI pending.

## Batch 8 final implementation and validation disposition — 2026-10-07

PL-PREREQ-01 accepted by owner Next; HMR-064 and HMR-065 each retain an independent
exact semantic commit. Two forward migrations 20261007_001/002 preserve published
history and establish revision guards before correlated plan pointers. Three new public
owner contract packages appear in both architecture/forensic registries (31 exact exports).
Unsupported REGION/NETWORK remain denied. No external FK, invented taxonomy, product
owner or approval lifecycle orchestration. Invalid legacy rows require owner reconciliation.
Current totals: **24 implemented, 32 still required, one blocked (HMR-080)**.

Validation performed:
- `bash ./mvnw -q -DskipTests compile`: Maven Central DNS failure resolving uncached
  Spring Boot parent 4.1.1; blocked before source compilation.
- `bash ./mvnw -o -q -Dtest=PlanRevisionSemanticRemediationTest,PlanRevisionUpdateApplicationServiceTest,PlanRevisionCommandControllerTest,PlanningSemanticPostgresTest test`:
  blocked by the same uncached parent.
- `bash ./mvnw -o -q -Dtest=OperationalPlanSemanticRemediationTest,PlanningTopologyScopeQueryServiceTest,PlanningCreatorQueryServiceTest,PlanningResponsibleUnitQueryAdapterTest,PlanningSemanticPostgresTest,ArchitectureGuardrailTest,ForensicRemediationClosureTest test`:
  blocked by the same uncached parent.
- Complete `bash ./mvnw -o -q test` and `bash ./mvnw -o -q clean verify`: same parent block.
- Host JDK17; no Docker/PostgreSQL. JDK source compiler compiled 150 actual source/
  temporary API units. Sixteen dedicated behavior methods and all five actual forensic
  source-scanning methods passed with temporary annotation/assertion APIs. These are
  not Maven/JUnit/framework integration/PostgreSQL pass claims.
- Nine PostgreSQL cases added for CI: positive/unique revision number plus nullable
  lineage; approved update/delete/truncate guards; exact active reason family; competing
  revision uniqueness; legacy-number abort without renumbering; current/approved same-plan
  pointers; required name/scope and active plan-type family; competing plan-code uniqueness;
  legacy cross-plan pointer abort without reassignment.
- Changed Java syntax/header checks, exact per-HMR path scope and both export registries
  passed. Canonical Markdown UTF-8/nonempty/conflict checks and git diff --check passed.

Publish chained admission/HMR/validation commits once, observe final-head CI trigger,
and stop. Final CI pending at preparation. Next owner-selected scope is attached Batch 9,
Documents aggregate HMR-067, 068, 084, only after complete Batch 8 CI is green and fresh
preflight/admission. Do not execute it automatically.

## Batch 9 preflight and concrete contract/storage proposal — 2026-10-07

Owner Next selects attached Batch 9: HMR-067/HMSR-079, HMR-068/HMSR-080 and
HMR-084/HMSR-098. Exact main/production head f56d55c0247875c5640ed61375da6b0896db9da6
confirmed by git fetch. CI #582/run 37643327683: the single verification job
112867542873 completed successfully, including Repository verification, deterministic
OpenAPI publication, base generation and backward compatibility. Documentation #76 was
already observed successful. Generic GitHub fetch calls timed out; purpose-built jobs
retrieval supplied the complete successful job evidence. Batch 8 CI-pending entries above
are historical preparation records; HMR-064/065 are now completed.

### Live evidence and split

All three HMSR obligation sets remain required. Document and DocumentVersion are SCC-05;
DocumentTargetLink depends on both. Legacy HMRB-032 pairs 067/068, while HMRB-045 keeps
084 solo; the attached three-task scope needs explicit canonical combined admission.

DocumentsApplicationService accepts caller creator/uploader/display/target values and
saves them without owner lookups. DocumentTargetLookupPort named by the DDD is absent;
DocumentTargetReferencePort only accepts one untyped string. Identity/Workflow outbound
ports similarly expose a boolean without owner implementations. No Documents-facing
Identity/Workflow owner contracts or typed target providers are evidenced.
NoopDocumentsExternalReferenceResolver returns true for all targets/actors/workflows but
is not wired into these writes. Its existence is not proof of owner validation; no
production entry path may acquire an accept-all fallback.

DocumentContentTransferService stores the physical blob and storage metadata before
UploadDocumentVersionUseCase is invoked. New uploader/catalog/parent/version-uniqueness
failures therefore need prevalidation and rollback cleanup to avoid orphan content.
These are DOC-PREREQ-01 owner-contract and storage-order prerequisites, not resolved
by assuming foreign domain/repository access or a cross-module FK.

AGENTS.md §3.2.9 and the SCC conditional envelope require stopping before mutation.
HMR-067, 068 and 084 are BLOCKED pending the concrete decision below. No production,
SQL, test, catalog or release-version mutation is claimed. Current register totals:
**24 implemented, 29 still required, four blocked (067, 068, 080, 084)**.

### Concrete proposed decision

1. Identity exports `identity.application.contract.documents.DocumentsActorContract`:
   `Optional<Actor> eligibleActor(String actorId, Instant at)`, Actor containing canonical
   id/displayName. Identity-owned DocumentsActorQueryService delegates internally to
   existing ACTIVE/unlocked actor eligibility; no new role/permission requirement.
   Register/upload/link operations require the supplied actor to match authenticated
   CurrentSecurityContext, with server-derived display snapshots at creation. Existing
   historical snapshots remain evidence and must not be silently refreshed on reads.
2. Documents exports neutral `documents.application.contract.target.DocumentsOwnedTargetLookup`:
   module(), targetTypeCodes(), resolve(typeCode,targetId), returning optional canonical
   target id/code/label. Owner implementations read only their own repositories.
   Documents' outbound DocumentTargetLookupPort/DocumentTargetLookupService require exactly
   one matching owner provider, canonical ID equality and existence. Missing, unsupported
   or ambiguous providers fail closed; caller code/label cannot stand in for identity.
   Optional owner tuple is all absent or complete module/type/id; blank normalization must
   not bypass a partially populated tuple. New snapshots use owner display data.
3. Initial approved target registry proposal: module `topology` with PIPELINE_SYSTEM,
   PIPELINE, FACILITY, EQUIPMENT; module `planning` with OPERATIONAL_PLAN, PLAN_REVISION.
   Providers are Topology-/Planning-owned; validate existence across historical/lifecycle
   states without inventing an ACTIVE-only attachment rule. Other modules/types, including
   unregistered `incidents`/`hse` aliases, deny new writes until explicitly owned providers
   are registered. This is a proposed support subset, not a global closed taxonomy.
4. Workflow exports `workflow.application.contract.documents.DocumentsApprovalReferenceContract`:
   boolean exists(String instanceId), implemented by DocumentsApprovalReferenceQueryService
   reading Workflow's own instance repository. Validate populated approval instance IDs
   without external FK. Do not invent completed-state, decision, purpose or target-equality
   requirements absent from these reviews. Routing/approval remains Workflow-owned.
5. HMR-067 enforces required titleFr/creator display, unique Document.code and exact active
   DOCUMENT_TYPE/DOCUMENT_CLASSIFICATION/optional DOCUMENT_CATEGORY. Add same-module catalog
   FKs/guards; existing invalid legacy rows abort. Protect populated currentVersionId with
   UNIQUE(document_id,id) on versions plus FK(document.id,current_version_id) to version
   (document_id,id). Tables already exist: introduce this composite target before the
   nullable pointer. Create document with null pointer, insert version, then assign pointer.
6. HMR-068 enforces required MIME/filename/checksum algorithm/value/uploader display, positive
   versionNumber and UNIQUE(document_id,version_number). Nullable supersededByVersionId
   references an existing local version with normalized no-self-reference; do not invent
   same-document supersession or full approval state-machine rules not required by HMSR.
   Keep existing document/storage FKs and date ordering; do not reject zero-byte files.
7. HMR-084 enforces targetModule, exact active DOCUMENT_LINK_ROLE and owner target resolution.
   Nullable version uses composite FK(document_id,document_version_id) to version
   (document_id,id), preserving document-wide links when null. No external target FK,
   primary-link uniqueness or new unlink-state matrix.
8. Binary transfer validates actor, document identity and version metadata before writing
   physical content; the transactional orchestration retains database uniqueness as the
   race arbiter. Register rollback cleanup for the newly created blob, including transaction
   commit failures, and clean it on pre-transaction/write failure. Only this upload's new
   blob may be deleted; preserve the original error if cleanup itself fails. Roll back new
   storage metadata when version creation fails. Do not silently delete existing objects or
   claim object storage and PostgreSQL have one physical transaction.

### Exact additional scope proposed for admission

Retain each HMR's legacy allowlist and exact semantic commit message. All three admit
canonical roadmap/reconciliation and Documents DDD status updates. Forward additive
migrations, subject to fresh maximum-version check:
- `src/main/resources/db/migration/V20261007_003__hmr_067_documents_document.sql`
- `src/main/resources/db/migration/V20261007_004__hmr_068_documents_document_version.sql`
- `src/main/resources/db/migration/V20261007_005__hmr_084_documents_document_target_link.sql`

Replace unused backdated registrations; never edit published migrations. Before mutation,
register exact extra production paths (prefix src/main/java/dz/sh/hidra/modules/):

| HMR | Additional production path |
|---|---|
| 067 | identity/application/contract/documents/DocumentsActorContract.java |
| 067 | identity/application/service/DocumentsActorQueryService.java |
| 067 | documents/application/contract/target/DocumentsOwnedTargetLookup.java |
| 067 | documents/application/port/out/DocumentTargetLookupPort.java |
| 067 | documents/application/service/DocumentTargetLookupService.java |
| 067 | documents/application/port/out/DocumentsCatalogEligibilityPort.java |
| 067 | documents/infrastructure/persistence/adapter/JpaDocumentsCatalogEligibilityAdapter.java |
| 067 | topology/application/service/DocumentsTopologyTargetLookup.java |
| 067 | planning/application/service/DocumentsPlanningTargetLookup.java |
| 068 | workflow/application/contract/documents/DocumentsApprovalReferenceContract.java |
| 068 | workflow/application/service/DocumentsApprovalReferenceQueryService.java |
| 068 | documents/application/service/DocumentsApplicationService.java |
| 068 | documents/application/service/DocumentContentTransferService.java |
| 084 | documents/application/service/DocumentsApplicationService.java |

HMR-067 additionally admits both exact guard files:
`src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java` and
`src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java`, mirroring three exact
exports (Identity documents, Workflow documents, Documents target) in both registries.
Workflow documents export addition belongs to HMR-068; therefore admit both guard files
there as well, without broad/private-package exceptions. Register new owner/lookup tests:
- `src/test/java/dz/sh/hidra/modules/identity/application/service/DocumentsActorQueryServiceTest.java` (067)
- `src/test/java/dz/sh/hidra/modules/topology/application/service/DocumentsTopologyTargetLookupTest.java` (067)
- `src/test/java/dz/sh/hidra/modules/planning/application/service/DocumentsPlanningTargetLookupTest.java` (067)
- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentTargetLookupServiceTest.java` (067)
- `src/test/java/dz/sh/hidra/modules/workflow/application/service/DocumentsApprovalReferenceQueryServiceTest.java` (068)
- `src/test/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferServiceTest.java` (068)
- `src/test/java/dz/sh/hidra/modules/documents/infrastructure/persistence/DocumentsSemanticPostgresTest.java` (all three)

HMR-068 admits the existing exact
`src/test/java/dz/sh/hidra/modules/documents/api/rest/controller/SpringDocumentsControllerContentTransferTest.java`
for new dependencies/rollback fixtures, if required. Preserve each registered dedicated
semantic test and message: Document, DocumentVersion, DocumentTargetLink. Apply 067,
then 068, then 084; supporting admission/status commits allowed, no semantic squash.

Validation after acceptance: compile; three dedicated semantic tests; owner registry
missing/ambiguous targets, actor impersonation and optional Workflow checks; existing
binary API/storage tests plus rollback/commit-failure cleanup; PostgreSQL competing code/
version uniqueness, current/link composition and nullable lineage; both guards; full test
and clean verify/OpenAPI CI. Local environment blocks must be reported accurately.

This is a reviewable proposal, not implementation. Accept or amend DOC-PREREQ-01,
then recheck baseline and explicitly admit the combined SCC-05 three-task scope before
production edits. For this docs-only preflight, stop after documentation CI trigger;
do not execute Batch 10 automatically.
### Batch 9 HMR-067 — IMPLEMENTED, CI PENDING

Required title/creator display, active exact document catalogs, code uniqueness and same-document current-version pointers enforced. Registration binds authenticated eligible Identity actor and canonical owner snapshots; neutral registry supports Topology/Planning and denies missing/ambiguous owners. Forward V20261007_003; 11 focused methods passed with temporary APIs, four PostgreSQL cases added. Compile/focused Maven blocked before compilation by uncached Boot 4.1.1 parent; full CI pending.
### Batch 9 HMR-068 — IMPLEMENTED, CI PENDING

Required upload metadata, positive per-document unique numbers, nullable existing supersession and owner-controlled Identity/Workflow references enforced. Generic upload derives authenticated uploader display. Binary prevalidates metadata and registers known-rollback new-blob cleanup; failed cleanup preserves original error, unknown commit outcome preserves content and logs reconciliation. Forward V20261007_004; 10 focused owner/version/cleanup methods passed with temporary APIs; nine combined PostgreSQL cases include transactional storage rollback and confirmed commit failure. Local focused Maven blocked by uncached Boot 4.1.1 parent; full CI pending.
### Batch 9 HMR-084 — IMPLEMENTED, CI PENDING

Required target module, active exact DOCUMENT_LINK_ROLE and owner-controlled target resolution enforced. Authenticated linking actor and canonical owner snapshots replace caller identity/display claims; optional version must belong to linked document, protected by composite FK. Forward V20261007_005; four focused methods passed with temporary APIs and three PostgreSQL cases added (12 combined). Both public export registries match 34 exact packages; all five forensic scans passed with temporary APIs. Focused Maven blocked by uncached parent; full CI pending.

## Batch 9 final implementation and validation disposition — 2026-10-07

Owner Next accepted DOC-PREREQ-01. HMR-067, HMR-068 and HMR-084 retain independent
exact semantic commits under the admitted combined SCC-05 scope. Three forward
migrations 003/004/005 preserve published schema history. Identity actors, neutral owner
targets and optional Workflow references are owner-controlled; unregistered/missing/
ambiguous targets deny. Existing snapshots remain historical evidence; new writes derive
canonical actor/target display values. Catalog families and local version composition are
protected without foreign module FKs or invented approval/unlink lifecycle rules.

Storage rollback cleanup targets only the newly generated blob ID. Known rollback cleans
content, including confirmed commit-failure rollback; metadata joins the same database
transaction. Cleanup errors remain logged and preserve the original error. Unknown commit
outcomes preserve possibly referenced content and log owner reconciliation. Binary and
PostgreSQL storage are not one atomic physical transaction. No release version change.
Current totals: **27 implemented, 29 still required, one blocked (HMR-080)**.

Validation performed:
- `bash ./mvnw -q -DskipTests compile`: failed before source compilation resolving
  uncached Boot parent 4.1.1; Maven Central DNS Temporary failure in name resolution.
- Offline compile and focused Maven sets attempted for each HMR:
  DocumentSemanticRemediationTest plus actor/owner/registry/PostgreSQL tests;
  DocumentVersionSemanticRemediationTest, DocumentContentTransferServiceTest, existing
  binary controller/storage tests, Workflow reference and PostgreSQL tests;
  DocumentTargetLinkSemanticRemediationTest and PostgreSQL tests. All blocked by the
  same uncached parent before compilation, not semantic test failures.
- `bash ./mvnw -o -q test` and `bash ./mvnw -o -q clean verify`: same parent block.
- JDK source compiler compiled 219 actual source/temporary API units. Eleven HMR-067
  owner/document/registry methods, ten HMR-068 Workflow/version/cleanup methods and four
  HMR-084 link methods passed with temporary annotation/assertion APIs (25 total).
  All five actual forensic source-scanning methods passed with temporary JUnit APIs.
  This is not Maven/JUnit/framework integration/PostgreSQL verification.
- Twelve PostgreSQL cases registered: current-version parent correlation; required
  document display/title/owner tuple and catalog families; competing document-code
  uniqueness; legacy wrong-current-version abort; version metadata/number/nullable
  supersession; competing version numbers; legacy-number abort; duplicate-version
  storage-metadata rollback/new-blob cleanup; confirmed commit failure rollback/cleanup;
  optional link-version correlation; target module/role family; legacy wrong-link abort.
  Real Spring/JDBC transaction cases await Docker CI. Host JDK17; Docker/PostgreSQL absent.
- Both architecture/forensic export registries match at 34 exact public packages;
  31 changed Java syntax/header checks and all independent HMR write-scope checks passed.
  Canonical Markdown UTF-8/nonempty/conflict and git diff checks passed.

Publish chained scope/HMR/validation commits once, observe final-head CI trigger and
stop. Full CI pending at preparation. Next proposed owner scope is attached Batch 10
Audit evidence (HMR-083, HMR-095, HMR-101, HMR-102), only after green Batch 9 CI and
fresh source/owner/scope admission. Do not execute it automatically.

## Batch 10 preflight and concrete audit evidence proposal — 2026-10-07

Owner Next selects attached Batch 10 HMR-083/HMSR-097, HMR-095/HMSR-112,
HMR-101/HMSR-118 and HMR-102/HMSR-119. Exact main is
6f147586583e61c0ac8212bb8dd0db14d635dabd, tree
327d8888d35b031423fa52753a697d9f36e32fba. Production CI #583/run 37656134940
completed successfully: job 112911441374 passed repository verification,
deterministic OpenAPI publication, base generation and backward compatibility.
Documentation #78 was previously observed successful. Batch 9 CI-pending entries
above are historical preparation records; all three Document HMRs are now completed.

### Fresh evidence and required split

- HMR-083: AuditExportRequest normalizes filterJson/format without required checks;
  AuditApplicationService passes filter JSON straight through and saves only the request.
  purposeId has no active EXPORT_PURPOSE family guard. Optional Workflow/Documents
  references are unchecked. AuditWorkflowReferencePort/AuditDocumentReferencePort have
  no implementation; NoopAuditExternalReferenceResolver returns true and is not proof
  of authoritative owner validation. Export creation emits neither AuditEvent nor
  AuditAccessRecord. Legacy HMRB-044 keeps this lifecycle/policy work solo.
- HMR-095: required module/type fields remain only normalized; catalog FKs prove
  existence, not EVENT_TYPE/EVENT_CATEGORY/SEVERITY/DECISION_REASON family membership.
  JpaAuditEventRepositoryAdapter uses generic save (merge capable), with no insert-only
  or DB immutability guard. reasonText/payloadJson pass through unchanged. The DDD
  requires a payload limit but supplies no number; AuditModuleConfiguration is an
  unwired record of booleans, not an enforceable approval or sensitive-data policy.
- HMR-101: generic save can replace an existing access ID; supplied optional event/export
  IDs lack existence checks. Optionality must remain unchanged.
- HMR-102: fieldPath is only normalized; masked/MASKED records may keep raw text;
  AuditSensitiveDataGuard checks a flag but does not remove raw text. MASK_REASON family
  validation and insert-only protection are absent. No current before/after producer was
  found; do not invent one.

These obligations need AUD-PREREQ-01: owner contracts, a concrete approved sanitation
budget, shared boundary scope, and database-backed append-only enforcement. Old
V20261004_083 would sort before already published migrations; forward filenames need
new canonical admission. Under AGENTS.md §3.2.9, stop before any semantic mutation.
All four selected HMRs remain blocked pending the decision below. No production, test,
SQL, taxonomy, version or API-contract mutation is included in this preflight.
Current totals: **27 implemented, 25 still required, five blocked** (including HMR-080).

### Proposed owner decision — AUD-PREREQ-01

1. **Combined scope and order.** Admit the attached four-task envelope in order
   083 → 095 → 101 → 102, with one exact semantic commit per HMR. This explicitly
   supersedes legacy solo HMRB-044/HMRB-053 only for this approved scope. HMR-083 adds
   common sanitation/catalog support and the request transaction; subsequent HMRs
   extend it within separately registered paths. Publish one final branch advancement;
   observe CI triggering and stop. No Batch 11 execution or release-version change.
2. **Owner references.** Documents exports
   `documents.application.contract.audit.AuditDocumentReferenceContract` with
   `boolean exists(String documentId)`, implemented by AuditDocumentReferenceQueryService
   using Documents' own repository. Workflow exports
   `workflow.application.contract.audit.AuditWorkflowReferenceContract` with
   `boolean exists(String instanceId)`, implemented by AuditWorkflowReferenceQueryService
   using Workflow's own repository. Audit adapter implements its existing outbound
   AuditDocumentReferencePort and AuditWorkflowReferencePort by delegation. Blank optional
   references normalize to null; populated references must resolve. No private imports,
   cross-module FKs, reuse of a Documents-specific contract for Audit, or accept-all fallback.
   Historical reads retain snapshots; validation applies to new writes.
3. **Request versus approval.** The present use case creates REQUESTED only. Existing
   optional Workflow reference proves existence, not approval. No purpose-to-approval
   mapping exists in governing source: do not infer one from an enum or configuration
   boolean. This scope grants no export execution, unmasking, approval, completion or
   result-document creation. Non-REQUESTED writes through this generic boundary fail
   closed until a separately admitted lifecycle/approval policy supplies owner-proven
   authorization and target correlation. Optional result document, if supplied on an
   admitted write, must exist through its owner; no invented artifact-type requirement.
4. **Explicit data budget and sanitation.** Propose a fixed **65,536 UTF-8 byte** ceiling
   for payloadJson and filterJson, including bounded parsing depth **32**. These numbers
   are a proposed owner decision, not a claim about existing DDD evidence. Reject
   oversized or invalid JSON and duplicate keys before storage, never silently truncate.
   Parse with the repository's existing Jackson dependency; recursively remove values
   of credential-sensitive keys after case/separator normalization (password, token,
   secret, private key, credential, API key, session, authorization, cookie).
   Replace sensitive-key values with a fixed redaction marker; handle nested objects
   and arrays. Reject recognizable credential material such as private-key blocks,
   bearer/basic authorization and labelled credential assignments in unstructured
   strings/free text. The supported detection patterns must be explicit and tested;
   arbitrary unlabelled secrets cannot be inferred. Keep raw inputs out of errors/logs.
   Apply policy to filter/payload and audit free-text before the authoritative write;
   enforce existing DDD field lengths, not a new smaller free-text budget. Apply the
   same guard to repository writes so callers cannot bypass the application service.
   SQL can enforce structure/size/required fields but is not a general secret classifier.
5. **Self-auditing without new taxonomy.** In one Spring transaction, persist a fresh
   REQUESTED export and exactly one AuditAccessRecord with existing accessType EXPORT,
   its exportRequestId, requesting actor snapshot, requestedAt and SHA-256 of the
   sanitized filter. It proves a request, not successful data export; resultCount remains
   null. Save directly through Audit-owned ports without recursive record/export calls.
   If evidence persistence fails, roll back the request. This uses an existing enum and
   DDD reference; no catalog seed or AUDIT_EXPORT_REQUESTED event is necessary. Actual
   export processing must create separate outcome evidence when explicitly implemented.
6. **Catalog eligibility.** Audit-owned shared eligibility port/adapter validates active,
   exact EXPORT_PURPOSE, EVENT_TYPE, EVENT_CATEGORY, optional SEVERITY, DECISION_REASON
   and MASK_REASON as used by each HMR. Do not retag rows or seed invented IDs.
   Add nullable same-module FKs where absent and insertion family guards. Deactivation
   preserves historical evidence; no retroactive rewrite. Invalid legacy rows abort
   migrations for explicit reconciliation.
7. **Append-only mechanism.** Keep existing save signatures for compatibility but
   document insert-only semantics for AuditEvent/AuditAccessRecord/AuditBeforeAfterValue.
   Use EntityManager.persist plus flush inside a transaction, never merge; primary-key
   uniqueness protects concurrent duplicate IDs. DB UPDATE/DELETE triggers protect
   all three evidence tables. No generic status/hash/timestamp update exemption is
   admitted; future seals/redaction/retention use appended evidence until a separate
   governed transition exists. Do not freeze mutable search projections/catalogs or
   invent a before/after producer. Preserve same-module mandatory parent FKs.
8. **Before/after evidence.** Reject blank fieldPath. Normalize sensitive field-path
   segments using the shared sensitive classifier. If masked=true, valueType=MASKED,
   or fieldPath is sensitive, prohibit beforeValueText/afterValueText; preserve supplied
   hashes/reference-only metadata. Prefer rejecting raw masked evidence over silently
   destroying evidence. Also guard unmasked free text against recognizable credentials.
   Validate optional MASK_REASON; changed=false remains legal. Add DB checks for
   required fieldPath and explicitly masked raw-text prohibition.
9. **Forward migrations.** Proposed filenames, all after current V20261007_005:
   - HMR-083: V20261007_006__hmr_083_audit_export_request.sql
   - HMR-095: V20261007_007__hmr_095_audit_event.sql
   - HMR-101: V20261007_008__hmr_101_audit_access_record.sql
   - HMR-102: V20261007_009__hmr_102_audit_before_after_value.sql
   Published migrations remain immutable. Preflight invalid existing required values,
   JSON budgets, catalog families, optional orphan references and masked raw evidence;
   abort without deleting or rewriting historical audit data.

### Proposed scope additions and validation admission

Preserve each HMR's legacy exact commit, full HMSR obligations and existing allowlist,
except replace stale migration filenames with the four forward names above. On
acceptance, register exhaustive paths before mutation. Proposed additions:

- HMR-083 owns AuditApplicationService, AuditSensitiveDataGuard, AuditBoundaryPolicy;
  new AuditCatalogEligibilityPort/JpaAuditCatalogEligibilityAdapter, AuditInputPolicy;
  new AuditOwnerReferenceAdapter; the two owner contracts and query services named
  above; package-info.java for their public packages; both architecture public-export
  registries; AuditExportRequestSemanticRemediationTest and shared
  AuditSemanticPostgresIntegrationTest. Existing Audit document/workflow outbound
  ports may gain explicit reference semantics. Register common sanitation tests here.
- HMR-095 also owns AuditApplicationService, shared input/catalog policy as needed,
  event adapter/entity/repository/domain/contract paths already allowed, dedicated
  AuditEventSemanticRemediationTest and shared PostgreSQL integration test.
- HMR-101 also owns AuditApplicationService if access prevalidation is required,
  dedicated AuditAccessRecordSemanticRemediationTest and shared PostgreSQL integration
  test; its existing repository/adapter/domain paths remain independently scoped.
- HMR-102 also owns shared AuditSensitiveDataGuard/AuditBoundaryPolicy/AuditInputPolicy
  if required, dedicated AuditBeforeAfterValueSemanticRemediationTest and shared
  PostgreSQL integration test; no producer/application API is introduced.
- Each semantic commit updates its own status in canonical Ultimate Roadmap,
  reconciliation and legacy remediation, plus Audit DDD where it defines policy.
  Supporting scope/validation documentation commits are separately identified.

Run compile, each dedicated focused test, shared PostgreSQL integration tests, relevant
owner/architecture tests, full test and clean verify. Cover malformed/duplicate/nested
JSON, multibyte byte boundaries and depth, credential redaction, optional reference
unknowns, active exact catalogs, atomic export/evidence rollback, concurrent duplicate
IDs, DB direct UPDATE/DELETE rejection, masked raw-text denial and legacy migration
abort. Verify adapters with actual JPA/PostgreSQL behavior, not only mocked existence
checks. Preserve existing Risk/Organization/Alarm audit contract compatibility.

This is a concrete reviewable proposal, not implementation or completed validation.
Accept or amend AUD-PREREQ-01, then register exact exhaustive paths, recheck green
production head and prepare the four independent commits. Do not advance automatically.

### Batch 10 HMR-083 — IMPLEMENTED, CI PENDING

Required export metadata, active EXPORT_PURPOSE, owner-controlled optional Workflow/Documents references, bounded sanitized filters and one transactional EXPORT access record implemented. Generic writes admit REQUESTED only and persist/flush without merge. Forward V20261007_006; eight focused tests, two owner tests and four PostgreSQL/Spring/JPA tests prepared. Local Maven compile/focused blocked before compilation by uncached Boot 4.1.1 parent; production CI pending.

### Batch 10 HMR-095 — IMPLEMENTED, CI PENDING

Required source/target module and target type, active exact event/category/optional severity/reason families, bounded sanitized payload/free text and persist/flush insertion enforced. Forward V20261007_007 adds optional catalog FKs, family guards and immutable event UPDATE/DELETE denial. Five focused and four added PostgreSQL/JPA/concurrency checks prepared; local focused Maven blocked by uncached Boot parent; CI pending.

### Batch 10 HMR-101 — IMPLEMENTED, CI PENDING

Access records use persist/flush without merge; populated optional AuditEvent/export IDs must exist. Forward V20261007_008 supplies nullable local FKs and UPDATE/DELETE denial. Three focused and four added PostgreSQL/JPA/concurrency/orphan checks prepared; local focused Maven blocked by uncached Boot parent; CI pending.

### Batch 10 HMR-102 — IMPLEMENTED, CI PENDING

Required fieldPath, masked/sensitive raw-text exclusion, optional exact active MASK_REASON and existing parent event enforced. Hash-only evidence and changed=false remain legal. Persist/flush insertion plus V20261007_009 local FKs/checks/UPDATE/DELETE denial preserve immutable rows. Six focused and five added PostgreSQL/JPA/concurrency/legacy checks prepared. Temporary API type compilation passed; local focused Maven blocked by uncached Boot parent; CI pending.


## Batch 10 final implementation and validation disposition — 2026-10-07

Owner Next accepted AUD-PREREQ-01. Four independent exact semantic commits preserve
HMR-083/HMSR-097, HMR-095/HMSR-112, HMR-101/HMSR-118 and HMR-102/HMSR-119. All are
IMPLEMENTED — CI PENDING; previous 27 implementations have green production CI through
#583. Current totals: **31 implemented, 25 still required, one blocked (HMR-080)**.

Export creation requires filter/format, active EXPORT_PURPOSE and owner-proven optional
Workflow/Documents references. The present generic boundary admits REQUESTED only;
no approval/execution/unmasking claim follows from existence. One EXPORT access row
with sanitized-filter SHA-256 joins the request transaction; evidence failure rolls
back the request. Export inserts also use persist/flush, preventing ID replacement.
Event/access/before-after evidence has insert-only JPA and SQL UPDATE/DELETE denial.
Exact active catalog families, nullable local references, required module/type/path,
JSON budgets/depth and masked raw-text exclusion are enforced. Technical actorId,
optional reason/reference values and changed=false retain their documented optionality.

The accepted 65,536-byte/depth-32 policy parses JSON strictly, rejecting duplicate
keys and trailing tokens, redacts nested credential-key values and rejects supported
credential patterns even in JSON property names. Basic authorization is recognized
by decoded user:password structure so ordinary text such as "Basic station inspection"
remains legal. Arbitrary unlabelled secrets cannot be inferred. PostgreSQL limits the
stored jsonb textual representation; its added whitespace can reject a near-limit input
whose compact application representation was within budget. Both boundaries fail
without truncation. No historical evidence or published migration is rewritten.

### Validation actually performed

- `bash ./mvnw -o -q -DskipTests compile`: blocked before compilation by uncached
  Spring Boot 4.1.1 parent. Online compile also failed: repo.maven.apache.org temporary
  failure in name resolution. No Maven compile success is claimed.
- Each dedicated semantic focused test plus the shared integration test was attempted
  with offline Maven; HMR-083 also selected both owner queries. All failed at the same
  parent-resolution stage. Full `bash ./mvnw -o -q test` and
  `bash ./mvnw -o -q clean verify` likewise failed before executing tests.
- 177 actual-source/temporary-API units compiled with Java's compiler. This detects
  project type/signature errors, but does not establish real Jackson/Spring/Hibernate,
  Mockito/JUnit or PostgreSQL compatibility. JSON parser behavior was deliberately
  not simulated and is not claimed passed.
- 16 actual Java domain/text/hash/recursive-policy checks passed using temporary
  external annotation/assertion/API surfaces: five domain methods, four credential
  rejections, benign text and known SHA-256, two Basic credential/ordinary-text checks,
  three recursive map/list policy checks. These are not Maven/JUnit execution.
- All five actual ForensicRemediationClosureTest source scans passed with temporary
  JUnit annotation/assertion APIs. Both public-export registries have the same 36 exact
  packages, with no duplicates. Private-import and wire-model checks remain closed.
- 34 changed Java files syntax parsed; canonical author/creation/update headers checked.
  Each semantic commit's changed paths match its independent admitted allowlist.
  All 82 canonical Markdown files are valid UTF-8/nonempty/conflict-free; diff whitespace
  checks pass. Forward SQL is only V20261007_006/007/008/009; existing SQL is unchanged.

### Validation prepared for production CI

24 dedicated semantic/owner test methods (8 export, 5 event, 3 access, 6 before-after,
2 owner queries) plus 17 PostgreSQL/Spring/Hibernate cases are prepared, not locally
passed. The latter cover stored JSON, catalog families, mutable catalog lifecycle,
request/evidence rollback, actual JPA insert-only behavior for all four models,
concurrent duplicate IDs, optional orphan denial, direct UPDATE/DELETE and upsert
rejection, raw masked exclusion and invalid legacy migration abort. Existing Audit
Risk/Organization/Alarm contracts, architecture, full verification and OpenAPI gates
remain part of CI. No Docker/PostgreSQL service or real Maven dependency graph was
available locally.

Final publication includes scope admission, four semantic commits and this supporting
validation disposition. Advance main once after exact-tree comparison, observe production
CI triggering, then stop. CI pending at preparation. Next proposed owner scope is attached
Batch 11 Integration evidence HMR-056/HMR-071, only after Batch 10 CI is green and fresh
source/owner/exact-scope admission. No automatic Batch 11 execution.


## Batch 11 preflight and concrete Integration resolver proposal — 2026-10-07

Owner Next selects attached Integration evidence Batch 11, HMR-056/HMSR-067 and
HMR-071/HMSR-084. Exact production main is
712ec827e905f92b8f280a7ac8fec95eaf5cc8c9. CI #584/run 37667837697 completed
successfully; job 112951543842 passed repository verification and deterministic
OpenAPI publication/base generation/backward compatibility. Batch 10's four pending
preparation entries are historical; all four audit HMRs are now completed.

### Fresh source evidence and split

HMR-056 still requires optional IntegrationJobRun existence, optional endpoint
existence and endpoint.externalSystemId == message.externalSystemId. The current
JpaIntegrationExchangeMessageRepositoryAdapter uses generic save with no local
lookups or family eligibility checks. Mandatory system/catalog generic FKs remain
existing protection; optional job/endpoint edges are only indexed in the base schema.

The old HMSR-067 missing-family statement is superseded by current source evidence:
`docs/data definition/Integration.md`, section 6.25 IntegrationCatalogEntry, explicitly
lists MESSAGE_TYPE and PAYLOAD_FORMAT under recommended catalog names (lines 1299–1300
at this production head). active is defined as whether an entry can be used. Therefore
exact active MESSAGE_TYPE/PAYLOAD_FORMAT validation is evidence-backed; no new family
name, hard-coded catalog ID or catalog seed is proposed.

HMR-071 still requires nonblank failureStage/reasonMessage, populated optional
jobRunId/exchangeMessageId/inboundRecordId/outboundRecordId existence and complete
manual-resolution actor/time/comment evidence. Current domain only normalizes those
strings; current repository adapter performs no validation. No dead-letter resolution
application producer or Integration-facing Identity resolver contract was found.
Existing Workflow/Documents/Planning contracts belong to their respective consumers;
Integration must not treat a consumer-specific contract as its own API. Existing
IntegrationJobRun validation proves manual actor presence, not owner identity, and
is outside this batch's correction scope.

INT-PREREQ-01 is the missing owner-contract and authenticated manual-write decision.
AGENTS.md §3.2.9 requires splitting it out and stopping before mutation. Both legacy
HMRB-023/HMRB-035 are solo; the attached two-task envelope also needs explicit combined
canonical admission. Unused V20261004_056/071 would sort before published migrations:
forward names must be registered. HMR-071 is blocked; HMR-056 remains required but
unexecuted until accepted batch scope. Totals: **31 implemented, 24 still required,
two blocked** (HMR-071 and HMR-080). No production/test/SQL/API/version change.

### Proposed owner decision — INT-PREREQ-01

1. Admit the attached two-task envelope in order HMR-056 → HMR-071, preserving the
   full HMSR obligations, exact messages and independent commits. This is a bounded
   combined exception to legacy solo HMRB-023/HMRB-035. Advance main once after scope
   checks and validation; observe production CI triggering and stop. No Batch 12.
2. HMR-056 validates optional run and endpoint through Integration-owned repositories
   at every save. Require endpoint's externalSystemId to equal message's system ID;
   no extra run/system, direction, storage-mode or lifecycle rule is inferred.
   Mandatory messageTypeId/payloadFormatId must resolve to active exact MESSAGE_TYPE/
   PAYLOAD_FORMAT entries. Preserve null optional run/endpoint values.
3. Add forward same-module protection: nullable message job-run FK;
   UNIQUE(endpoint.id, endpoint.external_system_id) plus message composite FK
   (endpoint_id, external_system_id) to endpoint (id, external_system_id). Keep existing
   mandatory ExternalSystem/catalog FKs. Catalog family/active guards apply to new
   writes; no historical catalog retagging. Orphan/mismatched legacy rows abort for
   reconciliation, rather than reassigning messages.
4. Identity exports `identity.application.contract.integration.IntegrationResolverContract`
   with `boolean eligibleResolver(String actorId, Instant at)`. Identity-owned
   `IntegrationResolverQueryService` implements it using Identity's existing ACTIVE/
   unlocked actor eligibility internally. No new permission/role is invented. Only
   this contract crosses the module boundary; no Identity domain/repository import
   or cross-module FK. Add its exact public package to both architecture registries.
5. HMR-071 requires failureStage/reasonMessage as nonblank strings. Preserve their
   current technical/string semantics; do not convert failureStage or reasonCode into
   new enums/catalog references. Supplied optional run/message/inbound/outbound IDs
   must resolve within Integration; add nullable FKs to protect concurrent references.
   Do not require every reference or infer they belong to one run/system absent a
   correction requiring that correlation.
6. A manual trio is all absent or complete after normalization. Any populated actor,
   timestamp or comment counts as manual evidence and requires all three. Do not equate
   every terminal status with manual resolution; REPLAYED/IGNORED retain their documented
   independent flows and no complete status matrix is invented. Status alone does not
   create or validate a resolver identity; no new resolution/replay API is introduced.
7. Generic dead-letter saves that newly record a complete manual trio must require
   resolvedByActorId to equal the authenticated CurrentSecurityContext actor and to be
   eligible through the Identity owner contract at current time. A caller's backdated
   resolvedAt cannot establish present eligibility. Existing complete recorded trios
   are retained unchanged on unrelated updates, without rechecking a historical actor's
   current lifecycle. Replacing/removing an existing complete trio fails closed so
   provenance is preserved; a separately governed correction would be needed. Insert
   with prepopulated manual evidence is treated as a new manual write and validated.
   Domain/SQL also protect complete-trio shape; SQL does not prove external identity.
8. Proposed forward migrations:
   - HMR-056: V20261007_010__hmr_056_integration_exchange_message.sql
   - HMR-071: V20261007_011__hmr_071_integration_dead_letter_record.sql
   Both follow published V20261007_009. Invalid legacy required failure evidence,
   optional orphan references and partial manual trios abort. Existing migrations and
   historical payload/failure evidence are never rewritten. No arbitrary JSON size,
   payload-mode cardinality, new replay producer or cleanup deletion is admitted.

### Proposed scope and validation admission

On acceptance, register exhaustive per-HMR paths before mutation. Preserve each legacy
allowlist except unused migration replacements, plus canonical roadmap/reconciliation
and the shared IntegrationSemanticPostgresIntegrationTest. Proposed additions:

- HMR-056: existing message repository adapter/domain/entity/repository/mapper/port paths;
  optional IntegrationApplicationService if authoritative prevalidation requires it;
  dedicated IntegrationExchangeMessageSemanticRemediationTest and shared PostgreSQL
  cases for run optionality/existence, endpoint correlation/concurrent ownership,
  active exact families and invalid legacy abort. Same-module repositories are read
  through the adapter; no new foreign owner contract is required for these edges.
- HMR-071: existing dead-letter adapter/domain/entity/repository/mapper/port paths;
  new Identity IntegrationResolverContract, contract package-info.java,
  IntegrationResolverQueryService and dedicated owner query test; both public-export
  registries; dedicated IntegrationDeadLetterRecordSemanticRemediationTest and shared
  PostgreSQL cases. Inject the existing CurrentSecurityContext into the authoritative
  adapter for newly recorded manual evidence. No new dead-letter command/controller.
- Supporting admission/validation documentation commits are separate; each semantic
  commit updates its independent canonical/legacy status and DDD policy as applicable.
  Exact semantic messages remain:
  `fix(integration): remediate semantic review IntegrationExchangeMessage` and
  `fix(integration): remediate semantic review IntegrationDeadLetterRecord`.

Run compile; both dedicated semantic tests; Identity owner test; shared PostgreSQL
integration tests; existing IntegrationJobRun/controller tests and architecture/forensic
checks; full test and clean verify. Cover wrong/inactive catalog families, unknown
optional IDs, cross-system endpoints, blank failure evidence, every partial manual-trio
combination, wrong authenticated actor, ineligible Identity actor, complete new manual
writes, unchanged historical trios and denied provenance replacement/removal. Prove
nullable/composite SQL FKs and legacy migration abort on actual PostgreSQL. Report
local dependency/runtime blocks accurately.

This is a concrete proposal, not implementation or completed validation. Accept or amend
INT-PREREQ-01, then register exact scope, recheck green production baseline, prepare the
two independent commits and publish one final head. Do not execute automatically.

### Batch 11 HMR-056 — IMPLEMENTED, CI PENDING

Optional run/endpoint existence, correlated endpoint/system ownership and active exact existing MESSAGE_TYPE/PAYLOAD_FORMAT catalogs enforced on saves. Forward V20261007_010 adds nullable/composite FKs and catalog guards without rewriting legacy evidence. Five dedicated and five PostgreSQL cases prepared. Local compile/focused Maven blocked before execution by uncached Boot parent; CI pending.

### Batch 11 HMR-071 — IMPLEMENTED, CI PENDING

Required failure evidence, all-or-none manual trio and optional local references enforced. New manual evidence requires authenticated eligible Identity actor; recorded provenance is immutable without historical actor revalidation. Forward V20261007_011 supplies nullable FKs/checks and concurrent provenance guard. Eight focused methods, one Identity owner method and five added PostgreSQL cases prepared. Temporary API type compilation passed; local focused Maven blocked by uncached Boot parent; CI pending.


## Batch 11 final implementation and validation disposition — 2026-10-07

Owner Next accepted INT-PREREQ-01; the resolver decision is implemented within admitted
056 → 071 scope, with independent exact semantic commits. HMR-056/HMSR-067 and
HMR-071/HMSR-084 are IMPLEMENTED — CI PENDING. Current totals: **33 implemented,
23 still required, one blocked (HMR-080)**. Previous 31 have green production CI #584.

ExchangeMessage saves enforce optional local run/endpoint existence, endpoint ownership
by the selected external system and active exact existing MESSAGE_TYPE/PAYLOAD_FORMAT
families. Forward 010 provides nullable local/composite FKs and active-family SQL guards.
DeadLetterRecord requires nonblank failure stage/reason message, all-or-none normalized
manual actor/time/comment and existing optional local references. New manual evidence
matches the authenticated actor and Identity's current eligibility through the explicit
IntegrationResolverContract. Complete recorded provenance remains immutable on updates,
without revalidating historical actor lifecycle. Forward 011 checks evidence shape and
references and freezes recorded trios, including competing updates. Status alone does
not imply manual resolution; no new status matrix or replay producer is introduced.
No foreign-module FK, catalog seed or published migration rewrite occurs.

### Validation actually performed

- Offline Maven compile, each dedicated semantic test with shared PostgreSQL tests,
  the Identity owner test, existing IntegrationJobRun/controller and architecture/forensic
  selections, full test and clean verify were attempted. All failed before compilation
  or test execution because the Boot 4.1.1 parent is uncached. Online compile confirms
  repo.maven.apache.org temporary failure in name resolution. No Maven pass is claimed.
- 158 source/temporary-API units compiled with Java's compiler. This detects source
  type/signature issues but does not establish real Spring/JPA/Mockito/JUnit compatibility.
- Four actual Java behavior checks passed with temporary external APIs: required failure
  evidence, all six partial manual trios, absent/complete trios with independent status
  semantics, and Identity delegation using the supplied current time. This is not
  Maven/JUnit execution; Mockito adapter tests were not simulated.
- All five actual forensic source scans passed with temporary JUnit annotations/assertions.
  Both architecture registries have the same 37 exact public packages, without duplicates.
  14 changed Java files syntax parsed; author/created/updated headers checked. Independent
  HMR-056 and HMR-071 commit scopes pass (9 and 16 paths respectively). All 82 canonical
  Markdown files are valid UTF-8/nonempty/conflict-free; diff whitespace checks pass.
- Only new forward V20261007_010/011 SQL is changed. Published migrations are unchanged.

### Validation prepared for production CI

14 dedicated semantic/owner test methods (5 ExchangeMessage, 8 DeadLetterRecord,
1 Identity owner) and 10 PostgreSQL cases await CI. Database cases cover optional local
references, correlated ownership, active catalog families, required/partial evidence,
legacy violation abort without repair, historical provenance immutability and concurrent
endpoint reparent/manual-resolution writers. The SQL tests create the Integration base
schema and execute these two forward migrations; they do not replace the full Flyway
chain or existing Spring/controller/architecture/OpenAPI verification gates. No local
Docker/PostgreSQL service or real Maven dependency graph was available.

Publish exact trees as scope admission, two semantic commits and this supporting
disposition; advance main once, observe production CI started, then stop. CI is pending
at preparation. Next proposed scope is attached Batch 12 Reporting HMR-057/HMR-093,
only after Batch 11 CI is green and fresh source/owner/exhaustive-scope admission.
Do not execute Batch 12 automatically.


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

### Batch 12 HMR-057 — IMPLEMENTED, CI PENDING

Queue eligibility/access/approval, exact template lineage, concrete required parameters and terminal evidence enforced. Forward 012 corrects run/parameter request FKs and guards lineage/history. Eight Run, four QueueEvidence and nine PostgreSQL cases prepared; local runtime validation follows; CI pending.

### Batch 12 HMR-093 — IMPLEMENTED, CI PENDING

Existing run and nonblank Documents reference evidence required; every supplied reference is independently owner-validated. Forward 013 corrects artifact/run FK without Documents FK. Six focused methods, one Documents owner method and five additional PostgreSQL cases prepared; CI pending.


## Batch 12 final implementation and validation disposition — 2026-10-07

Owner Next accepted REP-PREREQ-01 and the registered combined 057 → 093 scope. Exact
independent HMR-057/HMSR-068 and HMR-093/HMSR-110 semantic commits are IMPLEMENTED —
CI PENDING. Current totals: **35 implemented, 21 still required, one blocked HMR-080**.
Previous 33 have green production CI #585; preflight Documentation #83 passed.

New queue operations require ACTIVE definition, matching request definition, queueable
SUBMITTED/APPROVED status, approval-required APPROVED state plus Workflow confirmation,
restricted Identity access from persisted requester/scope, ACTIVE exact version/active
template lineage and active required concrete parameter evidence. Matching definition
and code with exactly one corresponding typed field is required; zero/false are concrete,
blank text is absent and defaults are not materialized. Terminal completedAt/failureReason
rules are enforced. Forward 012 corrects run and parameter-value request FKs and adds
correlated request/definition integrity, template lineage and parent-reparent guards.
Existing queue trigger is superseded additively so unrelated historical updates preserve
retired versions. Version/template rows are share-locked to serialize conflicting reparent
writes. Parameters are evaluated at queue time; no parameter immutability, new request
transition, job executor or duplicate-run guarantee is introduced.

Artifact generation requires an existing run and at least one normalized Documents ID;
every supplied ID is validated through Documents-owned distinct document/storage lookups.
No pairing/lifecycle/binary-availability inference or completed-only creation rule follows.
Checksum requirements remain. Forward 013 corrects artifact/run FK and reference shape;
no Documents foreign FK or private import. SQL cannot prove cross-module existence;
Documents owner validation is the authoritative application boundary. Compatibility
constructors without new lookup dependencies fail closed on those operations.

### Validation actually performed

- Offline compile, HMR-057 focused Run/QueueEvidence/PostgreSQL selection, HMR-093 focused
  Artifact/Documents-owner/PostgreSQL selection, existing Reporting request/definition and
  architecture/forensic selection, full test and clean verify were attempted. All failed
  before compilation/test execution because Boot 4.1.1 parent is uncached. Online compile
  confirms repo.maven.apache.org temporary failure in name resolution. No Maven pass.
- 189 actual-source/temporary-API units compiled using Java's compiler. This detects type
  and signature errors but is not real Spring/JPA/Mockito/JUnit compatibility verification.
- 13 actual Java behavior checks passed with temporary external APIs: three actual domain
  test methods for terminal evidence, normalized artifact references and checksum, plus
  ten typed-field policy checks (all seven value types, zero/false, blank/absent/multiple
  field rejection). Mockito service and owner tests were not simulated or claimed passed.
- Five actual forensic source scans passed with temporary JUnit APIs. Both architecture
  registries contain the same 38 exact exported packages, without duplicates. 17 changed
  Java files syntax parsed; canonical author/creation/update headers checked. Independent
  semantic scope checks passed (13 HMR-057 paths, 16 HMR-093 paths). All 82 canonical
  Markdown files are valid UTF-8/nonempty/conflict-free. Whitespace checks pass.
- Only new forward SQL 012/013 changes; published migration files are unchanged.

### Validation prepared for production CI

19 dedicated semantic/owner methods (8 Run, 4 QueueEvidence, 6 Artifact, 1 Documents owner)
and 14 PostgreSQL cases await CI. Database cases cover corrected request/artifact FKs,
request/definition/template consistency, retired-template historical updates, queue status,
approval/restricted-policy gates, required concrete fields, zero/false, terminal evidence,
legacy orphan/reference-shape abort without fabricated repair, and concurrent template
reparent/queue and run-delete/artifact races. The fixtures execute Reporting base SQL,
three actual HRA-111 incorrect FK clauses, actual HMR-013 and these two forward migrations.
They do not replace the complete Flyway chain or existing CI Spring/architecture/OpenAPI
gates. No local Docker/PostgreSQL service or actual Maven dependency graph was available.

Scope admission, two independent semantic commits and this validation record are published
as exact trees with one final branch advancement. Observe production CI started and stop;
CI pending at preparation. Next proposed owner scope is attached Batch 13 Simulation
HMR-078/HMR-079, gated on green Batch 12 production CI and fresh source/owner/exact-scope
admission. Do not execute Batch 13 automatically; release stays 0.6.0-SNAPSHOT.

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

Current total: 50 COMPLETED (49 CI-confirmed, HMR-098 final-head CI pending), six STILL REQUIRED
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
| HMR-100 / HMSR-117 | COMPLETED — CI #602 GREEN | 2c1693390e943356346c23d611222cee18609979 / 23f90c246df353ca5c31a1fc6a39736ca3071bc6 |
| HMR-104 / HMSR-121 | COMPLETED — CI #602 GREEN | 4373b97ad1e6cd5908bd05591e9b494499818af6 / 13f67c0cd1133d3ddb388ebcbdff9d1db8c4d2a4 |
| HMR-105 / HMSR-122 | COMPLETED — CI #602 GREEN | 0445518741bc62ba35ed117a81b1c143ea1b0171 / 69dac5de4e0356ffe586c37852baefe928adbac8 |
| HMR-106 / HMSR-123 | COMPLETED — CI #602 GREEN | Ordered final commit uses fix(alarm): remediate semantic review AlarmShelving; exact published tree is independently checked before main advancement. |
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

Owner Next revealed failed production CI #601 on unchanged main
`3841da6332d8abda073c50871e4c28348cf3f4df`; documentation CI #108 passed.
CI executed 1,232 tests with one failure, zero errors/skips. The only failing
DomainPersistenceMirrorGuardrailTest applies historical HRA-061 retirement to the
AlarmLifecycleEvent split expressly restored by accepted ALRM-PREREQ-01. Current
AlarmShelvingPolicy.restorationState consumes its authoritative domain evidence,
meeting REAL_DOMAIN criteria. No other runtime test failure is reported; this does
not establish a successful clean verify or OpenAPI compatibility gate.

Supporting repair admitted under AGENTS.md section 3.2.8 with exact message
`test(alarm): reconcile lifecycle event mirror disposition`. Exhaustive scope:
`src/test/java/dz/sh/hidra/DomainPersistenceMirrorGuardrailTest.java`,
`doc/roadmap/ULTIMATE_ROADMAP.md`, and this reconciliation. Preserve historical
51 REAL_DOMAIN / 343 READ_PERSISTENCE_MODEL inventory unchanged; reclassify only
alarm.AlarmLifecycleEvent in the current guard to effective 52/342. Require its
record/entity/port/adapter/repository/mapper and live domain policy consumer rather
than merely exempting it. All other retired pairs remain forbidden.

Validate focused mirror/architecture/forensic tests, full test, clean verify,
inventory/negative mutation probes, whitespace and canonical Markdown. No production,
migration, legacy classification, API, version or workflow change. Keep the four
semantic commits independent. Current gate: 52 CI-confirmed, four implemented awaiting
repaired-head CI, HMR-080 separately blocked, 57 evaluated; HPR-P2-008 stays open.
No physical survivability or release claim. Publish with the exact expected-head
lease; observe replacement production CI start and STOP without waiting.

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

## Independent P2 governance audit disposition — 2026-10-09

The owner requested analysis and a roadmap update after the twelve-check audit at
`c7d580cc7988f069bb939eb682664200e043c9bf`: 9/12 VERIFIED, overall FAIL.
Checks 9–11 expose incomplete API documentation, physical dictionary content and
schema/migration policy; they do not reopen any completed HMR implementation.
Current execution authority is the bounded correction plan in
[the Ultimate Roadmap](../roadmap/ULTIMATE_ROADMAP.md#independent-p2-audit-analysis-and-bounded-correction-plan--2026-10-09).
HPR-P2-005 and HPR-P2-006 are PENDING correction; HPR-P2-013 is PENDING renewed
closure. This note supersedes earlier phase CLOSED/no-next-task assertions only.
All 57 HMR completions and 123 semantic subjects, historical CI/physical provenance,
P0/P1 disposition and version 0.6.0-SNAPSHOT are preserved. P3 remains DEFERRED.
No corrective implementation is executed by this documentation-only amendment.

## HPR-P2-005 API documentation correction — 2026-10-09

The owner selected implementation after preflight Documentation Validation #130
PASSED on `b39c87dcaf887ada1d22c6a88d1e1f51b9f7ecaa`. The approved six-file scope
corrects API conventions/errors and supporting applicability/control records.
Current runtime mappings remain distinct from the unchanged generated OpenAPI;
258 HTTP-200/five HTTP-201 declarations are reconciled. Telemetry uses its own
Page DTO with hasNext; kernel page rules are not imposed on that adapter.

HPR-P2-005 implementation is complete and IN PROGRESS pending its exact-head
publication documentation CI. Next verifies that gate before HPR-P2-006 preflight.
Current [roadmap](../roadmap/ULTIMATE_ROADMAP.md#hpr-p2-005-api-correction-implementation--2026-10-09)
controls execution over older dated statuses. HPR-P2-006/013 remain PENDING;
P2 OPEN/P3 DEFERRED. Every HMR row, 57 completions, 123 semantic subjects, version
0.6.0-SNAPSHOT and historical provenance remain preserved. No semantic/runtime,
database, source/test/configuration, deployment, import or release change is made.

## HPR-P2-005 verification and HPR-P2-006 preflight — 2026-10-09

GitHub main remained `4d41f265374156846a86d31f3d30e2b0fe00dd91`; Documentation
Validation #131/run 37921916637 completed SUCCESS on that exact SHA.
HPR-P2-005 is COMPLETED; this supersedes its dated publication-awaiting-CI statements.
The owner selected HPR-P2-006 preflight only. The
[bounded database plan](../roadmap/ULTIMATE_ROADMAP.md#hpr-p2-006-concrete-database-correction-preflight--2026-10-09)
requires Stage A disposable-CI catalog evidence, then separately selected Stage B
physical dictionary/ownership/policy completion. The local environment lacks
PostgreSQL/Docker tooling; no migrated schema or artifact is claimed here.

Only roadmap/reconciliation controls change in this preflight. HPR-P2-006/013 remain
PENDING; P2 OPEN/P3 DEFERRED. All 57 HMR and historical table rows, 123 semantic
subjects, prior physical/CI provenance and version 0.6.0-SNAPSHOT are preserved.
No tool/workflow/dictionary implementation, runtime/database change, deployment,
import, release or P3 task executes through this planning publication.

## HPR-P2-006 Stage A schema evidence tooling — 2026-10-09

The owner selected Stage A after preflight Documentation Validation #132 PASSED on
`04e1cee9b584c0d834cb885faea3fd0046d0ea5f`. Only the six admitted tooling/workflow/
control paths change. Read-only catalog collection follows successful current
Flyway/JPA startup in disposable CI and precedes base-revision startup; captured
schema/history/mapping evidence and a DRAFT dictionary are retained as an artifact.
No artifact, full CI success or CURRENT physical dictionary is claimed at publication.

Local generator fixtures (17) and existing validator tests (20) passed. Mapping
inventory found 139 migrations, 470 JPA tables and 5,781 mappings including aliases.
No local PostgreSQL/Docker extraction was available. Maven clean verify failed before
compilation on Maven Central DNS/uncached Boot parent 4.1.1; this is not runtime proof.
Current [Stage A record](../roadmap/ULTIMATE_ROADMAP.md#hpr-p2-006-stage-a-implementation--2026-10-09)
controls over older pending/preflight notes. HPR-P2-006 is IN PROGRESS; Next verifies
both exact-head CI runs and schema artifact before separately selecting Stage B.
HPR-P2-005 remains COMPLETED, HPR-P2-013 PENDING; P2 OPEN/P3 DEFERRED. All HMR rows,
57 completions, 123 subjects, version 0.6.0-SNAPSHOT and historical evidence remain.
No application/migration/schema/policy/owner-value, deployment, import or release change.

## HPR-P2-006 Stage B physical dictionary and governance — 2026-10-09

Main parent `8b51b52b2aa31f7a2f0ca2b08a6066387663d092` remained unchanged;
Documentation #133 and Production #607 passed. The schema archive digest matched
GitHub; its draft regenerated exactly and migration/JPA hashes match current source.
The owner then separately selected Stage B. Only the eight admitted dictionary,
policy, ownership metadata and control paths change.

The reviewed dictionary covers 481 tables and one sequence, 5,840 catalog columns,
1,355 constraints, 3,030 indexes and 155 triggers. All 139 successful migrations and
470 JPA tables reconcile; 21 source-linked overrides leave zero unresolved owners.
Three retained unmapped legacy columns and actual prefix exceptions are documented.
All 688 physical FKs are within resolved owners; cross-module IDs/snapshots and
provider contracts do not transfer semantic ownership. Actual policy/business values
remain external approval decisions; no schema/data provision or HMR restart occurs.

Current [Stage B record](../roadmap/ULTIMATE_ROADMAP.md#hpr-p2-006-stage-b-implementation--2026-10-09)
controls older pending records. HPR-P2-006 remains IN PROGRESS pending both fresh
exact-head workflows and final integrated dictionary comparison. HPR-P2-013 PENDING;
P2 OPEN/P3 DEFERRED. All 57 HMR completions, 123 subjects, historical/P1 provenance
and version 0.6.0-SNAPSHOT remain. Next verifies publication gates before any closure
preflight; stop after CI startup for notification.

## HPR-P2-006 verification and renewed closure preflight — 2026-10-09

Main `1bc3c1bba2d08a0b493e5ece43e834e8d56d04f0` remained unchanged. Exact-head
Documentation #134 and Production #608 PASSED. Collection/final comparison reports
reviewed_dictionary_checked=true, 482 relations, zero unresolved owners and unchanged
139-migration/470-JPA source bundle. HPR-P2-006 is COMPLETED. GitHub retains the fresh
schema artifact ID 11615823318; this review inspected CI summary/steps and artifact
metadata, not a second archive download. Stage A archive/content review remains valid.

The [renewed bounded preflight](../roadmap/ULTIMATE_ROADMAP.md#hpr-p2-006-verification-and-renewed-hpr-p2-013-preflight--2026-10-09)
controls historical closure envelopes. Only the two execution memories change now.
Next after documentation CI selects read-only twelve-check re-verification; all twelve
must be VERIFIED before a separately selected fifteen-path maximum metadata closure
patch. Exact closure message and future documentation/full CI gate remain mandatory;
production dispatch must use the exact closure head. No dummy edits or premature
CLOSED disposition. HPR-P2-013 PENDING; P2 OPEN/P3 DEFERRED.

All 57 completed HMR identities including HMR-080, 123 semantic subjects, original
P1/generation applicability and 0.6.0-SNAPSHOT remain. No dictionary/owner-policy,
source/test/configuration/SQL, deployment/import/version/release or P3 change.

## HPR-P2-013 renewed audit and closure metadata — 2026-10-09

The read-only audit at `7be1c9cb47ed9b6f73a7692d0a328ad870e2b4d4`, tree
143314774f1c95eb1393c4a9b2a1737cddd14f65, returned PASS: twelve separate VERIFIED
checks. Preflight Documentation #135 passed; main remained unchanged. The owner then
separately selected the exact registered closure metadata implementation. Required
AGENTS/roadmap/reconciliation authority was read before mutation.

[Retained twelve-check evidence](../roadmap/ULTIMATE_ROADMAP.md#hpr-p2-013-renewed-audit-and-closure-metadata-implementation--2026-10-09)
records exact canonical/legacy/source/CI subjects and observed properties. It includes
24 substantive slices, 123 semantic subjects, 57 completed HMR identities, committed
OpenAPI/failing drift gate, source-scoped API/error behavior, reproducible physical
dictionary and concrete ownership/Flyway/traceability rules. Corrected Checks 9–11
supersede the prior failed content verdict without rewriting that historical audit.

Thirteen existing metadata paths change within the fifteen-path maximum. Physical
dictionary/ownership JSON, API snapshot, validators/workflows, module semantics,
legacy source, runtime/SQL/POM and operating evidence remain unchanged. All HMR rows
and completion identities, original P1/generation applicability and 0.6.0-SNAPSHOT
remain. No business approvals, deployment/import, runtime inference or field actuation
is established by the audit or metadata publication.

HPR-P2-013 is IN PROGRESS; P2 OPEN/P3 DEFERRED pending BOTH exact-head documentation
and full production CI on the closure commit. Production must use existing manual
workflow_dispatch because doc-only push is ignored. Current connector exposes no
dispatch action; owner Actions startup is required unless a supported authorized
capability becomes available. No dummy changes or premature CLOSED claim. Publish
`docs(roadmap): close P2 canonical governance`, verify files/parent/tree, observe
startup and stop for Next/failure notification; no later roadmap task executes.

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


## Phase 2.5 Registration — 2026-10-09

Owner-selected HPR-P25-000 registers the operational digital twin priority in the
Ultimate Roadmap: product-aware gas/oil/H2/other-product visualization; collected
trusted telemetry -> analytics/network-state assessment -> issue or safety/security
concern -> real local/regional/full-network simulation -> proposed operator decision
-> actual action and measured outcome -> validated AI learning. Prevent harmful
effects elsewhere from an apparently beneficial local action.

Verified parent main: 275a5d38e888ad70e33db4081ef03783e9e1e427, tree
fd8dff20c50a0193adbbc321c1a360925ded81b1; Documentation #141/run 37944111978
PASSED. Existing topology/map, raw/trusted telemetry and simulation metadata are
foundations; runtimeDigitalTwin returns false and the solver availability port
is not executing hydraulic simulation. This registration changes no such behavior.

HPR-P25-000 exact message: `docs(roadmap): register phase 2.5 operational digital twin`.
Write ONLY doc/roadmap/ULTIMATE_ROADMAP.md and doc/model-remediation/RECONCILIATION.md.
HPR-P25-001..017 are PENDING; Phase 2.5 PLANNED/OPEN, runtime NOT ESTABLISHED.
Next recommended task is HPR-P25-001, requirements/source gap and execution
preflight; when selected it writes only these same two execution memories.
Subsequent implementation requires registered exact file/migration/owner/validation
envelopes, approved product/mode and solver/data evidence; missing decisions block
affected tasks. Supporting preflight message is
`docs(twin): register HPR-P25-NNN execution preflight`, substituting the task number.

The roadmap retains per-product/mode physics validation, connected-scope boundary
conditions, adverse effects elsewhere, real solver evidence, operator review,
independent urgent safety/security response, actual-action outcome attribution,
offline learning evaluation/promotion/rollback and separate integrated frontend
evidence as acceptance gates. No direct OT actuation is authorized. P3 technologies
remain deferred and need their own measured requirements/selection.

Validate canonical documentation, offline OpenAPI, P1 evidence, whitespace and
exact two-file scope. Publish using the verified-parent expected-head lease, verify
parent/tree/files and observe Documentation Validation startup, then STOP for owner
Next/Fail. No full runtime campaign is claimed for this documentation-only change.
P0/P1/P2 CLOSED, all 57 HMR completions and 123 semantic subjects are preserved;
no HMR is reopened. Formal v0.6.0 and 0.7.0-SNAPSHOT remain unchanged.

Actual local registration checks PASSED: canonical documentation validation (95 documents,
5,063 relative links, 24 modules, 13 P2 rows), offline OpenAPI validation
(244 paths, 263 operations, 231 schemas), P1 closure evidence validation and
`git diff --check`. Exactly two documentation files changed. These checks do not
establish runtime twin implementation; exact-head Documentation CI follows publication.


## HPR-P25-001 Requirements Preflight — 2026-10-09

Owner Next selects source review and requirements preflight after Documentation
#142/run 37948108881 PASSED on exact registration SHA
02e86faa48c49c0c83c2ad51ccc4f1c52686f6c4, tree
2f0354ef2677706c9d67a267ea3419120086ed21. Read AGENTS.md, Ultimate Roadmap
and this reconciliation before mutation; verified local source tree is identical.

Exact message: `docs(twin): define requirements and execution preflight`. Write
ONLY doc/roadmap/ULTIMATE_ROADMAP.md and doc/model-remediation/RECONCILIATION.md.
Review delivered; HPR-P25-001 BLOCKED because no approved first demonstrator,
physical modes, solver/access, input ownership or operational acceptance values
have been supplied. Ultimate Roadmap governing rule 13 applies.
The earlier PENDING selection is historical, not current execution permission.

The roadmap now contains the exact-source ownership/gap matrix, requested
gas/oil/H2/blends/other-product coverage, physical-input requirements, unresolved
D25-01..09 decisions, F25-01..10 fixture specifications, solver integration
alternatives without selecting an engine, and bounded implementation envelopes.
Existing nominal diameter/design pressure must not silently become internal
diameter/approved operating limit. Persistence node/segment inventory is not
a current domain aggregate. Custody Planning product export is id/code/active,
not a Simulation fluid model. NoopExternalConnectorGateway returns true without
acquisition; SimulationSolverPort only checks available(referenceId) with no
implementation found. runtimeDigitalTwin remains false. Existing severity
classification is not approved multi-product detection accuracy.

Ownership proposals retain Topology connectivity/configuration, Custody product
identity, Telemetry trust, Integration read-only acquisition, Analytics derived
state/outcomes/learning, Simulation immutable physical inputs/execution/predictions
and Workflow/Audit actual-action decisions/evidence. New contracts and exports
require bounded exact-file preflight; no foreign JPA access or new module is approved.

First resolve D25-01..04: demonstrator, physical modes, engine/access and input
ownership. Subsequent source/field thresholds, product/mode validation data, issue
policy, operator environment/outcome windows and learning promotion/rollback need
D25-05..09. F25 fixtures are specifications only, not executed acceptance tests.
HPR-P25-002..017 remain PENDING and cannot bypass their prerequisite decisions.
Supporting decision record exact message:
`docs(twin): record approved phase 2.5 input decisions`; same two-file write scope.
No owner approval is invented. This record must be amended with actual decisions
before implementation selection; no implementation is automatically selected.

Validate canonical documentation, offline OpenAPI, retained P1 evidence, whitespace
and exact two-file scope; preserve P0/P1/P2/REL registry rows and all HMR subjects.
Publish with expected-parent lease, verify parent/tree/files, observe Documentation
Validation startup and STOP for owner Next/Fail. No runtime/physics execution
is claimed. P0/P1/P2 CLOSED, P2.5 OPEN, P3 DEFERRED, 57 HMR completions,
123 subjects, formal v0.6.0 and development 0.7.0-SNAPSHOT are unchanged.

Actual local HPR-P25-001 checks PASSED: canonical documentation (95 documents,
5,063 relative links, 24 modules, 13 P2 rows), offline OpenAPI (244 paths,
263 operations, 231 schemas), P1 closure evidence and git diff --check.
Exactly two documentation files change; exact-head Documentation CI follows
publication. No runtime, solver or acceptance-fixture execution is claimed.


## Approved Phase 2.5 Direction and Dynamic Inputs — 2026-10-09

Owner Next selects `docs(twin): record approved phase 2.5 input decisions` after
selecting gas, GZ2, both steady-state/transient modes and Hidra's own simulation
engine, then clarifying that input data changes. Parent main
d65cba71421ed5c994a8f63864754aa6083b7837, tree
8991a8b4ceed382cd324a85b554dfd3f061c646e passed Documentation #143/run 37953635173.
Read mandatory AGENTS/roadmap/reconciliation; local source tree matches parent.
Write ONLY doc/roadmap/ULTIMATE_ROADMAP.md and this reconciliation.

HPR-P25-001 is COMPLETED as source/requirements preflight delivery with decisions
and explicit remaining qualification gates; historical BLOCKED direction is superseded.
D25-01 pilot gas/GZ2, D25-02 both modes and D25-03 own-engine direction are owner
selected. No exact GZ2 topology, curves, numeric limits, physics formulation, license
or operational readiness is inferred. D25-04 remains bounded technical contract
design for HPR-P25-002; D25-05..09 remain applicable to their field/operational claims.

Version network/configuration, gas/model parameters/current curves/limits and trusted
operating measurements with effective times, units and provenance. Each run pins an
immutable coherent snapshot and engine version; transient runs also pin initial state
and time-dependent boundary/action schedules. Corrections create new versions/runs;
keep measured history, estimated state, forecasts and scenario actions distinguishable.
Do not hardcode GZ2 or treat all parameters as changing daily.

Actual GZ2 data gates calibration, pilot acceptance and operational recommendations,
not input-contract/engine development using clearly synthetic reference networks.
Independent numerical verification and later measured physical validation are distinct.
No synthetic result establishes GZ2 pressure/flow/capacity or safety performance.

Internal-engine scope is decomposed into six PENDING prerequisites HPR-P25-008A..F:
numerical design, steady-state implementation, equipment behavior, transient
implementation, independent numerical verification and lifecycle execution adapter.
The roadmap registers each exact message; later exact-file preflight is mandatory.
Existing HPR-P25-008 identity/message remains for final governed execution;
HPR-P25-009 remains distinct GZ2 calibration. HPR-P25-017 closure must include all
added prerequisites. Both gas modes are required; all other product targets remain
and need separate physical qualification. No engine code or method is implemented here.

Next recommended task: HPR-P25-002 documentation-only input-contract preflight,
exact message `docs(twin): register HPR-P25-002 execution preflight`, same two-file
scope. Define ownership/export, immutable versioned inputs and bounded implementation
file/migration/test envelopes using representative synthetic gas networks. No further
stage is automatically selected. P0/P1/P2 CLOSED, P2.5 OPEN, P3 DEFERRED, all 57
HMR completions, 123 subjects, formal v0.6.0 and 0.7.0-SNAPSHOT remain preserved.
Validate canonical documentation/OpenAPI/P1 evidence, whitespace, scope and retained
registry identities; publish with expected-parent lease, verify immutable parent/tree/files,
observe Documentation Validation startup and STOP for owner Next/Fail.

Actual decision-record validation PASSED: canonical documentation (95 documents,
5,063 relative links, 24 modules, 13 P2 rows), offline OpenAPI (244 paths,
263 operations, 231 schemas), P1 closure evidence and git diff --check.
Exactly two documentation files change; prior phase/HMR registry identities are
preserved. Exact-head Documentation CI follows publication; no engine execution
or GZ2 physical validation is claimed.


## HPR-P25-002 Input-Contract Preflight — 2026-10-09

Owner Next selects `docs(twin): register HPR-P25-002 execution preflight` after
Documentation #144/run 37955963870 PASSED on exact parent
61c1311f458e6844d28c4b4d162b0916885d01a0, tree
774e1806a8953b5582dcb32081256449d5d290de. Mandatory AGENTS/roadmap/reconciliation
read and local tree matches. Write ONLY the roadmap and this reconciliation.

Revalidated Topology Simulation scope/target exports and ArchitectureGuardrailTest.
Scope resolver currently supports active PIPELINE/PIPELINE_SYSTEM, rejects
SEGMENT_GROUP/FACILITY_NETWORK; it does not export complete physical inputs.
TopologySnapshotJpaEntity carries version/JSON payload; SimulationInputSnapshotJpaEntity
only references snapshots/captureHash and has no current corresponding domain record.
Equipment/node/segment structures do not establish physical curves. Custody product
identity and Telemetry trust remain owned by their existing modules.

Technical design retains owner truth and introduces Simulation-owned immutable
manifest/payload copies. Manifest captures source owner/id/revision/digest/evidence,
origin, recorded/effective times, mode, product/scope, initial state/capture time,
watermark and transient horizon/schedule. Effective intervals are start-inclusive/end-
exclusive. Corrections create new versions. Metadata consistency does not establish
actual hash integrity, physical completeness, trust or numerical readiness; later
capture validates actual evidence/payloads. Synthetic fixtures enable development
without hardcoding GZ2 or treating field data as a prerequisite for pure contracts.

Parent HPR-P25-002 remains PENDING. Register four PENDING stages: 002A immutable
manifest; 002B typed physical payloads; 002C owner queries/adapters; 002D reproducible
capture/persistence. Each retains the exact message in the roadmap. Phase closure
includes all four stages in addition to the six engine stages.

Next recommended selection after successful preflight Documentation CI: HPR-P25-002A,
exact message `feat(simulation): establish immutable input manifest contracts`.
Create ONLY Simulation domain/value/SimulationInputMode.java,
domain/model/SimulationInputSourceVersion.java, domain/model/SimulationInputManifest.java
under src/main/java/dz/sh/hidra/modules/simulation and
src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationInputManifestTest.java.
Update ONLY the roadmap and this reconciliation. Total six-path scope. Canonical headers,
local InvalidSimulationValueException, immutable Java records/nested enums and defensive
list copies; no foreign/Spring/JPA imports. No migration: nothing persisted or exposed.
No architecture export/API/schema/dependency/workflow/other production change admitted.

A tests cover valid fixed-time synthetic cases and invalid identity/digest/schema,
validity/recorded/capture/state/watermark ordering, duplicate/missing source kinds,
mode/horizon/schedule consistency, defensive immutability and synthetic detection.
Exact commands: ./mvnw -B -q -Dtest=SimulationInputManifestTest,ArchitectureGuardrailTest test,
then ./mvnw -B -q clean verify, canonical docs/OpenAPI/P1 evidence and git diff --check.
Applicable exact-head full CI is required; report local blocks, never invent runtime
verification. Observe CI startup and STOP. Do not execute A during this preflight.
No repeat A preflight is needed unless evidence changes. B/C/D require their own
exact-file/schema preflights with `docs(twin): register HPR-P25-002X execution preflight`.

Actual GZ2 data, limits, sources and solver physical validation remain field qualification
gates. Manifest success is not an executing engine. P0/P1/P2 CLOSED, P2.5 OPEN,
P3 DEFERRED, 57 HMR completions, 123 subjects, v0.6.0 and 0.7.0-SNAPSHOT preserved.
Validate this two-document preflight with canonical docs/OpenAPI/P1 evidence, whitespace
and scope/registry checks; publish using expected-parent lease, verify parent/tree/files
and observe Documentation CI startup, then STOP for owner Next/Fail.

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


## HPR-CI-001 Production CI Optimization — 2026-10-09

Owner confirms CI results and explicitly selects optimization before further product
work. GitHub confirms full #612/run 37958649053 and Documentation #146/run
37958648746 PASSED on parent eef35dbd35d4558a4ceb57f87d8670fb97c933ea,
tree 7951dd38b554f6260575bd942e8b07d726c53d02. HPR-P25-002A is COMPLETED;
14 JUnit tests are present but no per-class execution/skip report was independently
inspected. Source/runtime implementation is unchanged by this maintenance task.

Register HPR-CI-001 / `perf(ci): parallelize isolated test JVM execution`. Write
ONLY .github/workflows/ci.yml, the Ultimate Roadmap and this reconciliation.
Measured #612 job 113915537522: total 726s; Maven 592s; current OpenAPI 31s;
historical base generation 51s; service setup 23s; dictionary capture 10s.
Source inspection confirms per-class Testcontainers/dynamic ports; no fixed shared
PostgreSQL port, filesystem mutation, ProcessBuilder or global system-property
mutation found in tests. Cache is already enabled. Preserve all checks and
clean verify; use two isolated reused JVM forks, no method/thread parallelism.
Record per-class Surefire XML/timing plus aggregate test/failure/error/skip counts
with always-run diagnostics. Do not skip tests or share mutable database containers.

HPR-CI-001 remains IN PROGRESS pending optimized exact-head full CI and actual
performance/report evidence. Additional concurrent memory/CPU/container demand
requires observed verification; no speedup is claimed before measurement.
Same-scope rollback to one fork is permitted if optimized-run evidence requires it.
No POM/source/test/migration/dependency/API/release-workflow or product task changes.
Keep full OpenAPI equality/backward compatibility/current dictionary/P1/operations
gates and existing compatibility-base selection. Validate workflow syntax and retained
step invariants plus canonical docs/OpenAPI/P1 checks and exact three-file scope.
Publish with expected-head lease, verify parent/tree/files, observe both workflows
starting and STOP. After passing optimization evidence, resume HPR-P25-002B
physical-payload preflight only on owner Next. P0/P1/P2 CLOSED, P2.5 OPEN,
P3 DEFERRED, 57 HMR completions, 123 subjects and 0.7.0-SNAPSHOT preserved.

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


## HPR-P25-002B1 Physical Network Input Implementation — 2026-10-09

Owner selects ONLY HPR-P25-002B1, exact message
`feat(simulation): define physical network input contracts`. GitHub confirms main
9a94345dc8f578dfe39f1ddeb2e845e62b375545, tree
a5eb747471cf278f286eeee83edb2d43244ae899, and exact-head Documentation
#149/run 37963444826 completed SUCCESS. Mandatory AGENTS/roadmap/reconciliation
and published B1 envelope read; local source tree matches. No changed-source
reason to repeat preflight was found.

Created ONLY Simulation domain/model/SimulationNetworkNodeInput.java,
SimulationPipeSegmentInput.java and SimulationPhysicalNetworkInput.java under
src/main/java/dz/sh/hidra/modules/simulation, plus
src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalNetworkInputTest.java.
Updated ONLY the Ultimate Roadmap and reconciliation: six tracked paths.
Records reuse InvalidSimulationValueException and import only Java/local Simulation.
Canonical headers retain author/creation date and set UpdatedOn 2026-10-09.

Contracts require explicit SI elevation, length, internal diameter and roughness;
signed/zero elevation and zero roughness are valid. IDs use the existing trim-based,
case-preserving normalization. No missing value becomes zero; no nominal diameter
or design pressure becomes a physical/operating input. Defensive immutable lists
require at least two nodes and one pipe; duplicate normalized IDs, null entries,
dangling endpoints, isolated nodes and disconnected components are rejected.
Undirected connectivity preserves original pipe orientation; cycles, meshes and
distinct parallel pipes are accepted. This pipe-only graph is not complete station/
equipment modeling, an executing solver, operational readiness or a complete GZ2 model.

Thirteen JUnit tests prepared for synthetic single-pipe, branched, meshed/parallel
networks; identity normalization and every missing identity position; required and
invalid physical values; self-loops; null/empty/undersized lists and null entries;
duplicate IDs; either dangling endpoint; isolation and disconnected non-isolated
components; both defensive lists and immutable replacement topology/physical values.
No actual GZ2 parameters, operational limits or numerical accuracy claims introduced.

Actual validation:
- Wrapper is not executable; used bash without changing tracked permissions.
- bash ./mvnw -B -q -Dtest=SimulationPhysicalNetworkInputTest,SimulationInputManifestTest,ArchitectureGuardrailTest test
  and bash ./mvnw -B -q clean verify both BLOCKED before compilation by uncached
  Spring Boot parent 4.1.1 and repo.maven.apache.org temporary DNS failure.
  Local runtime is Java 17.0.20, not required Java 21. No Maven/JUnit/ArchUnit pass claimed.
- Three production records and existing exceptions compiled through Java 17
  jdk.compiler/com.sun.tools.javac.Main. External temporary smoke harness PASSED
  50 checks for values, graph validity, orientation, copying and revision replacement.
  This is not execution of the JUnit tests or full repository verification.
- Canonical documentation, offline OpenAPI, retained P1 closure evidence and whitespace/
  exact six-path checks PASSED. Historical phase/HMR registries and semantic catalogue
  preserved; no POM/dependency/API/OpenAPI/dictionary/schema/migration/workflow,
  owner module, Kernel/Platform or architecture export change.

B1 remains IN PROGRESS pending exact-head full CI; parent B/002 remain PENDING.
B2/B3/B4 and engine stages were not executed. Next registered selection after B1 CI
success is B2 documentation-only exact-file preflight:
`docs(twin): register HPR-P25-002B2 execution preflight`.
Publish with expected-parent lease, verify remote parent/tree/six paths, observe both
production and Documentation CI startup on exact implementation head and STOP.
P0/P1/P2 CLOSED, P2.5 OPEN, P3 DEFERRED, 57 HMR completions, 123 semantic subjects,
formal v0.6.0 and development 0.7.0-SNAPSHOT remain preserved.


## HPR-P25-002B2 Fluid and Equipment Input Preflight — 2026-10-09

Owner Next selects ONLY the registered documentation preflight, exact message
`docs(twin): register HPR-P25-002B2 execution preflight`. Write ONLY
`doc/roadmap/ULTIMATE_ROADMAP.md` and `doc/model-remediation/RECONCILIATION.md`.
GitHub main is 803efff1326cc20bc54b4a22d6daa8608b5233dc, tree
ae8508a1a613adfac5b45cf7d1b4557191eb13ff. Exact-head production
#614/run 37965240956 and Documentation #150/run 37965240996 completed SUCCESS.
Production job 113937836513 confirms Java 21 repository verification, retained
operations/P1 checks, OpenAPI publication/equality/backward compatibility and
migrated dictionary steps succeeded. B1 is COMPLETED; 13 test methods are present,
but no independent per-class XML/skip inspection is claimed. Mandatory AGENTS,
roadmap/reconciliation and current selected source read; local source tree matches.

### Current source and engineering boundary

Source reviewed: SimulationInputManifest.java, SimulationInputSourceVersion.java,
SimulationPhysicalNetworkInput.java, SimulationNetworkNodeInput.java and
SimulationPipeSegmentInput.java in Simulation domain/model; Topology domain/model/
Equipment.java; Custody application/contract/planning/PlanningProductReferenceContract.java;
Simulation domain/policy/SimulationSafetyPolicy.java; ArchitectureGuardrailTest.java.
Existing manifests pin FLUID_MODEL/EQUIPMENT_PARAMETERS references and declared
origins/evidence/validity, but not physical contents. Topology Equipment has identity,
attachment/type/status, not a current measured compressor map or valve characteristic.
Custody's existing Planning contract supplies catalogue identity/code/active only;
it is neither a gas composition nor a Simulation export. B2 imports no owner module
and does not extend that contract or architecture exports. Owner adapters remain 002C.

Simulation copies source-selected, immutable gas/equipment payloads. Catalogue,
connectivity and raw/trusted observation ownership stay unchanged. Daily correction
creates a replacement source revision; it cannot mutate prior records. Source-version
metadata conveys declared provenance/validity, not verified physical truth/hash integrity.
No inferred design curve, catalogue-derived composition or default operating limit.
Actual current GZ2 curves/compositions and reference evidence remain calibration gates.

### Exact B2 implementation envelope for a later owner Next

After successful Documentation CI on this preflight, the next owner Next selects ONLY
HPR-P25-002B2, exact implementation message
`feat(simulation): define fluid and equipment input contracts`.
Create ONLY these four production files under
`src/main/java/dz/sh/hidra/modules/simulation/domain/model/`:

1. `SimulationGasFluidInput.java`
   Immutable record fields: String id, SimulationInputSourceVersion sourceVersion,
   String productReference, String propertyMethodReference, String propertyMethodRevisionId,
   String propertyMethodEvidenceReference, List<Component> components.
   Nested immutable Component record: String componentReference, BigDecimal moleFraction.
   Require sourceVersion.kind FLUID_MODEL; all identities/evidence nonblank with existing
   trim/case-preserving normalization. Require nonempty defensive components, no null
   entries or duplicate normalized componentReference. Each fraction must be >0 and <=1;
   exact BigDecimal sum numerically equals 1 using compareTo, not scale-sensitive equals.
   This is a declared complete mole-fraction basis, not rounded measured fractions:
   reject incomplete sums instead of inventing normalization or tolerance. Acquisition
   rounding/conversion evidence requires later owner/capture design. Omitted constituents
   are not known zeros. No hardcoded chemistry/component list, molecular weights, density,
   viscosity, compressibility, heating value, equation of state or property method default.
   Method reference/revision/evidence is required metadata, not installed capability.

2. `SimulationCompressorCurveInput.java`
   Immutable record fields: String id, SimulationInputSourceVersion sourceVersion,
   String fluidInputId, String fluidRevisionId, String headDefinitionReference,
   String efficiencyDefinitionReference, String interpolationMethodReference,
   BigDecimal referenceInletPressurePascalsAbsolute,
   BigDecimal referenceInletTemperatureKelvin, List<SpeedLine> speedLines.
   Require EQUIPMENT_PARAMETERS source kind, required normalized identities/definition/
   interpolation references, strictly positive reference pressure/temperature and
   nonempty defensive speedLines. A source evidence reference is already mandatory in
   SimulationInputSourceVersion; a design/nameplate map is not assumed to be current.
   Nested SpeedLine: BigDecimal rotationalSpeedRevolutionsPerMinute, List<Point> points.
   Positive speed; nonempty immutable points with at least two points; reject duplicate
   numerically equal speeds even with different BigDecimal scales. Input speed lines
   must be strictly ascending; never silently sort supplied evidence.
   Nested Point: BigDecimal massFlowKilogramsPerSecond, BigDecimal specificHeadJoulesPerKilogram,
   BigDecimal efficiencyFraction. Require positive mass flow, nonnegative specific head,
   efficiency >0 and <=1; per-line mass flows strictly ascending by numerical compareTo.
   No monotonic-head assumption. Reference inlet/fluid/definition metadata prevents
   unlabeled curve ordinates from masquerading as interchangeable head/efficiency types.
   This stage stores declared data; it does not convert corrected/actual/standard flow,
   compute head, select polytropic/isentropic models, interpolate/extrapolate, establish
   surge/choke boundaries, or claim a speed/pressure/temperature applicability envelope.
   Bounds, corrections and actual method compatibility remain numerical/capture gates.

3. `SimulationEquipmentInput.java`
   Immutable record fields: String id, String fromNodeId, String toNodeId,
   Kind kind, CompressorConfiguration compressor, ValveConfiguration valve.
   Nested enum Kind ONLY COMPRESSOR and VALVE. Require distinct nonblank normalized
   endpoints and exactly the matching configuration, rejecting null/foreign configuration.
   Nested CompressorConfiguration: String curveId, String curveRevisionId,
   BigDecimal configuredSpeedRevolutionsPerMinute. Required curve identity/revision;
   positive configured speed. This is a selected model configuration, not a measured
   running-state claim or start/stop instruction. Off/bypass/transient behavior comes later.
   Nested ValveConfiguration: String characteristicReference, String characteristicRevisionId,
   String characteristicEvidenceReference, BigDecimal configuredOpeningFraction.
   Required reference/revision/evidence, opening >=0 and <=1 (fully closed/open accepted).
   Reference-only characteristic does not prove an executing valve law or imply an
   invented Kv/Cv unit basis. Characteristic resolution, pressure-loss law, isolation
   and bypass topology require later numerical/assembly design; no readiness method.
   No actuator methods, commands, owner status translation or arbitrary operating limits.

4. `SimulationEquipmentModelInput.java`
   Immutable record fields: String id, SimulationInputSourceVersion sourceVersion,
   List<SimulationEquipmentInput> equipment, List<SimulationCompressorCurveInput> compressorCurves.
   Require EQUIPMENT_PARAMETERS source kind, nonblank identity, defensive required lists,
   no null entries or duplicate normalized equipment/curve identities. Explicit empty
   equipment and empty curves is a valid declared pipe-only model, never a default for
   absent actual data. Reject nonempty curves when equipment is empty. Each compressor
   configuration must resolve curveId and curveRevisionId to a contained curve's id and
   sourceVersion.revisionId; distinct equipment may share one pinned curve. Do not require
   all curves to be consumed, or silently replace a missing/revised curve. Retain nested
   source validity/evidence; initial-state/capture coherence across model and curve sources
   is checked later by B4/002D, not guessed without a manifest/state time.

Create ONLY test file
`src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationFluidEquipmentInputTest.java`.
Update ONLY the two execution memories. Total B2 implementation scope: seven tracked paths.
All BigDecimal values required; no floating-point conversion, missing-to-zero fallback,
Spring/JPA/foreign imports or utility package. Reuse InvalidSimulationValueException.
Canonical Java headers preserve Author Abir MEDJERAB, CreatedOn 2025-06-26 and current
UpdatedOn. Private helpers/nested records/enums stay within the four admitted records.
No migration: payloads are neither persisted nor API-exposed. Do not modify B1/manifest,
Topology/Custody/Telemetry/other owner production, APIs/OpenAPI/dictionary/schema,
POM/dependencies/workflows, Kernel/Platform, architecture exports or existing tests.

### Assembly and graph limits

B2 is a bounded declared-data representation, not a complete gas solver contract.
B4 must bind fluid id/revision and model/curve source versions to the selected manifest,
validate actual node references, temporal coherence and supported method semantics.
B1 validates a connected PIPE-ONLY graph: equipment-only joins, compressor/valve cuts,
closed valves and bypasses cannot be represented as invented pipes or missing-value
geometry. B4's later preflight must explicitly address any graph-schema extension and
its exact-file authority before modifying B1. B2 does not require equipment endpoints
already connected by an artificial pipe, and does not validate whole-network physics.
Do not claim complete station/equipment/GZ2 modeling from B1+B2, or hide unrepresented
connectivity/characteristic data behind a successful record constructor.

### Required meaningful synthetic tests and checks

Test complete single-component and multicomponent mixtures including differing decimal
scales; no hardcoded GZ2 data. Reject missing identities/method/evidence/source/wrong
source kinds, missing/empty/null/duplicate components, missing/nonpositive/out-of-range
fractions and sums below/above one. Cover valid multi-speed/nonmonotonic-head maps,
invalid reference pressure/temperature, missing definitions/interpolation, null/empty
speed/point lists, insufficient points, duplicate/descending speeds or mass flows,
missing/invalid ordinates/efficiency; preserve supplied values and order.
Cover both equipment kinds, configuration exclusivity, normalized self-loops,
missing/invalid speed/opening, opening 0/1 boundaries, explicit empty equipment model,
duplicate/null identities, dangling and revision-mismatched curves and shared curves.
Verify all nested defensive lists and source-list mutation resistance; constructing
replacement fluid/equipment/curve revisions leaves prior source/physical values intact.
Do not assert measured curve accuracy or solver readiness from synthetic success.

For later B2 implementation run:
`./mvnw -B -q -Dtest=SimulationFluidEquipmentInputTest,SimulationPhysicalNetworkInputTest,SimulationInputManifestTest,ArchitectureGuardrailTest test`
then `./mvnw -B -q clean verify`, canonical docs/OpenAPI/P1 validators, whitespace,
exact seven-file scope and preserved registry/version checks. If wrapper is not executable,
use bash without permission changes. Report environment/dependency failures accurately.
Publish with expected-parent lease, verify remote parent/tree/paths, observe both full
production and Documentation CI on the exact implementation head and STOP. B3 requires
its already registered documentation-only exact-file preflight; do not execute it early.

This current preflight runs canonical docs/OpenAPI/P1 validation, maintained validator
tests, whitespace, exact two-document scope and preserved phase/HMR/version checks.
No Maven/runtime rerun required solely for this documentation change. B2 and parent
B/002 remain PENDING; no B2 record or physics implementation created in this turn.
Publish this preflight with expected-parent lease, verify parent/tree/two paths,
observe Documentation CI startup and STOP for owner Next/Fail. Do not repeat this
B2 preflight unless source evidence changes. P0/P1/P2 CLOSED, P2.5 OPEN, P3 DEFERRED,
57 completed HMR implementations, 123 subjects, v0.6.0 and 0.7.0-SNAPSHOT preserved.


Actual B2 preflight checks PASSED: all 37 maintained validator tests (10 docs,
10 OpenAPI, 17 dictionary); canonical docs (95 documents, 5,063 relative links,
24 modules, 13 P2 rows); offline OpenAPI (244 paths, 263 operations, 231 schemas);
P1 closure evidence and whitespace. Exact two-document scope and preserved phase/
HMR registries/version verified. B1 completion is supported by exact-head CI;
B2 implementation, characteristic resolution and numerical/field qualification
remain pending. Supporting preflight Documentation CI follows publication.


## HPR-P25-002B2 Fluid and Equipment Input Implementation — 2026-10-09

Owner Next selects ONLY B2 after GitHub-confirmed Documentation #151/run 37966757118
completed SUCCESS on exact parent de3849c1438c008e2500eb44f18e902849b6985f,
tree cb0775d44d6a56f1568cde5a176d951c37f4b787. Mandatory AGENTS/roadmap/
reconciliation and registered B2 envelope read; local source tree matches.
Exact message: `feat(simulation): define fluid and equipment input contracts`.

Created ONLY SimulationGasFluidInput.java, SimulationCompressorCurveInput.java,
SimulationEquipmentInput.java and SimulationEquipmentModelInput.java in
src/main/java/dz/sh/hidra/modules/simulation/domain/model, plus
src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationFluidEquipmentInputTest.java.
Updated ONLY Ultimate Roadmap and reconciliation: seven tracked paths total.
Canonical headers retained; local InvalidSimulationValueException reused;
production imports only Java/local Simulation. No migration because records are
neither persisted nor exposed. No existing source/test/owner/API/OpenAPI/dictionary/
schema/POM/dependency/workflow/Kernel/Platform/architecture-export change.

Fluid contract enforces complete exact mole-fraction sums with numeric BigDecimal
comparison, positive component fractions, explicit product/method/revision/evidence,
correct source kind and unique normalized component references. Curve contract retains
explicit fluid/revision/head/efficiency/interpolation references and positive reference
absolute pressure/temperature; immutable speed lines/points require strictly ascending
numeric speeds/flows, nonnegative head and efficiency >0 and <=1. No automatic sorting,
normalization, property calculation, interpolation/extrapolation or method selection.
Equipment has distinct endpoints and exactly matching compressor/valve configuration;
positive configured compressor speed and valve opening 0..1 are explicit selected
model data, not measured running state or actuation instructions. Equipment model
resolves exact contained curve revisions, rejects duplicate/null identities and
preserves intentionally empty pipe-only models, shared curves and unused declared curves.
All nested lists are defensive/unmodifiable; replacements retain earlier records.

Nineteen synthetic JUnit tests prepared for complete single/multiple mixtures with
scale-independent sums; missing/wrong source kinds and all identity/method/evidence
positions; invalid/missing/duplicate/incomplete composition; multi-speed/nonmonotonic
head maps; invalid/missing curve references/quantities/lists and speed/flow order;
configuration exclusivity/self-loops/identity/value boundaries; explicit empty models,
shared/unused curves, duplicate/null/dangling/revised references; every defensive nested
list and replacement composition/curve/equipment revisions. No actual GZ2 data used.

Actual validation:
- Non-executable wrapper invoked with bash; tracked permissions unchanged.
- bash ./mvnw -B -q -Dtest=SimulationFluidEquipmentInputTest,SimulationPhysicalNetworkInputTest,SimulationInputManifestTest,ArchitectureGuardrailTest test
  and bash ./mvnw -B -q clean verify BLOCKED before compilation: uncached Spring Boot
  parent 4.1.1, repo.maven.apache.org temporary DNS failure. Local runtime is Java
  17.0.20, not required Java 21. No Maven/JUnit/ArchUnit pass claimed locally.
- Four new records plus existing source-version/exceptions compiled through Java 17
  jdk.compiler/com.sun.tools.javac.Main. Temporary external smoke harness PASSED 81
  value/graph-reference/configuration/immutability checks. Not JUnit/full verification.
- All 37 maintained validator tests PASSED; canonical docs, offline OpenAPI, P1 closure
  evidence, whitespace, exact seven-path and retained registry/version checks PASSED.

B2 remains IN PROGRESS pending applicable exact-head CI; B/002 remain PENDING.
No solver, station connectivity, valve law, surge/choke/operating envelope or GZ2
qualification claimed. Graph-schema extension and fluid/source/time binding remain
B4 preflight/assembly work; property/characteristic compatibility and numerical
qualification remain engine/capture gates. B3/B4 and later engine stages not executed.
Next registered selection after successful B2 CI is documentation-only B3 exact-file
preflight: `docs(twin): register HPR-P25-002B3 execution preflight`.
Publish with expected-parent lease, verify remote parent/tree/seven files, observe both
production/Documentation startup on the exact head and STOP for owner Next/Fail.
P0/P1/P2 CLOSED, P2.5 OPEN, P3 DEFERRED, 57 completed HMR implementations,
123 semantic subjects, v0.6.0 and 0.7.0-SNAPSHOT preserved.


## HPR-P25-002B3 State and Boundary Timeline Preflight — 2026-10-09

Owner Next selects ONLY `docs(twin): register HPR-P25-002B3 execution preflight`.
Write ONLY doc/roadmap/ULTIMATE_ROADMAP.md and doc/model-remediation/RECONCILIATION.md.
GitHub main 97332bba3861348a6d4acf2aa995d1ad537cbc1c, tree
0c54e3849c0a8877280f1b6c533d76056c49339c, passed exact-head production
#615/run 37967717239 and Documentation #152/run 37967717190.
Job 113946166670 confirms Java 21 repository verification and retained P1/operations,
OpenAPI equality/backward compatibility, dictionary and report-retention steps succeeded.
B2 is COMPLETED. Nineteen JUnit methods are source-visible; individual XML/skip counts
not independently inspected. Mandatory instructions and execution memories read;
local tree matches before mutation. No source change selected in this preflight.

### Source findings and boundary

Reviewed SimulationInputManifest/SimulationInputSourceVersion, B1 physical graph and
B2 gas/equipment input records; Telemetry domain/model/TelemetryReading.java and
TrustedTelemetryReading.java; ArchitectureGuardrailTest.java owner-export boundaries.
Manifest has mode/stateAt/capturedAt/measurementWatermark/horizonEnd and exactly one
OPERATING_STATE plus transient BOUNDARY_SCHEDULE source reference. It has no numeric
initial state or schedule. Raw/trusted readings retain source timestamps, quality/
trust/assessment provenance and generic values/units; they are not solver-ready SI
quantities and must not be foreign-domain imports into Simulation. Existing B2 configured
speed/opening is selected model data, not actual measured running state.

B3 defines immutable Simulation copies, not telemetry trust rules, an estimator,
actuator commands or owner API adapters. Explicit quantity names prevent absolute/
gauge pressure or actual/standard volumetric/mass flow confusion. No missing value
becomes zero and no quality/freshness/accuracy/operating threshold is invented.
Later 002C/002D must retain conversion/reference/trust evidence and resolve sources.

### Exact later B3 implementation envelope

After successful preflight Documentation CI, owner Next selects ONLY B3, exact message
`feat(simulation): define state and boundary timeline inputs`.
Create ONLY four files under
src/main/java/dz/sh/hidra/modules/simulation/domain/model/:

1. SimulationStateQuantityInput.java
   Immutable record fields: TargetKind targetKind, String targetId, Quantity quantity,
   Knowledge knowledge, BigDecimal value, Instant valueAt, Instant evidenceRecordedAt,
   String evidenceReference. Nested enums TargetKind NODE/PIPE/EQUIPMENT;
   Knowledge MEASURED/ESTIMATED/SYNTHETIC/UNKNOWN. Require all enums and normalized
   nonblank target/evidence identity (existing trim, case preserved).
   Nested Quantity enum EXACT values and legal targets/ranges:
   PRESSURE_PASCALS_ABSOLUTE: NODE or PIPE, >0;
   TEMPERATURE_KELVIN: NODE or PIPE, >0;
   PIPE_MASS_FLOW_KILOGRAMS_PER_SECOND: PIPE, signed including zero;
   NODE_MASS_INJECTION_KILOGRAMS_PER_SECOND: NODE, signed including zero;
   COMPRESSOR_SPEED_REVOLUTIONS_PER_MINUTE: EQUIPMENT, >=0;
   VALVE_OPENING_FRACTION: EQUIPMENT, >=0 and <=1.
   Pipe flow positive follows B1 fromNodeId->toNodeId, negative reverses it. Node mass
   injection positive adds mass to scope, negative withdraws; no volumetric equivalence.
   Known values require value/valueAt/evidenceRecordedAt; valueAt <= evidenceRecordedAt.
   UNKNOWN requires all three null, with evidenceReference explaining missingness;
   do not label absent observations as measured zero. Apply target compatibility even
   for UNKNOWN; known numeric ranges via BigDecimal signum/compareTo.
   Quantity may expose package-local validation for reuse by the series constructor;
   keep validation inside this record/nested enum, no additional utility/value file.
   MEASURED is declared observation provenance, not verified trust. ESTIMATED valueAt
   is estimate's physical state time; SYNTHETIC is explicitly fixture data.

2. SimulationInitialStateInput.java
   Immutable record fields: String id, SimulationInputSourceVersion sourceVersion,
   Instant stateAt, List<SimulationStateQuantityInput> quantities.
   Require normalized nonblank id, source kind OPERATING_STATE, stateAt present,
   sourceVersion effectiveAt(stateAt), stateAt <= sourceVersion.recordedAt.
   Defensive nonempty required list, no null entries or duplicate normalized composite
   keys (targetKind,targetId,quantity). Use structural key comparison, not ambiguous
   delimiter concatenation; nested private record or equivalent local structure allowed.
   For each known quantity: valueAt <= stateAt, evidenceRecordedAt <= sourceVersion.recordedAt.
   Delayed evidence recorded after stateAt is allowed if included by source revision;
   capturedAt/watermark matching remains manifest assembly work. UNKNOWN values retained.
   Partial/unknown input is representable without claiming executable completeness.
   No fabricated node/pipe quantities, running-state inference or readiness method.

3. SimulationBoundarySeriesInput.java
   Immutable record fields: String id, SimulationStateQuantityInput.TargetKind targetKind,
   String targetId, SimulationStateQuantityInput.Quantity quantity,
   Interpolation interpolation, List<Point> points.
   Nested Interpolation enum EXACT STEP_PREVIOUS and LINEAR; required explicit choice.
   Require normalized nonblank series/target IDs, target/quantity compatibility and
   defensive points with at least two entries, no null entries. Boundary series target
   only NODE (pressure, temperature or mass injection) or EQUIPMENT (compressor speed
   or valve opening); reject PIPE boundary series. A pipe flow observation is initial
   state, not silently a node boundary. Physics-supported boundary combinations remain
   solver-design gates. Numeric validation reuses the declared Quantity ranges.
   Nested Point record fields: Instant at, BigDecimal value,
   SimulationInputSourceVersion.Origin origin, Instant recordedAt, String evidenceReference.
   Require all fields and nonblank normalized evidence. Allowed origin EXACT
   TRUSTED_TELEMETRY/SCENARIO/FORECAST/SYNTHETIC; reject ESTIMATED/APPROVED_PARAMETER
   here rather than relabel them as observed boundaries. TRUSTED_TELEMETRY point requires
   at <= recordedAt; future scenario/forecast/synthetic points may have at > recordedAt.
   Trust remains a declared origin until evidence resolved, no synthetic truth promotion.
   Point constructor checks shape/origin/time; series checks quantity-specific ranges
   and strictly ascending distinct at timestamps. No sorting/coalescing conflicting
   points. Preserve per-point origin/time/evidence; mixing future forecasts and known
   history is explicit. Initial estimates belong in initial state, not boundary history.
   STEP_PREVIOUS means value at a knot applies on [knot,nextKnot); final knot is end
   anchor. LINEAR declares interpolation between adjacent knots. No valueAt evaluator,
   interpolation/extrapolation implementation, wall-clock lookup or default is added.
   Linear controls are declared ramps, not supported real equipment dynamics or commands.

4. SimulationBoundaryScheduleInput.java
   Immutable record fields: String id, SimulationInputSourceVersion sourceVersion,
   Instant startsAt, Instant endsAt, List<SimulationBoundarySeriesInput> series.
   Require normalized nonblank id, source kind BOUNDARY_SCHEDULE, required times with
   endsAt > startsAt, sourceVersion effectiveAt(startsAt). Defensive nonempty list,
   no null entries; unique normalized series IDs and structural target/quantity keys.
   Each series first point EXACT startsAt and last point EXACT endsAt; require no
   points outside this closed data interval. EndsAt is an explicit endpoint anchor for
   horizon coverage, not license for extrapolation. No silent holding past last point,
   gap filling, empty default schedule or conversion of UNKNOWN to known.
   Every point recordedAt <= schedule sourceVersion.recordedAt. Together with measured
   point at<=recordedAt this rejects future observations invented after frozen revision.
   Planned future point times are permitted; record/evidence must already be selected.
   Source effective interval governs revision selection at startsAt, not automatic
   cancellation mid-horizon: immutable schedule payload covers its declared horizon.
   Initial state/manifest/capture/horizon equality and referenced physical IDs are B4.

Create ONLY
src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationStateBoundaryInputTest.java.
Update ONLY the two execution memories: seven tracked implementation paths total.
Canonical Java headers keep Author Abir MEDJERAB, CreatedOn 2025-06-26, current UpdatedOn.
Reuse InvalidSimulationValueException; production imports only standard Java/local
Simulation. Helpers, enums and nested records stay inside four new files. No migration:
records neither persisted nor API-exposed. No B1/B2/manifest/existing test edits,
owner-module/API/OpenAPI/dictionary/schema/POM/dependency/workflow/Kernel/Platform/
architecture export change. No state acquisition or runtime equations in this stage.

### Missing-data, completeness and follow-up gates

B3 constructor validity is not hydraulic readiness. Node/pipe scalars are not a full
spatial pipe profile; transient solver initialization/thermal state/discretization,
equipment off/bypass behavior, curves/limits and boundary well-posedness require B4
and engine preflights. Zero measured speed can represent data while B2 selected-map
configuration requires positive speed; do not infer stopped/bypass physics from this
number. Reject incompatible actual execution later rather than modifying old contracts
or fabricating additional connectivity. B4 must explicitly admit any required schema
extension. It also must propagate nested SYNTHETIC evidence and reject any misleading
real-only manifest label, validate known/unknown completeness for chosen mode and
network, bind sources/times/IDs, and distinguish forecast action from actual outcome.
No local numerical bounds here are operational thresholds or GZ2 acceptance policies.

### Meaningful synthetic tests and validation

Use fixed Instant/BigDecimal fixtures: measured/estimated/synthetic/unknown initial
quantities; every legal target/range with signed/zero flows, zero speed, opening 0/1;
blank/null identity/evidence/enums, wrong target, missing known fields, forbidden
unknown fields, invalid pressure/temperature/speed/opening, evidence/time order.
Cover source-kind/validity exclusive end, future state/evidence, empty/null/duplicate
structural keys including normalization, delayed evidence retained and partial unknowns.
Cover explicit STEP_PREVIOUS/LINEAR schedules with history/scenario/forecast/synthetic
origins; missing interpolation, unsupported origin/PIPE boundaries, invalid numeric
values, duplicate/descending timestamps, future observed point, point recorded after
source revision, absent/null/undersized series, missing/wrong horizon anchors, invalid
interval/source kind/validity and duplicate series IDs or target/quantity keys.
Prove defensive copies at all three list layers, unmodifiable access, replacement
initial-state/schedule revisions retaining earlier values/origins/times. Do not assert
interpolated output, numerical accuracy, operational completeness or measured trust.

Later implementation commands:
`./mvnw -B -q -Dtest=SimulationStateBoundaryInputTest,SimulationFluidEquipmentInputTest,SimulationPhysicalNetworkInputTest,SimulationInputManifestTest,ArchitectureGuardrailTest test`
then `./mvnw -B -q clean verify`, canonical docs/OpenAPI/P1 validators, whitespace,
exact seven-path and preserved registry/version checks. Use bash for a non-executable
wrapper without chmod; report environment/dependency failures accurately. Publish
with expected-parent lease, verify remote parent/tree/files, observe both exact-head
production/Documentation startup and STOP. B4 still requires its registered exact-file
preflight: `docs(twin): register HPR-P25-002B4 execution preflight`.

Current documentation-only preflight validates all maintained validator suites,
canonical docs/offline OpenAPI/P1 evidence, whitespace, exact two-document scope and
preserved registries/version. No local Maven/runtime rerun required solely for docs.
B3 and parent B/002 stay PENDING; no B3 record/source/solver/OT action implemented.
Publish exact preflight message with expected-parent lease, verify parent/tree/two
paths, observe Documentation CI startup and STOP for owner Next/Fail. Do not repeat
B3 preflight unless evidence changes. P0/P1/P2 CLOSED, P2.5 OPEN, P3 DEFERRED,
57 completed HMRs, 123 permanent subjects, v0.6.0 and 0.7.0-SNAPSHOT preserved.


Actual B3 preflight checks PASSED: all 37 maintained validator tests (10 docs,
10 OpenAPI, 17 dictionary); canonical docs (95 documents, 5,063 links, 24 modules,
13 P2 rows); offline OpenAPI (244 paths, 263 operations, 231 schemas); retained
P1 closure evidence and whitespace. Exact two-document scope and retained phase/
HMR registries/version verified. B2 completion rests on exact-head successful CI;
B3 source implementation and physical completeness remain pending. Supporting
preflight Documentation CI follows publication.


## HPR-P25-002B3 State and Boundary Timeline Implementation — 2026-10-09

Owner Next selects ONLY B3 after exact-head Documentation #153/run 37969289943
completed SUCCESS on parent 4f561895d89632a5f80f89ce808404e532d16f0b,
tree e2a43fcbe66c453a40ad3374a42af1152ce98011. Mandatory AGENTS/roadmap/
reconciliation and registered B3 envelope read; local source tree matches.
Exact message: `feat(simulation): define state and boundary timeline inputs`.

Created ONLY SimulationStateQuantityInput.java, SimulationInitialStateInput.java,
SimulationBoundarySeriesInput.java and SimulationBoundaryScheduleInput.java in
src/main/java/dz/sh/hidra/modules/simulation/domain/model, plus
src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationStateBoundaryInputTest.java.
Updated ONLY roadmap/reconciliation: seven tracked paths. Canonical headers retained;
production imports only Java/local Simulation, reuses InvalidSimulationValueException.
No persistence/API exposure or migration. Existing B1/B2/manifest/source/test,
owner modules, schema/API/OpenAPI/dictionary/POM/dependency/workflow/Kernel/Platform/
architecture exports unchanged.

State quantities preserve MEASURED/ESTIMATED/SYNTHETIC/UNKNOWN declarations;
known data requires numeric value/time/recorded evidence and UNKNOWN forbids all
three while retaining missingness evidence. Quantity enums enforce explicit SI basis,
legal NODE/PIPE/EQUIPMENT target combinations, signed/zero oriented pipe flow and
node injection, positive absolute pressure/temperature, nonnegative measured speed
and valve opening 0..1. No missing value defaults or operational threshold invented.
Initial-state source kind/validity, state/revision/evidence ordering and structural
unique target/quantity keys enforced; delayed evidence and partial unknowns retained.

Boundary series preserve ordered distinct points, explicit STEP_PREVIOUS/LINEAR
declarations and observed/scenario/forecast/synthetic origin/evidence. Future observed
points rejected, future planned points allowed. Node/equipment targets only; no PIPE
boundary coercion. Schedule source kind/initial effective selection, ordered horizon,
exact first/last anchors, unique IDs/structural keys and frozen recorded evidence
are required. All list layers defensive/unmodifiable; prior revisions remain intact.
Interpolation is never evaluated; endpoint coverage does not claim a hydraulic solution.

Nineteen synthetic JUnit test methods prepared for knowledge/missing-data shapes,
all legal targets and quantity edges, every illegal target including UNKNOWN,
identity/evidence/enums, source validity/exclusive ends, future state/value/evidence,
delayed observations/partial unknowns, null/empty/duplicate structural keys;
STEP/LINEAR mixed-origin schedules, unsupported origins/PIPE targets, range/identity/
shape errors, timestamp duplicates/order, future observations, horizon anchors,
recorded-after-source rejection, nested copying and immutable revision replacement.

Actual validation:
- Required focused command invoked as bash ./mvnw -B -q
  -Dtest=SimulationStateBoundaryInputTest,SimulationFluidEquipmentInputTest,SimulationPhysicalNetworkInputTest,SimulationInputManifestTest,ArchitectureGuardrailTest test;
  bash ./mvnw -B -q clean verify also attempted. Both BLOCKED before compilation by
  uncached Spring Boot parent 4.1.1 and repo.maven.apache.org temporary DNS failure.
  Wrapper tracked permissions unchanged. Runtime Java 17.0.20, not required Java 21.
- Four production records plus existing source-version/exceptions compiled through
  Java 17 jdk.compiler/com.sun.tools.javac.Main. Direct test compilation was blocked
  by absent JUnit API; no JUnit/ArchUnit/full repository pass claimed locally.
- An external temporary standalone harness adapted the 19 scenario bodies using its
  own plain assertions and no JUnit classes. PASSED 174 custom assertions on Java 17.
  This validates scenario behavior but is not JUnit execution or Java 21 full verification.
- All 37 maintained validator tests, canonical documentation, offline OpenAPI, retained
  P1 evidence, whitespace, exact seven-path and retained registry/version checks PASSED.

B3 IN PROGRESS pending exact-head full CI; parent B/002 remain PENDING. No estimator,
telemetry acquisition/trust resolution, equations, interpolation evaluator, OT actuation
or complete transient profile/station/GZ2 model introduced. B4 must bind sources,
manifest/state/horizon/IDs, retain nested synthetic provenance and define exact graph/
completeness extensions; numerical capability/initialization/physics qualification
remains later engine work. B4/002C/002D/engine stages were not executed.
Next registered step after B3 CI success is B4 documentation-only exact-file preflight:
`docs(twin): register HPR-P25-002B4 execution preflight`.
Publish expected-parent lease, verify remote parent/tree/seven paths, observe both
production/Documentation startup on the exact head and STOP for owner Next/Fail.
P0/P1/P2 CLOSED, P2.5 OPEN, P3 DEFERRED, 57 completed HMRs, 123 permanent subjects,
formal v0.6.0 and development 0.7.0-SNAPSHOT preserved.


## HPR-P25-002B4 Consistent Physical Payload Preflight — 2026-10-09

Owner Next selects ONLY `docs(twin): register HPR-P25-002B4 execution preflight`.
Write ONLY doc/roadmap/ULTIMATE_ROADMAP.md and doc/model-remediation/RECONCILIATION.md.
GitHub main 599bb58115f3d06140d8230a54d7969f743c0c3a, tree
6c341992625e2ede837cd5944fae285b0b57685b, passed exact-head production
#616/run 37970322450 and Documentation #154/run 37970322487.
Job 113954981627 confirms successful Java 21 repository verification, retained
operations/P1 checks, OpenAPI equality/backward compatibility, migrated dictionary
and test-report retention. B3 COMPLETED; 19 JUnit methods present, no independent
per-class XML/skip inspection claimed. Mandatory instructions/execution memories
read; local tree matches before mutation. No production source changed here.

### Source-backed design and graph prerequisite resolution

Reviewed SimulationInputManifest/SimulationInputSourceVersion; all B1 graph, B2
fluid/curve/equipment and B3 state/series/schedule records; ArchitectureGuardrailTest.
Existing records validate their local data, but not cross-payload binding, physical
reference existence, nested curve capture validity, state completeness or nested
synthetic propagation. Manifest.sources carries one metadata source per kind;
digest syntax is not actual payload-integrity verification. Owner adapters/real
source evidence remain 002C/002D; physics capability remains 008A..F.

B1 SimulationPhysicalNetworkInput accepts only a connected pipe graph and rejects
isolated nodes or pipe-disconnected components. A real equipment link joining two
pipe subnetworks cannot be wrapped in that record without fake connecting pipes.
Resolve the already registered B4 prerequisite with a NEW combined-graph record;
do not weaken B1 or modify its constructor/tests. Reuse its immutable node/pipe
records plus B2 typed equipment links, with connectivity over their union. This
supports structural compressor/valve joins without claiming active hydraulic
connectivity through a stopped/closed/bypassed asset. Topology truth stays owner-held.

B4 is assembly schema version 1 for gas scalar initial-state payloads. It performs
strict data coherence, not operational/physics readiness. Required known state
coverage below is a bounded engineering data contract, not chosen equations,
observability/convergence evidence or GZ2 operating policy. A complete spatial
transient profile, current operating limits, valve laws, compressor envelopes,
thermal initialization and supported boundary/method behavior are additional gates.
Those future exact-file/schema preflights must extend this representation explicitly;
do not hide unresolved physics behind an empty readiness flag or successful constructor.

### Exact B4 implementation envelope for a later owner Next

After successful preflight Documentation CI, owner Next selects ONLY B4, exact message
`feat(simulation): assemble consistent physical input payloads`.
Create ONLY two files under src/main/java/dz/sh/hidra/modules/simulation/domain/model/:

1. SimulationConnectedNetworkInput.java
   Immutable record fields: String id, String scopeType, String scopeId,
   SimulationInputSourceVersion sourceVersion, List<SimulationNetworkNodeInput> nodes,
   List<SimulationPipeSegmentInput> pipeSegments, SimulationEquipmentModelInput equipmentModel.
   Require normalized nonblank id/scopeId and scopeType from existing manifest set
   PIPELINE_SYSTEM/PIPELINE/SEGMENT_GROUP/FACILITY_NETWORK; identifiers alone do not
   grant owner eligibility or hydraulic independence. Require TOPOLOGY_CONFIGURATION
   source and nonnull equipmentModel. Defensive required lists, no null entries,
   at least two nodes and one real pipe. Nodes unique by normalized id. Pipes unique
   by normalized id; pipe IDs must be disjoint from equipment IDs (one link namespace).
   Reuse B2's validated equipment/curve records; no new equipment kind or inferred links.
   All pipe and equipment endpoints must exist in nodes. Validate no isolated node and
   one undirected connected component over PIPE PLUS EQUIPMENT edges. Preserve link
   orientation and order; allow cycles and distinct parallel/cross-kind links.
   Nodes with only equipment incidence and pipe components joined by equipment are
   valid structural graphs. Explicit empty equipment model preserves pipe-only use.
   No conversion to B1, fabricated zero-length pipes, valve opening-based edge deletion,
   active hydraulic partition algorithm, bypass inference or owner mutation.

2. SimulationPhysicalInputPayload.java
   Immutable record fields: SimulationInputManifest manifest,
   SimulationConnectedNetworkInput network, SimulationGasFluidInput fluid,
   SimulationInitialStateInput initialState, SimulationBoundaryScheduleInput boundarySchedule.
   First four required; boundarySchedule nullable ONLY for STEADY_STATE. Require manifest
   schemaVersion EXACT 1, the currently specified assembly schema (do not accept unknown
   future schemas merely because the manifest allows positive values). No new product
   whitelist from labels; fluid is gas-typed, real catalogue/product compatibility is 002C.
   No generic oil/H2 accuracy/capability claim from accepting a productReference string.

   Source binding: map manifest.sources by SourceKind using existing unique-kind invariant.
   Require full SimulationInputSourceVersion record equality for network.sourceVersion
   vs TOPOLOGY_CONFIGURATION, fluid.sourceVersion vs FLUID_MODEL,
   network.equipmentModel.sourceVersion vs EQUIPMENT_PARAMETERS,
   initialState.sourceVersion vs OPERATING_STATE, and transient schedule.sourceVersion
   vs BOUNDARY_SCHEDULE. Equality includes owner/source/revision/digest/time/origin/evidence,
   not just revisionId; never silently choose a latest/different revision. Payload object
   id is a local representation identity; do not assume it equals sourceId or scopeId.
   Require network.scopeType/scopeId == manifest.scopeType/scopeId and
   fluid.productReference == manifest.productReference. Initial state.stateAt == manifest.stateAt.

   Curve binding: every contained curve, including unused declared curves, must reference
   selected fluid.id and fluid.sourceVersion.revisionId. Each curve's source effectiveAt
   manifest.stateAt, recordedAt <= manifest.capturedAt and <= equipmentModel.sourceVersion.recordedAt.
   B2 already verifies compressor configuration resolves contained curve id/revision.
   Curve source kind is EQUIPMENT_PARAMETERS, but its identity need not equal the aggregate
   model source; the aggregate revision pins its immutable contained curve data. No
   content hash computation/trust verification, head/property/valve method resolution,
   interpolation evaluation or numerical applicability selection in B4.

   Physical reference integrity: every initial-state target must exist in the selected
   NODE/PIPE/EQUIPMENT namespace, including optional UNKNOWN quantities. Equipment
   quantity COMPRESSOR_SPEED must target COMPRESSOR, VALVE_OPENING must target VALVE;
   no equipment cross-kind reinterpretation. Structural quantity keys must be used,
   never ID-only or ambiguous delimiter strings. Preserve extra legitimate quantities.

   Required scalar known-state coverage (schema 1): every node has known absolute
   pressure and temperature; every pipe has known oriented mass flow; every compressor
   has known speed and every valve has known opening. TRANSIENT additionally requires
   known pipe pressure and temperature scalar values. A required key absent or UNKNOWN
   rejects assembly. Optional unknown quantities can remain explicit without making
   required values ready. No missing-to-zero/interpolation/default, inference from a
   boundary point or arbitrary freshness threshold. Existing B3 numeric validity applies.
   This set is scalar input completeness, not complete distributed transient state.
   Zero speed/closed opening are admissible data; engine execution must reject modes/
   active connectivity/behavior it has not qualified. Configured B2 values and actual
   initial values need not be equal: scenario parameters and baseline observations
   remain distinct, without inferring a performed operator action.

   Watermark coherence: if supplied, every initial MEASURED quantity.valueAt <=
   manifest.measurementWatermark; estimated/synthetic times are not observed-watermark
   facts. Existing B3 checks value/evidence against initial/source revision and manifest
   source equality bounds recording by capturedAt. Do not invent maximum age/latency.

   Mode/timeline coherence: STEADY_STATE boundarySchedule must be null (manifest already
   excludes boundary source/horizon). TRANSIENT requires schedule, startsAt exactly
   manifest.stateAt and endsAt exactly manifest.horizonEnd. Validate every schedule target
   against network nodes/equipment and equipment kind, with unique target/quantity keys
   already enforced by B3. Require every scheduled target/quantity key present and known
   in initialState, so an action/forecast is anchored to an explicit baseline quantity.
   Do not require first scheduled value to equal initial state: immediate scenario
   steps are allowed and remain proposed input, not measured action/outcome. B3 validates
   complete horizon anchors and point recording/provenance. Boundary selection/rank,
   number of pressure or flow constraints, energy balance and physical well-posedness
   remain numerical design gates; all-node measured pressure is not all-node imposed pressure.

   Synthetic propagation: if any initial quantity knowledge SYNTHETIC, require its
   OPERATING_STATE source origin SYNTHETIC. If any schedule point origin SYNTHETIC,
   require BOUNDARY_SCHEDULE source origin SYNTHETIC. If any curve source origin
   SYNTHETIC, require aggregate EQUIPMENT_PARAMETERS source origin SYNTHETIC.
   Reject a misleading real-only parent declaration even if some other manifest source
   already marks the overall run synthetic. Add public boolean synthetic() returning
   manifest.synthetic() only after those invariants; this is provenance, never readiness.
   Other measured/estimated/forecast/scenario distinctions remain in unchanged nested data;
   origin declarations still require later actual evidence validation.

Create ONLY
src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayloadTest.java.
Update ONLY both execution memories: five tracked implementation paths total.
No B1/B2/B3/manifest/source-version/existing test edits, migration, persistence or API
exposure. Canonical Java headers retain Author Abir MEDJERAB, CreatedOn 2025-06-26,
current UpdatedOn. Reuse InvalidSimulationValueException; standard Java/local Simulation
imports only. Helpers/nested key records remain in the two new records. No owner module,
API/OpenAPI/dictionary/schema/POM/dependency/workflow/Kernel/Platform/export change.

### Meaningful synthetic tests and required verification

Graph cases: pipe-only, real pipes joined solely by compressor/valve links, equipment-only
incidence at a node, cycles/parallel links and preserved directions. Reject null/empty/
undersized lists, duplicate nodes/pipes, pipe-equipment ID collision, either dangling
pipe/equipment endpoint, isolated nodes and union-disconnected components. Prove B1
still rejects its disconnected pipe-only input; the new combined graph accepts the
proper equipment join without fake geometry or modifying B1. Test defensive node/pipe
lists and nested equipment immutability, scope identity/kind/source validation.

Payload cases: complete synthetic steady and transient fixtures; optional unknowns and
signed/zero flows; immediate scenario change allowed; nested origins retained. Reject
null required objects, unsupported schema, every mismatched full source field/reference,
scope/product/state/horizon mismatch, forbidden/missing schedule, stale/future/expired
curve revisions or curve recorded after aggregate model, wrong fluid id/revision;
dangling initial/schedule targets and equipment-kind mismatch; missing/UNKNOWN required
node/pipe/equipment state for each mode, missing/unknown scheduled baseline key;
measured value later than declared watermark; hidden synthetic initial/schedule/curve
sources including when another manifest source is synthetic. Verify pure declared real
metadata fixtures yield synthetic false without claiming verified field trust.
Test replacement topology/model/fluid/state/schedule revisions preserve earlier values,
metadata, orientations and immutable lists. No GZ2 values/solver outputs invented.

Later B4 implementation commands:
`./mvnw -B -q -Dtest=SimulationPhysicalInputPayloadTest,SimulationStateBoundaryInputTest,SimulationFluidEquipmentInputTest,SimulationPhysicalNetworkInputTest,SimulationInputManifestTest,ArchitectureGuardrailTest test`
then `./mvnw -B -q clean verify`, maintained validator suites, canonical docs/offline
OpenAPI/P1 evidence, whitespace, exact five-path and retained registry/version checks.
Use bash if wrapper not executable without tracked chmod; report local dependency/
environment limitations. Publish expected-parent lease, verify remote parent/tree/files,
observe both exact-head production/Documentation startup and STOP for owner Next/Fail.
B4 and parent B remain pending applicable CI; 002 also still requires 002C/002D.
After B4 CI success, next registered selection is 002C owner-query/adapters exact-file
preflight `docs(twin): register HPR-P25-002C execution preflight`, not engine execution.
No owner export or migration path is authorized until that preflight establishes it.

Current preflight runs only maintained validator suites, canonical docs/offline OpenAPI/
P1 evidence, whitespace and exact two-document/registry/version checks. No runtime
Maven rerun required solely for docs. Publish registered preflight message using
expected-parent lease, verify remote parent/tree/two paths, observe Documentation
startup and STOP. B4 stays PENDING, no combined graph or payload source implemented
here. Do not repeat B4 preflight unless evidence changes. P0/P1/P2 CLOSED, P2.5 OPEN,
P3 DEFERRED, 57 completed HMRs, 123 permanent subjects, v0.6.0 and 0.7.0-SNAPSHOT preserved.


Actual B4 preflight checks PASSED: all 37 maintained validator tests (10 docs,
10 OpenAPI, 17 dictionary), canonical docs (95 documents, 5,063 links, 24 modules,
13 P2 rows), offline OpenAPI (244 paths, 263 operations, 231 schemas), P1 closure
evidence and whitespace. Exact two-document scope, retained phase/HMR registries
and 0.7.0-SNAPSHOT verified. B3 completion is exact-head CI-backed; B4 source,
full numerical readiness and field qualification remain pending. Supporting
preflight Documentation CI follows publication.


## HPR-P25-002B4 Consistent Physical Payload Implementation — 2026-10-09

Owner Next selects ONLY B4 after exact-head Documentation #155/run 37975207187
completed SUCCESS on parent 211b8d5dae066017b9d2305a16eac23c38ee599d,
tree d7746bc0fc28068f8b2c1585842099628550f762. Mandatory AGENTS/roadmap/
reconciliation and registered B4 envelope read; local source tree matches.
Exact message: `feat(simulation): assemble consistent physical input payloads`.

Created ONLY SimulationConnectedNetworkInput.java and SimulationPhysicalInputPayload.java
in src/main/java/dz/sh/hidra/modules/simulation/domain/model, plus
src/test/java/dz/sh/hidra/modules/simulation/domain/model/SimulationPhysicalInputPayloadTest.java.
Updated ONLY roadmap/reconciliation: five tracked paths. Canonical headers retained;
standard Java/local Simulation imports, existing InvalidSimulationValueException.
No B1/B2/B3/manifest/source-version/existing test change, migration/persistence/API
exposure, owner module/API/OpenAPI/dictionary/schema/POM/dependency/workflow/Kernel/
Platform/architecture export change.

Combined immutable graph requires real pipes and explicit equipment model, unique
node/link identities, disjoint pipe/equipment IDs, valid endpoints and connected
undirected union with no isolated node. Equipment-only incidence and real equipment
joins between pipe subnetworks are allowed while retaining orientation/cycles/parallel
links. B1 pipe-only contract remains unchanged; no fake pipes/geometry introduced.
Structural connectivity does not establish active flow through closed/stopped assets.

Payload schema 1 binds complete source-version record equality, scope/product/state/
horizon, exact selected curve fluid revision, curve effective/capture/aggregate recording
times, physical namespaces and equipment-kind quantities. Required known scalar node,
pipe and equipment coverage is mode-specific; optional unknowns retained. Measured
values respect declared watermark; transient controls need known baseline keys and exact
horizon anchors. Immediate proposed scenario changes need not equal baseline values.
Nested synthetic state/schedule/curve data requires its own synthetic parent source,
even when another source already makes the manifest synthetic. synthetic() exposes
provenance only. No hashing/trust validation, property/valve law resolution, hydraulic
well-posedness, distributed transient initialization, interpolation or actuation claimed.

Twenty synthetic JUnit methods prepared for pipe-only/equipment-joined graphs, nodes
with only equipment incidence, cycles/parallel/cross-kind links and orientations;
identity/scope/source/list/endpoint/duplicate/collision/isolated/disconnected errors;
complete steady/transient and declared-real metadata fixtures; exact source-field
mismatch for every manifest kind; scope/product/time/schema/curve fluid/capture/validity
errors; dangling/unknown targets and cross-kind equipment controls; every missing or
UNKNOWN required scalar in both modes; watermark; mode/horizon/schedule baseline;
hidden synthetic parents; defensive lists and replacement revisions/physical values.
No actual GZ2 data or measured solver accuracy used or inferred.

Actual validation:
- bash ./mvnw -B -q -Dtest=SimulationPhysicalInputPayloadTest,SimulationStateBoundaryInputTest,SimulationFluidEquipmentInputTest,SimulationPhysicalNetworkInputTest,SimulationInputManifestTest,ArchitectureGuardrailTest test
  and bash ./mvnw -B -q clean verify attempted; both BLOCKED before compilation by
  uncached Spring Boot parent 4.1.1 and repo.maven.apache.org temporary DNS failure.
  Wrapper permissions unchanged; Java 17.0.20 available, not required Java 21.
  No local Maven/JUnit/ArchUnit/full verification pass claimed.
- New records and their existing local record/exception/value dependencies compiled
  through Java 17 jdk.compiler/com.sun.tools.javac.Main. External temporary standalone
  harness adapted all 20 scenario bodies with plain custom assertions and no JUnit
  classes; PASSED 220 assertions. This is not JUnit or full Java 21 verification.
- All 37 maintained validator tests, canonical docs/offline OpenAPI/P1 evidence,
  whitespace, exact five-path and retained phase/HMR/version checks PASSED.

B4 IN PROGRESS and parent B remains PENDING until exact-head full CI verification;
002 remains PENDING with owner queries/capture still unimplemented. No solver, numerical
readiness, field calibration or complete operational GZ2 model established. Limits,
full spatial/thermal state, boundary/active-equipment behavior and actual method
capability remain explicit future exact-file/schema/numerical qualification gates.
Next registered step after successful B4 CI is documentation-only owner-query/adapters
preflight: `docs(twin): register HPR-P25-002C execution preflight`.
002C/002D/engine stages not executed. Publish expected-parent lease, verify remote
parent/tree/five paths, observe production/Documentation startup on exact head and STOP.
P0/P1/P2 CLOSED, P2.5 OPEN, P3 DEFERRED, 57 completed HMRs, 123 permanent subjects,
formal v0.6.0 and development 0.7.0-SNAPSHOT preserved.


## HPR-P25-002C Owner Query and Adapter Preflight — 2026-10-09

Owner Next selects ONLY `docs(twin): register HPR-P25-002C execution preflight`.
Write ONLY doc/roadmap/ULTIMATE_ROADMAP.md and doc/model-remediation/RECONCILIATION.md.
Verified main 938fa25d8856243cf340cecbf65ffcdbfaa27659, tree
926fb7fdf87a351eb9aafe40c1d31e920ebdd4b7: Production #617/run 37976570634
and Documentation #156/run 37976570645 completed SUCCESS on that exact SHA.
B4's 20 JUnit methods are present; individual XML reports were not independently
inspected. Remote full Java 21 CI supersedes the recorded local Maven limitation;
that historical limitation is retained. Mandatory instructions and both execution
memories read, source synchronized before mutation. No implementation executes here.

### Owner evidence and missing-data boundary

- Topology application/contract/simulation/SimulationTopologyScopeContract.java and
  application/service/TopologySimulationScopeQueryService.java export supported,
  exists and ACTIVE eligibility only for PIPELINE_SYSTEM/PIPELINE; SEGMENT_GROUP and
  FACILITY_NETWORK are explicitly unsupported. They do not export physical revisions.
  Pipeline domain/model/Pipeline.java has nominal diameter/design pressure; persistence
  PipelineSegmentJpaEntity has endpoints/nullable lengthKm, TopologyNodeJpaEntity has
  nullable elevationMeters. No domain revision/roughness/internal-diameter contract
  was found in the inspected Topology domain. Do not manufacture a revision from
  updatedAt, use a live catalog row as immutable history, or infer physical geometry.
- Custody application/contract/planning/PlanningProductReferenceContract.java and
  infrastructure/integration/PlanningProductReferenceQueryAdapter.java export identity,
  code and Planning policy eligibility under mandatory transaction/shared locks.
  Planning approval is not Simulation gas eligibility. No inspected Custody domain
  contract provides versioned gas composition, property methods or reference basis.
- Telemetry domain/model/TrustedTelemetryReading.java already retains actual values,
  units, quality assessment/code, TrustLevel, source/trusted times, original reading,
  point, asset binding, topologySnapshotId and ingestionBatchId. Its existing Monitoring
  export provides only id/pointId/trustLevel. TrustedTelemetryReadingRepositoryPort
  supports exact findById, allowing a useful owner-controlled evidence export now.
  Presence of a trusted-reading row, enum name or topologySnapshotId does not prove
  Simulation suitability, a coherent network revision or canonical input digest.
- Simulation application/port/out/TopologySnapshotLookupPort.java and
  TelemetryTrustedReadingSnapshotPort.java are boolean availability interfaces; do not
  retrofit their meaning or pretend they capture the new immutable physical payload.
  Owner equipment characteristic versions/approved limits remain missing prerequisites.

The next bounded implementation is C1: export existing raw Telemetry evidence and
adapt it into a Simulation-owned application projection without exposing Telemetry
domain/JPA classes. This is useful read-only acquisition, not completed versioned
physical-input resolution. Parent 002C/002 and aggregate B closure remain PENDING;
B1..B4 implementation CI is confirmed, no independent closure task is selected here.

### Bounded C stages

| Code | Status | Exact purpose | Exact commit message | Gate |
|---|---|---|---|---|
| HPR-P25-002C1 | COMPLETED — 28cd17ca96c7f9bbd195601f0cd24e718c8ce632 plus c6b2fa7257fd603965f4a6faa9a05b1cd094e8cb repair passed Java 21 Production #619 and Documentation #159; 16 JUnit methods present; per-class XML not independently inspected | Owner-controlled trusted-reading evidence export and Simulation adapter | `feat(simulation): resolve owner trusted reading evidence` | Successful C preflight Documentation CI; B1..B4 exact-head CI confirmed |
| HPR-P25-002C2 | COMPLETED — 15795dc000c60080dea0566d275d42d843420ba8 passed Java 21 Production #620 and Documentation #161; six contracts/adapters and 22 JUnit methods present; per-class XML not independently inspected | Topology/product eligibility adapters and explicit missing physical-revision results | `feat(simulation): resolve owner topology and product eligibility` | C1 CI; `docs(twin): register HPR-P25-002C2 execution preflight` |
| HPR-P25-002C3 | PENDING — source gaps decomposed into C3A..F; C3A exact envelope registered, later stages require preflights | Versioned physical source resolution, unit/reference conversion and Simulation suitability policy | `feat(simulation): resolve qualified physical source revisions` | C2 CI; `docs(twin): register HPR-P25-002C3 execution preflight`; demonstrated owner gaps/ownership/schema and approval evidence |

C2/C3 are ordered planning registrations, not exact write authorization. C3 may need
further bounded stages once actual source storage and policies are designed. Do not
add owner tables, trust thresholds, unit conversions or synthetic production resolvers
under C1. Parent completion requires topology/product/equipment/state revisions to be
resolved and unavailable or unqualified inputs rejected, not only these projections.
Canonical hash/capture/persistence remains 002D; solver design/execution remains 008A..F.

### C1 exact next implementation envelope

After this preflight Documentation CI succeeds, owner Next selects ONLY C1.
Create ONLY these seven paths:

1. src/main/java/dz/sh/hidra/modules/telemetry/application/contract/simulation/SimulationTrustedReadingContract.java
2. src/main/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryService.java
3. src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationTrustedReadingEvidencePort.java
4. src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/TelemetryTrustedReadingEvidenceQueryAdapter.java
5. src/test/java/dz/sh/hidra/modules/telemetry/application/service/SimulationTrustedReadingQueryServiceTest.java
6. src/test/java/dz/sh/hidra/modules/simulation/infrastructure/integration/TelemetryTrustedReadingEvidenceQueryAdapterTest.java
7. src/test/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationTrustedReadingEvidencePortTest.java

Update ONLY src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java and both execution
memories: ten tracked paths total. No migration, entity/repository change, REST/API,
OpenAPI/dictionary regeneration, POM/dependencies/workflows, Kernel/Platform, existing
Simulation payload/manifest/availability port or other owner code changes.

Owner contract method: Optional<ReadingEvidence> resolve(String id). Nested immutable
record ReadingEvidence carries exactly existing fields, preserving their values:
String id, readingId, pointId; BigDecimal numericValue; String textValue;
Boolean booleanValue; String unitId, qualityCodeId, trustLevel;
Instant sourceTimestamp, trustedAt; String qualityAssessmentId, topologyAssetTypeCode,
topologyAssetId, topologyAssetCode, topologySnapshotId, ingestionBatchId.
TrustLevel is exported as its existing enum name, not a foreign domain enum.
No field called revisionId/digest, synthetic marker or approved/eligible flag is invented.
No extra value validation or normalization modifies stored evidence. The immutable
projection is not a SimulationStateQuantityInput and supplies no default physical value.

Owner service is @Service, implements owner contract, constructor-injects only
TrustedTelemetryReadingRepositoryPort. Blank/null IDs return Optional.empty without
repository access; trim nonblank lookup IDs; findById and require returned id equals
normalized requested id. Missing/mismatched rows return empty. Map all fields unchanged,
including nullable optional metadata and numeric/text/boolean alternatives. Do not
silently suppress low/untrusted evidence or promote it; explicit downstream qualification
will decide suitability. Preserve even ambiguous value alternatives for assessment;
never convert raw evidence into a known physical scalar here. Repository failures
propagate, not masquerade as a successful absence or usable snapshot.

Simulation port method Optional<ReadingEvidence> resolve(String id); its own nested
immutable record has the same fields/types, with no Telemetry import. Adapter is
@Component, constructor-injects only SimulationTrustedReadingContract; blank/null
requests return empty without owner call, nonblank IDs trimmed, returned ID must match.
Copy every field into Simulation-owned record; missing/mismatch remains empty and
owner failures propagate. No call to solver, capture, acquisition endpoint or OT.
No wiring into existing scenario/run workflows, no default/noop fallback bean.

Architecture decision: add ONLY
 dz.sh.hidra.modules.telemetry.application.contract.simulation
to deliberate exported package prefixes and add a focused positive export assertion
plus negative owner domain/persistence access assertions. Keep all existing rules and
exceptions. Foreign Telemetry access is confined to this Simulation infrastructure
adapter through that public contract; owner never imports Simulation. All new production
imports Java/local types, appropriate Spring stereotype, and this deliberate contract
only. Preserve canonical headers, Author Abir MEDJERAB, CreatedOn 2025-06-26 and actual
execution-date UpdatedOn. Nested records/helpers stay in listed files.

### Required C1 validation and stop boundary

Meaningful deterministic JUnit tests with synthetic fixtures and stub/mock ports cover:
all field values round-trip; null/blank/trimmed IDs and zero calls for invalid IDs;
missing/mismatched IDs; each TrustLevel name without reclassification; numeric zero and
negative values preserved; text/boolean and multiple/missing alternatives preserved;
null optional provenance/unit/asset fields preserved without defaults; exact fixed
source/trusted timestamps and raw units; repository/owner exceptions propagate;
constructor null dependency rejection; projection equality and replacement evidence
leaves original values unchanged. At least one distinct-field fixture guards against
swapped mapping fields. Tests claim metadata transport only, not trusted physics.

Run ./mvnw -B -q -Dtest=SimulationTrustedReadingQueryServiceTest,TelemetryTrustedReadingEvidenceQueryAdapterTest,SimulationTrustedReadingEvidencePortTest,SimulationPhysicalInputPayloadTest,ArchitectureGuardrailTest test
then ./mvnw -B -q clean verify. Use bash if wrapper is nonexecutable without chmod;
report actual environment/dependency failures. Run maintained validator tests, canonical
docs, offline OpenAPI, P1 closure evidence, whitespace and exact ten-path checks.
Require remote full CI for implementation completion. Publish exact registered message
with expected-parent lease; verify remote parent/tree/file scope, observe production
and Documentation CI startup on exact implementation head, then STOP for Next/Fail.
Do not execute C2/C3/002D/engine or phase closure automatically.

This documentation-only preflight runs the maintained 37 validator tests, canonical
docs/offline OpenAPI/P1 evidence, whitespace and exact two-path checks; no Java source
change means no new Maven verification claim. Publish with expected-parent lease,
verify remote parent/tree/two files and observe exact-head Documentation CI startup,
then STOP. Next registered selection: C1 implementation after documentation success.
P0/P1/P2 CLOSED, P2.5 OPEN, P3 DEFERRED, 57 HMR implementations, 123 permanent subjects
and development 0.7.0-SNAPSHOT preserved; no runtime solver or GZ2 calibration claimed.

Preflight validation completed: 37 maintained validator tests PASSED; validate_docs.py
PASSED (95 documents, 5063 links, 24 modules, 13 P2 rows); offline OpenAPI validator
PASSED (244 paths, 263 operations, 231 schemas); validate_p1_closure.py and
git diff --check PASSED. Exact two-document scope and historical registry retention
checked; no Maven run required for this documentation-only change.


## HPR-P25-002C1 Trusted Reading Evidence Implementation — 2026-10-09

Owner Next selects ONLY C1 after exact-head Documentation #157/run 37977891201
completed SUCCESS on f0a9213d130c0d96f7b2b3dd27768851587b9c79, tree
9778f015ca57a3eb37f09381d6cab7b840865e15. Main/source synchronized, mandatory
AGENTS and both execution memories read; registered ten-path envelope retained.
Exact message: `feat(simulation): resolve owner trusted reading evidence`.

Created SimulationTrustedReadingContract and SimulationTrustedReadingQueryService
under Telemetry application, SimulationTrustedReadingEvidencePort under Simulation
application/port/out, TelemetryTrustedReadingEvidenceQueryAdapter under Simulation
infrastructure/integration, plus the three registered test classes. Updated only
ArchitectureGuardrailTest and these two execution memories: ten tracked paths.
Canonical Java headers retained. No entity/repository/availability-port/payload/schema/
migration/API/OpenAPI/dictionary/POM/dependency/workflow/Kernel/Platform change.

Both evidence records copy all 17 existing scalar/provenance fields. Owner lookup uses
only TrustedTelemetryReadingRepositoryPort; adapter uses only the new public owner
contract. Null/blank requests return empty without lookup; trim lookup IDs and reject
mismatched returned identities. Missing rows remain absent, dependency failures propagate.
Raw units/times, quality and asset/snapshot/batch provenance, numeric/text/boolean
alternatives and missing optional metadata remain unchanged. All existing trust enum
names are transported, including UNTRUSTED; no physical eligibility is assigned and no
trust level is promoted. Nullable/multiple alternatives are evidence for later assessment,
not known simulation scalars. No fabricated immutable owner revision or digest.

The only new architecture export is telemetry.application.contract.simulation. A new
focused classifier test admits that contract/nested evidence and rejects owner domain,
persistence and private repository access; all prior rules/exports/exceptions retained.
No runtime scenario/run wiring, capture, solver, fallback resolver or OT action added.

Sixteen deterministic JUnit methods in three new classes cover distinct-field mapping,
null/blank/no-call and trimmed lookups, missing/mismatched IDs, all trust levels, zero/
negative/raw numeric values, text/boolean/multiple/missing alternatives, nullable optional
metadata, fixed source/trusted times, exception propagation, null dependencies and
immutable equality/replacement. Added one focused architecture method. Fixtures are
synthetic; no actual GZ2 values, trust approval or measured hydraulic accuracy claimed.

Actual local validation:
- bash ./mvnw -B -q -Dtest=SimulationTrustedReadingQueryServiceTest,TelemetryTrustedReadingEvidenceQueryAdapterTest,SimulationTrustedReadingEvidencePortTest,SimulationPhysicalInputPayloadTest,ArchitectureGuardrailTest test
  and bash ./mvnw -B -q clean verify both FAILED before compilation: uncached Spring
  Boot parent 4.1.1; repo.maven.apache.org temporary DNS resolution failure. Available
  Java 17.0.20 is not required Java 21; wrapper permissions unchanged. No local Maven,
  JUnit, Spring integration, ArchUnit or full verification pass is claimed.
- Temporary source copies compiled using Java 17 jdk.compiler/com.sun.tools.javac.Main;
  Spring stereotype imports/annotations removed in service/adapter copies only, JUnit
  imports/annotations replaced in test copies with plain custom assertions. All 16
  scenario bodies PASSED 90 assertions. No framework package stubs or repository changes;
  this fallback does not establish framework wiring or Java 21/full verification.

C1 remains IN PROGRESS pending exact-head applicable full CI; parent 002C/002 remain
PENDING. Missing physical revisions/units/reference conversion and qualified trust policy
remain C2/C3/002D prerequisites. No complete physical model, operational readiness,
executing engine or GZ2 calibration established. Preserve P0/P1/P2 CLOSED, P2.5 OPEN,
P3 DEFERRED, all 57 HMR implementations, 123 subjects and 0.7.0-SNAPSHOT.
Next registered step after successful C1 CI is documentation-only C2 exact preflight:
`docs(twin): register HPR-P25-002C2 execution preflight`.
Publish with expected-parent lease, verify remote parent/tree/ten files, observe exact-head
production and Documentation CI startup, then STOP. No C2/C3/002D/engine/closure execution.

Additional local checks PASSED: all 37 maintained validator tests; canonical docs
(95 documents, 5063 links, 24 modules, 13 P2 rows); offline OpenAPI (244 paths,
263 operations, 231 schemas); P1 evidence; whitespace; exact ten-path envelope;
canonical headers/import boundaries; sole deliberate export addition and historical
registry/version retention. These do not substitute for pending implementation CI.


## HPR-P25-002C1-R1 Failed CI Export Registry Repair — 2026-10-09

Owner Fail selects only corrective repair of C1. Verified failed implementation parent
28cd17ca96c7f9bbd195601f0cd24e718c8ce632, tree
b67131e721c19cbab886c1d43b7c8e97191cbf04. Production #618/run 37978605641
FAILED; Documentation #158/run 37978605640 PASSED. Job 113983101081 logs report
1365 tests, one failure, zero errors/skips: ForensicRemediationClosureTest's
crossModulePrivateImportsRemainClosed rejected the deliberate Telemetry evidence export.
ArchitectureGuardrailTest's export was updated by C1, but its companion forensic
source scanner registry was missed. This is an incomplete test registry alignment,
not grounds to weaken private-module checks or change runtime evidence behavior.

Corrective task HPR-P25-002C1-R1, exact message:
`fix(architecture): align simulation telemetry forensic export`.
Admit ONLY src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java plus these two
execution memories: three tracked paths, no new file. Add ONLY the already-approved
telemetry.application.contract.simulation prefix to EXPORTED_PACKAGES, retain every
previous export and all scanners/assertions, and update canonical UpdatedOn date.
This corrective envelope supplements the original ten-path implementation scope;
no later C2/physical source/solver work or phase closure is selected.

Validation commands: bash ./mvnw -B -q -Dtest=ForensicRemediationClosureTest,ArchitectureGuardrailTest,SimulationTrustedReadingQueryServiceTest,TelemetryTrustedReadingEvidenceQueryAdapterTest,SimulationTrustedReadingEvidencePortTest test
and bash ./mvnw -B -q clean verify; maintained 37 validator tests, canonical docs,
offline OpenAPI, P1 closure evidence, whitespace and exact three-path checks.
If dependencies remain unavailable, report accurately and use an external temporary
plain-assertion harness for the forensic scanner without claiming JUnit/full CI.
Publish expected-parent lease, verify remote parent/tree/files, observe exact-head
Production/Documentation CI startup and STOP. C1 remains IN PROGRESS pending successful
repair CI; C2 preflight is next only after successful CI. Preserve all historical
registries/phase/HMR/catalogue evidence and 0.7.0-SNAPSHOT.

R1 validation result: both required Maven commands FAILED before compilation because
Spring Boot parent 4.1.1 is uncached and repo.maven.apache.org DNS resolution failed;
Java 17.0.20 available, not required Java 21, wrapper permissions unchanged. Temporary
Java 17 source harness removed JUnit annotations/imports only and supplied custom
assertions: original forensic source reproduced the exact rejected export; repaired
source passed all five existing scenario bodies / eight assertions. This is not a
JUnit/ArchUnit/full verification pass. All 37 validator tests, canonical docs (95
documents/5063 links), offline OpenAPI (244 paths/263 operations/231 schemas), P1
evidence, whitespace, exact three-path scope and version/historical registry checks
PASSED. No runtime code or test rule/assertion changed. Repair implemented pending
exact-head full CI; stop after CI startup.


## HPR-P25-002C2 Topology and Product Evidence Preflight — 2026-10-09

Owner Next selects ONLY `docs(twin): register HPR-P25-002C2 execution preflight`.
Write ONLY doc/roadmap/ULTIMATE_ROADMAP.md and doc/model-remediation/RECONCILIATION.md.
Verified main c6b2fa7257fd603965f4a6faa9a05b1cd094e8cb, tree
ce17d98a33a65c1bb0704ec7ceedbcee19fca01e: Production #619/run 37979772893
and Documentation #159/run 37979772904 completed SUCCESS at this exact SHA.
C1 implementation 28cd17ca96c7f9bbd195601f0cd24e718c8ce632 plus export repair
are CI-confirmed; retain #618 failure and local dependency limitations as history.
Mandatory instructions and both execution memories read; current source synchronized.
No C2 code executes in this documentation-only stage. Parent 002C/002 remain PENDING.

### Revalidated source and eligibility boundary

Topology's application/contract/simulation/SimulationTopologyScopeContract.java returns
supported/exists/eligible; application/service/TopologySimulationScopeQueryService.java
resolves PIPELINE_SYSTEM and PIPELINE via owner repository ports and ACTIVE status.
SEGMENT_GROUP/FACILITY_NETWORK/other types remain unsupported. The public result has
no resolved identity or revision; adapters may echo a normalized requested scope but
must not present that echo as independently returned owner identity or immutable topology.
Existing SimulationApplicationService consumes this contract for model scope validation;
C2 does not change that workflow or its existing boolean snapshot ports.

Custody infrastructure/persistence/entity/CustodyCatalogEntryJpaEntity.java stores id,
catalogName, code, active and createdAt/updatedAt (plus sortOrder/systemDefined).
CustodyCatalogEntryJpaRepository.java inherits findById and has findByIdForShare.
The inspected generic catalogue has no source-backed Simulation gas policy or canonical
product-catalog discriminator. PlanningProductReferenceQueryAdapter additionally queries
hidra_custody_planning_product_policy under mandatory transaction/shared locks. That
policy belongs to Planning; reusing its approval for Simulation would invent authority.

C2 therefore exports a product CANDIDATE catalogue reference, preserving catalogue name
and activity, and leaves Simulation product eligibility UNASSESSED. Any catalogue can
be returned as candidate evidence; presence/activity does not establish that the row is
a product, gas, fluid-method compatible or approved for Simulation. No classification
from code/name spelling, hardcoded GZ2 allowlist or generic catalogue active=true rule.
A source-backed classification/approval policy remains an explicit C3 prerequisite.
Topology eligibility is existing scope identity/status eligibility only. Neither result
has versioned physical inputs; C2 exposes explicit missing-source status for both.
No revision/digest is invented from IDs, catalogue timestamps or live owner data.

### C2 exact next implementation envelope

After successful Documentation CI on this preflight, next owner Next selects ONLY C2.
Exact implementation message already registered:
`feat(simulation): resolve owner topology and product eligibility`.
The eligibility claim is bounded to existing Topology scope eligibility and explicit
UNASSESSED product policy. Create ONLY these nine paths:

1. src/main/java/dz/sh/hidra/modules/custody/application/contract/simulation/SimulationProductCandidateContract.java
2. src/main/java/dz/sh/hidra/modules/custody/infrastructure/integration/SimulationProductCandidateQueryAdapter.java
3. src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationTopologyScopeEvidencePort.java
4. src/main/java/dz/sh/hidra/modules/simulation/application/port/out/SimulationProductCandidateEvidencePort.java
5. src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/TopologyScopeEvidenceQueryAdapter.java
6. src/main/java/dz/sh/hidra/modules/simulation/infrastructure/integration/CustodyProductCandidateEvidenceQueryAdapter.java
7. src/test/java/dz/sh/hidra/modules/custody/infrastructure/integration/SimulationProductCandidateQueryAdapterTest.java
8. src/test/java/dz/sh/hidra/modules/simulation/infrastructure/integration/TopologyScopeEvidenceQueryAdapterTest.java
9. src/test/java/dz/sh/hidra/modules/simulation/infrastructure/integration/CustodyProductCandidateEvidenceQueryAdapterTest.java

Update ONLY src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java,
src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java and both execution memories.
Total thirteen tracked paths. No new migration/table, existing entity/repository/owner
scope contract/service, application service/runtime workflows, payloads, availability
ports, API/OpenAPI/dictionary, POM/dependencies/workflows, Kernel or Platform changes.
Private helpers/nested records/enums stay in these files. Canonical headers retain
Author Abir MEDJERAB, CreatedOn 2025-06-26; UpdatedOn is actual implementation date.

### Exact contract, adapter and missing-input semantics

Custody owner contract: Optional<Candidate> resolve(String id). Nested immutable record
Candidate(String id, String catalogName, String code, boolean active, Instant createdAt,
Instant updatedAt). Export only Java types; retain all raw field values without additional
normalization or revision semantics. Custody adapter is @Component, nonfinal for Spring
transaction proxying, constructor-injects only CustodyCatalogEntryJpaRepository. Method
@Transactional(readOnly=true) uses existing findById; blank/null IDs return empty without
repository call, nonblank requests trimmed, returned id must equal the normalized request.
Missing/mismatched rows return empty; exceptions propagate. Copy the six fields exactly,
including inactive rows or missing timestamps. No JDBC policy query, Planning dependency,
shared-lock snapshot/capture claim, writes, classification or generated defaults.

Simulation product port: Optional<CandidateEvidence> resolve(String id). Nested immutable
CandidateEvidence has the same six fields/types. Nested enum EligibilityStatus has only
UNASSESSED; method simulationEligibility() on CandidateEvidence returns UNASSESSED.
Nested enum PhysicalInputStatus has only MISSING_VERSIONED_FLUID_SOURCE; method
physicalInputStatus() returns that value. Neither active flag nor timestamps can alter
these results. Do not use ready/approved booleans, populate fluid properties or add a
revision field. Optional empty means candidate not resolved; resolved but unassessed
means policy and physical data are still missing, never a usable calculation input.

Simulation product adapter is @Component, constructor-injects only
SimulationProductCandidateContract. Null/blank requests return empty without owner call;
trim others; filter returned ID equality, copy all six fields without reclassification.
Missing/mismatched returned IDs, including null ID, return empty; exceptions propagate.
No foreign Custody domain/JPA/repository import in Simulation.

Simulation topology port: Optional<ScopeEvidence> resolve(String scopeType, String scopeId).
Nested immutable ScopeEvidence(String scopeType, String scopeId, boolean supported,
boolean exists, boolean eligible). Required nonblank normalized type/id; reject logically
inconsistent flags (exists requires supported, eligible requires both exists/supported)
using InvalidSimulationValueException. Nested enum PhysicalInputStatus has only
MISSING_VERSIONED_TOPOLOGY_SOURCE; physicalInputStatus() always returns that explicit
status, including eligible scopes. No geometry, revision, qualified boundary or readiness
method is inferred. Scope identity is the normalized request echo described above.

Topology adapter is @Component, constructor-injects only existing
SimulationTopologyScopeContract. Null/blank type or ID returns empty with no owner call;
trim both otherwise; copy owner's three flags into ScopeEvidence. Unsupported, missing,
inactive and eligible owner results remain distinguishable and do not become absent.
Require nonnull owner result; malformed flag combinations reject through ScopeEvidence;
owner failures propagate. Do not inspect owner tables, change scope support, invent
identity checks unavailable in the public result, or infer product/connected scope.

New production imports only Java, own module types, appropriate Spring annotations and
these deliberate owner contracts in Simulation infrastructure adapters. Custody owns
its JPA dependency. Add ONLY custody.application.contract.simulation to BOTH deliberate
architecture export registries. Topology's export already exists. Extend focused tests
in ArchitectureGuardrailTest to admit owner contracts/nested records while rejecting
private owner domain/persistence/repository imports. Retain all rules, historical exports
and forensic scanning assertions; do not repeat the C1 companion-registry omission.
No runtime consumer wiring, physical input resolver fallback, hash/capture or OT action.

### Meaningful tests and validation

Three registered JUnit classes use deterministic synthetic fixtures and mocked/stubbed
owners/repositories without Spring context/database. Cover every six-field product
mapping with distinct strings/fixed timestamps, active and inactive catalogue candidates,
unknown catalogue names/codes with no gas inference, nullable timestamps, blank/null/
trimmed inputs and no calls for invalid IDs, missing/mismatched/null returned identities,
lookup exceptions and null dependencies. Verify UNASSESSED/MISSING statuses regardless
of candidate activity and original evidence unchanged after replacement rows.

Topology cases cover every supported/exists/eligible legal combination (unsupported,
missing, inactive, active), normalized request echo, explicit missing physical source
for all results; all illegal flag combinations and nonnull result rule; blank/null
fields/no-call, arbitrary unsupported type passthrough, owner failure propagation and
null dependency. Test immutable record equality and preserved earlier result after owner
replacement. Tests demonstrate transport/eligibility boundaries, not coherent physical
capture, catalogue product classification, Simulation approval or hydraulic accuracy.

When C2 executes run ./mvnw -B -q -Dtest=SimulationProductCandidateQueryAdapterTest,TopologyScopeEvidenceQueryAdapterTest,CustodyProductCandidateEvidenceQueryAdapterTest,TopologySimulationScopeQueryServiceTest,ArchitectureGuardrailTest,ForensicRemediationClosureTest test
then ./mvnw -B -q clean verify. If wrapper is nonexecutable use bash without changing
tracked permissions; accurately report environment/dependency failures. Run maintained
validator tests, canonical docs/offline OpenAPI/P1 evidence, whitespace, exact thirteen
paths and retained historical/version/export checks. Require full exact-head CI before
completion. Publish expected-parent lease, verify remote parent/tree/files, observe
Production/Documentation startup and STOP for Next/Fail. No C3/002D/engine/closure work.

Next selection after successful C2 CI: documentation-only
`docs(twin): register HPR-P25-002C3 execution preflight`. That preflight must resolve
actual missing source/version storage, product approval and unit/reference basis gaps
before authorizing code, split larger prerequisites as needed and never replace absent
physical data with defaults. C2 does not complete parent 002C or product-aware inputs.

This current docs-only task runs 37 validator tests, canonical docs/offline OpenAPI/P1
evidence, whitespace and exact two-file scope; no Maven pass claimed. Verify remote
parent/tree/two paths and exact-head Documentation CI startup, then STOP.
P0/P1/P2 CLOSED, P2.5 OPEN, P3 DEFERRED, 57 HMR implementations, 123 subjects,
0.7.0-SNAPSHOT and absence of an executing hydraulic solver/GZ2 calibration preserved.

C2 preflight validation PASSED: all 37 maintained validator tests; canonical docs
(95 documents, 5063 links, 24 modules, 13 P2 rows); offline OpenAPI (244 paths,
263 operations, 231 schemas); P1 closure evidence; whitespace; exact two-document
scope and historical registry/version retention. No Java source changed or Maven
verification claimed. C1 and its R1 repair are verified completed by #619/#159;
C2 implementation remains PENDING.


## HPR-P25-002C2 Owner Eligibility Evidence Implementation — 2026-10-09

Owner Next selects ONLY C2 after Documentation #160/run 37982159094 completed
SUCCESS at parent 68746ddcf058705151b71cb2880135801c98566c, tree
e9c98ce2a0d0dae0f92252e1ea596302a9d7fe70. Mandatory instructions and execution
memories read, current source synchronized, exact thirteen-path envelope retained.
Exact message: `feat(simulation): resolve owner topology and product eligibility`.

Created the nine registered paths: Custody SimulationProductCandidateContract and
SimulationProductCandidateQueryAdapter; SimulationTopologyScopeEvidencePort and
SimulationProductCandidateEvidencePort; TopologyScopeEvidenceQueryAdapter and
CustodyProductCandidateEvidenceQueryAdapter; three corresponding registered test classes.
Updated only both architecture export tests and these two memories. No schema/migration,
existing entity/repository/owner scope contract/service, availability port, runtime
workflow, physical payload, API/OpenAPI/dictionary/POM/dependency/workflow/Kernel/Platform
change. Canonical headers retained; Custody transactional adapter nonfinal for proxying.

Owner catalogue adapter uses existing findById within @Transactional(readOnly=true),
normalizes requested IDs and requires exact returned ID. Raw catalogue name/code,
activity and created/updated timestamps copy without classification or revision inference.
Missing/mismatched rows remain absent, invalid requests make no lookup, failures propagate.
Simulation adapter copies that owner contract evidence; product eligibility remains
UNASSESSED and physical status MISSING_VERSIONED_FLUID_SOURCE for all returned candidates.
Generic catalogue presence/activity/code spelling does not imply product/gas approval.
Planning policy is not imported or queried; absent physical properties remain missing.

Topology adapter uses existing public owner scope contract, retains unsupported/missing/
inactive/eligible results and echoes normalized requested identity without claiming an
independently returned identity/revision. ScopeEvidence validates required IDs and all
coherent boolean combinations; malformed/null results reject. Every scope result exposes
MISSING_VERSIONED_TOPOLOGY_SOURCE, including eligible scopes. Invalid requests make no
owner call and failures propagate. No connected consequence scope, geometry or revision
is fabricated. No runtime consumer wiring, captured immutable source, digest or solver.

Added only custody.application.contract.simulation to BOTH deliberate export registries;
new focused ArchitectureGuardrailTest method admits Custody/Topology public contracts and
nested records and rejects private domain/persistence/repository imports. All existing
rules, historical exports and forensic scanner assertions remain unchanged.

Twenty-two new deterministic JUnit methods: owner catalogue mapping/identity/lookup/raw
nullable metadata/activity/immutable replacement; Simulation candidate transport plus
unassessed/missing status; all legal and illegal scope flag combinations, required/trimmed
identity echoes, unsupported type passthrough, missing physical source, null result,
exception/null dependency and immutable equality/replacement. All fixtures synthetic;
no actual GZ2 inputs, product classification, approval or hydraulic accuracy claimed.

Actual local validation:
- bash ./mvnw -B -q -Dtest=SimulationProductCandidateQueryAdapterTest,TopologyScopeEvidenceQueryAdapterTest,CustodyProductCandidateEvidenceQueryAdapterTest,TopologySimulationScopeQueryServiceTest,ArchitectureGuardrailTest,ForensicRemediationClosureTest test
  and bash ./mvnw -B -q clean verify both FAILED before compilation: uncached Spring
  Boot parent 4.1.1 plus repo.maven.apache.org temporary DNS resolution failure. Java
  17.0.20 available, not required Java 21; wrapper permissions unchanged. No local
  Maven/JUnit/Spring/JPA/transaction/ArchUnit/full verification pass is claimed.
- Temporary dependency-free source copies compiled via Java 17 jdk.compiler: framework
  annotations/imports stripped; copied repository reduced to its inherited findById
  signature; JUnit replaced with plain assertions. New 22 scenario bodies PASSED 113
  custom assertions. Temporary forensic source harness reproduced the rejected new
  Custody contract with original registry and PASSED five existing scenarios/eight
  assertions with aligned registry. No framework-package stubs or tracked harness files;
  this does not validate framework proxying, persistence or full Java 21 execution.

C2 IN PROGRESS pending exact-head full CI; parent 002C/002 remain PENDING. C3 source/
version/unit/reference/qualification gaps persist. P0/P1/P2 CLOSED, P2.5 OPEN,
P3 DEFERRED, all 57 HMR implementations, 123 subjects and 0.7.0-SNAPSHOT preserved.
Next registered selection after successful C2 CI is documentation-only
`docs(twin): register HPR-P25-002C3 execution preflight`.
Publish expected-parent lease, verify remote parent/tree/thirteen files, observe
Production/Documentation startup and STOP. No C3/002D/engine/closure execution.

Additional checks PASSED: 37 maintained validator tests; canonical docs (95 documents,
5063 links, 24 modules, 13 P2 rows); offline OpenAPI (244 paths, 263 operations,
231 schemas); P1 evidence; whitespace; exact thirteen-path envelope; canonical
headers/import boundaries; same sole Custody export in both registries; retained
historical rows/version. These checks do not substitute for pending exact-head CI.


## HPR-P25-002C3 Qualified Physical Source Preflight — 2026-10-09

Owner Next selects ONLY `docs(twin): register HPR-P25-002C3 execution preflight`.
Write ONLY doc/roadmap/ULTIMATE_ROADMAP.md and doc/model-remediation/RECONCILIATION.md.
Verified main 15795dc000c60080dea0566d275d42d843420ba8, tree
851e285aa7380099a70a774b3a5494bbbd628598: Production #620/run 37983724675
and Documentation #161/run 37983724639 completed SUCCESS on this exact SHA.
C2 has six production contracts/adapters and 22 new JUnit methods; per-class XML not
independently inspected. Mandatory instructions and execution memories read, source
synchronized before mutation. No source/schema implementation occurs in this preflight.

### Source-backed gaps and qualification disposition

Topology domain/model/Pipeline.java contains nominal diameter/design pressure and live
createdAt/updatedAt, not approved operating limits/internal diameter or immutable revision
identity. Equipment.java supplies identity/attachments/status; TopologyConnection.java
supplies graph links/classification/capacity, not current compressor curves or valve laws.
Persistence PipelineSegmentJpaEntity/TopologyNodeJpaEntity retain nullable geometry;
owner application repository ports do not expose a complete physical revision aggregate.
Simulation's validated B1..B4 graph is a consumer payload, not owner source storage.
C2 ScopeEvidence echoes requested identity and deliberately has missing physical status.

Custody's generic catalogue and C2 candidate export have no Simulation classification,
composition/method versions or approval records. Planning eligibility remains a different
policy. SimulationGasFluidInput requires composition/property-method references but does
not resolve them. Assets owns maintenance/condition records; inspected Topology equipment
and Assets domain models do not supply versioned compressor/valve characteristics and
approved operating envelopes. Equipment source ownership must distinguish Topology's
network incidence from equipment parameter/maintenance evidence rather than merge tables.

Telemetry's TrustedTelemetryReadingApplicationService already enforces PASSED assessment,
MEDIUM/HIGH/CERTIFIED under TelemetryTrustPolicy, ACTIVE point, eligible quality, optional
unit/batch existence and selected binding rules using TelemetryTrustEvidencePort. C1 exports
raw persisted evidence including enum level, units and topology snapshot binding. Reusing
an enum label alone is not replay of those owner facts. Unit identity existence is not
quantity dimension, absolute/gauge pressure basis or volumetric/mass conversion evidence.
SimulationInputSourceVersion declares origin/digest/revision metadata; B4 compares exact
records but performs no source lookup, digest computation or owner qualification.

Missing source storage cannot be solved by adapters returning fabricated versions or
hardcoded MISSING responses indefinitely. C3 is decomposed into real owner revision,
storage, policy and conversion prerequisites, then the actual qualified resolver. C3A
starts the first missing owner physical contract; this is not parent completion. Synthetic
fixtures remain legitimate development inputs, explicitly distinguishable from actual
GZ2 field sources and operational approval. No actual source data/approval is supplied
by this preflight; no unknown geometry, thresholds, property laws or trust policy values
are invented. P0/P1/P2 closure and 123-subject historical catalogue remain preserved.

### Source storage and approval design decisions

- Topology owns immutable physical network revision content with stable sourceId and
  revisionId, explicit recorded/effective times, declaration provenance and evidence.
  A new revision replaces a prior revision in a selection, never mutates that prior object.
  The owner contract represents normalized supplied geometry and equipment incidence;
  no live entity timestamp is a revision, and no nominal-to-internal diameter conversion.
- Later C3B storage must be a separate append-only owner revision store, not edits to
  live Pipeline/Equipment tables. Before its migration, register exact schema, versioned
  canonical representation/digest, unique source/revision identity, atomic writes/replay,
  rejection of conflicting content, immutable update/delete rules, query/export ownership,
  authorization/evidence and repository-backed integration tests. A caller-supplied digest
  or evidence string is not proof. Keep live scope eligibility separate from stored
  revision content. No migration filename or write endpoint is authorized in this stage.
- Custody must own explicit Simulation product classification/approval and immutable
  composition/property-method revisions. Future policy needs qualified evidence binding
  exact candidate identity/revision, product kind, compatible method revision and effective
  interval; no generic catalogue/Planning policy shortcut. Approval is resolved through
  actual owner/governance records and permitted operations, not trusted merely because
  a DTO says APPROVED. Missing policy/source yields unavailable/unqualified results.
- Equipment incidence belongs to Topology; current curves/valve parameters/approved limits
  require a source-backed ownership and append-only revision preflight before persistence.
  Assets maintenance observations may be evidence, not automatically approved performance.
  Explicit synthetic pipe-only empty equipment models remain distinguishable from missing
  real equipment inputs. Do not infer approvals or limits from design pressure.
- Unit/reference conversion and state qualification must resolve owner unit definitions,
  quantity dimension and pressure/flow/reference basis plus raw value/asset/time/quality
  evidence. Gas volumetric flow cannot become kg/s without explicit compatible reference
  conditions, composition/property method and resolved conversion evidence. Gauge pressure
  cannot become absolute without explicit ambient reference. Reject missing/incompatible
  basis instead of guessing. Reuse existing Telemetry trust meaning; additional Simulation
  freshness/uncertainty/field acceptance limits require governed inputs, not new constants.
- Final C3F resolves exact owner revisions and explicit qualification evidence as of the
  chosen operating state/capture time, fails closed on absent/unsupported/ineligible data,
  retains conversion and source references, and assembles existing typed physical inputs.
  Parent 002D later performs coherent persisted capture and canonical hash verification;
  per-owner source revision storage is distinct from the Simulation captured aggregate.
  No engine readiness or operational approval arises solely from structurally valid records.

### Bounded C3 execution registry

| Code | Status | Exact purpose | Exact commit message | Gate |
|---|---|---|---|---|
| HPR-P25-002C3A | PENDING — exact four-path envelope below | Immutable Topology-owned physical network revision contract | `feat(topology): define immutable physical network revisions` | Successful C3 preflight Documentation CI; C2 full CI confirmed |
| HPR-P25-002C3B | PENDING — storage/export/schema preflight required | Append-only Topology physical revision store and owner export | `feat(topology): persist physical network source revisions` | C3A CI; `docs(twin): register HPR-P25-002C3B execution preflight` |
| HPR-P25-002C3C | PENDING — policy/fluid/schema preflight required | Custody Simulation product approval and immutable gas fluid source revisions | `feat(custody): establish qualified gas fluid revisions` | C3B CI; `docs(twin): register HPR-P25-002C3C execution preflight` |
| HPR-P25-002C3D | PENDING — ownership/parameters/schema preflight required | Versioned equipment characteristics and governed limits | `feat(simulation): resolve governed equipment parameter revisions` | C3C CI; `docs(twin): register HPR-P25-002C3D execution preflight` |
| HPR-P25-002C3E | PENDING — unit/state/qualification preflight required | Explicit unit/reference conversion evidence and qualified operating-state revisions | `feat(simulation): qualify converted owner operating state` | C3D CI; `docs(twin): register HPR-P25-002C3E execution preflight` |
| HPR-P25-002C3F | PENDING — exact resolver preflight required | Resolve qualified physical source revisions into typed inputs | `feat(simulation): resolve qualified physical source revisions` | C3A..E CI; `docs(twin): register HPR-P25-002C3F execution preflight` |

Each later envelope must register actual files/migration/dictionary/OpenAPI consequences
before mutation; split larger semantic tasks as necessary. No batch is selected. The
previous parent C3 message is retained for final C3F resolver; no empty parent commit.
Parent C3/002C/002 remain PENDING until their real required source/qualification/capture
work is verified, irrespective of individual contract status. No aggregate closure here.

### C3A exact next implementation envelope

After successful Documentation CI on this preflight, next owner Next selects ONLY C3A.
Do not repeat its preflight unless source evidence changes. Create ONLY:

1. src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevision.java
2. src/test/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevisionTest.java

Update ONLY these two execution memories. Total four tracked paths. Production imports
only standard Java/local Topology exception; reuse InvalidTopologyValueException.
No Simulation/other owner/framework/JPA import, new dependency, export/architecture test
change, existing entity/repository/model mutation, migration, API/OpenAPI/dictionary
regeneration, POM/workflow, Kernel or Platform change. Nested types/private helpers stay
in the single production file; no utility/package skeleton. Canonical headers retain
Author Abir MEDJERAB, CreatedOn 2025-06-26, UpdatedOn actual implementation date.

Immutable outer Java record TopologyPhysicalNetworkRevision fields, in this order:
String sourceId, String revisionId, ScopeType scopeType, String scopeId,
Instant recordedAt, Instant effectiveFrom, Instant effectiveUntil, Origin origin,
String evidenceReference, List<Node> nodes, List<PipeSegment> pipeSegments,
List<EquipmentLink> equipmentLinks.
Nested ScopeType enum: PIPELINE_SYSTEM, PIPELINE only, matching existing owner support.
Nested Origin enum: DECLARED_PARAMETER, SYNTHETIC. DECLARED_PARAMETER means supplied
non-synthetic declaration, not approved/trusted/verified actual field data. No default
origin or operational readiness/approval method. Require nonblank normalized source,
revision, scope and evidence IDs; nonnull scopeType/origin/recordedAt/effectiveFrom;
optional effectiveUntil must be strictly after effectiveFrom. effectiveAt(Instant at)
requires nonnull at and uses inclusive start/exclusive end; open end permitted. Allow
retroactive and scheduled effective dates; do not invent ordering between recordedAt
and effectiveFrom. Later capture/approval checks enforce their own as-of coherence.

Nested Node(String id, BigDecimal elevationMeters): required normalized id and required
elevation; signed/zero valid. Nested PipeSegment(String id, String fromNodeId,
String toNodeId, BigDecimal lengthMeters, BigDecimal internalDiameterMeters,
BigDecimal absoluteRoughnessMeters): nonblank normalized IDs, distinct endpoints,
required positive length/internal diameter, required nonnegative roughness. No nominal
size/design-pressure inference or missing-to-zero conversion. Values are explicitly
supplied in SI, not implicitly converted from existing live owner units. Original unit/
conversion evidence is a required later source-ingestion/qualification responsibility.

Nested EquipmentLink(String id, String fromNodeId, String toNodeId, EquipmentKind kind):
required normalized IDs, distinct endpoints and nonnull kind. Nested EquipmentKind enum
COMPRESSOR, VALVE. These two kinds bound the initial gas graph, not the existing owner
EquipmentType catalogue or engine capability approval; do not infer kind from a label.
No curve/operating point/valve law/approved limit in this incidence-only type. Other asset
kinds require later exact schema/capability preflight rather than silent reinterpretation.

Defensive immutable ordered lists; require all three lists, reject null entries. At least
two nodes and one actual pipe; equipmentLinks may be explicitly empty. Reject duplicate
normalized node IDs, duplicate pipe IDs, duplicate equipment IDs and pipe/equipment link
ID collisions; node and link namespaces remain distinct. Endpoints must exist, no node
may be isolated; validate one undirected connected component using the union of pipes
and equipment links. Equipment can join disconnected pipe subnetworks and be a node's
only incidence; preserve orientation and insertion order. Allow cycles/meshes and distinct
parallel pipe/equipment links. No fake pipe needed for a compressor/valve connection.
Graph connectivity does not imply flow through closed/stopped assets, station completeness,
complete downstream consequence scope, operating feasibility or a qualified GZ2 model.
No requirement that sourceId equals scopeId or revisionId equals asset id; source revisions
are explicit identities. No hash, persisted lookup, owner export, capture or solver here.

### C3A meaningful validation and stop boundary

Deterministic synthetic JUnit fixtures cover valid pipe-only single/branched/meshed and
parallel graphs; equipment joins and equipment-only node incidence; parallel cross-kind
links/orientations preserved; both scope types/origins, normalized identities, signed/zero
elevations and zero roughness; explicit empty equipment. Negative tests cover all missing/
blank identities/enums/times/numbers, invalid intervals and effective-time boundaries,
nonpositive dimensions/negative roughness/self links, null/empty lists/null entries,
normalized duplicates/link collisions, dangling endpoints, isolated nodes and disconnected
union. Assert source-list mutations cannot change record/exposed lists are unmodifiable,
replacement revision with changed geometry/links/times preserves earlier content and
value equality/hash stability. Never supply actual GZ2 thresholds/data or a wall clock.

When C3A executes run ./mvnw -B -q -Dtest=TopologyPhysicalNetworkRevisionTest,SimulationPhysicalNetworkInputTest,SimulationPhysicalInputPayloadTest,ArchitectureGuardrailTest,ForensicRemediationClosureTest test
then ./mvnw -B -q clean verify; use bash without chmod if wrapper nonexecutable. Run
maintained validator tests, canonical docs/offline OpenAPI/P1 evidence, whitespace,
exact four-path/header/import and historical/version checks. Report dependency/toolchain
failures accurately, require full exact-head CI for completion. Publish expected-parent
lease, verify remote parent/tree/four files, observe Production/Documentation startup and
STOP for Next/Fail. No storage/schema/export/C3B..F/002D/engine/closure execution.

Next registered selection after successful C3A CI is documentation-only
`docs(twin): register HPR-P25-002C3B execution preflight`.
This current docs-only C3 preflight runs 37 validator tests, canonical docs/offline
OpenAPI/P1 evidence, whitespace and exact two-file scope. No Java/Maven pass claimed.
Publish expected-parent lease, verify remote parent/tree/two files, observe exact-head
Documentation CI startup and STOP. Preserve P0/P1/P2 CLOSED, P2.5 OPEN, P3 DEFERRED,
57 HMR implementations, 123 subjects, 0.7.0-SNAPSHOT; no executing solver/calibrated GZ2.

C3 preflight validation PASSED: all 37 maintained validator tests; canonical docs
(95 documents, 5063 links, 24 modules, 13 P2 rows); offline OpenAPI (244 paths,
263 operations, 231 schemas); P1 evidence; whitespace; exact two-document scope;
historical registries/version retention. No production source changed or Maven pass
claimed. C2 verified completed by #620/#161; C3/C3A and parent 002C/002 remain PENDING.


## HPR-P25-002 Consolidated Delivery Plan — 2026-10-09

Owner accepted the roadmap trace/recommendation with Ok / Next before further source
implementation. This selects ONLY HPR-P25-002-PLAN1, exact message
`docs(twin): consolidate HPR-P25-002 delivery plan`.
Write ONLY doc/roadmap/ULTIMATE_ROADMAP.md and doc/model-remediation/RECONCILIATION.md.
Verified parent c9dc28b743eff614e8f336ae754864a45d6b71bd, tree
e21559ff585155cfd82a2e69b6c5e65c9b67b233; Documentation #162/run 37986429630
PASSED. Production #620 at 15795dc000c60080dea0566d275d42d843420ba8 remains
the latest verified executable baseline. Mandatory instructions/execution memories read,
source synchronized. No production/test/schema/workflow change or phase closure here.

This owner-approved consolidation supersedes per-substage selection/preflight sequencing
in the earlier C3 section, not its source findings, physical requirements or historical
commit identities. It preserves all completed A/B/C1/C2 implementations and evidence.
B is now recorded completed from its four CI-confirmed implementation leaves; this is
status alignment, not a fabricated parent implementation commit or closure of 002/Phase2.5.
Parent 002 and C remain PENDING until resolved sources and reproducible capture exist.

### Four delivery outcomes and acceptance evidence

| Delivery | Retained implementation codes | Concrete completion evidence | Position |
|---|---|---|---|
| Network revisions | HPR-P25-002C3A + HPR-P25-002C3B | Persist two explicit synthetic physical network revisions; query each exact source/revision through an owner export; prove original content unchanged, conflicting same-identity content rejected and missing revision unavailable | Next delivery; neither code implemented |
| Qualified fluid and equipment | HPR-P25-002C3C + HPR-P25-002C3D | Resolve versioned gas composition/method and current equipment parameters with source/approval evidence; reject unsupported or unqualified combinations; retain synthetic status and explicit limits | Pending network delivery |
| Qualified operating state | HPR-P25-002C3E | Resolve coherent state with actual owner trust/binding evidence and explicit quantity/unit/reference conversions; reject missing or incompatible basis and retain raw provenance | Pending fluid/equipment delivery |
| Reproducible calculation input | HPR-P25-002C3F + HPR-P25-002D | Resolve all selected source revisions into one immutable typed payload/manifest; compute and verify canonical hashes; persist and reload identical capture; demonstrate replacement cannot alter original | Pending qualified state delivery |

The first acceptance case is a connected synthetic gas network, using actual persistence
and owner queries rather than in-memory mocks as storage evidence. An explicitly empty
equipment model may support the first pipe-only case; it does not complete equipment
coverage or hide missing real station parameters. Actual GZ2 data/approval/calibration
is a later operational gate. These outcomes do not by themselves establish an engine.

Keep the original implementation messages and separate commits. Delivery groupings are
not new semantic tasks, releases or squashed substitutes for retained task codes.
Exact files, migrations, ownership, qualification and tests still matter. A delivery is
complete only when its usable acceptance behavior and applicable exact-head CI pass;
adding a record/interface alone is an intermediate implementation result.

### Consolidated execution protocol

1. Use ONE shared exact-file execution preflight for each delivery, covering its retained
   codes, dependency order, individual commit messages, file/migration allowlists and
   acceptance tests. Do not demand a second documentation commit merely because the next
   code in that already-preflighted delivery is selected. Revisit only material source,
   ownership/schema/approval or failure evidence changes.
2. Shared preflight must make the entire delivery concrete before mutation. If a schema,
   security/ownership contract or source policy remains unregistered, complete its design
   inside that preflight; do not implement invented policy or broad unspecified writes.
3. For a preflighted delivery, one owner Next may execute its listed two retained codes in
   order, with separate commits and one final branch advancement/CI observation when
   connector/dependencies permit. This is the owner-approved P2.5 delivery envelope,
   distinct from historical HMR remediation batches. No general multi-stage permission
   beyond the selected delivery is implied. Single-code state delivery remains bounded.
4. Tests must exercise the delivery acceptance behavior, plus necessary lower-level
   invariants and architecture rules. Keep full repository verification, both architecture
   export registries when applicable, documentation/OpenAPI/P1/whitespace/scope evidence.
   Report blocked commands accurately; a standalone harness is not full verification.
5. Publish with expected-parent lease, verify remote commit chain/tree/individual scopes,
   observe applicable exact-head CI startup and STOP. Owner Next/Fail remains the control;
   never proceed into the next delivery, engine or phase closure automatically.
6. Do not introduce deeper nested codes for routine files, serializers, mappers or tests.
   They belong to the selected delivery's exact envelope. A genuine independent semantic
   or approval gap may be split, with a specific reason and observable completion target.

### Next registered selection

Next owner Next after this plan's Documentation CI succeeds selects ONLY a shared
network-revision delivery preflight:
`docs(twin): register network revision delivery preflight`.
Supporting code HPR-P25-002-NETWORK-PREFLIGHT; scope remains these two execution memories.
It must retain C3A's already-defined record/validation requirements, review whether the
source/store/export representation needs adjustments, and register C3B's actual storage,
canonical representation, migration, owner contract/adapter, tests and documentation
consequences in the same envelope. Do not repeat C3A's general source-gap review. The
combined code envelope is not authorized until that schema/file plan is concrete.
This replaces the former immediate standalone C3A selection and separate later C3B
preflight selection; historical text is retained as history. C3A/C3B code not executed here.

After network-delivery CI, the subsequent owner-selected shared preflights are:
`docs(twin): register fluid equipment delivery preflight`,
`docs(twin): register operating state delivery preflight`, and
`docs(twin): register reproducible input delivery preflight`.
They replace the separate per-code C3C/C3D/C3E/C3F/002D preflight sequence. Their code
write scopes/migrations are not implicitly authorized by this planning registration.

### Engine work and Phase 2.5 position

HPR-P25-008A numerical design may be separately selected after network-delivery CI,
using the completed B physical input contracts and retained known owner/field gaps.
Full parent 002 closure is not a prerequisite for mathematical design or synthetic
reference planning; actual engine execution still requires complete compatible inputs.
A future owner Next must explicitly select that design track and its exact preflight;
it does not run alongside this task automatically. Keep original 008A..F messages.

Near-term product target: one stored-revision synthetic network resolved into a persisted
reproducible input, followed by an executing steady-state calculation with conservation
and independent reference evidence. Then equipment/transient verification and actual
GZ2 calibration. Do not market contract counts or planning commits as runtime progress.

Source boundaries remain: no direct OT actuation, no inferred physical values/approved
limits, no unverified deployments, no synthetic-to-operational promotion from labels.
P0/P1/P2 CLOSED, P2.5 OPEN, P3 DEFERRED, 57 HMR implementations, 123 subjects and
0.7.0-SNAPSHOT retained. No release, deployment, calibrated GZ2 model or solver claim.
This planning task runs maintained 37 validator tests, canonical docs/offline OpenAPI/P1
evidence, whitespace and exact two-file scope; no Maven pass claimed. Verify remote
parent/tree/two paths, observe Documentation CI startup and STOP.

PLAN1 validation PASSED: 37 maintained validator tests; canonical docs (95 documents,
5063 links, 24 modules, 13 P2 rows); offline OpenAPI (244 paths, 263 operations,
231 schemas); P1 evidence; whitespace; exact two-document scope; retained historical
registries/version. Plan implementation recorded, exact-head Documentation CI pending;
network-delivery shared preflight remains the next selected task after successful CI.


## HPR-P25-002 Shared Network Revision Delivery Preflight — 2026-10-09

### Selection, evidence and retained tasks

Owner Next selects ONLY HPR-P25-002-NETWORK-PREFLIGHT, exact message
`docs(twin): register network revision delivery preflight`. Write ONLY these two
execution memories now. Verified main f55e2fb26b6a6eb64e03ad4403846f5d5a7ada7e,
tree eb0eee9b1e3191ff6e4f6613f593fc4b5df161f3; Documentation #163/run 37987465475
PASSED. Latest executable baseline remains 15795dc000c60080dea0566d275d42d843420ba8,
Production #620 PASSED. Mandatory instructions/memories read before mutation.

This shared preflight supersedes standalone C3A/C3B selection and intervening preflight/
CI requirements in historical C3 text. Preserve C3A's already defined physical record,
field order, validation and tests; no domain representation adjustment is needed.
C3A/C3B remain PENDING. No source/test/schema/dictionary change or phase closure here.

| Order | Retained code | Exact separate message | Scope |
|---|---|---|---|
| 1 | HPR-P25-002C3A | `feat(topology): define immutable physical network revisions` | Earlier exact four paths |
| 2 | HPR-P25-002C3B | `feat(topology): persist physical network source revisions` | Seventeen paths below |

After successful preflight Documentation CI, one owner Next selects both codes in order
as ONE network delivery envelope: separate commits, chain C3B to C3A, advance main once
with expected-parent lease, verify each commit scope/final tree, observe final-head
Production/Documentation CI startup and STOP. No further routine C3B preflight. Before
source execution verify unchanged main, successful documentation head and green latest
production baseline; revisit material changed source/schema/failure evidence only.

### Source review and representation choice

TopologySnapshotJpaEntity and V20260611_004__create_topology_tables.sql have a general
JSON/status/workflow snapshot model, not this physical source-revision contract. Preserve
it and all live asset tables. Current migration chain ends V20261008_026; V20261009_001
is unoccupied at verified parent. Recheck before implementation; a collision requires
explicit re-registration, never overwrite a prior migration.

topology.application.contract.simulation is already exported in BOTH ArchitectureGuardrailTest
and ForensicRemediationClosureTest; no export-prefix/registry mutation is needed. Application
cannot depend on infrastructure. Standard-Java codec stays with JDBC adapter; owner port
returns domain objects internally; query maps to public DTOs. No Simulation code change now.
Existing PostgreSQL tests use Testcontainers PostgreSQL 16, JDBC, DriverManagerDataSource
and real transaction proxies. Reuse available dependencies. Production CI compares an actual
full-chain catalog capture against DATA_DICTIONARY.md. JDBC-only persistence needs reviewed
ownership metadata and a regenerated physical dictionary; no new JPA entity is necessary.

### C3A retained exact scope and requirements

Create ONLY:
1. src/main/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevision.java
2. src/test/java/dz/sh/hidra/modules/topology/domain/model/TopologyPhysicalNetworkRevisionTest.java

Update ONLY doc/roadmap/ULTIMATE_ROADMAP.md and doc/model-remediation/RECONCILIATION.md.
Earlier C3A field order/nested enums and full meaningful test requirements remain binding:
source/revision/scope/recorded/effective times/origin/evidence and immutable ordered nodes,
pipes/equipment links; inclusive-start/exclusive-end effectiveAt; explicit supplied SI;
signed/zero elevation, positive length/internal diameter, nonnegative roughness; normalized
IDs, valid nonself endpoints, disjoint pipe/equipment IDs, no isolated nodes and connected
undirected union. Preserve orientation, meshes/cycles/parallel links, equipment-only node
incidence and equipment joins. At least two nodes/one real pipe, explicit empty equipment
allowed. No defaults, nominal-diameter/design-pressure inference, hash/framework/persistence/
foreign owner imports. Reuse InvalidTopologyValueException. No adjustments for storage.

### C3B exact write allowlist

Create ONLY these ten files:
1. src/main/java/dz/sh/hidra/modules/topology/application/port/out/TopologyPhysicalNetworkRevisionRepositoryPort.java
2. src/main/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationPhysicalNetworkRevisionContract.java
3. src/main/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryService.java
4. src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyPhysicalNetworkRevisionCodec.java
5. src/main/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/JdbcTopologyPhysicalNetworkRevisionRepositoryAdapter.java
6. src/main/resources/db/migration/V20261009_001__p25_topology_physical_network_revisions.sql
7. src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/adapter/TopologyPhysicalNetworkRevisionCodecTest.java
8. src/test/java/dz/sh/hidra/modules/topology/application/service/TopologySimulationPhysicalNetworkRevisionQueryServiceTest.java
9. src/test/java/dz/sh/hidra/modules/topology/infrastructure/persistence/TopologyPhysicalNetworkRevisionPostgresIntegrationTest.java
10. src/test/java/dz/sh/hidra/modules/topology/application/contract/simulation/SimulationPhysicalNetworkRevisionContractTest.java

Update ONLY these seven files:
11. .github/database-dictionary-ownership.json
12. doc/database/DATA_DICTIONARY.md
13. doc/database/SCHEMA_OWNERSHIP.md
14. doc/database/README.md
15. doc/roadmap/ULTIMATE_ROADMAP.md
16. doc/model-remediation/RECONCILIATION.md
17. doc/modules/topology.md

Seventeen C3B paths; nineteen distinct paths across delivery. Module document path verified
as doc/modules/topology.md. No other paths: preserve existing entities/live repositories,
controllers/APIs/OpenAPI, Simulation/other owners, catalogue, dependencies/POM, scripts/
workflows, architecture exports, Kernel and Platform. Canonical Java headers retain Author
Abir MEDJERAB, CreatedOn 2025-06-26, UpdatedOn actual implementation date. No deeper codes
for codec/mapping/tests. All helpers/nested DTOs remain in the listed files.

### Exact owner ports and public query boundary

Repository methods: TopologyPhysicalNetworkRevision append(TopologyPhysicalNetworkRevision
revision), Optional<TopologyPhysicalNetworkRevision> find(String sourceId,String revisionId),
Optional<StoredRevision> findStored(String sourceId,String revisionId). Nested immutable
StoredRevision carries revision, String payloadFormat, String sha256. Require nonnull
revision and trimmed/nonblank lookup identities; reject malformed owner input using
InvalidTopologyValueException. find delegates to verified findStored. Identical replay
returns the stored equivalent revision; conflicting same-identity content throws that
exception without mutation. No update/delete/list/latest/fallback methods.

Public SimulationPhysicalNetworkRevisionContract: Optional<Revision> find(String sourceId,
String revisionId). Nested Revision metadata matches domain field order followed by ordered
List<Node>, List<PipeSegment>, List<EquipmentLink>, String payloadFormat, String sha256.
Nested element fields match domain scalars. Use String enum names for scopeType/origin/
equipment kind, not domain enums/types. Standard Java imports only; immutable defensive
lists reject null entries; required identities/metadata/physical scalars and exact enum
names are validated, optional end interval preserved, exact format and lowercase 64-hex
digest required. Use IllegalArgumentException for malformed public DTO construction.
Contract tests exercise valid exports, malformed metadata/scalars and immutable boundaries.

Query service @Service/@Transactional(readOnly=true), constructor-injected owner port,
calls findStored once and maps validated owner objects into fresh DTOs with stored verified
format/digest. Missing exact revision yields Optional.empty; integrity errors propagate,
never become missing. Normalize/reject lookup identities consistently. No domain/JDBC/
codec/Spring type leaks into DTOs. Storage does not imply live scope eligibility or approval;
existing live scope query remains separate. No Simulation adapter/resolver mutation now.

Writes are internal Topology repository operations exercised by tests and available to a
later registered owner ingestion use case. No public write endpoint, invented actor policy
or implicit credential/approval bypass. Supplied origin/evidence remain declarations;
DECLARED_PARAMETER and a correct digest grant no operational trust. No synthetic live-asset
registration, inferred SI values, approval or live-status mutation.

### Canonical bytes and digest

Standard Java/local Topology codec methods encode(revision), decode(byte[]) and sha256(byte[])
with defensive arrays. Format tag HIDRA_TOPOLOGY_PHYSICAL_NETWORK_V1. Deterministic
DataOutputStream/DataInputStream layout: signed 32-bit big-endian UTF-8 string byte length
then bytes; first string format tag, then metadata in domain field order. Enums encode
names. Instants: signed 64-bit epoch seconds then signed 32-bit nanos. Optional end: one
byte 0 absent/1 present, then Instant if present. Three lists: signed 32-bit count then
elements in insertion order, fields in their declared order. BigDecimals: length-prefixed
toPlainString text followed by signed 32-bit scale; reconstruct exactly with setScale
without rounding, including negative scales. No floating point/locale/default charset.

Lowercase SHA-256 covers all bytes including format, IDs/times/evidence/origin/physical
values/equipment incidence. Order, orientation and decimal scale are identity-significant:
1.0 and 1.00 may have different digests; do not silently sort, normalize or round scalars.
Integrity is not approval. No clock/random value in representation. Decode rejects unknown
format, malformed UTF-8 with strict decoder, invalid lengths/counts/optional marker/enums/
nanos, truncated/trailing bytes and domain-invalid values/graphs. Bound allocations from
remaining bytes, not invented operational thresholds. Re-encode and compare full bytes
to reject noncanonical decimal/identity spelling. No Java object serialization or Jackson.
Codec tests include a fixed synthetic fixture with independently specified expected bytes/
digest, scalar/order/orientation/scale sensitivity, negative-time/nanosecond/null-end round
trips and corrupt/unsupported inputs. This preserves full Instant precision in payload.

### Authorized forward migration and append semantics

Create ONLY hidra_topology_physical_network_revision with source_id text NOT NULL,
revision_id text NOT NULL, payload_format text NOT NULL, canonical_payload bytea NOT NULL,
sha256 varchar(64) NOT NULL. Primary key (source_id,revision_id). CHECK normalized nonblank
IDs, exact supported format, nonempty payload, lowercase 64-hex digest and sha256 =
encode(sha256(canonical_payload),'hex') using PostgreSQL's built-in SHA-256. Metadata/
geometry occur once in authoritative bytes; no lossy duplicate timestamp columns, arbitrary
text size limit, FK to mutable live assets/catalogues/Simulation, seed/backfill/default
geometry/approval or pgcrypto extension. Synthetic/historical revisions remain independent
of live eligibility. Preserve all prior migrations/tables/rows.

Create a Topology-named trigger function, BEFORE UPDATE OR DELETE row trigger and BEFORE
TRUNCATE statement trigger that always reject mutation. These normal-write protections
do not claim administrators cannot disable triggers. One authorized forward SQL file only.

Adapter @Repository, constructor-injected JdbcTemplate, owns standard codec. append is
@Transactional: compute bytes/digest internally; parameterized INSERT ... ON CONFLICT
(source_id,revision_id) DO NOTHING then separate exact SELECT and compare format/full
bytes/digest. Identical replay succeeds; different content rejects; never DO UPDATE.
Separate SELECT sees a concurrent committed winner under ordinary READ COMMITTED; no
claim of arbitrary isolation retry support. Propagated failures roll back transaction.
Every read recomputes digest, checks format, decodes/re-encodes, verifies payload identities
match row key and rejects corruption. Caller-supplied hash is never trusted; arrays do
not escape public DTOs. A hash-consistent but domain-invalid row still fails read.

### Acceptance tests and complete verification

PostgreSQL test uses existing PostgreSQL 16 Testcontainers, new migration on disposable
schema, real JdbcTemplate adapter, transaction proxy/manager and owner query service.
Persist two synthetic revisions of one source with changed geometry; query both exact
identities through public contract; compare all ordered scalars/metadata/origin/evidence/
digest. Original remains identical after replacement and replay; missing revision is empty.
No mocks count as persistence evidence. Cover sequential/concurrent identical replay,
competing conflicting content with exactly one winner, transaction rollback, direct SQL
duplicate/bad-digest/blank-ID/unknown-format rejection, UPDATE/DELETE/TRUNCATE rejection,
empty-schema creation and existing sentinel preservation. Direct SQL hash-consistent corrupt
bytes/mismatched payload key must fail owner read. Bounded concurrent waits/separate
connections and real transaction proxies must exercise transaction annotations.

Docker-disabled skips are reported as skipped, never acceptance success. Completion needs
PostgreSQL tests actually run in exact-head CI plus full verify green. No workflow changes
to suppress failures/skips. Query unit tests cover mapping/normalization/missing/integrity
failure; contract/domain/codec tests cover meaningful independent invariants.

Run C3A's previously registered focused command before its separate commit. C3B runs:
`bash ./mvnw -B -q -Dtest=TopologyPhysicalNetworkRevisionTest,TopologyPhysicalNetworkRevisionCodecTest,SimulationPhysicalNetworkRevisionContractTest,TopologySimulationPhysicalNetworkRevisionQueryServiceTest,TopologyPhysicalNetworkRevisionPostgresIntegrationTest,SimulationPhysicalNetworkInputTest,SimulationPhysicalInputPayloadTest,ArchitectureGuardrailTest,ForensicRemediationClosureTest test`
then `bash ./mvnw -B -q clean verify`. Bash preserves tracked wrapper permissions.
Run all 37 maintained validator tests, canonical docs, offline OpenAPI, P1 evidence,
whitespace and exact per-commit/delivery/header/import checks. Both export registries
remain unchanged and checked. Report toolchain/dependency failures accurately.

Ownership override public.hidra_topology_physical_network_revision: owner topology,
purpose append-only physical source revisions; evidence new migration, adapter and domain
record. SCHEMA_OWNERSHIP and topology module document explain JDBC owner, immutable
identity/query boundary, supplied SI/evidence, synthetic limitations and missing behavior.
Database README records actual capture provenance/counts/ownership consequences while
preserving historical deployment/recovery evidence. No new catalogue subjects introduced.

Regenerate DATA_DICTIONARY with existing generator from an ACTUAL full-chain migrated
PostgreSQL catalog and exact source inventory with reviewed ownership override. Retain
schema.json/verification as temporary evidence only; use collect/render then --check.
Do not fabricate catalog facts, hand-invent table metadata or suppress comparison. If
local collection is unavailable, obtain equivalent exact-source disposable catalog evidence
before C3B publication; otherwise report blocker and stop before branch advancement.
No dependency/workflow edits to evade this gate. Offline OpenAPI remains identical.

### Current validation and next control

This docs-only preflight runs maintained validator tests, canonical docs/offline OpenAPI,
P1 evidence, whitespace and exact two-file/historical/version checks. No Maven/runtime
persistence success claimed here. Publish with lease, verify remote parent/tree/two paths,
observe Documentation CI startup and STOP. Next after success selects C3A+C3B network
implementation; after successful delivery select shared fluid/equipment preflight unless
owner explicitly selects separately admitted 008A design. No automatic next delivery.
P0/P1/P2 CLOSED, P2.5 OPEN, P3 DEFERRED, 57 HMR implementations, 123 permanent subjects,
0.7.0-SNAPSHOT retained. No solver, calibrated GZ2, direct OT actuation, inferred operating
threshold or verified deployment claim. C3/C/002 remain PENDING.

NETWORK-PREFLIGHT validation PASSED: 37 maintained validator tests; canonical docs
(95 documents, 5063 links, 24 modules, 13 P2 rows); offline OpenAPI (244 paths,
263 operations, 231 schemas); P1 evidence; whitespace; exact two-document scope;
historical content/version retained. No source mutation or Maven/runtime persistence pass
claimed. Shared preflight registered; C3A/C3B remain PENDING until delivery implementation
and applicable exact-head CI. Documentation CI on this publication awaits observation.

## HPR-P25-002C3A Local Candidate — 2026-10-09

Owner Next resumed the already registered network delivery. Remote main remains
2a66db636d77416fb675c24591b027cfbd428303 (tree
5a46b1835a55402a1819c40ff9bc5508f9adbea7); Documentation #164/run 37988273509
PASSED on that exact head. Production #620/run 37983724675 PASSED on executable
baseline 15795dc000c60080dea0566d275d42d843420ba8.

Prepared the exact C3A domain record and 19 synthetic JUnit test methods. Explicit
source/revision/scope/time/origin/evidence, supplied SI values and ordered defensive
lists are retained; connectivity uses pipes plus equipment incidence. Equipment can
join pipe subnetworks or provide a node's only incidence. No defaults, approvals,
physical inference, solver, public write API or architecture-export mutation.

C3A remains PENDING: local candidate only, not published or CI-confirmed. Focused
Maven validation and clean verify both failed before compilation: uncached Spring
Boot parent 4.1.1 cannot resolve because repo.maven.apache.org DNS fails. Environment
has Java 17, not required Java 21; no javac executable, Docker or PostgreSQL tools.
Package installation also failed on runtime setgroups/setegid/seteuid operations.
Compilation through Java 17's existing jdk.compiler module succeeded for the domain
record and its actual local exceptions; this is not Java 21/Maven/JUnit acceptance.

The required dictionary generator refused collection outside its designated disposable
Actions database. No full-chain catalog for the new migration exists in this local
execution; old-source artifacts cannot substitute. Do not fabricate dictionary metadata
or advance main without exact-source migrated-catalog evidence. Keep C3B and all
C3/C/002 parents PENDING. Separate C3A/C3B commits and single leased main advancement
remain the publication protocol once required evidence is available. Retain
0.7.0-SNAPSHOT, P0/P1/P2 CLOSED, P2.5 OPEN, P3 DEFERRED, 57 HMR implementations
and 123 permanent semantic subjects.

Local candidate checks: 21 independent standalone Java 17 domain smoke checks PASSED
(normalization, explicit decimal values, equipment-only incidence/joins, effective interval,
immutable lists/hash, duplicates/collisions, isolated/disconnected/dangling graphs, self
links, invalid values and mesh/parallel orientation). All 37 maintained validator tests,
canonical docs (95 documents/5063 links/24 modules/13 P2 rows), offline OpenAPI
(244 paths/263 operations/231 schemas), P1 evidence, whitespace and header/import
checks PASSED. Both architecture export registries are unchanged. No JUnit, Java 21,
Maven full verification, PostgreSQL acceptance or new exact-head CI pass is claimed.
A local four-path C3A checkpoint uses its registered exact message; main remains at the
verified preflight. C3B cannot publish until actual full-chain catalog capture/regeneration
is available. No dictionary is hand-edited and no parent is closed.

## HPR-P25-002C3B Network Delivery Candidate — 2026-10-09

Owner Next resumed the existing envelope; no new preflight or deeper code was introduced.
C3A local parent 7a2b09e359a64a7a8468b2f1fa9e4eef2363e1cd retains its exact four
paths and registered message. C3B prepares its ten registered new paths and six of seven
updated paths. DATA_DICTIONARY remains unchanged until actual full-chain migrated
PostgreSQL evidence is obtained; no physical metadata is invented. The registered
forward migration is still V20261009_001, separate from all existing asset/snapshot tables.

Candidate implementation: exact append/find/findStored owner port, standard-Java immutable
Simulation DTOs, read-only owner query, deterministic binary codec with signed decimal
scale/full Instant precision/strict UTF-8/re-encoding, SHA-256 verified JDBC reads, atomic
identical replay and conflicting content rejection, normal-write UPDATE/DELETE/TRUNCATE
triggers and reviewed JDBC ownership. Meaningful codec/contract/query/PostgreSQL tests
cover exact scalars, corruption, replay, real proxied transactions, concurrent conflicts,
rollback, sentinel preservation and full-chain Flyway catalog capture. No new write API,
Simulation resolver, dependency/workflow/export/catalogue/Kernel/Platform change.

Local Java 21/Maven and PostgreSQL acceptance remain unavailable. A candidate branch/draft
PR may use unchanged CI to obtain equivalent actual full-chain disposable catalog evidence
from the integration test's compressed schema-only job-log capture. No main advancement,
completion status, runtime success or final dictionary capture is implied by this candidate.
C3A/C3B/C3/C/002 remain PENDING; 0.7.0-SNAPSHOT and all historical phase/catalogue counts
are retained. Once exact-source capture is validated, regenerate/check dictionary, preserve
separate C3A/C3B commits, advance main once with expected-parent lease, observe final
Production/Documentation CI startup and STOP.

C3B local candidate validation: standard-Java port/DTO/codec compilation using the
existing Java 17 compiler module PASSED; independent expected bytes/digest/negative-scale
roundtrip and 235 malformed/truncated payload smoke checks PASSED. All 37 maintained
validator tests, canonical docs (95 documents/5073 links/24 modules/13 P2 rows), offline
OpenAPI (244 paths/263 operations/231 schemas), P1 evidence and whitespace PASSED.
Registered focused Maven command and clean verify both FAILED before compilation on the
uncached Spring Boot 4.1.1 parent/Maven Central DNS failure. No JUnit or PostgreSQL
acceptance is claimed. Validation candidate has sixteen C3B changed paths (ten new/six
updated); the final seventeen-path C3B commit must additionally include the actually
regenerated dictionary. Candidate CI is evidentiary staging, not main publication or task
closure. Both architecture export registries and all workflows are unchanged.

## HPR-P25-002 Network Candidate Java 21 Validation — 2026-10-09

Owner requested no PRs. Draft #135 was CLOSED WITHOUT MERGE; do not create/reopen/use
PRs for this delivery. Main remains 2a66db636d77416fb675c24591b027cfbd428303. Remote
candidate C3A is 93cbff8839048ba82ea22ae0ae0bd36011966654, parent verified preflight;
its tree matches the original local C3A checkpoint. Candidate C3B
02a23fafbbcf0143dd4f9279cffcfabccde7f826 had Documentation #165 PASSED. Production
#621/run 37992042813 attempts 1 and 2 FAILED before checkout on Docker Hub anonymous
postgres:16 pull rate limits. Zero tests and no migrated-catalog evidence were produced.
The closed PR and past run are historical staging only; no further PR selection is admitted.

Recovered local environment without repository dependency/workflow changes: downloaded
Ubuntu OpenJDK 21.0.12 and PostgreSQL 16.15 into scratch via existing APT, configured
Maven's existing runtime proxy per invocation, and resolved the actual Spring Boot 4.1.1
parent/dependencies. The earlier Java 17/DNS blockage is historical, not the current local
Java validation state. Mockito's default dynamic attachment was unavailable here; using
its existing dependency as a startup Java agent fixed local execution without changing
POM, mocking behavior or assertions. Temporary tools/proxy configuration remain outside Git.

Codec review found independently declared decimal scales could expand a short malicious
payload. Reconstruction now requires canonical plain notation; nonnegative scale must
match supplied fraction digits, and nonzero negative scale cannot exceed supplied trailing
zeros. Signed negative scales, including zero at Integer.MIN_VALUE scale, remain exact.
This bounds reconstruction by supplied bytes without inventing a physical threshold.
An additional meaningful regression test covers extreme signed scales and negative-scale
zero roundtrip; only the already registered codec and codec-test paths changed.

Final Java 21 focused command used all nine registered test classes plus local Maven
settings/startup agent: 95 tests total, 86 PASSED, 9 PostgreSQL tests SKIPPED, zero failures
or errors. Full bash ./mvnw -B -q clean verify with the same local environment settings
and startup agent PASSED: 1430 tests total, 1136 PASSED, 294 Docker-dependent SKIPPED,
zero failures/errors, 339 Surefire report files. Both architecture guardrail suites ran
and PASSED; export registries unchanged. Source/test compilation and real JUnit execution
are now established locally, not just standalone smoke checks. No exact-head CI pass or
PostgreSQL acceptance is inferred from these results.

Docker remains unavailable. Local PostgreSQL binaries work but initdb refuses the root-only
runtime; no unprivileged UID is mapped and an ordinary namespace attempt was denied.
No server was started and no root guard was bypassed. The actual full-chain PostgreSQL
catalog and dictionary regeneration remain mandatory and unavailable. DATA_DICTIONARY
remains unchanged; final C3B still requires its seventeenth changed path from actual
catalog evidence. Preserve CI dictionary comparison and no-PR/direct-main lease protocol.
C3A/C3B and all parents remain PENDING. No main advancement, phase closure, release,
solver, calibrated GZ2 or operational approval claim. 0.7.0-SNAPSHOT retained.

## HPR-P25-002 Network Candidate PostgreSQL Evidence — 2026-10-09

No-PR manual Production #622/run 37996634815 executed exact candidate
4c771cf86cc5ff61a341f451d90ad7c241e2422a. Repository clean verify PASSED:
1430 tests, zero failures/errors/skips. All nine network PostgreSQL tests ran;
actual full-chain capture contained 140 migrations, 470 JPA mappings, 483 relations
and zero unresolved owners. Maintained documentation/production validators and runtime
OpenAPI equality PASSED. Overall CI FAILED at current database dictionary comparison;
the dictionary still records the historical 139-migration/482-relation capture.

Recovered the complete 96-part schema-only catalog from that exact job's logs.
Its canonical SHA-256 a1952d019fbc6932660303c1525649a10d6112811a4d6288b763fb5f8fc9d7e1
matches emitted verification. Existing generator validated exact source inventory and
reviewed ownership; regenerated temporary dictionary SHA-256 is
ef9a0537f595295423207783ae11f13d039500455ef5f1552640c6de72c60167.
The existing --check reproduces Physical dictionary drift. This is real disposable
PostgreSQL evidence, not fabricated schema facts or production deployment evidence.

Capture used postgres:16-alpine with server_version 16.15; the unchanged maintained CI
collector uses postgres:16 with Debian version metadata. Comparison ignores only the
validated source-SHA line. Align the already registered integration-test image to the
existing CI postgres:16 image before obtaining a fresh equivalent capture; do not invent
or normalize catalog facts, alter the generator or weaken comparison. DATA_DICTIONARY
remains unchanged pending that fresh evidence. Only the registered test and two execution
memories change in this candidate correction; separate C3A/C3B chain retained.
Main remains 2a66db636d77416fb675c24591b027cfbd428303. C3A/C3B and all parents
remain PENDING; no later delivery, release, solver, calibrated GZ2 or operational approval
claim. 0.7.0-SNAPSHOT retained. Dispatch existing candidate CI without PR, observe startup
and STOP; inspect fresh capture on the next owner selection before dictionary publication.

## HPR-P25-002 Network Candidate Dictionary Regeneration — 2026-10-09

No-PR manual Production #623/run 37997704862 executed exact candidate
c82cf6a80425dc191b2c00c290375a5406b54620. Full clean verify PASSED with 1430
tests, zero failures/errors/skips, including actual network PostgreSQL acceptance.
Documentation/production validators and runtime OpenAPI equality PASSED. Overall run
FAILED at maintained dictionary comparison because the historical checked-in dictionary
was deliberately awaiting actual aligned catalog capture. Compatibility checks after
that failed step did not run; no overall CI pass is claimed.

The complete 97-part schema-only capture was recovered and its canonical SHA-256
verified as e6fe19c09169f6576ba92ec84f08adf8ec8ad14c08f70792917aef7f5a2e090d.
Actual PostgreSQL server metadata is 16.15 (Debian 16.15-1.pgdg13+2), matching the
maintained CI collector image. Existing generator validates the exact source bundle
9462e6a083bcb9b0b2a4f78da230fae396c318b5a05ecafd0f4998d27db77900 and reviewed
ownership: 140 migrations, 470 JPA mappings, 483 relations, zero unresolved owners.
DATA_DICTIONARY is regenerated from this actual catalog using --input/--source-sha/
--ownership/--render; its SHA-256 is
0ef393290fe02cbb1086c6992b9a63a3e135ccba03d5efe19fef585edd197756.
No schema facts were invented or hand-edited. Existing --check must now pass locally
and maintained CI comparison remains unchanged. This disposable catalog establishes
source-schema applicability only, not deployment, calibration or operational approval.

The candidate now includes all seventeen registered C3B paths (ten new/seven updated),
with nineteen distinct paths across the separate C3A/C3B delivery. Only dictionary,
database README and two execution memories change in this evidence-finalization step.
Preserve C3A 93cbff8839048ba82ea22ae0ae0bd36011966654 as separate parent; no PR.
Main remains 2a66db636d77416fb675c24591b027cfbd428303 until candidate full CI
including dictionary comparison and compatibility is green. C3A/C3B and all parents
remain PENDING pending publication; 0.7.0-SNAPSHOT and all phase/catalogue counts retained.
Dispatch existing candidate CI, observe startup and STOP. After green candidate evidence,
record executed leaf completion, advance main once with expected-parent lease, observe
final exact-head Production/Documentation CI startup and STOP; no later delivery selected.

## HPR-P25-002 Network Revision Delivery Publication — 2026-10-10

Owner Next selects publication of the already registered C3A+C3B network envelope,
without a PR. Production #624/run 37999445146 PASSED on exact candidate
570e3b816e5b04c56c6e2c25969461c79e63a998: full clean verify executed 1430 tests,
zero failures/errors/skips, including all nine actual PostgreSQL acceptance tests.
Maintained documentation/production validators, runtime OpenAPI equality, regenerated
physical dictionary comparison and supported OpenAPI backward compatibility all PASSED.
The maintained collector verified 140 migrations, 470 JPA mappings, 483 relations,
zero unresolved owners and exact source bundle
9462e6a083bcb9b0b2a4f78da230fae396c318b5a05ecafd0f4998d27db77900.
Its actual catalog SHA-256 is 478e4f372dd9e9476d2a58e7ff60346abe96568007bac706162a7c7390d88db3;
source-head-rendered dictionary SHA-256 is
4d2d751673dd13f249e37b2e60fbde2ca806310a849e99d25fbd2db4d4e359fe.
The checked-in dictionary retains its verified C82 capture source line; the unchanged
comparison ignores only that validated provenance line. No schema facts are fabricated.

| Code | Current disposition | Evidence and scope |
|---|---|---|
| HPR-P25-002C3A | COMPLETED | Immutable supplied-SI physical network revision; separate commit 93cbff8839048ba82ea22ae0ae0bd36011966654, exact four paths; acceptance exercised in #624 |
| HPR-P25-002C3B | COMPLETED | Append-only JDBC source revisions, strict deterministic codec/SHA-256, owner exact-revision export, PostgreSQL migration/acceptance and actual generated dictionary; exact seventeen paths, #624 PASSED |
| HPR-P25-002C3C..F | PENDING | No later physical source delivery selected |
| HPR-P25-002C3 / HPR-P25-002C / HPR-P25-002D / HPR-P25-002 | PENDING | No parent closure or calculation-input delivery selected |

These leaf dispositions supersede dated candidate-pending records; historical evidence
is retained. Final C3B publication changes only the two execution memories from the
fully green executable/dictionary candidate, preserving its separate C3A parent and
nineteen distinct delivery paths. Main advances once from verified expected parent
2a66db636d77416fb675c24591b027cfbd428303 using a lease. Verify final remote chain,
per-commit scope and tree; observe final exact-head Production/Documentation CI startup
and STOP. Final-head CI completion is pending, not inferred from the candidate pass.
No PR, workflow/dependency/export-registry/Kernel/Platform/Simulation-resolver mutation.

P0/P1/P2 CLOSED; P2.5 OPEN; P3 DEFERRED. All 57 HMR implementations and 123 permanent
subjects retained; 0.7.0-SNAPSHOT unchanged. No executing solver, calibrated GZ2,
operational approval, deployed current schema or direct OT actuation is established.
Next registered selection after network-delivery CI is the shared fluid/equipment
preflight: docs(twin): register fluid equipment delivery preflight. HPR-P25-008A
numerical design remains separately selectable; neither next item executes automatically.
