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

Verified source parent: `00c4fda266b2dfd175cca37ad789dc9462a5af0b`, 2026-10-09. This refresh supersedes the
historical HPR-P2-002 relationship list for current package inventory. The 24-module
root list remains unchanged. Current exports comprise 63 unique packages and 70
non-package-info Java files; [the complete contract inventory](CROSS_MODULE_CONTRACTS.md)
matches architecture enforcement exactly.

The direction below is contract owner to named module-consumer package. It is an
exported dependency surface, not a guarantee of complete deployed interaction.

| Contract owner | Named module-consumer packages |
|---|---|
| assets | hse |
| audit | alarm, custody, organization, risk, simulation |
| custody | planning |
| documents | audit, reporting |
| identity | assets, custody, documents, hse, incident, integration, integrity, planning, reporting, risk, workflow |
| integrity | assets |
| organization | analytics, assets, hse, identity, incident, integration, integrity, leakdetection, planning, reporting, risk, workflow |
| party | assets, planning, topology |
| planning | monitoring |
| telemetry | monitoring, planning |
| topology | analytics, assets, incident, integrity, leakdetection, organization, planning, risk, simulation |
| workflow | alarm, assets, audit, custody, documents, hse, incident, integrity, organization, planning, reporting, risk |

Three additional neutral extension packages have no invented module consumer:

| Owner | Extension package suffix | Exported lookup type |
|---|---|---|
| documents | target | DocumentsOwnedTargetLookup |
| workflow | target | WorkflowOwnedTargetLookup |
| risk | evidence | RiskOwnedEvidenceLookup |

`target` and `evidence` are extension roles, not current business module roots.
All packages above derive from Java declarations and ArchitectureGuardrailTest;
current implementations still require the actual owner provider and admission rules.

## Boundary Rule

A module may not reach directly into another module's private domain, infrastructure or private application packages. Cross-context collaboration must use a deliberately exported contract or another separately approved boundary.

## Target / Deferred Contexts

Legacy architecture material discusses `agents`, `environment` and `otsecurity`, but none is a current source root. They are not part of the 24 implemented module set and HPR-P2-002 does not create them.

Canonical terminology, ownership and focused cross-domain invariants are now defined under `doc/domain/**`. Detailed current-state documentation for each of the 24 modules remains HPR-P2-004 scope.
