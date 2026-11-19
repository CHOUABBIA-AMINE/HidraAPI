# HSE Module

## Status

CURRENT — canonical module inventory and HPR-P2-009 permanent semantics.

## Verification Baseline

Source baseline: `44d4fe773d69ed51dd90820140c8d9e7aee6cba2`

Package root: `dz.sh.hidra.modules.hse`

Verified on 2026-10-09 against the source parent above. Metadata follows [the module index](README.md); business accountability follows this owning module, without an invented named human owner. Historical HPR-P2-004 established the inventory; HPR-P2-009 refreshes source applicability and lasting semantics. The canonical [API](../api/README.md) and [database](../database/README.md) sets retain their separate authority.

## Responsibility

Owns HSE cases, case closure, corrective/preventive actions and permit-to-work records.

The module has the current Hexagonal structure `api/`, `application/`, `domain/` and `infrastructure/`. Its private domain, application implementation and persistence internals remain owned by this module.

## Domain Model

Current domain model types (package-info excluded):

- [HseCase](../../src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCase.java)
- [HseClosure](../../src/main/java/dz/sh/hidra/modules/hse/domain/model/HseClosure.java)
- [HseCorrectivePreventiveAction](../../src/main/java/dz/sh/hidra/modules/hse/domain/model/HseCorrectivePreventiveAction.java)
- [PermitToWork](../../src/main/java/dz/sh/hidra/modules/hse/domain/model/PermitToWork.java)

Domain policies:

- [HseBoundaryPolicy](../../src/main/java/dz/sh/hidra/modules/hse/domain/policy/HseBoundaryPolicy.java)

Domain services:

- [HseCaseClosureGuard](../../src/main/java/dz/sh/hidra/modules/hse/domain/service/HseCaseClosureGuard.java)

Canonical semantic context: [ownership](../domain/DOMAIN_OWNERSHIP.md) and [permanent decisions](../domain/SEMANTIC_DECISIONS.md).

## Application and API Surface

Current inbound/use-case ports:

- [CloseHseCaseUseCase](../../src/main/java/dz/sh/hidra/modules/hse/application/port/in/CloseHseCaseUseCase.java)
- [CreateHseCapaUseCase](../../src/main/java/dz/sh/hidra/modules/hse/application/port/in/CreateHseCapaUseCase.java)
- [HseQueryUseCase](../../src/main/java/dz/sh/hidra/modules/hse/application/port/in/HseQueryUseCase.java)
- [OpenHseCaseUseCase](../../src/main/java/dz/sh/hidra/modules/hse/application/port/in/OpenHseCaseUseCase.java)

Current application services:

- [HseApplicationService](../../src/main/java/dz/sh/hidra/modules/hse/application/service/HseApplicationService.java)
- [HseQueryApplicationService](../../src/main/java/dz/sh/hidra/modules/hse/application/service/HseQueryApplicationService.java)

Current API/controller classes:

- [HseController](../../src/main/java/dz/sh/hidra/modules/hse/api/rest/controller/HseController.java)
- [HseQueryController](../../src/main/java/dz/sh/hidra/modules/hse/api/rest/controller/HseQueryController.java)
- [SpringHseController](../../src/main/java/dz/sh/hidra/modules/hse/api/rest/controller/SpringHseController.java)

These inventories identify source-visible adapters/use cases, not proof of every external integration. Exact wire contracts and compatibility rules are maintained in [the API set](../api/README.md).

## Persistence

Current JPA entity count: **17** (classes annotated `@Entity`, excluding package-info).

Persistence entities:

- [ComplianceAssessmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/ComplianceAssessmentJpaEntity.java)
- [ComplianceObligationJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/ComplianceObligationJpaEntity.java)
- [EmergencyDrillJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/EmergencyDrillJpaEntity.java)
- [EnvironmentalEventJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/EnvironmentalEventJpaEntity.java)
- [HazardReportJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HazardReportJpaEntity.java)
- [HseCaseEvidenceLinkJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseEvidenceLinkJpaEntity.java)
- [HseCaseJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseJpaEntity.java)
- [HseCaseStatusHistoryJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCaseStatusHistoryJpaEntity.java)
- [HseCatalogEntryJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCatalogEntryJpaEntity.java)
- [HseCatalogTranslationJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCatalogTranslationJpaEntity.java)
- [HseClosureJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseClosureJpaEntity.java)
- [HseCorrectivePreventiveActionJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseCorrectivePreventiveActionJpaEntity.java)
- [HseImpactAssessmentJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseImpactAssessmentJpaEntity.java)
- [HseInspectionJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/HseInspectionJpaEntity.java)
- [NearMissReportJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/NearMissReportJpaEntity.java)
- [PermitToWorkJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/PermitToWorkJpaEntity.java)
- [SafetyObservationJpaEntity](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/entity/SafetyObservationJpaEntity.java)

