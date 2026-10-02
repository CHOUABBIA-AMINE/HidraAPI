# ADR-0003 — REST API boundary and module application ports

**Status:** Accepted (existing policy; recorded 2026-09-22).

## Context
Client and external integration contracts must not leak persistence or framework details into the domain model.

## Decision
Expose Spring MVC REST contracts in the owning module's api/rest packages; apply OpenAPI annotations to controllers/API request-response records only. Controllers invoke application use-case ports, not infrastructure/JPA repositories. Domain is framework-independent; infrastructure implements ports. Cross-module writes remain owned by the destination bounded context; reference another domain through approved stable IDs, ports or events.

## Consequences
Do not infer that a domain model automatically has a supported public write API; inspect current contracts before import. Preserve the module ownership and dependency guardrail tests.

## Source
[README API conventions](../../README.md), [architecture](../ARCHITECTURE.md), [coding policy](../policy/Coding-policy.md), [AGENTS.md](../../AGENTS.md).
