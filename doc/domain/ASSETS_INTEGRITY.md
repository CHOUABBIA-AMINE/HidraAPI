# Assets and Integrity Semantics

## Status

CURRENT focused semantic baseline.

## Assets

`MaintainableAsset` is the assets-module maintenance identity linked to topology by neutral topology references/snapshots.

Current rules show the ownership split directly:

- `AssetsBoundaryPolicy.ownsPhysicalTopologyIdentity()` returns false;
- `AssetsBoundaryPolicy.ownsMaintenanceWorkOrders()` returns true;
- maintainable assets require asset number/code/type, topology asset type/id, lifecycle status and registration timestamp;
- a maintainable asset cannot identify itself as its own parent;
- retired/disposed/cancelled assets are outside the active lifecycle.

`MaintenanceWorkOrder` is assets-owned maintenance execution state tied to a maintainable asset. CLOSED, CANCELLED and REJECTED work orders are outside the open lifecycle.

`AssetConditionRecord` represents condition evidence. Current `MaintenancePriorityClassifier` maps condition status to P1/P2/P3/P4 priority labels in code; those labels are maintenance-priority values and must not be confused with HPR roadmap phase names.

## Integrity

Integrity owns `IntegrityProgram`, `IntegrityAssessment`, `PipelineDefect` and `IntegrityCase`.

`IntegrityCase` requires case identity/type, topology asset type/id, lifecycle status and opening timestamp. Closed timestamps cannot precede opening; CLOSED and CANCELLED are closed lifecycle states.

`IntegrityAssessment` carries program/methodology, assessment/review/approval references and workflow/audit evidence. `PipelineDefect` represents defect evidence under integrity ownership.

## Assets vs Integrity Boundary

The current code makes the distinction explicit:

- assets owns maintainable assets and maintenance work orders;
- topology owns physical topology identity;
- integrity owns assessment/defect/case semantics;
- `IntegrityBoundaryPolicy.mayCreateMaintenanceWorkOrderDirectly()` returns false.

Integrity may therefore identify a maintenance need or retain references, but direct ownership/creation of maintenance work orders belongs to assets/application collaboration rather than the integrity domain itself.

## Cross-Domain References

Assets and integrity use topology IDs/types/codes/snapshots and organization/party/workflow/source references rather than importing foreign aggregates as owned state.

## Not Established

This document does not invent maintenance scheduling rules, inspection standards, remaining-life engineering equations, acceptance limits or automated maintenance authorization beyond source-visible behavior.

## Permanent reference and history rules

[Assets decisions](SEMANTIC_DECISIONS.md#assets-decisions) distinguish local parent
existence from unsupported business correlation: a referenced maintenance plan must
exist, but its existence does not impose asset equality. Fresh actor/unit/recommendation
and Workflow references are owner-validated; optional and valid unchanged historical
references remain permitted. Owner lookup failure propagates before writes.

[Integrity decisions](SEMANTIC_DECISIONS.md#integrity-decisions) retain nullable source
finding, program and defect references with local existence integrity when populated.
Cases use explicit approved taxonomy policy rather than guessed catalog families.
Fresh topology/actor/unit/Workflow evidence comes from owners; unchanged taxonomy
and historical target snapshots are preserved. An existing optional defect does not
impose an unsupported topology/status equality, and timestamp ordering does not invent
CLOSED-state timestamp coupling. The related
[IntegrityCaseReferenceValidation](../../src/main/java/dz/sh/hidra/modules/integrity/infrastructure/persistence/adapter/IntegrityCaseReferenceValidation.java)
and [MaintenanceWorkOrderReferenceValidation](../../src/main/java/dz/sh/hidra/modules/assets/infrastructure/persistence/adapter/MaintenanceWorkOrderReferenceValidation.java)
are the owned validation boundaries; their guard tests/migrations are linked per subject.

HSE CAPA follows its own approved action-family policy and owner contracts; it does
not silently gain an Integrity or Assets rule. See [HSE decisions](SEMANTIC_DECISIONS.md#hse-decisions).