Persistence repository adapters and reference validators:

- [HseCapaReferenceValidation](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/HseCapaReferenceValidation.java)
- [HseCaseReferenceValidation](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/HseCaseReferenceValidation.java)
- [JpaHseCaseRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseCaseRepositoryAdapter.java)
- [JpaHseClosureLifecycleAdapter](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseClosureLifecycleAdapter.java)
- [JpaHseClosureRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseClosureRepositoryAdapter.java)
- [JpaHseCorrectivePreventiveActionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaHseCorrectivePreventiveActionRepositoryAdapter.java)
- [JpaPermitToWorkRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/hse/infrastructure/persistence/adapter/JpaPermitToWorkRepositoryAdapter.java)

Table/schema ownership and the generated dictionary remain in [the database set](../database/README.md). Entity presence does not invent constraints, retention policy or production-data approval.

## Cross-Module Boundary

Exported application contracts owned by this module:

No source class is present in this category at the verified parent.

Imported scalar contracts supplied by collaborating owners:

- [HseWorkOrderReferenceContract](../../src/main/java/dz/sh/hidra/modules/assets/application/contract/hse/HseWorkOrderReferenceContract.java)
- [HseActorContract](../../src/main/java/dz/sh/hidra/modules/identity/application/contract/hse/HseActorContract.java)
- [HseOrganizationReferenceContract](../../src/main/java/dz/sh/hidra/modules/organization/application/contract/hse/HseOrganizationReferenceContract.java)
- [RiskOwnedEvidenceLookup](../../src/main/java/dz/sh/hidra/modules/risk/application/contract/evidence/RiskOwnedEvidenceLookup.java)
- [HseWorkflowReferenceContract](../../src/main/java/dz/sh/hidra/modules/workflow/application/contract/hse/HseWorkflowReferenceContract.java)

Outbound application ports (persistence and collaborating capabilities):

- [HseAlarmReferencePort](../../src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseAlarmReferencePort.java)
- [HseAuditReferencePort](../../src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseAuditReferencePort.java)
- [HseCaseRepositoryPort](../../src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseCaseRepositoryPort.java)
- [HseClosureLifecyclePort](../../src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseClosureLifecyclePort.java)
- [HseClosureRepositoryPort](../../src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseClosureRepositoryPort.java)
- [HseCorrectivePreventiveActionRepositoryPort](../../src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseCorrectivePreventiveActionRepositoryPort.java)
- [HseDocumentReferencePort](../../src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseDocumentReferencePort.java)
- [HseIncidentReferencePort](../../src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseIncidentReferencePort.java)
- [HseLeakDetectionReferencePort](../../src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseLeakDetectionReferencePort.java)
- [HseTopologyReferencePort](../../src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseTopologyReferencePort.java)
- [HseWorkflowReferencePort](../../src/main/java/dz/sh/hidra/modules/hse/application/port/out/HseWorkflowReferencePort.java)
- [PermitToWorkRepositoryPort](../../src/main/java/dz/sh/hidra/modules/hse/application/port/out/PermitToWorkRepositoryPort.java)

Export scope is checked by [architecture guardrails](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) and [forensic closure](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java). Consumers use deliberately exported contracts; private domain, infrastructure and non-exported application packages remain private. Owner existence, eligibility and approval are separate predicates and cannot be substituted for one another.

## Current-State Limits

