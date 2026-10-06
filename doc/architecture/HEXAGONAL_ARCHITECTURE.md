# HidraAPI Hexagonal Architecture

## Status

CURRENT — repository-enforced Hexagonal/Ports-and-Adapters boundary model.

## Module Shape

The canonical module structure is:

```text
api                inbound delivery adapters
application
  port.in           inbound/use-case contracts where present
  service           use-case orchestration
  port.out          outbound contracts where present
  contract          deliberate cross-module exported contracts
domain              business model, rules and policies
infrastructure      persistence and technical/external adapters
```

Exact subpackages vary by module; the architectural direction does not.

## Enforced Dependency Rules

`ArchitectureGuardrailTest` currently enforces all of the following:

1. `kernel` cannot depend on Spring, JPA, Hibernate, platform or business modules.
2. business `domain` cannot depend on Spring, JPA, Hibernate, Jackson, Swagger annotations or platform.
3. `application` cannot depend on module API, module infrastructure, JPA, Spring Web or Spring Data.
4. module `api` cannot depend on module infrastructure.
5. REST controllers cannot depend directly on `application.port.out`, `infrastructure.persistence` or Spring Data repositories.
6. business modules cannot depend on another module's private domain, infrastructure or private application packages.
7. deliberate cross-module access is allowed only through the exported `application.contract.<consumer>` prefixes.
8. the transitional cross-module dependency allowlist is currently empty.

## Normal Request/Command Direction

```text
API adapter/controller
        |
        v
application inbound use case
        |
        v
domain behavior
        |
        v
application outbound port
        |
        v
infrastructure adapter
```

The domain remains unaware of HTTP, JPA and infrastructure technology.

## Platform Exception Boundary

Platform-wide generic persistence access is not generally allowed. The architecture test restricts JPA access to the reviewed `HidraOperationalWorkbenchService` boundary and requires its fail-closed `HidraOperationalWorkbenchExposurePolicy` dependency. Platform code may not use that exception to reach module persistence packages.

## Cross-Module Direction

Cross-module contract packages are owned by the providing module and named for their intended consumer:

```text
dz.sh.hidra.modules.<owner>.application.contract.<consumer>
```

Consumers depend on that explicit surface rather than the provider's domain model, repositories or private application ports.

## Target / Deferred Rules

Any future adapter, integration protocol, module or extracted service must preserve equivalent inward dependency and ownership rules or introduce an explicit approved architecture decision and corresponding enforcement update. HPR-P2-002 does not approve a new exception.
