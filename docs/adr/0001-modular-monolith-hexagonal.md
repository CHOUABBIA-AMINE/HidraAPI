# ADR-0001 — Modular monolith, DDD and hexagonal architecture

**Status:** Accepted (existing architecture; recorded 2026-09-22).

## Context
HidraAPI contains business modules with distinct domain and persistence ownership, deployed in a single modular-monolith backend.

## Decision
Retain the **modular monolith first** structure under dz.sh.hidra: framework-neutral kernel, technical platform and business modules with domain, application, API and infrastructure layers. API depends on application ports; infrastructure adapts external systems/persistence to inward-defined ports; domain owns business rules without Spring, JPA, REST or OpenAPI dependencies. Cross-module interaction uses approved stable IDs/ports/events, not imports of foreign aggregate internals. Moving to microservices requires a separate approved architecture decision.

## Consequences
Respect code/module boundaries and architecture guardrails. Do not add generic shared/common/helper packages or infer production maturity from a class filename.

## Source
[Architecture](../ARCHITECTURE.md), [README](../../README.md), [coding policy](../policy/Coding-policy.md), [AGENTS.md](../../AGENTS.md).
