# HidraAPI Architecture Testing

## Status and applicability

CURRENT — HPR-P2-011 source-derived verification documentation, checked on 2026-10-09
against parent `35d9d949aa773a4d754c22d00181330f579f752b`. Owner authority, evidence precedence and
verification limits follow [the testing index](README.md). TARGET additions and
NOT ESTABLISHED facts below do not represent implemented gates or executed results.

## Current enforced rules

[ArchitectureGuardrailTest](../../src/test/java/dz/sh/hidra/ArchitectureGuardrailTest.java) imports production classes with ArchUnit
and combines dependency, reflection and persistence checks. The exact test source,
not a desired architecture diagram, defines the enforced scope.

| Boundary | Specific current assertion |
|---|---|
| Kernel | No Spring/JPA/Hibernate/Swagger, platform or module dependency |
| Business domain | No framework/platform infrastructure dependency |
| Application/API | Application avoids API/infrastructure; API avoids infrastructure; controllers avoid outbound/persistence repositories |
| Cross-module | Private packages denied unless exact exported owner contract package or explicit transitional exception |
| Platform JPA | Only the reviewed workbench boundary may access the admitted persistence surface |
| Transactions | Organization responsibility services must remain proxyable |
| Persistence naming | JPA table names unique; column names unique within an entity |
| Package structure | identityaccess package absent |

The exported contract set is explicitly enumerated, not permission to import every
application package. The current transitional cross-module dependency map is empty;
its exact-and-in-use assertion remains present, alongside a negative classifier case.
[ForensicRemediationClosureTest](../../src/test/java/dz/sh/hidra/ForensicRemediationClosureTest.java) independently checks source imports,
fictional scaffolding, wire-domain leakage and retained closure evidence. The
canonical [module boundaries](../architecture/MODULE_BOUNDARIES.md) and
[cross-module contracts](../architecture/CROSS_MODULE_CONTRACTS.md) retain their own
verification baseline; current test/source exports take precedence over older lists.

## Other mechanisms and evidence limits

| Test | Mechanism and responsibility |
|---|---|
| [DomainPersistenceMirrorGuardrailTest](../../src/test/java/dz/sh/hidra/DomainPersistenceMirrorGuardrailTest.java) | Source/file and persistence ownership inventory; retired mirrors remain absent |
| [DomainInvariantGuardrailTest](../../src/test/java/dz/sh/hidra/DomainInvariantGuardrailTest.java) | Recorded classified invariant batch/source guard, not exhaustive dynamic invariant exploration |
| [GeneratedBoundaryMapperContractTest](../../src/test/java/dz/sh/hidra/modules/GeneratedBoundaryMapperContractTest.java) | Reflection/generated mapper boundary contracts |
| [MonitoringPlanTargetReferenceContractTest](../../src/test/java/dz/sh/hidra/modules/planning/semantic/MonitoringPlanTargetReferenceContractTest.java) | Planning-owned provider contract example; consumer references preserve ownership |

The [strategy commands](TEST_STRATEGY.md#commands-and-prerequisites) select these
existing tests. An architecture rule passing says its particular classified boundary
held at that exact build; it does not prove every business rule, database race or
runtime authorization. Owner-provider behavior and transaction tests remain distinct.
All 24 module documents and [permanent decisions](../domain/SEMANTIC_DECISIONS.md)
provide subject-specific evidence rather than extending test claims by analogy.

## TARGET and NOT ESTABLISHED

No new exports/allowlists, source classifiers, ArchUnit rules or mapper tests are
added. Exhaustive semantic coverage and deployed module-isolation guarantees are
NOT ESTABLISHED by the static rule inventory. Future architecture enforcement changes
need their own admitted implementation and exact-head verification.
