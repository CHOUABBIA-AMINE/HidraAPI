# Duplicate Enum Vocabulary Review

**Recorded:** 2026-09-29  
**Roadmap:** HRA-080  
**Live main:** `71f41fcd7807ed06c3f1b664c17b568255be5257`

## 1. Decision rule

HRA-080 reviews duplicate enum vocabularies semantically. Identical constant sets are not sufficient
evidence for type consolidation.

A pair is eligible for `MERGE` only when both enums represent the same domain concept, share the
same lifecycle meaning, have compatible persistence/API ownership, and are expected to evolve
together.

A pair is `KEEP` when the types govern different aggregates, responsibilities, association roles,
or lifecycle boundaries even when their current constants are identical.

A pair is `DELETE` only when one enum has no independent semantic owner or consumer.

## 2. Reviewed pairs

| Pair | Constants | Decision | Semantic evidence |
|---|---|---|---|
| `LeakDetectionProfileStatus` / `LeakDetectionRuleStatus` | `DRAFT, ACTIVE, SUSPENDED, RETIRED` | **KEEP** | A profile governs configuration of a leak-detection method for an asset; a rule governs an individual detection rule. Both statuses are persisted on different JPA entities. Their lifecycle policies may diverge independently (for example a profile may remain active while one rule is suspended). |
| `RoleStatus` / `PermissionStatus` | `ACTIVE, DISABLED, DEPRECATED` | **KEEP** | Roles and permissions are separate authorization concepts. Identity administration and authorization code reasons about them independently; sharing constants does not make their lifecycle semantics identical. |
| `DelegationStatus` / `GrantStatus` | `ACTIVE, SUSPENDED, REVOKED, EXPIRED` | **KEEP** | `DelegationStatus` governs an authorization delegation from one subject to another; `GrantStatus` governs role/permission grants. Grant status is actively queried by administrator authorization logic and repositories. Delegation revocation/expiry and grant revocation/expiry are separate business processes. |
| `ReportTemplateVersionStatus` / `ReportDefinitionStatus` | `DRAFT, ACTIVE, RETIRED` | **KEEP** | Report definitions and template versions have different ownership and versioning lifecycles. A definition can remain active while a specific template version is retired/replaced. Both values are persisted independently. |
| `ResponsibilityAssigneeType` / `ContactPointTargetType` | `EMPLOYEE, ORGANIZATION_UNIT` | **KEEP** | The first defines who may receive an organizational responsibility; the second defines who may own an operational contact point. Organization roadmap/tests explicitly model these as separate typed dependency boundaries even though the currently permitted subject set matches. |
| `MappingProfileStatus` / `ExternalSystemStatus` | `DRAFT, ACTIVE, SUSPENDED, RETIRED` | **KEEP** | A mapping profile is a transformation/mapping configuration lifecycle; an external system is an integration endpoint/system lifecycle. Suspending one does not semantically imply suspending the other, and each value is persisted on its own entity. |
| `SchemaVersionStatus` / `ContractStatus` | `DRAFT, ACTIVE, DEPRECATED, RETIRED` | **KEEP** | A schema version describes one version of an integration schema; a contract describes the higher-level integration data contract. A particular schema version may be deprecated while the contract remains active with another version. |

## 3. Compatibility considerations

All reviewed statuses/types are domain enums used by persistence models, domain/application code, or
typed architecture boundaries. Several are serialized through APIs under the HRA-102 same-module
domain-enum policy.

Therefore a merge would not be a source-only cleanup. It could couple:

- persisted enum literals;
- JPA attribute types;
- Spring Data query signatures;
- REST/OpenAPI enum vocabularies;
- application/domain lifecycle semantics.

HRA-080 found no semantic benefit sufficient to justify that coupling.

## 4. HRA-081 disposition

No reviewed pair is approved for `MERGE` or `DELETE`.

```text
reviewed pairs  7
KEEP            7
MERGE           0
DELETE          0
```

Accordingly, HRA-081 has no authorized production change. It must not perform enum consolidation
merely to reduce type count. If a future domain decision proves two enum concepts truly identical,
the roadmap must be amended with explicit persistence/API compatibility handling before code changes.

## 5. Final decision

The duplicate vocabularies are **deliberate semantic duplication** rather than redundant types.

The current enum boundaries remain unchanged.
