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
