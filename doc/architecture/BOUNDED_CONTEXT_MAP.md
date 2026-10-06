# HidraAPI Bounded-Context Map

## Status

CURRENT structural bounded-context/module map. Canonical ubiquitous language and cross-domain semantic ownership are maintained under `doc/domain/**`; per-module detail remains HPR-P2-004 scope.

## Current Implemented Module Roots

The live source tree contains exactly these 24 business module roots:

| Module | Package root |
|---|---|
| alarm | `dz.sh.hidra.modules.alarm` |
| analytics | `dz.sh.hidra.modules.analytics` |
| assets | `dz.sh.hidra.modules.assets` |
| audit | `dz.sh.hidra.modules.audit` |
| configuration | `dz.sh.hidra.modules.configuration` |
| custody | `dz.sh.hidra.modules.custody` |
| documents | `dz.sh.hidra.modules.documents` |
| hse | `dz.sh.hidra.modules.hse` |
| identity | `dz.sh.hidra.modules.identity` |
| incident | `dz.sh.hidra.modules.incident` |
| integration | `dz.sh.hidra.modules.integration` |
| integrity | `dz.sh.hidra.modules.integrity` |
| leakdetection | `dz.sh.hidra.modules.leakdetection` |
| monitoring | `dz.sh.hidra.modules.monitoring` |
| notification | `dz.sh.hidra.modules.notification` |
| organization | `dz.sh.hidra.modules.organization` |
| party | `dz.sh.hidra.modules.party` |
| planning | `dz.sh.hidra.modules.planning` |
| reporting | `dz.sh.hidra.modules.reporting` |
| risk | `dz.sh.hidra.modules.risk` |
| simulation | `dz.sh.hidra.modules.simulation` |
| telemetry | `dz.sh.hidra.modules.telemetry` |
| topology | `dz.sh.hidra.modules.topology` |
| workflow | `dz.sh.hidra.modules.workflow` |

Package-root presence proves structural implementation, not semantic completeness or operational maturity.

## Verified Cross-Context Contract Relationships

The current architecture exposes cross-module application contracts in the direction **contract owner → named consumer**:

```text
workflow     -> planning, organization, alarm, reporting
topology     -> organization, simulation, leakdetection, analytics, assets, risk
organization -> analytics, assets, integration, reporting, risk
audit        -> organization, alarm, risk
party        -> topology, assets
telemetry    -> monitoring
identity     -> reporting
```

These relationships are derived from current `application.contract.<consumer>` packages and the exported-package allowlist in `ArchitectureGuardrailTest`. They are architectural dependency surfaces, not claims that every possible business interaction is represented here.

## Boundary Rule

A module may not reach directly into another module's private domain, infrastructure or private application packages. Cross-context collaboration must use a deliberately exported contract or another separately approved boundary.

## Target / Deferred Contexts

Legacy architecture material discusses `agents`, `environment` and `otsecurity`, but none is a current source root. They are not part of the 24 implemented module set and HPR-P2-002 does not create them.

Canonical terminology, ownership and focused cross-domain invariants are now defined under `doc/domain/**`. Detailed current-state documentation for each of the 24 modules remains HPR-P2-004 scope.
