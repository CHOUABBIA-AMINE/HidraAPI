# HidraAPI Semantic Remediation Reconciliation

## Status

CURRENT — HPR-P2-007 exact-source reconciliation.

## Verification Baseline

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

The HMR-054 semantic implementation itself is still not evidenced by its registered migration/test, so execution remains required under HPR-P2-008 after normal preflight.

### HMR-080 — planning.Nomination

**BLOCKED — Party→Planning owner contract remains absent.**

No `party.application.contract.planning` package exists in the exact current tree. HPR-P2-008 must not invent a direct Party-domain/repository dependency or cross-module FK. The owner contract must be explicitly introduced/authorized before HMR-080 can execute.

## Reconciled Outstanding Register

| HMR | HMSR | Subject | Canonical disposition | Exact-current evidence |
|---|---|---|---|---|
| HMR-050 | HMSR-059 | integrity.IntegrityProgram | COMPLETED — HPR-P2-008 | legacy migration not required after current-schema revalidation; dedicated semantic test added; active `INTEGRITY_PROGRAM_TYPE` family enforced; Organization-owned Integrity contract validates populated owner unit; no cross-module FK |
| HMR-051 | HMSR-060 | leakdetection.LeakDetectionCase | COMPLETED — HPR-P2-008 | Topology and optional Organization references validated on every case save; snapshot preserved; no migration because primary-candidate FK already exists; owner contract and architecture export added. |
| HMR-052 | HMSR-061 | notification.NotificationMessage | COMPLETED — HPR-P2-008 | Recipient/request composite FK; exact-version/template FK and pre-dispatch composition guard; required-input schema checked for sendable states; inputs/version frozen; active NOTIFICATION_PRIORITY eligibility; V20261006_005; dedicated unit and PostgreSQL tests added. |
| HMR-053 | HMSR-062 | telemetry.TrustedTelemetryReading | COMPLETED — HPR-P2-008 | Telemetry trust application use case derives values/provenance/binding from locked source evidence; PASSED plus MEDIUM/HIGH/CERTIFIED, ACTIVE point and active QUALITY_CODE required; composite assessment/reading identity and optional unit/batch FKs; snapshot preservation; V20261006_008; focused unit and PostgreSQL tests added; CI pending. |
| HMR-054 | HMSR-063 | topology.Equipment | COMPLETED — HPR-P2-008 | EquipmentType identity/code is sole active classification; EquipmentKind deleted from domain/JPA; forward V20261006_009 preserves legacy strings, rejects conflicting classification/orphan attachments and adds nullable same-module FKs; manufacturer checked by existing Party contract; snapshots preserved; focused tests added; CI pending. |
| HMR-055 | HMSR-064 | workflow.WorkflowInstance | COMPLETED — CI #581 GREEN | Owner-bound starts enforce active definition/version and exact binding, governed purpose/type, current-step coherence and owner target/actor snapshots; nonterminal uniqueness and same-definition/version database guards. Planning target registry denies unsupported/ambiguous owners. Eight focused behavior checks passed with temporary stubs; local Maven blocked by uncached parent, not a JUnit/PostgreSQL pass. |
| HMR-056 | HMSR-067 | integration.IntegrationExchangeMessage | COMPLETED — CI #585 PASSED | Optional run/endpoint existence, correlated endpoint/system ownership and active exact existing MESSAGE_TYPE/PAYLOAD_FORMAT catalogs enforced on saves. Forward V20261007_010 adds nullable/composite FKs and catalog guards without rewriting legacy evidence. Five dedicated and five PostgreSQL cases prepared. Local compile/focused Maven blocked before execution by uncached Boot parent; CI pending. |
| HMR-057 | HMSR-068 | reporting.ReportRun | COMPLETED — CI #588 GREEN | Queue eligibility/access/approval, exact template lineage, concrete required parameters and terminal evidence enforced. Forward 012 corrects run/parameter request FKs and guards lineage/history. Eight Run, four QueueEvidence and nine PostgreSQL cases prepared; local runtime validation follows; Full production CI #588 passed. |
| HMR-058 | HMSR-069 | risk.RiskAssessment | COMPLETED — CI #593 GREEN | Atomic structured scopes, matrix provenance, authenticated Workflow/Audit approval and approved immutability; Java 21 clean verify and OpenAPI compatibility passed at cfc7798477c70d10e1c3e0afd4dd7e1b42676898. |
| HMR-059 | HMSR-071 | leakdetection.LeakEscalationReference | COMPLETED — HPR-P2-008 | Optional candidate validated before save and protected by V20261006_001 nullable same-module FK with fail-closed orphan preflight; no case-primary equality rule. |
| HMR-060 | HMSR-072 | notification.NotificationDeliveryAttempt | COMPLETED — HPR-P2-008 | Channel/message composite FK; create-only EntityManager.persist plus PK race protection; update/delete/truncate rejected; permanent/cancelled automatic retry rejected; V20261006_006; dedicated unit and PostgreSQL tests added. |
| HMR-061 | HMSR-073 | workflow.WorkflowTransition | COMPLETED — CI #581 GREEN | Distinct same-definition steps and unique source decisions are protected in configuration persistence and PostgreSQL. Unsupported conditions/callbacks/COMMENT cannot attach to ACTIVE definitions or survive activation; runtime remains fail closed. Three focused behavior checks passed with temporary stubs; PostgreSQL validation pending CI. |
| HMR-062 | HMSR-074 | incident.Incident | COMPLETED — CI #595 GREEN | Batch 15 and inventory repair passed full Java 21/PostgreSQL/OpenAPI CI at e2e92bae7d69c54a46fa92702b539858404bf7ce. |
| HMR-063 | HMSR-075 | identity.User | COMPLETED — HPR-P2-008 | Nonblank username enforced in domain; PostgreSQL named username/email uniqueness, nullable-email semantics and fail-closed legacy preflight; optional Employee resolved through Organization-owned contract; no cross-module FK. V20261006_010 and focused tests added; final CI pending. |
| HMR-064 | HMSR-076 | planning.PlanRevision | COMPLETED — CI #582 GREEN | Positive per-plan revision numbers, nullable validated base lineage, active REVISION_REASON and approved metadata/persistence/database immutability enforced. Forward V20261007_001; four focused checks passed using temporary API/assertion stubs, not Maven/JUnit. Five PostgreSQL cases registered; local compile/focused Maven blocked by uncached Boot 4.1.1 parent. Full database validation pending CI. |
| HMR-065 | HMSR-077 | planning.OperationalPlan | COMPLETED — CI #582 GREEN | Required French name/scope type, unique plan code, active PLAN_TYPE, owner-controlled Topology/Identity/Organization references and same-plan nullable revision pointers enforced. Creation binds authenticated eligible actor and snapshots owner display values; unsupported REGION/NETWORK denied. Forward V20261007_002; twelve focused HMR-065/owner/catalog checks passed with temporary API stubs. Nine combined PostgreSQL cases registered; Maven compile/focused/full test/clean verify blocked before compilation by uncached Boot 4.1.1/Maven Central DNS. Full CI pending. |
| HMR-066 | HMSR-078 | workflow.WorkflowTask | COMPLETED — CI #581 GREEN | Actionable assignment, live actor/unit membership, catalog eligibility, actor/time pairs and chronology are enforced. Terminal task evidence is immutable; generic creation is starter-bound, missing next-step rules fail closed, and execution/query paths no longer authorize by username snapshots. Seven focused behavior checks passed with temporary stubs; existing transition fixtures updated for new owner dependencies. |
| HMR-067 | HMSR-079 | documents.Document | COMPLETED — CI #583 GREEN | Required title/creator display, active exact document catalogs, code uniqueness and same-document current-version pointers enforced. Registration binds authenticated eligible Identity actor and canonical owner snapshots; neutral registry supports Topology/Planning and denies missing/ambiguous owners. Forward V20261007_003; 11 focused methods passed with temporary APIs, four PostgreSQL cases added. Compile/focused Maven blocked before compilation by uncached Boot 4.1.1 parent; full CI #583 passed. |
| HMR-068 | HMSR-080 | documents.DocumentVersion | COMPLETED — CI #583 GREEN | Required upload metadata, positive per-document unique numbers, nullable existing supersession and owner-controlled Identity/Workflow references enforced. Generic upload derives authenticated uploader display. Binary prevalidates metadata and registers known-rollback new-blob cleanup; failed cleanup preserves original error, unknown commit outcome preserves content and logs reconciliation. Forward V20261007_004; 10 focused owner/version/cleanup methods passed with temporary APIs; nine combined PostgreSQL cases include transactional storage rollback and confirmed commit failure. Local focused Maven blocked by uncached Boot 4.1.1 parent; full CI #583 passed. |
| HMR-069 | HMSR-081 | assets.MaintenanceWorkOrder | COMPLETED — CI #596 GREEN | Batch 16 passed full Java 21/PostgreSQL/OpenAPI verification at 68e330562b03cf92c5500b99ffceca1fd024d083. |
| HMR-070 | HMSR-082 | custody.CustodyTransferTicket | COMPLETED — CI #596 GREEN | Batch 16 passed full Java 21/PostgreSQL/OpenAPI verification at 68e330562b03cf92c5500b99ffceca1fd024d083. |
| HMR-071 | HMSR-084 | integration.IntegrationDeadLetterRecord | COMPLETED — CI #585 PASSED | Required failure evidence, all-or-none manual trio and optional local references enforced. New manual evidence requires authenticated eligible Identity actor; recorded provenance is immutable without historical actor revalidation. Forward V20261007_011 supplies nullable FKs/checks and concurrent provenance guard. Eight focused methods, one Identity owner method and five added PostgreSQL cases prepared. Temporary API type compilation passed; local focused Maven blocked by uncached Boot parent; CI pending. |
| HMR-072 | HMSR-085 | integrity.IntegrityAssessment | COMPLETED — CI #596 GREEN | Batch 16 passed full Java 21/PostgreSQL/OpenAPI verification at 68e330562b03cf92c5500b99ffceca1fd024d083. |
| HMR-073 | HMSR-087 | organization.EmployeeAssignment | COMPLETED — HPR-P2-008 | Assignment service resolves same-module OrganizationUnit and rejects missing or non-ACTIVE units before save; existing employee/unit/position FKs retained; no migration. |
| HMR-074 | HMSR-088 | organization.OrganizationDelegation | COMPLETED — HPR-P2-008 | JPA responsibility_assignment_id is mandatory; V20261006_002 aborts on legacy null rows before SET NOT NULL; existing same-module FK preserved; real PostgreSQL focused tests added. |
| HMR-075 | HMSR-089 | organization.OrganizationHierarchySnapshot | COMPLETED — HPR-P2-008 | JPA captured_by_employee_id is mandatory; V20261006_003 aborts on legacy null rows before SET NOT NULL; existing same-module FK preserved; real PostgreSQL focused tests added. |
| HMR-076 | HMSR-090 | organization.ShiftAssignment | COMPLETED — HPR-P2-008 | JPA organization_unit_id is mandatory; V20261006_004 aborts on legacy null rows before SET NOT NULL; existing same-module FK preserved; real PostgreSQL focused tests added. |
| HMR-077 | HMSR-091 | risk.RiskEvidenceLink | COMPLETED — CI #593 GREEN | Typed owner evidence validation and named provider beans verified by Java 21 clean verify and OpenAPI compatibility at cfc7798477c70d10e1c3e0afd4dd7e1b42676898. |
| HMR-078 | HMSR-092 | simulation.SimulationCandidateChange | COMPLETED — CI #590 GREEN | Accepted SIM-PREREQ-01; required change values, active exact catalog and Topology target lookup; forward 001. |
| HMR-079 | HMSR-093 | simulation.SimulationRecommendation | COMPLETED — CI #590 GREEN | Required content, exact catalogs, nullable local references and transactional Audit-owned publication; forward 002/003. |
| HMR-080 | HMSR-094 | planning.Nomination | BLOCKED — OWNER CONTRACT REQUIRED | registered migration: absent; dedicated test: absent; Party→Planning contract absent |
| HMR-081 | HMSR-095 | workflow.WorkflowAction | COMPLETED — CI #581 GREEN | Generic recording permits comments only; configured transitions exclusively produce decisions using live Identity authority. Optional task ownership and conditional evidence are enforced; canonical actor snapshots and server-owned locked sequences replace caller evidence. Action persistence is insert-only with unique monotonic sequence and immutable database guards. Five focused behavior checks passed with temporary stubs; existing permission regression fixture updated. |
| HMR-082 | HMSR-096 | hse.HseCase | COMPLETED — CI #597 GREEN | Accepted Batch 17 implementation passed full Java 21/PostgreSQL/OpenAPI CI on cfb3681ef1c79b4416336a3533cbc0599b4fd6b2. |
| HMR-083 | HMSR-097 | audit.AuditExportRequest | COMPLETED — CI #584 GREEN | Required export metadata, active EXPORT_PURPOSE, owner-controlled optional Workflow/Documents references, bounded sanitized filters and one transactional EXPORT access record implemented. Generic writes admit REQUESTED only and persist/flush without merge. Forward V20261007_006; eight focused tests, two owner tests and four PostgreSQL/Spring/JPA tests prepared. Local Maven compile/focused blocked before compilation by uncached Boot 4.1.1 parent; production CI #584 passed. |
| HMR-084 | HMSR-098 | documents.DocumentTargetLink | COMPLETED — CI #583 GREEN | Required target module, active exact DOCUMENT_LINK_ROLE and owner-controlled target resolution enforced. Authenticated linking actor and canonical owner snapshots replace caller identity/display claims; optional version must belong to linked document, protected by composite FK. Forward V20261007_005; four focused methods passed with temporary APIs and three PostgreSQL cases added (12 combined). Both public export registries match 34 exact packages; all five forensic scans passed with temporary APIs. Focused Maven blocked by uncached parent; full CI #583 passed. |
| HMR-085 | HMSR-100 | identity.AuthorizationDecision | COMPLETED — CI #579 PASSED | Transactional graph, bounded ABAC, verified mappings, deterministic evidence and configurable persistence; Batch 6 implementation below. |
| HMR-086 | HMSR-101 | identity.AuthorizationDelegationGrant | COMPLETED — HPR-P2-008 | Required nonblank delegation reason and validTo carried through domain/JPA/mapper; DelegationStatus narrowed to ACTIVE/REVOKED/EXPIRED; optional Role and Permission validated with nullable same-module FKs; no XOR rule; V20261006_011 fails closed on legacy evidence; focused tests added; final CI pending. |
| HMR-087 | HMSR-104 | identity.LoginSession | COMPLETED — HPR-P2-008 | AuthenticationProtocol sessionType and independent endedAt carried through domain/JPA/mapper; exact ExternalIdentity propagated from LDAP/OIDC through principal/input/completion; terminal lifecycle preserves lastSeenAt and prior termination; V20261006_012 requires explicit legacy protocol evidence; no inferred historical termination; focused tests added; final CI pending. |
| HMR-088 | HMSR-105 | identity.UserPermissionGrant | COMPLETED — HPR-P2-008 | Domain and PostgreSQL enforce nonblank grantReason, bounded validTo and ACTIVE/REVOKED/EXPIRED for direct permission grants including emergency records; shared GrantStatus and optional role-grant reason/end remain unchanged; V20261006_013 and focused tests added; final CI pending. |
| HMR-089 | HMSR-106 | identity.UserRoleGrant | COMPLETED — HPR-P2-008 | Authoritative ordinary role-grant application flow requires ACTIVE User before Role lookup/save; inactive states reject without implicit emergency bypass; optional reason/end and shared SUSPENDED role status preserved; focused tests added; no migration required; final CI pending. |
| HMR-090 | HMSR-107 | incident.IncidentClosure | COMPLETED — CI #595 GREEN | Batch 15 and inventory repair passed full Java 21/PostgreSQL/OpenAPI CI at e2e92bae7d69c54a46fa92702b539858404bf7ce. |
| HMR-091 | HMSR-108 | incident.IncidentRelatedIncident | COMPLETED — CI #595 GREEN | Batch 15 and inventory repair passed full Java 21/PostgreSQL/OpenAPI CI at e2e92bae7d69c54a46fa92702b539858404bf7ce. |
| HMR-092 | HMSR-109 | incident.IncidentResponseAction | COMPLETED — CI #595 GREEN | Batch 15 and inventory repair passed full Java 21/PostgreSQL/OpenAPI CI at e2e92bae7d69c54a46fa92702b539858404bf7ce. |
| HMR-093 | HMSR-110 | reporting.ReportOutputArtifact | COMPLETED — CI #588 GREEN | Existing run and nonblank Documents reference evidence required; every supplied reference is independently owner-validated. Forward 013 corrects artifact/run FK without Documents FK. Six focused methods, one Documents owner method and five additional PostgreSQL cases prepared; Full production CI #588 passed. |
| HMR-094 | HMSR-111 | planning.PlanTarget | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-095 | HMSR-112 | audit.AuditEvent | COMPLETED — CI #584 GREEN | Required source/target module and target type, active exact event/category/optional severity/reason families, bounded sanitized payload/free text and persist/flush insertion enforced. Forward V20261007_007 adds optional catalog FKs, family guards and immutable event UPDATE/DELETE denial. Five focused and four added PostgreSQL/JPA/concurrency checks prepared; local focused Maven blocked by uncached Boot parent; CI #584 passed. |
| HMR-096 | HMSR-113 | hse.HseClosure | COMPLETED — CI #597 GREEN | Accepted Batch 17 implementation passed full Java 21/PostgreSQL/OpenAPI CI on cfb3681ef1c79b4416336a3533cbc0599b4fd6b2. |
| HMR-097 | HMSR-114 | hse.HseCorrectivePreventiveAction | COMPLETED — CI #597 GREEN | Accepted Batch 17 implementation passed full Java 21/PostgreSQL/OpenAPI CI on cfb3681ef1c79b4416336a3533cbc0599b4fd6b2. |
| HMR-098 | HMSR-115 | integrity.IntegrityCase | COMPLETED — FINAL CI PENDING | Accepted IC-PREREQ-01: optional defect lookup/FK, explicit case-family metadata, typed owner validation and forward 017/018; final Java 21/PostgreSQL/OpenAPI CI pending. |
| HMR-099 | HMSR-116 | workflow.WorkflowStateHistory | COMPLETED — CI #581 GREEN | Mandatory status/actor display evidence fails fast. History persistence inserts and flushes without upsert; optional task/step/action/reason references are checked for instance/definition and action evidence coherence. Database guards prohibit update/delete/truncate. Four focused behavior checks passed with temporary stubs; ten PostgreSQL/Hibernate cases added for CI, not locally executed. |
| HMR-100 | HMSR-117 | alarm.Alarm | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-101 | HMSR-118 | audit.AuditAccessRecord | COMPLETED — CI #584 GREEN | Access records use persist/flush without merge; populated optional AuditEvent/export IDs must exist. Forward V20261007_008 supplies nullable local FKs and UPDATE/DELETE denial. Three focused and four added PostgreSQL/JPA/concurrency/orphan checks prepared; local focused Maven blocked by uncached Boot parent; CI #584 passed. |
| HMR-102 | HMSR-119 | audit.AuditBeforeAfterValue | COMPLETED — CI #584 GREEN | Required fieldPath, masked/sensitive raw-text exclusion, optional exact active MASK_REASON and existing parent event enforced. Hash-only evidence and changed=false remain legal. Persist/flush insertion plus V20261007_009 local FKs/checks/UPDATE/DELETE denial preserve immutable rows. Six focused and five added PostgreSQL/JPA/concurrency/legacy checks prepared. Temporary API type compilation passed; local focused Maven blocked by uncached Boot parent; CI #584 passed. |
| HMR-103 | HMSR-120 | monitoring.PlanActualDeviation | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-104 | HMSR-121 | alarm.AlarmAcknowledgement | STILL REQUIRED | no migration registered; dedicated test: absent; revalidate obligations before mutation |
| HMR-105 | HMSR-122 | alarm.AlarmClosure | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-106 | HMSR-123 | alarm.AlarmShelving | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |

