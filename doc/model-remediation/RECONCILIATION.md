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
| HMR-056 | HMSR-067 | integration.IntegrationExchangeMessage | IMPLEMENTED — CI #585 PASSED | Optional run/endpoint existence, correlated endpoint/system ownership and active exact existing MESSAGE_TYPE/PAYLOAD_FORMAT catalogs enforced on saves. Forward V20261007_010 adds nullable/composite FKs and catalog guards without rewriting legacy evidence. Five dedicated and five PostgreSQL cases prepared. Local compile/focused Maven blocked before execution by uncached Boot parent; CI pending. |
| HMR-057 | HMSR-068 | reporting.ReportRun | IMPLEMENTED — CI PENDING | Queue eligibility/access/approval, exact template lineage, concrete required parameters and terminal evidence enforced. Forward 012 corrects run/parameter request FKs and guards lineage/history. Eight Run, four QueueEvidence and nine PostgreSQL cases prepared; local runtime validation follows; CI pending. |
| HMR-058 | HMSR-069 | risk.RiskAssessment | STILL REQUIRED | no migration registered; dedicated test: absent; revalidate obligations before mutation |
| HMR-059 | HMSR-071 | leakdetection.LeakEscalationReference | COMPLETED — HPR-P2-008 | Optional candidate validated before save and protected by V20261006_001 nullable same-module FK with fail-closed orphan preflight; no case-primary equality rule. |
| HMR-060 | HMSR-072 | notification.NotificationDeliveryAttempt | COMPLETED — HPR-P2-008 | Channel/message composite FK; create-only EntityManager.persist plus PK race protection; update/delete/truncate rejected; permanent/cancelled automatic retry rejected; V20261006_006; dedicated unit and PostgreSQL tests added. |
| HMR-061 | HMSR-073 | workflow.WorkflowTransition | COMPLETED — CI #581 GREEN | Distinct same-definition steps and unique source decisions are protected in configuration persistence and PostgreSQL. Unsupported conditions/callbacks/COMMENT cannot attach to ACTIVE definitions or survive activation; runtime remains fail closed. Three focused behavior checks passed with temporary stubs; PostgreSQL validation pending CI. |
| HMR-062 | HMSR-074 | incident.Incident | STILL REQUIRED | no migration registered; dedicated test: absent; revalidate obligations before mutation |
| HMR-063 | HMSR-075 | identity.User | COMPLETED — HPR-P2-008 | Nonblank username enforced in domain; PostgreSQL named username/email uniqueness, nullable-email semantics and fail-closed legacy preflight; optional Employee resolved through Organization-owned contract; no cross-module FK. V20261006_010 and focused tests added; final CI pending. |
| HMR-064 | HMSR-076 | planning.PlanRevision | COMPLETED — CI #582 GREEN | Positive per-plan revision numbers, nullable validated base lineage, active REVISION_REASON and approved metadata/persistence/database immutability enforced. Forward V20261007_001; four focused checks passed using temporary API/assertion stubs, not Maven/JUnit. Five PostgreSQL cases registered; local compile/focused Maven blocked by uncached Boot 4.1.1 parent. Full database validation pending CI. |
| HMR-065 | HMSR-077 | planning.OperationalPlan | COMPLETED — CI #582 GREEN | Required French name/scope type, unique plan code, active PLAN_TYPE, owner-controlled Topology/Identity/Organization references and same-plan nullable revision pointers enforced. Creation binds authenticated eligible actor and snapshots owner display values; unsupported REGION/NETWORK denied. Forward V20261007_002; twelve focused HMR-065/owner/catalog checks passed with temporary API stubs. Nine combined PostgreSQL cases registered; Maven compile/focused/full test/clean verify blocked before compilation by uncached Boot 4.1.1/Maven Central DNS. Full CI pending. |
| HMR-066 | HMSR-078 | workflow.WorkflowTask | COMPLETED — CI #581 GREEN | Actionable assignment, live actor/unit membership, catalog eligibility, actor/time pairs and chronology are enforced. Terminal task evidence is immutable; generic creation is starter-bound, missing next-step rules fail closed, and execution/query paths no longer authorize by username snapshots. Seven focused behavior checks passed with temporary stubs; existing transition fixtures updated for new owner dependencies. |
| HMR-067 | HMSR-079 | documents.Document | COMPLETED — CI #583 GREEN | Required title/creator display, active exact document catalogs, code uniqueness and same-document current-version pointers enforced. Registration binds authenticated eligible Identity actor and canonical owner snapshots; neutral registry supports Topology/Planning and denies missing/ambiguous owners. Forward V20261007_003; 11 focused methods passed with temporary APIs, four PostgreSQL cases added. Compile/focused Maven blocked before compilation by uncached Boot 4.1.1 parent; full CI #583 passed. |
| HMR-068 | HMSR-080 | documents.DocumentVersion | COMPLETED — CI #583 GREEN | Required upload metadata, positive per-document unique numbers, nullable existing supersession and owner-controlled Identity/Workflow references enforced. Generic upload derives authenticated uploader display. Binary prevalidates metadata and registers known-rollback new-blob cleanup; failed cleanup preserves original error, unknown commit outcome preserves content and logs reconciliation. Forward V20261007_004; 10 focused owner/version/cleanup methods passed with temporary APIs; nine combined PostgreSQL cases include transactional storage rollback and confirmed commit failure. Local focused Maven blocked by uncached Boot 4.1.1 parent; full CI #583 passed. |
| HMR-069 | HMSR-081 | assets.MaintenanceWorkOrder | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-070 | HMSR-082 | custody.CustodyTransferTicket | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-071 | HMSR-084 | integration.IntegrationDeadLetterRecord | IMPLEMENTED — CI #585 PASSED | Required failure evidence, all-or-none manual trio and optional local references enforced. New manual evidence requires authenticated eligible Identity actor; recorded provenance is immutable without historical actor revalidation. Forward V20261007_011 supplies nullable FKs/checks and concurrent provenance guard. Eight focused methods, one Identity owner method and five added PostgreSQL cases prepared. Temporary API type compilation passed; local focused Maven blocked by uncached Boot parent; CI pending. |
| HMR-072 | HMSR-085 | integrity.IntegrityAssessment | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-073 | HMSR-087 | organization.EmployeeAssignment | COMPLETED — HPR-P2-008 | Assignment service resolves same-module OrganizationUnit and rejects missing or non-ACTIVE units before save; existing employee/unit/position FKs retained; no migration. |
| HMR-074 | HMSR-088 | organization.OrganizationDelegation | COMPLETED — HPR-P2-008 | JPA responsibility_assignment_id is mandatory; V20261006_002 aborts on legacy null rows before SET NOT NULL; existing same-module FK preserved; real PostgreSQL focused tests added. |
| HMR-075 | HMSR-089 | organization.OrganizationHierarchySnapshot | COMPLETED — HPR-P2-008 | JPA captured_by_employee_id is mandatory; V20261006_003 aborts on legacy null rows before SET NOT NULL; existing same-module FK preserved; real PostgreSQL focused tests added. |
| HMR-076 | HMSR-090 | organization.ShiftAssignment | COMPLETED — HPR-P2-008 | JPA organization_unit_id is mandatory; V20261006_004 aborts on legacy null rows before SET NOT NULL; existing same-module FK preserved; real PostgreSQL focused tests added. |
| HMR-077 | HMSR-091 | risk.RiskEvidenceLink | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-078 | HMSR-092 | simulation.SimulationCandidateChange | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-079 | HMSR-093 | simulation.SimulationRecommendation | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-080 | HMSR-094 | planning.Nomination | BLOCKED — OWNER CONTRACT REQUIRED | registered migration: absent; dedicated test: absent; Party→Planning contract absent |
| HMR-081 | HMSR-095 | workflow.WorkflowAction | COMPLETED — CI #581 GREEN | Generic recording permits comments only; configured transitions exclusively produce decisions using live Identity authority. Optional task ownership and conditional evidence are enforced; canonical actor snapshots and server-owned locked sequences replace caller evidence. Action persistence is insert-only with unique monotonic sequence and immutable database guards. Five focused behavior checks passed with temporary stubs; existing permission regression fixture updated. |
| HMR-082 | HMSR-096 | hse.HseCase | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-083 | HMSR-097 | audit.AuditExportRequest | COMPLETED — CI #584 GREEN | Required export metadata, active EXPORT_PURPOSE, owner-controlled optional Workflow/Documents references, bounded sanitized filters and one transactional EXPORT access record implemented. Generic writes admit REQUESTED only and persist/flush without merge. Forward V20261007_006; eight focused tests, two owner tests and four PostgreSQL/Spring/JPA tests prepared. Local Maven compile/focused blocked before compilation by uncached Boot 4.1.1 parent; production CI #584 passed. |
| HMR-084 | HMSR-098 | documents.DocumentTargetLink | COMPLETED — CI #583 GREEN | Required target module, active exact DOCUMENT_LINK_ROLE and owner-controlled target resolution enforced. Authenticated linking actor and canonical owner snapshots replace caller identity/display claims; optional version must belong to linked document, protected by composite FK. Forward V20261007_005; four focused methods passed with temporary APIs and three PostgreSQL cases added (12 combined). Both public export registries match 34 exact packages; all five forensic scans passed with temporary APIs. Focused Maven blocked by uncached parent; full CI #583 passed. |
| HMR-085 | HMSR-100 | identity.AuthorizationDecision | IMPLEMENTED — CI #579 PASSED | Transactional graph, bounded ABAC, verified mappings, deterministic evidence and configurable persistence; Batch 6 implementation below. |
| HMR-086 | HMSR-101 | identity.AuthorizationDelegationGrant | COMPLETED — HPR-P2-008 | Required nonblank delegation reason and validTo carried through domain/JPA/mapper; DelegationStatus narrowed to ACTIVE/REVOKED/EXPIRED; optional Role and Permission validated with nullable same-module FKs; no XOR rule; V20261006_011 fails closed on legacy evidence; focused tests added; final CI pending. |
| HMR-087 | HMSR-104 | identity.LoginSession | COMPLETED — HPR-P2-008 | AuthenticationProtocol sessionType and independent endedAt carried through domain/JPA/mapper; exact ExternalIdentity propagated from LDAP/OIDC through principal/input/completion; terminal lifecycle preserves lastSeenAt and prior termination; V20261006_012 requires explicit legacy protocol evidence; no inferred historical termination; focused tests added; final CI pending. |
| HMR-088 | HMSR-105 | identity.UserPermissionGrant | COMPLETED — HPR-P2-008 | Domain and PostgreSQL enforce nonblank grantReason, bounded validTo and ACTIVE/REVOKED/EXPIRED for direct permission grants including emergency records; shared GrantStatus and optional role-grant reason/end remain unchanged; V20261006_013 and focused tests added; final CI pending. |
| HMR-089 | HMSR-106 | identity.UserRoleGrant | COMPLETED — HPR-P2-008 | Authoritative ordinary role-grant application flow requires ACTIVE User before Role lookup/save; inactive states reject without implicit emergency bypass; optional reason/end and shared SUSPENDED role status preserved; focused tests added; no migration required; final CI pending. |
| HMR-090 | HMSR-107 | incident.IncidentClosure | STILL REQUIRED | no migration registered; dedicated test: absent; revalidate obligations before mutation |
| HMR-091 | HMSR-108 | incident.IncidentRelatedIncident | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-092 | HMSR-109 | incident.IncidentResponseAction | STILL REQUIRED | no migration registered; dedicated test: absent; revalidate obligations before mutation |
| HMR-093 | HMSR-110 | reporting.ReportOutputArtifact | IMPLEMENTED — CI PENDING | Existing run and nonblank Documents reference evidence required; every supplied reference is independently owner-validated. Forward 013 corrects artifact/run FK without Documents FK. Six focused methods, one Documents owner method and five additional PostgreSQL cases prepared; CI pending. |
| HMR-094 | HMSR-111 | planning.PlanTarget | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-095 | HMSR-112 | audit.AuditEvent | COMPLETED — CI #584 GREEN | Required source/target module and target type, active exact event/category/optional severity/reason families, bounded sanitized payload/free text and persist/flush insertion enforced. Forward V20261007_007 adds optional catalog FKs, family guards and immutable event UPDATE/DELETE denial. Five focused and four added PostgreSQL/JPA/concurrency checks prepared; local focused Maven blocked by uncached Boot parent; CI #584 passed. |
| HMR-096 | HMSR-113 | hse.HseClosure | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-097 | HMSR-114 | hse.HseCorrectivePreventiveAction | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
| HMR-098 | HMSR-115 | integrity.IntegrityCase | STILL REQUIRED | registered migration: absent; dedicated test: absent; revalidate obligations before mutation |
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
- HMR-050..106 **STILL REQUIRED**: **21**;
- HMR-050..106 **BLOCKED**: **3**;
- HMR-050..106 **IMPLEMENTED during HPR-P2-008**: **33** (green production CI through #585);
- HMR-050..106 **SUPERSEDED**: **0**;
- HMR-054 completed; repaired CI #575 is green;
- HMR-080 remains blocked; HMR-055 prerequisite resolved and implemented in Batch 7.

## HPR-P2-008 Progress

- HMR-050 — **COMPLETED** at the first HPR-P2-008 execution step.
- Batch 7 **COMPLETED — CI #581 GREEN**: HMR-055, 061, 066, 081, 099; exact repaired head ec63af0414d7fa85b9200d4bd181ac799bd072ed. Batch 8 preflight split below; no Planning implementation claimed.
- Current remaining: **21 STILL REQUIRED + 3 BLOCKED (HMR-057, HMR-080, HMR-093)**; 33 implementations have green production CI through #585.

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