- Source/class presence is structural evidence; this documentation transfer executes no runtime test or external system.
- Legacy reviews/roadmaps remain unchanged history. HPR-P2-008 is closed; durable rules now live in [the semantic register](../domain/SEMANTIC_DECISIONS.md), with execution evidence in [reconciliation](../model-remediation/RECONCILIATION.md).
- Optional references and historical replay follow the subject-specific rules; no universal active-only rule is implied.
- Retention values, owner-approved policy contents and workload/physical survivability are not established by documentation.
- `agents`, `environment` and `otsecurity` are not implemented module roots. Target/deferred capabilities require separately admitted implementation.

## Permanent Semantic Decisions

HSE owns case, permit, CAPA and closure semantics. Closure delegates to the authoritative lifecycle with canonical actor evidence; CAPA classification uses owner-approved policy metadata and preserves optional/unchanged history without invented state restrictions.

The linked decisions carry the precise per-subject exceptions and source/test/migration evidence:

| Subject | Canonical decision |
|---|---|
| PermitToWork | [HMSR-083 reconciled rule](../domain/SEMANTIC_DECISIONS.md#hse-permittowork) |
| HseCase | [HMSR-096 reconciled rule](../domain/SEMANTIC_DECISIONS.md#hse-hsecase) |
| HseClosure | [HMSR-113 reconciled rule](../domain/SEMANTIC_DECISIONS.md#hse-hseclosure) |
| HseCorrectivePreventiveAction | [HMSR-114 reconciled rule](../domain/SEMANTIC_DECISIONS.md#hse-hsecorrectivepreventiveaction) |

## T17 enrolled GZ2 CAPA lifecycle — 2026-11-18 source handoff

Registered source implementation introduces HSE-owned enrolled company/FACILITY case scope, immutable source/command receipts, accepted T24 version evidence and human effectiveness findings while preserving un-enrolled HMR-082/096/097 semantics. The signed native contract contains 13 company/FACILITY-scoped endpoints for reviewed Incident enrollment, case/CAPA list/detail, proposal, Workflow-backed approval, owner assignment, accepted evidence attachment, completion, independent verification, case closure and history.

HSE owns six forward-only non-JPA PostgreSQL tables in `V20261118_002__t17_hse_capa_governed_lifecycle.sql`. The owner-access bridge uses five exact Identity/Topology/Incident/Documents/Workflow scalar contracts. Captured hashes/bytes and review evidence are historical, not permanent bypasses: current owner permissions and facility binding are rechecked before protected reads, replays and writes. Unenrolled HSE rows remain on the legacy paths, while T17-enrolled rows must not appear in unscoped list/count/detail or bypass new closure rules.

**Delivery state:** implementation submitted for exact-SHA CI-first validation; staged code has not been demonstrated to pass Maven, PostgreSQL, runtime OpenAPI, strict physical dictionary, the GZ2 positive journey or operational HSE acceptance. In particular, closure requires a persisted HSE impact assessment, not a caller-provided boolean or a substitute Incident review snapshot. Outstanding gates must be reported as such.

The scoped impact assessment command records an eligible HSE catalog type, severity and
reason against the exact enrolled case and current actor. It checks the case revision,
returns the original receipt for exact replay, and retains its assessment event in
scoped history. The scoped closure receipt binds the admitted assessment ID.
Signed HSE Case and CAPA Workflow approvals resolve the exact enrolled owner
target. Workflow reads and mutations recheck live access. Decisions require
an independent eligible human with the current HSE reviewer grant.

## T29 typed owner read discovery — source change awaiting exact-SHA CI

The two existing signed HSE native lists now accept optional exact `caseNumber` and `actionNumber` (trimmed, nonblank, at most 80 characters; case-sensitive), without changing operation IDs, verbs, result DTOs or page limits. Duplicate business keys remain legal. Case list streams a SQL-ordered company/FACILITY candidate cursor with bounded JDBC fetch size and authorizes all matching Incident sources before exposing any page or count. Incident proof failure aborts the whole response, including when off-page. CAPA list first authorizes its exact parent and uses one SQL statement for filtered page and total; empty and beyond-end responses retain correct totals. Legacy enrolled records remain excluded from unscoped list/count/detail, and absent well-formed legacy detail IDs return the same unavailable 404 as enrolled IDs. Generic Workbench explicitly prohibits the six derived HSE resource keys, including dependent evidence and closure. No new permission family, data migration or physical/OT capability is claimed. Signed A13 and related JVM assertions remain subject to hosted CI and exact generated OpenAPI reconciliation.
