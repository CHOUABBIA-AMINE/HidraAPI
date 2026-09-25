# ADR-0005 — Organization operational scope identity and multi-scope responsibility

**Status:** Superseded in scope-identity/GLOBAL representation by [ADR-0006](0006-central-operational-scope-registry.md); retained for history. Previously accepted as the target design for issue [#130](https://github.com/CHOUABBIA-AMINE/HidraAPI/issues/130); implementation is **not** approved by this record alone and remains subject to the organization roadmap's discrete execution and validation gates.  
**Decision date:** 2026-09-24.  
**Owners:** Organization bounded context; target-object identity and current attributes remain with their owning bounded contexts.  
**Supersedes:** No prior ADR. Refines the operational-scope sketches in `docs/roadmap/organization.md`; approved architecture takes precedence over those outdated examples for future correction tasks.

## Context and reviewed evidence

The current `OrganizationUnit`, `EmployeeAssignment`, and `ResponsibilityAssignment` records each carry independently mutable `operationalScopeType`, `operationalScopeId`, `operationalScopeCode`, and `operationalScopeName`. `OperationalScopeReference` groups those four fields but does not establish referential integrity by grouping them. The current Flyway organization schema stores these values as scalar columns and offers no polymorphic FK or validation against the target object's authoritative owner. One embedded tuple on `OrganizationUnit` cannot model a unit responsible for multiple physical assets. Issue #130 records the correction request.

Architecture already mandates: organization owns people, units and assignments; topology owns physical network objects; cross-module interaction uses public ports, stable references and events; existing migrations are immutable once applied. The `ResponsibilityAssignment` record is already effective-dated, supports assignee type/ID and responsibility type, and is the chosen starting point rather than inventing a parallel unit-to-scope aggregate.

## Decision

### 1. Separation and cardinality

`OrganizationUnit` represents **organizational identity and hierarchy only**. Remove the embedded operational-scope tuple from its canonical domain model and write contracts **after** compatible migration and legacy reconciliation. An organizational unit has **zero-to-many** `ResponsibilityAssignment` records for independently identified scope targets; assignments may overlap in time when their responsibility types differ. `ResponsibilityAssignment` owns the validity window, lifecycle and responsibility semantics. Do not automatically inherit an assigned unit's scopes to descendants or its employees; such propagation requires an explicit separately approved policy.

Retain `EmployeeAssignment` as the employee–organization unit–position/tenure association. Its existing optional scope tuple must **not** become a second authoritative owner of operational responsibility: deprecate it after auditing consumers and reconcile employee-specific mandates into `ResponsibilityAssignment` (assignee type = employee) when needed. Employee membership in a unit does not confer authority over all the unit's assets.

### 2. Reference identity and type rules

For an entity-backed target, the canonical persistent reference is `(OperationalScopeType, operationalScopeId)`. The type discriminator is mandatory because IDs need not be globally unique across target domains. `operationalScopeId` is a stable target-owner identifier, not a display code, name or inferred string from SONATRACH organizational labels. For `PIPELINE_SYSTEM`, `PIPELINE`, `FACILITY`, and `EQUIPMENT`, resolve against **actual current topology contracts** and real target IDs; do not assume every enum constant maps to a deployed API or table. `ORGANIZATION_UNIT` is resolved through the organization module with a self-target/cycle policy and no hidden granting of access.

`GLOBAL` is a deliberate organization-wide applicability marker **with no target ID**, not an alias for a fabricated global object: type = GLOBAL requires ID = null, no code/name; all entity-backed types require nonblank ID. The existing DB column `hidra_org_responsibility_assignment.operational_scope_id` is NOT NULL, so the change must use an additive migration before global assignments become writable. `CUSTOM` is rejected unless its target type/namespace, owner, resolver, allowed roles and lifecycle rules have been registered by a separate approved contract; no free-form unresolvable IDs. Reject missing, mismatched, unregistered or disallowed scope references.

### 3. Current attributes versus history

Current scope code, multilingual name, status and other descriptive details are served by the **owning module** using an explicit read/resolution port. Callers cannot submit independent authoritative code/name fields. Historical descriptions, if needed for an event, report or audit, may be stored as clearly labeled **immutable** snapshots with capture timestamp and provenance and must never be substituted for current object resolution. This ADR does not require a snapshot on each responsibility assignment.

### 4. Integrity and lifetime

Application commands resolve and validate type, ID, target existence, assignability, permitted assignee, role and effective window before creation/change, and enforce authorization separately from organizational responsibility. For an assignable object retired after an assignment is made: do not erase assignment history; block new assignments and flag/revalidate affected active assignments through owner events or reconciliation. Polymorphic cross-module references cannot be made universally safe by a single DB FK: implement resolver ports, authorization, audit, idempotency, version-aware change handling and periodic reconciliation. Define TOCTOU/concurrency policy in implementation (optimistic locking or owner-version checks and safe revalidation); do not claim strong distributed transactional guarantees that do not exist.

Intervals are half-open `[validFrom, validTo)` (null end means open-ended), with `validFrom < validTo` when end is set. Define one active assignment for an exact `(assigneeType, assigneeId, responsibilityType, scopeType, scopeId)` over any overlapping interval; duplicate/overlap attempts are rejected or return the existing idempotent result. Different roles on the same target, or different targets, may coexist. For GLOBAL use the same uniqueness key with a normalized no-ID sentinel **only inside matching/constraint logic**, never persist a fabricated target ID. Define how rejected, revoked or superseded assignments participate in overlap checks; no cascade deletion of historical assignments.

### 5. Schema, API and rollout

Use **additive, separately versioned Flyway migrations**; do not edit `V20260611_002__create_organization_tables.sql`. Inventory every caller and current JPA/API contract, verify actual DB values, map legitimate tuple pairs to target owner IDs, and quarantine unknown/mismatched/orphan records rather than guessing. Introduce validated assignment queries/commands and compatible read projections before retiring old request/response fields. Plan deployment order, rollback, reference reconciliation, authorization and monitoring. After an approved backfill and explicit consumers' migration, retire the redundant columns through a later independent task. Do not mutate topology tables or import scope links inferred solely from an organizational chart.

### 6. Security and industrial safety

A responsibility assignment describes a business responsibility, **not** an identity-role grant or direct industrial-control permission. Existing identity authorization, workflow approvals and audit requirements continue to apply. No scope relationship enables direct SCADA/PLC/pump/valve actuation. Domain code imports no topology internals; external data enters via the owning module's input ports.

## Alternatives considered

- **Keep one tuple on OrganizationUnit:** rejected; one-to-many responsibility is lossy and code/name can disagree with ID.
- **Introduce a separate new OrganizationUnitScope table immediately:** rejected for now; existing `ResponsibilityAssignment` already models assignee, responsibility and dates. Reassess through a new ADR only if its semantics prove insufficient.
- **Persist ID without type:** rejected unless a verified system-wide globally unique namespace and owner resolver is established; type-discriminated ID is safer with current bounded contexts.
- **Persist current code/name as writable duplicates:** rejected. Immutable labeled historical snapshots are allowed for a distinct audit need.

## Consequences and explicit limits

This decision changes the **target architecture**, not the present Java models, schema or operational maturity. `OperationalScopeReference` will be refined to represent canonical typed identity and its invariants, but the final Java shape and DTO migration sequence are implementation-task decisions. Global and cross-owner reference validation requires an approved resolver contract and cannot be enforced by PostgreSQL FK alone. The presence of a type in the enum does not guarantee an owner resolver exists. Import of SONATRACH hierarchy may proceed independently of operational scope associations under the existing provisioning gates.

## Acceptance/verification

Issue #130 is resolved only after all roadmap tasks are implemented and verified: multiple concurrent scopes per unit; matching owner-resolved current details; rejection of invalid type/ID pairs and unregistered CUSTOM; no-ID GLOBAL; temporal overlap and role behavior; controlled employee migration; authorization/audit, target-retirement policy and preservation of history; additive PostgreSQL migration and quarantined legacy records; public-port architecture tests, JPA and API tests. There is **no** claim in this ADR that tests passed or production data was migrated.

## References

- [Issue #130](https://github.com/CHOUABBIA-AMINE/HidraAPI/issues/130)
- [Organization module roadmap](../roadmap/organization.md)
- [Macro architecture](../architecture/Hidra%20%E2%80%94%20Macro%20Architecture.md)
- [Micro architecture](../architecture/Hidra%20%E2%80%94%20Micro%20Architecture.md)
- [Database migration](../../src/main/resources/db/migration/V20260611_002__create_organization_tables.sql)
