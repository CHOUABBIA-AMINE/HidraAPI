# HidraAPI Data Provenance

## Status and applicability

CURRENT — HPR-P2-010 source-derived governance baseline, verified on 2026-10-09
against parent `b36733fc05e789613485606e1e1dd1731b11af53`. Metadata and ownership follow [the data index](README.md).
TARGET admission requirements below are separated from implemented controls;
unknown owner approvals and business values remain NOT ESTABLISHED.

## Current evidence matrix

These are source-visible evidence boundaries, not a claim that every historical row
has complete authenticated lineage. The linked owner/source and
[subject decisions](../domain/SEMANTIC_DECISIONS.md) define precise optionality,
unchanged-history exceptions and where actual validation occurs.

| Evidence population | Recorded or validated provenance | Enforcement/evidence and limit |
|---|---|---|
| Raw Telemetry | Point, source/receive timestamps, quality, optional ingestion/mapping/batch context and typed value | [TelemetryReading](../../src/main/java/dz/sh/hidra/modules/telemetry/domain/model/TelemetryReading.java); raw persistence is not trust and optional metadata is not universally required |
| Trusted Telemetry | Matching reading/point/PASSED assessment, accepted MEDIUM/HIGH/CERTIFIED level, eligible active point/quality and populated optional unit/batch; values and applicable binding snapshots derived from evidence | [TrustedTelemetryReadingApplicationService](../../src/main/java/dz/sh/hidra/modules/telemetry/application/service/TrustedTelemetryReadingApplicationService.java); [TrustedTelemetryReadingSemanticRemediationTest](../../src/test/java/dz/sh/hidra/modules/telemetry/semantic/TrustedTelemetryReadingSemanticRemediationTest.java); multiple applicable bindings require selection; trust level is not upgraded |
| Planning target | Locked revision, same-revision optional nomination/scenario, exact type/value policy, owner-derived fresh topology/point snapshots | [PlanTargetReferenceValidation](../../src/main/java/dz/sh/hidra/modules/planning/infrastructure/persistence/adapter/PlanTargetReferenceValidation.java); valid unchanged snapshots are retained rather than refreshed |
| Monitoring deviation | Mandatory Planning scalar target; coherent fresh optional point/reading and locked populated evaluation context | [PlanActualDeviationReferenceValidation](../../src/main/java/dz/sh/hidra/modules/monitoring/infrastructure/persistence/adapter/PlanActualDeviationReferenceValidation.java); optional references remain optional and unchanged historical evidence is preserved |
| Analytics lineage metadata | Dataset-version/source-module/type/ID, optional snapshot/version/period and lineage role; source access/refresh/trusted metadata | [AnalyticsDatasetLineageJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDatasetLineageJpaEntity.java); [AnalyticsDataSourceReferenceJpaEntity](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/entity/AnalyticsDataSourceReferenceJpaEntity.java); structural fields do not prove source authenticity or universally complete traversal |
| Analytics output version/watermark | Captured projection-definition version, success watermark, immutable published dataset version | [JpaAnalyticsProjectionRunRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsProjectionRunRepositoryAdapter.java); [JpaAnalyticsDatasetVersionRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/analytics/infrastructure/persistence/adapter/JpaAnalyticsDatasetVersionRepositoryAdapter.java); outcome evidence is not model accuracy or an AI inference runtime |
| Report reproducibility | Request/definition/template/required-parameter evidence and actual required Workflow approval on queue path | [ReportingApplicationService](../../src/main/java/dz/sh/hidra/modules/reporting/application/service/ReportingApplicationService.java); queuing checks are not automatically guarantees of every generic repository save |
| Simulation output | Scenario/model/input/version run context, immutable completed run, audited recommendation publication | [Simulation source inventory](../modules/simulation.md); [Simulation decisions](../domain/SEMANTIC_DECISIONS.md); recommendation is advisory and solver metadata is not proof of execution |
| Document content | Uploader owner evidence, document/version parenting, checksums and stored-binary metadata | [DocumentContentTransferService](../../src/main/java/dz/sh/hidra/modules/documents/application/service/DocumentContentTransferService.java); newly created blob cleanup handles failure/confirmed rollback, while unknown commit outcome preserves possibly referenced content |
| Integration exchange/failure | System/message/job correlation and populated local evidence; manual dead-letter actor/time/comment trio | [JpaIntegrationDeadLetterRecordRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/integration/infrastructure/persistence/adapter/JpaIntegrationDeadLetterRecordRepositoryAdapter.java); fresh manual actor is authenticated/eligible and recorded provenance is immutable, not proof that external payload is true |
| Durable audit and process evidence | Sanitized insert-only Audit event/access evidence; Workflow action/state history and Alarm lifecycle event coupled to owned write paths | [JpaAuditEventRepositoryAdapter](../../src/main/java/dz/sh/hidra/modules/audit/infrastructure/persistence/adapter/JpaAuditEventRepositoryAdapter.java); [AuditInputPolicy](../../src/main/java/dz/sh/hidra/modules/audit/application/service/AuditInputPolicy.java); [Workflow](../modules/workflow.md); [Alarm](../modules/alarm.md); append-only evidence is not a universal retention/hold engine |
| Referenced risk evidence | Exactly one supported owner confirms exact module/type/ID and available canonical labels | [RiskEvidenceRegistry](../../src/main/java/dz/sh/hidra/modules/risk/application/service/RiskEvidenceRegistry.java); existence and optional snapshots do not replace context-specific approval |

## Identity, time and content are separate facts

A Git commit/blob identifies repository content, not the data's business effective
date. A conventional file digest and a Git object SHA use different representations;
label the actual digest rather than renaming one SHA family as another. Checksum/hash
metadata can identify changed bytes when actually verified; it does not alone prove
who collected the data, its accuracy, permission to reuse it or business authenticity.

Source time, receive time, server action time and contractual expiry time retain their
coded meanings. Correlation IDs support traceability, not authentication. Canonical
owner snapshots preserve context from a particular write; uncontrolled live refresh
would rewrite that historical meaning. Fresh reference checks do not impose a global
active-only rule on valid historical records.

Authenticated actor handling is path-specific. Documents/Workflow and the admitted
actor-sensitive use cases obtain real Identity/security evidence; persisted display
labels alone cannot prove it. The inherited
[security boundary](../security/TRUST_BOUNDARIES.md) documents its own verification parent, including the untrusted nature of caller
headers. Do not claim all historical records were created through today's supported
path or that an imported ID could reproduce an authenticated historical action.

## TARGET provenance admission for new data

A future governed import should retain approved source identity/digest, source-owner
and reuse decision, collection/effective-time meaning, transformation/mapping version,
permitted owner write path, validation/reconciliation results, exceptions and final
acceptance. This is an admission record to be designed for actual approved datasets;
no new provenance table, fabricated actor/snapshot or historical event is created here.

## NOT ESTABLISHED

Universal end-to-end lineage completeness, accuracy of external source claims,
legally approved reuse, AI inference provenance and complete historical actor
authenticity are NOT ESTABLISHED. Existing test files support their specific coded
boundaries; they were inspected, not run again by this documentation task.