## Reconciliation Totals

- legacy HMR records parsed: **104**;
- HMR-005 corrected from stale planned status to **COMPLETED**;
- HMR-009 confirmed **COMPLETED** and removed as a carry-over blocker;
- HMR-050..106 evaluated: **57**;
- HMR-050..106 **STILL REQUIRED**: **17**;
- HMR-050..106 **BLOCKED**: **1** (HMR-080);
- HMR-050..106 **IMPLEMENTED during HPR-P2-008**: **39** (37 CI-confirmed through #590; two Batch 14 implementations CI pending);
- HMR-050..106 **SUPERSEDED**: **0**;
- HMR-054 completed; repaired CI #575 is green;
- HMR-080 remains blocked; HMR-055 prerequisite resolved and implemented in Batch 7.

## HPR-P2-008 Progress

- HMR-050 — **COMPLETED** at the first HPR-P2-008 execution step.
- Batch 7 **COMPLETED — CI #581 GREEN**: HMR-055, 061, 066, 081, 099; exact repaired head ec63af0414d7fa85b9200d4bd181ac799bd072ed. Batch 8 preflight split below; no Planning implementation claimed.
- Current remaining: **17 STILL REQUIRED + 1 BLOCKED (HMR-080)**; 37 CI-confirmed and two Batch 14 CI-pending implementations.

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

HMR-080 remains excluded until its owner-contract prerequisite is resolved.

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
