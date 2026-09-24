# Architecture Decision Records

These short records preserve **already established** architecture and scoped legacy-data decisions. They supplement, not supersede, [AGENTS.md](../../AGENTS.md), [architecture](../ARCHITECTURE.md), [coding policy](../policy/Coding-policy.md) or owning roadmaps. Recheck main when the code has changed.

| ADR | Decision |
|---|---|
| [0001](0001-modular-monolith-hexagonal.md) | DDD modular monolith and hexagonal boundaries |
| [0002](0002-postgresql-flyway.md) | PostgreSQL target and Flyway-managed migrations |
| [0003](0003-api-module-boundaries.md) | REST/OpenAPI and application ports / ownership |
| [0004](0004-legacy-data-precedence.md) | Scoped workbook precedence, provisional deduplication and gating |

| [0005](0005-organization-operational-scope-integrity.md) | Superseded scope-identity proposal (historical context for [0006](0006-central-operational-scope-registry.md)) |
| [0006](0006-central-operational-scope-registry.md) | Accepted target design: one canonical scope registry, FK-based repeatable responsibility, staged independent numeric-ID migration ([issue #130](https://github.com/CHOUABBIA-AMINE/HidraAPI/issues/130)) |

"Accepted" records an existing architecture practice, not a new runtime deployment claim. "Scoped" applies only to the named data-provisioning context and does not grant generic security/import clearance.
